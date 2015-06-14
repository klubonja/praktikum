package cluedoClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javafx.application.Platform;
import json.CluedoJSON;
import json.CluedoProtokollChecker;

import org.json.JSONArray;
import org.json.JSONObject;

import staticClasses.Config;
import staticClasses.NetworkMessages;
import cluedoNetworkGUI.CluedoClientGUI;
import cluedoNetworkLayer.CluedoGameClient;
import enums.NetworkHandhakeCodes;


class IncomingHandler implements Runnable {
	
	Socket cSocket;
	CluedoClientGUI gui;
	ServerList serverList;
	ServerItem server;
	
	boolean run = true;
	
	IncomingHandler(Socket cs,CluedoClientGUI g,ServerItem server,ServerList sList,boolean run){
		cSocket = cs;
		gui = g;
		serverList = sList;
		this.run = run;
		this.server = server;
		setListener();
	}
	
	@Override
	public void run() {	
		getGamesList();
		while (run) {
			try {
				String msg = getMessageFromServer(cSocket);
				CluedoProtokollChecker checker = new CluedoProtokollChecker(
						new CluedoJSON(new JSONObject(msg)));
				checker.validate();
				if (checker.isValid())				
					gui.addMessageIn(checker.getMessage().toString());
					if (checker.getType().equals("game created")){
						int gameID = checker.getMessage().getInt("gameID");
						CluedoGameClient newgame = 
								new CluedoGameClient(gameID);
						Platform.runLater(() -> {
							gui.addGame(gameID, "Game", 
									checker.getMessage().
									getJSONObject("player").getString("nick"));
						});						
					}
					else if (checker.getType().equals("player added")){
							int gameID = checker.getMessage().getInt("gameID");
		        		   JSONObject player = checker.getMessage().getJSONObject("player");
		        		  
		        		   Platform.runLater(() -> {
								gui.updateGame(
										gameID, 
										"(updated) Game "+gameID, 
										gui.getGame(gameID).
											getInfoString()+ " "+player.getString("nick"));
								gui.addMessageIn(checker.getMessage().toString());
							});			        		   
					}
						
				else {
					gui.addMessageIn(checker.getErrString());
				}
					
			}
			catch (Exception e){
				System.out.println("running out "+e.getMessage());
				Platform.runLater(() -> {
					gui.setStatus("Server hat sich unhöflich verabschiedet");					
				});	
				kill();
			}
			
		}
		kill();
		
		System.out.println("serverlistener thread running out");		
	}
	
	private void getGamesList(){
		String msg = getMessageFromServer(cSocket);
		CluedoProtokollChecker checker = new CluedoProtokollChecker(new JSONObject(msg));
		NetworkHandhakeCodes errcode = checker.validateExpectedType("login successful", null);
		
		if (errcode == NetworkHandhakeCodes.OK) {	
			JSONArray gamearray = checker.getMessage().getJSONArray("game array");	
			ArrayList<CluedoGameClient> gameslist = NetworkMessages.createGamesFromJSONGameArray(gamearray);
			server.addGames(gameslist);
			Platform.runLater(() -> {
				for (CluedoGameClient c: gameslist)
					gui.addGame(c.getGameId(),"Game" ,c.getNicksConnected());
			});					
		}
		else if (errcode == NetworkHandhakeCodes.TYPEOK_MESERR 
				|| errcode == NetworkHandhakeCodes.TYPERR){
			Platform.runLater(() -> {
				gui.addMessageIn(server.getGroupName()+" sends invalid Messages : \n"+checker.getErrString()+"\n"+checker.getMessage());
			});				
			kill(); // thread will run out without further notice					
		}
	}
	
	private String getMessageFromServer(Socket s){
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
		
		serverList.remove(server);
		Platform.runLater(() -> {
			gui.removeIp(server.getGroupName());
		});			
	}
}