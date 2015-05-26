package cluedoClient;

import java.io.BufferedWriter;
import json.*;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	}
	
	private final boolean login(){
		CluedoJSON handShake = new CluedoJSON("login");
		String[] loginData = gui.loginPrompt();
		handShake.put("nick", loginData[0]);
		handShake.put("group", loginData[1]);
		sendMsg(handShake.toString());
		
		return true;
		
	}
	
	public void run(){
		Platform.runLater(() -> {
			gui.addMessage("listening");
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