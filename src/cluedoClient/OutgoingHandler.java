package cluedoClient;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import json.CluedoJSON;
import cluedoNetworkGUI.CluedoClientGUI;


/**
 * @author guldener
 * 
 */

class clientMessageListener implements Runnable{
	
	Socket cSocket;
	CluedoClientGUI gui;
	String id;
	
	public clientMessageListener(Socket cs,CluedoClientGUI g,int id) {
		cSocket = cs;
		gui = g;
		login();
		addClientGUIListener();
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
				sendMsg(gui.inputField.getText());	
				gui.inputField.setText("");
				
			}
		});	
	}
	
	private final boolean login(){
		CluedoJSON handShake = new CluedoJSON();
		String[] loginData = gui.loginPrompt("Login to remote Server");
		handShake.put("type", "login");
		handShake.put("nick", loginData[0]);
		handShake.put("group", loginData[1]);
		sendMsg(handShake.toString());
		
		return true;
		
	}
	
	@Override
	public void run(){
		Platform.runLater(() -> {
			gui.addMessageIn("listening");
		});		
	}
	
	private void sendMsg(String msg){
		try {
			PrintWriter out = new PrintWriter(
					   new BufferedWriter(new OutputStreamWriter(
					        cSocket.getOutputStream(), StandardCharsets.UTF_8)), true);
			 out.print(msg);
			 out.flush();	
		}
		catch (IOException e){
			gui.setStatus(e.getMessage());
		}			
	}		
}