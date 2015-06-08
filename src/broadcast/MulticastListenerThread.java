package broadcast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.util.ArrayList;

import json.CluedoJSON;
import json.CluedoProtokollChecker;

import org.json.JSONObject;

import cluedoClient.ServerItem;
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
	
	abstract void listen();
	
	
	public MulticastListenerThread(String answer, String expType, int port, CluedoNetworkGUI g)  {
		super();
		this.answer = new String(answer);
		this.expType = new String(expType);
		try {
			socket = new MulticastSocket(null);
			SocketAddress a = new InetSocketAddress(port);
			socket.bind(a);
			bufSize = Config.networkBufferSize;
			gui = g;
		} 
		catch (Exception e) {
			System.out.println("mutlicast client :"+e.getMessage());
		}	
	}
	
	@Override
	public void run(){
		try {
			listen();
		} catch (Exception e) {
			// TODO: handle exception
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
}
