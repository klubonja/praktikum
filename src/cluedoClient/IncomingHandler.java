package cluedoClient;


import java.util.ArrayList;

import json.CluedoJSON;
import json.CluedoProtokollChecker;

import org.json.JSONArray;
import org.json.JSONObject;

import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoNetworkGUI.DataGuiManagerClientSpool;
import cluedoNetworkLayer.CluedoGameClient;
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
					dataGuiManager.addMsgIn(checker.getMessage().toString());
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
					else if (checker.getType().equals("game ended")){
		        		 dataGuiManager.setGameEndedOnServer(server,checker.getMessage().getInt("gameID"));		        		  
					}
					else if (checker.getType().equals("game deleted")){
		        		 dataGuiManager.deleteGameOnServer(server,checker.getMessage().getInt("gameID"));		        		  
					}
					else if (checker.getType().equals("user left")){
		        		  String player = checker.getMessage().getString("nick");
		        		  dataGuiManager.removeClientFromSystemServer(server,player);		        		  
					}
					else if (checker.getType().equals("disconnect")){
		        		  killConnection();   
					}
					else {
						auxx.loginfo("INCOMING unchecked type: "+checker.getType());
					}
					
					auxx.loginfo("INCOMING : handeled type : "+checker.getType());
				}		
				else {
					auxx.loginfo("INCOMING invalid : "+checker.getErrString());
				}
				
				auxx.loginfo("INCOMING anyway:  "+checker.getMessage().toString());
				
			}			
			catch (Exception e){
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
					
		}
		else if (errcode == NetworkHandhakeCodes.TYPEOK_MESERR 
				|| errcode == NetworkHandhakeCodes.TYPERR){
			dataGuiManager.addMsgIn(server.getGroupName()+" sends invalid Messages : \n"+checker.getErrString());		
			killConnection(); // thread will run out without further notice					
		}
		else if (errcode == NetworkHandhakeCodes.TYPEIGNORED){
			dataGuiManager.addMsgIn(checker.getMessage().getString("message"));
		}
		else {
			dataGuiManager.addMsgIn(checker.getMessage().toString());
		}
		
	}
	
	
	
	public void setListener(){}
	
	
	
	public void killConnection(){
		localRun = false;
		auxx.logsevere("incomming handler thread runnning outon server : "+server.getGroupName());
	}
}