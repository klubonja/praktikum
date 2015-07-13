package yoloKI;

import java.util.ArrayList;

import enums.Persons;
import enums.Rooms;
import enums.Weapons;

public class KIPerson {

	ArrayList<Persons> personen;
	ArrayList<Weapons> waffen;
	ArrayList<Rooms> raeume;
	
	ArrayList<Persons> nichtPersonen;
	ArrayList<Weapons> nichtWaffen;
	ArrayList<Rooms> nichtRaeume;
	
	ArrayList<Persons> vielleichtPersonen;
	ArrayList<Weapons> vielleichtWaffen;
	ArrayList<Rooms> vielleichtRaeume;
	
	public KIPerson(){
		
	}
	
	public boolean hatPerson(Persons person){
		if (personen.contains(person)){
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean hatWaffe(Weapons waffe){
		if (waffen.contains(waffe)){
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean hatRaum(Rooms raum){
		if (raeume.contains(raum)){
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean hatVielleichtPerson(Persons person){
		if (vielleichtPersonen.contains(person)){
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean hatVielleichtWaffe(Weapons waffe){
		if (vielleichtWaffen.contains(waffe)){
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean hatVielleichtRaum(Rooms raum){
		if (vielleichtRaeume.contains(raum)){
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addNichtKombination(Persons person, Weapons waffe, Rooms raum){
		personen.add(person);
		waffen.add(waffe);
		raeume.add(raum);
	}
	
	public void addPerson(Persons person){
		personen.add(person);
	}
	
	public void addWaffe(Weapons waffe){
		waffen.add(waffe);
	}
	
	public void addRaum(Rooms raum){
		raeume.add(raum);
	}
	
	public void addNichtPerson(Persons person){
		nichtPersonen.add(person);
	}
	
	public void addNichtWaffe(Weapons waffe){
		nichtWaffen.add(waffe);
	}
	
	public void addNichtRaum(Rooms raum){
		nichtRaeume.add(raum);
	}
	
	public void addVielleichtPerson(Persons person){
		vielleichtPersonen.add(person);
	}
	public void addVielleichtWaffe(Weapons waffe){
		vielleichtWaffen.add(waffe);
	}
	public void addVielleichtRaum(Rooms raum){
		vielleichtRaeume.add(raum);
	}
	
	public ArrayList<Persons> wirWissenNichtPersonen(){
		ArrayList<Persons> wissenNichtPersonen = new ArrayList<Persons>();
		for (Persons person : Persons.values()){
			if ( ! personen.contains(person) && ! nichtPersonen.contains(person) && ! vielleichtPersonen.contains(person)){
				wissenNichtPersonen.add(person);
			}
		}
		return wissenNichtPersonen;
	}
	
	public ArrayList<Weapons> wirWissenNichtWaffen(){
		ArrayList<Weapons> wissenNichtWaffen = new ArrayList<Weapons>();
		for (Weapons waffe : Weapons.values()){
			if ( ! waffen.contains(waffe) && ! nichtWaffen.contains(waffe) && ! vielleichtWaffen.contains(waffe)){
				wissenNichtWaffen.add(waffe);
			}
		}
		return wissenNichtWaffen;
	}
	
	public ArrayList<Rooms> wirWissenNichtRaeume(){
		ArrayList<Rooms> wissenNichtRaeume = new ArrayList<Rooms>();
		for (Rooms raum : Rooms.values()){
			if ( ! raeume.contains(raum) && ! nichtRaeume.contains(raum) && ! vielleichtRaeume.contains(raum)){
				wissenNichtRaeume.add(raum);
			}
		}
		return wissenNichtRaeume;
	}
	
	
}
