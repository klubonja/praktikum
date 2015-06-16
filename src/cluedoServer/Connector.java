package cluedoServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import staticClasses.Config;
import staticClasses.NetworkMessages;
import cluedoNetworkGUI.DataGuiManagerServer;


class Connector extends Thread{	
	
	private ServerSocket serverSocket;
	
//	ClientPool clientPool;
//	ArrayList<ClientItem> blackList;
//	GameListServer gameList;
	DataManagerServer dataManger;
	DataGuiManagerServer dataGuiManager;
	boolean running = true;	
	
	
	Connector (ServerSocket ss, DataManagerServer datam, DataGuiManagerServer dgm) {
		serverSocket = ss;
		dataManger = datam;
		dataGuiManager = dgm;
	}
	
	@Override
	public void run(){
		try {			
			while (running){
				Socket clientSocket = serverSocket.accept();
				if (!dataManger.checkIpExists(clientSocket.getInetAddress())){
					if (dataManger.isBlacklisted(clientSocket.getInetAddress()))
						sendMsg(NetworkMessages.error_Msg(Config.BLACKLISTED_MSG), clientSocket);
					Thread newCommunicationThread = new Thread(new CommunicationHandler(
							serverSocket, new ClientItem(clientSocket),dataManger,dataGuiManager));
					newCommunicationThread.start();	
				}
				else {
					sendMsg(NetworkMessages.error_Msg("already connected"), clientSocket);
					clientSocket.close();
				}					 		
			}					
		}
		catch(IOException e){
			//gui.setStatus(e.getMessage());
			dataGuiManager.addMsgIn("connector says :fuck "+e.getMessage());
			System.out.println(e.getMessage());
		}
		finally {
			System.out.println("thread runningflag: "+running+"");
			kill();
		}		
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