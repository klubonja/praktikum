package kommunikation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.logging.Level;

import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.stage.WindowEvent;
import kacheln.KachelContainer;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import view.AussergewohnlichesZugfenster;
import view.AussergewohnlichesZugfensterPresenter;
import view.BoardView;
import view.DicePresenter;
import view.DiceView;
import view.GameFramePresenter;
import view.GameFrameView;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;
import cluedoNetworkLayer.CluedoStatement;
import enums.Persons;
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
	
	public final int gameID;
	private String myNick;
	
	private CluedoGameClient network;
	
	private int [] wuerfelWurf;
	private boolean sswitch;
	private CluedoStatement curSuspicion = null;

	
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
	private Stack<CluedoPlayer> players;
	public PlayerCircleManager pcManager;
	private KachelContainer kacheln;
	

	public Communicator(CluedoGameClient ngame) {
		network = ngame;
		gameID = ngame.getGameId();
		pcManager = new PlayerCircleManager(network.getPlayers());
		kacheln = new KachelContainer();
		gameView = new GameFrameView(pcManager, kacheln, network);
		gameView.start();
		gamePresenter = new GameFramePresenter(gameView,network,pcManager, gameID, kacheln);
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

		myNick = network.getMyNick();
		
	}

	public void startGame() {
		auxx.loginfo("Communicator started");
		myNick = network.getMyNick();
		setHandler();

		auxx.loginfo("%%%%%%%%%%%%%%%  Mynick : " +myNick);
		
		//pcManager.setIndexByPlayer(pcManager.players.get(0));
		auxx.loginfo("Der hier fängt an : " +pcManager.getCurrentNick());
		
		auxx.logsevere("Communicator.startGame");
		auxx.logsevere("currentPlayer Color : " +pcManager.getCurrentPlayer().getCluedoPerson().getColor());
		auxx.logsevere("currentPlayer x : " +pcManager.getCurrentPlayer().getPosition().getX() + "  ||  y : " +pcManager.getCurrentPlayer().getPosition().getY());
		
		
	}
	
	public void setCurSuspicion(CluedoStatement curSuspicion) {
		this.curSuspicion = curSuspicion;
	}

	public void setTitle(String newtitle) {
		gameView.setStageTitle(newtitle);
	}

	public void addChatMsg(String msg) {
		gameView.getChat().getChatArea().appendText(msg + "\n");
	}

	public GameFrameView getGameView() {
		return gameView;
	}

	public GameFramePresenter getGamePresenter() {
		return gamePresenter;
	}
	
	public void rollDice(int [] wuerfelWurf){
		
		this.wuerfelWurf = wuerfelWurf;
		int ersterWuerfel = wuerfelWurf[0];
		int zweiterWuerfel = wuerfelWurf[1];
		
		// Vor diesem hier wurde der Spieler vertauscht.
		
		auxx.logsevere("Communicator.rollDice");
		auxx.logsevere("currentPlayer Color : " +pcManager.getCurrentPlayer().getCluedoPerson().getColor());
		auxx.logsevere("currentPlayer x : " +pcManager.getCurrentPlayer().getPosition().getX() + "  ||  y : " +pcManager.getCurrentPlayer().getPosition().getY());
		
		dicePresenter.rollTheDiceForSomeone(ersterWuerfel, zweiterWuerfel, pcManager);
	}
	
	public void useSecretPassage(){
		
		beweger.useSecretPassage(pcManager);
	}
	
	public void move(CluedoPosition position, String person){
		//pcManager.setIndexByPlayer(pcManager.getPlayerByPerson(person));
					int yKoordinate = position.getY();
					int xKoordinate = position.getX();
					ausloeser.ausloesen(yKoordinate, xKoordinate, person, pcManager);
			
	}

	public void suspect() {
		String person = zugView.getPersonenListe().getValue();
		String weapon = zugView.getWaffenListe().getValue();
		String room = kacheln.getKacheln()
				[pcManager.getCurrentPlayer().getPosition().getY()]
				[pcManager.getCurrentPlayer().getPosition().getX()].
				getRaum().getName();
		network.sendMsgToServer(NetworkMessages.suspectMsg(gameID, person,weapon,room)); //nicht suspicion das darf nur der server!
//=======
//		
//		network.sendMsgToServer(NetworkMessages.suspicionMsg(
//				gameid,
//				NetworkMessages.statement(person, room, weapon)));
//>>>>>>> master
	}

	public void accuse() {
		String person = gameView.getHand().getPersons().getValue();
		String weapon = gameView.getHand().getWeapons().getValue();
		String room = gameView.getHand().getRooms().getValue();
		network.sendMsgToServer(NetworkMessages.accuseMsg(network.getGameId(),
				NetworkMessages.statement(person, room, weapon)));

	}

	public void disprove(String card) {
		network.sendMsgToServer(NetworkMessages.disproveMsg(network.getGameId(), card));
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
					disprove(card);
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
		
	}
		
	public void endTurn() {
		network.sendMsgToServer(NetworkMessages.end_turnMsg(gameID));
	}
	
	public void itsYourTurn(){
		
		if (!sswitch){
			sswitch = true;
			openWindow();
		}
		else {
			pcManager.next();
			
			openWindow();
		}
			
	}
	
	public void itsSomeonesTurn(){
		if (!sswitch){
			sswitch = true;
		}
		else {
		pcManager.next();
		}
	}
	
	public void kill() {
		gameView.close();
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
			gameView.getKomplettesFeld().getZugView().getOrganizer().getChildren().
				remove(gameView.getKomplettesFeld().getZugView().getBottomBox());
			gameView.getKomplettesFeld().getZugView().getOrganizer().getChildren().
				remove(gameView.getKomplettesFeld().getZugView().getVermuten());
			gameView.getKomplettesFeld().getZugView().getBottomBox().getChildren().
				remove(gameView.getKomplettesFeld().getZugView().OFFanklage);
			gameView.getKomplettesFeld().getZugView().getOrganizer().getChildren().
				add(gameView.getKomplettesFeld().getZugView().getButtonsBox());
			gameView.getKomplettesFeld().getZugView().getOrganizer().getChildren().
				add(gameView.getKomplettesFeld().getZugView().getBottomBox());
			gameView.getKomplettesFeld().getZugView().getBottomBox().getChildren().
				remove(gameView.getKomplettesFeld().getZugView().getClose());
			gameView.getKomplettesFeld().getZugView().getBottomBox().getChildren().
				add(gameView.getKomplettesFeld().getZugView().getClose());
			gameView.getKomplettesFeld().getChildren().remove(zugView);
		});

		gameView.getHand().getAccuse().setOnMouseClicked(e -> accuse());

		// END TURN
		gameView.getHand().getEndTurn().setOnMouseClicked(e -> endTurn());
		
	}
	
	
	//OPEN WINDOW
			public void openWindow(){
				gameView.getKomplettesFeld().getChildren().remove(zugView);
				gameView.getKomplettesFeld().getChildren().add(zugView);
			}
			
			//CLOSE WINDOW
			public void closeWindow(){
				gameView.getKomplettesFeld().getChildren().remove(zugView);
			}

			public void updateStatesToNothing() {
				pcManager.getCurrentPlayer().setPossibleState(PlayerStates.do_nothing);
			}
			
			public void updateStatesToRolls() {
				pcManager.getCurrentPlayer().setPossibleState(PlayerStates.do_nothing);
			}
			
			public void updateStatesToSuspect(){
				pcManager.getCurrentPlayer().setPossibleState(PlayerStates.suspect);
			}
			
			public void updateStatesToAccuse(){
				pcManager.getCurrentPlayer().setPossibleState(PlayerStates.accuse);
			}
			
			public void updateStatesToDisprove(){
				pcManager.getCurrentPlayer().setPossibleState(PlayerStates.disprove);
			}
			
			public void changeLabel(String str){
				gameView.getStatusView().setCurEventsMsg(str);
			}
			
			public void setServerStateMsg(String str){
				gameView.getStatusView().setSStateMsg(str);
			}
			

			
			
