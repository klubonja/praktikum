package broadcast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import staticClasses.Config;
import staticClasses.auxx;
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
			auxx.logsevere("broadcasting init failed",e);
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
				return true;				
			} 
			catch (Exception e) {
				auxx.logsevere("broadcasting failed",e);
			}
		}
		else {
			auxx.loginfo("broadcastmessage not set");
		}
		
		
		return false;
	}

}
