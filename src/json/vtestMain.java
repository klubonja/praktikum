package json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import enums.Orientation;

public class vtestMain {
	public static void main(String[] args) {
		
		Orientation s = Orientation.S;
		CluedoJSON jsonRoot = new CluedoJSON();
		
		JSONObject gameInfo1 = new JSONObject();
		JSONObject gameInfo2 = new JSONObject();
		
		
//		LinkedHashMap<String,String> playerInfo1 = new LinkedHashMap<String,String>();
//		LinkedHashMap<String,String> playerInfo2 = new LinkedHashMap<String,String>();
//		
//		ArrayList<LinkedHashMap<String,String>> playerinfo2Game42 = new ArrayList<LinkedHashMap<String,String>>() ;
//		ArrayList<LinkedHashMap<String,String>> playerinfo2Game43 = new ArrayList<LinkedHashMap<String,String>>() ;
//		
//		
//		ArrayList<String> expansions = new ArrayList<String>();
//		List<String> nick_array = new ArrayList<>();
//		Collection<String> watchers1 = new ArrayList<String>();
//		List<String> watchers2 = new LinkedList<String>();
//		ArrayList<JSONObject> game_array = new ArrayList<JSONObject>();
		
		JSONObject playerInfo1 = new JSONObject();
		JSONObject playerInfo2 = new JSONObject();
		
		JSONArray playerinfo2Game42 = new JSONArray();
		JSONArray playerinfo2Game43 = new JSONArray();
		
		JSONArray expansions = new JSONArray();
		JSONArray nick_array = new JSONArray();
		JSONArray game_array = new JSONArray();
		JSONArray watchers1 = new JSONArray();
		JSONArray watchers2 = new JSONArray();
		

		
		
		game_array.put(gameInfo1);
		game_array.put(gameInfo2);
		
		expansions.put("Chat");
		expansions.put("Hypie,Hypie");
		
		nick_array.put("groupie");
		nick_array.put("nickie");
		nick_array.put("franz");
		nick_array.put("ellie");
		
		watchers1.put("franz");
		watchers2.put("ellie");	
		
		playerinfo2Game42.put( playerInfo1);
		playerinfo2Game42.put(playerInfo2);
		
		playerinfo2Game43.put( playerInfo1);
		playerinfo2Game43.put(playerInfo2);
		
		
		gameInfo1.put("gameID", 42);
		gameInfo1.put("gamestate", "not started");
		gameInfo1.put("players", playerinfo2Game42);
		gameInfo1.put("watchers",watchers1);
		
		gameInfo2.put("gameID", 43);
		gameInfo2.put("gamestate", "started");
		gameInfo2.put("players", playerinfo2Game43);
		gameInfo2.put("watchers",watchers2);
		
		playerInfo1.put("nick", "groupie" );
		playerInfo1.put("color", "white" );
		playerInfo1.put("fields", "<12,12>" );
		playerInfo1.put("cards", "3" );
		playerInfo1.put("playerstate", "do nothing" );
		
		playerInfo2.put("nick", "nickie" );
		playerInfo2.put("color", "red" );
		playerInfo2.put("fields", "<24,12>" );
		playerInfo2.put("cards", "3" );
		playerInfo2.put("playerstate", "roll dice" );	
		
		jsonRoot.put("type","login successful");
		jsonRoot.put("expansions", expansions);
		jsonRoot.put("nick array", nick_array);
		jsonRoot.put("game array", game_array);
		
		System.out.println(jsonRoot.toString());
		
		
		CluedoProtokollChecker c = new CluedoProtokollChecker(jsonRoot);
		if (!c.validate()) c.printErrs();
		
		
//		CluedoJSONTypes[] ty = CluedoJSONTypes.values();
//		for (CluedoJSONTypes t : ty){
//			System.out.println("static void val_"+t.getNameNoSpace()+"(CluedoJSON json){\n\n}");
//			
//		}
		
    }
	
		
	
	
}
