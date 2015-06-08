package cluedoClient;


import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import json.CluedoJSON;
import broadcast.Brodcaster;
import broadcast.MulticastClientThread;
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
		setListener();	
		System.out.println("client started");
		listenForBrodcast();
		sayHello();
	}
	
	private void setListener(){
		if (gui != null){
			gui.startService.setOnAction(new EventHandler<ActionEvent>() {				
				@Override
				public void handle(ActionEvent event) {
					//startTCPConnection();	
				}
			});	
			gui.getIpList().setOnMouseClicked(new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent click) {
			        if (click.getClickCount() == 2) {
			           String currentItemSelected = gui.getIpList().getSelectionModel().getSelectedItem();
			           int currentItemSelectedIndex = gui.getIpList().getSelectionModel().getSelectedIndex();
			           gui.loginPrompt("Login to "+currentItemSelected);
			           System.out.println(currentItemSelected);
			        }
			    }
			});			
		}		
	}
	
	private void sayHello(){		
		Brodcaster bc = new Brodcaster(Config.BroadcastIp, gui);
		CluedoJSON msg = new CluedoJSON("udp client");
		msg.put("group", Config.GroupName);
		bc.setMsg(msg.toString());
	}
	
	private void listenForBrodcast(){
		MulticastClientThread broadcastListener = 
				new MulticastClientThread(serverList,"bcListenerThread",Config.BroadcastPort, gui);
		broadcastListener.start();		
	}
	
	
	/**
	 * 
	 */
	private void startTCPConnection(){		
		try {
			int port = 7000;		
			ip = gui.askForIp();
			if (ip.length() < 8) ip = new String("127.0.0.1");//localhost	
			
			//ab hier nur noch tcp 
			cSocket = new Socket(ip,port);			
			
			Thread t1 = new Thread(new ServerListener(cSocket,gui,id));
			t1.start();
			Thread t2 = new Thread(new clientMessageListener(cSocket,gui,id));
			t2.start();
			
			gui.setStatus("Connected to "+ cSocket.getInetAddress().toString());	
			
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





	
