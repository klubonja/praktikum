package broadcast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import cluedoNetworkGUI.CluedoNetworkGUI;
import enums.Config;

public class Brodcaster {
	byte[] buf;
	DatagramPacket packet;
	DatagramSocket socket = null;
	
	InetAddress groupAdress;
	int port;
	
	CluedoNetworkGUI gui;
	
	String broadcastMessage;
	
	public Brodcaster(String targetIp,CluedoNetworkGUI g) {
		try {
			gui = g;
			groupAdress = InetAddress.getByName(targetIp);
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
		if (broadcastMessage != null){
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
		}
		else {
			gui.addMessageOut("single Brodcast msg not set");
		}
		
		
		return false;
	}

}
