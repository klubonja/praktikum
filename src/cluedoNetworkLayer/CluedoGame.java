package cluedoNetworkLayer;

import java.util.ArrayList;
import java.util.Stack;

import staticClasses.auxx;
import enums.GameStates;
import enums.Persons;
import enums.PlayerStates;
import enums.Weapons;

public abstract class CluedoGame {
		
	int size;
	int gameId;
	
	GameStates gameState;
	
	Stack<CluedoPlayer> players;
	ArrayList<CluedoWeapon> weapons;
	
	
//	Communicator communicator; //das gehört hier nicht nicht rein der server bracuht das nicht
	
	abstract boolean start();
	public abstract boolean hasPlayerConnectedByNick(String nick);
	public abstract boolean hasWatcherConnectedByNick(String nick);
	
	public CluedoGame(int gameId){
		this.gameId = gameId; 		
		init();
	}
	
	void init(){		
		gameState = GameStates.not_started;
		players = new Stack<CluedoPlayer>();
		weapons = new ArrayList<CluedoWeapon> ();
		
		Persons[] persons = Persons.values();
		for(Persons p : persons) 
			players.add(new CluedoPlayer(p, PlayerStates.do_nothing, new CluedoPosition(p.getStartposition().getX(),p.getStartposition().getY())));
		Weapons[] weaponsEnum = Weapons.values();
		for(Weapons w : weaponsEnum) 
			weapons.add(new CluedoWeapon(w, new CluedoPosition(11, 11)) ); //11,11 ist schwimmbad	
		
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
					return true;
				}
				return false;				
			}
		}	
				
		return false;
	}
	
	public boolean joinGame(String color,String nick,ArrayList<PlayerStates> states){
		for (CluedoPlayer p: players){
			if (p.getCluedoPerson().getColor().equals(color)){
				if (p.getNick().equals("")){
					p.setNick(nick);
					p.setCurrentStates(states);
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
	
	public Stack<CluedoPlayer> getPlayers() {
		return players;
	}
	
	public boolean removePlayer(String nick){
		for (CluedoPlayer cp: players){
			if (cp.getNick().equals(nick)){
				cp.setNick("");
				if (gameState == GameStates.started) gameState = GameStates.ended;
				//if (getNumberConnected() < Config.MIN_CLIENTS_FOR_GAMESTART && gameState != GameStates.ended) setGameState(GameStates.not_started);
				auxx.loginfo(nick +" removed from Game " +getGameId()+" Gamestate is now : " + gameState.getName());
				return true;
			}
		}		
		return false;			
	}

	
	public void setGameState(GameStates gameState) {
		this.gameState = gameState;
	}
	
	public ArrayList<String> getAvailableColors(){
			ArrayList<String> availableColors = new ArrayList<String>();
			for (CluedoPlayer p: players){				
					if (p.getNick().equals("")){
						availableColors.add(p.getCluedoPerson().getColor());
					}				
			}
			return availableColors;			
	}
	
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
	
	public Stack<CluedoPlayer> getPlayersConnected(){
		Stack<CluedoPlayer> cp = new Stack<CluedoPlayer>();
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
	
	int getIndexByNick(String nick) {
		for (int i = 0; i < players.size();i++)
			if (players.get(i).getNick().equals(nick))
				return i;
		
		return -1;
	}
	
	public CluedoPlayer getPlayerByNick(String nick){
		for (CluedoPlayer player : players){
			if (player.getNick().equals(nick)){
				return player;
			}
		}
		return null;
	}	
}
