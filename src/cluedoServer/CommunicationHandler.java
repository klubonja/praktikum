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
import cluedoNetworkLayer.CluedoField;
import cluedoNetworkLayer.CluedoPosition;
import enums.JoinGameStatus;
import enums.NetworkHandhakeCodes;
import enums.Persons;
import enums.PlayerStates;

/**
 * @author guldener verteilt die nachrichten der des clients
 */
class CommunicationHandler implements Runnable {

	ClientItem client;
	Connector networkService;

	DataManagerServer dataManager;
	DataGuiManagerServer dataGuiManager;

	boolean run = true;
	boolean readyForCommunication = false;
	String currentMsg;
	String suspector;

	/**
	 * @param ss
	 * @param c
	 * @param g
	 * @param cList
	 * @param bList
	 * 
	 *            tcp verbindung steht server wartet auf tcp handshake
	 */
	CommunicationHandler(ClientItem c, DataManagerServer dm,
			DataGuiManagerServer dgm) {
		dataManager = dm;
		dataGuiManager = dgm;
		client = c;
	}

	private void awaitingLoginAttempt() {
		auxx.logfine("awaiting login from client");
		while (!readyForCommunication) { // will keep listening for valid login
											// msg
			try {
				String[] messages = auxx.getTCPMessages(client.getSocket());
				for (String message : messages)
					if (!message.equals(""))
						loginAttemptLogic(message);
			} catch (Exception e) {
				auxx.logsevere(
						"communcitationhandler of client" + client.getNick(), e);
			}
		}
	}

