package json;

public enum CluedoJSONTypes {
	
	//Verbindung
	login("login"),
	login_successful("login successful"),
	user_added("user added"),
	disconnect("disconnect"),
	disconnected("disconnected"),
	user_left("user_left"),
	//Bestätigungen und Fehler
	ok("ok"),
	error("error"),
	chat("chat"),
	//Konfiguration und Spielstart
	create_game("create_game"),
	join_game("join game"),
	player_added("player_added"),
	watch_game("watch game"),
	watcher_added("watcher added"),
	leave_game("leave_game"),
	left_game("left game"),
	game_deleted("game deleted"),
	start_game("start game"),
	
	//Spielzustand
	game_started("game started"),
	game_ended("game ended"),
	game_created("game created"),
	
	// Statusupdate eines Spielers
	statupdate("stateupdate"),	
	dice_result("dice result"),
	moved("moved"),
	suspicion("suspicion"),
	disproved("disproved"),
	no_disprove("no disprove"),
	wrong_accusation("wrong accusation"),
	roll_dice("roll dice"),
	move("move"),
	secret_passage("secret passage"),
	suspect("suspect"),
	disprove("disprove"),
	end_turn("end turn"),
	accuse("accuse");
	
	
	
	
	
	private String name;

    CluedoJSONTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    
    public String getNameNoSpace() {
        return this.name.replaceAll(" ", "_");
    }  
    
    static public boolean isMember(String aName) {
        CluedoJSONTypes[] types = CluedoJSONTypes.values();
        for (CluedoJSONTypes type  : types)
            if (type.getName().equals(aName))
                return true;
        return false;
    }
}
