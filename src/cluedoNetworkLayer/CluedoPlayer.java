package cluedoNetworkLayer;

import enums.Persons;
import enums.PlayerStates;

public class CluedoPlayer {
	
	Persons cluedoPerson;
	PlayerStates state;
	CluedoPosition position;
	String nick;
	
	public CluedoPlayer(Persons pers,PlayerStates s, CluedoPosition p) {
		cluedoPerson = pers;
		state = s;
		position = p;
		nick = "alfons"+Math.random();
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
