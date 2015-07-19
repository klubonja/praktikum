package cluedoNetworkLayer;

import enums.Persons;
import enums.Rooms;
import enums.Weapons;

public class CluedoStatement {
	Persons person;
	Rooms room;
	Weapons weapon;
	
	public CluedoStatement(Persons p, Weapons w,Rooms r) {
		person = p;
		weapon = w;
		room = r;
	}
	
	public CluedoStatement(String p, String  w,String r) {
		person = Persons.getPersonByColor(p);
		weapon = Weapons.getWeaponByName(w);
		room = Rooms.getRoomByName(r);
	}
	
	public Persons getPerson() {
		return person;
	}
	
	public Rooms getRoom() {
		return room;
	}
	
	public Weapons getWeapon() {
		return weapon;
	}
	
	public boolean equals(CluedoStatement tbcmp){
		if (tbcmp.getPerson() != getPerson() || tbcmp.getWeapon() != getWeapon() || tbcmp.getRoom() != getRoom())
			return false;
		return true;
	}
}
