package kommunikation;

import java.util.ArrayList;
import java.util.Stack;

import staticClasses.Images;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import cluedoNetworkLayer.CluedoPlayer;
import enums.Persons;


public class PlayerCircleManager {
		
	public Stack<CluedoPlayer> players;
	public  ArrayList<ImageView> characters;
	
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
		characters = new ArrayList<ImageView>();
		for (CluedoPlayer p: players){
			switch(p.getCluedoPerson().getColor()){
			case "red": characters.add(Images.red); break;
			case "blue": characters.add(Images.blue); break;
			case "purple": characters.add(Images.purple); break;
			case "green": characters.add(Images.green); break;
			case "yellow": characters.add(Images.yellow); break;
			case "white": characters.add(Images.white); break;
			}
		}
	}
	
	public void addPlayer(CluedoPlayer p){
		players.add(p);
	}
	
	public void addCharacter(ImageView character){
		characters.add(character);
	}
	public void next(){		
		currentIndex = (currentIndex + 1) % players.size();		
	}
	
	public CluedoPlayer getCurrentPlayer(){
		return players.get(currentIndex);
	}
	
	public ImageView getCurrentCharacter(){
		return characters.get(currentIndex);
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
	
	public ImageView getCharacterByIndex(int i){
		return characters.get(i);
	}
	
	public CluedoPlayer getPlayerByNick(String nick){
		for (CluedoPlayer p:players){
			if (p.getNick().equals(nick))
				return p;
		}
		
		return null;
	}
	
	public ImageView getCharacterByNick(String nick){
		for (int i = 0; i < getSize(); i++){
			if (players.get(i).getNick().equals(nick))
				return characters.get(i);
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
	
	public ImageView getCharacterByPerson(String person){
		for (int i = 0; i < getSize(); i++){
			if (players.get(i).getCluedoPerson().getColor().equals(person))
				return characters.get(i);
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
