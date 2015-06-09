package cluedoServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import json.CluedoJSON;

import org.json.JSONObject;

import broadcast.Multicaster;
import broadcast.ClientHandShakeListener;
import cluedoNetworkGUI.CluedoServerGUI;
import enums.Config;

/**
 * @author guldener
 * 
 */
public class Server {
	
	Connector connector;
	public ServerSocket tcpSocket;
	ArrayList<ClientItem> clientList;
	int TCPport;
	
	final CluedoServerGUI gui;
	public CluedoGame[] games;
	
	boolean running;
	
	
	
	public Server(CluedoServerGUI g){
		gui = g;
		TCPport = Config.TCP_PORT;	
		clientList = new ArrayList<ClientItem>();
			
		sayHello();
		listenForClientsThread();
		
		startServer();		
		setListener();	
		
		System.out.println("Server Start");	
	}
	
	private void sayHello() {
		JSONObject msg = new JSONObject();
		msg.put("type", "udp server");
		msg.put("group", Config.GROUP_NAME);
		msg.put("tcp port", TCPport);				
		Multicaster bc = new Multicaster(Config.BROADCAST_WILDCARD_IP, gui, msg.toString());
		bc.sendBrodcast();				
	}
	
	void listenForClientsThread(){
		CluedoJSON answer = new CluedoJSON("udp server");
		answer.put("group", Config.GROUP_NAME);
		answer.put("tcp port", Config.TCP_PORT);
		ClientHandShakeListener cl = 
				new ClientHandShakeListener(answer.toString(),"udp client",Config.BROADCAST_PORT,gui);
		cl.start();
	}
	
	
	private void createGroups(){
		for(int i = 0; i < 4; i++) games[i] = new CluedoGame(6,"reduzierterHund"+i);
	}
	
	/**
	 * ein thread für eingehende verbindungen
	 * aufgebaute verbindungen werden auf eigenen threads ausgeführt
	 * @throws IOException
	 */
	private void startServer()  {
		try {
			tcpSocket = new ServerSocket(TCPport);	
			connector = new Connector(tcpSocket, gui,clientList);
			connector.start();
			try {
				NetworkInterfacesIpManager nm = new NetworkInterfacesIpManager();				 	
				gui.setStatus("port "+TCPport+ " NInterfaces: "+nm.getServicesFormated()+" "+StandardCharsets.UTF_8);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Server running");			
		} catch (IOException e) {
			gui.addMessageIn(e.getMessage());
		}		
	}
	
	private void stopServer()  throws IOException{
		connector.kill();
		tcpSocket.close();	
		System.out.println("Serverservices closed");
	}
	
	private void setListener(){
		gui.startService.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	try {            	
	            	if (!running){            		
	            		startServer();
	            		gui.startService.setText("Shutdown");
	            		running = true;
	            	}
	            	else {	            		
	            		gui.startService.setText("Server restart");
	            		gui.setStatus("Socket closed, Server Shutdown"); 
	            		gui.emptyList();
	            		stopServer();
	            		running = false;
	            	}           
            	}
            	catch(IOException e){
            		System.out.println(e.getMessage());
            	}            		               
            }
        });	
		
		gui.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		      @Override
			public void handle(WindowEvent e){		          
		          try {		        	   
		               Platform.exit();
		               System.exit(0);
		               System.out.println("Terminated");  
		          } 
		          catch (Exception e1) {
		               e1.printStackTrace();
		          }
		      }
		 });
	}
}
























