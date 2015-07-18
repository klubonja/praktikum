package cluedoServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import staticClasses.Config;
import staticClasses.auxx;
import cluedoNetworkGUI.CluedoServerGUI;


public class MulticastServerThread extends Thread {
	
	boolean running;
	
	byte[] buf;
	DatagramPacket packet;
	DatagramSocket socket = null;
	
	InetAddress groupAdress;
	int port;
	
	CluedoServerGUI gui;
	
	String broadcastMessage;
	
	
	public MulticastServerThread(String name,String hostip,String bm,CluedoServerGUI g) throws Exception {
		super(name);
		try {
			gui = g;
			groupAdress = InetAddress.getByName(hostip);
			port = Config.BROADCAST_PORT;
			broadcastMessage = bm;
			running = true;
			socket = new DatagramSocket();
			running = true;
			
		} catch (Exception e) {
			killService();
		}
		
		
	}
	
	@Override
	public void run(){
		while (running){
			try {
				buf = new byte[265];
				buf = broadcastMessage.getBytes();
				packet = new DatagramPacket(buf, buf.length, groupAdress, port);
				socket.send(packet);				
			} 
			catch (Exception e) {
				auxx.logsevere(e.getMessage());
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
