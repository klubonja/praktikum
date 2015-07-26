package json;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import staticClasses.Config;
import staticClasses.auxx;
import enums.CluedoProtokollMessageTypes;
import enums.Field;
import enums.GameStates;
import enums.NetworkHandhakeCodes;
import enums.Persons;
import enums.PlayerStates;
import enums.Rooms;
import enums.Weapons;

public class CluedoProtokollChecker {
	
	private ArrayList<String> errs;
	private ArrayList<String> msgs;
	CluedoJSON jsonRoot;
	String type;
	String typeNoSpace;

	public CluedoProtokollChecker(CluedoJSON j) {
		jsonRoot = j;
		errs = new ArrayList<String>();
		msgs = new ArrayList<String>();
	}
	
	public CluedoProtokollChecker(JSONObject j) {
		jsonRoot = new CluedoJSON(j);
		errs = new ArrayList<String>();
		msgs = new ArrayList<String>();
	}
	
	public String getType(){
		return type;
	}
	
	public NetworkHandhakeCodes validateExpectedType(String exptype,String[] ignoredTypes){
		checkType();
		if (type.equals(exptype)) 
			if (validate()) return NetworkHandhakeCodes.OK; //alles OK
			else return NetworkHandhakeCodes.TYPEOK_MSGERR; //typ ok aber andere protokollabweichungen
		else if (ignoredTypes != null && Arrays.asList(ignoredTypes).contains(type)){
			setMsg(type+" is ignored");
			return NetworkHandhakeCodes.TYPEIGNORED;//ignored
		}
		
		setErr(exptype +" : is expected, found :"+type);		
		if (validate()) return NetworkHandhakeCodes.TYPEERR_MSGOK;			
			
		return NetworkHandhakeCodes.TYPERR_MSGERR; // falscher typ nichtprotokollgemäße nachricht
	}
	
	private void invokeValMethod(){
		if (type != null){
			try {
				Method m = CluedoProtokollChecker.class
						.getDeclaredMethod("val_" + typeNoSpace);
				try {
					try {
						m.invoke(this);
					} catch (IllegalAccessException | IllegalArgumentException e) {
						e.printStackTrace();
					}
					//auxx.logfine("invoking :"+"val_" + typeNoSpace);
				} 
				catch (InvocationTargetException e) {
					auxx.logsevere("invoking :"+"val_" + typeNoSpace +" failed",e);
				}
			} catch (NoSuchMethodException | SecurityException e) {
				auxx.logsevere("invoking :"+"val_" + typeNoSpace +" failed : no such method",e);
			}
		}
	}
	
	public boolean validate() {
		if (checkType()) { // sets type to check
			invokeValMethod();
		};

		if (errs.size() == 0){
			//auxx.logfine("msg OK");
			return true;
		}
		auxx.loginfo("msg NOT OK");
		auxx.loginfo(getErrString());
		
		return false;
	}

	// type is set
	void val_login() {
		validateValue(jsonRoot, "nick");
		validateValue(jsonRoot, "group");
		if (validateValue(jsonRoot, "version"))
			validateProtokollVersion(jsonRoot,"version");
		isJSONArrayOfType(jsonRoot, "expansions", "string");
	}

	void val_login_successful() {		
		isJSONArrayOfType(jsonRoot, "expansions", "string");
		isJSONArrayOfType(jsonRoot, "nick array", "string");
		isJSONArrayOfType(jsonRoot, "game array", "gameinfo");
	}

	void val_user_added() {
		validateValue(jsonRoot, "nick");
	}

	void val_disconnect() {}

	void val_disconnected() {
		validateValue(jsonRoot, "message");
	}

	void val_user_left() {
		validateValue(jsonRoot, "nick");
	}

	void val_ok() {}

	void val_error() {
		validateValue(jsonRoot, "message");
	}

