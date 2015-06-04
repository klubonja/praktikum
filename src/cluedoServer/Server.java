package cluedoServer;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*; 
import java.net.*;

import org.json.JSONObject;

import cluedoNetworkGUI.*;
import enums.Config;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

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
	}
	
	private void broadcast() {
		JSONObject msg = new JSONObject();
		msg.put("type", "udp server");
		msg.put("group", Config.GroupName);
		msg.put("tcp port", TCPport);
		
		
		MulticastServerThread broadcaster = 
				new MulticastServerThread("broadcastTthread", Config.BroadcastIp,msg.toString() , gui);
		broadcaster.start();
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
		gui.startServerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	try {            	
	            	if (!running){            		
	            		startServer();
	            		gui.startServerButton.setText("Shutdown");
	            		running = true;
	            	}
	            	else {	            		
	            		gui.startServerButton.setText("Server restart");
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
























