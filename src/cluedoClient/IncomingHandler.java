package cluedoClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import json.CluedoJSON;
import json.CluedoProtokollChecker;

import org.json.JSONArray;
import org.json.JSONObject;

import staticClasses.Config;
import staticClasses.NetworkMessages;
import cluedoNetworkGUI.CluedoClientGUI;
import cluedoNetworkGUI.DataGuiManagerClient;
import cluedoNetworkLayer.CluedoGameClient;
import enums.NetworkHandhakeCodes;


class IncomingHandler implements Runnable {
	
	Socket cSocket;
	DataGuiManagerClient dataGuiManager;
	ServerItem server;
	
	boolean run = true;
	
	IncomingHandler(CluedoClientGUI gui,ServerItem server,boolean run){
		dataGuiManager = new DataGuiManagerClient(gui,server);
		this.run = run;
		this.server = server;
		setListener();
	}
	
	@Override
	public void run() {	
		getGamesList();
		while (run) {
			try {
				String msg = getMessageFromServer(server.getSocket());
				CluedoProtokollChecker checker = new CluedoProtokollChecker(
						new CluedoJSON(new JSONObject(msg)));
				checker.validate();
				if (checker.isValid()){				
					dataGuiManager.addMsgIn(checker.getMessage().toString());
					if (checker.getType().equals("game created")){
						int gameID = checker.getMessage().getInt("gameID");
						JSONObject playerJSON = checker.getMessage().getJSONObject("player");
						dataGuiManager.addGame(gameID, playerJSON.getString("nick"),playerJSON.getString("color"),server);
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
				System.out.println("running out "+e.getMessage());
				dataGuiManager.setStatus("Server hat sich unhöflich verabschiedet");		
				kill();
			}			
		}
		kill();
		
		System.out.println("serverlistener thread running out");		
	}
	
	private void getGamesList(){
		String msg = getMessageFromServer(server.getSocket());
		CluedoProtokollChecker checker = new CluedoProtokollChecker(new JSONObject(msg));
		NetworkHandhakeCodes errcode = checker.validateExpectedType("login successful", null);
		
		if (errcode == NetworkHandhakeCodes.OK) {	
			JSONArray gamearray = checker.getMessage().getJSONArray("game array");	
			ArrayList<CluedoGameClient> gameslist = NetworkMessages.createGamesFromJSONGameArray(gamearray);
			dataGuiManager.setGames(gameslist);			
		}
		else if (errcode == NetworkHandhakeCodes.TYPEOK_MESERR 
				|| errcode == NetworkHandhakeCodes.TYPERR){
			dataGuiManager.addMsgIn(server.getGroupName()+" sends invalid Messages : \n"+checker.getErrString());		
			kill(); // thread will run out without further notice					
		}
	}
	
	private static String getMessageFromServer(Socket s){
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(s.getInputStream(),StandardCharsets.UTF_8));
			char[] buffer = new char[Config.MESSAGE_BUFFER];
			int charCount = br.read(buffer,0,Config.MESSAGE_BUFFER);
			
			return new String (buffer, 0, charCount);			
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
	    }		
	}
	
	public void setListener(){
		
	
		
	}
	
	
	
	public void kill(){
		run = false;
		dataGuiManager.removeIp(server.groupName);	
	}
}