package cluedoServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import staticClasses.Config;
import staticClasses.NetworkMessages;
import broadcast.ClientHandShakeListener;
import broadcast.Multicaster;
import cluedoNetworkGUI.CluedoServerGUI;
import cluedoNetworkGUI.DataGuiManagerServer;
import cluedoNetworkLayer.CluedoGameServer;

/**
 * @author guldener
 * 
 */
public class Server {
	
	Connector connector;
	public ServerSocket tcpSocket;
	int TCPport;

	DataManagerServer dataManager;	
	final CluedoServerGUI gui;
	public DataGuiManagerServer dataGuiManager;
	
	boolean run;
	
	
	
	public Server(CluedoServerGUI g){
		gui = g;
		TCPport = Config.TCP_PORT;	
		dataManager = new DataManagerServer();
		dataGuiManager = new DataGuiManagerServer(gui,dataManager);
	
		run = true;
		
		createTestGroups();
		sayHello();
		listenForClientsThread();		
		startTCPServer();		
		
		
		System.out.println("Server Starteeeee");
		dataGuiManager.setWindowName(Config.GROUP_NAME+" Server");
		
		setListener();	
	}
	
	private void sayHello() {
		String msg = NetworkMessages.udp_serverMsg(Config.GROUP_NAME, TCPport);				
		Multicaster bc = new Multicaster(Config.BROADCAST_WILDCARD_IP, dataGuiManager, msg);
		bc.sendBrodcast();				
	}
	
	void listenForClientsThread(){
		String answer = NetworkMessages.udp_serverMsg(Config.GROUP_NAME, TCPport);				
		ClientHandShakeListener cl = 
				new ClientHandShakeListener(answer.toString(),"udp client",Config.BROADCAST_PORT,dataGuiManager,run);
		cl.start();
	}
	
	
	private void createTestGroups(){
		for(int i = 0; i < 4; i++) {
			dataGuiManager.addGame(new CluedoGameServer(i));
		}
	}
	
	/**
	 * ein thread für eingehende verbindungen
	 * aufgebaute verbindungen werden auf eigenen threads ausgeführt
	 * @throws IOException
	 */
	private void startTCPServer()  {
		try {
			tcpSocket = new ServerSocket(TCPport);	
			connector = new Connector(tcpSocket,dataManager,dataGuiManager);
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
		gui.button0.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
	            sayHello();               
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
