//<<<<<<< HEAD
//			public void compareCards(String person, String weapon, String room){
//				switch (person) {// nochwer hasst enums
//				case "red":
//					person = "Fräulein Gloria";
//					break;
//				case "yellow":
//					person = "Oberts von Gatow";
//					break;
//				case "white":
//					person = "Frau Weiss";
//					break;
//				case "green":
//					person = "Reverend Green";
//					break;
//				case "blue":
//					person = "Baronin von Porz";
//					break;
//				case "purple":
//					person = "Professor Bloom";
//					break;
//				}
//				
//				int bufferIndex = pcManager.getCurrentPlayerIndex();
//				pcManager.next();
//				while(!cardInspector(person, weapon, room, pcManager.getCurrentPlayer().getCards())){
//					network.sendMsgToServer(NetworkMessages.no_disproveMsg(network.getGameId()));
//					pcManager.next();
//					if(bufferIndex == pcManager.getCurrentPlayerIndex() || cardInspector(person, weapon, room, pcManager.getCurrentPlayer().getCards())){
//						break;
//					}
//				}
//				if(bufferIndex != pcManager.getCurrentPlayerIndex()){
//					for(String cardOfTheOne : pcManager.getCurrentPlayer().getCards()){
//=======
			

//			
			public void showPossibleDisprovals(ArrayList<String> possibleDisprovals){
				for(String cardOfTheOne : possibleDisprovals){
					highlightCard(cardOfTheOne);
					setCardFunction(cardOfTheOne);
				}	
			}

			public void handleDisprove() {
				ArrayList<String> disprover = curSuspicion.makeConjunction(pcManager.getPlayerByNick(myNick).getCards());
				if (disprover.size() != 0){
					//show cardpane with possible disprover
					//network.sendMsgToServer(NetworkMessages.disproveMsg(gameID, selectedcard));
					//bis dahin :
					showPossibleDisprovals(disprover);
//					network.sendMsgToServer(NetworkMessages.disproveMsg(gameID, disprover.get(0)));
				}
				else {
					network.sendMsgToServer(NetworkMessages.cantDisproveMsg(gameID));
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
