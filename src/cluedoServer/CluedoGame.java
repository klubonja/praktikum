package cluedoServer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import enums.GameStates;
import enums.Persons;
import enums.PlayerStates;
import enums.Weapons;

public class CluedoGame {
	
	ExecutorService pool;	
	String groupName;
	
	int size;
	int gameId;
	
	GameStates gameState;
	
	ArrayList<ClientItem> participants;
	ArrayList<ClientItem> watchers;
	Map<String, CluedoPlayer> players;
	Map<String, CluedoWeapon> weapons;
	
	
	
	//playerInfos, watchers, personposes, weaponposes)
	
	public CluedoGame(int poolSize,String groupName){
		size = poolSize;
		this.groupName = groupName;
		

		//pool = Executors.newFixedThreadPool(poolSize);
	}
	
	void init(){
		players = new LinkedHashMap<String,CluedoPlayer>();
		weapons = new LinkedHashMap<String,CluedoWeapon>();
		
		Persons[] persons = Persons.values();
		for(Persons p : persons) players.put("", new CluedoPlayer(p, PlayerStates.do_nothing, p.getStartposition()));
		Weapons[] weaponsEnum = Weapons.values();
		for(Weapons w : weaponsEnum) weapons.put("", new CluedoWeapon(w, new CluedoPosition(11, 11)) ); //11,11 ist schwimmbad

		
		
	}
	
	
	public ArrayList<String> getWatchersNicks(){
		ArrayList<String> nicks = new ArrayList<String>();
		for (ClientItem c : watchers)
			nicks.add(c.getNick());
		
		return nicks;
	}
	
	public int getGameId() {
		return gameId;
	}
	
	public GameStates getGameState() {
		return gameState;
	}
	
	public ArrayList<CluedoPosition> getPersonPoss(){
		ArrayList<CluedoPosition> poss = new ArrayList<CluedoPosition>();
		for (Map.Entry<String, CluedoPlayer> p : players.entrySet())
			poss.add(p.getValue().getPosition());
		return poss;
	}
	
	public ArrayList<CluedoPosition> getWeaponPoss(){
		ArrayList<CluedoPosition> poss = new ArrayList<CluedoPosition>();
		for (Map.Entry<String, CluedoWeapon> w : weapons.entrySet())
			poss.add(w.getValue().getPosition());
		return poss;
	}
	
	public ArrayList<CluedoPlayer> getPlayersInfo(){
		ArrayList<CluedoPlayer> ps = new ArrayList<CluedoPlayer>();
		for (Map.Entry<String, CluedoPlayer> p : players.entrySet())
			ps.add(p.getValue());
		return ps;
	}
	
	public Map<String, CluedoPlayer> getPlayers() {
		return players;
	}
	
	public Map<String, CluedoWeapon> getWeapons() {
		return weapons;
	}
	
	
}
