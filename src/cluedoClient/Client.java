package cluedoClient;


import java.net.Socket;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import staticClasses.Config;
import staticClasses.NetworkMessages;
import broadcast.Multicaster;
import broadcast.ServerHandShakeListener;
import cluedoNetworkGUI.CluedoClientGUI;



/**
 * @author guldener
 * 
 */
public class Client {
	
	Socket cSocket;
	CluedoClientGUI gui;
	ServerList serverList;
	boolean run;
		
	public Client(CluedoClientGUI g) {
		gui = g;
		serverList = new ServerList();
		gui.setWindowName(Config.GROUP_NAME+" Client");
		System.out.println("client started");
		run = true;
		setCloseHandler();
		listenForServersThread();
		sayHello();
		
		
	}	
	
	void sayHello(){	
		String msg = NetworkMessages.udp_clientMsg(Config.GROUP_NAME);
		Multicaster bc = new Multicaster(Config.BROADCAST_WILDCARD_IP, gui, msg);
		bc.sendBrodcast();
	}
	
	void listenForServersThread(){
		String answer = NetworkMessages.udp_clientMsg(Config.GROUP_NAME);
		ServerHandShakeListener cl = 
				new ServerHandShakeListener(serverList,answer,"udp server",Config.BROADCAST_PORT,gui,this,run);
		cl.start();
	}
	
	
	/** 
	 * 
	 */
	public void startTCPConnection(ServerItem server){	
		try {				
			cSocket = new Socket(server.getIp(),server.getPort());			
			serverList.add(server);
			Thread t1 = new Thread(new IncomingHandler(cSocket,gui,server,serverList,run));
			t1.start();
			Thread t2 = new Thread(new OutgoingHandler(cSocket,gui,server.getGroupName(),run));
			t2.start();
			
			gui.setStatus("Connected to "+server.getGroupName()+" on : "+ cSocket.getInetAddress().toString());	
			
			setCloseHandler();
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			gui.setStatus("connecting to "+server.getGroupName()+" failed \n"+e.getMessage());
			run = false;
			serverList.remove(server);
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
		
		gui.getIpListView().setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent click) {
		        if (click.getClickCount() == 2) {
		           selectIp(gui.getIpListView().getSelectionModel());		
		        }
		    }
		});	
	}
	
	void selectIp(SelectionModel<String> smod) {
		//String[] loginInfo = ((CluedoClientGUI) gui).loginPrompt("Login to "+selectedListItemName);
		ServerItem serverInfo = serverList.get(smod.getSelectedIndex());
		startTCPConnection(serverInfo);
		System.out.println(smod.toString());
		
		
		
	}
}





	
