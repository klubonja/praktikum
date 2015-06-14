package broadcast;

import java.net.DatagramPacket;
import java.net.InetAddress;

import javafx.application.Platform;
import json.CluedoJSON;
import json.CluedoProtokollChecker;

import org.json.JSONObject;

import cluedoClient.Client;
import cluedoClient.ServerItem;
import cluedoClient.ServerList;
import cluedoNetworkGUI.CluedoClientGUI;
import enums.NetworkHandhakeCodes;

public class ServerHandShakeListener extends MulticastListenerThread{
	
	Client parent;
	ServerList serverList;
	String[] ignoredTypes = {"udp client"};
	
	
	public ServerHandShakeListener(ServerList sl,String answer, String expType, int port, CluedoClientGUI g,Client client,boolean run) {
		super(answer, expType, port, g,run);
		parent = client;
		serverList = sl;
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
					gui.addIp(checker.getMessage().getString("group"));
					gui.addMessageIn(ip.toString()+" says \n"+msg);
				});
				serverList.add(new ServerItem(checker.getMessage().getString("group"),packet.getAddress(),checker.getMessage().getInt("tcp port")));
			}
			else if (errcode == NetworkHandhakeCodes.TYPEOK_MESERR){
				Platform.runLater(() -> {
					gui.addMessageIn(ip.toString()+" sends invalid Messages : \n"+checker.getErrString());
				});
			}
			else if (errcode == NetworkHandhakeCodes.TYPEIGNORED){
				Platform.runLater(() -> {
					gui.addMessageIn(ip.toString()+" is client and is ignored : \n"+checker.getErrString());

				});
			gui.addMessageIn(msg);
				
			}
		} 
		catch (Exception e) {
			gui.addMessageIn(e.toString());
			e.printStackTrace();
		}		
	}
	
	
}
