package cluedoServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

class ClientItem {
	
	String id;
	String nick;	
	String groupName;
	
	int gameId;
	
	Socket socket;
	InetAddress adress;
	
	
	ClientItem(Socket s){
		socket = s;
		adress = socket.getInetAddress();
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

	public String getAdress(){
		return socket.getInetAddress().getHostAddress();
	}
	
	public void sendMsg(String msg){
		try {
			 PrintWriter out = new PrintWriter(
					   new BufferedWriter(new OutputStreamWriter(
					        socket.getOutputStream(), StandardCharsets.UTF_8)), true);
			 out.print(msg);
			 out.flush();
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}	
	}
}