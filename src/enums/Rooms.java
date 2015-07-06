package enums;

import java.util.ArrayList;


public enum Rooms {
	
	hall("hall"),
	lounge("lounge"),
	diningroom("diningroom"),
	kitchen("kitchen"),
	ballroom("ballroom"),
	conservatory("conservatory"),
	billiard("billiard"),
	library("library"),
	study("study"),
	pool("pool")
	;
	
	
	private String name;
	
	Rooms(String name){
		this.name = name;
	}
	
	public String getName() {
        return this.name;
    }
	
	public static ArrayList<String> getRoomsString() {        
        Rooms[] rooms = Rooms.values();
        ArrayList<String> rnames = new ArrayList<String>();
        for (Rooms r: rooms)
        	rnames.add(r.getName());
        return rnames;
    }
	
	 static public boolean isMember(String aName) {
        Rooms[] rooms = Rooms.values();
        for (Rooms room  : rooms)
            if (room.getName().equals(aName))
                return true;
        return false;
    }
	 
	 static public Rooms getRoomByName(String aName) {
        Rooms[] rooms = Rooms.values();
        for (Rooms room  : rooms)
            if (room.getName().equals(aName))
              return room;
       return null;
    }
}
