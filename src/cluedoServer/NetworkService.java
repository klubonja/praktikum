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
class NetworkService implements Runnable{	
	final CluedoServerGUI gui;
	private ServerSocket serverSocket;
	public CluedoGroup group;
	int port;
	int numberConnectedClients;
	ArrayList<Client> clientList = new ArrayList<Client>();
	boolean runningGame = false;
	boolean netWorkServiceThreadRunning = true;
	int maxClients = 6;
	
	
	NetworkService (ServerSocket ss, CluedoGroup group, int port, CluedoServerGUI g) {
		gui = g;
		serverSocket = ss;
		this.group = group;
		this.port = port;
		numberConnectedClients = 0;
	}
	
	public void run(){
		try {
			Platform.runLater(() -> {
				try {
					NetworkInterfacesIpManager nm = new NetworkInterfacesIpManager();				 	
					gui.setStatus("port "+port+ " NInterfaces: "+nm.getServicesFormated()+" "+StandardCharsets.UTF_8);
				} catch (Exception e) {
					e.printStackTrace();
				}
            });
			
			while (netWorkServiceThreadRunning){
				Socket clientSocket = serverSocket.accept();  
				numberConnectedClients++;
				Client cl = new Client(numberConnectedClients,clientSocket);
				clientList.add(cl);
				
				Platform.runLater(() -> {
					gui.addClient(cl.socket.getInetAddress().toString());
	            });
	            
				group.pool.execute(new communicationHandler(serverSocket,cl,this,gui,numberConnectedClients));
			}					
		}
		catch(IOException e){
			gui.setStatus(e.getMessage());
			System.out.println(e.getMessage());
		}
		finally {
			System.out.println("thread runningflag: "+netWorkServiceThreadRunning+"");
			notifyAllClients("CLOSE");
			group.pool.shutdownNow();	
			if (group.pool.isShutdown()) System.out.println("threadpoolshutdown");// ist scheisse
		}		
	}
	
	public void notifyAllClients(String msg){
		for (int i = 0;i < numberConnectedClients;i++){
			clientList.get(i).sendMsg(msg);
		}
	}
	
	public void notifyAllClientsButSender(String msg,Client c){
		for (int i = 0;i < numberConnectedClients;i++){
			if (c.id != clientList.get(i).id) clientList.get(i).sendMsg(msg);
		}
	}
	
	public void notifyClient(String msg,Client c){
		for (int i = 0;i < numberConnectedClients;i++){
			if (c.id == clientList.get(i).id) clientList.get(i).sendMsg(msg);
		}
	}
	

	

	
	/**
	 * wird vom server aufgerufen zum höflichen schliessen der laufenden verbindungen
	 */
	public void kill(){
		netWorkServiceThreadRunning = false;
		System.out.println("networkthread killed");
	}
}