	@Override
	public void run() {
		awaitingLoginAttempt();
		while (run) {
			try {

				String[] messages = auxx.getTCPMessages(client.socket);
				currentMsg = messages[0];
				for (String message : messages)
					if (!message.equals(""))
						serverLogic(message);
			} catch (Exception e) {
				auxx.logsevere(
						"communicationhandler for client : " + client.getNick()
								+ "running out", e);
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
			client.sendMsg(NetworkMessages.login_sucMsg(
					client.getExpansions(),
					dataManager.getClientPool(), 
					dataManager.getGameList()
					)
			);
			if (dataGuiManager.addNetworkActor(client,"logged in")){
				dataManager.notifyAll(NetworkMessages.user_addedMsg(client.getNick()));
				client.setGroupName(checker.getMessage().getString("group"));
				client.sendMsg(NetworkMessages.login_sucMsg(client.getExpansions(),
								dataManager.getClientPool(), dataManager.getGameList()));

//			if (dataGuiManager.addNetworkActor(client, "logged in"))
//				dataManager.notifyAll(NetworkMessages.user_addedMsg(client
//						.getNick()));
//>>>>>>> d62cee416b07023f92e8b8a0ea083aa68d1efac5
			}
			else {
				client.sendMsg(NetworkMessages.error_Msg(client.getNick()
						+ " already exists, try again with different nick"));
				readyForCommunication = false;
			}
			readyForCommunication = true;
		} 
	    else if (errcode == NetworkHandhakeCodes.TYPEOK_MESERR
				|| errcode == NetworkHandhakeCodes.TYPERR) {

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
		dataManager.notifyAll(
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
			dataManager
					.notifyAll(NetworkMessages.user_leftMsg(client.getNick()));
			killThread();
		}
	}

	public void killThread() {
		readyForCommunication = true; // no further listinenig on this socket
		run = false; // thread will run out without further notice
	}

	private void serverLogic(String message){
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
	        								   new ArrayList<String>(
	        										    Arrays.asList(PlayerStates.do_nothing.getName()
	        										    	)
	        									)
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
	        		   
//	        			dataManager.notifyAll(
//	        					NetworkMessages.game_startedMsg(
//	        							gameID, 
//	        							 dataManager.getGameByID(gameID).getConnectedPlayersString()
//	        							 )
//	        					);
	        		//	dataGuiManager.getGameByIndex(gameID).notifyInit();	
	        			dataGuiManager.startGameByID(gameID,client.getNick());
	        			dataGuiManager.addMsgIn("game "+checker.getMessage().getInt("gameID")+ " started");
	        			boolean esGibtRot = false;
	        			for(ClientItem clientTemp : dataManager.clientPool){
	        				if(clientTemp.getPlayer().getCluedoPerson().getColor() == Persons.red.getColor()){
	        					esGibtRot = true;
	        				}
	        			}
	        			
	        			
	        			if (esGibtRot){
		        			for(ClientItem clientTemp : dataManager.clientPool){
		        				String  state = PlayerStates.do_nothing.getName();
		        					if(clientTemp.getPlayer().getCluedoPerson().getColor() == Persons.red.getColor()){
			        					state = PlayerStates.roll_dice.getName();
			        				}
		        				clientTemp.sendMsg(NetworkMessages.stateupdateMsg(gameID, NetworkMessages.player_info(clientTemp.getNick(), clientTemp.getPlayer().getCluedoPerson().getColor(), new ArrayList<String>(
									    Arrays.asList(state
										    	)
									))));
		        			}
		        		}
	        			else {
	        				int anfangsSpielerZufall = auxx.getRandInt(1, dataManager.clientPool.size()-1);
	        				for(int welcherSpieler = 0; welcherSpieler < dataManager.clientPool.size(); welcherSpieler++){
		        				String  state = PlayerStates.do_nothing.getName();
		        				if (welcherSpieler == anfangsSpielerZufall){
		        					state = PlayerStates.roll_dice.getName();		        				
		        				}
		        				dataManager.getClientPool().get(welcherSpieler).sendMsg(NetworkMessages.stateupdateMsg(gameID, NetworkMessages.player_info(dataManager.getClientPool().get(welcherSpieler).getNick(), dataManager.getClientPool().get(welcherSpieler).getPlayer().getCluedoPerson().getColor(), new ArrayList<String>(
									    Arrays.asList(state
										    	)
									))));	
		        				
	        				}
	        			}
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
     	   
     	  else if(checker.getType().equals("roll dice")){
   		   int gameID = checker.getMessage().getInt("gameID");
   		   dataManager.notifyAll(NetworkMessages.dice_resultMsg(gameID, dataManager.getGameByID(gameID).rollTheDice()));
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
     	   
   	   	else if (checker.getType().equals("accuse")) {
			int id = checker.getMessage().getInt("gameID");
			JSONObject json = checker.getMessage().getJSONObject(
					"statement");
			String person = json.getString("person");
			String room = json.getString("room");
			String weapon = json.getString("weapon");
			dataManager.notifyAll(NetworkMessages.accuseMsg(
					id, NetworkMessages.statement(person, room, weapon))
					);
			if(person.equals(dataManager.getGameByID(id).getWinningStatement().getPerson()) &&
				weapon.equals(dataManager.getGameByID(id).getWinningStatement().getWeapon()) &&
				room.equals(dataManager.getGameByID(id).getWinningStatement().getRoom())){
				//KICK PLAYER OUT OF THE GAME
			} else {
				dataManager.notifyAll(NetworkMessages.
				wrong_accusationMsg(id, NetworkMessages.
				statement(person, room, weapon)));
			}
		} else
			if (checker.getType().equals("suspicion")) {
			int id = checker.getMessage().getInt("gameID");
			JSONObject json = checker.getMessage().getJSONObject("statement");
			String person = json.getString("person").toString();
			String room = json.getString("room").toString();
			String weapon = json.getString("weapon").toString();
			dataManager.notifyAll(NetworkMessages.suspicionMsg(
					id, NetworkMessages.statement(person, room, weapon))
					);
		} else
			if (checker.getType().equals("disprove")) {
				String pool = "pool";
				int id = checker.getMessage().getInt("gameID");
				String card = checker.getMessage().getString("card");
				dataManager.notifyAll(NetworkMessages.disprovedMsg(id, client.getNick(), pool));
				client.sendMsg(NetworkMessages.disproveMsg(client.getGameId(), card));
				
		} else 
			if (checker.getType().equals("no disprove")) {
			int id = checker.getMessage().getInt("gameID");
			dataManager.notifyAll(NetworkMessages.no_disproveMsg(id));
			
		}
   	   
   	   else if(checker.getType().equals("end turn")){
   		   auxx.loginfo("end turn angekommen");
   		   int gameID = checker.getMessage().getInt("gameID");
   		   ClientItem currentPlayer = dataManager.gamesList.getGameByID(gameID).getParticipants().get(dataManager.gamesList.getGameByID(gameID).getCurrentPlayer()); 
   		   CluedoJSON playerinfo = new CluedoJSON("player");
   		   playerinfo.put("nick", currentPlayer.getNick());
   		   playerinfo.put("color", currentPlayer.getPlayer().getCluedoPerson().getColor());
   		   playerinfo.put("playerstate", PlayerStates.do_nothing.getName());
   		   currentPlayer.sendMsg(NetworkMessages.stateupdateMsg(gameID, playerinfo));
   		   dataManager.gamesList.getGameByID(gameID).setCurrentPlayerNext();
   		   currentPlayer = dataManager.gamesList.getGameByID(gameID).getParticipants().get(dataManager.gamesList.getGameByID(gameID).getCurrentPlayer()); 
		   playerinfo = new CluedoJSON("player");
		   playerinfo.put("nick", currentPlayer.getNick());
		   playerinfo.put("color", currentPlayer.getPlayer().getCluedoPerson().getColor());
		   playerinfo.put("playerstate", PlayerStates.roll_dice.getName());
		   currentPlayer.sendMsg(NetworkMessages.stateupdateMsg(gameID, playerinfo));
   		   //setNextRound();
   		   //setCurrentPlayerNext();
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
}
