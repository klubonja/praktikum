package cluedoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import javafx.application.Platform;
import cluedoNetworkGUI.CluedoServerGUI;

/**
 * @author guldener
 * verteilt die nachrichten der des clients
 */
class communicationHandler implements Runnable{
	
	ServerSocket serverSocket;
	Client client;
	NetworkService networkService;
	Socket socket;
	final CluedoServerGUI gui;
	boolean running = true;
	int id;
	int bufferSize = 1024;
	
	
	
	communicationHandler(ServerSocket ss, Client c, NetworkService s, CluedoServerGUI g,int id) throws IOException{
		serverSocket = ss;
		client = c;
		networkService = s;
		gui = g;
		this.id = id;	
		long threadId = Thread.currentThread().getId();
		System.out.println("client "+id+" on group:"+networkService.group.getName()+" living in thread"+threadId+ "added");

	}
	
	public void run(){		
		while (running){
			try {
	           String message = getMessageFromClient(client.socket).trim();
	           //System.out.println(message);
	           if (message.equals("CLOSE")){
	        	   closeConnection("Client "+id+ " says plolitely: "+message);
	           }
				networkService.notifyAllClientsButSender(message,client);
				Platform.runLater(() -> {
					gui.addMessage(client.id+" says : "+ message);
				});		
			}
			catch (IOException e){
				//System.out.println(e.getMessage());
				try {
					closeConnection("closing :"+e.getMessage());
				}
				catch (IOException ex){
					//System.out.println(ex.getMessage());
					Platform.runLater(() -> {
						gui.addMessage(ex.getMessage());
					});	
				}				
			}
		}				
	}
	
	private void closeConnection(String msg) throws IOException{
		serverSocket.close();
		Platform.runLater(() -> {
			gui.addMessage(msg);
			System.out.println(msg);
			gui.removeClient(id);
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
