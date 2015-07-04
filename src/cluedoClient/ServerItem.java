package cluedoClient;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import cluedoNetworkLayer.CluedoGameClient;
import cluedoServer.GameListClient;
import enums.ServerStatus;


public class ServerItem  {
	String groupName;
	InetAddress ip;
	int port;
	GameListClient gamesList;
	Socket socket;
	ServerStatus status;
	String myNick;
	
	public ServerItem(String groupName,InetAddress ip, int port) {
		this.groupName = groupName;
		this.ip = ip;
		this.port = port;
		gamesList = new GameListClient();
		status = ServerStatus.not_connected;
		
		
	}
	
	public ArrayList<CluedoGameClient> getGamesByNick(String nick){		
		ArrayList<CluedoGameClient> assocgames = new ArrayList<CluedoGameClient>();
		for (CluedoGameClient game: gamesList){
			if (game.hasNick(nick)){
				assocgames.add(game);
			}
		}		
		return assocgames;
	}
	
	public String getMyNick() {
		return myNick;
	}
	public void setMyNick(String myNick) {
		this.myNick = myNick;
	}
	
	public void setStatus(ServerStatus status) {
		this.status = status;
	}
	
	public ServerStatus getStatus() {
		return status;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public InetAddress getIp() {
		return ip;
	}
	
	public String getIpString() {
		return ip.toString();
	}
	
	public int getPort() {
		return port;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public ArrayList<CluedoGameClient> getGameList() {
		return gamesList;
	}
	
	public void addGame(CluedoGameClient cg){
		gamesList.add(cg);
	}
	
	public boolean addPlayerByGameID(int gameID, String color,String nick){
		CluedoGameClient game = getGameByGameID(gameID);
		if (nick.equals(myNick)){
			game.setMyNick(nick);
			return game.joinGame(color, nick);
		}
		return false;
	}
		
		
	public void addGames(ArrayList<CluedoGameClient> cg){
		for (CluedoGameClient c : cg)
			gamesList.add(c);
	}
	
	public void removeGame(CluedoGameClient cg){
		gamesList.remove(cg);
	}
	
	public boolean removeGameByID(int gameID){
		for (CluedoGameClient cg: gamesList)
			if (cg.getGameId() == gameID)
				return gamesList.remove(cg);
		
		return false;
	}
	
	public CluedoGameClient getGameByGameID(int gameID){
		return gamesList.getGameByGameID(gameID);
	}
	
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public boolean removePlayerFromGames(String nickID){
		return gamesList.leaveAllGames(nickID);
	}

	
}
