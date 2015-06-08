package broadcast;

import java.net.DatagramPacket;
import java.net.InetAddress;

import javafx.application.Platform;

import org.json.JSONObject;

import json.CluedoJSON;
import json.CluedoProtokollChecker;
import cluedoNetworkGUI.CluedoServerGUI;
import enums.Config;
import enums.NetworkHandhakeCodes;

public class ClientHandShakeListener extends MulticastListenerThread {
	
	String[] ignoredTypes = {"udp server"};
	public ClientHandShakeListener(String answer,String expType, int port, CluedoServerGUI g) {
		super(answer,expType, port, g);
		
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
				Platform.runLater(() -> {
					gui.addMessageIn(ip.toString()+" says :"+msg);
				});
				Multicaster bc = new Multicaster(Config.BROADCAST_WILDCARD_IP, gui,answer);
				bc.sendBrodcast();			
			}
			else if (errcode == NetworkHandhakeCodes.TYPEOK_MESERR){
				Platform.runLater(() -> {
					gui.addMessageIn(ip.toString()+" sends invalid Messages : \n"+checker.getErrString());
				});
				
			}
			else if (errcode == NetworkHandhakeCodes.MESSOK_TYPEIGNORED){
				Platform.runLater(() -> {
					gui.addMessageIn(ip.toString()+" is server and is ignored : \n"+checker.getAllString());
				});				
			}
		} 
		catch (Exception e) {
			gui.addMessageIn(e.toString());
			e.printStackTrace();
		}
		
	}

}
