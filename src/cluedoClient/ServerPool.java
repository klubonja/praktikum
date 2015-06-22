package cluedoClient;

import java.util.ArrayList;

import staticClasses.aux;

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
				return false;
			}
		}
		
		return super.add(e);
		
	}
	
	public void sendToAll(String msg){
		for (ServerItem server: this)
			aux.sendTCPMsg(server.getSocket(), msg);
			
	}
	
	
	
//	public ClientGameItem getGameById(int gameID,ServerItem server){
//		for (ServerItem s : this)
//			if (s == server) return s.getGameByGameID(gameID);
//		
//		return null;
//	}
}
