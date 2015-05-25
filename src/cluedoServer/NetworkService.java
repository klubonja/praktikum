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
	private ExecutorService pool;
	int port;
	int numberConnectedClients;
	ArrayList<Client> clientList = new ArrayList<Client>();
	boolean runningGame = false;
	boolean netWorkServiceThreadRunning = true;
	int maxClients = 6;
	
	
	NetworkService (ServerSocket ss, ExecutorService pool, int port, CluedoServerGUI g) {
		gui = g;
		serverSocket = ss;
		this.pool = pool;
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
	            
				pool.execute(new communicationHandler(serverSocket,cl,this,gui,numberConnectedClients));
				if (numberConnectedClients > maxClients){
					notifyClient("enough players",cl);
					notifyClient("CLOSE", cl);
				}
			}					
		}
		catch(IOException e){
			gui.setStatus(e.getMessage());
			System.out.println(e.getMessage());
		}
		finally {
			System.out.println("thread runningflag: "+netWorkServiceThreadRunning+"");
			notifyAllClients("CLOSE");
			pool.shutdownNow();	
			if (pool.isShutdown()) System.out.println("threadpoolshutdoen");// ist scheisse
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
	 * wartet auf eingehende UDP verbindung mit client
	 * schickt ein spieldatenobjekt zur begrüssung
	 * @author sanghun, jakob
	 * @param port
	 * @param dataJSON
	 * @return
	 * @throws IOException
	 */
	public static boolean handShakeWithClientUDP(int port,JSONObject dataJSON) throws IOException{		
		try {
			System.out.println("Waitung for UDP client");
			DatagramSocket serverSocket = new DatagramSocket(port);            
			byte[] receiveData = new byte[1024];             
			byte[] sendData = new byte[1024];    	
	    	DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
	    	serverSocket.receive(receivePacket);
	    	String serverMsg = new String(receivePacket.getData());
	    	System.out.println("Vom Client : " + serverMsg);
	    	InetAddress IPAddress = receivePacket.getAddress(); 	
	    	int port1 = receivePacket.getPort();  	    	
	    	sendData = dataJSON.toString().getBytes();                   
	    	DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port1);                   
	    	serverSocket.send(sendPacket); 
	    	serverSocket.close();
	    	
	    	return true;
			
		} 
		catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}	
	}
	
	
	private JSONObject addFromGameDataToInitJSON(int id) {
		JSONObject giveit = new JSONObject();
		/*
		GameData gameDataInit = giveit; //nur damit keine fehler stören
		giveit.put("ID", id);
		giveit.put("block1Up", gameDataInit.block1Up);
		giveit.put("block1Down", gameDataInit.block1Down);
		giveit.put("block2Up", gameDataInit.block2Up);
		giveit.put("block2Down", gameDataInit.block2Down);
		
		giveit.put("speedUpRounds", gameDataInit.speedUpRounds);
		
		giveit.put("padding", String.valueOf(gameDataInit.padding));
		giveit.put("blockHeight", String.valueOf(gameDataInit.blockHeight));
		giveit.put("blockWidth", String.valueOf(gameDataInit.blockWidth));
		giveit.put("ballRad", String.valueOf(gameDataInit.ballRad));
		
		giveit.put("blockMoveDelta", String.valueOf(gameDataInit.blockMoveDelta));
		giveit.put("ballMoveDelta", String.valueOf(gameDataInit.ballMoveDelta));
		
		giveit.put("frames", String.valueOf(gameDataInit.frames));
		
		giveit.put("frameRate", String.valueOf(gameDataInit.frameRate));
		giveit.put("speedUpStep", String.valueOf(gameDataInit.speedUpStep));
		giveit.put("spin", false);
		giveit.put("defaultFeldWidth", String.valueOf(gameDataInit.defaultFeldWidth));
		giveit.put("defaultFeldHeight", String.valueOf(gameDataInit.padding));
		
		giveit.put("feldH", String.valueOf(gameDataInit.feldH));
		giveit.put("feldW", String.valueOf(gameDataInit.feldW));
		*/

		return giveit;
	}
	
	/**
	 * wird vom server aufgerufen zum höflichen schliessen der laufenden verbindungen
	 */
	public void kill(){
		netWorkServiceThreadRunning = false;
		System.out.println("networkthread killed");
	}
}