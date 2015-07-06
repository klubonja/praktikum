package staticClasses;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.TextArea;

import org.json.JSONArray;

import cluedoClient.ServerItem;
import cluedoNetworkGUI.CluedoClientGUI;
import enums.Persons;
import enums.Rooms;
import enums.Weapons;

public abstract class auxx {
	
	public static final Logger log = Logger.getLogger("mydearliittlelogger");
	public static final ConsoleHandler C_HANDLER = new ConsoleHandler();
	public static final void setLoggingLevel(Level level){
		log.setUseParentHandlers(false);
		log.setLevel(level);
		C_HANDLER.setLevel(level);
		log.addHandler(C_HANDLER);
	}
	
	public static long getMillis(){
		return  System.currentTimeMillis() % 1000;
	}
	public static final void logsevere(String msg){
		log.log(Level.SEVERE, getMillis()+" "+msg);
	}
	
	public static final void logsevere(String msg,Throwable e){
		log.log(Level.SEVERE, getMillis()+" "+ msg, e);
	}
	
	public static final void loginfo(String msg){
		log.log(Level.INFO,getMillis()+" "+ msg);
	}
	
	public static final void logfine(String msg){		
		log.log(Level.FINE,getMillis()+" "+ msg);
	}
	
	public static ArrayList<String> makeConjunction(String[] sa1,JSONArray sa2JSON){
		ArrayList<String> res = new ArrayList<String>();		
		for (String s1 : sa1)
			for (Object s2o : sa2JSON)
				if (s1.equals(s2o.toString()))
					res.add(s1);
		
		
		return res;
	}
	
	public static String now(){
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"); //2015-04-08T15:16:23.42"
		  Date now = new Date();
		  
		  return date.format(now);
	}
	
	public static String convertTs(String localDateTime){
		String[] datetimestring = localDateTime.split("T");
		return datetimestring[1];
		
		
//		try {
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss",  Locale.GERMAN);
//			LocalDate date = LocalDate.parse(localDateTime, formatter);
//			return date.toString();
//		} 
//		catch (DateTimeParseException e) {
//			logsevere("converting time failed :",e);
//			return localDateTime;
//		}
		
	}
	
	public static String[] getTCPMessages(Socket s){
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(s.getInputStream(),StandardCharsets.UTF_8));
			char[] buffer = new char[Config.MESSAGE_BUFFER];
			int charCount = br.read(buffer,0,Config.MESSAGE_BUFFER);
			logsevere("charcount : "+charCount+" from getTCPMesg");
			String msg = new String (buffer, 0, charCount);	
			logfine("RECEIVED : "+ msg);
			String[] msgs = msg.split(Config.TCP_MESSAGE_DELIMITER_REGEX);
//			if (msgs[msgs.length-1].equals("")) {
//				String[] msgsfinal = new String[msgs.length-1];
//				for (int i = 0; i < msgs.length-2; i++)
//					msgsfinal[i] = msgs[i];
//				return msgs;
//			}
			if (msgs.length == 0) return new String[]{""};
			return msgs;
		} 
		catch (Exception e) {
			logsevere("RECEIVE failed : ", e);
	    }	
		return null;
	}
	
	public static boolean sendTCPMsg(Socket socket,String msg){
		String message = msg+Config.TCP_MESSAGE_DELIMITER;
		try {
			PrintWriter out = new PrintWriter(
					   new BufferedWriter(new OutputStreamWriter(
					        socket.getOutputStream(), StandardCharsets.UTF_8)), true);
			 out.print(message);
			 out.flush();	
			 logfine("SENT : "+ message);
			 return true;
		}
		catch (IOException e ){
			logsevere("SEND failed : " + message, e);
			return false;
		}			
	}
	
	public static boolean login(CluedoClientGUI gui,ServerItem server){		
//		String[] loginData = new String[]{"",""};
//		while (loginData[0].equals("") || loginData[1].equals(""))
//			loginData = gui.loginPrompt("Login to Server: "+dataGuiManager.getServer().getGroupName());
//		
		try {
			String[] loginData = gui.loginPrompt("Login to Server: "+server.getGroupName());
			if (loginData[0] == null || loginData[1] == null) throw new Exception("some login prompt fields empty") ;
			String msg = NetworkMessages.loginMsg(loginData[0],loginData[1]);
			if (auxx.sendTCPMsg(server.getSocket(),msg)){
				server.setMyNick(loginData[0]);
				return true;
			}	
			return false;
		} 
		catch (Exception e) {
			logsevere("getting nick from propmpt failed"+ e.getMessage());
			return false;
		}		
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
	
	public static String getRandomWeapon(){
		Weapons[] ps = Weapons.values();
		Random rand = new Random();	
	
		return ps[Math.abs(rand.nextInt()%ps.length)].getName();		
	}
	
	public static String getRandomRoom(){
		Rooms[] ps = Rooms.values();
		Random rand = new Random();	
	
		return ps[Math.abs(rand.nextInt()%ps.length)].getName();		
	}
	
	public static ArrayList<String> jsonArrayToArrayList(JSONArray jsonarray){
		ArrayList<String> list = new ArrayList<String>();
		for (int i= 0; i < jsonarray.length(); i++)
			list.add(jsonarray.getString(i));
		System.out.println(list);
		
		return list;
	}
	
	public static int getRandInt(int min,int max){
		Random rint = new Random();		
		return rint.nextInt(Math.max(max-min + 1,0)) + min;				
	}
	
	public static void setStyleChatField(TextArea inputField, boolean focused){
		if (focused){
			inputField.setText("");
    		inputField.setStyle("-fx-text-fill: #000000;-fx-font-style: normal;");
		}
		else {
			inputField.setText("submit chatmsg with enter");
	    	inputField.setStyle("-fx-text-fill: #999999;-fx-font-style: italic;");
		}
		
	}
}
