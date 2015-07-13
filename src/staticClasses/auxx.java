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
import javafx.scene.paint.Color;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
		//log.setLevel(level);
		ConsoleHandler newch = new ConsoleHandler();
		newch.setLevel(level);
		log.addHandler(newch);
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
	
	public static ArrayList<String> getTCPMessages(Socket s){
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(s.getInputStream(),StandardCharsets.UTF_8));
			char[] buffer = new char[Config.MESSAGE_BUFFER];
			int readpos = br.read(buffer,0,Config.MESSAGE_BUFFER);
			String msg = new String (buffer,0,readpos);	
			
			if (readpos != -1 ){				
				loginfo("RECEIVED : <MSG START> "+ msg +" <MSGS END>");
				String[] rawmsgs = msg.split(Config.TCP_MESSAGE_DELIMITER_REGEX); //removing and splitting along newlines
				return handledTCPMessages(rawmsgs, s);
			}
			else {
				logsevere("readpos : "+readpos+" network stream closed");	
				throw new Exception("Socket close");
			}			
		} 
		catch (Exception e) {
			logsevere("RECEIVE failed : ", e);
	    }
		return null;
		
	}
	
	public static ArrayList<String> handledTCPMessages(String[] jsonSource, Socket s){
		ArrayList<String> handled = new ArrayList<String>();
		for (int i = 0; i < jsonSource.length-1; i++){			
			JSONObject newjson = new JSONObject(jsonSource[i]);
			handled.add(jsonSource[i]);
			loginfo("json msg OK");
		}
		if (!jsonSource[jsonSource.length-1].equals("")){
			try {
				JSONObject potentiallyIncomplete = new JSONObject(jsonSource[jsonSource.length-1]);
				handled.add(jsonSource[jsonSource.length-1]);
				loginfo("last json msg OK");
			}
			catch (JSONException e){
				logsevere("last json msg NOT OK");
				ArrayList<String> sucList = getTCPMessages(s);
				logsevere("goiing into recursion");
				String fixedJSONSource = jsonSource[jsonSource.length-1].concat(sucList.get(0));
				try {
					JSONObject tobefixed = new JSONObject(fixedJSONSource);
					sucList.set(0, fixedJSONSource);
				}
				catch (JSONException e1){
					loginfo("fixing network message failed, dropping msg : \n"+ fixedJSONSource);					
				}
				handled.addAll(sucList);	
			}
		}		
		return  handled;
	}
	
	public static boolean sendTCPMsg(Socket socket,String msg){
		String message = msg+Config.TCP_MESSAGE_DELIMITER;
		try {
			PrintWriter out = new PrintWriter(
					   new BufferedWriter(new OutputStreamWriter(
					        socket.getOutputStream(), StandardCharsets.UTF_8)), true);
			 out.print(message);
			 out.flush();	
			 loginfo("SENT : "+ message);
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
	
	public static String getRandomPersonColorString(){
		Persons[] ps = Persons.values();
		Random rand = new Random();	
	
		return ps[Math.abs(rand.nextInt()%ps.length)].getColor();		
	}
	
	public static Persons getRandomPerson(){
		Persons[] ps = Persons.values();
		Random rand = new Random();	
	
		return ps[Math.abs(rand.nextInt()%ps.length)];
	}
	
	 public static Color getRandFarbe(){
		 return getRandomPerson().getFarbe();
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
