package kommunikation;

import java.util.ArrayList;

import java.util.Stack;
import java.util.logging.Level;
import animation.DerBeweger;
import animation.RaumBeweger;
import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.stage.WindowEvent;
import kacheln.KachelContainer;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import view.AussergewohnlichesZugfenster;
import view.AussergewohnlichesZugfensterPresenter;
import view.DicePresenter;
import view.DiceView;
import view.spielfeld.BallEbene;
import view.spielfeld.BoardView;
import view.spielfeld.GameFramePresenter;
import view.spielfeld.GameFrameView;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;
import cluedoNetworkLayer.CluedoStatement;
import enums.PlayerStates;
import finderOfPaths.Ausloeser;
import finderOfPaths.Sucher;
import finderOfPaths.Vorschlaege;
import finderOfPaths.WahnsinnigTollerPathfinder;

/**
 * @since ca. 10.7.2015
 * @version 21.07.2015
 * @author Benedikt Mayer & Pascal Guldener
 * 
 * Hier ist die krasseste Schnittstelle zwischen Client und Server, welche es je gegeben hat.
 * Hier wird quasi alles losgetreten, hier ist der Anfang von Allem.
 * 
 * hans
 *
 */
public class Communicator {
	
	public final int gameID;
	private String myNick;
	
	private CluedoGameClient network;
	
	private int [] wuerfelWurf;
	private boolean sswitch;
	private CluedoStatement curSuspicion = null;
	
	private GameFrameView gameView;
	private BoardView boardView;
	private DiceView diceView;
	private BallEbene ballEbene;
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
	

