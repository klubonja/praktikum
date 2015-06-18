package cluedoClient;

import java.time.LocalDateTime;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import staticClasses.Config;
import staticClasses.Methods;
import staticClasses.NetworkMessages;
import cluedoNetworkGUI.CluedoClientGUI;
import cluedoNetworkGUI.DataGuiManagerClient;
import cluedoNetworkGUI.GameVBox;


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
		login(dataGuiManager.getGui());
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
            		createGame("white");	               
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
	
	private void sendInputFieldTextContent(CluedoClientGUI gui){
		Methods.sendTCPMsg(
				dataGuiManager.getServer().getSocket(),
				NetworkMessages.chat_to_serverMsg(
						gui.inputField.getText(), 
						LocalDateTime.now().toString()
						)
				);
		gui.inputField.setText("");
	}
	
	void selectGame(SelectionModel<GameVBox> g) {
		int gameID = g.getSelectedItem().getGameID();
		Methods.sendTCPMsg(dataGuiManager.getServer().getSocket(),NetworkMessages.join_gameMsg("white", gameID));
	}
	
	void createGame(String color){
		Methods.sendTCPMsg(dataGuiManager.getServer().getSocket(),NetworkMessages.create_gameMsg(color));
	}
	
	private final boolean login(CluedoClientGUI gui){
		String[] loginData = gui.loginPrompt("Login to Server: "+dataGuiManager.getServer().getGroupName());
		String msg;
		
//		String[] loginData = new String[]{"",""};
//		while (loginData[0].equals("") || loginData[1].equals(""))
//			loginData = gui.loginPrompt("Login to Server: "+dataGuiManager.getServer().getGroupName());
//		
		try {
			msg = NetworkMessages.loginMsg(loginData[0],loginData[1]);

		} catch (Exception e) {
			msg = null;
		}
		if (msg == null)	return false;

		Methods.sendTCPMsg(dataGuiManager.getServer().getSocket(),msg);
		return true;
	}
	
	@Override
	public void run(){
		while (run){
			try {
				Thread.sleep(Config.SECOND);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("CLIENT OutgoingHandlerThread running out");		
	}
	
	
}