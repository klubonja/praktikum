package cluedoClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import staticClasses.Config;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoNetworkGUI.CluedoClientGUI;
import cluedoNetworkGUI.DataGuiManagerClientSpool;
import cluedoNetworkGUI.GameVBox;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPlayer;
import enums.GameStates;
import enums.Persons;


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
            	ArrayList<String> colors = new ArrayList<String>();
            	for(Persons p : Persons.values()){
            		colors.add(p.getColor());
            	}
	        	String color = gui.selectColor(colors);
	        	if(!(color == null)){
            		createGame(color);	  
	        	}
            }
        });	
		
		gui.refreshGamesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent click) {
		       dataGuiManager.refreshGamesListServer(server);
		    }
		});	
		
		//clickonagame event
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
		        	ArrayList<String> colors = dataGuiManager.getSelectedServer().getGameByGameID(gameID).getAvailableColors();
		        	String color = gui.selectColor(colors);
		      
		        	if (game.getNumberConnected() >= Config.MIN_CLIENTS_FOR_GAMESTART 
		        			&& game.hasNick(server.getMyNick())
		        			&& game.getGameState() == GameStates.not_started){
		        		sendStartGameRequest(gameID);
		        	}
		        	else if (game.getGameState() != GameStates.ended && !(color == null))  {
		        		
		        		selectGame(game, color);		
		        	}	
		        	
		        	auxx.logfine("game on: "+serverip+" groupname : "+servername+" gamestate : "+game.getGameState());

		        }
		    }
		});			
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
	
	void createGame(String color){
		auxx.sendTCPMsg(dataGuiManager.getSelectedServer().getSocket(),NetworkMessages.create_gameMsg(color));
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