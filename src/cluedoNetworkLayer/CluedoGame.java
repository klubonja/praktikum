package cluedoNetworkLayer;

import java.util.ArrayList;

import enums.GameStates;
import enums.Persons;
import enums.PlayerStates;
import enums.Weapons;

public class CluedoGame {
		
	int size;
	int gameId;
	
	GameStates gameState;
	
	ArrayList<CluedoPlayer> players;
	ArrayList<CluedoWeapon> weapons;
		
	public CluedoGame(int gameId){
		this.gameId = gameId; 
		gameState = GameStates.not_started;
		
		init();
	}
	
	void init(){
		players = new ArrayList<CluedoPlayer>();
		weapons = new ArrayList<CluedoWeapon> ();
		
		Persons[] persons = Persons.values();
		for(Persons p : persons) 
			players.add(new CluedoPlayer(p, PlayerStates.do_nothing, p.getStartposition()));
		Weapons[] weaponsEnum = Weapons.values();
		for(Weapons w : weaponsEnum) 
			weapons.add(new CluedoWeapon(w, new CluedoPosition(11, 11)) ); //11,11 ist schwimmbad	
		
	}
	
	
	
	
	public int getGameId() {
		return gameId;
	}
	
	public GameStates getGameState() {
		return gameState;
	}
	
	public ArrayList<CluedoPosition> getPersonPoss(){
		ArrayList<CluedoPosition> poss = new ArrayList<CluedoPosition>();
		for (CluedoPlayer p : players)
			poss.add(p.getPosition());
		return poss;
	}
	
	public ArrayList<CluedoWeapon> getWeapons() {
		return weapons;
	}
	
	public ArrayList<CluedoPlayer> getPlayers() {
		return players;
	}

	
	public void setGameState(GameStates gameState) {
		this.gameState = gameState;
	}
	
	public CluedoPlayer getPlayer(String name){
		for (CluedoPlayer p : players)
			if (p.getCluedoPerson().getPersonName().equals(name))
				return p;
		
		return null;
	}
	
	public CluedoWeapon getWeaponByName(String name){
		for (CluedoWeapon w : weapons)
			if (w.getWeaponName().equals(name))
				return w;
		
		return null;
	}
	
	public int getNumberConnected(){
		int n = 0;
		for (CluedoPlayer p : players)
			if (p != null)
				n++;
		return n;
	}
	
	public ArrayList<CluedoPlayer> getPlayersConnected(){
		ArrayList<CluedoPlayer> cp = new ArrayList<CluedoPlayer>();
		for (CluedoPlayer p : players)
			if (!p.getNick().equals(""))
				cp.add(getConnectedPlayerByName(p.getNick()));
		
		return cp;
	}
	
	public CluedoPlayer getConnectedPlayerByName(String name){
		for (CluedoPlayer p : players)
			if (p.getNick().equals(name))
				return p;
		
		return null;
		
	}
	
	public String getNicksConnected(){
		StringBuffer nb = new StringBuffer();
		for (CluedoPlayer p : players)
			if (!p.getNick().equals(""))
				nb.append(p.getNick()+", ");
		if (nb.length() > 2) nb.delete(nb.length()-2, nb.length()-1);
		return nb.toString();
	}
	
	
	
	
	
	
	
	
}
