package cluedoClient;


import java.util.ArrayList;

import json.CluedoJSON;
import json.CluedoProtokollChecker;

import org.json.JSONArray;
import org.json.JSONObject;

import staticClasses.Methods;
import staticClasses.NetworkMessages;
import cluedoNetworkGUI.CluedoClientGUI;
import cluedoNetworkGUI.DataGuiManagerClient;
import cluedoNetworkLayer.CluedoGameClient;
import enums.NetworkHandhakeCodes;


class IncomingHandler implements Runnable {
	DataGuiManagerClient dataGuiManager;
	
	boolean run = true;
	
	IncomingHandler(CluedoClientGUI gui,ServerItem server,boolean run){
		dataGuiManager = new DataGuiManagerClient(gui,server);
		this.run = run;
		setListener();
	}
	
	@Override
	public void run() {	
		getGamesList();
		while (run) {
			try {
				String msg = Methods.getTCPMessage(dataGuiManager.getServer().getSocket());
				CluedoProtokollChecker checker = new CluedoProtokollChecker(
						new CluedoJSON(new JSONObject(msg)));
				checker.validate();
				if (checker.isValid()){				
					dataGuiManager.addMsgIn(checker.getMessage().toString());
					if (checker.getType().equals("game created")){
						int gameID = checker.getMessage().getInt("gameID");
						JSONObject playerJSON = checker.getMessage().getJSONObject("player");
						dataGuiManager.addGame(gameID, playerJSON.getString("nick"),playerJSON.getString("color"));
					}
					
					else if (checker.getType().equals("player added")){
						  int gameID = checker.getMessage().getInt("gameID");
		        		  JSONObject player = checker.getMessage().getJSONObject("player");
		        		  dataGuiManager.joinGame(gameID,player.getString("color"),player.getString("nick"));      		   
					}
				}		
				else {
					dataGuiManager.addMsgIn(checker.getErrString());
				}
			}			
			catch (Exception e){
				dataGuiManager.setStatus("Server "+dataGuiManager.getServer().getGroupName()+" hat sich unhöflich verabschiedet\n "+e.getMessage());	
				dataGuiManager.refreshGamesList();
				kill();
			}			
		}
		System.out.println("running out client connected to"+dataGuiManager.getServer().getGroupName()+"incoming thread running out");
		kill();	
	}
	
	private void getGamesList(){
		String msg = Methods.getTCPMessage(dataGuiManager.getServer().getSocket());
		CluedoProtokollChecker checker = new CluedoProtokollChecker(new JSONObject(msg));
		NetworkHandhakeCodes errcode = checker.validateExpectedType("login successful", new String[] {"error"});
		
		if (errcode == NetworkHandhakeCodes.OK) {	
			JSONArray gamearray = checker.getMessage().getJSONArray("game array");	
			ArrayList<CluedoGameClient> gameslist = NetworkMessages.createGamesFromJSONGameArray(gamearray);
			
			dataGuiManager.setServerLoggedIn(
					gameslist,
					dataGuiManager.getServer().getGroupName(),
					dataGuiManager.getServer().getIpString(),
					"logged in"
					);
					
			
		}
		else if (errcode == NetworkHandhakeCodes.TYPEOK_MESERR 
				|| errcode == NetworkHandhakeCodes.TYPERR){
			dataGuiManager.addMsgIn(dataGuiManager.getServer().getGroupName()+" sends invalid Messages : \n"+checker.getErrString());		
			kill(); // thread will run out without further notice					
		}
		else if (errcode == NetworkHandhakeCodes.TYPEIGNORED){
			dataGuiManager.addMsgIn(checker.getMessage().getString("message"));
		}
		
	}
	
	
	
	public void setListener(){}
	
	
	
	public void kill(){
		run = false;
		dataGuiManager.kill();	
	}
}