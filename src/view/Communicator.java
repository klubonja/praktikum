package view;

import java.util.Stack;
import java.util.logging.Level;

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

	private boolean sswitch;
	
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
	private CluedoGameClient network;
	//private ArrayList <String> order;
	public PlayerCircleManager pcManager;
	public final int gameid;

	public Communicator(CluedoGameClient ngame) {
		network = ngame;
		gameid = ngame.getGameId();
		
		pcManager = new PlayerCircleManager(network.getPlayers());
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

		myNick = network.getMyNick();
		
	}

	public void startGame() {
		auxx.loginfo("Communicator started");
		myNick = network.getMyNick();
		setHandler();

		auxx.loginfo("%%%%%%%%%%%%%%% Der nick : " +myNick);
		
		pcManager.setIndexByPlayer(pcManager.playerManager.get(0));
		auxx.loginfo("Der hier fängt an : " +pcManager.getCurrentNick());
		
		auxx.logsevere("Communicator.startGame");
		auxx.logsevere("currentPlayer Color : " +pcManager.getCurrentPlayer().getCluedoPerson().getColor());
		auxx.logsevere("currentPlayer x : " +pcManager.getCurrentPlayer().getPosition().getX() + "  ||  y : " +pcManager.getCurrentPlayer().getPosition().getY());
		
		// Hier ist noch alles richtig
		
		// Hier passiert schon mal nichts.
		testButtons();
		auxx.loginfo("Oh my giddy giddy gosh");
		
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
		
	}
		
	public void endTurn() {
		network.sendMsgToServer(NetworkMessages.end_turnMsg(gameid));
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
