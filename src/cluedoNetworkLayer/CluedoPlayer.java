package cluedoNetworkLayer;

import java.util.ArrayList;
import java.util.Arrays;

import enums.Persons;
import enums.PlayerStates;

public class CluedoPlayer {
	
	Persons cluedoPerson;
	PlayerStates state;
	CluedoPosition position;
	String nick;
	ArrayList<String> cards;
	
	public CluedoPlayer(Persons pers,PlayerStates s, CluedoPosition p) {
		cluedoPerson = pers;
		state = s;
		position = p;
		nick = "";
		setCards(new ArrayList<String>(Arrays.asList("conservatory", "pipe", "purple", "blue", "study", "ballroom")));
	}
	
	public void setCards(ArrayList<String> cards) {
		this.cards = cards;
	}
	
	public ArrayList<String> getCards() {
		return cards;
	}
	
	public CluedoPlayer(Persons pers,PlayerStates s) {
		cluedoPerson = pers;
		state = s;
		position = new CluedoPosition(cluedoPerson.getStartposition().getX(),
									 cluedoPerson.getStartposition().getY());
	}
	
	public void setNewPosition(int x,int y) {
		this.position.setX(x);
		this.position.setY(y);
	}
	
	public void setState(PlayerStates state) {
		this.state = state;
	}
	
	public PlayerStates getState() {
		return state;
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
