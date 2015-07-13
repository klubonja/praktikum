package enums;

import java.util.ArrayList;

public enum Weapons {
	
	dagger("dagger"),
	candlestick("candlestick"),
	revolver("revolver"),
	rope("rope"),
	pipe("pipe"),
	spanner("spanner")
	;
	
	private String name;
	
	Weapons(String name){
		this.name = name;
	}
	
	public String getName() {
        return this.name;
    }
	
	 static public boolean isMember(String aName) {
        Weapons[] weapons = Weapons.values();
        for (Weapons weapon  : weapons)
            if (weapon.getName().equals(aName))
                return true;
        return false;
    }
	 
	static public Weapons getWeaponByName(String aName) {
	        Weapons[] weapons = Weapons.values();
	        for (Weapons weapon  : weapons)
	            if (weapon.getName().equals(aName))
	                return weapon;
	        
	        return null;
	   }
	 
	 public static ArrayList<String> getWeaponsString() {        
	        Weapons[] weapons = Weapons.values();
	        ArrayList<String> names = new ArrayList<String>();
	        for (Weapons r: weapons)
	        	names.add(r.getName());
	        return names;
	    }
}
