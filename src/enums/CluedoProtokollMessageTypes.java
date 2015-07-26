package enums;

public enum CluedoProtokollMessageTypes {
	
	//Verbindung
	login("login"),
	login_successful("login successful"),
	user_added("user added"),
	disconnect("disconnect"),
	disconnected("disconnected"),
	user_left("user left"),
	udp_server("udp server"),
	udp_client("udp client"),
	//Bestätigungen und Fehler
	ok("ok"),
	error("error"),
	chat("chat"),
	
	//Konfiguration und Spielstart
	create_game("create game"),
	join_game("join game"),
	player_added("player added"),
	watch_game("watch game"),
	watcher_added("watcher added"),
	leave_game("leave game"),
	left_game("left game"),
	game_deleted("game deleted"),
	start_game("start game"),
	player_cards("player_cards"),
	
	//Spielzustand
	game_started("game started"),
	game_ended("game ended"),
	game_created("game created"),
	gameinfo("gameinfo"),
	
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
	poolcards("poolcards"),
	accuse("accuse");
	
	
	
	
	
	private String name;

    CluedoProtokollMessageTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    
    public String getNameNoSpace() {
        return this.name.replaceAll(" ", "_");
    }  
    
    static public boolean isMember(String aName) {
        CluedoProtokollMessageTypes[] types = CluedoProtokollMessageTypes.values();
        for (CluedoProtokollMessageTypes type  : types)
            if (type.getName().equals(aName))
                return true;
        return false;
    }
}
