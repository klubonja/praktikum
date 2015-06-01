package json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.lang.reflect.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import enums.*;

public class CluedoProtokollChecker {
	
	private ArrayList<String> errs;
	CluedoJSON json;
	String type;
	String typeNoSpace;
	boolean isValid;
	double protokollVersion = 1;
	Map<String, Method> methodMap = new HashMap<String, Method>();

	public CluedoProtokollChecker(CluedoJSON j) {
		json = j;
		isValid = false;
		errs = new ArrayList<String>();
	}

	public boolean validate() {
		if (checkType()) { // sets type to check
			try {
				Method m = CluedoProtokollChecker.class
						.getDeclaredMethod("val_" + typeNoSpace);
				try {
					m.invoke(this);
					System.out.println("invoking :"+"val_" + typeNoSpace );
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					System.out.println("invoking :"+"val_" + typeNoSpace +" failed" );
				}
			} catch (NoSuchMethodException | SecurityException e) {
				System.out.println("finding :"+"val_" + typeNoSpace +" failed : no such method");
				//e.printStackTrace();
			}

			// CluedoJSONTypes[] types = CluedoJSONTypes.values();
			// for (CluedoJSONTypes t : types){
			// try {//maps cluedotypes to val_methods
			// methodMap.put(t.getNameNoSpace(),
			// CluedoProtokollChecker.class.getDeclaredMethod("val_"+t.getNameNoSpace()));
			// } catch (NoSuchMethodException | SecurityException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }

			// for (CluedoJSONTypes t : types){
			// try {
			// methodMap.get(t.getNameNoSpace()).invoke(this);
			// } catch (IllegalAccessException | IllegalArgumentException
			// | InvocationTargetException e) {
			//
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }

		}
		;

