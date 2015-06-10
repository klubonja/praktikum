package staticClasses;

import json.CluedoJSON;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class NetworkMessages {
	
	public static String udp_clientMsg(String group){
		CluedoJSON json = new CluedoJSON("udp client");
		json.put("group",group);
		
		return json.toString();
	}
	public static String udp_serverMsg(String group,int tcpport){
		CluedoJSON json = new CluedoJSON("udp server");
		json.put("group",group);
		json.put("tcp port",tcpport);
		
		return json.toString();
	}
	
	public static String loginMsg(String nick,String group){
		CluedoJSON json = new CluedoJSON("login");
		json.put("nickwer", nick);
		json.put("versionwe", Config.PROTOKOLL_VERSION);
		json.put("group", group);
		json.put("expansions", new JSONArray(Config.EXPANSIONS));		
		
		return json.toString();
	}
	
	public static String login_sucMsg(String[] nickArray,JSONArray gameArray){
		CluedoJSON json = new CluedoJSON("login successful");
		json.put("expansions", new JSONArray(Config.EXPANSIONS));
		json.put("game array", gameArray);
		json.put("nick array", nickArray);
		
		return json.toString();
	}
	
	public static String user_addedMsg(String nick){
		CluedoJSON json = new CluedoJSON("user added");
		json.put("nick", nick);
		
		return json.toString();
	}
	
	public static String disconnectMsg(){
		CluedoJSON json = new CluedoJSON("disconnect");
		
		return json.toString();
	}
	
	public static String disconnectedMsg(String msg){
		CluedoJSON json = new CluedoJSON("disconnected");
		json.put("message", msg);
		
		return json.toString();
	}
	
	public static String user_leftMsg(String nick){
		CluedoJSON json = new CluedoJSON("user left");
		json.put("nick", nick);
		
		return json.toString();
	}
	
	public static String statement(String person,String room,String weapon){
		JSONObject json = new JSONObject();
		json.put("person", person);
		json.put("weapon", weapon);
		json.put("room", room);
		
		return json.toString();
	}
	
	public static String game_info(int gameId,String gamestate,JSONArray playerInfos,JSONArray watchers,JSONArray personposes,JSONArray weaponposes){
		JSONObject json = new JSONObject();
		json.put("gameID",gameId);
		json.put("gamestate",gamestate);
		json.put("players", playerInfos);
		json.put("watchers", watchers);
		json.put("person positions", personposes);
		json.put("weapon positions", weaponposes);
		
		return json.toString();
	}
	
	public static String player_info(String nick,String color,String playerstate){
		JSONObject json = new JSONObject();
		json.put("nick", nick);
		json.put("color", color);
		json.put("playerstate", playerstate);
		
		return json.toString();
	}
	
	public static String player_pos(String person,String field){
		JSONObject json = new JSONObject();
		json.put("person", person);
		json.put("field", field);
		
		return json.toString();
	}
	
	public static String weapon_pos(String weapon,String field){
		JSONObject json = new JSONObject();
		json.put("weapon", weapon);
		json.put("field", field);
		
		return json.toString();
	}
	
	public static String okMsg(){
		CluedoJSON json = new CluedoJSON("OK");
		
		return json.toString();
	}
	
	public static String error_Msg(String msg){
		CluedoJSON json = new CluedoJSON("error");
		json.put("message", msg);
		
		return json.toString();
	}
	
	public static String chat_to_serverMsg(String msg,String timestamp){
		CluedoJSON json = new CluedoJSON("chat");
		json.put("message", msg);
		json.put("timestamp", timestamp);	
		
		return json.toString();
	}
	
	public static String chat_to_clientMsg(String msg,String timestamp,String sender){
		CluedoJSON json = new CluedoJSON("chat");
		json.put("sender", sender);
		json.put("message", msg);
		json.put("timestamp", timestamp);	
		
		return json.toString();
	}
	
	public static String create_gameMsg(String color){
		CluedoJSON json = new CluedoJSON("create game");
		json.put("color", color);
		
		return json.toString();
	}
	
	public static String game_createdMsg(JSONObject playerinfo, int gameID){
		CluedoJSON json = new CluedoJSON("game created");
		json.put("gameID", gameID);
		json.put("player", playerinfo);
		
		return json.toString();
	}
	
	public static String join_gameMsg(String color, int gameID){
		CluedoJSON json = new CluedoJSON("join game");
		json.put("gameID", gameID);
		json.put("color", color);
		
		return json.toString();
	}
	
	public static String player_addedMsg(JSONObject playerinfo, int gameID){
		CluedoJSON json = new CluedoJSON("player added");
		json.put("gameID", gameID);
		json.put("player", playerinfo);
		
		return json.toString();
	}
	
	public static String watch_gameMsg(int gameID){
		CluedoJSON json = new CluedoJSON("watch game");
		json.put("gameID", gameID);
		
		return json.toString();
	}
	
	public static String watcher_addedMsg(int gameID,String nick){
		CluedoJSON json = new CluedoJSON("watcher_added");
		json.put("gameID", nick);
		
		return json.toString();
	}
	
	public static String gameinfoMsg(JSONObject gameinfo){
		CluedoJSON json = new CluedoJSON("gameinfo");
		json.put("game", gameinfo);
		
		return json.toString();
	}
	
	public static String leave_gameMsg(int gameID){
		CluedoJSON json = new CluedoJSON("leave game");
		json.put("gameID", gameID);
		
		return json.toString();
	}
	
	public static String left_gameMsg(int gameID,String nick){
		CluedoJSON json = new CluedoJSON("left game");
		json.put("gameID", gameID);
		json.put("nick", nick);
		
		return json.toString();
	}
	
	public static String game_deletedMsg(int gameID){
		CluedoJSON json = new CluedoJSON("game deleted");
		json.put("gameID", gameID);
		
		return json.toString();
	}
	
	public static String start_gameMsg(int gameID){
		CluedoJSON json = new CluedoJSON("start game");
		json.put("gameID", gameID);
		
		return json.toString();
	}
	
	public static String player_cardsMsg(int gameID,String[] cards){
		CluedoJSON json = new CluedoJSON("player cards");
		json.put("gameID", gameID);
		json.put("cards", cards);		
		
		return json.toString();
	}
	
	public static String game_startedMsg(int gameID,String[] order){
		CluedoJSON json = new CluedoJSON("game started");
		json.put("order", order);
		json.put("gamestate", "started");
		json.put("gameID", gameID);		
		
		return json.toString();
	}
	
	public static String stateupdateMsg(int gameID,String nick,JSONObject playerstate){
		CluedoJSON json = new CluedoJSON("game started");
		json.put("nick", nick);
		json.put("playerstat", playerstate);
		json.put("gameID", gameID);		
		
		return json.toString();
	}
	
	public static String game_endedMsg(int gameID,String nick,JSONObject statement){
		CluedoJSON json = new CluedoJSON("game ended");
		json.put("nick", nick);
		json.put("statement", statement);
		json.put("gameID", gameID);		
		
		return json.toString();
	}
	
	public static String dice_resultMsg(int gameID,int[] result){
		CluedoJSON json = new CluedoJSON("game ended");
		json.put("result", result);
		json.put("gameID", gameID);		
		
		return json.toString();
	}
	
	public static String movedMsg(int gameID,JSONObject personpos){
		CluedoJSON json = new CluedoJSON("moved");
		json.put("person position", personpos);
		json.put("gameID", gameID);		
		
		return json.toString();
	}
	
	public static String suspicionMsg(int gameID,JSONObject statement){
		CluedoJSON json = new CluedoJSON("suspition");
		json.put("statement", statement);
		json.put("gameID", gameID);		
		
		return json.toString();
	}
	
	public static String disprovedMsg(int gameID,String nick,String card){
		CluedoJSON json = new CluedoJSON("disproved");
		json.put("nick", nick);
		json.put("gameID", gameID);		
		
		return json.toString();
	}
	
	public static String no_disproveMsg(int gameID){
		CluedoJSON json = new CluedoJSON("no disprove");
		json.put("gameID", gameID);		
		
		return json.toString();
	}
	
	public static String wrong_accusationMsg(int gameID,JSONObject statement){
		CluedoJSON json = new CluedoJSON("wrong accusation");
		json.put("gameID", gameID);	
		json.put("statement", statement);	
		
		return json.toString();
	}
	
	public static String roll_diceMsg(int gameID){
		CluedoJSON json = new CluedoJSON("roll dice");
		json.put("gameID", gameID);		
		
		return json.toString();
	}
	
	public static String moveMsg(int gameID,JSONObject field){
		CluedoJSON json = new CluedoJSON("roll dice");
		json.put("gameID", gameID);	
		json.put("field", field);	
		
		return json.toString();
	}
	
	public static String secret_passageMsg(int gameID){
		CluedoJSON json = new CluedoJSON("secret passage");
		json.put("gameID", gameID);	
		
		return json.toString();
	}
	
	public static String suspectMsg(int gameID,JSONObject statement){
		CluedoJSON json = new CluedoJSON("suspect");
		json.put("gameID", gameID);
		json.put("statement", statement);
		
		return json.toString();
	}
	
	public static String disproveMsg(int gameID,String card){
		CluedoJSON json = new CluedoJSON("disprove");
		json.put("gameID", gameID);
		json.put("card", card);
		
		return json.toString();
	}
	
	public static String accuseMsg(int gameID,String card){
		CluedoJSON json = new CluedoJSON("accuse");
		json.put("gameID", gameID);
		json.put("card", card);
		
		return json.toString();
	}


	
	
	
	
}
