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
	
	ClientPool clientPool;
	ArrayList<ClientItem> blackList;
	GameListServer gameList;
	boolean running = true;	
	
	
	Connector (ServerSocket ss, CluedoServerGUI g,ClientPool cList,ArrayList<ClientItem> bList,GameListServer gl) {
		gui = g;
		serverSocket = ss;
		clientPool = cList;
		blackList = bList;
		gameList = gl;
	}
	
	@Override
	public void run(){
		try {			
			while (running){
				Socket clientSocket = serverSocket.accept();
				if (!clientPool.checkForExistingIp(clientSocket.getInetAddress())){
					if (isBlacklisted(clientSocket.getInetAddress()))
						sendMsg(NetworkMessages.error_Msg(Config.BLACKLISTED_MSG), clientSocket);
					Thread newCommunicationThread = new Thread(new CommunicationHandler(
							serverSocket, new ClientItem(clientSocket), gui,clientPool,blackList,gameList));
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
			kill();
		}		
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