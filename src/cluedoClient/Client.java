package cluedoClient;


import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import staticClasses.Config;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import broadcast.Multicaster;
import broadcast.ServerHandShakeListener;
import cluedoNetworkGUI.CluedoClientGUI;
import cluedoNetworkGUI.CluedoServerGUI;
import cluedoNetworkGUI.DataGuiManagerClientSpool;
import cluedoNetworkGUI.GameVBox;
import cluedoNetworkGUI.NetworkActorVBox;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoServer.Server;
import enums.GameStates;
import enums.Persons;
import enums.ServerStatus;



/**
 * @author guldener
 * 
 */
public class Client {
	
	CluedoClientGUI gui;
	ServerPool serverList;
	ArrayList<Server> createdServers;
	DataGuiManagerClientSpool dataGuiManager;
	boolean globalRun;
		
	public Client(CluedoClientGUI g) {
		gui = g;
		serverList = new ServerPool();
		dataGuiManager = new DataGuiManagerClientSpool(gui, serverList);
		dataGuiManager.setWindowName(Config.GROUP_NAME+ " Client");		
		globalRun = true;
		createdServers = new ArrayList<Server>();
		
		
		setHandler();
		listenForServersThread();
		sayHello();
		
		//gui.createServer.setOnMouseClicked(e -> createServer());
		auxx.log.log(Level.INFO,"CLIENT started");		
	}	
	
