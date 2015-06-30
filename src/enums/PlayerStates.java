package enums;

public enum PlayerStates {
	
	do_nothing("do nothing"),
	use_secret_passage("use secret passage"),
	roll_dice("roll dice"),
	move("move"),
	suspect("suspect"),
	disprove("disprove"),
	end_turn("end turn"),
	disprove_trap("disprove trap"),
	do_nothing_trap("do nothing trap");
	
	private String name;
	
	PlayerStates(String name){
		this.name = name;
	}
	
	public String getName() {
        return this.name;
    }
	
	 static public boolean isMember(String aName) {
        PlayerStates[] playerStates = PlayerStates.values();
        for (PlayerStates playerState  : playerStates)
            if (playerState.getName().equals(aName))
                return true;
        return false;
    }
	 
	 static public PlayerStates getPlayerState(String name) {
	        PlayerStates[] pstates = PlayerStates.values();
	        for (PlayerStates pstate  : pstates)
	            if (pstate.getName().equals(name))
	                return pstate;
	        return null;
	    }
}
