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
//		Collection<Object> field1 = new ArrayList<Object>();
//		Collection<Object> field2 = new ArrayList<Object>();

		JSONObject playerInfo1 = new JSONObject();
		JSONObject playerInfo2 = new JSONObject();
		
		JSONArray playerinfo2Game42 = new JSONArray();
		JSONArray playerinfo2Game43 = new JSONArray();
		
		JSONArray expansions = new JSONArray();
		JSONArray nick_array = new JSONArray();
		JSONArray game_array = new JSONArray();
		JSONArray watchers1 = new JSONArray();
		JSONArray watchers2 = new JSONArray();
		JSONObject field1 = new JSONObject();
		JSONObject field2 = new JSONObject();
		
		JSONObject personpos1 = new JSONObject();
		JSONObject personpos2 = new JSONObject();
		
		JSONArray personposes1 = new JSONArray();
		JSONArray personposes2 = new JSONArray();
		
		personpos1.put("person", "white");
		personpos1.put("field", field1);
		
		personpos2.put("person", "red");
		personpos2.put("field", field2);
		
		personposes1.put(personpos1);
		personposes1.put(personpos2);
		personposes2.put(personpos1);
		personposes2.put(personpos2);
		
		JSONObject field3 = new JSONObject();
		JSONObject field4 = new JSONObject();
		
		JSONObject weaponpos1 = new JSONObject();
		JSONObject weaponpos2 = new JSONObject();
		
		JSONArray weaponposes1 = new JSONArray();
		JSONArray weaponposes2 = new JSONArray();
		
		weaponpos1.put("weapon", "dagger");
		weaponpos1.put("field", field3);
		
		weaponpos2.put("weapon", "rope");
		weaponpos2.put("field", field4);
		
		weaponposes1.put(weaponpos1);
		weaponposes1.put(weaponpos2);
		weaponposes2.put(weaponpos1);
		weaponposes2.put(weaponpos2);
		
		field1.put("x",2);
		field1.put("y",5);
		field2.put("x",23);
		field2.put("y",28);
		
		

		
		field3.put("x",12);
		field3.put("y",23);
		field4.put("x",11);
		field4.put("y",245);
		
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
		gameInfo1.put("person positions", personposes1);
		gameInfo1.put("weapon positions", weaponposes1);
		
		gameInfo2.put("gameID", 43);
		gameInfo2.put("gamestate", "started");
		gameInfo2.put("players", playerinfo2Game43);
		gameInfo2.put("watchers",watchers2);
		gameInfo2.put("person positions", personposes2);
		gameInfo2.put("weapon positions", weaponposes2);
		
		playerInfo1.put("nick", "groupie" );
		playerInfo1.put("color", "white" );
		playerInfo1.put("field", field1 );
		playerInfo1.put("cards", "3" );
		playerInfo1.put("playerstate", "do nothing" );
		
		playerInfo2.put("nick", "nickie" );
		playerInfo2.put("color", "red" );
		playerInfo2.put("field", field2 );
		playerInfo2.put("cards", "3" );
		playerInfo2.put("playerstate", "roll dice" );	
		
		jsonRoot.put("type","login successful");
		jsonRoot.put("expansions", expansions);
		jsonRoot.put("nick array", nick_array);
		jsonRoot.put("game array", game_array);
		
		System.out.println(jsonRoot.toString());
		
		
		CluedoProtokollChecker c = new CluedoProtokollChecker(jsonRoot);
		if (!c.validate()) c.printErrs();
		
		

		
    }
	
		
	
	
}
