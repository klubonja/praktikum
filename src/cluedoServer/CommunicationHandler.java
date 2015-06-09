package cluedoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import javafx.application.Platform;
import cluedoNetworkGUI.CluedoServerGUI;

/**
 * @author guldener
 * verteilt die nachrichten der des clients
 */
class CommunicationHandler implements Runnable{
	
	ServerSocket serverSocket;
	ClientItem client;
	Connector networkService;
	Socket socket;
	final CluedoServerGUI gui;
	boolean running = true;
	int bufferSize = 1024;
	
	
	
	CommunicationHandler(ServerSocket ss, ClientItem c,  CluedoServerGUI g) throws IOException{
		serverSocket = ss;
		client = c;
		gui = g;
	}
	
	private void awaitingLoginAttempt (){
		boolean readyForCommunication = false;
		while (!readyForCommunication) {
			try {
				String message = getMessageFromClient(client.socket).trim();
				JSONObject json = new JSONObject(message);
				Platform.runLater(() -> {
					gui.addMessageIn(client.id+" says : after json login : "+ json.get("type"));
				});
				readyForCommunication = true;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}	
	}
	
	public void run(){	
		awaitingLoginAttempt();
		while (running){
			try {
	           String message = getMessageFromClient(client.socket).trim();
	           //System.out.println(message);	         
			}
			catch (IOException e){
				try {
					closeConnection("closing :"+e.getMessage());
				}
				catch (IOException ex){
					Platform.runLater(() -> {
						gui.addMessageIn(ex.getMessage());
					});	
				}				
			}
		}				
	}
	
	private void closeConnection(String msg) throws IOException{
		serverSocket.close();
		Platform.runLater(() -> {
			gui.addMessageIn(msg);
			System.out.println(msg);
		});
		running = false;
	}
	
	
	private String getMessageFromClient(Socket cs) throws IOException{
		StringBuffer message = new StringBuffer();
			try {
				BufferedReader clientInMessage = new BufferedReader(new InputStreamReader(cs.getInputStream(),StandardCharsets.UTF_8));
				char[] buffer = new char[bufferSize];
			 	int anzahlZeichen = clientInMessage.read(buffer, 0, bufferSize); // blockiert bis Nachricht empfangen
				message.append(new String(buffer, 0, anzahlZeichen));
			}
			catch(Exception e) {}		
		 	
			return message.toString();
	}
	
	private void sendMessageToClient(String msg){
		client.sendMsg(msg);
	}
	
	
	
	private static String getHostInfo(){
		StringBuffer hostInfo = new StringBuffer();
		try {
			hostInfo.append(InetAddress.getLocalHost());
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}
		finally {
			return hostInfo.toString();
		}
	}
	
	public void kill(){
		running = false;
	}
	
}
