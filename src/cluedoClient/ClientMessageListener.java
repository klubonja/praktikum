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
import cluedoNetworkGUI.CluedoClientGUI;


/**
 * @author guldener
 * 
 */

class clientMessageListener implements Runnable{
	
	Socket cSocket;
	CluedoClientGUI gui;
	int id = 0;
	
	public clientMessageListener(Socket cs,CluedoClientGUI g,int id) {
		cSocket = cs;
		gui = g;
		this.id = id;
		System.out.println(gui.loginPrompt().toString());
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