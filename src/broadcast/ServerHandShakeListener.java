package broadcast;

import java.net.DatagramPacket;
import java.net.InetAddress;

import json.CluedoJSON;
import json.CluedoProtokollChecker;

import org.json.JSONObject;

import cluedoClient.Client;
import cluedoClient.ServerItem;
import cluedoClient.ServerPool;
import cluedoNetworkGUI.DataGuiManagerClientSpool;
import enums.NetworkHandhakeCodes;

public class ServerHandShakeListener extends MulticastListenerThread{
	
	Client parent;
	ServerPool serverList;
	String[] ignoredTypes = {"udp client"};
	
	DataGuiManagerClientSpool dataGuiManager;
	
	
	public ServerHandShakeListener(DataGuiManagerClientSpool dgm,String answer, String expType, int port, Client client,boolean run) {
		super(answer, expType, port, dgm, run);
		parent = client;
		dataGuiManager = dgm;
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
//					gui.addIp(checker.getMessage().getString("group"));
//					gui.addMessageIn(ip.toString()+" says \n"+msg);
//				});
				dataGuiManager.addServer(
						new ServerItem(
								checker.getMessage().getString("group"),
								packet.getAddress(),
								checker.getMessage().getInt("tcp port")
								)
						);
				//serverList.add(new ServerItem(checker.getMessage().getString("group"),packet.getAddress(),checker.getMessage().getInt("tcp port")));
			}
			else if (errcode == NetworkHandhakeCodes.TYPEOK_MESERR){
//				Platform.runLater(() -> {
//					gui.addMessageIn(ip.toString()+" sends invalid Messages : \n"+checker.getErrString());
//				});
				dataGuiManager.addMsgIn(ip.toString()+" sends invalid Messages : \n"+checker.getErrString());
			}
			else if (errcode == NetworkHandhakeCodes.TYPEIGNORED){
//				Platform.runLater(() -> {
//					gui.addMessageIn(ip.toString()+" is client and is ignored : \n"+checker.getErrString());
//
//				});
				dataGuiManager.addMsgIn(ip.toString()+" is client and is ignored : \n"+checker.getErrString());

				
			}
		} 
		catch (Exception e) {
			//gui.addMessageIn(e.toString());
			dataGuiManager.addMsgIn(e.toString());
			e.printStackTrace();
		}		
	}
	
	
}
