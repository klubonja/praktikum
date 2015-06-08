package broadcast;

import java.net.DatagramPacket;
import java.util.ArrayList;

import org.json.JSONObject;

import json.CluedoJSON;
import json.CluedoProtokollChecker;
import cluedoClient.ServerItem;
import cluedoNetworkGUI.CluedoClientGUI;

public class ServerHandShakeListener extends MulticastListenerThread{
	
	ArrayList<ServerItem> serverList;
	String[] ignoredTypes = {"udp client"};
	
	public ServerHandShakeListener(ArrayList<ServerItem> sl,String answer, String expType, int port, CluedoClientGUI g) {
		super(answer, expType, port, g);
		serverList = sl;
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
				gui.addIp(checker.getMessage().getString("group"));
				gui.addMessageIn(packet.getAddress()+" says \n"+msg);
				serverList.add(new ServerItem(checker.getMessage().getString("group"),packet.getAddress(),checker.getMessage().getInt("tcp port")));
			}
			else if (errcode == 1){
				gui.addMessageIn(packet.getAddress()+" sends invalid Messages : \n"+checker.getErrString());
			}
			else if (errcode == 2){
				gui.addMessageIn(packet.getAddress()+" is client and is ignored : \n"+checker.getAllString());
			}
		} 
		catch (Exception e) {
			gui.addMessageIn(e.toString());
			e.printStackTrace();
		}		
	}
	
	
}
