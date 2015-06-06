package broadcast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.util.ArrayList;

import json.CluedoJSON;
import json.CluedoProtokollChecker;
import cluedoClient.ServerItem;
import cluedoNetworkGUI.CluedoClientGUI;
import enums.Config;

public class MulticastListener {
	MulticastSocket socket;
	DatagramPacket packet;
	InetAddress listenAdress;
	int port;
	
	byte[] buf;
	int bufSize;
	
	ArrayList<ServerItem> serverList;
	
	CluedoClientGUI gui;
	
	public MulticastListener(ArrayList<ServerItem> sl,String name, int port, CluedoClientGUI g) {
		try {
			serverList = sl;
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
		
	public void listenForServerHandshake(){
		try {
			buf = new byte[bufSize];
			packet = new DatagramPacket(buf, buf.length);			
			socket.receive(packet);	
			String msg = new String (packet.getData());		
			CluedoProtokollChecker checker = new CluedoProtokollChecker(new CluedoJSON(msg));
			if (checker.validate()) {
				gui.addIp(checker.getMessage().getString("group"));
				gui.addMessageIn(msg);
				serverList.add(new ServerItem(checker.getMessage().getString("group"),packet.getAddress(),checker.getMessage().getInt("tcp port")));
			}
			else {
				gui.addMessageIn(packet.getAddress()+" sends invalid Messages : \n"+checker.getErrString());
			}
		} 
		catch (Exception e) {
			gui.addMessageIn(e.getMessage());
		}
	}
	
	public void listenForClientHandshake(){
		try {
			buf = new byte[bufSize];
			packet = new DatagramPacket(buf, buf.length);			
			socket.receive(packet);	
			String msg = new String (packet.getData());		
			CluedoProtokollChecker checker = new CluedoProtokollChecker(new CluedoJSON(msg));
			if (checker.validateExpectedType("udp _client")) {				
				
				serverList.add(new ServerItem(checker.getMessage().getString("group"),packet.getAddress(),checker.getMessage().getInt("tcp port")));
			}
			else {
				gui.addMessageIn(packet.getAddress()+" sends invalid Messages : \n"+checker.getErrString());
			}
		} 
		catch (Exception e) {
			gui.addMessageIn(e.getMessage());
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