		if (errs.size() == 0){
			System.out.println("OK");
			return true;
		}
		System.out.println("Not OK see : this.printErrs()");
		return false;
	}

	// type is set
	void val_login() {
		validateField(json, "nick");
		validateField(json, "group");
		if (validateField(json, "version"))
			validateProtokollVersion(json,"version");
		isJSONArrayOfType(json, "expansions", "string");
	}

	void val_login_successful() {		
		isJSONArrayOfType(json, "expansions", "string");
		isJSONArrayOfType(json, "nick array", "string");
		isJSONArrayOfType(json, "game array", "gameinfo");
	}

	void val_user_added() {
		validateField(json, "nick");
	}

	void val_disconnect() {}

	void val_disconnected() {
		validateField(json, "message");
	}

	void val_user_left() {
		validateField(json, "nick");
	}

	void val_ok() {

	}

	void val_error() {
		validateField(json, "message");
	}

	void val_chat() {
		validateField(json, "sender");
		validateField(json, "message");
		if (validateField(json, "timestamp"))
			validateLocalTimeFormat(json, "timestamp");
	}

	void val_create_game() {
		if (validateField(json, "color"))
			validatePerson("color");
	}

	void val_game_created() {
		if (validateField(json, "gameId"))
			isInt(json, "gameId");
		validateField(json, "nick");
	}

	void val_join_game() {
		if (validateField(json, "gameId"))
			isInt(json, "gameId");
		if (validateField(json, "color"))
			validatePerson("color");
	}

	void val_player_added() {
		val_join_game();
		validateField(json, "nick");
	}

	void val_watch_game() {
		if (validateField(json, "gameId"))
			isInt(json, "gameId");
	}

	void val_watcher_added() {
		val_join_game();
	}

	void val_leave_game() {
		val_watch_game();
	}

	void val_left_game() {
		val_game_created();
	}

	void val_game_deleted() {
		val_watch_game();
	}

	void val_start_game() {
		val_watch_game();
	}

	void val_game_started() {
		val_watch_game();
		if (validateField(json, "gamestate"))
			validateGameState(json.getString("gamestate"), "started");
		isJSONArrayOfType(json, "order", "string");
	}

	void val_game_ended() {
		val_game_created();
		if (validateField(json, "statement"))
			validateStatement(json.getJSONObject("statement"));

	}

	void val_stateupdate() {
		val_game_created();
		if (validateField(json, "playerstate"))
			validatePlayerState(json.getString("playerstate"));		
	}
	
	void val_player_cards(){
		val_watch_game();
		if (validateField(json, "cards"))
			isJSONArrayOfType(json, "cards", "cards");
	}

	void val_dice_result() {
		val_watch_game();
		if (validateField(json,"result"))
			validateDiceResult(json,"result");
	}

	void val_moved() {
		val_watch_game();
		if (validateField(json, "color"))
			validatePerson(json.getString("color"));
		validateField(json,"field");
	}

	void val_suspicion() {
		val_watch_game();
		if (validateField(json, "statement"))
			validateStatement(json.getJSONObject("statement"));
		
	}

	void val_disproved() {
		val_game_created();
		if (validateField(json, "card"))
			validateCards(json.getString("cards"));
	}

	void val_no_disprove() {
		val_watch_game();
	}

	void val_wrong_accusation() {
		val_suspicion();
	}

	void val_roll_dice() {
		val_watch_game();
	}

	void val_move() {
		val_watch_game();
		validateField(json,"field");
	}

	void val_secret_passage() {
		val_watch_game();
	}

	void val_suspect() {
		val_watch_game();
		validateStatement(json);
	}

	void val_disprove() {
		val_watch_game();
		// card kann teil sein muss aber nicht
	}

	void val_end_turn() {
		val_watch_game();
	}

	void val_accuse() {
		val_suspicion();		
	}
	
	void val_upd_server(){
		validateField(json,"group");
		if (validateField(json,"tcp port"))
			isInt(json, "tcp port");
		
	}
	
	void val_upd_client(){
		validateField(json,"group");		
	}
	
	
	
	
	void validateLocalTimeFormat(JSONObject jsonParent,String key){
		String value = jsonParent.getString("key");
		if (!value.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}\\d{2}\\.\\d{2}"))
			setErr(value +" hat nicht protokollgemäßes (java.time.LocalDateTime) Format");
	}
	
	void validatePerson(String personName){
		if (!Persons.isMemberPersonName(personName))
			setErr("Personname "+personName+ " not part of this Game");
	}
	
	void validateWeapon(String weaponName){
		if (!Weapons.isMember(weaponName))
			setErr("Weapon"+weaponName+ " not part of this Game");
	}
	
	void validateRoom(String personName){
		if (!Persons.isMember(personName))
			setErr("Personname "+personName+ " not part of this Game");
	}
	
	void validatePlayerState(String playerState){
		if (!PlayerStates.isMember(playerState))
			setErr("PlayerState "+playerState+ " not a valid PlayerState in this Game");
	}
	
	boolean validateProtokollVersion(JSONObject jsonParent,String key){
		if (jsonParent.getString(key).equals(protokollVersion)) return true;		
		return false;
 	}
	
	void validateDiceResult(JSONObject jsonParent,String key){
		try {
			int amountDices = 2;
			JSONArray diceres = new JSONArray(jsonParent.getJSONArray(key));
			for (int i = 0; i < amountDices;i++)
				if (!isInt(jsonParent, diceres.getString(i)))
					setErr("JSONArray : in JSONArray "+key+" on index "+i+" : noInt");
			
		}
		catch (JSONException je){
			setErr("JSONArray expected : "+key+" is not JSONArray");
		}
	}
	
	/**
	 * @param gameState
	 * @param expectedState
	 * assuming expected state is valid
	 */
	void validateGameState(String gameState,String expectedState){
			if (!gameState.equals(expectedState))
				setErr("expected State :"+ expectedState);
	}
	
	void validateGameState(String gameState){
		if (!GameStates.isMember(gameState))
			setErr("GameState "+gameState+ " not a valid Gamestate in this Game");
	}
	
	void validateColor(String color){
		if (!Persons.isMember(color))
			setErr("Color "+color+ " not a valid Color in this Game");
	}
	
	void validateStatement(JSONObject jsonParent){
		String[] expectedFields = {"person","weapon","room"};
		for (String fieldName : expectedFields){
			if (validateField(jsonParent, fieldName))
				validatePerson(jsonParent.getString(fieldName));
		}
	}
	
	boolean validateField(JSONObject jsonParent, String key) {
		if (jsonParent.has(key))
			if (jsonParent.get(key).toString().length() > 0)
				return true;
			else
				setErr(key + " is empty");
		else
			setErr(key + " expected");

		return false;
	}
	
	boolean validateCards(String value){
		Persons[] persons = Persons.values();
		for(Persons p : persons) if (value.equals(p.getName())) return true;
		Weapons[] weapons = Weapons.values();
		for(Weapons w : weapons) if (value.equals(w.getName())) return true;
		Rooms[] rooms = Rooms.values();
		for(Rooms r : rooms) if (value.equals(r.getName())) return true;
		
		return false;
	}
	/**
	 * @param jsonParent
	 * ich weis noch nicht als was field kommt
	 * 	
	 *  */
	void validatePlayerInfo(JSONObject jsonParent){		
			validateField(jsonParent, "nick");			
			if (validateField(jsonParent, "color"))
				validatePerson(jsonParent.getString("color"));
			validateField(jsonParent, "fields");
			if (validateField(jsonParent, "cards"))
				isInt(jsonParent, "cards");
			if (validateField(jsonParent, "playerstate"))
				validatePlayerState(jsonParent.getString("playerstate"));		
	}
	
	void validateGameInfo(JSONObject jsonParent) throws JSONException{
		if (validateField(jsonParent, "gameID"))
			isInt(jsonParent, "gameID");
		validateGameState(jsonParent.getString("gamestate"));
		isJSONArrayOfType(jsonParent,"players", "playerinfo");
//		isJSONArrayOfType(jsonParent,"watchers", "string");
//		isJSONArrayOfType(jsonParent,"person positions", "personpos");
//		isJSONArrayOfType(jsonParent,"weapon positions", "weaponpos");			

	}
	
	boolean isJSONArrayOfType(JSONObject jsonParent, String key,String localtype) {
		int index = 0;
		try {
			JSONArray jar = jsonParent.getJSONArray(key);
			for (index = 0; index < jar.length(); index++) {
				try {
					switch (localtype) {
						case "playerinfo" :
							validatePlayerInfo(jar.getJSONObject(index));
							break;
						case "gameinfo" :
							validateGameInfo(jar.getJSONObject(index));
							break;
						case  "cards" :
							validateCards(jar.getString(index));
							break;
						case "string" :
							;
							break;
						case "weaponpos" :
							;
							break;
						case "personpos" :
							;
							break;
						default :
							;
					}					
				} 
				catch (Exception e) {
					System. out.println("BAD : attempting jsonarray : "+key+" loopindex"+ index + " for "+localtype);
				}				
			}		
			
			return true;			
		} 
		catch (JSONException e) {
		//	System. out.println("VERYBAD :JSONArray expected on : "+key+" loopindex"+ index + " for "+localtype+" value \n"+jsonParent.toString());
			//e.printStackTrace();
			setErr("JSONArray: "+key+" expected  ");
			return false;
		}		
	}

	boolean isInt(JSONObject jsonParent,String key) {
		try {
			jsonParent.getInt(key);
			return true;
		} catch (JSONException je) {
			setErr("value of " + key + " is not of type int");
			return false;
		}
	}

	boolean checkType() {
		if (!json.has("type")) {
			errs.add("message type not set");
			return false;
		} 
		else if (!CluedoProtokollMessageTypes.isMember(json.getString("type"))) {
			errs.add("type " + json.getString("type")
					+ " is not part of protokoll v." + protokollVersion);
			return false;
		}

		this.typeNoSpace = json.getString("type").replaceAll(" ", "_");
		this.type = json.getString("type");
		return true;
	}

	public void printErrs() {
		System.out.println("Missing: ");
		for (String s : errs)
			System.out.println(s);
	}

	private void setErr(String err) {
		errs.add(err);
	}
}