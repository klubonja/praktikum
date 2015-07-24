package cluedoServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoNetworkLayer.CluedoPlayer;

public class ClientItem {
	
	String id;
	String nick;	
	String groupName;
	CluedoPlayer player;
	ArrayList<String> expansions;
	
	int gameId;
	
	Socket socket;
	InetAddress adress;
	
	
	public ClientItem(Socket s){
		socket = s;
		adress = socket.getInetAddress();
	}
	
	public Socket getSocket(){
		return socket;
	}
	
	public void setPlayer(CluedoPlayer player) {
		this.player = player;
	}
	
	public CluedoPlayer getPlayer() {
		return player;
	}
	
	public String getIpString(){
		return adress.toString();
	}
	
	public void setExpansions(ArrayList<String> expansion) {
		this.expansions = expansion;
	}
	
	public ArrayList<String> getExpansions() {
		return expansions;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public String getGroupName() {
		return groupName;
	}

	public int getGameId() {
		return gameId;
	}

	public void setAdress(InetAddress adress) {
		this.adress = adress;
	}

	public InetAddress getAdress(){
		return adress;
	}
	
	public boolean closingConnection(String msg){
		try {
			sendMsg(NetworkMessages.disconnectedMsg(msg));
			socket.close();
			return true;
		} catch (IOException e) {
			auxx.logsevere("client connection "+getAdress()+ "was not closed due to:\n"+ e.getMessage());
		}
		
		return false;
	}
	
	public void sendMsg(String msg){
		auxx.sendTCPMsg(socket, msg);
		System.out.println("message sent to "+getNick());
	}
}