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

import org.json.JSONObject;

import staticClasses.Config;
import cluedoNetworkGUI.CluedoClientGUI;

/**
 * Diese Klasse schickt über das Klientsocket nachrichten an den Server
 * @author pascal
 * @param cs das Socket über das eine Serververbindung aufgebaut wurde
 * 
 */
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
		while (run) {
			try {
				System.out.println("CLIENT on incoming trhead");
				getMessagesFromServer(cSocket);
			}
			catch (Exception e){
				System.out.println("running out "+e.getMessage());
				Platform.runLater(() -> {
					gui.setStatus("Server hat sich unhöflich verabschiedet");
					//gui.removeIp(server.getGroupName());
					//
				});		
				run = false;
				serverList.remove(server);
			}
		}
		System.out.println("serverlistener thread running out");		
	}
	
	private void getMessagesFromServer(Socket s){
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(s.getInputStream(),StandardCharsets.UTF_8));
			char[] buffer = new char[Config.MESSAGE_BUFFER];
			int charCount = br.read(buffer,0,Config.MESSAGE_BUFFER);
			String message = new String (buffer, 0, charCount);
			CluedoProtokollChecker checker = new CluedoProtokollChecker(
					new CluedoJSON(new JSONObject(message)));
			checker.validate();
			if (checker.isValid())				
				gui.addMessageIn(checker.getMessage().toString());
			else 
				gui.addMessageIn(checker.getErrString());

			
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("CLIENT getting messsage on Incomming thread failed");
	    }		
	}
}