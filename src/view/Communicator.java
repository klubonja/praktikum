package view;


import java.util.ArrayList;
import java.util.logging.Level;

import javafx.event.EventHandler;
import javafx.scene.shape.Circle;
import javafx.stage.WindowEvent;
import staticClasses.auxx;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPlayer;
import finderOfPaths.Ausloeser;
import finderOfPaths.DerBeweger;
import finderOfPaths.GanzTolleSpielerliste;
import finderOfPaths.Sucher;
import finderOfPaths.WahnsinnigTollerPathfinder;

public class Communicator {

	private GameFrameView gameView;
	private BoardView boardView;
	private DiceView diceView;
	
	private GameFramePresenter gamePresenter;
	private DicePresenter dicePresenter;
	
	private Ausloeser ausloeser;
	private Sucher sucher;
	private WahnsinnigTollerPathfinder pathfinder;
	private DerBeweger beweger;
	private ArrayList <CluedoPlayer> players;
	private CluedoGameClient network;
	
	//public static GanzTolleSpielerliste<CluedoPlayer> playerManager = new GanzTolleSpielerliste<CluedoPlayer>();
	//public static GanzTolleSpielerliste<Circle> circleManager = new GanzTolleSpielerliste<Circle>();
	
	public Communicator(CluedoGameClient ngame){
		network = ngame;
		players = ngame.getPlayersConnected();
		for (CluedoPlayer p : players){
			GanzTolleSpielerliste.playerManager.add(p);
			GanzTolleSpielerliste.circleManager.add(new Circle(0,0,14,p.getCluedoPerson().getFarbe()));
		}
		
	}
	
	public void startGame(){
		
		auxx.loginfo("Communicater");

		gameView = new GameFrameView();
		gameView.start();
		gamePresenter = new GameFramePresenter(gameView,network);
		dicePresenter = gamePresenter.getDicePresenter();
		
		diceView = gameView.getDice();
		boardView = gameView.getBoard();

		ausloeser = gamePresenter.getAusloeser();
		beweger = gamePresenter.getBeweger();
		pathfinder = gamePresenter.getPathfinder();
		sucher = gamePresenter.getSucher();
		
	}
	public void setTitle(String newtitle){
		gameView.setStageTitle(newtitle);
	}
	
	public void addChatMsg(String msg){
		gamePresenter.getGfv().chat.chatArea.appendText(msg+"\n");
	}
	
	public GameFrameView getGameView() {
		return gameView;
	}
	
	public GameFramePresenter getGamePresenter() {
		return gamePresenter;
	}
	
	
	public void rollDice(){
		///////////////////////////////
		/////////BRAUCHT INPUT/////////
		///////////////////////////////
		// dicePresenter.rollTheDiceForSomeone(ersterWuerfel, zweiterWuerfel);
	}
	
	public void useSecretPassage(){
		beweger.useSecretPassage();
	}
	
	public void move(){
		
	}
	
	public void suspect(){
		
	}
	
	public void accuse(){
		
	}
	
	public void disprove(){
		
	}
	
	public void endTurn(){

//		Communicator.playerManager.next();
//		Communicator.circleManager.next();
		GanzTolleSpielerliste.playerManager.next();
		GanzTolleSpielerliste.circleManager.next();
		/////////////////////////////////////
		///BENACHRICHTUGUNG, DASS NÄCHSTER///
		/////////ZUG ANGEFANGEN HAT//////////
		/////////////////////////////////////
	}
	
	public void kill(){
		gameView.close();
	}
	
	public void setHandler(){
		gameView.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
		      @Override
			public void handle(WindowEvent e){
		          try {
		        	  network.kill();
		          } 
		          catch (Exception e1) {
		               auxx.log.log(Level.SEVERE,e1.getMessage());
		          }
		      }
		 });
	}
	/*
	 * (check) start game 
	 * roll dice --> letztes Bild ihre Würfel-Kombination und dann pathfinder / sucher / vorschlager "losschicken"
	 * use secret passage --> beweger muss position neu setzen
	 * move --> ausloeser und beweger an diese position bewegen (neue methoden) / mit pathfinder ergebnissen vergleichen
	 * suspect --> nedko
	 * accuse --> nedko
	 * disprove --> nedko
	 * (check) end turn PlayerManager.playerManager.next() UND PlayerManager.circleManager.next() 
	 * 
	 */
	
	
	
}
