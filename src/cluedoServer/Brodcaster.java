package cluedoServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import cluedoNetworkGUI.CluedoClientGUI;
import enums.Config;

public class Brodcaster {
	byte[] buf;
	DatagramPacket packet;
	DatagramSocket socket = null;
	
	InetAddress groupAdress;
	int port;
	
	CluedoClientGUI gui;
	
	String broadcastMessage;
	
	public Brodcaster(String name,String hostip,CluedoClientGUI g) {
		try {
			gui = g;
			groupAdress = InetAddress.getByName(hostip);
			port = Config.BroadcastPort;
			socket = new DatagramSocket();
		}
		catch (Exception e) {
			gui.addMessageIn(e.getMessage());
		}	
	}
	
	public void setMsg(String msg){
		this.broadcastMessage= msg;
	}
	
	public boolean sendBrodcast(){
		try {
			buf = new byte[Config.networkBufferSize];
			buf = broadcastMessage.getBytes();
			packet = new DatagramPacket(buf, buf.length, groupAdress, port);
			socket.setBroadcast(true);
			socket.send(packet);
			gui.addMessageOut("Sending UDPMessage :"+broadcastMessage);
			return true;
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			gui.addMessageOut(e.getMessage());

		}
		
		return false;
	}

}
