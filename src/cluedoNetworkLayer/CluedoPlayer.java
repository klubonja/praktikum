package cluedoNetworkLayer;

import java.util.ArrayList;

import stateManager.CluedoStateMachine;

import enums.Persons;
import enums.PlayerStates;

public class CluedoPlayer {
	
	Persons cluedoPerson;
	ArrayList<PlayerStates>  possibleStates;
	CluedoStateMachine dfa = new CluedoStateMachine(PlayerStates.do_nothing);
	PlayerStates currentState;
	CluedoPosition position;
	String nick = "";
	ArrayList<String> cards;
	boolean hasAccused = false;
	
	
	
	public CluedoPlayer(Persons pers,PlayerStates s, CluedoPosition p) {
		cluedoPerson = pers;
		if (s == PlayerStates.do_nothing) setDoNothing();
		else setCurrentState(s);
		position = p;
		cards = new ArrayList<String>();
	}
	
	public boolean hasAccused(){
		return hasAccused;
	}
	
	public CluedoPlayer(Persons pers,PlayerStates s) {
		cluedoPerson = pers;
		if (s == PlayerStates.do_nothing) setDoNothing();
		else setCurrentState(s);
		cards = new ArrayList<String>();
		position = new CluedoPosition(cluedoPerson.getStartposition().getX(),
									 cluedoPerson.getStartposition().getY());
	}
	
	
	public void setPossibleState(PlayerStates state){
		possibleStates = new ArrayList<PlayerStates>();
		possibleStates.add(state);
	}
	
	public void addPossibleState(PlayerStates state){
		possibleStates.add(state);
	}
	
	public void setDoNothing() {
		this.currentState = PlayerStates.do_nothing;
		possibleStates = new ArrayList<PlayerStates>();
		possibleStates.add(currentState);
	}
	
	public void setDisprove() {
		this.currentState = PlayerStates.disprove;
		possibleStates = new ArrayList<PlayerStates>();
		possibleStates.add(PlayerStates.disprove);
	}
	
	public void setCurrentState(PlayerStates currentState) {
		this.currentState = currentState;
		possibleStates = dfa.getSucStates(currentState);
	}
	
	public void setCurrentStates(ArrayList<PlayerStates> states) {
		//this.currentState = currentState;
		possibleStates = states;
	}
	
	public PlayerStates getCurrentState() {
		return currentState;
	}
	
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
	
	public void setPossibleStates(ArrayList<PlayerStates> newstates) {
		this.possibleStates = newstates;
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
	
	public String returnStatesAsString(){
		String ausgabe = new String();
		for (PlayerStates state : possibleStates){
			ausgabe += state.getName() + " ";
		}
		return ausgabe;
	}
	
	public void removeFromPossibleStates(PlayerStates state){
		possibleStates.remove(state);
	}

}
