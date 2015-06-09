package cluedoClient;


import java.net.Socket;
import java.util.ArrayList;

import staticClasses.Config;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import json.CluedoJSON;
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
	
	void sayHello(){	
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
	public void startTCPConnection(ServerItem server){		
		try {				
			cSocket = new Socket(server.getIp(),server.getPort());			
			serverList.add(server);
			Thread t1 = new Thread(new IncomingHandler(cSocket,gui,serverList));
			t1.start();
			Thread t2 = new Thread(new OutgoingHandler(cSocket,gui,id,server.getGroupName()));
			t2.start();
			
			gui.setStatus("Connected to "+server.getGroupName()+" on : "+ cSocket.getInetAddress().toString());	
			
			setCloseHandler();
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			gui.setStatus("connecting to "+server.getGroupName()+" failed \n"+e.getMessage());
		}
		finally {}	
	}	
	

	
	void setCloseHandler(){
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





	
