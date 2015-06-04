package cluedoClient;


import java.net.*;

import org.json.JSONObject;

import cluedoNetworkGUI.CluedoClientGUI;
import enums.Config;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;



/**
 * @author guldener
 * verbindet sich mit server und kontrolliert die kommunikation zwischen diesem und dem spiel
 * das ist natürlich anfällig für betrug aber auch sehr effizient
 * 
 */
public class Client {
	
	Socket cSocket;
	CluedoClientGUI gui;
	String ip;
	int id;
	
	public Client(CluedoClientGUI g) {
		gui = g;
		setListener();	
		listenForBroadCast();
	}
	
	private void setListener(){
		if (gui != null){
			gui.startClientButton.setOnAction(new EventHandler<ActionEvent>() {				
				@Override
				public void handle(ActionEvent event) {
					//startTCPConnection();	
				}
			});			
		}
		
	}
	
	private void listenForBroadCast(){
		MulticastClientThread broadCastListener = 
				new MulticastClientThread("bcListenerThread",Config.BroadcastListenerIp, gui);
		broadCastListener.start();
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





	