	/**
	 * Der Kontruktor unsers ach-so-krassen Communicators
	 * @param game das Spiel, welches wir von unserem netten Server kriegen.
	 */
	public Communicator(CluedoGameClient game) {
		network = game;
		gameID = game.getGameId();
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

	/**
	 * Es geht los! Hui, ich bin schon ganz aufgeregt
	 */
	public void startGame() {
		auxx.loginfo("Communicator started");
		myNick = network.getMyNick();
		setHandler();
		
		auxx.loginfo("this is importenter:: \n \n %%%%%%%%%%%%%%%  Mynick : " +myNick);
		
		auxx.loginfo("Der hier fängt an : " +pcManager.getCurrentNick());
		
		auxx.logsevere("Communicator.startGame");
		auxx.logsevere("currentPlayer Color : " +pcManager.getCurrentPlayer().getCluedoPerson().getColor());
		auxx.logsevere("currentPlayer x : " +pcManager.getCurrentPlayer().getPosition().getX() + "  ||  y : " +pcManager.getCurrentPlayer().getPosition().getY());
		
	}
		
	/**
	 * <insert amazing joke about dice here>
	 * @param wuerfelWurf unser atemberaumbender Wuerfelwurf
	 */
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
	
	/**
	 * Jemand will also den Geheimgang nutzen! oho!
	 */
	public void useSecretPassage(){
		CluedoPosition position = pcManager.getCurrentPlayer().getPosition();
		beweger.useSecretPassage(pcManager);
		if (pcManager.getCurrentPlayer().getNick().equals(myNick) && kacheln.getKacheln()[position.getY()][position.getX()].isIstRaum()){
			openWindow();
		}
		
	}
	
	public void move(CluedoPosition position, String person){

		// hier machen wenn jemand nur eine secret passage verwenden will
		if (wuerfelWurf == null){
			useSecretPassage();
		}
	
		// hier wird sogar richtig bewegt. Krass.
		else {
			int yKoordinate = position.getY();
			int xKoordinate = position.getX();
			ausloeser.ausloesen(yKoordinate, xKoordinate, person, pcManager);
			if (pcManager.getCurrentPlayer().getNick().equals(myNick) && kacheln.getKacheln()[yKoordinate][xKoordinate].isIstRaum()){
				openWindow();
			}
			this.wuerfelWurf = null;
		}
	}

	/**
	 * Suspect! Surprise! Drama!
	 */
	public void suspect() {
		String person = zugView.getPersonenListe().getValue();
		String weapon = zugView.getWaffenListe().getValue();
		String room = kacheln.getKacheln()
				[pcManager.getCurrentPlayer().getPosition().getY()]
				[pcManager.getCurrentPlayer().getPosition().getX()].
				getRaum().getName();

		network.sendMsgToServer(NetworkMessages.suspectMsg(gameID, person,weapon,room)); //nicht suspicion das darf nur der server!

	}

	public void accuse() {
		String person = gameView.getHand().getPersons().getValue();
		String weapon = gameView.getHand().getWeapons().getValue();
		String room = gameView.getHand().getRooms().getValue();
		network.sendMsgToServer(NetworkMessages.accuseMsg(network.getGameId(),
				NetworkMessages.statement(person, room, weapon)));

	}

	/**
	 * Schicke eine disprove-Nachricht mit der disprovten Karte raus
	 * @param card die disprovte Karte
	 */
	public void sendDisproveMsg(String card) {
		network.sendMsgToServer(NetworkMessages.disproveMsg(network.getGameId(), card));
	}

	/**
	 * macht unsere Karten schoener und fancier
	 * @param card Karte wird gehighlitet
	 */
	public void highlightCard(String card) {
		for (int i = 0; i < gameView.getHand().getHandURI().size(); i++) {
			if (card.equals(gameView.getHand().getHandURI().get(i))) {
				gameView.getHand().getHand().get(i).setEffect(new Glow(0.5));
			}
		}
	}
	
	/**
	 * 
	 * @param card
	 */
	public void setCardFunction(String card){
		for (int i = 0; i < gameView.getHand().getHandURI().size(); i++) {
			if (card.equals(gameView.getHand().getHandURI().get(i))) {
				gameView.getHand().getHand().get(i).setOnMousePressed(e -> {
					sendDisproveMsg(card);
				});
				gameView.getHand().getHand().get(i).setOnMouseReleased(e -> {
					gamePresenter.getHandFramePresenter().removeEffects();
				});
			}
		}
	}

	public void showPoolCards() {
		//TODO hier muss noch was rein Leute

	}

	public void endTurn() {
		network.sendMsgToServer(NetworkMessages.end_turnMsg(gameID));
	}
	
	public void itsYourTurn(){
		
//		if (!sswitch){
//			sswitch = true;
//			openWindow();
//		}
//		else {
//			pcManager.next();
//			
//			openWindow();
//		}
		
		pcManager.setIndexByPlayer(network.getPlayerByNick(myNick));
		System.out.println("its MY("+myNick+") turn and opening window");
		openWindow();
			
	}
	
	public void itsSomeonesTurn(String nick){
		pcManager.setIndexByPlayer(network.getPlayerByNick(nick));
		closeWindow();
		System.out.println("its "+nick+"s turn and closing window");
//		if (!sswitch){
//			sswitch = true;
//		}
//		else {
//		pcManager.next();
//		}
	}
	
		/**
	 * Hier werden irgendwelche Dinge gehandelt (z.B. wenn das Spielfenster einfach so geschlossen wird.
	 * Aber welcher Vollhonk schliesst einfach so das Fenster......?
	 */
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

		zugPresenter.getGameView().YESgangImage.setOnMouseClicked(e -> network.sendMsgToServer(NetworkMessages.secret_passageMsg(gameID)));
		
		
		// BOESE!!
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
			System.out.println("openwindow");
			gameView.getKomplettesFeld().getChildren().remove(zugView);
			gameView.getKomplettesFeld().getChildren().add(zugView);
		}
		
		//CLOSE WINDOW
		public void closeWindow(){
			System.out.println("closewindow");
			gameView.getKomplettesFeld().getChildren().remove(zugView);
		}

		
		public void setServerStateMsg(String str){
			gameView.getStatusView().setSStateMsg(str);
		}
			
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
					//network.sendMsgToServer(NetworkMessages.disproveMsg(gameID, disprover.get(0)));
					showPossibleDisprovals(disprover);				
				}
				else {
					network.sendMsgToServer(NetworkMessages.cantDisproveMsg(gameID));
				}				
			}
	
	public void kill() {
		gameView.close();
	}
			
	public boolean checkForValidMovement(CluedoPosition position) {
		return vorschlager.hierHerValide(position);
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

	public void moveForSuspiciton(int gameID2, CluedoStatement suspicion) {
		CluedoPlayer player = pcManager.getPlayerByPerson(suspicion.getPerson());
		beweger.getCarriedAlong(suspicion.getRoom(), player);
	}

}
