package json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.*;

import org.json.JSONArray;
import org.json.JSONException;

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
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

		if (errs.size() == 0)
			return true;
		return false;
	}

	// type is set
	void val_login() {
		validateField("nick");
		validateField("group");
		validateField("expansions");
	}

	void val_login_successful() {
		if(validateField("expansions"))
			isJSONArray("expansions");
		if (validateField("nick array"))
			isJSONArray("nick array");
		if (validateField("game array"))
			isJSONArray("game array");
	}

	void val_user_added() {
		validateField("user added");
		validateField("nick");
	}

	void val_disconnect() {

	}

	void val_disconnected() {
		validateField("message");
	}

	void val_user_left() {
		validateField("nick");
	}

	void val_ok() {

	}

	void val_error() {

	}

	void val_chat() {
		validateField("sender");
		validateField("message");
		validateField("timestamp");
	}

	void val_create_game() {
		validateField("color");
	}

	void val_game_created() {
		validateField("gameID");
		validateField("nick");
	}

	void val_join_game() {

	}

	void val_player_added() {

	}

	void val_watch_game() {

	}

	void val_watcher_added() {

	}

	void val_leave_game() {

	}

	void val_left_game() {

	}

	void val_game_deleted() {

	}

	void val_start_game() {

	}

	void val_game_started() {

	}

	void val_game_ended() {

	}

	void val_stateupdate() {

	}

	void val_dice_result() {

	}

	void val_moved() {

	}

	void val_suspicion() {

	}

	void val_disproved() {

	}

	void val_no_disprove() {

	}

	void val_wrong_accusation() {

	}

	void val_roll_dice() {

	}

	void val_move() {

	}

	void val_secret_passage() {

	}

	void val_suspect() {
		validateField("person");
		validateField("weapon");
		validateField("room");
	}

	void val_disprove() {

	}

	void val_end_turn() {

	}

	void val_accuse() {

	}
	
	void validatePerson(String personName){
		if (!Persons.isMember(personName))
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
	
	void validateGameState(){
		
	}
	
	void validatePlayerInfo(){
			
	}
	
	boolean validateField(String key) {
		if (json.has(key))
			if (json.get(key).toString().length() > 0)
				return true;
			else
				setErr(key + " is empty");
		else
			setErr(key + " expected");

		return false;
	}

	boolean isJSONArray(String key) {
		if (json.get(key) instanceof JSONArray)
			return true;
		setErr("JSONArray expected");
		return false;
	}

	boolean isInt(String key) {
		try {
			json.getInt(key);
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
		for (String s : errs)
			System.out.println(s);
	}

	private void setErr(String err) {
		errs.add(err);
	}
}