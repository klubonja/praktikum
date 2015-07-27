package cluedoServer;

import java.util.ArrayList;
import java.util.Arrays;

import json.CluedoJSON;
import json.CluedoProtokollChecker;

import org.json.JSONObject;

import staticClasses.Config;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoNetworkGUI.DataGuiManagerServer;
import cluedoNetworkLayer.CluedoPosition;
import cluedoNetworkLayer.CluedoStatement;
import enums.NetworkHandhakeCodes;
import enums.PlayerStates;

/**
 * @author guldener verteilt die nachrichten der des clients
 */
class CommunicationHandler implements Runnable {

	ClientItem client;
	Connector networkService;

	DataManagerServer dataManager;
	DataGuiManagerServer dataGuiManager;

	boolean runThread = false;
	boolean readyForCommunication = false;
	String currentMsg;
	
	/**
	 * @param ss
	 * @param c
	 * @param g
	 * @param cList
	 * @param bList
	 * 
	 *            tcp verbindung steht server wartet auf tcp handshake
	 */
	CommunicationHandler(ClientItem c, DataManagerServer dm, DataGuiManagerServer dgm) {
		dataManager = dm;
		dataGuiManager = dgm;
		client = c;
	}

	private void awaitingLoginAttempt() {
		auxx.logfine("awaiting login from client");
		while (!readyForCommunication) { // will keep listening for valid login msg
			try {
				ArrayList<String> messages = auxx.getTCPMessages(client.getSocket());
				for (String message : messages){
					if (!message.equals(""))
						loginAttemptLogic(message);				
				}					
			} 
			catch (Exception e) {
				auxx.logsevere(
						"error client login attempt : " + client.getNick(), e);
			}
		}
	}

	@Override
	public void run() {
		awaitingLoginAttempt();
		while (runThread) {
			try {
				ArrayList<String> messages = auxx.getTCPMessages(client.socket);
				try {					
					currentMsg = messages.get(0);				
				}
				catch (Exception e){}
				
				for (String message : messages)
					if (!message.equals(""))
						serverLogic(message);			
				
			} 
			catch (Exception e) {
				auxx.logsevere(
						"communicationhandler for client : " + client.getNick()	+ " running out because : "+e.getMessage(),e);
				auxx.logsevere("last message :" + currentMsg);
				closeProtokollConnection();
			}
		}
	}

	void loginAttemptLogic(String message) {
		CluedoProtokollChecker checker = new CluedoProtokollChecker(
				new CluedoJSON(new JSONObject(message)));
		NetworkHandhakeCodes errcode = checker.validateExpectedType("login",
				null);

		if (errcode == NetworkHandhakeCodes.OK) {
			client.setExpansions(auxx.makeConjunction(Config.EXPANSIONS,
					checker.getMessage().getJSONArray("expansions")));
			client.setNick(checker.getMessage().getString("nick"));
			client.setGroupName(checker.getMessage().getString("group"));					
			if (dataGuiManager.addNetworkActor(client,"logged in")){
				client.setGroupName(checker.getMessage().getString("group"));
				client.sendMsg(NetworkMessages.login_sucMsg(client.getExpansions(),
								dataManager.getClientPool(), dataManager.getGameList()));
				dataManager.sendMsgToAllClients(NetworkMessages.user_addedMsg(client.getNick()));
				readyForCommunication = true;
				runThread = true;
			}
			else {
				client.sendMsg(NetworkMessages.error_Msg(client.getNick()
						+ " already exists, try again with different nick"));
			}
			
		} 
		else if (errcode == NetworkHandhakeCodes.TYPEERR_MSGOK){
			
		}
	    else if (errcode == NetworkHandhakeCodes.TYPEOK_MSGERR) {

			dataGuiManager.addMsgIn(client.getAdress()
					+ " sends invalid Messages : \n" + checker.getErrString());
			client.sendMsg(NetworkMessages
					.error_Msg("you are violating the protokoll due to the following: \n"
							+ checker.getErrString()));
			client.sendMsg(NetworkMessages.disconnectMsg());
			client.closingConnection(dataManager.getGroupName()
					+ " is closing connection");
			dataManager.blacklist(client);
			killThread();
		}

		auxx.loginfo("login attempt :" + checker.getMessage().toString());
	}

	void createGame(String color, ClientItem client) {
		int gameID = dataGuiManager.createGame(color, client);
		dataManager.sendMsgToAllClients(
				NetworkMessages.game_createdMsg(
						NetworkMessages.player_info(
							client.getNick(), 
							color,
							new ArrayList<String>(
								    Arrays.asList(PlayerStates.do_nothing.getName()
								    	)
							)
						), 
						gameID)
					);
	}

	private void closeProtokollConnection() {
		if (dataGuiManager.removeClient(client)) {
			dataManager.sendMsgToAllClients(NetworkMessages.user_leftMsg(client.getNick()));			
		}
		killThread();
	}

	public void killThread() {
		readyForCommunication = true; // no further listinenig on this socket
		runThread = false; // thread will run out without further notice
	}