	void val_chat() {
		//validateValue(jsonRoot, "sender");
		//validateValue(jsonRoot, "message");
		if (!jsonRoot.has("message")) //allow messsage to be empty
			setErr("key : message expected");
		if (validateValue(jsonRoot, "timestamp"))
			validateLocalTimeFormat(jsonRoot, "timestamp");
	}

	void val_create_game() {
		if (validateValue(jsonRoot, "color"))
			validatePerson(jsonRoot.getString("color"));
	}

	void val_game_created() {
		if (validateValue(jsonRoot, "gameID"))
			isInt(jsonRoot, "gameID");
//		if (validateValue(jsonRoot, "player"))
//			validatePlayerInfo(jsonRoot.getJSONObject("player"));
	}

	void val_join_game() {
		if (validateValue(jsonRoot, "gameID"))
			isInt(jsonRoot, "gameID");
		if (validateValue(jsonRoot, "color"))
			validatePerson(jsonRoot.getString("color"));
	}

	void val_player_added() {
		if (validateValue(jsonRoot, "gameID"))
			isInt(jsonRoot, "gameID");
		if (validateValue(jsonRoot, "player"))
			validatePlayerInfo(jsonRoot.getJSONObject("player"));
		
	}

	void val_watch_game() {
		if (validateValue(jsonRoot, "gameID"))
			isInt(jsonRoot, "gameID");
	}

	void val_watcher_added() {
		if (validateValue(jsonRoot, "gameID"))
			isInt(jsonRoot, "gameID");
		validateValue(jsonRoot, "nick");
	}
	
	void val_gameinfo(){
		if (validateValue(jsonRoot, "game"))
			validateGameInfo(jsonRoot.getJSONObject("game"));
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
		if (validateValue(jsonRoot, "gamestate"))
			validateGameState(jsonRoot.getString("gamestate"), "started");
		isJSONArrayOfType(jsonRoot, "order", "string");
	}

	void val_game_ended() {
		val_game_created();
		if (validateValue(jsonRoot, "statement"))
			validateStatement(jsonRoot.getJSONObject("statement"));

	}

	void val_stateupdate() {
		val_game_created();
		if (validateValue(jsonRoot, "player"))
			validatePlayerInfo(jsonRoot.getJSONObject("player"));
	}
	
	void val_player_cards(){
		val_watch_game();
		if (validateValue(jsonRoot, "cards"))
			isJSONArrayOfType(jsonRoot, "cards", "cards");
	}

	void val_dice_result() {
		val_watch_game();
		if (validateValue(jsonRoot,"result"))
			validateDiceResult(jsonRoot,"result");
	}
	
	void val_poolcards(){
		if(validateValue(jsonRoot, "cards"))
			isJSONArrayOfType(jsonRoot, "cards", "cards");
	}

	void val_moved() {
		val_watch_game();
		if (validateValue(jsonRoot, "person position"))
			validatePersonPos(jsonRoot.getJSONObject("person position"));
	}

	void val_suspicion() {
		val_watch_game();
		if (validateValue(jsonRoot, "statement"))
			validateStatement(jsonRoot.getJSONObject("statement"));
		
	}

	void val_disproved() {
		val_game_created();
//		if (validateValue(jsonRoot, "card"))
//			validateCards(jsonRoot.getString("card"));
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
		if (validateValue(jsonRoot, "field"))
			validateField(jsonRoot, "field");
	}

	void val_secret_passage() {
		val_watch_game();
	}

