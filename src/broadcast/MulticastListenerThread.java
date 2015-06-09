package broadcast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionModel;
import javafx.scene.input.MouseEvent;
import json.CluedoJSON;
import json.CluedoProtokollChecker;
import cluedoNetworkGUI.CluedoNetworkGUI;
import enums.Config;

public abstract class MulticastListenerThread extends Thread{
	MulticastSocket socket;
	DatagramPacket packet;
	InetAddress listenAdress;
	int port;
	
	byte[] buf;
	int bufSize;
	
	String answer;
	String expType;
	
	CluedoNetworkGUI gui;
	
	boolean running;
	
	abstract void listen();
	abstract void select(SelectionModel<String> s);
	abstract void startServiceAction();
	
	
	public MulticastListenerThread(String answer, String expType, int port, CluedoNetworkGUI g)  {
		super();
		this.answer = new String(answer);
		this.expType = new String(expType);
		try {
			socket = new MulticastSocket(null);
			SocketAddress a = new InetSocketAddress(port);
			socket.bind(a);
			socket.setLoopbackMode(false);
			bufSize = Config.NETWORK_BUFFER_SIZE;
			gui = g;
			setListener();
			running = true;
			
		} 
		catch (Exception e) {
			System.out.println("mutlicast client :"+e.getMessage());
		}	
	}
	
	@Override
	public void run(){
		while (running){
			try {
				listen();
			} catch (Exception e) {
				gui.addMessageIn("Listening Thread: failed to listen :\n"+e.getMessage());
			}
		}		
	}
	
	private void setListener(){
		if (gui != null){
			gui.startService.setOnAction(new EventHandler<ActionEvent>() {				
				@Override
				public void handle(ActionEvent event) {
					startServiceAction();
				}
			});	
			gui.getIpList().setOnMouseClicked(new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent click) {
			        if (click.getClickCount() == 2) {
			           select(gui.getIpList().getSelectionModel());		
			        }
			    }
			});			
		}		
	}
		
	
	private boolean isValidCluedoMsg(String msg){
		CluedoJSON json = new CluedoJSON(msg);
		CluedoProtokollChecker checker =
				new CluedoProtokollChecker(json);
		return checker.validate();
	}
	
	private String getNetworkMessage(String networkMes){
		CluedoJSON json = new CluedoJSON(networkMes);
		CluedoProtokollChecker checker =
				new CluedoProtokollChecker(json);
		checker.validate();
		StringBuffer sb = new StringBuffer();
		if (!checker.isValid()){
			ArrayList<String> errs = checker.getErrs();
			for (String s : errs) sb.append(s);
		}
		else 
			sb.append(json.toString());
		
		return sb.toString();
	}
	
	public void kill(){
		running = false;
	}
}
