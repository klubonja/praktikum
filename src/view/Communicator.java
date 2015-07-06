package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
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
	private CluedoGameClient network;
	public final PlayerCircleManager pcManager;
	public final int gameid;

	public Communicator(CluedoGameClient ngame) {
		network = ngame;
		gameid = ngame.getGameId();
		pcManager = new PlayerCircleManager(network.getPlayersConnected());
	}

	public void startGame() {
		auxx.loginfo("Communicater");

		gameView = new GameFrameView(pcManager, network);
		gameView.start();
		gamePresenter = new GameFramePresenter(gameView, network, pcManager,
				gameid);
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
		auxx.logsevere("Oh my giddy giddy gosh");
		updatePCs();
	}
	
	public void updatePCs(){
		CluedoPlayer currentPlayer = pcManager.getCurrentPlayer();
		beweger.setCurrentPlayer(currentPlayer);
		vorschlager.setCurrentPlayer(currentPlayer);
		ausloeser.setCurrentPlayer(currentPlayer);
		pathfinder.setCurrentPlayer(currentPlayer);
		dicePresenter.pcManager = pcManager;
		beweger.pcManager = pcManager;
		vorschlager.pcManager = pcManager;
		ausloeser.pcManager = pcManager;
		pathfinder.pcManager = pcManager;
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

	public void rollDice(int[] wuerfelWurf) {
		// int [] testWuerfelWurf = {6,6};
		int ersterWuerfel = wuerfelWurf[0];
		int zweiterWuerfel = wuerfelWurf[1];
		updatePCs();
		dicePresenter.rollTheDiceForSomeone(ersterWuerfel, zweiterWuerfel, pcManager);
	}
	
	public void useSecretPassage(){
		updatePCs();
		beweger.useSecretPassage(pcManager);
	}
	
	public void move(CluedoPosition position, String person){
		updatePCs();
		int yKoordinate = position.getY();
		int xKoordinate = position.getX();
		beweger.setCurrentPlayer(pcManager.getCurrentPlayer());
		beweger.setCurrentCircle(pcManager.getCurrentCircle());
		ausloeser.ausloesen(yKoordinate, xKoordinate, person, pcManager);
	}

	public void suspect() {
		String person = zugView.getPersonenListe().getValue();
		String weapon = zugView.getWaffenListe().getValue();
		String room = "hall";
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

	public void highlightCard(String card) {
		for (int i = 0; i < gameView.getHand().getHandURI().size(); i++) {
			if (card.equals(gameView.getHand().getHandURI().get(i))) {
				gameView.getHand().getHand().get(i).setEffect(new Glow(0.5));
			}
		}
	}
	
	public void setCardFunction(String card){
		for (int i = 0; i < gameView.getHand().getHandURI().size(); i++) {
			if (card.equals(gameView.getHand().getHandURI().get(i))) {
				gameView.getHand().getHand().get(i).setOnMousePressed(e -> {
					network.sendMsgToServer(NetworkMessages.disproveMsg(network.getGameId(), card));
				});
				gameView.getHand().getHand().get(i).setOnMouseReleased(e -> {
					gamePresenter.getHandFramePresenter().removeEffects();
				});
			}
		}
	}

	public void showPoolCards() {
		// if im pool

	}

	public void setNextTurn(){
		pcManager.next();// erhöht den index und sonst nix
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
		setNextTurn();
		openWindow();
		updatePCs();
	}
	
	public void kill() {
		gameView.close();
	}
	
	public void testButtons(){
//		ballEbene.getFremdBewegen().setOnAction(e -> move(new int [] hans = new int {5,9}));
		int [] cheater = {6,6};
		ballEbene.getFremdWuerfeln().setOnAction(e -> rollDice(cheater));
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
			
			public void changeLabel(String str){
				gameView.getHand().getText().setText(str);
			}
			
			public void compareCards(String person, String weapon, String room){
				boolean found = false;
				int currentIndex = pcManager.getIndex();
				pcManager.next();
				while(currentIndex != pcManager.getIndex()){
				for(int i = 0; i < pcManager.getPlayerManager().size(); i++){
					for(String card : pcManager.getCurrentPlayer().getCards()){
					switch (person) {
					case "red":
						person = "Fräulein Gloria";
						break;
					case "yellow":
						person = "Oberts von Gatow";
						break;
					case "white":
						person = "Frau Weiss";
						break;
					case "green":
						person = "Reverend Green";
						break;
					case "blue":
						person = "Baronin von Porz";
						break;
					case "purple":
						person = "Professor Bloom";
						break;
					}
					if (card.equals(person) ||
						card.equals(weapon) ||
						card.equals(room)) {
								highlightCard(card);
								setCardFunction(card);
								pcManager.setIndex(currentIndex);
								found = true;
					}
					}
					if(found){
						break;
					}
					pcManager.next();
				}
				}
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
