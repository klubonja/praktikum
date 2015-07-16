package staticClasses;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

import json.CluedoJSON;

import org.json.JSONArray;
import org.json.JSONObject;

import cluedoClient.ServerItem;
import cluedoNetworkLayer.CluedoField;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoGameServer;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;
import cluedoNetworkLayer.CluedoWeapon;
import cluedoNetworkLayer.WinningStatement;
import cluedoServer.ClientItem;
import enums.GameStates;
import enums.Persons;
import enums.PlayerStates;


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
	
	public static JSONObject player_info(String nick,String color,ArrayList<String> playerstates){
		JSONObject json = new JSONObject();
		json.put("nick", nick);
		json.put("color", color);
		JSONArray states = new JSONArray();
		for (String state : playerstates)
			states.put(state);
		json.put("playerstate", states);
		
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
		CluedoJSON json = new CluedoJSON("ok");
		
		return json.toString();
	}
	
	public static String error_Msg(String msg){
		CluedoJSON json = new CluedoJSON("error");
		json.put("message", msg);
		
		return json.toString();
	}
	
	public static String chatMsg(String msg,String ts){
		CluedoJSON json = new CluedoJSON("chat");
		json.put("message", msg);
		json.put("timestamp", ts);	
		
		return json.toString();
	}
	
	public static String chatMsg(String msg,String sender,String ts){
		CluedoJSON json = new CluedoJSON("chat");
		json.put("sender", sender);
		json.put("message", msg);
		json.put("timestamp", ts);	
		
		return json.toString();
	}
	
	public static String chatMsg(String msg,int gameID,String ts){
		CluedoJSON json = new CluedoJSON("chat");
		json.put("gameID", gameID);
		json.put("message", msg);
		json.put("timestamp", ts);	
		
		return json.toString();
	}
	public static String chatMsg(String msg,int gameID,String sender,String ts){
		CluedoJSON json = new CluedoJSON("chat");
		json.put("sender", sender);
		json.put("gameID", gameID);
		json.put("message", msg);
		json.put("timestamp", ts);	
		
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
		CluedoJSON json = new CluedoJSON("watcher added");
		json.put("gameID", gameID);
		json.put("nick", nick);
		
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
	
	public static String player_cardsMsg(int gameID,ArrayList<String> cards){
		CluedoJSON json = new CluedoJSON("player_cards");
		json.put("gameID", gameID);
		JSONArray cardsJSON = new JSONArray();
		for (String s: cards)
			cardsJSON.put(s);
		json.put("cards", cardsJSON);		
		
		return json.toString();
	}
	
	public static String game_startedMsg(int gameID,ArrayList<String> order){
		JSONArray orderJSON = new JSONArray();
		for (String nick: order)
			orderJSON.put(nick);
		
		return make_game_startedMsg(gameID, orderJSON);
	}
	
	public static String make_game_startedMsg(int gameID,JSONArray order){
		CluedoJSON json = new CluedoJSON("game started");
		json.put("order", order);
		json.put("gamestate", "started");
		json.put("gameID", gameID);		
		
		return json.toString();
	}
	
	
	public static String stateupdateMsg(int gameID,JSONObject playerinfo){
		CluedoJSON json = new CluedoJSON("stateupdate");
		json.put("player", playerinfo);
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
	
	public static String end_turnMsg(int gameID){
		CluedoJSON json = new CluedoJSON("end turn");
		json.put("gameID", gameID);
		return json.toString();
	}
	
	public static String game_endedMsg(int gameID,WinningStatement statement){
		JSONObject statementJSON = new JSONObject();
		try {
			statementJSON.put("person", statement.getPerson().getColor());
			statementJSON.put("weapon", statement.getWeapon().getName());
			statementJSON.put("room", statement.getRoom().getName());
			
		} catch (NullPointerException e) {
			statementJSON.put("person", auxx.getRandomPersonColorString());
			statementJSON.put("weapon",auxx.getRandomWeapon());
			statementJSON.put("room", auxx.getRandomRoom());
		}
		

		return make_game_endedMsg(gameID, statementJSON);
	}
	
	public static String make_game_endedMsg(int gameID,JSONObject statement){
		CluedoJSON json = new CluedoJSON("game ended");
		json.put("statement", statement);
		json.put("gameID", gameID);		
		
		return json.toString();
	}
	
	public static String dice_resultMsg(int gameID,int[] result){
		JSONArray ergebnis = new JSONArray ();
		ergebnis.put(result[0]);
		ergebnis.put(result[1]);
		CluedoJSON json = new CluedoJSON("dice result");
		json.put("result", ergebnis);
		json.put("gameID", gameID);		
		
		return json.toString();
	}
	
	public static String movedMsg(int gameID,String personname,CluedoPosition newpos){
		CluedoJSON json = new CluedoJSON("moved");
		json.put("person position", personPos(personname, newpos));
		json.put("gameID", gameID);		
		
		return json.toString();
	}
	
	public static JSONObject personPos(String personname ,CluedoPosition pos){
		JSONObject personpos = new JSONObject();
		personpos.put("person", personname);
		personpos.put("field", makefield(pos.getX(), pos.getY()));
		
		return personpos;
	}
	
	public static JSONObject makefield(int x, int y){
		JSONObject field = new JSONObject();
		field.put("x",x);
		field.put("y", y);
		
		return field;
	}
	public static String suspicionMsg(int gameID,JSONObject statement){
		CluedoJSON json = new CluedoJSON("suspicion");
		json.put("statement", statement);
		json.put("gameID", gameID);		
		
		return json.toString();
	}
	
	public static String disprovedMsg(int gameID,String nick,String card){
		CluedoJSON json = new CluedoJSON("disproved");
		json.put("nick", nick);
		json.put("gameID", gameID);	
		json.put("card", card);
		
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
	
	public static String moveMsg(int gameID, CluedoField field){
		CluedoJSON json = new CluedoJSON("move");
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
	
	public static String accuseMsg(int gameID, JSONObject statement){
		CluedoJSON json = new CluedoJSON("accuse");
		json.put("gameID", gameID);
		json.put("statement", statement);
		
		return json.toString();
	}
	
	public static JSONObject gameInfo(CluedoGameServer game){
		JSONArray playerInfosJSON = new JSONArray();
		JSONArray perspossJSON = new JSONArray();
		Stack<CluedoPlayer> playerInfos = game.getPlayersConnected();
		for (CluedoPlayer p : playerInfos){
			playerInfosJSON.put(
				NetworkMessages.player_info(
					p.getNick(), 
					p.getCluedoPerson().getColor(), 
					p.getStatesStringList()
				)
			);
			perspossJSON.put(
				NetworkMessages.player_pos(
					p.getCluedoPerson().getColor(), 
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
	
	public static String gameInfoMsg(CluedoGameServer serverGame){
		CluedoJSON json = new CluedoJSON("gameinfo");
		JSONObject gameinfo = gameInfo(serverGame);
		json.put("game", gameinfo);
		
		return json.toString();
	}
	
	public static String make_login_sucMsg(JSONArray nickArray,JSONArray gameArray,JSONArray expansions){
		CluedoJSON json = new CluedoJSON("login successful");
		json.put("expansions", expansions);
		json.put("game array", gameArray);
		json.put("nick array", nickArray);
		
		return json.toString();
	}
	
	public static String login_sucMsg(ArrayList<String> expansions, ArrayList<ClientItem> clientList, Vector<CluedoGameServer> gameList ){
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
	
	public static CluedoGameClient createGameFromJSONGameInfo(JSONObject gameinfo,ServerItem server){
		CluedoGameClient newgame = new CluedoGameClient(
				gameinfo.getInt("gameID"),server);
		newgame.setGameState(
				GameStates.getState(
						gameinfo.getString("gamestate")
						)
					);
		
		JSONArray players = gameinfo.getJSONArray("players");				
		for (int n = 0;n < players.length();n++){
//			CluedoPlayer player = new CluedoPlayer(
//					Persons.getPersonByColor(
//							players.getJSONObject(n).getString("color")
//							),
//					PlayerStates.getPlayerState(
//							players.getJSONObject(n).getString("playerstate")
//							)									
//					);	
//			player.setNick(players.getJSONObject(n).getString("nick"));
			
			JSONArray states = players.getJSONObject(n).getJSONArray("playerstate");
			ArrayList<PlayerStates> playerstates = new ArrayList<PlayerStates>();
			for (int t = 0; t < states.length(); t++){
				playerstates.add(PlayerStates.getPlayerState(states.getString(t)));
			}
			
			newgame.joinGame(
					players.getJSONObject(n).getString("color")
					,
					players.getJSONObject(n).getString("nick"), 
//					PlayerStates.getPlayerState(
//							players.getJSONObject(n).getString("playerstate"))
					playerstates
					);
		}
		
		JSONArray watchers = gameinfo.getJSONArray("watchers");				
		for (int n = 0;n < watchers.length();n++){
			newgame.addWatcher(watchers.getString(n));
		}
		
		if (gameinfo.has("person positions")){
			JSONArray personposs = gameinfo.getJSONArray("person positions");				
			for (int n = 0;n < personposs.length();n++){						
				JSONObject ppos = personposs.getJSONObject(n);
				String pname = ppos.getString("person");
				newgame.
					getPlayer(pname).
						getPosition().
							setX(ppos.getJSONObject("field").
									getInt("x"));
				newgame.getPlayer(pname).getPosition().setY(ppos.getJSONObject("field").getInt("y"));	
			}
		}
		if (gameinfo.has("weapon positions")){
			JSONArray weaponposs = gameinfo.getJSONArray("weapon positions");				
			for (int n = 0;n < weaponposs.length();n++){						
				JSONObject wpos = weaponposs.getJSONObject(n);
				String wname = wpos.getString("weapon");
				newgame.
					getWeaponByName(wname).
						getPosition().
							setX(
									wpos.getJSONObject("field").
									getInt("x")
								);
				newgame.getWeaponByName(wname).getPosition().setY(wpos.getJSONObject("field").getInt("y"));
			}
		}	
		
		return newgame;
	}
	
	public static ArrayList<CluedoGameClient> createGamesFromJSONGameArray(JSONArray gamearray,ServerItem server){
		ArrayList<CluedoGameClient> gamelist = new ArrayList<CluedoGameClient>();
		for (int i = 0; i < gamearray.length(); i++)						
			gamelist.add(createGameFromJSONGameInfo(gamearray.getJSONObject(i), server));
		
		return gamelist;
	}	
}
