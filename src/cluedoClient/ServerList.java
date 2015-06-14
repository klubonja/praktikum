package cluedoClient;

import java.util.ArrayList;

public class ServerList extends ArrayList<ServerItem> {
	
	public ServerList() {
		super();
	}
	
	@Override
	public boolean add(ServerItem e) {
		for (ServerItem se : this){
			if (se.getGroupName().equals(e.getGroupName())){
				se.setIp(e.getIp());
				se.setPort(e.getPort());
				return true;
			}
		}
		return super.add(e);
	}
}
