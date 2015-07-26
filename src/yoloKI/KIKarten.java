package yoloKI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Deck;
import staticClasses.Config;
import staticClasses.auxx;
import cluedoNetworkLayer.CluedoStatement;
import enums.Persons;
import enums.Rooms;
import enums.Weapons;

public class KIKarten {

	private ArrayList<Rooms> raeume = new ArrayList<Rooms>();
	private ArrayList<Persons> personen = new ArrayList<Persons>();
	private ArrayList<Weapons> waffen = new ArrayList<Weapons>();
	private Deck deck;

	public KIKarten(ArrayList<String> mycards,int amtplayers){
		deck = new Deck(amtplayers);
		addKarten(mycards);
	}

	public void addRaum(Rooms raum){
		raeume.add(raum);
	}
	
	public void addPerson(Persons person){
		personen.add(person);
	}
	
	public void addWaffe(Weapons waffe){
		waffen.add(waffe);
	}
	
	
	/**
	 * 
	 * @return so viel Prozent
	 */
	public double wieVielProzentHabenWir(){
		double soVielProzent = 0;
		soVielProzent = soVielProzent * raeume.size() / Rooms.values().length;
		soVielProzent = soVielProzent * personen.size() / Persons.values().length;
		soVielProzent = soVielProzent * waffen.size() / Weapons.values().length;
		return soVielProzent;
	}
	
	/**
	 * 
	 * @return falls wir eine hohe Wahrscheinlichkeit haben, richtig zu liegen
	 */
	public boolean sindWirFertig(){
		if (wieVielProzentHabenWir() >= Config.HOWMANYOFDEMCARDS/Config.ALLOFDEMCARDS){
			return true;
		}
		else {
			return false;
		}
	}
	
	public ArrayList<Persons> welchePersonenHabenWirNicht(){
		ArrayList<Persons> dieseNicht = new ArrayList<Persons>();
		for (Persons person : Persons.values()){
			if ( ! (habenWirPerson(person) ) ){
				dieseNicht.add(person);
			}
		}
		return dieseNicht;
	}

	public ArrayList<Rooms> welcheRaeumeHabenWirNicht(){
		ArrayList<Rooms> dieseNicht = new ArrayList<Rooms>();
		for (Rooms raum : Rooms.values()){
			if ( ! (habenWirRaum(raum) ) ){
				dieseNicht.add(raum);
			}
		}
		return dieseNicht;
	}
	
	public ArrayList<Weapons> welcheWaffenHabenWirNicht(){
		ArrayList<Weapons> dieseNicht = new ArrayList<Weapons>();
		for (Weapons waffe : Weapons.values()){
			if ( ! (habenWirWaffe(waffe) ) ){
				dieseNicht.add(waffe);
			}
		}
		return dieseNicht;
	}
	
	
	public boolean habenWirKombination(Rooms raum, Persons person, Weapons waffe){
		if (habenWirRaum(raum) && habenWirPerson(person) && habenWirWaffe(waffe)){
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean habenWirRaum(Rooms raum){
		return raeume.contains(raum);
	}
	
	public boolean habenWirPerson(Persons person){
		return personen.contains(person);
	}
	
	public boolean habenWirWaffe(Weapons waffe){
		return waffen.contains(waffe);
	}
	
	public ArrayList<Rooms> getRaeume() {
		return raeume;
	}

	public void setRaeume(ArrayList<Rooms> raeume) {
		this.raeume = raeume;
	}

	public ArrayList<Persons> getPersonen() {
		return personen;
	}

	public void setPersonen(ArrayList<Persons> personen) {
		this.personen = personen;
	}

	public ArrayList<Weapons> getWaffen() {
		return waffen;
	}

	public void setWaffen(ArrayList<Weapons> waffen) {
		this.waffen = waffen;
	}
	
	public void addKarten(ArrayList<String> cards){
		for (String card: cards){
			if (Persons.isMemberPersonName(card)) addPerson(Persons.getPersonByName(card));
			if (Weapons.isMember(card)) addWaffe(Weapons.getWeaponByName(card));
			if (Rooms.isMember(card)) addRaum(Rooms.getRoomByName(card));
		}
	}
	
	public List<String> getWhatYouHaveAsStringList(){
		List<String> have = new ArrayList<String>();		
		for (Rooms r: raeume) have.add(r.getName());
		for (Persons p: personen) have.add(p.getPersonName());
		for (Weapons w: waffen) have.add(w.getName());
		
		return have;		
	}
		
	public List<String> getWhatYouDontHave(){
		List<String> dontave = deck.getDeck();
		dontave.removeAll(getWhatYouHaveAsStringList());
		return dontave;
	}
	
	public CluedoStatement makeAccusingRandStatement(){
		List<Rooms> rooms = Arrays.asList(Rooms.values());
		rooms.removeAll(getRaeume());
		Rooms room = rooms.get(auxx.getRandInt(0, rooms.size()-1));
		List<Persons> persons = Arrays.asList(Persons.values());
		persons.removeAll(getPersonen());
		Persons person = persons.get(auxx.getRandInt(0, persons.size()-1));
		List<Weapons> weapons = Arrays.asList(Weapons.values());
		weapons.removeAll(getWaffen());
		Weapons weapon = weapons.get(auxx.getRandInt(0, weapons.size()-1));
		
		return new CluedoStatement(person, weapon, room);		
	}
	
	
	
}
