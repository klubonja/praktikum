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
import cluedoNetworkGUI.CluedoClientGUI;
import enums.Config;

public class MulticastClientThread extends Thread{
	
	MulticastSocket socket;
	DatagramPacket packet;
	InetAddress listenAdress;
	int port;
	
	byte[] buf;
	int bufSize;
	boolean listening;
	
	ArrayList<ServerItem> serverList;
	
	CluedoClientGUI gui;
	
	public MulticastClientThread(ArrayList<ServerItem> sl,String name, int port, CluedoClientGUI g) {
		super(name);
		try {
			serverList = sl;
			socket = new MulticastSocket(null);
			SocketAddress a = new InetSocketAddress(port);
			socket.bind(a);
			bufSize = Config.networkBufferSize;
			gui = g;
			listening = true;
		} 
		catch (Exception e) {
			System.out.println("mutlicast client :"+e.getMessage());
			kill();		
		}	
	}
	
	@Override
	public void run(){
		while (listening){
			try {
				buf = new byte[bufSize];
				packet = new DatagramPacket(buf, buf.length);			
				socket.receive(packet);	
				String msg = new String (packet.getData());		
				CluedoJSON json = new CluedoJSON(msg);
				CluedoProtokollChecker checker = new CluedoProtokollChecker(new CluedoJSON(msg));
				if (checker.validate()) {
					JSONObject jsonMsg = new JSONObject(msg);
					gui.addIp(jsonMsg.getString("group"));
					gui.addMessageIn(msg);
					serverList.add(new ServerItem(jsonMsg.getString("group"),packet.getAddress(),jsonMsg.getInt("tcp port")));
				}
				else {
					gui.addMessageIn(packet.getAddress()+" sends invalid Messages : \n"+checker.getErrString());
				}
			} 
			catch (Exception e) {
				gui.addMessageIn(e.getMessage());
			}
			
			finally {
				try {
					sleep(Config.SECOND*Config.BroadcastInterval);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void kill(){
		listening = false;
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
