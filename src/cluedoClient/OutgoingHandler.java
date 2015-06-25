package cluedoClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import staticClasses.Config;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoNetworkGUI.CluedoClientGUI;
import cluedoNetworkGUI.DataGuiManagerClient;
import cluedoNetworkGUI.DataGuiManagerClientSpool;
import cluedoNetworkGUI.GameVBox;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPlayer;


/**
 * @author guldener
 * 
 */

class OutgoingHandler implements Runnable{
	
	DataGuiManagerClientSpool dataGuiManager;	
	ServerItem server;
	boolean localRun;
	boolean globalRun;
	
	public OutgoingHandler(DataGuiManagerClientSpool dataGuiManager,ServerItem server,boolean globalRun, boolean localRun) {
		this.localRun = localRun;
		this.globalRun = globalRun;
		this.dataGuiManager = dataGuiManager;
		this.server = server;
		addClientGUIListener(dataGuiManager.getGui());
	
	}
	
	public void addClientGUIListener(CluedoClientGUI gui){
		EventHandler<KeyEvent> listenForEnter = new EventHandler<KeyEvent> (){
			@Override
			public void handle(KeyEvent e) {
			        if (e.getCode() == KeyCode.ENTER){
			        	sendInputFieldTextContent(dataGuiManager.getGui());
						gui.inputField.setText("");
						e.consume();
			        }
			    }
			};	
		dataGuiManager.getGui().inputField.focusedProperty().addListener(new ChangeListener<Boolean>(){				
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean hasFocus){		    			
		    	if (hasFocus){ 
		        	gui.inputField.addEventHandler(KeyEvent.KEY_PRESSED,listenForEnter );        	        	
		    	}
		    	else {
		    		gui.inputField.removeEventHandler(KeyEvent.KEY_PRESSED,listenForEnter );   
		    	}
		    }
		});
		
		gui.submitMessageButton.setOnAction(new EventHandler<ActionEvent>() {				
			@Override
			public void handle(ActionEvent event) {
				sendInputFieldTextContent(gui);		
			}
		});	
		
		gui.createGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            		createGame(auxx.getRandomPerson());	               
            }
        });	
		
		gui.refreshGamesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent click) {
		       dataGuiManager.refreshGamesListServer(server);
		    }
		});	
		
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
		        	
		        	/*
		        	CluedoGameClient game = dataGuiManager.getServerByID(
		        			gui.getGamesListView().getSelectionModel().getSelectedItem().getServerName(),
		        			gui.getGamesListView().getSelectionModel().getSelectedItem().getServerIp()
		        			).getGameByGameID(
		        					gui.getGamesListView().getSelectionModel().getSelectedItem().getGameID()
		        					);
		        	*/
		        	
		        	if (game.getNumberConnected() >= Config.MIN_CLIENTS_FOR_GAMESTART && game.hasNick(server.getMyNick())){
		        		sendStartGameRequest(gameID);
		        	}
		        	else {
		        		ArrayList<CluedoPlayer> plist = server.getGameByGameID(gameID).getPlayersConnected();
			        	//TODO 
			        	selectGame(gui.getGamesListView().getSelectionModel(), gui.selectColor());		
		        	}	
		        	auxx.loginfo("clicking on game on: "+serverip+" groupname : "+servername);
		        }
		    }
		});			
	}
	
	void selectGame(SelectionModel<GameVBox> g, String color) {
		int gameID = g.getSelectedItem().getGameID();		
		auxx.sendTCPMsg(
				server.getSocket(),
				NetworkMessages.join_gameMsg(
						color,
						gameID)
				);
	}
	
	void sendStartGameRequest(int gameID){
		auxx.sendTCPMsg(server.getSocket(), NetworkMessages.start_gameMsg(gameID));
	}
	
	
	private void sendInputFieldTextContent(CluedoClientGUI gui){
		auxx.sendTCPMsg(
				server.getSocket(),
				NetworkMessages.chat_to_serverMsg(
						gui.inputField.getText(), 
						LocalDateTime.now().toString() // 2015-04-08T15:16:23.42
						)
				);
				
		gui.inputField.setText("");
	}
	
//	void selectGame(SelectionModel<GameVBox> g) {
//		int gameID = g.getSelectedItem().getGameID();
//		Methods.sendTCPMsg(
//				dataGuiManager.getServer().getSocket(),
//				NetworkMessages.join_gameMsg(
//						Methods.getRandomPerson(),
//						gameID)
//				);
//	}
	
	void createGame(String color){
		auxx.sendTCPMsg(server.getSocket(),NetworkMessages.create_gameMsg(color));
	}
	
	
	@Override
	public void run(){
		while (localRun && globalRun){
			try {
				Thread.sleep(Config.SECOND*10);
			} catch (InterruptedException e) {
				auxx.log.log(Level.SEVERE,e.getMessage());
			}
		}
		auxx.log.log(Level.INFO,"CLIENT OutgoingHandlerThread running out");
	}
	
	
}