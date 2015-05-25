package cluedoServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*; 
import java.net.*;

import cluedoNetworkGUI.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * @author guldener
 * 
 */
public class TCPServer  {
	
	public ExecutorService pool;
	public ServerSocket socket;
	final CluedoServerGUI gui;
	int port;
	int poolSize;
	boolean running;
	NetworkService networkService;
	
	public TCPServer(CluedoServerGUI g){
		gui = g;
		poolSize = 4;
		port = 7000;		
		running = false;
		setListener();
		System.out.println("Server Start");		
	}
	
	/**
	 * ein thread für eingehende verbindungen
	 * aufgebaute verbindungen werden auf eigenen threads ausgeführt
	 * @throws IOException
	 */
	private void startServer()  throws IOException{
		pool = Executors.newFixedThreadPool(poolSize);		
		socket = new ServerSocket(port);	
		networkService = new NetworkService(socket, pool, port, gui);
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
























