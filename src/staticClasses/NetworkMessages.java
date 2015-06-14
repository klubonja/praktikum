package staticClasses;

import java.util.ArrayList;

import json.CluedoJSON;

import org.json.JSONArray;
import org.json.JSONObject;

import cluedoNetworkLayer.CluedoGameServer;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoWeapon;
import cluedoServer.ClientItem;


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
		json.put("nick", nick);
		json.put("version", Config.PROTOKOLL_VERSION);
		json.put("group", group);
		json.put("expansions", new JSONArray(Config.EXPANSIONS));		
		
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
	
	public static JSONObject statement(String person,String room,String weapon){
		JSONObject json = new JSONObject();
		json.put("person", person);
		json.put("weapon", weapon);
		json.put("room", room);
		
		return json;
	}
	
	public static JSONObject gameinfo(int gameId,String gamestate,JSONArray playerInfos,JSONArray watchers,JSONArray personposes,JSONArray weaponposes){
		JSONObject json = new JSONObject();
		json.put("gameID",gameId);
		json.put("gamestate",gamestate);
		json.put("players", playerInfos);
		json.put("watchers", watchers);
		json.put("person positions", personposes);
		json.put("weapon positions", weaponposes);
		
		return json;
	}
	
	public static JSONObject player_info(String nick,String color,String playerstate){
		JSONObject json = new JSONObject();
		json.put("nick", nick);
		json.put("color", color);
		json.put("playerstate", playerstate);
		
		return json;
	}
	
	public static JSONObject field(int x, int y){
		JSONObject json = new JSONObject();
		json.put("x", x);
		json.put("y", y);
		
		return json;
	}
	
	public static JSONObject player_pos(String person,JSONObject field){
		JSONObject json = new JSONObject();
		json.put("person", person);
		json.put("field", field);
		
		return json;
	}
	
	public static JSONObject weapon_pos(String weapon,JSONObject field){
		JSONObject json = new JSONObject();
		json.put("weapon", weapon);
		json.put("field", field);
		
		return json;
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
	
	public static String player_cardsMsg(int gameID,JSONArray cards){
		CluedoJSON json = new CluedoJSON("player cards");
		json.put("gameID", gameID);
		json.put("cards", cards);		
		
		return json.toString();
	}
	
	public static String game_startedMsg(int gameID,JSONArray order){
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
	
	public static JSONObject gameInfo(CluedoGameServer game){
		JSONArray playerInfosJSON = new JSONArray();
		JSONArray perspossJSON = new JSONArray();
		ArrayList<CluedoPlayer> playerInfos = game.getPlayers();
		for (CluedoPlayer p : playerInfos){
			playerInfosJSON.put(
				NetworkMessages.player_info(
					p.getNick(), 
					p.getCluedoPerson().getColor(), 
					p.getState().getName()
				)
			);
			perspossJSON.put(
				NetworkMessages.player_pos(
					p.getCluedoPerson().getPersonName(), 
					NetworkMessages.field(
						p.getPosition().getX(), 
						p.getPosition().getY()
					)
				)
			);
		}
		JSONArray watchersJSON = new JSONArray();
		ArrayList<String> watchers = game.getWatchersNicks();
		for (String w : watchers)
			watchersJSON.put(w);
		
		JSONArray weaponpossJSON = new JSONArray();
		ArrayList<CluedoWeapon> weaponPoss = game.getWeapons();
		for (CluedoWeapon wp : weaponPoss){
			weaponpossJSON.put(
				NetworkMessages.weapon_pos(
					wp.getWeapon().getName(), 
					NetworkMessages.field(
						wp.getPosition().getX(),
						wp.getPosition().getY()
					)
				)
			);
		}
		
		return NetworkMessages.gameinfo(
				game.getGameId(), 
				game.getGameState().getName(), 
				playerInfosJSON, 
				watchersJSON, 
				perspossJSON,
				weaponpossJSON
			  );		
	}
	
	public static String make_login_sucMsg(JSONArray nickArray,JSONArray gameArray,JSONArray expansions){
		CluedoJSON json = new CluedoJSON("login successful");
		json.put("expansions", expansions);
		json.put("game array", gameArray);
		json.put("nick array", nickArray);
		
		return json.toString();
	}
	
	public static String login_sucMsg(ArrayList<String> expansions, ArrayList<ClientItem> clientList, ArrayList<CluedoGameServer> gameList ){
		JSONArray nickArray = new JSONArray();
		JSONArray gameArray = new JSONArray();
		JSONArray expansionsJSON = new JSONArray();
		for (ClientItem c : clientList)
			nickArray.put(c.getNick());
		for (CluedoGameServer g : gameList)
			gameArray.put(gameInfo(g));
		for (String ex: expansions)
			expansionsJSON.put(ex);
			
		return NetworkMessages.make_login_sucMsg(
						nickArray, 
						gameArray,
						expansionsJSON
					);
	}
	
	
	
	
	
}
