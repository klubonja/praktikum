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

public class MulticastListenerThread extends Thread{
	MulticastSocket socket;
	DatagramPacket packet;
	InetAddress listenAdress;
	int port;
	
	byte[] buf;
	int bufSize;
	
	String answer;
	String role;
	
	ArrayList<ServerItem> serverList;
	
	CluedoNetworkGUI gui;
	
	public MulticastListenerThread(ArrayList<ServerItem> sl,String role, int port, CluedoNetworkGUI g)  {
		super(role);
		role = new String(role);
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
	
	@Override
	public void run(){
		try {
			if (role.equals("server")) listenForClientHandshake();
			else if (role.equals("client")) listenForServerHandshake();
		} catch (Exception e) {
			// TODO: handle exception
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
			if (checker.validateExpectedType("udp client")) {				
				JSONObject answer = new JSONObject();
				answer.put("type", "udp server");
				answer.put("group", Config.GroupName);
				answer.put("tcp port", Config.TCPport);
				
				Brodcaster bc = new Brodcaster(Config.BroadcastIp, gui);
				bc.setMsg(answer.toString());
				bc.sendBrodcast();			
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
