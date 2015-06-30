package enums;

public enum GameStates {
	
	started("started"),
	not_started("not started"),
	//startable("startable"),
	ended("ended")
	//to_be_deleted("to be deleted");
	;
	private String name;
	
	GameStates(String name){
		this.name = name;
	}
	
	public String getName() {
        return this.name;
    }
	
	 static public boolean isMember(String aName) {
        GameStates[] gameStates = GameStates.values();
        for (GameStates gameState  : gameStates)
            if (gameState.getName().equals(aName))
                return true;
        return false;
    }
	 static public GameStates getState(String aName) {
	        GameStates[] gameStates = GameStates.values();
	        for (GameStates gameState  : gameStates)
	            if (gameState.getName().equals(aName))
	                return gameState;
	        return null;
	    }
}
