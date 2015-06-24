package cluedoNetworkLayer;

import enums.Persons;
import enums.Rooms;
import enums.Weapons;

public class WinningStatement {
	Persons person;
	Rooms room;
	Weapons weapon;
	
	public WinningStatement(Persons p, Rooms r, Weapons w) {
		person = p;
		weapon = w;
		room = r;
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
}
