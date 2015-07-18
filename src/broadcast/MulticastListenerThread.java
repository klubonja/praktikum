package broadcast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;

import staticClasses.Config;
import staticClasses.auxx;
import cluedoNetworkGUI.DataGuiManager;

public abstract class MulticastListenerThread extends Thread{
	MulticastSocket socket;
	DatagramPacket packet;
	InetAddress listenAdress;
	int port;
	
	byte[] buf;
	int bufSize;
	
	String answer;
	String expType;
	
	DataGuiManager dataGuiManager;
	
	boolean run;
	
	abstract void listen();
	
	
	
	public MulticastListenerThread(String answer, String expType, int port, DataGuiManager dgm,boolean run)  {
		super();
		this.answer = new String(answer);
		this.expType = new String(expType);
		try {
			socket = new MulticastSocket(null);
			SocketAddress a = new InetSocketAddress(port);
			socket.bind(a);
			socket.setLoopbackMode(true);
			bufSize = Config.NETWORK_BUFFER_SIZE;
			dataGuiManager = dgm;
			setListener();
			this.run = run;
			
		} 
		catch (Exception e) {
			auxx.logsevere("mutlicast client :"+e.getMessage());
		}	
	}
	
	@Override
	public void run(){
		while (run){
			try {
				listen();
			} catch (Exception e) {
				auxx.logsevere("listen to handshake failed",e);
			}
		}		
	}
	
	private void setListener(){}
	
	public void kill(){
		run = false;
	}
}
