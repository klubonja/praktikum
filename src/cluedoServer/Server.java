package cluedoServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import staticClasses.Config;
import staticClasses.NetworkMessages;
import broadcast.ClientHandShakeListener;
import broadcast.Multicaster;
import cluedoNetworkGUI.CluedoServerGUI;

/**
 * @author guldener
 * 
 */
public class Server {
	
	Connector connector;
	public ServerSocket tcpSocket;
	ArrayList<ClientItem> clientList;
	ArrayList<ClientItem> blackList;
	int TCPport;
	
	final CluedoServerGUI gui;
	public CluedoGame[] games;
	
	boolean run;
	
	
	
	public Server(CluedoServerGUI g){
		gui = g;
		TCPport = Config.TCP_PORT;	
		clientList = new ArrayList<ClientItem>();
		blackList = new ArrayList<ClientItem>();
		run = true;
			
		sayHello();
		listenForClientsThread();
		
		startServer();		
		setListener();	
		
		System.out.println("Server Starteeeee");
		gui.setWindowName(Config.GROUP_NAME+" Server");
	}
	
	private void sayHello() {
		String msg = NetworkMessages.udp_serverMsg(Config.GROUP_NAME, TCPport);				
		Multicaster bc = new Multicaster(Config.BROADCAST_WILDCARD_IP, gui, msg);
		bc.sendBrodcast();				
	}
	
	void listenForClientsThread(){
		String answer = NetworkMessages.udp_serverMsg(Config.GROUP_NAME, TCPport);				
		ClientHandShakeListener cl = 
				new ClientHandShakeListener(answer.toString(),"udp client",Config.BROADCAST_PORT,gui,run);
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
			connector = new Connector(tcpSocket, gui,clientList,blackList);
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
		run = false;
		
		System.out.println("Serverservices closed");
	}
	
	private void setListener(){
		gui.startService.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	//try {            	
	            	sayHello();
           // 	}
//            	catch(IOException e){
//            		System.out.println(e.getMessage());
//            	}            		               
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
























