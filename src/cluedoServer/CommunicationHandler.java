package cluedoServer;

import json.CluedoJSON;
import json.CluedoProtokollChecker;

import org.json.JSONObject;

import staticClasses.Config;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoNetworkGUI.DataGuiManagerServer;
import cluedoNetworkLayer.CluedoField;
import cluedoNetworkLayer.CluedoPosition;
import enums.JoinGameStatus;
import enums.NetworkHandhakeCodes;
import enums.PlayerStates;

/**
 * @author guldener
 * verteilt die nachrichten der des clients
 */
class CommunicationHandler implements Runnable{
	
	ClientItem client;
	Connector networkService;
	
	DataManagerServer dataManager;
	DataGuiManagerServer dataGuiManager;
	
	boolean run = true;	
	boolean readyForCommunication = false;
	String currentMsg;
	
	/**
	 * @param ss
	 * @param c
	 * @param g
	 * @param cList
	 * @param bList
	 * 
	 * tcp verbindung steht server wartet auf tcp handshake
	 */
	CommunicationHandler(ClientItem c, DataManagerServer dm,DataGuiManagerServer dgm) {
		dataManager = dm;
		dataGuiManager = dgm;
		client = c;
	}	
	
	
	private void awaitingLoginAttempt (){
		auxx.logfine("awaiting login from client");
		while (!readyForCommunication) { // will keep listening for valid login msg
			try {
				String message = auxx.getTCPMessage(client.getSocket()).trim();
				CluedoProtokollChecker checker = new CluedoProtokollChecker(
						new CluedoJSON(
								new JSONObject(message)));
				NetworkHandhakeCodes errcode = checker.validateExpectedType("login",null);

				if (errcode == NetworkHandhakeCodes.OK) {					
					client.setExpansions(
						auxx.makeConjunction(
							Config.EXPANSIONS, 
							checker.getMessage().getJSONArray("expansions")
						)
					);
					client.setNick(checker.getMessage().getString("nick"));
					client.setGroupName(checker.getMessage().getString("group"));					
					client.sendMsg(NetworkMessages.login_sucMsg(
							client.getExpansions(),
							dataManager.getClientPool(), 
							dataManager.getGameList()
							)
					);
					if (dataGuiManager.addNetworkActor(client,"logged in"))
						dataManager.notifyAll(NetworkMessages.user_addedMsg(client.getNick()));
					else {
						client.sendMsg(NetworkMessages.error_Msg(client.getNick()+" already exists, try again with different nick"));
						readyForCommunication = false;
					}						
					readyForCommunication = true;
				}
				else if (errcode == NetworkHandhakeCodes.TYPEOK_MESERR 
						|| errcode == NetworkHandhakeCodes.TYPERR){
					
					dataGuiManager.addMsgIn(client.getAdress()+" sends invalid Messages : \n"+checker.getErrString());
					client.sendMsg(NetworkMessages.error_Msg("you are violating the protokoll due to the following: \n"+checker.getErrString()));
					client.sendMsg(NetworkMessages.disconnectMsg());
					client.closingConnection(dataManager.getGroupName()+" is closing connection");
					dataManager.blacklist(client);					
					killThread();
				}
				
				auxx.loginfo("login attempt :"+ checker.getMessage().toString());				
			} 
			catch (Exception e) {
				auxx.logsevere("communcitationhandler of client"+ client.getNick(), e);
			}
		}	
	}
	
