package cluedoClient;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import json.CluedoJSON;
import json.CluedoProtokollChecker;

import org.json.JSONArray;
import org.json.JSONObject;

import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoNetworkGUI.DataGuiManagerClientSpool;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPosition;
import enums.NetworkHandhakeCodes;
import enums.PlayerStates;

class IncomingHandler implements Runnable {

	DataGuiManagerClientSpool dataGuiManager;
	ServerItem server;

	boolean localRun = true;
	boolean globalRun = true;

	IncomingHandler(DataGuiManagerClientSpool dgm, ServerItem server,
			boolean globalRun, boolean localRun) {
		dataGuiManager = dgm;
		this.server = server;
		this.localRun = localRun;
		this.globalRun = globalRun;
		setListener();
	}

	@Override
	public void run() {
		getGamesList();
		while (globalRun && localRun) {
			try {
				ArrayList<String> messages = auxx.getTCPMessages(server.getSocket());
				for (String message: messages)
					if (!message.equals("")) incommingLogic(message);
			}			
			catch (Exception e){
				auxx.logsevere("error on incomming handler client", e);
				killConnection();
				dataGuiManager.refreshGamesListServer(server);// refresh view
																// before
																// running out,
																// its a
																// differnet
																// thread anyway
			}
		}

		killConnection();
	}

