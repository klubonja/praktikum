package json;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.json.JSONObject;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.TopLevelAttribute;

import enums.Orientation;

public class vtestMain {
	public static void main(String[] args) {
		
		Orientation s = Orientation.S;
		CluedoJSON jsonRoot = new CluedoJSON();
		
		JSONObject gameInfo1 = new JSONObject();
		JSONObject gameInfo2 = new JSONObject();
		JSONObject gameState1 = new JSONObject();
		JSONObject gameState2 = new JSONObject();
		
		LinkedHashMap playerInfo1 = new LinkedHashMap();
		LinkedHashMap playerInfo2 = new LinkedHashMap();
		
		LinkedList<String> l1 = new LinkedList<String>();
		LinkedList<String> l2 = new LinkedList<String>();		
		LinkedList<JSONObject> l3 = new LinkedList<JSONObject>();

		jsonRoot.put("type","login successful");
		jsonRoot.put("expansions", l1);
		jsonRoot.put("nick array", l2);
		jsonRoot.put("game array", l3);
		l3.add(gameInfo1);
		l3.add(gameInfo2);
		
		l1.add("Chat");
		l1.add("Hypie,Hypie");
		l2.add("groupie");
		l2.add("nickie");
		l2.add("franz");
		
		gameInfo1.put("gameID", 42);
		gameInfo1.put("gamestate", gameState1);
		gameInfo1.put("players", gameState1);
		
		gameInfo2.put("gameID", 43);
		gameInfo2.put("gamestate", gameState2);
		
		
		
		
		CluedoProtokollChecker c = new CluedoProtokollChecker(jsonRoot);
		if (!c.validate()) c.printErrs();
		
		
//		CluedoJSONTypes[] ty = CluedoJSONTypes.values();
//		for (CluedoJSONTypes t : ty){
//			System.out.println("static void val_"+t.getNameNoSpace()+"(CluedoJSON json){\n\n}");
//			
//		}
		
    }
		
		
	
	
}
