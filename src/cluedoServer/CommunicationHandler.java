package cluedoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javafx.application.Platform;
import json.CluedoJSON;
import json.CluedoProtokollChecker;

import org.json.JSONObject;

import staticClasses.Config;
import staticClasses.NetworkMessages;
import cluedoNetworkGUI.CluedoServerGUI;
import enums.NetworkHandhakeCodes;

/**
 * @author guldener
 * verteilt die nachrichten der des clients
 */
class CommunicationHandler implements Runnable{
	
	ServerSocket serverSocket;
	ClientItem client;
	Connector networkService;
	Socket socket;
	
	ArrayList<ClientItem> clientList;
	ArrayList<ClientItem> blackList;
	
	final CluedoServerGUI gui;
	boolean running = true;
	
	
	
	CommunicationHandler(ServerSocket ss, ClientItem c,  CluedoServerGUI g,ArrayList<ClientItem> cList,ArrayList<ClientItem> bList) {
		gui = g;
		serverSocket = ss;
		clientList = cList;
		blackList = bList;
		client = c;
	}
	
	private void awaitingLoginAttempt (){
		boolean readyForCommunication = false;
		System.out.println("awaiting");

		while (!readyForCommunication) {
			try {
				String message = getMessageFromClient(client.getSocket()).trim();
				CluedoProtokollChecker checker = new CluedoProtokollChecker(new CluedoJSON(new JSONObject(message)));
				NetworkHandhakeCodes errcode = checker.validateExpectedType("login",null);

				if (errcode == NetworkHandhakeCodes.OK) {							
					Platform.runLater(() -> {
						gui.addMessageIn(client.getAdress()+" says :"+message);
						gui.addIp(client.getAdress()+" "+client.getNick());
					});
					
					client.setNick(checker.getMessage().getString("nick"));
					client.setGroupName(checker.getMessage().getString("group"));
					clientList.add(client);
					readyForCommunication = true;
				}
				else if (errcode == NetworkHandhakeCodes.TYPEOK_MESERR 
						|| errcode == NetworkHandhakeCodes.TYPERR){
					Platform.runLater(() -> {
						gui.addMessageIn(client.getAdress()+" sends invalid Messages : \n"+checker.getErrString());
					});	
					client.sendMsg(NetworkMessages.error_Msg("you are violating the protokoll due to the following: \n"+checker.getErrString()));
					client.sendMsg(NetworkMessages.disconnectMsg());
					client.closingConnection();
					blackList.add(client);
					
					
					
					readyForCommunication = true; // no further listinenig on this socket
					running = false; // thread will run out without further notice					
				}
				
				else {
					Platform.runLater(() -> {
						gui.addMessageIn("unhandled incoming : \n" + message);
					});
				}	
				

			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	@Override
	public void run(){	
		awaitingLoginAttempt();
		while (running){
			try {
	           String message = getMessageFromClient(client.socket).trim();
	           
	           System.out.println(message);	         
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
	
	
	String getMessageFromClient(Socket cs) throws IOException{
		StringBuffer message = new StringBuffer();
			try {
				BufferedReader clientInMessage = new BufferedReader(new InputStreamReader(cs.getInputStream(),StandardCharsets.UTF_8));
				char[] buffer = new char[Config.MESSAGE_BUFFER];
			 	int anzahlZeichen = clientInMessage.read(buffer, 0, Config.MESSAGE_BUFFER); // blockiert bis Nachricht empfangen
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
