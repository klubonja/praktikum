package json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.*;

public class CluedoProtokollChecker {
	
	private ArrayList<String> errs;
	CluedoJSON json;
	String type;
	boolean isValid;
	double protokollVersion = 1;
	Map<String, Method> methodMap = new HashMap<String, Method>(); 
	
	public CluedoProtokollChecker(CluedoJSON json){
		json = json; 
		isValid = false;
		errs = new ArrayList<String>();
	}
	
	public boolean validate(){
		if (checkType()){ //sets type to check
			CluedoJSONTypes[] types = CluedoJSONTypes.values();
			for (CluedoJSONTypes t : types){				
				try {//maps cluedotypes to val_methods
					methodMap.put(t.getNameNoSpace(), CluedoProtokollChecker.class.getDeclaredMethod("val_"+t.getNameNoSpace()));
				} catch (NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for (CluedoJSONTypes t : types){				
				try {
					methodMap.get(t.getNameNoSpace()).invoke(this);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
		};
		
		if (errs.size() == 0) return true;
		 return false;
	}
	
	

     
   


	
	//type is set
	void val_login(){
		if (!json.has("nick")) setErr("nick expected");
		if (!json.has("group")) setErr("group expected");
		if (!json.has("expansions")) setErr("expansions expected");
	}
	
	
	void val_login_successful(){
		if (!json.has("expansions")) setErr("expansions expected");
		if (!json.has("nick array")) setErr("nick expected");
		if (!json.has("game array")) setErr("group expected");
	}
	void val_user_added(){
		if (!json.has("user added")) setErr("user added expected");
		if (!json.has("nick")) setErr("nick expected");	
	}
	void val_disconnect(){

	}
	void val_disconnected(){
		if (!json.has("message")) setErr("message expected");
	}
	void val_user_left(){
		if (!json.has("nick")) setErr("nick expected");
	}
	void val_ok(){

	}
	void val_error(){

	}
	void val_chat(){
		if (!json.has("sender")) setErr("sender expected");
		if (!json.has("message")) setErr("message expected");
		if (!json.has("timestamp")) setErr("timestamp expected");

	}
	void val_create_game(){
		if (!json.has("color")) setErr("color expected");
	}
	
	void val_game_created(){
		if (!json.has("gameID")) setErr("gameID expected");
		if (!json.has("nick")) setErr("message expected");
	}
	void val_join_game(){

	}
	void val_player_added(){

	}
	void val_watch_game(){

	}
	void val_watcher_added(){

	}
	void val_leave_game(){

	}
	void val_left_game(){

	}
	void val_game_deleted(){

	}
	void val_start_game(){

	}
	void val_game_started(){

	}
	void val_game_ended(){

	}
	void val_stateupdate(){

	}
	void val_dice_result(){

	}
	void val_moved(){

	}
	void val_suspicion(){

	}
	void val_disproved(){

	}
	void val_no_disprove(){

	}
	void val_wrong_accusation(){

	}
	void val_roll_dice(){

	}
	void val_move(){

	}
	void val_secret_passage(){

	}
	void val_suspect(){
		if (!json.has("person")) setErr("person expected");
		if (!json.has("weapon")) setErr("weapon expected");
		if (!json.has("room")) setErr("room expected");
	}
	void val_disprove(){

	}
	void val_end_turn(){

	}
	void val_accuse(){

	}
	
	
	
	boolean checkType(){
		
		if (!json.has("type")){
			errs.add("message type not set");
			return false;
		}
		else if (!CluedoJSONTypes.isMember(json.getString("type"))){
			errs.add("type "+type+" is not part of protokoll v."+protokollVersion);
			return false;
		}
		
		this.type = json.getString("type");
		return true;		
	}
	public void printErrs() {
		for (String s : errs) System.out.println(s);
	}
	
	private void setErr(String err){
		errs.add(err);
	}
}