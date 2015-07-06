package cluedoNetworkLayer;

import java.util.ArrayList;

import enums.Persons;
import enums.PlayerStates;

public class CluedoPlayer {
	
	Persons cluedoPerson;
	ArrayList<PlayerStates>  possibleStates;
	CluedoStateMachine dfa = new CluedoStateMachine(PlayerStates.do_nothing);
	PlayerStates currentState;
	CluedoPosition position;
	String nick;
	ArrayList<String> cards;
	
	
	
	public CluedoPlayer(Persons pers,PlayerStates s, CluedoPosition p) {
		cluedoPerson = pers;
		if (s == PlayerStates.do_nothing) setDoNothing();
		else setCurrentState(s);
		position = p;
		nick = "";
		cards = new ArrayList<String>();
	}
	
	public CluedoPlayer(Persons pers,PlayerStates s) {
		cluedoPerson = pers;
		if (s == PlayerStates.do_nothing) setDoNothing();
		else setCurrentState(s);
		position = new CluedoPosition(cluedoPerson.getStartposition().getX(),
									 cluedoPerson.getStartposition().getY());
	}
	
	public void setDoNothing() {
		this.currentState = PlayerStates.do_nothing;
		possibleStates = new ArrayList<PlayerStates>();
		possibleStates.add(currentState);
	}
	
	public void setCurrentState(PlayerStates currentState) {
		this.currentState = currentState;
		possibleStates = dfa.getPossibleStatesFromState(currentState);
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
	
	public ArrayList<PlayerStates> getStates() {
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
	

}
