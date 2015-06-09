package staticClasses;

import json.CluedoJSON;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class StaticNetworkMessages {
	
	public static CluedoJSON loginMsg(String nick,String group){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "login");
		json.put("nick", nick);
		json.put("version", Config.PROTOKOLL_VERSION);
		json.put("group", "group");
		json.put("expansions", new JSONArray(Config.EXPANSIONS));		
		
		return json;
	}
	
	public static CluedoJSON login_sucMsg(String[] nickArray,JSONArray gameArray){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "login successful");
		json.put("expansions", new JSONArray(Config.EXPANSIONS));
		json.put("game array", gameArray);
		json.put("nick array", nickArray);
		
		return json;
	}
	
	public static CluedoJSON user_addedMsg(String nick){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "user added");
		json.put("nick", nick);
		
		return json;
	}
	
	public static CluedoJSON disconnectMsg(){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "disconnect");
		
		return json;
	}
	
	public static CluedoJSON disconnectedMsg(String msg){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "disconnected");
		json.put("message", msg);
		
		return json;
	}
	
	public static CluedoJSON user_leftMsg(String nick){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "user left");
		json.put("nick", nick);
		
		return json;
	}
	
	public static CluedoJSON statement(String person,String room,String weapon){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("person", person);
		json.put("weapon", weapon);
		json.put("room", room);
		
		return json;
	}
	
	public static CluedoJSON game_info(int gameId,String gamestate,JSONArray playerInfos,JSONArray watchers,JSONArray personposes,JSONArray weaponposes){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("gameID",gameId);
		json.put("gamestate",gamestate);
		json.put("players", playerInfos);
		json.put("watchers", watchers);
		json.put("person positions", personposes);
		json.put("weapon positions", weaponposes);
		
		return json;
	}
	
	public static CluedoJSON player_info(String nick,String color,String playerstate){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("nick", nick);
		json.put("color", color);
		json.put("playerstate", playerstate);
		
		return json;
	}
	
	public static CluedoJSON player_pos(String person,String field){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("person", person);
		json.put("field", field);
		
		return json;
	}
	
	public static CluedoJSON weapon_pos(String weapon,String field){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("weapon", weapon);
		json.put("field", field);
		
		return json;
	}
	
	public static CluedoJSON okMsg(){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "OK");
		
		return json;
	}
	
	public static CluedoJSON error_Msg(String msg){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "error");
		json.put("message", msg);
		
		return json;
	}
	
	public static CluedoJSON chat_to_serverMsg(String msg,String timestamp){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "chat");
		json.put("message", msg);
		json.put("timestamp", timestamp);	
		
		return json;
	}
	
	public static CluedoJSON chat_to_clientMsg(String msg,String timestamp,String sender){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "chat");
		json.put("sender", sender);
		json.put("message", msg);
		json.put("timestamp", timestamp);	
		
		return json;
	}
	
	public static CluedoJSON create_gameMsg(String color){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "create game");
		json.put("color", color);
		
		return json;
	}
	
	public static CluedoJSON game_createdMsg(JSONObject playerinfo, int gameID){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "game created");
		json.put("gameID", gameID);
		json.put("player", playerinfo);
		
		return json;
	}
	
	public static CluedoJSON join_gameMsg(String color, int gameID){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "join game");
		json.put("gameID", gameID);
		json.put("color", color);
		
		return json;
	}
	
	public static CluedoJSON player_addedMsg(JSONObject playerinfo, int gameID){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "player added");
		json.put("gameID", gameID);
		json.put("player", playerinfo);
		
		return json;
	}
	
	public static CluedoJSON watch_gameMsg(int gameID){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "watch game");
		json.put("gameID", gameID);
		
		return json;
	}
	
	public static CluedoJSON watcher_addedMsg(int gameID,String nick){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "watcher_added");
		json.put("gameID", nick);
		
		return json;
	}
	
	public static CluedoJSON gameinfoMsg(JSONObject gameinfo){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "gameinfo");
		json.put("game", gameinfo);
		
		return json;
	}
	
	public static CluedoJSON leave_gameMsg(int gameID){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "leave game");
		json.put("gameID", gameID);
		
		return json;
	}
	
	public static CluedoJSON left_gameMsg(int gameID,String nick){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "left game");
		json.put("gameID", gameID);
		json.put("nick", nick);
		
		return json;
	}
	
	public static CluedoJSON game_deletedMsg(int gameID){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "game deleted");
		json.put("gameID", gameID);
		
		return json;
	}
	
	public static CluedoJSON start_gameMsg(int gameID){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "start game");
		json.put("gameID", gameID);
		
		return json;
	}
	
	public static CluedoJSON player_cardsMsg(int gameID,String[] cards){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "player cards");
		json.put("gameID", gameID);
		json.put("cards", cards);		
		
		return json;
	}
	
	public static CluedoJSON game_startedMsg(int gameID,String[] order){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "game started");
		json.put("order", order);
		json.put("gamestate", "started");
		json.put("gameID", gameID);		
		
		return json;
	}
	
	public static CluedoJSON stateupdateMsg(int gameID,String nick,JSONObject playerstate){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "game started");
		json.put("nick", nick);
		json.put("playerstat", playerstate);
		json.put("gameID", gameID);		
		
		return json;
	}
	
	public static CluedoJSON game_endedMsg(int gameID,String nick,JSONObject statement){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "game ended");
		json.put("nick", nick);
		json.put("statement", statement);
		json.put("gameID", gameID);		
		
		return json;
	}
	
	public static CluedoJSON dice_resultMsg(int gameID,int[] result){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "game ended");
		json.put("result", result);
		json.put("gameID", gameID);		
		
		return json;
	}
	
	public static CluedoJSON movedMsg(int gameID,JSONObject personpos){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "moved");
		json.put("person position", personpos);
		json.put("gameID", gameID);		
		
		return json;
	}
	
	public static CluedoJSON suspicionMsg(int gameID,JSONObject statement){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "suspition");
		json.put("statement", statement);
		json.put("gameID", gameID);		
		
		return json;
	}
	
	public static CluedoJSON disprovedMsg(int gameID,String nick,String card){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "disproved");
		json.put("nick", nick);
		json.put("gameID", gameID);		
		
		return json;
	}
	
	public static CluedoJSON no_disproveMsg(int gameID){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "no disprove");
		json.put("gameID", gameID);		
		
		return json;
	}
	
	public static CluedoJSON wrong_accusationMsg(int gameID,JSONObject statement){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "wrong accusation");
		json.put("gameID", gameID);	
		json.put("statement", statement);	
		
		return json;
	}
	
	public static CluedoJSON roll_diceMsg(int gameID){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "roll dice");
		json.put("gameID", gameID);		
		
		return json;
	}
	
	public static CluedoJSON moveMsg(int gameID,JSONObject field){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "roll dice");
		json.put("gameID", gameID);	
		json.put("field", field);	
		
		return json;
	}
	
	public static CluedoJSON secret_passageMsg(int gameID){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "secret passage");
		json.put("gameID", gameID);	
		
		return json;
	}
	
	public static CluedoJSON suspectMsg(int gameID,JSONObject statement){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "suspect");
		json.put("gameID", gameID);
		json.put("statement", statement);
		
		return json;
	}
	
	public static CluedoJSON disproveMsg(int gameID,String card){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "disprove");
		json.put("gameID", gameID);
		json.put("card", card);
		
		return json;
	}
	
	public static CluedoJSON accuseMsg(int gameID,String card){
		CluedoJSON json = new CluedoJSON(new JSONObject());
		json.put("type", "accuse");
		json.put("gameID", gameID);
		json.put("card", card);
		
		return json;
	}


	
	
	
	
}
