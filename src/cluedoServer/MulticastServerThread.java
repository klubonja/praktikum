package cluedoServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import cluedoNetworkGUI.CluedoServerGUI;
import enums.Config;


public class MulticastServerThread extends Thread {
	
	boolean running;
	
	byte[] buf;
	DatagramPacket packet;
	DatagramSocket socket = null;
	
	InetAddress groupAdress;
	int port;
	
	CluedoServerGUI gui;
	
	String broadcastMessage;
	
	
	public MulticastServerThread(String name,String hostip,String msg,CluedoServerGUI g) {
		super(name);
		try {
			gui = g;
			groupAdress = InetAddress.getByName(hostip);
			port = Config.BroadcastPort;
			broadcastMessage = msg;
			running = true;
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
				buf = new byte[Config.networkBufferSize];
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
					sleep(Config.SECOND*Config.BroadcastInterval);
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
