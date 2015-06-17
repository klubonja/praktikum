package cluedoClient;


import java.io.IOException;
import java.net.Socket;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import staticClasses.Config;
import staticClasses.NetworkMessages;
import sun.security.util.PropertyExpander.ExpandException;
import broadcast.Multicaster;
import broadcast.ServerHandShakeListener;
import cluedoNetworkGUI.CluedoClientGUI;
import cluedoNetworkGUI.DataGuiManager;
import cluedoNetworkGUI.DataGuiManagerClientSpool;
import cluedoNetworkGUI.NetworkActorHBox;



/**
 * @author guldener
 * 
 */
public class Client {
	
	Socket cSocket;
	CluedoClientGUI gui;
	ServerPool serverList;
	DataGuiManagerClientSpool dataGuiManager;
	boolean run;
		
	public Client(CluedoClientGUI g) {
		gui = g;
		serverList = new ServerPool();
		dataGuiManager = new DataGuiManagerClientSpool(gui, serverList);
		dataGuiManager.setWindowName(Config.GROUP_NAME+ " Client");
		System.out.println("client started");
		run = true;
		setCloseHandler();
		listenForServersThread();
		sayHello();
		
		
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
						dataGuiManager,answer,"udp server",Config.BROADCAST_PORT,this,run);
		cl.start();
	}
	
	
	/** 
	 * 
	 */
	public void startTCPConnection(ServerItem server){	
		try {				
			cSocket = new Socket(server.getIp(),server.getPort());
			server.setSocket(cSocket);
			dataGuiManager.addServer(server);
			//serverList.add(server);
			//dataManager.addNe
			Thread t1 = new Thread(new IncomingHandler(gui,server,run));
			t1.start();
			Thread t2 = new Thread(new OutgoingHandler(gui,server,run));
			t2.start();
						
			setCloseHandler();
		}
		catch (IOException e){
			System.out.println(e.getMessage());			
			dataGuiManager.removeServer("TCP server connection failed"+e.getMessage(),server);
			run = false;
			
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
		gui.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		      @Override
			public void handle(WindowEvent e){
		          
		          try {
		        	   run = false;
		        	   if (cSocket != null) cSocket.close();
		               Platform.exit();
		               System.exit(0);
		               System.out.println("Terminated");
		               
		          } 
		          catch (Exception e1) {
		               e1.printStackTrace();
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
	
	void selectIp(SelectionModel<NetworkActorHBox> smod) {
		//String[] loginInfo = ((CluedoClientGUI) gui).loginPrompt("Login to "+selectedListItemName);
		try {
			System.out.println("attempting getting from serverpool atr"+smod.getSelectedIndex());
			NetworkActorHBox nb = smod.getSelectedItem();
			ServerItem serverInfo = dataGuiManager.getServerByID(
					smod.getSelectedItem().getNameID(),
					smod.getSelectedItem().getIpID());
			if (serverInfo.getSocket() == null){
				System.out.println("attemtping to create TCP Con wioth"+serverInfo.getIpString());
				startTCPConnection(serverInfo);
				System.out.println("slecting"+serverInfo.getGroupName()+" at "+smod.getSelectedIndex());
			}
			else {
				dataGuiManager.addMsgIn("alread connected to Server "+serverInfo.getGroupName());
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
}





	
