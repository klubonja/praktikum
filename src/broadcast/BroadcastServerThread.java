package broadcast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import staticClasses.Config;
import cluedoNetworkGUI.CluedoServerGUI;


public class BroadcastServerThread extends Thread {
	
	boolean running;
	
	byte[] buf;
	DatagramPacket packet;
	DatagramSocket socket = null;
	
	InetAddress groupAdress;
	int port;
	
	CluedoServerGUI gui;
	
	String broadcastMessage;
	
	
	public BroadcastServerThread(String name,String targetIp,String msg,CluedoServerGUI g) {
		super(name);
		try {
			gui = g;
			groupAdress = InetAddress.getByName(targetIp);
			port = Config.BROADCAST_PORT;
			broadcastMessage = msg;
			socket = new DatagramSocket();
			running = true;
		}
		catch (Exception e) {
			killService();
		}		
	}
	
	@Override
	public void run(){
		while (running){
			try {
				buf = new byte[Config.NETWORK_BUFFER_SIZE];
				buf = broadcastMessage.getBytes();
				packet = new DatagramPacket(buf, buf.length, groupAdress, port);
				socket.setBroadcast(true);
				socket.send(packet);				
			} 
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			finally {
				try {
					gui.addMessageOut("Sending UDPMessage :"+broadcastMessage);
					sleep(Config.SECOND*Config.BROADCAST_INTERVAL);
					
				} catch (InterruptedException e2) {
					// TODO: handle exception
				}
			}
		}
	
		
	}
	
	public void setBroadCastMsg(String msg){
		broadcastMessage = new String(msg);
	}
	
	public void killService(){
		running = false;
	}
	
}
