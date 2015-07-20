package cluedoNetworkLayer;

import java.util.ArrayList;

import cluedoServer.ClientItem;
import enums.Persons;
import enums.PlayerStates;

public class CluedoPlayer {
	
	Persons cluedoPerson;
	ArrayList<PlayerStates>  possibleStates;
	CluedoPosition position;
	String nick = "";
	ArrayList<String> cards;
	boolean hasAccused = false;
	
	
	
	public CluedoPlayer(Persons pers,PlayerStates s, CluedoPosition p) {
		cluedoPerson = pers;
		possibleStates = new ArrayList<PlayerStates>();
		position = p;
		possibleStates.add(s);
		cards = new ArrayList<String>();
	}
	
	
	
	public CluedoPlayer(Persons pers,PlayerStates s) {
		possibleStates = new ArrayList<PlayerStates>();
		position = new CluedoPosition(cluedoPerson.getStartposition().getX(),
									 cluedoPerson.getStartposition().getY());
		cluedoPerson = pers;
		possibleStates.add(s);
		cards = new ArrayList<String>();
		
	}
	
	public boolean hasAccused(){
		return hasAccused;
	}
	
	public void setAccused(boolean accused){
		hasAccused = accused;
	}
	
	public void setPossibleState(PlayerStates state){
		possibleStates = new ArrayList<PlayerStates>();
		possibleStates.add(state);
	}
	
	public void addPossibleState(PlayerStates state){
		possibleStates.add(state);
	}
	
	public void setCurrentStates(ArrayList<PlayerStates> states) {
		//this.currentState = currentState;
		possibleStates = states;
	}
	
//	public PlayerStates getCurrentState() {
//		return currentState;
//	}
	
	public void setCards(ArrayList<String> cards) {
		this.cards = cards;
	}
	
	public void setCards(String[] cardsIN) {
		for (String c: cardsIN)
			cards.add(c);
	}
	
	public ArrayList<String> getCards() {
		return cards;
	}
	
	
	
	public void setNewPosition(int x,int y) {
		this.position.setX(x);
		this.position.setY(y);
	}
	
	public void setNewPosition(CluedoPosition newpos) {
		position = newpos;
	}
	
	public void setPossibleStates(ArrayList<PlayerStates> newstates) {
		this.possibleStates = newstates;
	}
	
	public void setPossibleStatesFromStringList(ArrayList<String> newStatesString) {
		ArrayList<PlayerStates> newPlayerStates = new ArrayList<PlayerStates>();
		for(String newstateString: newStatesString)
			newPlayerStates.add(PlayerStates.getPlayerState(newstateString));
		this.possibleStates = newPlayerStates;
	}
	
	public ArrayList<PlayerStates> getPossibleStates() {
		return possibleStates;
	}
	
	public ArrayList<String> getStatesStringList() {
		ArrayList<String> sl = new ArrayList<String>();
		for (PlayerStates s: possibleStates)
			sl.add(s.getName());
		
		return sl;
	}
	
	public CluedoPosition getPosition() {
		return position;
	}
	
	public Persons getCluedoPerson() {
		return cluedoPerson;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public String getNick() {
		return nick;
	}
	
	public String getStatesAsStringFormatted(){
		String ausgabe = new String();
		for (int i = 0; i < possibleStates.size();i++){
			if (i == possibleStates.size())  ausgabe += "und "+possibleStates.get(i).getName();
			else if (i == possibleStates.size()-1) ausgabe += possibleStates.get(i).getName()+" ";
			else ausgabe += possibleStates.get(i).getName() + " ,";
		}
		return ausgabe;
	}
	
	public void removeFromPossibleStates(PlayerStates state){
		possibleStates.remove(state);
	}

}
