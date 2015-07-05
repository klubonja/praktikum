package view;


import java.util.ArrayList;
import java.util.logging.Level;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import staticClasses.auxx;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;
import finderOfPaths.Ausloeser;
import finderOfPaths.BallEbene2;
import finderOfPaths.DerBeweger;
import finderOfPaths.PlayerCircleManager;
import finderOfPaths.RaumBeweger;
import finderOfPaths.Sucher;
import finderOfPaths.Vorschlaege;
import finderOfPaths.WahnsinnigTollerPathfinder;

public class Communicator {

	private GameFrameView gameView;
	private BoardView boardView;
	private DiceView diceView;
	private BallEbene2 ballEbene;
	private AussergewohnlichesZugfenster zugView;
	
	private GameFramePresenter gamePresenter;
	private DicePresenter dicePresenter;
	private AussergewohnlichesZugfensterPresenter zugPresenter;
	
	private Ausloeser ausloeser;
	private Sucher sucher;
	private Vorschlaege vorschlager;
	private WahnsinnigTollerPathfinder pathfinder;
	private DerBeweger beweger;
	private RaumBeweger raumBeweger;
	private ArrayList <CluedoPlayer> players;
	private CluedoGameClient network;
	public final PlayerCircleManager pcManager;
	public final int gameid;
	
		
	public Communicator(CluedoGameClient ngame){
		network = ngame;
		gameid = ngame.getGameId();
		players = ngame.getPlayersConnected();
		pcManager = new PlayerCircleManager(players);		
	}
	
	public void startGame(){
		
		auxx.loginfo("Communicater");

		gameView = new GameFrameView(pcManager);
		gameView.start();
		gamePresenter = new GameFramePresenter(gameView,network,pcManager, gameid);
		dicePresenter = gamePresenter.getDicePresenter();
		zugPresenter = gamePresenter.getZugPresenter();
		
		diceView = gameView.getDice();
		boardView = gameView.getBoard();
		ballEbene = gameView.getBallEbene();
		zugView = gameView.getZugView();
		
		ausloeser = gamePresenter.getAusloeser();
		beweger = gamePresenter.getBeweger();
		vorschlager = gamePresenter.getVorschlager();
		raumBeweger = gamePresenter.getRaumBeweger();
		pathfinder = gamePresenter.getPathfinder();
		sucher = gamePresenter.getSucher();
		
		setHandler();
		
		testButtons();
		
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
	
	
	public void rollDice(int [] wuerfelWurf){
		//int [] testWuerfelWurf = {6,6};
		int ersterWuerfel = wuerfelWurf[0];
		int zweiterWuerfel = wuerfelWurf[1];
		dicePresenter.rollTheDiceForSomeone(ersterWuerfel, zweiterWuerfel);
	}
	
	public void useSecretPassage(){
		
		beweger.useSecretPassage();
	}
	
	public void move(CluedoPosition position){
		int yKoordinate = position.getY();
		int xKoordinate = position.getX();
		ausloeser.ausloesen(yKoordinate, xKoordinate);
	}
	
	public void suspect(){
		
	}
	
	public void accuse(){
		
	}
	
	public void disprove(){
		
	}
	
	public void showPoolCards(){
		
	}
	
	public void endTurn(){

//		Communicator.playerManager.next();
//		Communicator.circleManager.next();
		pcManager.next();//erhöht den index und sonst nix
		/////////////////////////////////////
		///BENACHRICHTUGUNG, DASS NÄCHSTER///
		/////////ZUG ANGEFANGEN HAT//////////
		/////////////////////////////////////
	}
	
	public void kill(){
		gameView.close();
	}
	
	public void testButtons(){
//		ballEbene.getFremdBewegen().setOnAction(e -> move(new int [] hans = new int {5,9}));
		int [] cheater = {6,6};
		ballEbene.getFremdWuerfeln().setOnAction(e -> rollDice(cheater));
		ballEbene.getGeheimgang().setOnAction(e -> useSecretPassage());
	}
	
	public boolean checkForValidMovement(CluedoPosition position){
		return vorschlager.hierHerValide(position);
		
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
	 * (check) roll dice --> letztes Bild ihre Würfel-Kombination und dann pathfinder / sucher / vorschlager "losschicken"
	 * (check) use secret passage --> beweger muss position neu setzen
	 * (check) move --> ausloeser und beweger an diese position bewegen (neue methoden) / mit pathfinder ergebnissen vergleichen
	 * suspect --> nedko
	 * accuse --> nedko
	 * disprove --> nedko
	 * showPoolCards --> nedko
	 * (check) end turn PlayerManager.playerManager.next() UND PlayerManager.circleManager.next() 
	 * (check) check for valid moves.
	 */
	
	
	
}