	private void incommingLogic(String msg) {
		CluedoProtokollChecker checker = new CluedoProtokollChecker(
				new CluedoJSON(new JSONObject(msg)));
		checker.validate();
		if (checker.isValid()) {
			if (checker.getType().equals("game created")) {
				int gameID = checker.getMessage().getInt("gameID");
				JSONObject playerJSON = checker.getMessage().getJSONObject(
						"player");
				dataGuiManager.addGameToServer(server, gameID,
						playerJSON.getString("nick"),
						playerJSON.getString("color"));
			}

			else
				if (checker.getType().equals("player added")) {
				int gameID = checker.getMessage().getInt("gameID");
				JSONObject player = checker.getMessage()
						.getJSONObject("player");
				dataGuiManager.joinGameOnServer(server, gameID,
						player.getString("color"), player.getString("nick"));

			}
			
				else
				if (checker.getType().equals("game started")) {
				ArrayList<String> orderlist = auxx.jsonArrayToArrayList(checker
						.getMessage().getJSONArray("order"));
				dataGuiManager.startGameOnServer(server, checker.getMessage()
						.getInt("gameID"),
						checker.getMessage().getString("gamestate"), orderlist);

			}
			
			else if (checker.getType().equals("player added")){
				  int gameID = checker.getMessage().getInt("gameID");
        		  JSONObject player = checker.getMessage().getJSONObject("player");
        		  dataGuiManager.joinGameOnServer(server,gameID,player.getString("color"),player.getString("nick"));
        		  
			}
			else if (checker.getType().equals("watcher added")){
      		  dataGuiManager.addWatcherToGame(server,checker.getMessage().getInt("gameID"),checker.getMessage().getString("nick"));      		  
			}
			else if (checker.getType().equals("game started")){
        		  ArrayList<String> orderlist = auxx.jsonArrayToArrayList(checker.getMessage().getJSONArray("order"));
        		  dataGuiManager.startGameOnServer(
        				  server,
        				  checker.getMessage().getInt("gameID"),
        				  checker.getMessage().getString("gamestate"),
        				  orderlist
        				  );
        		  
			}
			else if (checker.getType().equals("player_cards")){
        		 server.getGameByGameID(
        				 checker.getMessage().getInt("gameID")
        				 ).getConnectedPlayerByName(
        						 server.getMyNick()
        						 ).setCards(
        								 auxx.jsonArrayToArrayList(
        										 checker.getMessage().getJSONArray("cards")
        										 )
        							);	        		  
			} 
			else if (checker.getType().equals("suspicion")) {
				JSONObject json = checker.getMessage().getJSONObject(
						"statement");
				int id = checker.getMessage().getInt("gameID");
				String person = json.getString("person").toString();
				String weapon = json.getString("weapon").toString();
				String room = json.getString("room").toString();
				server.getGameByGameID(id)
						.compareCards(person, room, weapon);
				server.getGameByGameID(id).changeLabel(server.getGameByGameID(id).getMyNick()
						+ " is suspecting. " + "\n" + person + " " + room + " " + weapon);
			}
				
			else if (checker.getType().equals("accuse")) {
				JSONObject json = checker.getMessage().getJSONObject(
						"statement");
				String person = json.getString("person").toString();
				String weapon = json.getString("weapon").toString();
				String room = json.getString("room").toString();
				server.getGameByGameID(checker.getMessage().getInt("gameID"))
						.somebodyIsAccusing(
						server.getGameByGameID(checker.getMessage().getInt("gameID")).getMyNick(),
						person, weapon, room
						);
			}
					
			else if(checker.getType().equals("wrong accusation")){
					JSONObject json = checker.getMessage().getJSONObject(
							"statement");
					String person = json.getString("person").toString();
					String weapon = json.getString("weapon").toString();
					String room = json.getString("room").toString();
					server.getGameByGameID(checker.getMessage().getInt("gameID")).
					somebodyFailedToAccuse(person, weapon, room);
			} 
				
			else if (checker.getType().equals("disprove")) {
				System.out.println("NICK INCOMING " + server.getGameByGameID(checker.getMessage().getInt("gameID")).getPlayerByNick(server.myNick));
				String card = checker.getMessage().getString("card").toString();
			server.getGameByGameID(checker.getMessage().getInt("gameID"))
			.changeLabel(
			server.getGameByGameID(checker.getMessage().getInt("gameID")).getPlayerByNick(server.myNick).getCluedoPerson().getPersonName()
			+ " had the card: " + card);
			} 
				
				else
					if(checker.getType().equals("disproved")){
				server.getGameByGameID(checker.getMessage().getInt("gameID")).changeLabel(
						checker.getMessage().getString("nick").toString() + " disproved!"
						);
			} else
				if (checker.getType().equals("no disprove")){
					server.getGameByGameID(checker.getMessage().getInt("gameID")).changeLabel(
							server.getGameByGameID(checker.getMessage().getInt("gameID"))
							.getPlayerByNick(server.myNick).getCluedoPerson().getPersonName() +
							" did not disprove.");
				}
			else if (checker.getType().equals("game ended")){
        		 dataGuiManager.setGameEndedOnServer(server,checker.getMessage().getInt("gameID"));		        		  
			}
			else if (checker.getType().equals("left game")){
//        		 CluedoGameClient game = server.getGameByGameID(checker.getMessage().getInt("gameID"));
//        		 game.removePlayer(checker.getMessage().getString("nick"));	
//        		 dataGuiManager.refreshGamesListServer(server);
        		 dataGuiManager.removePlayerFromGameOnServer(
        				 checker.getMessage().getInt("gameID"),
        				 checker.getMessage().getString("nick"),
        				 server);
			}
			else if (checker.getType().equals("game deleted")){
        		 dataGuiManager.deleteGameOnServer(server,checker.getMessage().getInt("gameID"));		        		  
			}
			else if (checker.getType().equals("user left")){
        		  String player = checker.getMessage().getString("nick");
        		  dataGuiManager.removeClientFromSystemServer(server,player);		        		  
			}
			else if(checker.getType().equals("stateupdate")){
				int gameID = checker.getMessage().getInt("gameID");
				CluedoGameClient game = server.getGameByGameID(gameID); 
				JSONArray states = checker.getMessage().getJSONObject("player").getJSONArray("playerstate");
				JSONObject playerinfo = checker.getMessage().getJSONObject("player");
				String nick = playerinfo.getString("nick");
				for (int welcherState = 0; welcherState < states.length(); welcherState++){
					if (states.get(welcherState).equals(PlayerStates.end_turn.getName())){
						game.itsYourTurn();
					}
					else if (server.getMyNick().equals(nick) &&  states.get(welcherState).equals(PlayerStates.roll_dice.getName())){
						auxx.loginfo("voll ghetto-code");
						game.currentPlayerToRolls();
						game.itsYourTurn();
					}
					else if ( ! (server.getMyNick().equals(nick) ) &&  states.get(welcherState).equals(PlayerStates.roll_dice.getName())){
						auxx.loginfo("voll ghetto-code");
						game.currentPlayerToRolls();
						game.itsSomeonesTurn();
					}
					else if (server.getMyNick().equals(nick) && states.get(welcherState).equals(PlayerStates.accuse.getName())){
						game.currentPlayerToAccuse();
						game.itsSomeonesTurn();
					}
					else if (server.getMyNick().equals(nick) && states.get(welcherState).equals(PlayerStates.suspect.getName())){
						game.currentPlayerToSuspect();
						game.itsSomeonesTurn();
					}
					else if (server.getMyNick().equals(nick) && states.get(welcherState).equals(PlayerStates.disprove.getName())){
						game.currentPlayerToDisprove();
						game.itsSomeonesTurn();
					}
//					else if (states.get(welcherState).equals(PlayerStates.do_nothing.getName())){
//						auxx.loginfo("voll ghetto-code");
//						game.currentPlayerToNothing();
//					}
					else if ( ! (states.get(welcherState).equals(PlayerStates.do_nothing.getName()))){
						auxx.loginfo("nicht so ghetto-code");
//						game.currentPlayerToRolls();
					}
				}
//				else if (checker.getMessage().getJSONObject("player").getJSONArray("playerstate").get(0).equals(PlayerStates.disprove.getName())){
//					server.getGameByGameID(gameID).disprove();
//				}
			}
			else if(checker.getType().equals("dice result")){
				int [] wuerfel = new int [2];
				wuerfel[0] = Integer.parseInt(checker.getMessage().getJSONArray("result").get(0).toString());
				wuerfel[1] = Integer.parseInt(checker.getMessage().getJSONArray("result").get(1).toString());
				server.getGameByGameID(checker.getMessage().getInt("gameID")).rollDice(wuerfel);
			}
			else if (checker.getType().equals("moved")){
				String person = checker.getMessage().getJSONObject("person position").get("person").toString();
				int xKoord = checker.getMessage().getJSONObject("person position").getJSONObject("field").getInt("x");
				int yKoord = checker.getMessage().getJSONObject("person position").getJSONObject("field").getInt("y");
				
				CluedoPosition position = new CluedoPosition(xKoord, yKoord);
				server.getGameByGameID(checker.getMessage().getInt("gameID")).move(position, person);
			}
			else if (checker.getType().equals("chat")){
				  JSONObject chatmsgJSON = checker.getMessage();
				  String chatmsg = chatmsgJSON.getString("message");
				  String ts = auxx.convertTs(chatmsgJSON.getString("timestamp"));
        		  if (chatmsgJSON.has("gameID")){
        			  server.getGameByGameID(chatmsgJSON.getInt("gameID")).addChatMsg(ts+" : "+chatmsg);
        		  }
//        		  if (chatmsgJSON.has("nick")){
//        			  dataGuiManager.addMsgIn(
//        					  ts+" "+chatmsgJSON.getString("sender")+" says (privately) : "+ chatmsg
//        					  );
//        		  }
//    			  else if (chatmsg.has("sender")){
//    				  dataGuiManager.addMsgIn(
//        					  chatmsg.getString("timestamp")+" "+chatmsg.getString("sender")+" says : \n"+
//        							  chatmsg.getString("message")
//        					  );
//    			  }
    			  else {
    				  dataGuiManager.addChatMsgIn(chatmsg, ts,server);    				  
    			  }        		  
			}
			else if (checker.getType().equals("user added")){
				dataGuiManager.addClient(server,checker.getMessage().getString("nick"));
			}
			else if (checker.getType().equals("disconnect")){
        		  killConnection();   
			}
			else if (checker.getType().equals("error")){
        		dataGuiManager.setStatus("ERROR : "+checker.getMessage().getString("message")); 
        		dataGuiManager.setStatusStyle(Color.RED);
			}
			else {
				auxx.loginfo("INCOMING unchecked valid type: "+checker.getType());
			}
		}		
		else {
			auxx.loginfo("INCOMING invalid : "+checker.getErrString());
			server.sendMsg(NetworkMessages.error_Msg(checker.getErrString()));
		}	
	}
	
