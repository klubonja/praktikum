package cluedoServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import org.json.JSONObject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import cluedoNetworkGUI.*;

/**
 * @author guldener
 * hört auf eingehende verbindungen und weist ihnen threads zu
 * übernimmt die kontroll-kommunikation des severs mit den clients
 */
class Connector extends Thread{	
	
	final CluedoServerGUI gui;
	private ServerSocket serverSocket;
	
	ArrayList<ClientItem> clientList;
	boolean running = true;	
	
	
	Connector (ServerSocket ss, CluedoServerGUI g,ArrayList<ClientItem> clist) {
		gui = g;
		serverSocket = ss;
		clientList = clist;
	}
	
	public void run(){
		try {			
			while (running){
				Socket clientSocket = serverSocket.accept();  
				ClientItem client = new ClientItem(clientSocket);
				Thread newCommunicationThread = new Thread(new CommunicationHandler(serverSocket, client, gui));
				newCommunicationThread.start();				
			}					
		}
		catch(IOException e){
			gui.setStatus(e.getMessage());
			System.out.println(e.getMessage());
		}
		finally {
			System.out.println("thread runningflag: "+running+"");
			notifyAllClients("CLOSE");
		}		
	}
	
	public void notifyAllClients(String msg){
		for (int i = 0;i < clientList.size();i++)
			clientList.get(i).sendMsg(msg);
		
	}
	
	public void notifyAllClientsButSender(String msg,ClientItem c){
		for (int i = 0;i < clientList.size(); i++)
			if (c.id != clientList.get(i).id) 
				clientList.get(i).sendMsg(msg);
		
	}
	
	public void notifyClient(String msg,ClientItem c){
		for (int i = 0;i < clientList.size();i++)
			if (c.id == clientList.get(i).id) 
				clientList.get(i).sendMsg(msg);
	}
	

	/**
	 * wird vom server aufgerufen zum höflichen schliessen der laufenden verbindungen
	 */
	public void kill(){
		running = false;
		System.out.println("networkthread killed");
	}
}