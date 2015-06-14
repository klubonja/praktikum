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
import cluedoNetworkGUI.CluedoClientGUI;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPlayer;
import enums.GameStates;
import enums.NetworkHandhakeCodes;
import enums.Persons;
import enums.PlayerStates;


class IncomingHandler implements Runnable {
	
	Socket cSocket;
	CluedoClientGUI gui;
	ArrayList<ServerItem> serverList;
	ServerItem server;
	
	boolean run = true;
	
	IncomingHandler(Socket cs,CluedoClientGUI g,ServerItem server,ArrayList<ServerItem> sList,boolean run){
		cSocket = cs;
		gui = g;
		serverList = sList;
		this.run = run;
		this.server = server;
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
				else 
					gui.addMessageIn(checker.getErrString());
			}
			catch (Exception e){
				System.out.println("running out "+e.getMessage());
				Platform.runLater(() -> {
					gui.setStatus("Server hat sich unhöflich verabschiedet");					
				});	
				kill();
			}
			kill();
		}
		
		System.out.println("serverlistener thread running out");		
	}
	
	private void getGamesList(){
		String msg = getMessageFromServer(cSocket);
		CluedoProtokollChecker checker = new CluedoProtokollChecker(new JSONObject(msg));
		NetworkHandhakeCodes errcode = checker.validateExpectedType("login successful", null);
		
		if (errcode == NetworkHandhakeCodes.OK) {	
			JSONArray gamearray = checker.getMessage().getJSONArray("game array");
			for (int i = 0; i < gamearray.length(); i++){							
				CluedoGameClient newgame = new CluedoGameClient(
						gamearray.getJSONObject(i).getInt("gameID"));
				newgame.setGameState(
						GameStates.getState(
								gamearray.getJSONObject(i).getString("gamestate")
								)
							);
				
				JSONArray players = gamearray.getJSONObject(i).getJSONArray("players");				
				for (int n = 0;n < players.length();n++){
					CluedoPlayer player = new CluedoPlayer(
							Persons.getPersonByColor(
									players.getJSONObject(n).getString("color")
									),
							PlayerStates.getPlayerState(
									players.getJSONObject(n).getString("playerstate")
									)									
							);	
					player.setNick(players.getJSONObject(n).getString("nick"));
				}
				
				JSONArray watchers = gamearray.getJSONObject(i).getJSONArray("watchers");				
				for (int n = 0;n < watchers.length();n++){
					//wofür?
				}
				
				JSONArray personposs = gamearray.getJSONObject(i).getJSONArray("person positions");				
				for (int n = 0;n < personposs.length();n++){						
					JSONObject ppos = personposs.getJSONObject(n);
					String pname = ppos.getString("person");
					newgame.
						getPlayer(pname).
							getPosition().
								setX(ppos.getJSONObject("field").
										getInt("x"));
					newgame.getPlayer(pname).getPosition().setY(ppos.getJSONObject("field").getInt("y"));
				}
				
				JSONArray weaponposs = gamearray.getJSONObject(i).getJSONArray("weapon positions");				
				for (int n = 0;n < weaponposs.length();n++){						
					JSONObject wpos = weaponposs.getJSONObject(n);
					String wname = wpos.getString("weapon");
					newgame.getWeapon(wname).getPosition().setX( wpos.getJSONObject("field").getInt("x"));
					newgame.getWeapon(wname).getPosition().setY(wpos.getJSONObject("field").getInt("y"));
				}				
				
				server.addGame(newgame);
				Platform.runLater(() -> {
				});	
				
			}
			System.out.println(gamearray.toString());
					
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
	
	public void kill(){
		run = false;
		serverList.remove(server);
		Platform.runLater(() -> {
			gui.removeIp(server.getGroupName());
		});			
	}
}