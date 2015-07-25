package cluedoServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.WindowEvent;
import staticClasses.Config;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import broadcast.BroadcastServerThread;
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
	public ServerSocket TCPServerSocket;
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
		
		//createTestGroups();
		sayHello();
		listenForClientsThread();
		
		startTCPServer();				
		
		setListener();	
	}
	
	
	private void sayHello() {
		String msg = NetworkMessages.udp_serverMsg(Config.GROUP_NAME, TCPport);				
		Multicaster bc = new Multicaster(Config.BROADCAST_WILDCARD_IP, dataGuiManager, msg);
		bc.sendBrodcast();	
		BroadcastServerThread permanentBroadcaster = new BroadcastServerThread(
				Config.GROUP_NAME, Config.BROADCAST_WILDCARD_IP, msg, dataGuiManager);
		permanentBroadcaster.start();
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
			TCPServerSocket = new ServerSocket();	
			TCPServerSocket.setReuseAddress(true);
			TCPServerSocket.bind(new InetSocketAddress(TCPport));
			connector = new Connector(TCPServerSocket,dataManager,dataGuiManager);
			connector.start();
			
			try {
				NetworkInterfacesIpManager nm = new NetworkInterfacesIpManager();				 	
				gui.setStatus("port "+TCPport+ " NInterfaces: "+nm.getServicesFormated()+" "+StandardCharsets.UTF_8);
			} 
			catch (Exception e) {
				auxx.logsevere("getting networkserveices failed", e);
			}
			
			auxx.loginfo("new TCPSocket created");
		} 
		catch (IOException e) {
			kill();
			auxx.logsevere("creating serversocket failed :" + e.getMessage());			
		}		
	}
	
	public void kill(){
		dataManager.sendMsgToAllClients(NetworkMessages.disconnectedMsg("server "+ Config.GROUP_NAME + " says : byebye, and thanks for all the fish"));
		if (connector != null) connector.kill();
		if (TCPServerSocket != null)
			try {
				TCPServerSocket.close();
			} catch (IOException e) {
				auxx.logsevere("destroying serversocket failed ", e);
			}	
		run = false;
		
		auxx.loginfo("Server will bu shutdown run == false");
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
		               auxx.loginfo("SERVER Window closed");
		          } 
		          catch (Exception e1) {
		               e1.printStackTrace();
		          }
		      }
		 });
		//////////////////////////CHAT//////////////////////////////////////////////////////////////////
		auxx.setStyleChatField(gui.inputField, false);
		
		EventHandler<KeyEvent> listenForEnter = new EventHandler<KeyEvent> (){
			@Override
			public void handle(KeyEvent e) {
			  if (e.getCode() == KeyCode.ENTER){
			  	try {	
			  		if (!gui.inputField.getText().equals("")){
			  			dataManager.sendMsgToAllClients(
			  					NetworkMessages.chatMsg(gui.inputField.getText(),auxx.now())
			  					);	
			  		}	
			  		e.consume();
			      	gui.inputField.setText("");	
			  	} 
			  	catch (Exception e1) {
			  		auxx.logsevere("sending chat failed ",e1);
				}		        			        	
			  }			        
			}
		};	
		gui.inputField.focusedProperty().addListener(new ChangeListener<Boolean>(){				
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean hasFocus){		    			
				if (hasFocus){ 
					gui.inputField.addEventHandler(KeyEvent.KEY_PRESSED,listenForEnter );        	        	
				}
				else {
					gui.inputField.removeEventHandler(KeyEvent.KEY_PRESSED,listenForEnter );   
				}
				auxx.setStyleChatField(gui.inputField, hasFocus);
			}
		});		
		////////////////////////// ENDCHAT//////////////////////////////////////
	}
}
























