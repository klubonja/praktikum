package finderOfPaths;

import java.util.ArrayList;

import javafx.scene.shape.Circle;
import cluedoNetworkLayer.CluedoPlayer;


public class PlayerCircleManager {
		
	public  ArrayList<CluedoPlayer> playerManager;
	public  ArrayList<Circle> circleManager;
	public  ArrayList<String> order;
	
	private int currentIndex = 0;
 	
	public PlayerCircleManager(ArrayList<CluedoPlayer> plist){
		super();
		init(plist);
	}
	
	public void init(ArrayList<CluedoPlayer> plist){
		playerManager = plist;
		circleManager = new ArrayList<Circle>();
		for (CluedoPlayer p: playerManager)
			circleManager.add(new Circle(0,0,14,p.getCluedoPerson().getFarbe()));
	}
	
	public void addPlayer(CluedoPlayer p){
		playerManager.add(p);
	}
	
	public void addCircle(Circle c){
		circleManager.add(c);
	}
	public void next(){		
		currentIndex = (currentIndex + 1) % playerManager.size();		
	}
	
	public CluedoPlayer getCurrentPlayer(){
		return playerManager.get(currentIndex);
	}
	
	public Circle getCurrentCircle(){
		return circleManager.get(currentIndex);
	}

	public String getCurrentNick(){
		return order.get(currentIndex);
	}
	
	public int getIndex() {
		return currentIndex;
	}
	
	public void setIndex(int index){
		this.currentIndex = index;
	}
	
	public int getSize(){
		return playerManager.size();
	}
	
	public CluedoPlayer getPlayerByIndex(int i){
		return playerManager.get(i);
	}
	
	public Circle getCircleByIndex(int i){
		return circleManager.get(i);
	}
	
	public CluedoPlayer getPlayerByNick(String nick){
		for (CluedoPlayer p:playerManager){
			if (p.getNick().equals(nick))
				return p;
		}
		
		return null;
	}
	
	public Circle getCircleByNick(String nick){
		for (int i = 0; i < getSize(); i++){
			if (playerManager.get(i).getNick().equals(nick))
				return circleManager.get(i);
		}
		
		return null;
	}
	

	public CluedoPlayer getPlayerByPerson(String person){
		for (CluedoPlayer p:playerManager){
			if (p.getCluedoPerson().getColor().equals(person))
				return p;
		}
		
		return null;
	}
	
	public Circle getCircleByPerson(String person){
		for (int i = 0; i < getSize(); i++){
			if (playerManager.get(i).getCluedoPerson().getColor().equals(person))
				return circleManager.get(i);
		}
		
		return null;
	}

	
	
	public void setIndexByPlayer(CluedoPlayer p){
		for (int i = 0; i < getSize(); i++)
			if (p == playerManager.get(i)){
				currentIndex = i;
				return;		
			}					
	}

	public ArrayList<CluedoPlayer> getPlayerManager() {
		return playerManager;
	}

	public void setPlayerManager(ArrayList<CluedoPlayer> playerManager) {
		this.playerManager = playerManager;
	}

	public ArrayList<String> getOrder() {
		return order;
	}

	public void setOrder(ArrayList<String> order) {
		this.order = order;
	}

	
	
}