	private void serverLogic(String message){
		CluedoProtokollChecker checker = new CluedoProtokollChecker(new JSONObject(message));
        checker.validate();
        if (checker.isValid()){
     	   if (checker.getType().equals("create game")){													//CREATE GAME
     		   createGame(checker.getMessage().getString("color"),client);
     	   }
     	   else if (checker.getType().equals("join game")){
     		   int gameID = checker.getMessage().getInt("gameID");
     		   String color = checker.getMessage().getString("color");
     		   dataGuiManager.joinGame(gameID, color, client);
     	   }
	 	   else if (checker.getType().equals("start game")) {												//START GAME
        	   int gameID = checker.getMessage().getInt("gameID");
        	   dataGuiManager.startGameByID(gameID, client);
	 	   }	        	   
	 	   else  if (checker.getType().equals("leave game")){													//LEAVE GAME
	 		   dataGuiManager.removeClientfromGame(client, checker.getMessage().getInt("gameID"));
	 	   }
	 	   else  if (checker.getType().equals("watch game")){													//WATCH GAME
			   dataGuiManager.addWatcherToGame(checker.getMessage().getInt("gameID"),client);
		   }
	 	   else if (checker.getType().equals("disconnect")) {												//DISCONNECT
	 		  closeProtokollConnection();
	 	   } 	   
	 	   else if(checker.getType().equals("roll dice")){													//ROLL DICE
			   int gameID = checker.getMessage().getInt("gameID");
			   dataManager.rollDiceRequest(gameID,client);
	   	   }
	   	   else if(checker.getType().equals("move")){															//MOVE
	   		   int xKoordinate = checker.getMessage().getJSONObject("field").getInt("x");
	   		   int yKoordinate = checker.getMessage().getJSONObject("field").getInt("y");
	   		   CluedoPosition newpos = new CluedoPosition(xKoordinate, yKoordinate);
	   		   int gameID = checker.getMessage().getInt("gameID");
	   		   dataManager.moveRequest(gameID,client,newpos);   		 
	   	   }
	   	   else if(checker.getType().equals("secret passage")){
	   		   int gameID = checker.getMessage().getInt("gameID");
	   		   dataManager.secretPassageRequest(gameID, client);
	   	   }
	     	   
	   	   	else if (checker.getType().equals("accuse")) {
		   	   	int gameID = checker.getMessage().getInt("gameID");
				dataManager.accuseRequest(gameID, NetworkMessages.makeCluedoStatementFromJSON(
								checker.getMessage().getJSONObject("statement")),client);
				JSONObject statement = checker.getMessage().getJSONObject("statement");
				String person = statement.getString("person");
				String weapon = statement.getString("weapon");
				String room = statement.getString("room");
				
				if(person.equals(dataManager.getGameByID(gameID).getWinningStatement().getPerson())&&
					weapon.equals(dataManager.getGameByID(gameID).getWinningStatement().getWeapon())&&
					room.equals(dataManager.getGameByID(gameID).getWinningStatement().getRoom())){
					dataManager.sendMsgToAllClients(NetworkMessages.game_endedMsg(gameID, client.getNick(), statement));
				}
			} 
	   	   	else if (checker.getType().equals("suspect")) {
					int gameID = checker.getMessage().getInt("gameID");	
					dataManager.suspectRequest(
							gameID, 
							NetworkMessages.makeCluedoStatementFromJSON(
									checker.getMessage().getJSONObject("statement")
							), 
							client
					);
			} 
			else if (checker.getType().equals("disprove")) {
				int gameID = checker.getMessage().getInt("gameID");
				String card = "";
				if (checker.getMessage().has("card")) 
					card = checker.getMessage().getString("card");
					
				dataManager.disproveRequest(gameID,card,client);					
				
//				else {
//					card = "";
//					dataManager.getGameByID(gameID).sendMsgsToAll(NetworkMessages.no_disproveMsg(gameID));
//				}
				
					
			} 	   	   

	   	   else if(checker.getType().equals("end turn")){
	   		   int gameID = checker.getMessage().getInt("gameID");
		   	   dataManager.endTurnRequest(gameID,client);
	   	   }
	 	   else if (checker.getType().equals("chat")) {														//CHAT
			   JSONObject chatmsg = checker.getMessage();
			   String msg = checker.getMessage().getString("message");
			   String ts = checker.getMessage().getString("timestamp");
				if (chatmsg.has("gameID")){
					int gameID = chatmsg.getInt("gameID");
					  dataManager.getGameByID(gameID).sendMsgsToAll(NetworkMessages.chatMsg(msg,gameID,ts));
				}
				else if (checker.getMessage().has("nick")){
					dataGuiManager.addMsgIn(
						chatmsg.getString("timestamp")+" "+chatmsg.getString("sender")+" says (privately) : \n"+
						chatmsg.getString("message")
					);
					dataManager.getClientByNick(chatmsg.getString("nick")).sendMsg(NetworkMessages.chatMsg(msg,ts));
				}
				else {							  
					dataManager.sendMsgToAllClients(NetworkMessages.chatMsg(msg , ts));
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
     	   client.sendMsg(NetworkMessages.error_Msg(checker.getErrString()+ " "
	        	   		+ "bye "+client.getNick()+" and "+client.getGroupName()+" is a shitty group"));
//	        	   client.sendMsg(NetworkMessages.disconnectMsg());
//	        	   dataGuiManager.removeClient(client);
	        	   auxx.loginfo("INCOMING INVALID : "+ checker.getErrString());
        }
	}
}