	void val_suspect() {
		val_watch_game();
		validateStatement(jsonRoot.getJSONObject("statement"));

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
	
	void val_udp_server(){
		validateValue(jsonRoot,"group");
		if (validateValue(jsonRoot,"tcp port"))
			isInt(jsonRoot, "tcp port");
		
	}
	
	void val_udp_client(){
		validateValue(jsonRoot,"group");		
	}
	
	
	
	
	void validateLocalTimeFormat(JSONObject jsonParent,String key){
		String value = jsonParent.getString(key);		
		if (!value.matches("\\d+-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d*")) //"2015-04-08T15:16:23.42"
			setErr(value +" hat nicht protokollgemäßes (java.time.LocalDateTime) Format");
	}
	
	void validatePersonName(String personName){
		if (!Persons.isMemberPersonName(personName))
			setErr("Person :\" "+personName+ "\" is not a valid Personname in this Game");
	}
	
	void validateWeapon(String weaponName){
		if (!Weapons.isMember(weaponName))
			setErr("Weapon : \""+weaponName+ "\" is not a valid Weapon in this Game");
	}
	
	void validateRoom(String roomName){
		if (!Rooms.isMember(roomName))
			setErr("Room  : \""+roomName+ "\" not a valid Room in this Game");
	}
	
	void validatePlayer(String playerState){
		if (!PlayerStates.isMember(playerState))
			setErr("PlayerState : \""+playerState+ "\" not a valid PlayerState in this Game");
	}
	
	
	
	boolean validateProtokollVersion(JSONObject jsonParent,String key){
		if (jsonParent.getString(key).equals(Config.PROTOKOLL_VERSION)) return true;		
		return false;
 	}
	
	void validateDiceResult(JSONObject jsonParent,String key){
		try {
			int amountDices = 2;
			JSONArray diceres = jsonParent.getJSONArray(key);
			for (int i = 0; i < amountDices;i++)
				if (!(diceres.getInt(i)== diceres.getInt(i)))
					setErr("JSONArray : in JSONArray \""+key+"\" on index "+i+" : noInt");
			
		}
		catch (JSONException je){
			setErr("JSONArray expected : \""+key+"\" is not JSONArray");
			auxx.logsevere("is not a JSONArray dipshit ", je);
		}
	}
	
	/**
	 * @param gameState
	 * @param expectedState
	 * assuming expected state is valid
	 */
	void validateGameState(String gameState,String expectedState){
			if (!gameState.equals(expectedState))
				setErr("expected State :\""+ expectedState+"\"");
	}
	
	void validateGameState(String gameState){
		if (!GameStates.isMember(gameState))
			setErr("GameState \""+gameState+ "\" not a valid Gamestate in this Game");
	}
	
	void validatePerson(String color){
		if (!Persons.isMemberColor(color))
			setErr("Color \""+color+ "\" not a valid Color in this Game");
	}
	
	void validateStatement(JSONObject jsonParent){
		if (validateValue(jsonParent, "person"))
			validatePerson(jsonParent.getString("person"));
		if (validateValue(jsonParent, "weapon"))
			validateWeapon(jsonParent.getString("weapon"));
		if (validateValue(jsonParent, "room"))
			validateRoom(jsonParent.getString("room"));
	}
	
	void validateField(JSONObject jsonParent, String key){
		JSONObject json = jsonParent.getJSONObject(key);
		if (validateValue(json, "x"))
			Field.isValidX(json.getInt("x"));
		if (validateValue(json, "y"))
			Field.isValidY(json.getInt("y"));
	}
	
	boolean validateValue(JSONObject jsonParent, String key) {
		if (jsonParent.has(key))
			if (jsonParent.get(key).toString().length() > 0)
				return true;
			else
				setErr("value of key : \""+ key + "\" is empty");
		else
			setErr("key : \""+key + "\" expected in :\n"+jsonParent.toString());

		return false;
	}
	
	boolean validateCards(String value){
		Persons[] persons = Persons.values();
		for(Persons p : persons) if (value.equals(p.getColor())) return true;
		Weapons[] weapons = Weapons.values();
		for(Weapons w : weapons) if (value.equals(w.getName())) return true;
		Rooms[] rooms = Rooms.values();
		for(Rooms r : rooms) if (value.equals(r.getName())) return true;
		
		return false;
	}
	/**
	 * @param jsonParent
	 * 
	 * 	
	 *  */
	void validatePlayerInfo(JSONObject jsonParent){		
			validateValue(jsonParent, "nick");			
			if (validateValue(jsonParent, "color"))
				validatePerson(jsonParent.getString("color"));
//			if (validateValue(jsonParent, "field"))
//				validateField(jsonParent, "field");
//			if (validateValue(jsonParent, "cards"))
//				isInt(jsonParent, "cards");
			if (jsonParent.has("playerstate"))
				if (validateValue(jsonParent, "playerstate"))
					validatePlayerStates(jsonParent,"playerstate");		
	}
	
	void validatePlayerStates(JSONObject parent,String key){
		isJSONArrayOfType(parent, key, "playerstate");
	}
	
	void validateGameInfo(JSONObject jsonParent) throws JSONException{
		if (validateValue(jsonParent, "gameID"))
			isInt(jsonParent, "gameID");
		validateGameState(jsonParent.getString("gamestate"));
		isJSONArrayOfType(jsonParent,"players", "playerinfo");
		isJSONArrayOfType(jsonParent,"watchers", "string");
//		isJSONArrayOfType(jsonParent,"person positions", "personpos");
//		isJSONArrayOfType(jsonParent,"weapon positions", "weaponpos");			

	}
	
	void validatePersonPos(JSONObject jsonParent){
		if (validateValue(jsonParent, "person"))
			validatePerson(jsonParent.getString("person"));
		if (validateValue(jsonParent, "field"))
			validateField(jsonParent, "field");
	}
	
	void validateWeaponPos(JSONObject jsonParent){
		if (validateValue(jsonParent, "weapon"))
			validateWeapon(jsonParent.getString("weapon"));
		if (validateValue(jsonParent, "field"))
			validateField(jsonParent, "field");
	}
	
	private void validatePlayerState(String playerstate){
		if (!PlayerStates.isMember(playerstate))
			setErr("Playerstate \""+playerstate+ "\" not a valid Playerstate in this Game");
			
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
						case  "playerstate" :
							validatePlayerState(jar.getString(index));
							break;
						case "weaponpos" :
							validateWeaponPos(jar.getJSONObject(index));
							break;
						case "personpos" :
							validatePersonPos(jar.getJSONObject(index));
							break;
						case "string" :
							;
						default :
							;
					}					
				} 
				catch (Exception e) {
					auxx.logsevere("no JSONArray index :"+index, e);
				}				
			}		
			
			return true;			
		} 
		catch (JSONException e) {
			setErr("JSONArray: \""+key+"\" expected  in "+jsonParent.toString());
			return false;
		}		
	}

	boolean isInt(JSONObject jsonParent,String key) {
		try {
			jsonParent.getInt(key);
			return true;
		} catch (JSONException je) {
			setErr("value of \"" + key + "\" is not of type int");
			return false;
		}
	}

	boolean checkType() {
		if (!jsonRoot.has("type")) {
			errs.add("message type not set");
			return false;
		} 
		else if (!CluedoProtokollMessageTypes.isMember(jsonRoot.getString("type"))) {
			errs.add("type " + jsonRoot.getString("type")
					+ " is not part of protokoll v." + Config.PROTOKOLL_VERSION);
			return false;
		}

		this.typeNoSpace = jsonRoot.getString("type").replaceAll(" ", "_");
		this.type = jsonRoot.getString("type");
		return true;
	}
	
	public ArrayList getErrs(){
		return errs;
	}
	
	public boolean isValid(){
		return errs.size() == 0;
	}
	
	public void printErrs() {
		for (String s : errs)
			auxx.loginfo(s);
		for (String s : msgs)
			auxx.loginfo(s);
	}

	private void setErr(String err) {
		errs.add(err);
	}
	
	private void setMsg(String msg) {
		errs.add(msg);
	}
	
	public String getErrString(){
		StringBuffer sb = new StringBuffer("");
		if (!isValid())
			for (String err : errs) sb.append(err+"\n");
		return sb.toString();
	}
	
	public String getAllString(){
		StringBuffer sb = new StringBuffer("");
		sb.append(getErrString());		
			for (String msg : msgs) sb.append(msg+"\n");
		
		return sb.toString();
	}
	
	public JSONObject getMessage(){
		return jsonRoot;
	}
}