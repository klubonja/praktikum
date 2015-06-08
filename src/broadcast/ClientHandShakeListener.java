package broadcast;

import java.net.DatagramPacket;
import json.CluedoJSON;
import json.CluedoProtokollChecker;
import cluedoNetworkGUI.CluedoServerGUI;
import enums.Config;

public class ClientHandShakeListener extends MulticastListenerThread {
	

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
			CluedoProtokollChecker checker = new CluedoProtokollChecker(new CluedoJSON(msg));
			if (checker.validateExpectedType(expType)) {				
				Brodcaster bc = new Brodcaster(Config.BroadcastIp, gui,answer);
				bc.sendBrodcast();			
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
