package cluedoClient;


import java.util.ArrayList;

import json.CluedoJSON;
import json.CluedoProtokollChecker;

import org.json.JSONArray;
import org.json.JSONObject;

import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoNetworkGUI.DataGuiManagerClientSpool;
import cluedoNetworkLayer.CluedoField;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPosition;
import enums.NetworkHandhakeCodes;


class IncomingHandler implements Runnable {
	
	DataGuiManagerClientSpool dataGuiManager;
	ServerItem server;
	
	boolean localRun = true;
	boolean globalRun = true;
	
	
	IncomingHandler(DataGuiManagerClientSpool dgm,ServerItem server,boolean globalRun,boolean localRun){
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
				String msg = auxx.getTCPMessage(server.getSocket());
				CluedoProtokollChecker checker = new CluedoProtokollChecker(
						new CluedoJSON(new JSONObject(msg)));
				checker.validate();
				if (checker.isValid()){				
					if (checker.getType().equals("game created")){
						int gameID = checker.getMessage().getInt("gameID");
						JSONObject playerJSON = checker.getMessage().getJSONObject("player");
						dataGuiManager.addGameToServer(server,gameID, playerJSON.getString("nick"),playerJSON.getString("color"));
					}
					
					else if (checker.getType().equals("player added")){
						  int gameID = checker.getMessage().getInt("gameID");
		        		  JSONObject player = checker.getMessage().getJSONObject("player");
		        		  dataGuiManager.joinGameOnServer(server,gameID,player.getString("color"),player.getString("nick"));
		        		  
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
		        				 checker.getMessage().getInt("gameId")
		        				 ).getConnectedPlayerByName(
		        						 server.getMyNick()
		        						 ).setCards(
		        								 auxx.jsonArrayToArrayList(
		        										 checker.getMessage().getJSONArray("cards")
		        										 )
		        							);	        		  
					}
					else if (checker.getType().equals("game ended")){
		        		 dataGuiManager.setGameEndedOnServer(server,checker.getMessage().getInt("gameID"));		        		  
					}
					else if (checker.getType().equals("left game")){
//		        		 CluedoGameClient game = server.getGameByGameID(checker.getMessage().getInt("gameID"));
//		        		 game.removePlayer(checker.getMessage().getString("nick"));	
//		        		 dataGuiManager.refreshGamesListServer(server);
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
					else if (checker.getType().equals("moved")){
						int xKoord = checker.getMessage().getJSONObject("person position").getJSONObject("field").getInt("x");
						int yKoord = checker.getMessage().getJSONObject("person position").getJSONObject("field").getInt("y");
						
						CluedoPosition position = new CluedoPosition(xKoord, yKoord);
						server.getGameByGameID(checker.getMessage().getInt("gameID")).move(position);
					}
					else if (checker.getType().equals("chat")){
						  JSONObject chatmsg = checker.getMessage();
		        		  if (chatmsg.has("gameID")){
		        			  server.getGameByGameID(
		        					  	chatmsg.getInt("gameID")
		        					  ).addChatMsg(
		        							  auxx.convertTs(chatmsg.getString("timestamp"))+" : "+chatmsg.getString("message")
		        						);
		        		  }
		        		  if (checker.getMessage().has("nick")){
		        			  dataGuiManager.addMsgIn(
		        					  auxx.convertTs(chatmsg.getString("timestamp"))+" "+chatmsg.getString("sender")+" says (privately) : \n"+
		        							  chatmsg.getString("message")
		        					  );
		        		  }
//	        			  else if (chatmsg.has("sender")){
//	        				  dataGuiManager.addMsgIn(
//		        					  chatmsg.getString("timestamp")+" "+chatmsg.getString("sender")+" says : \n"+
//		        							  chatmsg.getString("message")
//		        					  );
//	        			  }
	        			  else {
	        				  dataGuiManager.addMsgIn(
	        						  auxx.convertTs(chatmsg.getString("timestamp"))+" : \n"+
		        							  chatmsg.getString("message")
		        					  );
	        			  }
					}
					else if (checker.getType().equals("disconnect")){
		        		  killConnection();   
					}
					else if (checker.getType().equals("error")){
		        		  dataGuiManager.setStatus("ERROR : "+checker.getMessage().getString("message")); 
					}
					else {
						auxx.loginfo("INCOMING unchecked valid type: "+checker.getType());
					}
				}		
				else {
					auxx.loginfo("INCOMING invalid : "+checker.getErrString());
				}				
			}			
			catch (Exception e){
				auxx.logsevere("error on incomming handler client", e);
				killConnection();
				dataGuiManager.refreshGamesListServer(server);// refresh view before running out, its a differnet thread anyway
			}			
		}
		
		killConnection();	
	}
	
	private void getGamesList(){
		String msg = auxx.getTCPMessage(server.getSocket());
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
			dataGuiManager.setStatus("server "+server.getGroupName()+" status :"+server.getStatus());
					
		}
		else if (errcode == NetworkHandhakeCodes.TYPEOK_MESERR 
				|| errcode == NetworkHandhakeCodes.TYPERR){
			dataGuiManager.addMsgIn(server.getGroupName()+" sends invalid Messages : \n"+checker.getErrString());		
			killConnection(); // thread will run out without further notice					
		}
		else if (errcode == NetworkHandhakeCodes.TYPEIGNORED){
			//dataGuiManager.addMsgIn(checker.getMessage().getString("message"));
		}
		else {
			//dataGuiManager.addMsgIn(checker.getMessage().toString());
		}
		
	}
	
	
	
	public void setListener(){}
	
	
	
	public void killConnection(){
		localRun = false;
		globalRun = false;
		dataGuiManager.setStatus(server.getGroupName()+" said FUCKOFF (kindof)");
		dataGuiManager.removeServer(server);
		dataGuiManager.setServer();
		
		
		auxx.logsevere("incomming handler thread runnning outon server : "+server.getGroupName());
	}
}