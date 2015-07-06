package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

import org.json.JSONObject;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;
import jdk.nashorn.api.scripting.JSObject;
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
		dicePresenter.rollTheDiceForSomeone(ersterWuerfel, zweiterWuerfel);
	}

	public void useSecretPassage() {

		beweger.useSecretPassage();
	}

	public void move(CluedoPosition position) {
		int yKoordinate = position.getY();
		int xKoordinate = position.getX();
		ausloeser.ausloesen(yKoordinate, xKoordinate);
	}

	// SENDS MESSAGE BUT SERVER DOESNT DO SHIT AFTER THAT
	public void suspect() {
		String person = zugView.getPersonenListe().getValue();
		String weapon = zugView.getWaffenListe().getValue();
		String room = "hall";
		network.sendMsgToServer(NetworkMessages.suspicionMsg(
				network.getGameId(),
				NetworkMessages.statement(person, room, weapon)));
		ArrayList<CluedoPlayer> list = network.getPlayersConnected();
		String color = "";
		for(CluedoPlayer p : list){
			if(p.getNick().equals(network.getMyNick())){
				color = p.getCluedoPerson().getColor();
			}
		}
		System.out.println("COLOR IS HEEEEEEEEEEEEEEEEEERE " + color);
		network.sendMsgToServer(
				(NetworkMessages.player_info(network.getMyNick(),
				color,
				PlayerStates.suspect.getName())).toString());
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

	public void endTurn() {

		// Communicator.playerManager.next();
		// Communicator.circleManager.next();
		pcManager.next();// erhöht den index und sonst nix
		// ///////////////////////////////////
		// /BENACHRICHTUGUNG, DASS NÄCHSTER///
		// ///////ZUG ANGEFANGEN HAT//////////
		// ///////////////////////////////////
	}

	public void kill() {
		gameView.close();
	}

	public void testButtons() {
		// ballEbene.getFremdBewegen().setOnAction(e -> move(new int [] hans =
		// new int {5,9}));
		int[] cheater = { 6, 6 };
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
		gameView.getHand().getEndTurn().setOnMouseClicked(e -> {
		});

	}

	// OPEN WINDOW
	public void openWindow() {
		gameView.getKomplettesFeld().getChildren().add(zugView);
	}

	// CLOSE WINDOW
	public void closeWindow() {
		gameView.getKomplettesFeld().getChildren().remove(zugView);
	}
	
	public void changeLabel(String str){
		gameView.getHand().getText().setText(str);
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
