package broadcast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import staticClasses.Config;
import cluedoNetworkGUI.DataGuiManager;

public class Multicaster {
	byte[] buf;
	DatagramPacket packet;
	MulticastSocket socket = null;
	
	InetAddress groupAdress;
	int port;
	
	DataGuiManager dataGuiManager;
	
	String broadcastMessage;
	
	public Multicaster(String targetIp,DataGuiManager dm,String msg) {
		try {
			dataGuiManager = dm;
			groupAdress = InetAddress.getByName(targetIp);
			port = Config.BROADCAST_PORT;
			
			socket = new MulticastSocket();
			socket.setBroadcast(true);
			socket.setLoopbackMode(true);
			broadcastMessage = msg;
		}
		catch (Exception e) {
			dataGuiManager.addMsgIn(e.getMessage());
		}	
	}
	
	public void setMsg(String msg){
		this.broadcastMessage = msg;
	}
	
	public boolean sendBrodcast(){
		if (broadcastMessage != null){
			try {
				buf = new byte[Config.NETWORK_BUFFER_SIZE];
				buf = broadcastMessage.getBytes();
				packet = new DatagramPacket(buf, buf.length, groupAdress, port);				
				socket.send(packet);
									
				dataGuiManager.addMessageOut("Sending UDPMessage :"+broadcastMessage);				
				return true;				
			} 
			catch (Exception e) {
				System.out.println(e.getMessage());	
				dataGuiManager.addMsgIn(e.getMessage());
			}
		}
		else {
			dataGuiManager.addMsgIn("single Brodcast msg not set");
		}
		
		
		return false;
	}

}
