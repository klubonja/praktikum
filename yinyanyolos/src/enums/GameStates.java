package enums;

public enum GameStates {
	
	started("started"),
	not_started("not started"),
	ended("ended");
	
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
}
