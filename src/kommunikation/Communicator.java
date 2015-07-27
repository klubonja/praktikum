
package kommunikation;

import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import kacheln.KachelContainer;
import staticClasses.Config;
import staticClasses.Images;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import view.AussergewohnlichesZugfenster;
import view.AussergewohnlichesZugfensterPresenter;
import view.DicePresenter;
import view.DiceView;
import view.ShowKarten;
import view.spielfeld.BallEbene;
import view.spielfeld.BoardView;
import view.spielfeld.GameFramePresenter;
import view.spielfeld.GameFrameView;
import yoloKI.KI;
import animation.DerBeweger;
import animation.RaumBeweger;
import cluedoNetworkGUI.Loser;
import cluedoNetworkGUI.Winner;
import cluedoNetworkLayer.CluedoField;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;
import cluedoNetworkLayer.CluedoStatement;
import enums.PlayerStates;
import enums.Rooms;
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
	
	private boolean kiplay = false;
	private KI ki;
	
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
	 * @param network das Spiel, welches wir von unserem netten Server kriegen.
	 */
	public Communicator(CluedoGameClient network) {
		
		this.network = network;
		myNick = network.getMyNick();	
		gameID = network.getGameId();		
		pcManager = new PlayerCircleManager(network.getPlayers());
		
		kacheln = new KachelContainer();
		
		gameView = new GameFrameView(pcManager, kacheln, network);
		gameView.start();
		gamePresenter = new GameFramePresenter(gameView,network,pcManager, gameID, kacheln);
		dicePresenter = gamePresenter.getDicePresenter();
		zugPresenter = gamePresenter.getZugPresenter();

		ki = new KI(network,pcManager,this);
		
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
		
		gameView.getMenu().getActivateKI().setOnAction(e -> kiSwitch());
	}

	/**
	 * Es geht los! Huy, ich bin schon ganz aufgeregt, Huuuuuuuuyyyyyy !!!
	 */
	public void startGame() {
		auxx.loginfo("Communicator started");
		myNick = network.getMyNick();
		setHandler();
		if (network.hasWatcherConnectedByNick(myNick)) gameView.setWatchersMode();
		
		auxx.loginfo("this is importenter:: \n \n %%%%%%%%%%%%%%%  Mynick : " +myNick);
		
		auxx.loginfo("Der hier fängt an : " +pcManager.getCurrentNick());
		
		auxx.logsevere("Communicator.startGame");
		auxx.logsevere("currentPlayer Color : " +pcManager.getCurrentPlayer().getCluedoPerson().getColor());
		auxx.logsevere("currentPlayer x : " +pcManager.getCurrentPlayer().getPosition().getX() + "  ||  y : " +pcManager.getCurrentPlayer().getPosition().getY());
		gameView.getHand().setPlayerCards(pcManager.getCardsByNick(myNick));
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
		
		if (kiplay) ki.move();
	}
	
	/**
	 * Jemand will also den Geheimgang nutzen! oho!
	 */
	public void useSecretPassage(){
		CluedoPosition position = pcManager.getCurrentPlayer().getPosition();
		beweger.useSecretPassage(pcManager);
		if (pcManager.getCurrentPlayer().getNick().equals(myNick) && kacheln.getKacheln()[position.getY()][position.getX()].isIstRaum()){
			if (!kiplay) openWindow();
		}		
	}
	
	public void requestUseSecretPassge(){
		network.sendMsgToServer(NetworkMessages.secret_passageMsg(gameID));
	}
	
	public void move(CluedoPosition position, String person){

		CluedoPosition playerPosition = pcManager.getPlayerByPersonName(person).getPosition();
		
		if ( (Math.abs(position.getY() - playerPosition.getY() ) >=10 ) && ( Math.abs(position.getX() - playerPosition.getX() ) >=10 ) ){
			useSecretPassage();
		}
		
		// hier wird sogar richtig bewegt. Krass.
		else {
			int yKoordinate = position.getY();
			int xKoordinate = position.getX();
			ausloeser.ausloesen(yKoordinate, xKoordinate, person, pcManager);
			if (pcManager.getCurrentPlayer().getNick().equals(myNick) && kacheln.getKacheln()[yKoordinate][xKoordinate].isIstRaum()){
				
				if (kiplay){
					ki.postmove();
				}
				else if (kacheln.getKacheln()[yKoordinate][xKoordinate].getRaum() != Rooms.pool){
					openWindow();
				}
			}
			this.wuerfelWurf = null;
		}
	}

	/**
	 * Suspect! Surprise! Drama!
	 */
	public void suspect(String person,String weapon,String room) {
		network.sendMsgToServer(NetworkMessages.suspectMsg(gameID, person,weapon,room)); //nicht suspicion das darf nur der server!
	}

	public void accuse(String person, String weapon, String room) {	
		network.sendMsgToServer(NetworkMessages.accuseMsg(network.getGameId(),
				NetworkMessages.statement(person, room, weapon)));
	}
	

	public void endTurn() {
		network.sendMsgToServer(NetworkMessages.end_turnMsg(gameID));
	}
	
	public void itsYourTurn(){
		pcManager.setIndexByPlayer(network.getPlayerByNick(myNick));
		System.out.println("its MY("+myNick+") turn and opening window");
		openWindow();
		if (kiplay) ki.startTurn();
			
	}
	
	public void itsSomeonesTurn(String nick){
		pcManager.setIndexByPlayer(network.getPlayerByNick(nick));
		closeWindow();
		System.out.println("its "+nick+"s turn and closing window");
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
//	public void highlightCard(String card) {
//		for (int i = 0; i < gameView.getHand().getHandURI().size(); i++) {
//			if (card.equals(gameView.getHand().getHandURI().get(i))) {
//				gameView.getHand().getHand().get(i).setEffect(new Glow(0.5));
//			}
//		}
//	}
	
	public void highlightCard(String card) {
		ArrayList<String> myHand = new ArrayList<String>();
		for (int i = 0; i < gameView.getHand().getHandURI().size(); i++) {
				if(enums.Persons.isMemberPersonName(gameView.getHand().getHandURI().get(i))){
					myHand.add(enums.Persons.getColorByPersonName(gameView.getHand().getHandURI().get(i)));
				}
				else{
					myHand.add(gameView.getHand().getHandURI().get(i));
				}
			
			
		}
		
		for (int i = 0; i < myHand.size(); i++) {
			
			if (card.equals(myHand.get(i))) {
				gameView.getHand().getHand().get(i).setEffect(new Glow(0.5));
			}
		}
	}
	
	public void sendKIMove(CluedoPosition pos){
		network.sendMsgToServer(NetworkMessages.moveMsg(network.getGameId(),new CluedoField(pos)));
	}
	
	/**
	 * 
	 * @param card
	 */
//	public void setCardFunction(String card){
//		for (int i = 0; i < gameView.getHand().getHandURI().size(); i++) {
//			if (card.equals(gameView.getHand().getHandURI().get(i))) {
//				gameView.getHand().getHand().get(i).setOnMousePressed(e -> {
//					sendDisproveMsg(card);
//				});
//				gameView.getHand().getHand().get(i).setOnMouseReleased(e -> {
//					gamePresenter.getHandFramePresenter().removeEffects();
//				});
//			}
//		}
//	}

	
	
	public void setCardFunction(String card){
		ArrayList<String> myHand = new ArrayList<String>();
		for (int i = 0; i < gameView.getHand().getHandURI().size(); i++) {
			if(enums.Persons.isMemberPersonName(gameView.getHand().getHandURI().get(i))){
				myHand.add(enums.Persons.getColorByPersonName(gameView.getHand().getHandURI().get(i)));
			}
			else{
				myHand.add(gameView.getHand().getHandURI().get(i));
			}
		
		
		
	}
		for (int i = 0; i < myHand.size(); i++) {
			if (card.equals(myHand.get(i))) {
				gameView.getHand().getHand().get(i).setOnMousePressed(e -> {
					sendDisproveMsg(card);
				});
				gameView.getHand().getHand().get(i).setOnMouseReleased(e -> {
					gamePresenter.getHandFramePresenter().removeEffects();
				});
			}
		}
	}
	
public void showPoolCards(ArrayList<String> karten) {
		
		Stage pool = new Stage(StageStyle.TRANSPARENT);
		ShowKarten poolcards = new ShowKarten(pool, karten); 
		System.out.println("ShowKarten stage wird erstellt? ");
		Scene secondary = new Scene(poolcards, Config.SHOWKARTEN_WIDTH, Config.SHOWKARTEN_HEIGHT);
		
		System.out.println("PoolCards scene wird erstellt? ");
		secondary.setFill(Color.TRANSPARENT);
		pool.setScene(secondary);
		pool.setOpacity(0.9);
		pool.setAlwaysOnTop(true);
		pool.showAndWait();

	}
	
	public void showDisprovedCard(String karte){
		
		
		Stage pool = new Stage(StageStyle.TRANSPARENT);
		ShowKarten poolcards = new ShowKarten(pool, karte);
		System.out.println("ShowKarten stage wird erstellt? ");
		Scene secondary = new Scene(poolcards, Config.SHOWKARTEN_WIDTH, Config.SHOWKARTEN_HEIGHT);
		
		System.out.println("Disprove scene wird erstellt? ");
		
		secondary.setFill(Color.TRANSPARENT);
		pool.setScene(secondary);
		pool.setOpacity(0.95);
		pool.setAlwaysOnTop(true);
		pool.showAndWait();
	}
	
	public void showCardsforSuspiction(CluedoStatement suspicion) {
		String[] cards = new String[3];
		cards = suspicion.getStatementAsArray();	
		
		System.out.println(cards[0] + cards[2]);
		ArrayList<String> karten = new ArrayList<String>();
		karten.add(cards[0]);
		karten.add(cards[1]);
		karten.add(cards[2]);
		
		String suspector = pcManager.getCurrentNick();
		Stage suspicionStage = new Stage(StageStyle.TRANSPARENT);
		ShowKarten suspicionCards = new ShowKarten(suspicionStage ,karten, suspector); 
		System.out.println("ShowSuspicion stage wird erstellt? ");
		Scene secondary = new Scene(suspicionCards, Config.SUSPICION_WIDTH, Config.SUSPICION_HEIGHT);
		
		System.out.println("PoolCards scene wird erstellt? ");
		secondary.setFill(Color.TRANSPARENT);
		suspicionStage.setScene(secondary);
		suspicionStage.setOpacity(0.90);
		suspicionStage.setAlwaysOnTop(true);
		suspicionStage.showAndWait();

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

		Images.passage.setOnMouseClicked(
				e -> requestUseSecretPassge());
		
		Images.suspectNOW.setOnMouseClicked(e -> {
			String person = zugView.getPersonenListe().getValue();
			String weapon = zugView.getWaffenListe().getValue();
			String room = kacheln.getKacheln()
					[pcManager.getCurrentPlayer().getPosition().getY()]
					[pcManager.getCurrentPlayer().getPosition().getX()].
					getRaum().getName();
			suspect(person,weapon,room);
			
			
			// BOESE!!
			gameView.getKomplettesFeld().getZugView().getOrganizer().getChildren().
			remove(gameView.getKomplettesFeld().getZugView().getBottomBox());
		gameView.getKomplettesFeld().getZugView().getOrganizer().getChildren().
			remove(gameView.getKomplettesFeld().getZugView().getVermuten());
		gameView.getKomplettesFeld().getZugView().getBottomBox().getChildren().
			remove(Images.suspectLATER);
		gameView.getKomplettesFeld().getZugView().getOrganizer().getChildren().
			add(gameView.getKomplettesFeld().getZugView().getButtonsBox());
		gameView.getKomplettesFeld().getZugView().getOrganizer().getChildren().
			add(gameView.getKomplettesFeld().getZugView().getBottomBox());
		gameView.getKomplettesFeld().getZugView().getBottomBox().getChildren().
			remove(gameView.getKomplettesFeld().getZugView().getBackButton());
		gameView.getKomplettesFeld().getZugView().getBottomBox().getChildren().
			add(gameView.getKomplettesFeld().getZugView().getBackButton());
		gameView.getKomplettesFeld().getChildren().remove(zugView);
			
		});

		gameView.getHand().getAccuse().setOnMouseClicked(e -> {
			String person = gameView.getHand().getPersons().getValue();
			String weapon = gameView.getHand().getWeapons().getValue();
			String room = gameView.getHand().getRooms().getValue();
			
			accuse(person,weapon,room);
		});

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
			gameView.getStatusView().setothersstatemsg(str);
		}
			
			public void showPossibleDisprovals(ArrayList<String> possibleDisprovals){
				for(String cardOfTheOne : possibleDisprovals){
					highlightCard(cardOfTheOne);
					setCardFunction(cardOfTheOne);
				}	
			}
			
		
//			public void handleDisprove() {
//				ArrayList<String> disprover = 
//						curSuspicion.makeConjunction(
//								pcManager.getPlayerByNick(myNick).
//								getCards());
//				if (disprover.size() != 0){
//					if (kiplay) ki.chooseDisprove(disprover);
//					else showPossibleDisprovals(disprover);		
//				}
//				else {
//					network.sendMsgToServer(NetworkMessages.cantDisproveMsg(gameID));
//				}				
//			}
			
			public void handleDisprove() {
				ArrayList<String> cards = new ArrayList<String>();
				ArrayList<String> myCards = new ArrayList<String>();
				cards = pcManager.getPlayerByNick(myNick).
						getCards();
				for(int i = 0; i < cards.size(); i++){
					if(enums.Persons.isMemberPersonName(cards.get(i))){
						myCards.add(enums.Persons.getColorByPersonName(cards.get(i)));
					}
					else{
						myCards.add(cards.get(i));
					}
				}
				System.out.println("Unsere Karten: " +pcManager.getPlayerByNick(myNick).
						getCards().toString());
				System.out.println("In der Arraylist: " + myCards.toString());
				ArrayList<String> disprover = 
						curSuspicion.makeConjunction(
								myCards);
				if (disprover.size() != 0){
					if (kiplay) ki.chooseDisprove(disprover);
					else showPossibleDisprovals(disprover);					
				}
				else {
					network.sendMsgToServer(NetworkMessages.cantDisproveMsg(gameID));
				}				
			}

			//arrangiert welche der Buttons im Zugfenster verwendet werde dürfen 
			public void setZugFensterButtons(ArrayList<String> states){
				if(states.contains(PlayerStates.suspect.getName())){
					zugPresenter.enableSuspect();
				} else {
					zugPresenter.disableSuspect();
				}
				if(states.contains(PlayerStates.roll_dice.getName())){
					zugPresenter.enableRoll();
				} else {
					zugPresenter.disableRoll();
				}
				if(states.contains(PlayerStates.use_secret_passage.getName())){
					zugPresenter.enablePassage();
				} else {
					zugPresenter.disablePassage();
				}
			}
			
			public void checkIfWon(String nick){
				if(nick.equals(this.myNick)){
					youHaveWon();
				}
				else {
					youHaveLost();
				}
			}
			
			  public void youHaveWon(){
					Stage stage = new Stage();
					Winner window = new Winner(stage);
					Scene scene = new Scene(window, 480, 360);
					stage.setTitle("You have cracked the mystery!");
					stage.setScene(scene);
					stage.setAlwaysOnTop(true);
					stage.initStyle(StageStyle.UNDECORATED);
					stage.showAndWait();
				}
				
				public void youHaveLost(){
					Stage stage = new Stage();
					Loser window = new Loser(stage);
					Scene scene = new Scene(window, 1280, 720);
					stage.setTitle("Better luck next time!");
					stage.setScene(scene);
					stage.setAlwaysOnTop(true);
					stage.initStyle(StageStyle.UNDECORATED);
					stage.showAndWait();
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
			
	public void showyourstates(String str){
		gameView.getStatusView().setyourstatesmsg(str);
	}
	
	public void showsothersstates(String str) {
		gameView.getStatusView().setothersstatemsg(str);
		
	}
			

			
	public KachelContainer getKacheln() {
		return kacheln;
	}

	public void setKacheln(KachelContainer kacheln) {
		this.kacheln = kacheln;
	}

	public WahnsinnigTollerPathfinder getPathfinder() {
		return pathfinder;
	}

	public void setPathfinder(WahnsinnigTollerPathfinder pathfinder) {
		this.pathfinder = pathfinder;
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
	
	public DicePresenter getDicePresenter() {
		return dicePresenter;
	}

	public void moveForSuspiciton(int gameID2, CluedoStatement suspicion) {
		CluedoPlayer player = pcManager.getPlayerByPerson(suspicion.getPerson());
		beweger.getCarriedAlong(suspicion.getRoom(), player);
	}
	
	public Ausloeser getAusloeser() {
		return ausloeser;
	}
	
	
	public void setCards(String myNick,ArrayList<String> cards) {
		gameView.getHand().setPlayerCards(cards);		
	}
	public void kiSwitch(){
		kiplay = !kiplay;
		auxx.logsevere("KI playing : "+ kiplay);
		
		if(kiplay){
		gameView.getMenu().getActivateKI().setText("Disable KI");
		}else{
		gameView.getMenu().getActivateKI().setText("Enable KI");	
		}
			
	}

	public void moved() {
		if (kiplay){
			ki.moved();
		}
		
	}

//	public void showCardsforSuspiction(CluedoStatement suspicion) {
//		String[] cards = new String[3];
//		cards = suspicion.getStatementAsArray();	
//		
//	}
}

