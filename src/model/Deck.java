package model;

import java.util.ArrayList;
import java.util.List;

import staticClasses.auxx;
import enums.Persons;
import enums.Rooms;
import enums.Weapons;

/**
 * Eine Model Klasse die das Deck repraesentiert und alle Karten auffasst.
 * 
 * @author NedkoChulev
 *
 */

public class Deck {
	
	int amountPlayers;
	String[] poolCards;
	String[][] playerHands;
	String[] winningHand;
	
	private List<String> persons = new ArrayList<String>();
	private List<String> weapons = new ArrayList<String>();
	private List<String> rooms = new ArrayList<String>();
	private List<String> deck;
	
	public Deck(int amtp) {
		amountPlayers = amtp;
		deck = new ArrayList<String>();
		rooms = Rooms.getRoomsString();
		persons = Persons.getPersonsString();
		weapons = Weapons.getWeaponsString();
	}
	
	public void makeDeck(){
		rooms.remove("pool");
		deck.addAll(rooms);
		deck.addAll(weapons);
		deck.addAll(persons);
	}
	
	public void dealCluedoCards(){

		makeDeck();
		
		winningHand = new String[3];
		winningHand[0] = choseRandom(persons);
		winningHand[1] = choseRandom(weapons);
		winningHand[2] = choseRandom(rooms);
		
		
		int playerModulo = deck.size()%amountPlayers;		
		poolCards = choseNRandom(playerModulo,deck);
		
		int handSize = deck.size()/amountPlayers;
		playerHands = new String[amountPlayers][handSize];
		for (int i = 0; i < amountPlayers; i++)
			playerHands[i] = choseNRandom(handSize,deck);		
	}
	
	public String[] getPoolCards() {
		return poolCards;
	}
	
	public String[][] getPlayerHands() {
		return playerHands;
	}
	
	public String[] getWinningHand() { // person,weapon,room
		return winningHand;
	}

	public String choseRandom(List<String> list) {
		if (list.size() < 0) return null;
		int index = auxx.getRandInt(0, list.size()-1);
		String card =  list.get(index);
		list.remove(index);
		
		return card;
	}
	
	public String[] choseNRandom(int n,List<String> list){
		if (n > deck.size()) return null;
		String[] dieantwoord = new String[n];
		while (n > 0) {
			dieantwoord[n-1] = choseRandom(list);
			n--;
		}
		
		return dieantwoord;
	}

	public List<String> getDeck() {
		return this.deck;
	}

	public List<String> getPersons() {
		return persons;
	}

	public List<String> getWeapons() {
		return weapons;
	}

	public List<String> getRooms() {
		return rooms;
	}

	public void setPersons(List<String> persons) {
		this.persons = persons;
	}

	public void setWeapons(List<String> weapons) {
		this.weapons = weapons;
	}

	public void setRooms(List<String> rooms) {
		this.rooms = rooms;
	}
}