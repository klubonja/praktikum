package cluedoClient;

import java.util.ArrayList;

public class ServerPool extends ArrayList<ServerItem> {
	
	public ServerPool() {
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
	
//	public ClientGameItem getGameById(int gameID,ServerItem server){
//		for (ServerItem s : this)
//			if (s == server) return s.getGameByGameID(gameID);
//		
//		return null;
//	}
}
