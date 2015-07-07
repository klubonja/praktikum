package view;

import java.util.ArrayList;
import java.util.logging.Level;


import org.json.JSONObject;

import javafx.application.Platform;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;
import enums.PlayerStates;
import finderOfPaths.Ausloeser;
import finderOfPaths.BallEbene2;
import finderOfPaths.DerBeweger;
import finderOfPaths.PlayerCircleManager;
import finderOfPaths.RaumBeweger;
import finderOfPaths.Sucher;
import finderOfPaths.Vorschlaege;
import finderOfPaths.WahnsinnigTollerPathfinder;

public class Communicator {

	private int [] wuerfelWurf;
	private String myNick;
	
	public boolean erstesMal;
	
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
	private ArrayList<CluedoPlayer> players;
	private CluedoGameClient network;
	//private ArrayList <String> order;
	public PlayerCircleManager pcManager;
	private CluedoPlayer currentPlayer; 
	private String currentNick;
	public final int gameid;

	public Communicator(CluedoGameClient ngame, ArrayList <String> order) {
		network = ngame;
		
		
		
		
		gameid = ngame.getGameId();
		players = network.getServer().getGameByGameID(network.getGameId())
				.getPlayers();
		pcManager = new PlayerCircleManager(network.getPlayersConnected());
		pcManager.order = order;
		doThatCurrentPlayerInititationStuff();
		
		
		
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

		updatePCs();

		myNick = network.getMyNick();
		//pcManager.getPlayerByNick(myNick);
		
	}

	private void doThatCurrentPlayerInititationStuff() {
		
		for (String nick : pcManager.order){
			System.out.println("alle nicks : " +nick);
		}
		
		currentPlayer = pcManager.getPlayerByNick(pcManager.order.get(0));
		currentNick = pcManager.order.get(0);
		pcManager.setIndexByPlayer(currentPlayer);
		
	}

	public void startGame() {
		auxx.loginfo("Communicator");

		myNick = network.getMyNick();
		setHandler();

		testButtons();
		auxx.logsevere("Oh my giddy giddy gosh");
		updatePCs();
		
	}
	
	

	public void setTitle(String newtitle) {
		gameView.setStageTitle(newtitle);
	}

	public void addChatMsg(String msg) {
		gamePresenter.getGfv().chat.chatArea.appendText(msg + "\n");
	}

	public GameFrameView getGameView() {
		return gameView;
	}

	public GameFramePresenter getGamePresenter() {
		return gamePresenter;
	}
	
	public void rollDice(int [] wuerfelWurf){
		//int [] testWuerfelWurf = {6,6};
		this.wuerfelWurf = wuerfelWurf;
		int ersterWuerfel = wuerfelWurf[0];
		int zweiterWuerfel = wuerfelWurf[1];
		updatePCs();
		auxx.logsevere("Communicator.rollDice");
		auxx.logsevere("currentPlayer Color : " +pcManager.getCurrentPlayer().getCluedoPerson().getColor());
		auxx.logsevere("currentPlayer x : " +pcManager.getCurrentPlayer().getPosition().getX() + "  ||  y : " +pcManager.getCurrentPlayer().getPosition().getY());
		
		System.out.println("my nick : " +myNick);
		currentPlayer = pcManager.getCurrentPlayer();
		System.out.println("current nick : " +currentNick);
		if (currentNick.equals(myNick)){
			dicePresenter.rollTheDiceForSomeone(ersterWuerfel, zweiterWuerfel, pcManager);
		}
		
		
	}
	
	public void rollDiceForMoving(int [] wuerfelWurf, CluedoPosition position, String person){
		//int [] testWuerfelWurf = {6,6};
		auxx.logsevere("wird das hier ausgeführt?");
		Platform.runLater(() -> {
			this.wuerfelWurf = wuerfelWurf;
			int ersterWuerfel = wuerfelWurf[0];
			int zweiterWuerfel = wuerfelWurf[1];
			updatePCs();
			auxx.logsevere("Communicator.rollDice");
			auxx.logsevere("currentPlayer Color : " +pcManager.getCurrentPlayer().getCluedoPerson().getColor());
			auxx.logsevere("currentPlayer x : " +pcManager.getCurrentPlayer().getPosition().getX() + "  ||  y : " +pcManager.getCurrentPlayer().getPosition().getY());
			
			currentPlayer = pcManager.getCurrentPlayer();
			dicePresenter.rollTheDiceForSomeone(ersterWuerfel, zweiterWuerfel, pcManager);
		});
		Platform.runLater(() -> {
			auxx.logsevere("kommt er hier hin?");
			updatePCs();
			int yKoordinate = position.getY();
			int xKoordinate = position.getX();
			beweger.setCurrentPlayer(pcManager.getCurrentPlayer());
			beweger.setCurrentCircle(pcManager.getCurrentCircle());
			ausloeser.ausloesen(yKoordinate, xKoordinate, person, pcManager);
		});
		
	}
	
	public void useSecretPassage(){
		updatePCs();
		beweger.useSecretPassage(pcManager);
	}
	
	public void move(CluedoPosition position, String person){
		currentPlayer = pcManager.getPlayerByPerson(person);
		pcManager.setIndexByPlayer(currentPlayer);
		if (!(currentNick.equals(myNick))){
			rollDiceForMoving(wuerfelWurf, position, person);
		}
		else {
//				Platform.runLater(() -> {
					//auxx.logsevere("kommt er hier hin?");
					updatePCs();
					int yKoordinate = position.getY();
					int xKoordinate = position.getX();
					beweger.setCurrentPlayer(pcManager.getCurrentPlayer());
					beweger.setCurrentCircle(pcManager.getCurrentCircle());
					ausloeser.ausloesen(yKoordinate, xKoordinate, person, pcManager);
//				});
			}
			
	}

