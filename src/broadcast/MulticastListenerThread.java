package broadcast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.util.ArrayList;

import json.CluedoJSON;
import json.CluedoProtokollChecker;
import staticClasses.Config;
import cluedoNetworkGUI.CluedoNetworkGUI;
import cluedoNetworkGUI.DataGuiManager;

public abstract class MulticastListenerThread extends Thread{
	MulticastSocket socket;
	DatagramPacket packet;
	InetAddress listenAdress;
	int port;
	
	byte[] buf;
	int bufSize;
	
	String answer;
	String expType;
	
	DataGuiManager dataGuiManager;
	
	boolean run;
	
	abstract void listen();
	
	
	
	public MulticastListenerThread(String answer, String expType, int port, DataGuiManager dgm,boolean run)  {
		super();
		this.answer = new String(answer);
		this.expType = new String(expType);
		try {
			socket = new MulticastSocket(null);
			SocketAddress a = new InetSocketAddress(port);
			socket.bind(a);
			socket.setLoopbackMode(true);
			bufSize = Config.NETWORK_BUFFER_SIZE;
			dataGuiManager = dgm;
			setListener();
			this.run = run;
			
		} 
		catch (Exception e) {
			System.out.println("mutlicast client :"+e.getMessage());
		}	
	}
	
	@Override
	public void run(){
		while (run){
			try {
				listen();
			} catch (Exception e) {
				//gui.addMessageIn("Listening Thread: failed to listen :\n"+e.getMessage());
				dataGuiManager.addMsgIn("Listening Thread: failed to listen :\n"+e.getMessage());
			}
		}		
	}
	
	private void setListener(){
		
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
		run = false;
	}
}
