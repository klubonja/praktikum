package cluedoNetworkLayer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import enums.GameStates;
import enums.Persons;
import enums.PlayerStates;
import enums.Weapons;

public class CluedoGame {
		
	int size;
	int gameId;
	
	GameStates gameState;
	
	ArrayList<CluedoPlayer> players;
	Map<String, CluedoWeapon> weapons;
		
	public CluedoGame(int gameId){
		this.gameId = gameId; 
		gameState = GameStates.not_started;
		
		init();
	}
	
	void init(){
		players = new ArrayList<CluedoPlayer>();
		weapons = new LinkedHashMap<String,CluedoWeapon>();
		
		Persons[] persons = Persons.values();
		for(Persons p : persons) 
			players.add(new CluedoPlayer(p, PlayerStates.do_nothing, p.getStartposition()));
		Weapons[] weaponsEnum = Weapons.values();
		for(Weapons w : weaponsEnum) 
			weapons.put("", new CluedoWeapon(w, new CluedoPosition(11, 11)) ); //11,11 ist schwimmbad	
		
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
	
	public ArrayList<CluedoPosition> getWeaponPoss(){
		ArrayList<CluedoPosition> poss = new ArrayList<CluedoPosition>();
		for (Map.Entry<String, CluedoWeapon> w : weapons.entrySet())
			poss.add(w.getValue().getPosition());
		return poss;
	}
	
	public ArrayList<CluedoPlayer> getPlayers() {
		return players;
	}
	
	public Map<String, CluedoWeapon> getWeapons() {
		return weapons;
	}
	
	public void setGameState(GameStates gameState) {
		this.gameState = gameState;
	}
	
	public CluedoPlayer getPlayer(String name){
		for (CluedoPlayer p : players)
			if (p.getNick().equals(name))
				return p;
		
		return null;
	}
	
	public CluedoWeapon getWeapon(String name){
		return weapons.get(name);
	}
	
	public int getNumberConnected(){
		int n = 0;
		for (CluedoPlayer p : players)
			if (p != null)
				n++;
		return n;
	}
	
	public String getNicksConnected(){
		StringBuffer nb = new StringBuffer();
		for (CluedoPlayer p : players)
			if (p != null)
				nb.append(p.getNick()+", ");
		nb.delete(nb.length()-2, nb.length()-1);
		return nb.toString();
	}
	
	
	
	
	
	
	
	
}
