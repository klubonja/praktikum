package cluedoClient;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import staticClasses.auxx;
import cluedoNetworkLayer.CluedoGameClient;
import enums.ServerStatus;


public class ServerItem  {
	String groupName;
	InetAddress ip;
	int port;
	GameListClient gamesList;
	ArrayList<String> clientNicks;
	Socket socket;
	ServerStatus status;
	String myNick;
	ArrayList<String> chatProtokoll;
	ArrayList<String> chatTs;
	
	public ServerItem(String groupName,InetAddress ip, int port) {
		this.groupName = groupName;
		this.ip = ip;
		this.port = port;
		gamesList = new GameListClient();
		status = ServerStatus.not_connected;
		chatProtokoll = new ArrayList<String>();
		chatTs = new ArrayList<String>();
		clientNicks = new ArrayList<String>();		
	}
	
	public void setClientNicks(ArrayList<String> newClientNicks){
		clientNicks = newClientNicks;
	}
	
	public boolean addClient(String clientnick){
		if (!clientNicks.contains(clientnick))
				return clientNicks.add(clientnick);
		return false;
	}
	
	public boolean removeClient(String clientnick){
		for (String s: clientNicks)
			if (s.equals(clientnick))
				return clientNicks.remove(s);
		return false;
	}
	
	public ArrayList<String> getClientNicks() {
		return clientNicks;
	}
	
	public ArrayList<CluedoGameClient> getGamesByNick(String nick){		
		ArrayList<CluedoGameClient> assocgames = new ArrayList<CluedoGameClient>();
		for (CluedoGameClient game: gamesList){
			if (game.hasPlayerConnectedByNick(nick)){
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
	
	public void killAllGames(){
		for (CluedoGameClient cg: gamesList){
			cg.kill();
			cg = null;
		}
			
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
	
	public String getChat() {
		StringBuffer chat = new StringBuffer("");
		for (int i = 0; i < chatProtokoll.size(); i++)
			chat.append(chatTs.get(i)+" : "+chatProtokoll.get(i)+"\n");
		
		return chat.toString();
	}
	
	public void addChatMsg(String msg,String ts){
		chatProtokoll.add(msg);
		chatTs.add(ts);
	}
	
	public void sendMsg(String msg){
		auxx.sendTCPMsg(getSocket(), msg);
	}	
}
