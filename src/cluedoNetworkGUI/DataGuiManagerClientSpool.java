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
			addNetworkActorToGui(server.getGroupName(),server.getIpString());
			int n = serverPool.size()-1;
			System.out.println("server added at"+n);
			return true;
		};
		
		return false;
	}
	
	public boolean removeServer(String msg,ServerItem server){
		if (serverPool.remove(server)){
			addMsgIn(msg );
			return true;
		}
		return false;
	}
	
	public ServerItem getServerByIndex(int index){
		return serverPool.get(index);
	}
	
	public ServerItem getServerByID(String name, String ip){
		for (ServerItem s: serverPool)
			if (s.getGroupName().equals(name) && s.getIpString().equals(ip))
				return s;
		return null;
	}
	
	
	
}
