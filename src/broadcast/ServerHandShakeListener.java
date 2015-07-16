package broadcast;

import java.net.DatagramPacket;
import java.net.InetAddress;

import json.CluedoJSON;
import json.CluedoProtokollChecker;

import org.json.JSONObject;

import staticClasses.auxx;
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
				dataGuiManager.addServer(
						new ServerItem(
								checker.getMessage().getString("group"),
								packet.getAddress(),
								checker.getMessage().getInt("tcp port")
								),
						"not logged in"
						
						);
			}
			else if (errcode == NetworkHandhakeCodes.TYPEOK_MSGERR){
				//dataGuiManager.addMsgIn(ip.toString()+" sends invalid Messages : \n"+checker.getErrString());
			}
			else if (errcode == NetworkHandhakeCodes.TYPEIGNORED){
				//dataGuiManager.addMsgIn(ip.toString()+" is client and is ignored : \n"+checker.getErrString());			
			}
		} 
		catch (Exception e) {
			auxx.logsevere("server handshake error",e);
		}		
	}
	
	
}
