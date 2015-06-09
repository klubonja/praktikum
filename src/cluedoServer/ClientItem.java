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