	private void getGamesList(){
		ArrayList<String> msgs = auxx.getTCPMessages(server.getSocket());
		for (String msg : msgs)
			if (!msg.equals(""))  
				getGamesListLogic(msg);
	}
	
	private void getGamesListLogic(String msg){
		CluedoProtokollChecker checker = new CluedoProtokollChecker(new JSONObject(msg));
		NetworkHandhakeCodes errcode = checker.validateExpectedType("login successful", new String[] {"error"});
		
		if (errcode == NetworkHandhakeCodes.OK) {	
			JSONArray gamearray = checker.getMessage().getJSONArray("game array");	
			ArrayList<CluedoGameClient> gameslist = NetworkMessages.createGamesFromJSONGameArray(gamearray,server);
			
			dataGuiManager.setServerLoggedIn(
					server,
					gameslist,
					server.getGroupName(),
					server.getIpString(),
					"logged in"
					);
			dataGuiManager.setClients(server,auxx.jsonArrayToArrayList(checker.getMessage().getJSONArray("nick array")));
			dataGuiManager.setStatus("server "+server.getGroupName()+" status :"+server.getStatus());
			dataGuiManager.setSelectedServer(server);		
		}
		else if (errcode == NetworkHandhakeCodes.TYPEOK_MSGERR ){
			dataGuiManager.addMsgIn(server.getGroupName()+" sends invalid Messages : \n"+checker.getErrString());		
			killConnection(); // thread will run out without further notice					
		}
		else if (errcode == NetworkHandhakeCodes.TYPEIGNORED){
			//dataGuiManager.addMsgIn(checker.getMessage().getString("message"));
		}
		else {
			//dataGuiManager.addMsgIn(checker.getMessage().toString());
		}
		
		auxx.logfine("Server login response was : " +msg);
	}

	public void setListener() {
	}

	public void killConnection() {
		localRun = false;
		globalRun = false;
		dataGuiManager.setStatus(server.getGroupName()
				+ " ceased to be");
		dataGuiManager.removeServer(server);
		dataGuiManager.setServer();

		auxx.logsevere("incomming handler thread runnning outon server : "
				+ server.getGroupName());
	}
}