	@Override
	public void run(){	
		awaitingLoginAttempt();
		while (run){
			try {
	           String message = auxx.getTCPMessage(client.socket).trim();
			   currentMsg = message;

	           CluedoProtokollChecker checker = new CluedoProtokollChecker(new JSONObject(message));
	           checker.validate();
	           if (checker.isValid()){
	        	   if (checker.getType().equals("create game")){													//CREATE GAME
	        		   createGame(checker.getMessage().getString("color"),client);
	        	   }
	        	   else if (checker.getType().equals("join game")){
	        		   int gameID = checker.getMessage().getInt("gameID");											//JOIN GAME
	        		   String color = checker.getMessage().getString("color");
	        		   JoinGameStatus status = dataGuiManager.joinGame(gameID, color, client) ;
	        		   if (status == JoinGameStatus.added){
	        			   dataManager.notifyAll(
		        				   NetworkMessages.player_addedMsg(
		        						   NetworkMessages.player_info(
		        								   client.getNick(), 
		        								   color , 
		        								   PlayerStates.do_nothing.getName()
		        								   ),
		        						   gameID
		        						   )
		        				   );
	        			   auxx.logfine(status.name());
	        		   }
	        		   else if (status == JoinGameStatus.already_joined)
	        			   client.sendMsg(NetworkMessages.error_Msg("you have already joined this game"));
	        		   else if (status == JoinGameStatus.nick_already_taken)
	        			   client.sendMsg(NetworkMessages.error_Msg("color is already chosen by someone else"));
	        		   else if (status == JoinGameStatus.not_joinable)
	        			   client.sendMsg(NetworkMessages.error_Msg("you can't join this game, idiot"));	        		  
	        	   }
	        	   else if (checker.getType().equals("start game")) {												//START GAME
		        	   int gameID = checker.getMessage().getInt("gameID");
		        	   if (dataManager.getGameByID(gameID).hasNick(client.getNick())){
		        		   
//		        			dataManager.notifyAll(
//		        					NetworkMessages.game_startedMsg(
//		        							gameID, 
//		        							 dataManager.getGameByID(gameID).getConnectedPlayersString()
//		        							 )
//		        					);
		        		//	dataGuiManager.getGameByIndex(gameID).notifyInit();	
		        			dataGuiManager.startGameByID(gameID,client.getNick());
		        			dataGuiManager.addMsgIn("game "+checker.getMessage().getInt("gameID")+ " started");
		        	   }
	        		  
	        		   else {
	        			   client.sendMsg(NetworkMessages.error_Msg("you cant start this game"));
	        		   }
	        	   }	        	   
	        	   else  if (checker.getType().equals("leave game")){													//CREATE GAME
	        		   dataGuiManager.removePlayerfromGame(client, checker.getMessage().getInt("gameID"));
	        	   }
	        	   else if (checker.getType().equals("disconnect")) {												//DISCONNECT
	        		  closeProtokollConnection();
	        	   }
	        	   
	        	   else if(checker.getType().equals("move")){
	        		   int xKoordinate = checker.getMessage().getJSONObject("field").getInt("x");
	        		   int yKoordinate = checker.getMessage().getJSONObject("field").getInt("y");
	        		   CluedoField field = new CluedoField(new CluedoPosition(xKoordinate, yKoordinate));
	        		   int gameID = checker.getMessage().getInt("gameID");
	        		   // TODO: Position checken dataManager.getGameByID(gameID)
	        		   
	        		   client.sendMsg(NetworkMessages.okMsg());
	        		   JSONObject personpos = new JSONObject();
	        		   personpos.put("person",client.getPlayer().getCluedoPerson()); 
	        		   personpos.put("field", field);
	        		   dataManager.notifyAll(NetworkMessages.movedMsg(gameID, personpos));
	        		   
	        	   }
	        	  
	        	   else if (checker.getType().equals("chat")) {														//CHAT
					   JSONObject chatmsg = checker.getMessage();
					   String msg = checker.getMessage().getString("message");
					   String ts = checker.getMessage().getString("timestamp");
						if (chatmsg.has("gameID")){
							int gameID = chatmsg.getInt("gameID");
							  dataManager.getGameByID(gameID).notifyAll(NetworkMessages.chatMsg(msg,gameID,ts));
						}
						else if (checker.getMessage().has("nick")){
							dataGuiManager.addMsgIn(
								chatmsg.getString("timestamp")+" "+chatmsg.getString("sender")+" says (privately) : \n"+
								chatmsg.getString("message")
							);
							dataManager.getClientByNick(chatmsg.getString("nick")).sendMsg(NetworkMessages.chatMsg(msg,ts));
						}
						else {							  
							dataManager.notifyAll(NetworkMessages.chatMsg(msg , ts));
							dataGuiManager.addMsgIn(
								ts+" "+chatmsg.getString("sender")+" says : \n"+
								msg
							);
						}
		          }
	        	  else  {											
	        		   auxx.loginfo("SERVER INCOMING valid unchecked : \n"+ checker.getMessage());
	        	  } 
	           }
	           else {
	        	   client.sendMsg(NetworkMessages.error_Msg(checker.getErrString()+ " \n "
		        	   		+ "bye "+client.getNick()+" and "+client.getGroupName()+" is a shitty group"));
		        	   client.sendMsg(NetworkMessages.disconnectMsg());
		        	   dataGuiManager.removeClient(client);
		        	   auxx.loginfo("INCOMING INVALID : "+ checker.getErrString());
	           }
			}
			catch (Exception e ){	
				auxx.logsevere("communicationhandler for client : "+ client.getNick()+ "running out", e);
				auxx.logsevere("last message :"+ currentMsg);

				closeProtokollConnection();			
			}
		}
		
	}
	
	void createGame(String color,ClientItem client){
		int gameID = dataGuiManager.createGame(color, client);
		dataManager.notifyAll(
				NetworkMessages.game_createdMsg(
						NetworkMessages.player_info(
								client.getNick(), 
								color,PlayerStates.do_nothing.getName()
								), 
						gameID
						)
				);
	}
	
	private void closeProtokollConnection() {
		 if (dataGuiManager.removeClient(client)){
  		   dataManager.notifyAll(NetworkMessages.user_leftMsg(client.getNick()));
  		   killThread();
		 }			
	}
	
	public void killThread(){
		readyForCommunication = true; // no further listinenig on this socket
		run = false; // thread will run out without further notice					
	}
	
}
