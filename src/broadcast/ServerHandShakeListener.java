package broadcast;

import java.net.DatagramPacket;
import java.util.ArrayList;

import json.CluedoJSON;
import json.CluedoProtokollChecker;
import cluedoClient.ServerItem;
import cluedoNetworkGUI.CluedoClientGUI;

public class ServerHandShakeListener extends MulticastListenerThread{
	
	ArrayList<ServerItem> serverList;
	
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
			CluedoProtokollChecker checker = new CluedoProtokollChecker(new CluedoJSON(msg));
			if (checker.validate()) {
				gui.addIp(checker.getMessage().getString("group"));
				gui.addMessageIn(msg);
				serverList.add(new ServerItem(checker.getMessage().getString("group"),packet.getAddress(),checker.getMessage().getInt("tcp port")));
			}
			else {
				gui.addMessageIn(packet.getAddress()+" sends invalid Messages : \n"+checker.getErrString());
			}
		} 
		catch (Exception e) {
			gui.addMessageIn(e.getMessage());
		}		
	}
	
	
}
