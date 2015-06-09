package cluedoClient;


import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import json.CluedoJSON;
import broadcast.Multicaster;
import broadcast.ServerHandShakeListener;
import cluedoNetworkGUI.CluedoClientGUI;
import enums.Config;



/**
 * @author guldener
 * 
 */
public class Client {
	
	Socket cSocket;
	CluedoClientGUI gui;
	String ip;
	int id;
	ArrayList<ServerItem> serverList;
	
	public Client(CluedoClientGUI g) {
		gui = g;
		serverList = new ArrayList<ServerItem>();
		//setListener();	
		System.out.println("client started");
		listenForServersThread();
		sayHello();
	}
	
	
	
	
	
	private void sayHello(){	
		CluedoJSON msg = new CluedoJSON("udp client");
		msg.put("group", Config.GROUP_NAME);
		Multicaster bc = new Multicaster(Config.BROADCAST_WILDCARD_IP, gui, msg.toString());
		bc.sendBrodcast();
	}
	
	void listenForServersThread(){
		CluedoJSON answer = new CluedoJSON("udp client");
		answer.put("group", Config.GROUP_NAME);
		answer.put("tcp port", Config.TCP_PORT);
		ServerHandShakeListener cl = 
				new ServerHandShakeListener(serverList,answer.toString(),"udp server",Config.BROADCAST_PORT,gui,this);
		cl.start();
	}
	
	
	/** 
	 * 
	 */
	public void startTCPConnection(ServerItem serverInfo){		
		try {				
			cSocket = new Socket(serverInfo.getIp(),serverInfo.getPort());			
			
			Thread t1 = new Thread(new IncomingHandler(cSocket,gui,id));
			t1.start();
			Thread t2 = new Thread(new OutgoingHandler(cSocket,gui,id,serverInfo.getGroupName()));
			t2.start();
			
			gui.setStatus("Connected to "+serverInfo.getGroupName()+" on : "+ cSocket.getInetAddress().toString());	
			
			setCloseHandler();
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			gui.setStatus(e.getMessage());
		}
		finally {}	
	}	
	

	
	private void setCloseHandler(){
		gui.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		      @Override
			public void handle(WindowEvent e){
		          
		          try {
		        	   cSocket.close();
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





	
