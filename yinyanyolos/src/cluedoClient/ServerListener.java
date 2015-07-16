package cluedoClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import javafx.application.Platform;
import cluedoNetworkGUI.CluedoClientGUI;

/**
 * Diese Klasse schickt über das Klientsocket nachrichten an den Server
 * @author pascal
 * @param cs das Socket über das eine Serververbindung aufgebaut wurde
 * 
 */
class ServerListener implements Runnable {
	int bufferSize = 1024;
	Socket cSocket;
	CluedoClientGUI gui;
	int id = 0;
	boolean running = true;
	
	/**
	 * wartet auf und behandelt nachrichten vom server
	 * und beschränkt je nach rolle die handlungmöglichkeiten des spielers im spiel
	 * @param cs
	 * @param g
	 * @param game
	 * @param id
	 */
	ServerListener(Socket cs,CluedoClientGUI g,int id){
		cSocket = cs;
		gui = g;
		this.id = id;
	}
	
	public void run() {			
		while (running) {
			try {
				getMessagesFromServer(cSocket);
			}
			catch (Exception e){
				System.out.println(""+e.getMessage());
				Platform.runLater(() -> {
					gui.setStatus("Server hat sich unhöflich verabschiedet");
					gui.clearMessages();
				});		
				running = false;				
			}
		}
		try {
			cSocket.close();
			Platform.runLater(() -> {
				gui.setStatus("Server Connection closed (höflich)");
				gui.clearMessages();
			});		
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}		
		System.out.println("serverlistener thread running out");		
	}
	
	/**
	 * wartet auf und behandelt nachrichten vom server
	 * signalwörter sind 
	 * START  
	 * RESET
	 * CLOSE hier soll der thread zu servernachrichtenbehandlung auslaufen
	 * ansonsten wird je nach rolle ein paddle bewegt
	 * @param s
	 * @throws Exception
	 */
	private void getMessagesFromServer(Socket s) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(s.getInputStream(),StandardCharsets.UTF_8));
		char[] buffer = new char[bufferSize];
		int charCount = br.read(buffer,0,bufferSize);
		String message = new String (buffer, 0, charCount);
		if (message.equals("START")){
			Platform.runLater(() -> {
			});			
		}
		else if (message.equals("RESET")){
			Platform.runLater(() -> {
			});			
		}
		else if (message.equals("CLOSE")){
			Platform.runLater(() -> {		
			});
			running = false;	
		}
		else {
			Platform.runLater(() -> {
				try {
									}
				catch(Exception e){
					gui.addMessage(e.getMessage());
				}
				
			});				
		}			
	}
}