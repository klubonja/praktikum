package enums;

public enum PlayerStates {
	//DO NOT TOUCH ORDER !!!! IT WILL FUCK UP THE GAME IF YOU DO !!!!
	do_nothing("do nothing"),//0
	use_secret_passage("use secret passage"),//1
	roll_dice("roll dice"),//2
	move("move"),//3
	suspect("suspect"),//4	
	end_turn("end turn"),//5
	disprove("disprove"),//6
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
	 
	 public static PlayerStates getPlayerStateByOrdinal(int i){
		 PlayerStates[] states = PlayerStates.values();
		 for (PlayerStates ps: states)
			 if (ps.ordinal() == i) return ps;
		 
		 return null;
	 }
}
