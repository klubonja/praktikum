package enums;

public enum PlayerStates {
	
	do_nothing("do nothing"),//0
	use_secret_passage("use secret passage"),//1
	roll_dice("roll dice"),//2
	move("move"),//3
	suspect("suspect"),//4
	disprove("disprove"),//5
	end_turn("end turn"),//6
	accuse("accuse"),//7
	//disprove_trap("disprove trap"),
	//do_nothing_trap("do nothing trap")
	;
	
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
