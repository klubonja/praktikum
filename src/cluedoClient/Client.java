package cluedoClient;


import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import staticClasses.Config;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import broadcast.Multicaster;
import broadcast.ServerHandShakeListener;
import cluedoNetworkGUI.CluedoClientGUI;
import cluedoNetworkGUI.DataGuiManagerClientSpool;
import cluedoNetworkGUI.NetworkActorVBox;
import enums.ServerStatus;



/**
 * @author guldener
 * 
 */
public class Client {
	
	CluedoClientGUI gui;
	ServerPool serverList;
	DataGuiManagerClientSpool dataGuiManager;
	boolean globalRun;
		
	public Client(CluedoClientGUI g) {
		gui = g;
		serverList = new ServerPool();
		dataGuiManager = new DataGuiManagerClientSpool(gui, serverList);
		dataGuiManager.setWindowName(Config.GROUP_NAME+ " Client");
		
		globalRun = true;
		setCloseHandler();
		listenForServersThread();
		sayHello();
		setCloseHandler();
		
		auxx.log.log(Level.INFO,"CLIENT started");		
	}	
	
	void sayHello(){	
		String msg = NetworkMessages.udp_clientMsg(Config.GROUP_NAME);
		Multicaster bc = new Multicaster(Config.BROADCAST_WILDCARD_IP, dataGuiManager, msg);
		bc.sendBrodcast();
	}
	
	void listenForServersThread(){
		String answer = NetworkMessages.udp_clientMsg(Config.GROUP_NAME);
		ServerHandShakeListener cl = 
				new ServerHandShakeListener(
						dataGuiManager,answer,"udp server",Config.BROADCAST_PORT,this,globalRun);
		cl.start();
	}
	
	
	/** 
	 * 
	 */
	public void startTCPConnection(ServerItem server){	
		boolean localRun = true;
		try {	
			server.setSocket(new Socket(server.getIp(),server.getPort()));
			dataGuiManager.addServer(server,"not logged in");
			Thread t1 = new Thread(new IncomingHandler(dataGuiManager,server,globalRun,localRun));
			t1.start();
			Thread t2 = new Thread(new OutgoingHandler(dataGuiManager,server,globalRun,localRun));
			t2.start();
						
		}
		catch (IOException e){
			 auxx.logsevere("TCP server connection failed",e);
			dataGuiManager.removeServer(server);
			localRun = false;
		}
		finally {}	
	}	
	

	
	void setCloseHandler(){
		gui.button0.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	sayHello();
            }
        });	
		gui.connectToTestServer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
				try {
					InetAddress addr = InetAddress.getByName("vanuabalavu.pms.ifi.lmu.de");
					startTCPConnection(new ServerItem("testendeTentakel", addr, 30305));
				} 
				catch (UnknownHostException e) {
					auxx.logsevere("testserverconnection failed Unknown Host:  ", e);
				}            	
            }
        });	
		gui.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		      @Override
			public void handle(WindowEvent e){
		          try {
		        	  dataGuiManager.sayGoodbye(NetworkMessages.disconnectMsg());
		        	   globalRun = false;
		        	   auxx.log.log(Level.INFO,"CLIENT CLOSED");
		               Platform.exit();
		               System.exit(0);	               
		          } 
		          catch (Exception e1) {
		               auxx.log.log(Level.SEVERE,e1.getMessage());
		          }
		      }
		 });
		
		gui.getNetworkActorsView().setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent click) {
		        if (click.getClickCount() == 2) {
		           selectIp(dataGuiManager.getNetworkActorsListView().getSelectionModel());		
		        }
		    }
		});	
	}
	
	
	
	void selectIp(SelectionModel<NetworkActorVBox> smod) {
		//String[] loginInfo = ((CluedoClientGUI) gui).loginPrompt("Login to "+selectedListItemName);
		try {
			ServerItem server = dataGuiManager.getServerByID(
					smod.getSelectedItem().getNameID(),
					smod.getSelectedItem().getIpID());
					auxx.logfine("attempting login to serverport : "+server.getPort()+", ip: "+server.getIpString());
			if (server.getStatus() == ServerStatus.not_connected){
				if (server.getSocket() == null)	startTCPConnection(server);
					
				if (!auxx.login(dataGuiManager.getGui(), server))	
					dataGuiManager.removeServer(server);							
			}
			else if (server.getStatus() == ServerStatus.connected){
				dataGuiManager.refreshGamesListServer(server);
			}
		}
		catch (Exception e){
			auxx.logsevere("server isnt connected anymore", e);
		}		
	}
}





	
