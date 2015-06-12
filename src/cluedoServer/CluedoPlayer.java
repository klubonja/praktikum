package cluedoServer;

import enums.Persons;
import enums.PlayerStates;

public class CluedoPlayer {
	
	Persons cluedoPerson;
	PlayerStates state;
	CluedoPosition position;
	
	public CluedoPlayer(Persons pers,PlayerStates s, CluedoPosition p) {
		cluedoPerson = pers;
		state = s;
		position = p;
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

}
