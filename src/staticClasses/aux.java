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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;

import cluedoNetworkGUI.CluedoClientGUI;
import enums.Persons;

public abstract class aux {
	
	public static final Logger log = Logger.getLogger("mydearliittleloger");
	
	public static final void logsevere(String msg){
		log.log(Level.SEVERE, msg);
	}
	
	public static final void logsevere(String msg,Throwable e){
		log.log(Level.SEVERE, msg, e);
	}
	
	public static final void loginfo(String msg){
		log.log(Level.INFO, msg);
	}
	
	public static final void logfine(String msg){
		log.log(Level.FINE, msg);
	}
	
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
			String msg = new String (buffer, 0, charCount);			
			aux.log.log(Level.INFO,"RECEIVED : "+ msg);
			
			return msg;
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
			 aux.log.log(Level.INFO,"SENT : "+ msg);
		}
		catch (IOException e){
			e.printStackTrace();
		}			
	}
	
	public static boolean login(CluedoClientGUI gui,String servername,Socket socket){
		String[] loginData = gui.loginPrompt("Login to Server: "+servername);
		String msg;
		
//		String[] loginData = new String[]{"",""};
//		while (loginData[0].equals("") || loginData[1].equals(""))
//			loginData = gui.loginPrompt("Login to Server: "+dataGuiManager.getServer().getGroupName());
//		
		try {
			msg = NetworkMessages.loginMsg(loginData[0],loginData[1]);

		} catch (Exception e) {
			msg = null;
		}
		if (msg == null)	return false;

		aux.sendTCPMsg(socket,msg);
		return true;
	}
	
	public static String getRandomString(int length){
		Random rand = new Random();
		StringBuffer sb = new StringBuffer();
		String cons = "qwrtzuopüsdfghjklöäyxcvbnm";
		for (int i = 0;i < length; i++)
			sb.append(cons.charAt(Math.abs(rand.nextInt()%cons.length())));			
		
	
		return sb.toString();		
	}
	
	public static String getRandomPerson(){
		Persons[] ps = Persons.values();
		Random rand = new Random();	
	
		return ps[Math.abs(rand.nextInt()%ps.length)].getColor();		
	}
}
