package kommunikation;

import java.util.ArrayList;
import java.util.Stack;

import javafx.scene.shape.Circle;
import cluedoNetworkLayer.CluedoPlayer;
import enums.Persons;


public class PlayerCircleManager {
		
	public Stack<CluedoPlayer> players;
	public  ArrayList<Circle> circle;
	
	private int currentIndex = 0;
 	
	public PlayerCircleManager(Stack<CluedoPlayer> plist){
		super();
		init(plist);
	}

	public ArrayList<String> getCardsByNick(String nick){
		CluedoPlayer player = getPlayerByNick(nick);
		if (player != null) return player.getCards();
		return new ArrayList<String>();
	}
	
	public void init(Stack<CluedoPlayer> plist){
		players = plist;
		circle = new ArrayList<Circle>();
		for (CluedoPlayer p: players)
			circle.add(new Circle(0,0,14,p.getCluedoPerson().getFarbe()));
	}
	
	public void addPlayer(CluedoPlayer p){
		players.add(p);
	}
	
	public void addCircle(Circle c){
		circle.add(c);
	}
	public void next(){		
		currentIndex = (currentIndex + 1) % players.size();		
	}
	
	public CluedoPlayer getCurrentPlayer(){
		return players.get(currentIndex);
	}
	
	public Circle getCurrentCircle(){
		return circle.get(currentIndex);
	}

	public String getCurrentNick(){
		return players.get(currentIndex).getNick();
	}
	
	public int getCurrentPlayerIndex() {
		return currentIndex;
	}
	
	public void setIndex(int index){
		this.currentIndex = index;
	}
	
	public int getSize(){
		return players.size();
	}
	
	public CluedoPlayer getPlayerByIndex(int i){
		return players.get(i);
	}
	
	public Circle getCircleByIndex(int i){
		return circle.get(i);
	}
	
	public CluedoPlayer getPlayerByNick(String nick){
		for (CluedoPlayer p:players){
			if (p.getNick().equals(nick))
				return p;
		}
		
		return null;
	}
	
	public Circle getCircleByNick(String nick){
		for (int i = 0; i < getSize(); i++){
			if (players.get(i).getNick().equals(nick))
				return circle.get(i);
		}
		
		return null;
	}
	

	public CluedoPlayer getPlayerByPersonName(String person){
		for (CluedoPlayer p:players){
			if (p.getCluedoPerson().getColor().equals(person))
				return p;
		}
		
		return null;
	}
	
	public CluedoPlayer getPlayerByPerson(Persons person){
		for (CluedoPlayer p:players){
			if (p.getCluedoPerson() == person)
				return p;
		}
		
		return null;
	}
	
	public Circle getCircleByPerson(String person){
		for (int i = 0; i < getSize(); i++){
			if (players.get(i).getCluedoPerson().getColor().equals(person))
				return circle.get(i);
		}
		
		return null;
	}

	public void setIndexByPlayer(CluedoPlayer p){
		for (int i = 0; i < getSize(); i++)
			if (p == players.get(i)){
				currentIndex = i;
				return;		
			}					
	}

	public Stack<CluedoPlayer> getPlayers() {
		return players;
	}

	public void setPlayers(Stack<CluedoPlayer> playerManager) {
		this.players = playerManager;
	}
	

}
