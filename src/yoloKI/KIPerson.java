package yoloKI;

import java.util.ArrayList;

import enums.Persons;
import enums.Rooms;
import enums.Weapons;

public class KIPerson {

	ArrayList<Persons> personen = new ArrayList<Persons>();
	ArrayList<Weapons> waffen = new ArrayList<Weapons>();
	ArrayList<Rooms> raeume = new ArrayList<Rooms>();
	
	ArrayList<Persons> nichtPersonen = new ArrayList<Persons>();
	ArrayList<Weapons> nichtWaffen = new ArrayList<Weapons>();
	ArrayList<Rooms> nichtRaeume = new ArrayList<Rooms>();
	
	ArrayList<Persons> vielleichtPersonen = new ArrayList<Persons>();
	ArrayList<Weapons> vielleichtWaffen = new ArrayList<Weapons>();
	ArrayList<Rooms> vielleichtRaeume = new ArrayList<Rooms>();
	
	public KIPerson(){
		
	}
	
	public boolean hatPerson(Persons person){
		return personen.contains(person);
	}
	
	public boolean hatWaffe(Weapons waffe){
		return waffen.contains(waffe);
	}
	
	public boolean hatRaum(Rooms raum){
		return raeume.contains(raum);
	}
	
	public boolean hatVielleichtPerson(Persons person){
		return vielleichtPersonen.contains(person);
	}
	
	public boolean hatVielleichtWaffe(Weapons waffe){
		return vielleichtWaffen.contains(waffe);
	}
	
	public boolean hatVielleichtRaum(Rooms raum){
		return vielleichtRaeume.contains(raum);
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
