package cluedoNetworkGUI;


import cluedoClient.ServerItem;
import cluedoClient.ServerPool;

public class DataGuiManagerClientSpool extends DataGuiManager{
	
	ServerPool serverPool;
	
	public DataGuiManagerClientSpool(CluedoClientGUI gui,ServerPool spool) {
		super(gui);
		serverPool = spool;
	}
	
	public boolean addServer(ServerItem server){
		if (serverPool.add(server)){
			addMsgIn("opened connection to "+server.getGroupName()+" on : "+ server.getIpString());
			addIp(server.getGroupName());
			int n = serverPool.size()-1;
			System.out.println("server added at"+n);
			return true;
		};
		
		return false;
	}
	
	public boolean removeServer(String msg,ServerItem server){
		addMsgIn(msg );
		return serverPool.remove(server);
	}
	
	public ServerItem getServerByIndex(int index){
		return serverPool.get(index);
	}
	
	
	
	
	
}
