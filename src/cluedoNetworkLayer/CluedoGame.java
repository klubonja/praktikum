package cluedoNetworkLayer;

import java.util.ArrayList;

import staticClasses.Config;
import staticClasses.auxx;
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
	
	public boolean start(String starterNick){
		if (hasNick(starterNick)){
			setGameState(GameStates.started);
			return true;
		}
		
		return false;
	}
	
	public boolean start(){
		
		
		return true;
	}
	
	public boolean addPlayers(ArrayList<CluedoPlayer> plist){
			for (CluedoPlayer pl: plist){
				joinGame(pl.getCluedoPerson().getColor(),pl.getNick());
			}
		return false;
	}
	
	public boolean joinGame(String color,String nick){
		for (CluedoPlayer p: players){
			if (p.getCluedoPerson().getColor().equals(color)){
				if (p.getNick().equals("")){
					p.setNick(nick);
					if (getNumberConnected() >= 3) setGameState(GameStates.startable);
					return true;
				}
				return false;				
			}
		}	
				
		return false;
	}
	
	public boolean joinGame(String color,String nick,PlayerStates state){
		for (CluedoPlayer p: players){
			if (p.getCluedoPerson().getColor().equals(color)){
				if (p.getNick().equals("")){
					p.setNick(nick);
					p.setState(state);
					if (getNumberConnected() >= 3) setGameState(GameStates.startable);
					return true;
				}
				return false;				
			}
		}	
				
		return false;
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
	
	public boolean removePlayer(String nick){
		for (CluedoPlayer cp: players){
			if (cp.getNick().equals(nick)){
				cp.setNick("");
				if (gameState == GameStates.started) gameState = GameStates.ended;
				if (gameState == GameStates.startable && getNumberConnected() < Config.MIN_CLIENTS_FOR_GAMESTART) setGameState(GameStates.not_started);
				if (getNumberConnected() == 0) setGameState(GameStates.to_be_deleted);

				auxx.loginfo(nick +" removed from Game " +getGameId()+" Gamestate is now : "+gameState.getName());
				return true;
			}
		}		
		return false;			
	}

	
	public void setGameState(GameStates gameState) {
		this.gameState = gameState;
	}
	
	//TODO
//	public ArrayList<String> getAvailableColors(){
//		
//	}
	
	public CluedoPlayer getPlayer(String color){
		for (CluedoPlayer p : players)
			if (p.getCluedoPerson().getColor().equals(color))
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
			if (!p.getNick().equals(""))
				n++;
		return n;
	}
	
	public ArrayList<CluedoPlayer> getPlayersConnected(){
		ArrayList<CluedoPlayer> cp = new ArrayList<CluedoPlayer>();
		for (CluedoPlayer p : players)
			if (!p.getNick().equals(""))
				cp.add(p);
		
		return cp;
	}
	
	public CluedoPlayer getConnectedPlayerByName(String name){
		for (CluedoPlayer p : players)
			if (p.getNick().equals(name))
				return p;
		
		return null;
		
	}
	
	public ArrayList<String> getConnectedPlayersString(){
		ArrayList<String> cp = new ArrayList<String>();
		for (CluedoPlayer p : players)
			if (!p.getNick().equals(""))
				cp.add(p.getNick());
		
		return cp;
		
	}
	
	public String getNicksConnected(){
		StringBuffer nb = new StringBuffer();
		for (CluedoPlayer p : players){
			if (!p.getNick().equals("")){
				nb.append(p.getNick()+", ");
			}
		}
			
		if (nb.length() > 2) nb.delete(nb.length()-2, nb.length()-1);
		return nb.toString();
	}
	
	public boolean hasNick(String nick){
		for (CluedoPlayer p: players)
			if (p.getNick().equals(nick)) return true;
		return false;
			
	}
	
	public void setOrder(ArrayList<String> order){
		
	}
	
	
	
	
	
	
	
	
}
