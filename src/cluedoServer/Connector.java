package cluedoServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import staticClasses.Config;
import staticClasses.NetworkMessages;
import cluedoNetworkGUI.CluedoServerGUI;


class Connector extends Thread{	
	
	final CluedoServerGUI gui;
	private ServerSocket serverSocket;
	
	ArrayList<ClientItem> clientList;
	ArrayList<ClientItem> blackList;
	boolean running = true;	
	
	
	Connector (ServerSocket ss, CluedoServerGUI g,ArrayList<ClientItem> cList,ArrayList<ClientItem> bList) {
		gui = g;
		serverSocket = ss;
		clientList = cList;
		blackList = bList;
	}
	
	@Override
	public void run(){
		try {			
			while (running){
				Socket clientSocket = serverSocket.accept();
				if (!checkForExistingIp(clientSocket.getInetAddress())){
					if (isBlacklisted(clientSocket.getInetAddress()))
						sendMsg(NetworkMessages.error_Msg(Config.BLACKLISTED_MSG), clientSocket);
					Thread newCommunicationThread = new Thread(new CommunicationHandler(
							serverSocket, new ClientItem(clientSocket), gui,clientList,blackList));
					newCommunicationThread.start();	
				}
				else {
					sendMsg(NetworkMessages.error_Msg("already connected"), clientSocket);
					clientSocket.close();
				}
					 		
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
	
	private boolean checkForExistingIp(InetAddress adress){
		for (ClientItem c : clientList)
			if (adress.equals(c.getAdress())) return true;
		return false;
	}
	
	boolean isBlacklisted(InetAddress adress){
		for (ClientItem c : blackList)
			if (adress.equals(c.getAdress())) return true;
		return false;
	}
	
	public static void sendMsg(String msg,Socket socket){
		try {
			 PrintWriter out = new PrintWriter(
					   new BufferedWriter(new OutputStreamWriter(
					        socket.getOutputStream(), StandardCharsets.UTF_8)), true);
			 out.print(msg);
			 out.flush();
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}	
	}
	

	/**
	 * wird vom server aufgerufen zum höflichen schliessen der laufenden verbindungen
	 */
	public void kill(){
		running = false;
		System.out.println("networkthread killed");
	}
}