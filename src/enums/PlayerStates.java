package enums;

public enum PlayerStates {
	
	do_nothing("do nothing"),
	roll_dice("roll dice"),
	roll_dice_or_use_secret_passage("roll dice or use secret passage"),
	move("move"),
	suspect("suspect"),
	disprove("disprove"),
	end_turn("end turn");
	
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
}
