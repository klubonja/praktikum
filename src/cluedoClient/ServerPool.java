package cluedoClient;

import java.io.IOException;
import java.util.ArrayList;

import staticClasses.auxx;
import cluedoNetworkLayer.CluedoGameClient;

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
	
	public ArrayList<CluedoGameClient> getGamesConnected(){
		ArrayList<CluedoGameClient> assocgames = new ArrayList<CluedoGameClient>();
		for (ServerItem server: this){
			assocgames.addAll(server.getGamesByNick(server.getMyNick()));
		}
		
		return assocgames;
	}
	
	
	public void sendToAll(String msg){
		for (ServerItem server: this)
			if (server.getSocket() != null)
				auxx.sendTCPMsg(server.getSocket(), msg);					
	}
	
	public ServerItem getServerItem(ServerItem server) {
		for (ServerItem si: this){
			if (si.groupName.equals(server.getGroupName()) &&
					si.getIpString().equals(server.getIpString()))
				return si;				
		}
		
		return null;
	}
	
	public boolean remove(ServerItem server){
		try {
			server.getSocket().close();
			return super.remove(server);
		} catch (IOException e) {
			auxx.logsevere("Closing Socket of Server"+server.getGroupName()+" failed", e);
		}
		return false;
	}
	
	
	
	
	
//	public ClientGameItem getGameById(int gameID,ServerItem server){
//		for (ServerItem s : this)
//			if (s == server) return s.getGameByGameID(gameID);
//		
//		return null;
//	}
}
