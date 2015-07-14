package finderOfPaths;

import java.util.ArrayList;
import java.util.Stack;

import javafx.scene.shape.Circle;
import cluedoNetworkLayer.CluedoPlayer;
import enums.PlayerStates;


public class PlayerCircleManager {
		
	public Stack<CluedoPlayer> players;
	public  ArrayList<Circle> circle;
	
	private int currentIndex = 0;
 	
	public PlayerCircleManager(Stack<CluedoPlayer> plist){
		super();
		init(plist);
	}
	
	public void updatePlayerStates(){		
		for (int i = 0;i < players.size(); i++){
			if (i == currentIndex)	{
				players.get(i).setCurrentState(PlayerStates.do_nothing); // hier werden possible moves von do nothing aus gesetzt
			}
			else{
				players.get(i).setDoNothing(); // hier werden possible moves geleert und do nothing hinzugefügt
			}			
		}
			
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
	
	public int getIndex() {
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
	

	public CluedoPlayer getPlayerByPerson(String person){
		for (CluedoPlayer p:players){
			if (p.getCluedoPerson().getColor().equals(person))
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

	public Stack<CluedoPlayer> getPlayerManager() {
		return players;
	}

	public void setPlayerManager(Stack<CluedoPlayer> playerManager) {
		this.players = playerManager;
	}
	

}
