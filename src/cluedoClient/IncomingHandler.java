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

class IncomingHandler implements Runnable {

	DataGuiManagerClientSpool dataGuiManager;
	ServerItem server;

	boolean localRun = true;
	boolean globalRun = true;
	boolean winner = false;

	IncomingHandler(DataGuiManagerClientSpool dgm, ServerItem server,
			boolean globalRun, boolean localRun) {
		dataGuiManager = dgm;
		this.server = server;
		this.localRun = localRun;
		this.globalRun = globalRun;
	}

	@Override
	public void run() {
		getGamesList();
		while (globalRun && localRun) {
			try {
				ArrayList<String> messages = auxx.getTCPMessages(server.getSocket());
				if (messages != null){
					for (String message: messages)
						incommingLogic(message);
				}
				else {				
					auxx.logsevere("server hastily decided to leave");
					killConnection();
					dataGuiManager.refreshGamesListServer(server);
				}
			}			
			catch (Exception e){
				auxx.logsevere("error on incomming handler client :"+e.getMessage() ,e);
				killConnection();
				dataGuiManager.refreshGamesListServer(server);// refresh view
																// before
																// running out,
																// its all
																// different
																// threads anyway
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
			else if (checker.getType().equals("player added")) {
				int gameID = checker.getMessage().getInt("gameID");
				JSONObject player = checker.getMessage()
						.getJSONObject("player");
				dataGuiManager.joinGameOnServer(server, gameID,
						player.getString("color"), player.getString("nick"));
			}			
			else if (checker.getType().equals("game started")) {
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
        		 dataGuiManager.setCardsOnGame(checker.getMessage().getInt("gameID"), auxx.jsonArrayToArrayList(
						 checker.getMessage().getJSONArray("cards")),server);        		  		  
			} 
			else if (checker.getType().equals("suspicion")) {
				int gameID = checker.getMessage().getInt("gameID");
				dataGuiManager.handleSuspicion(
						gameID,
						NetworkMessages.makeCluedoStatementFromJSON(checker.getMessage().getJSONObject("statement")),
						server
				);

			}
			else if (checker.getType().equals("accuse")) {
				JSONObject json = checker.getMessage().getJSONObject(
						"statement");
				String person = json.getString("person").toString();
				String weapon = json.getString("weapon").toString();
				String room = json.getString("room").toString();
				server.getGameByGameID(checker.getMessage().getInt("gameID"))
						.somebodyIsAccusing();
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

				int gameID = checker.getMessage().getInt("gameID");
				dataGuiManager.handleDisprove(gameID, server);
			} 
				
			else if(checker.getType().equals("disproved")){
				server.getGameByGameID(checker.getMessage().getInt("gameID")).changeLabel(
					checker.getMessage().getString("nick").toString() + " disproved!"
					);
			} 
			else if (checker.getType().equals("no disprove")){
					dataGuiManager.noDisprove(checker.getMessage().getInt("gameID"),server);
			}

			else if (checker.getType().equals("game ended")){
        		 dataGuiManager.setGameEndedOnServer(server,checker.getMessage().getInt("gameID"));
        		 if(checker.getMessage().getString("nick").equals(server.getMyNick())){
						dataGuiManager.youWin();
						winner = true;
					} else
					if(!winner){
					dataGuiManager.youLose();
					}
			}
			else if (checker.getType().equals("left game")){
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
				JSONObject playerinfo = checker.getMessage().getJSONObject("player");
				dataGuiManager.handleStateUpdate(
						checker.getMessage().getInt("gameID"),
						playerinfo.getString("nick"),
						server,
						auxx.jsonArrayToArrayList(playerinfo.getJSONArray("playerstate"))
						);				
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
    			  else {
    				  dataGuiManager.addChatMsgIn(chatmsg, ts,server);    				  
    			  }        		  
			}
			else if (checker.getType().equals("user added")){
				dataGuiManager.addClient(server,checker.getMessage().getString("nick"));
			}
			else if (checker.getType().equals("gameinfo")){
				CluedoGameClient newgame = NetworkMessages.createGameFromJSONGameInfo(checker.getMessage().getJSONObject("game"), server);			
				if (dataGuiManager.deleteGameOnServer(server, newgame.getGameId()))					
					dataGuiManager.addRunningGameToServer(server, newgame);
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
			//server.sendMsg(NetworkMessages.error_Msg(checker.getErrString()));
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
		NetworkHandhakeCodes errcode = checker.validateExpectedType("login successful", new String[] {"error,ok"});
		
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
	
	public void killConnection() {
		localRun = false;
		globalRun = false;
		dataGuiManager.setStatus("Server "+server.getGroupName()+ " ceased to be, is no more, passed the bucket and so on");
		dataGuiManager.removeServer(server);
		dataGuiManager.setServer();

		auxx.logsevere("incomming handler thread runnning out on server : " + server.getGroupName());
	}
}