	void setHandler(){
		
		//////////////////////////CHAT//////////////////////////////////////////////////////////////////
		auxx.setStyleChatField(gui.inputField, false);
		gui.createGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	ArrayList<String> colors = new ArrayList<String>();
            	for(Persons p : Persons.values()){
            		colors.add(p.getColor());
            	}
	        	String color = gui.selectColor(colors);
	        	auxx.loginfo("color from prompt "+color);

	        	if(color != null){
            		createGame(color);	  
	        	}
            }
        });	
		
		gui.getCreateGameMenu().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	ArrayList<String> colors = new ArrayList<String>();
            	for(Persons p : Persons.values()){
            		colors.add(p.getColor());
            	}
	        	String color = gui.selectColor(colors);
	        	auxx.loginfo("color from prompt "+color);

	        	if(color != null){
            		createGame(color);	  
	        	}
            }
        });	
		
		EventHandler<KeyEvent> listenForEnter = new EventHandler<KeyEvent> (){
			@Override
			public void handle(KeyEvent e) {
			        if (e.getCode() == KeyCode.ENTER){
			        	try {	
			        		if (!gui.inputField.getText().equals("")){
			        			auxx.sendTCPMsg(
				        				dataGuiManager.getSelectedServer().getSocket(),
				        				NetworkMessages.chatMsg(gui.inputField.getText(),dataGuiManager.getSelectedServer().getMyNick(),auxx.now()));	
			        		}	
			        		e.consume();
				        	gui.inputField.setText("");	
			        	} 
			        	catch (Exception e1) {
			        		auxx.logsevere("sending chat failed ",e1);
							globalRun = false;
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
			
		///////////////////////CREATE SERVER/////////////////////////////////////	
			gui.createServer.setOnMouseClicked(new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent click) {
			    	createServer();
			    }
			});	
			
			gui.getCreateServerMenu().setOnAction(new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent click) {
			    	createServer();
			    }
			});	
		////////////////////////END CREATE SERVER/////////////////////////////////
			
		/////////////////////////RREFRSHGAMESLIST SELECTED SERVER////////////////
			gui.refreshGamesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent click) {
			       dataGuiManager.refreshGamesListServer(dataGuiManager.getSelectedServer());
			    }
			});	
		///////////////////////REFERSH GAMESLIST ON SECLECTED SERVER END////////////////////////
		
		////////////////////////SELECT GAME /////////////////////////////////////////////////
			dataGuiManager.getGui().getGamesListView().setOnMouseClicked(new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent click) {
			        if (click.getClickCount() == 2) {
			        	GameVBox guiGame = gui.getGamesListView().getSelectionModel().getSelectedItem();
			        	int gameID = guiGame.getGameID();
			        	String servername = guiGame.getServerName();
			        	String serverip = guiGame.getServerIp();
			        	ServerItem server = dataGuiManager.getServerByID(servername, serverip);
			        	CluedoGameClient game = server.getGameByGameID(gameID);
			        	
			      
			        	if (game.getNumberConnected() >= Config.MIN_CLIENTS_FOR_GAMESTART 
			        			&& game.hasPlayerConnectedByNick(server.getMyNick())
			        			&& game.getGameState() == GameStates.not_started){
			        		sendStartGameRequest(gameID);
			        	}
			        	else if (
			        			game.getGameState() == GameStates.started && game.hasPlayerConnectedByNick(server.getMyNick())
			        			|| game.getGameState() == GameStates.ended
			        			)  {
				        	dataGuiManager.leaveGame(game.getGameId());	
			        	}
			        	else if (game.getGameState() == GameStates.started){
			        		 dataGuiManager.joinGameAsWatcher(server,game.getGameId());

			        	}
			        	else if (game.getGameState() != GameStates.ended)  {
				        	ArrayList<String> colors = server.getGameByGameID(gameID).getAvailableColors();
			        		selectGame(game, gui.selectColor(colors));		
			        	}
			        	auxx.logfine("game on: "+serverip+" groupname : "+servername+" mynick : "+server.getMyNick()+" gamestate : "+game.getGameState());

			        }
			    }
			});		
		////////////////////////SELECT GAME END/////////////////////////////////////////////////
        ////////////////////////SEDNDHANDSHAKE MAN/////////////////////////////////////////////////
			gui.button0.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	sayHello();
	            }
	        });	
		////////////////////////SEDNDHANDSHAKE MAN END/////////////////////////////////////////////////			
			////////////////////////TESTSERVERCONNECTION MAN/////////////////////////////////////////////////
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
			
			gui.getConnectToTestServer().setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
					startTestServerConnection();
	            }
	        });	
			////////////////////////TESTSERVERCONNECTION MAN END/////////////////////////////////////////////////
			////////////////////////CLOSEWINDOW/////////////////////////////////////////////////

			gui.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			      @Override
				public void handle(WindowEvent e){
			          try {
			        	   dataGuiManager.sayGoodbye();
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
			////////////////////////CLOSEWINDOW END////////////////////////////////////////////////

			////////////////////////SELECT SERVER/////////////////////////////////////////////////
			gui.getNetworkActorsView().setOnMouseClicked(new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent click) {
			        if (click.getClickCount() == 2) {
			           selectIp(dataGuiManager.getNetworkActorsListView().getSelectionModel());		
			        }
			    }
			});	
			////////////////////////SELECT SERVER END/////////////////////////////////////////////////
			
			

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
						dataGuiManager,answer,"udp server",Config.BROADCAST_PORT,this,true);
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
//			Thread t2 = new Thread(new OutgoingHandler(dataGuiManager,server,globalRun,localRun));
//			t2.start();
						
		}
		catch (IOException e){
			 auxx.logsevere("TCP server connection failed",e);
			dataGuiManager.removeServer(server);
			localRun = false;
		}
		finally {}	
	}	
	
	void createGame(String color){
		
		auxx.sendTCPMsg(dataGuiManager.getSelectedServer().getSocket(), NetworkMessages.create_gameMsg(color));
	}	
	
	void selectGame(CluedoGameClient game, String color) {	
		auxx.sendTCPMsg(
				game.getServer().getSocket(),
				NetworkMessages.join_gameMsg(
						color,
						game.getGameId())
				);
	}
	
	void sendStartGameRequest(int gameID){
		
		auxx.sendTCPMsg(dataGuiManager.getSelectedServer().getSocket(), NetworkMessages.start_gameMsg(gameID));
		this.gui.getAudio().stop();
	}
	
	void selectIp(SelectionModel<NetworkActorVBox> smod) {
		try {
			ServerItem server = dataGuiManager.getServerByID(
					smod.getSelectedItem().getNameID(),
					smod.getSelectedItem().getIpID());
					auxx.logfine("attempting login to serverport : "+server.getPort()+", ip: "+server.getIpString());
			if (server.getStatus() == ServerStatus.not_connected){
				if (server.getSocket() == null)	startTCPConnection(server); //login to server					
				if (!auxx.login(dataGuiManager.getGui(), server)) dataGuiManager.removeServer(server);							
			}
			else if (server.getStatus() == ServerStatus.connected){//select server being fully connected
				dataGuiManager.refreshGamesListServer(server);
				dataGuiManager.setSelectedServer(server);
			}
			
			
		}
		catch (Exception e){
			auxx.logsevere("server isnt connected anymore", e);
		}		
	}
	
//	public void createServer(){
//		Stage stage = new Stage();
//		CluedoServerGUI gui = new CluedoServerGUI(stage);
//		Server server = new Server(gui);
//		stage.show();
//	}
	
	public void startTestServerConnection(){
		try {
			InetAddress addr = InetAddress.getByName("vanuabalavu.pms.ifi.lmu.de");
			startTCPConnection(new ServerItem("testendeTentakel", addr, 30305));
		} 
		catch (UnknownHostException e) {
			auxx.logsevere("testserverconnection failed Unknown Host:  ", e);
		}            
	}
	
	public void createServer(){
		Stage stage = new Stage();
		CluedoServerGUI gui = new CluedoServerGUI(stage);
		@SuppressWarnings("unused")
		Server server = new Server(gui);
		createdServers.add(server);
		stage.show();
		stage.hide();
	}
	
	public void kill(){
		try {
     	   dataGuiManager.sayGoodbye();
     	   for (Server s: createdServers) s.kill();
     	   globalRun = false;
     	   auxx.log.log(Level.INFO,"CLIENT CLOSED");
            Platform.exit();
            System.exit(0);	               
       } 
       catch (Exception e1) {
            auxx.log.log(Level.SEVERE,e1.getMessage());
       }
   }
}






	
