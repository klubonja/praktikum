package cluedoServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import staticClasses.aux;
import staticClasses.NetworkMessages;

public class ClientItem {
	
	String id;
	String nick;	
	String groupName;
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
			System.out.println("client connection "+getAdress()+ "was not closed due to:\n"+ e.getMessage());
		}
		
		return false;
	}
	
	public void sendMsg(String msg){
//		try {
//			 PrintWriter out = new PrintWriter(
//					   new BufferedWriter(new OutputStreamWriter(
//					        socket.getOutputStream(), StandardCharsets.UTF_8)), true);
//			 out.print(msg);
//			 out.flush();
//		}
//		catch (IOException e){
//			System.out.println(e.getMessage());
//		}	
		
		aux.sendTCPMsg(socket, msg);
	}
}