package staticClasses;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.json.JSONArray;

public abstract class Methods {
	
	
	public static ArrayList<String> makeConjunction(String[] sa1,JSONArray sa2JSON){
		ArrayList<String> res = new ArrayList<String>();
		
		for (String s1 : sa1)
			for (Object s2o : sa2JSON)
				if (s1.equals(s2o.toString()))
					res.add(s1);
		
		
		return res;
	}
	
	public static String getTCPMessage(Socket s){
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(s.getInputStream(),StandardCharsets.UTF_8));
			char[] buffer = new char[Config.MESSAGE_BUFFER];
			int charCount = br.read(buffer,0,Config.MESSAGE_BUFFER);
			
			return new String (buffer, 0, charCount);			
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
	    }		
	}
	
	public static void sendTCPMsg(Socket socket,String msg){
		try {
			PrintWriter out = new PrintWriter(
					   new BufferedWriter(new OutputStreamWriter(
					        socket.getOutputStream(), StandardCharsets.UTF_8)), true);
			 out.print(msg);
			 out.flush();	
		}
		catch (IOException e){
			e.printStackTrace();
		}			
	}		
}
