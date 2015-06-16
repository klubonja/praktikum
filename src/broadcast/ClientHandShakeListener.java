package broadcast;

import java.net.DatagramPacket;
import java.net.InetAddress;

import javafx.application.Platform;
import json.CluedoJSON;
import json.CluedoProtokollChecker;

import org.json.JSONObject;

import cluedoNetworkGUI.CluedoServerGUI;
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
//				Platform.runLater(() -> {
//					gui.addMessageIn(ip.toString()+" sends handshake");
//				});
				dataGuiManager.addMsgIn(ip.toString()+" sends handshake");
				Multicaster bc = new Multicaster(packet.getAddress().getLocalHost().getHostAddress(), dataGuiManager,answer);
				bc.sendBrodcast();			
			}
			else if (errcode == NetworkHandhakeCodes.TYPEOK_MESERR){
//				Platform.runLater(() -> {
//					gui.addMessageIn(ip.toString()+" sends invalid Messages : \n"+checker.getErrString());
//				});	
				dataGuiManager.addMsgIn(ip.toString()+" sends invalid Messages : \n"+checker.getErrString());
			}
			else if (errcode == NetworkHandhakeCodes.TYPEIGNORED){
//				Platform.runLater(() -> {
//					gui.addMessageIn(ip.toString()+" is server and is ignored");
//				});	
				dataGuiManager.addMsgIn(ip.toString()+" is server and is ignored");
			}
			else {
//				Platform.runLater(() -> {
//					gui.addMessageIn("TODO : unhandled incoming : \n" + msg);
//				});
				dataGuiManager.addMsgIn("TODO : unhandled incoming : \n" + msg);
			}
			
			
		} 
		catch (Exception e) {
			dataGuiManager.addMsgIn(e.getMessage());
			e.printStackTrace();
		}
		
	}

}
