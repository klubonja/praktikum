package broadcast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javafx.application.Platform;
import cluedoNetworkGUI.CluedoNetworkGUI;
import enums.Config;

public class Multicaster {
	byte[] buf;
	DatagramPacket packet;
	MulticastSocket socket = null;
	
	InetAddress groupAdress;
	int port;
	
	CluedoNetworkGUI gui;
	
	String broadcastMessage;
	
	public Multicaster(String targetIp,CluedoNetworkGUI g,String msg) {
		try {
			gui = g;
			groupAdress = InetAddress.getByName(targetIp);
			port = Config.BroadcastPort;
			
			socket = new MulticastSocket();
			socket.setBroadcast(true);
			socket.setLoopbackMode(false);
			broadcastMessage = msg;
		}
		catch (Exception e) {
			gui.addMessageIn(e.getMessage());
		}	
	}
	
	public void setMsg(String msg){
		this.broadcastMessage = msg;
	}
	
	public boolean sendBrodcast(){
		if (broadcastMessage != null){
			try {
				buf = new byte[Config.networkBufferSize];
				buf = broadcastMessage.getBytes();
				packet = new DatagramPacket(buf, buf.length, groupAdress, port);				
				socket.send(packet);
				
				Platform.runLater(() -> {
					gui.addMessageOut("Sending UDPMessage :"+broadcastMessage);
				});		
				
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