	// SENDS MESSAGE BUT SERVER DOESNT DO SHIT AFTER THAT
	public void suspect() {
		String person = zugView.getPersonenListe().getValue();
		String weapon = zugView.getWaffenListe().getValue();
		String room = "Hall";
		network.sendMsgToServer(NetworkMessages.suspicionMsg(
				network.getGameId(),
				NetworkMessages.statement(person, room, weapon)));
	}

	// SENDS A MESSAGE BUT SERVER DOESNT DO SHIT AFTER THAT AS WELL
	public void accuse() {
		String person = gameView.getHand().getPersons().getValue();
		String weapon = gameView.getHand().getWeapons().getValue();
		String room = gameView.getHand().getRooms().getValue();
		network.sendMsgToServer(NetworkMessages.accuseMsg(network.getGameId(),
				NetworkMessages.statement(person, room, weapon)));

	}

	public void disprove() {

	}

	public void showPoolCards() {
		//if im pool
		
	}

	public void setNextTurn(){
		
		//pcManager.next();// erhöht den index und sonst nix
		updatePCs();
	}
		
	public void endTurn() {
		network.sendMsgToServer(NetworkMessages.end_turnMsg(gameid));
		// Communicator.playerManager.next();
		// Communicator.circleManager.next();
		// ///////////////////////////////////
		// /BENACHRICHTUGUNG, DASS NÄCHSTER///
		// ///////ZUG ANGEFANGEN HAT//////////
		// ///////////////////////////////////
		
	}
	
	public void itsYourTurn(){
//		if (!erstesMal){
//			updatePCs();
//			//changePlayer();
//			//updatePCs();
//			openWindow();
//			erstesMal = true;
//		}
//		else {
//			pcManager.next();
//			updatePCs();
//			//changePlayer();
//			//updatePCs();
//			openWindow();
//		}

		pcManager.next();
		updatePCs();
		openWindow();
		
	}
	
	public void changePlayer() {
		currentNick = pcManager.getCurrentNick();
	}
	
	public void updatePCs(){
		currentNick = pcManager.getCurrentNick();
		currentPlayer = pcManager.getPlayerByNick(currentNick);
		beweger.setCurrentPlayer(currentPlayer);
		vorschlager.setCurrentPlayer(currentPlayer);
		ausloeser.setCurrentPlayer(currentPlayer);
		pathfinder.setCurrentPlayer(currentPlayer);
		sucher.setCurrentPlayer(currentPlayer);
		dicePresenter.pcManager = pcManager;
		beweger.pcManager = pcManager;
		vorschlager.pcManager = pcManager;
		ausloeser.pcManager = pcManager;
		pathfinder.pcManager = pcManager;
		sucher.pcManager = pcManager;
	}
	public void kill() {
		gameView.close();
	}
	
	public void testButtons(){
//		ballEbene.getFremdBewegen().setOnAction(e -> move(new int [] hans = new int {5,9}));
		int [] cheater = {6,6};
		ballEbene.getGeheimgang().setOnAction(e -> useSecretPassage());
	}

	public boolean checkForValidMovement(CluedoPosition position) {
		return vorschlager.hierHerValide(position);

	}

	public void setHandler() {
		gameView.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				try {
					network.kill();
				} catch (Exception e1) {
					auxx.log.log(Level.SEVERE, e1.getMessage());
				}
			}
		});

		zugPresenter.getGameView().ONanklage.setOnMouseClicked(e -> {
			suspect();
			gameView.getKomplettesFeld().getChildren().remove(zugView);
		});

		gameView.getHand().getAccuse().setOnMouseClicked(e -> {
			accuse();
		});
		
		
		// END TURN
		gameView.getHand().getEndTurn().setOnMouseClicked(e -> endTurn());
		
	}
	
	//OPEN WINDOW
			public void openWindow(){
				auxx.logsevere("to the front!!!");
				auxx.logsevere("" +zugView);
				gameView.getKomplettesFeld().getChildren().remove(zugView);
				gameView.getKomplettesFeld().getChildren().add(zugView);
			}
			
			//CLOSE WINDOW
			public void closeWindow(){
				gameView.getKomplettesFeld().getChildren().remove(zugView);
			}

			public void updateStatesToNothing() {
				pcManager.getCurrentPlayer().setDoNothing();
			}
			
			public void updateStatesToRolls() {
				pcManager.getCurrentPlayer().setCurrentState(PlayerStates.roll_dice);
			}

			
			
	
	/*
	 * (check) start game (check) roll dice --> letztes Bild ihre
	 * Würfel-Kombination und dann pathfinder / sucher / vorschlager
	 * "losschicken" (check) use secret passage --> beweger muss position neu
	 * setzen (check) move --> ausloeser und beweger an diese position bewegen
	 * (neue methoden) / mit pathfinder ergebnissen vergleichen suspect -->
	 * nedko accuse --> nedko disprove --> nedko showPoolCards --> nedko (check)
	 * end turn PlayerManager.playerManager.next() UND
	 * PlayerManager.circleManager.next() (check) check for valid moves.
	 */

}
