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
import staticClasses.aux;
import cluedoNetworkGUI.CluedoClientGUI;
import cluedoNetworkGUI.DataGuiManagerClient;
import cluedoNetworkGUI.GameVBox;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPlayer;


/**
 * @author guldener
 * 
 */

class OutgoingHandler implements Runnable{
	
	DataGuiManagerClient dataGuiManager;	
	boolean run;
	
	public OutgoingHandler(CluedoClientGUI gui,ServerItem server, boolean run) {
		this.run = run;
		dataGuiManager = new DataGuiManagerClient(gui, server);		
		
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
            		createGame(aux.getRandomPerson());	               
            }
        });	
		
		gui.refreshGamesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent click) {
		       dataGuiManager.refreshGamesList();
		    }
		});	
		
		dataGuiManager.getGui().getGamesListView().setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent click) {
		        if (click.getClickCount() == 2) {
		        	int gameID = gui.getGamesListView().getSelectionModel().getSelectedItem().getGameID();
		        	CluedoGameClient game = dataGuiManager.getGameByID(gameID);
		        	if (game.getNumberConnected() >= 3 && 	game.hasNick(dataGuiManager.getServer().getMyNick())){
		        		startGame(gameID);
		        	}
		        	else {
		        		ArrayList<CluedoPlayer> plist = dataGuiManager.getServer().getGameByGameID(gameID).getPlayersConnected();
			        	//TODO 
			        	selectGame(gui.getGamesListView().getSelectionModel(), gui.selectColor());		
		        	}
		        	
		        }
		    }
		});			
	}
	
	void selectGame(SelectionModel<GameVBox> g, String color) {
		int gameID = g.getSelectedItem().getGameID();		
		aux.sendTCPMsg(
				dataGuiManager.getServer().getSocket(),
				NetworkMessages.join_gameMsg(
						color,
						gameID)
				);
	}
	
	void startGame(int gameID){
		aux.sendTCPMsg(dataGuiManager.getServer().getSocket(), NetworkMessages.start_gameMsg(gameID));
	}
	
	
	private void sendInputFieldTextContent(CluedoClientGUI gui){
		aux.sendTCPMsg(
				dataGuiManager.getServer().getSocket(),
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
		aux.sendTCPMsg(dataGuiManager.getServer().getSocket(),NetworkMessages.create_gameMsg(color));
	}
	
	
	@Override
	public void run(){
		while (run){
			try {
				Thread.sleep(Config.SECOND);
			} catch (InterruptedException e) {
				aux.log.log(Level.SEVERE,e.getMessage());
			}
		}
		aux.log.log(Level.INFO,"CLIENT OutgoingHandlerThread running out");
	}
	
	
}