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
import staticClasses.aux;
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
		run = true;
		TCPport = Config.TCP_PORT;	
		dataManager = new DataManagerServer(Config.GROUP_NAME);
		dataGuiManager = new DataGuiManagerServer(gui,dataManager);	
		dataGuiManager.setWindowName(Config.GROUP_NAME+" Server");
		
		createTestGroups();
		sayHello();
		listenForClientsThread();		
		startTCPServer();				
		
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
			
			aux.loginfo("new TCPSocket created");
		} 
		catch (IOException e) {
			gui.addMessageIn(e.getMessage());
			try {
				stopServer();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}		
	}
	
	private void stopServer()  throws IOException{
		dataManager.notifyAll(NetworkMessages.disconnectedMsg("server "+ Config.GROUP_NAME + " says : byebye, and thanks for all the fish"));
		connector.kill();
		tcpSocket.close();	
		run = false;
		
		aux.loginfo("Server will bu shutdown run == false");
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
		               aux.loginfo("SERVER Window closed");
		          } 
		          catch (Exception e1) {
		               e1.printStackTrace();
		          }
		      }
		 });
	}
}
























