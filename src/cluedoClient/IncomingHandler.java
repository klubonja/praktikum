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
	
	boolean running = true;
	
	IncomingHandler(Socket cs,CluedoClientGUI g,ArrayList<ServerItem> sList){
		cSocket = cs;
		gui = g;
		serverList = sList;
	}
	
	@Override
	public void run() {			
		while (running) {
			try {
				getMessagesFromServer(cSocket);
			}
			catch (Exception e){
				System.out.println("running out "+e.getMessage());
				Platform.runLater(() -> {
					gui.setStatus("Server hat sich unhöflich verabschiedet");
					//
				});		
				running = false;				
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
			CluedoProtokollChecker checker = new CluedoProtokollChecker(new CluedoJSON(new JSONObject(message)));
//			NetworkHandhakeCodes errcode = checker.validateExpectedType("login successful",null);
//			if (errcode == NetworkHandhakeCodes.OK) {
//				
//				
//			}
//			else if (errcode == NetworkHandhakeCodes.TYPEOK_MESERR 
//					|| errcode == NetworkHandhakeCodes.TYPERR){
//								
//			}
//			
//			else {
//				Platform.runLater(() -> {
//					gui.addMessageIn("unhandled incoming : \n" + message);
//				});
//			}		
			gui.addMessageOut(new JSONObject(message).toString());

			
		} 
		catch (IOException e) {
			e.printStackTrace();
	    }		
	}
}