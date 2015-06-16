package cluedoClient;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import staticClasses.NetworkMessages;
import cluedoNetworkGUI.CluedoClientGUI;
import cluedoNetworkGUI.DataGuiManagerClient;
import cluedoNetworkGUI.GameVBox;


/**
 * @author guldener
 * 
 */

class OutgoingHandler implements Runnable{
	
	Socket cSocket;
	CluedoClientGUI gui;
	DataGuiManagerClient dataGuiManager;
	ServerItem server;
	
	
	boolean run;
	
	public OutgoingHandler(CluedoClientGUI g,ServerItem s, boolean run) {
		this.gui = g;
		this.run = run;
		dataGuiManager = new DataGuiManagerClient(gui, server);		
		server = s;		
		
		addClientGUIListener();
		login();
	}
	
	public void addClientGUIListener(){
		EventHandler<KeyEvent> listenForEnter = new EventHandler<KeyEvent> (){
			@Override
			public void handle(KeyEvent e) {
			        if (e.getCode() == KeyCode.ENTER){
			        	sendMsg(gui.inputField.getText());	
						gui.inputField.setText("");
						e.consume();
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
		    }
		});
		
		gui.submitMessageButton.setOnAction(new EventHandler<ActionEvent>() {				
			@Override
			public void handle(ActionEvent event) {
				sendMsg(NetworkMessages.chat_to_serverMsg(gui.inputField.getText(), LocalDateTime.now().toString()));	
				gui.inputField.setText("");				
			}
		});	
		
		gui.createGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            		createGame();	               
            }
        });	
		
		dataGuiManager.getGui().getGamesListView().setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent click) {
		        if (click.getClickCount() == 2) {
		           selectGame(gui.getGamesListView().getSelectionModel());		
		        }
		    }
		});		
	}
	
	void selectGame(SelectionModel<GameVBox> g) {
		int gameID = g.getSelectedItem().getGameID();
		sendMsg(NetworkMessages.join_gameMsg("white", gameID));		
	}
	
	void createGame(){
		sendMsg(NetworkMessages.create_gameMsg("white"));
		System.out.println("wefwfe");
	}
	
	private final boolean login(){
		String[] loginData = gui.loginPrompt("Login to Server: "+server.getGroupName());
		String msg = NetworkMessages.loginMsg(loginData[0],loginData[1]);
		sendMsg(msg);
		
		return true;
		
	}
	
	@Override
	public void run(){
		while (run){
			
		}
		System.out.println("CLIENT OutgoingHandlerThread running out");		
	}
	
	private void sendMsg(String msg){
		try {
			PrintWriter out = new PrintWriter(
					   new BufferedWriter(new OutputStreamWriter(
					        server.getSocket().getOutputStream(), StandardCharsets.UTF_8)), true);
			 out.print(msg);
			 out.flush();	
		}
		catch (IOException e){
			dataGuiManager.setStatus(e.getMessage());
		}			
	}		
}