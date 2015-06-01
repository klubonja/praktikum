package cluedoServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

class Client {
	String id;
	Socket socket;
	
	Client(int i, Socket s){
		id = "Client"+ i;
		socket = s;
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
			 //outToClient.writeUTF(msg);
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}	
	}
}