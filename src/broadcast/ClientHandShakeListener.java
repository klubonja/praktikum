package broadcast;

import java.net.DatagramPacket;
import java.net.InetAddress;

import json.CluedoJSON;
import json.CluedoProtokollChecker;

import org.json.JSONObject;

import staticClasses.Config;
import staticClasses.auxx;
import cluedoNetworkGUI.DataGuiManagerServer;
import enums.NetworkHandhakeCodes;

public class ClientHandShakeListener extends MulticastListenerThread {
	
	String[] ignoredTypes = {"udp server"};
	public ClientHandShakeListener(String answer,String expType, int port,DataGuiManagerServer dgm,boolean run) {
		super(answer,expType, port, dgm,run);
		
	}
	
	
	@Override
	void listen() {
		try {
			buf = new byte[bufSize];
			packet = new DatagramPacket(buf, buf.length);			
			socket.receive(packet);	
			String msg = new String (packet.getData());	
			InetAddress ip = packet.getAddress();
			CluedoProtokollChecker checker = new CluedoProtokollChecker(new CluedoJSON(new JSONObject(msg)));
			NetworkHandhakeCodes errcode = checker.validateExpectedType(expType,ignoredTypes);
			
			if (errcode == NetworkHandhakeCodes.OK) {
				//dataGuiManager.addMsgIn(ip.toString()+" sends handshake");
				Multicaster bc = new Multicaster(Config.BROADCAST_WILDCARD_IP, dataGuiManager,answer);
				bc.sendBrodcast();			
			}
			else if (errcode == NetworkHandhakeCodes.TYPEOK_MSGERR){
				dataGuiManager.addMsgIn(ip.toString()+" sends invalid Messages : \n"+checker.getErrString());
			}
			else if (errcode == NetworkHandhakeCodes.TYPEIGNORED){
				//dataGuiManager.addMsgIn(ip.toString()+" is server and is ignored");
			}
			else {
				//dataGuiManager.addMsgIn("TODO : unhandled incoming : \n" + msg);
			}			
			
		} 
		catch (Exception e) {
			//dataGuiManager.addMsgIn(e.getMessage());
			auxx.logsevere("client handshake error", e);
		}
		
	}

}
