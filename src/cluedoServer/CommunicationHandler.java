package cluedoServer;

import java.io.IOException;

import json.CluedoJSON;
import json.CluedoProtokollChecker;

import org.json.JSONObject;

import staticClasses.Config;
import staticClasses.NetworkMessages;
import staticClasses.aux;
import cluedoNetworkGUI.DataGuiManagerServer;
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
		System.out.println("awaiting");

		while (!readyForCommunication) { // will keep listening for valid login msg
			try {
				String message = aux.getTCPMessage(client.getSocket()).trim();
				
				CluedoProtokollChecker checker = new CluedoProtokollChecker(
						new CluedoJSON(
								new JSONObject(message)));
				NetworkHandhakeCodes errcode = checker.validateExpectedType("login",null);

				if (errcode == NetworkHandhakeCodes.OK) {					
					client.setExpansions(
						aux.makeConjunction(
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
				
				else {
					dataGuiManager.addMsgIn("unhandled incoming : \n" + message);
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	@Override
	public void run(){	
		awaitingLoginAttempt();
		while (run){
			try {
	           String message = aux.getTCPMessage(client.socket).trim();
	           
	           CluedoProtokollChecker checker = new CluedoProtokollChecker(new JSONObject(message));
	           checker.validate();
	           if (!checker.isValid()){
	        	   client.sendMsg(NetworkMessages.error_Msg(checker.getErrString()+ " \n "
	        	   		+ "bye "+client.getNick()+" and "+client.getGroupName()+" is a shitty group"));
	        	   client.sendMsg(NetworkMessages.disconnectMsg());
	        	   dataGuiManager.removeClient(client);
	           }
	           else {
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
	        		   }
	        		   else if (status == JoinGameStatus.already_joined)
	        			   client.sendMsg(NetworkMessages.error_Msg("you have already joined this game"));
	        		   else if (status == JoinGameStatus.nick_already_taken)
	        			   client.sendMsg(NetworkMessages.error_Msg("color is already chosen by someone else"));	        		  
	        	   }
	        	   else if (checker.getType().equals("start game")) {												//START GAME
		        	   int gameID = checker.getMessage().getInt("gameID");
	        		   if (dataGuiManager.startGameByID(gameID,client.getNick())){
		        			dataManager.notifyAll(
		        					NetworkMessages.game_startedMsg(
		        							gameID, 
		        							 dataManager.getGameByID(gameID).getConnectedPlayersString()
		        							 )
		        					);
		        		}
	        		   else {
	        			   client.sendMsg(NetworkMessages.error_Msg("you cant start this game"));
	        		   }
	        	   }
	        	   
	        	   if (checker.getType().equals("leave game")){													//CREATE GAME
	        		   dataGuiManager.removePlayerfromGame(client, checker.getMessage().getInt("gameID"));
	        	   }
	        	   else if (checker.getType().equals("disconnect")) {												//DISCONNECT
	        		  closeProtokollConnection("");
	        	   }
	        	  
	        	   else if (checker.getType().equals("chat")) {														//CHAT
	        		   String msg = checker.getMessage().getString("message");
	        		   String ts = checker.getMessage().getString("timestamp");
	        		   dataManager.notifyAll(NetworkMessages.chat_to_clientMsg(msg , ts, client.getNick()));
		           }	        	   
	           }	
	           //nur damit nichts unter den tisch fällt
		       dataGuiManager.addMsgIn(message);

			}
			catch (Exception e){
				try {
					closeProtokollConnection("closing :"+e.getMessage());
				}
				catch (IOException ex){
					dataGuiManager.addMsgIn(ex.getMessage());
				}				
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
	
	private void closeProtokollConnection(String msg) throws IOException{
		 //client.sendMsg(NetworkMessages.disconnectedMsg("bye " +client.getNick()));
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
