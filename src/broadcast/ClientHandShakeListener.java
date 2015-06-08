package broadcast;

import java.net.DatagramPacket;

import org.json.JSONObject;

import json.CluedoJSON;
import json.CluedoProtokollChecker;
import cluedoNetworkGUI.CluedoServerGUI;
import enums.Config;

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
			CluedoProtokollChecker checker = new CluedoProtokollChecker(new CluedoJSON(new JSONObject(msg)));
			int errcode = checker.validateExpectedType(expType,ignoredTypes);
			if (errcode == 0) {	
				gui.addMessageIn(packet.getAddress()+" says :"+msg);

				Multicaster bc = new Multicaster(Config.BroadcastIp, gui,answer);
				bc.sendBrodcast();			
			}
			else if (errcode == 1){
				gui.addMessageIn(packet.getAddress()+" sends invalid Messages : \n"+checker.getErrString());
			}
			else if (errcode == 2){
				gui.addMessageIn(packet.getAddress()+" is server and is ignored : \n"+checker.getAllString());
			}
		} 
		catch (Exception e) {
			gui.addMessageIn(e.toString());
			e.printStackTrace();
		}
		
	}

}
