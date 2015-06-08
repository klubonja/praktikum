package cluedoServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import json.CluedoJSON;

import org.json.JSONObject;

import broadcast.BrodcastServerThread;
import broadcast.Brodcaster;
import broadcast.ClientHandShakeListener;
import cluedoNetworkGUI.CluedoServerGUI;
import enums.Config;

/**
 * @author guldener
 * 
 */
public class Server  {
	
	public CluedoGroup[] groups;
	public ServerSocket socket;
	final CluedoServerGUI gui;
	ExecutorService pool;
	int TCPport;
	int poolSize;
	boolean running;
	NetworkService networkService;
	
	public Server(CluedoServerGUI g){
		gui = g;
		poolSize = 4;
		TCPport = Config.TCPport;		
		running = false;
		pool = Executors.newFixedThreadPool(poolSize);	
		setListener();
		System.out.println("Server Start");	
		broadcast();
		listenForClientsThread();
	}
	
	private void broadcast() {
		JSONObject msg = new JSONObject();
		msg.put("type", "udp server");
		msg.put("group", Config.GroupName);
		msg.put("tcp port", TCPport);				
		Brodcaster bc = new Brodcaster(Config.BroadcastIp, gui, msg.toString());
		bc.sendBrodcast();				
	}
	
	void listenForClientsThread(){
		CluedoJSON answer = new CluedoJSON("udp server");
		answer.put("group", Config.GroupName);
		answer.put("tcp port", Config.TCPport);
		ClientHandShakeListener cl = 
				new ClientHandShakeListener(answer.toString(),"udp client",Config.BroadcastPort,gui);
		cl.start();
	}
	
	/**
	 * ein thread für eingehende verbindungen
	 * aufgebaute verbindungen werden auf eigenen threads ausgeführt
	 * @throws IOException
	 */
	private void createGroups(){
		for(int i = 0; i < 4; i++) groups[i] = new CluedoGroup(6,"reduzierterHund"+i);
	}
	
	private void startServer()  throws IOException{
		
		socket = new ServerSocket(TCPport);	
		networkService = new NetworkService(socket, pool, TCPport, gui);
		Thread t = new Thread(networkService);
		t.start();
		System.out.println("Server running");
		running = true;		
	}
	
	private void stopServer()  throws IOException{
		networkService.kill();
		socket.close();	
		running = false;
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
























