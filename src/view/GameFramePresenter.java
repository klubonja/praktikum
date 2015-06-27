package view;

import staticClasses.auxx;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;
import enums.Persons;
import enums.PlayerStates;
import finderOfPaths.Ausloeser;
import finderOfPaths.DerBeweger;
import finderOfPaths.Sucher;
import finderOfPaths.Vorschlaege;
import finderOfPaths.WahnsinnigTollerPathfinder;

public class GameFramePresenter {
	
	private GameFrameView gfv;
	private CluedoPlayer player;
	Circle playerCircle;
	private Stage stage;
	
	private Ausloeser ausloeser;
	private Sucher sucher;
	private DerBeweger beweger;
	private Vorschlaege vorschlager;
	private WahnsinnigTollerPathfinder pathfinder;
	private char [][] anweisungen;
	
	
	public GameFramePresenter(GameFrameView gfv , CluedoPlayer player){
		
		this.gfv = gfv;
		this.player = player;
		this.playerCircle = new Circle(12);
		//this.playerCircle.setFill(player.getColor());
		
		startEvents();
		
	}
	
	@SuppressWarnings("unused")
	public void startEvents(){
		
		auxx.logsevere("null? : " +player.getPosition().getY() +player.getPosition().getX());
	
		NotesPresenter notes = new NotesPresenter(gfv.notes);
		HandFramePresenter hand = new HandFramePresenter(gfv.hand);
		MenuBarPresenter menuBar = new MenuBarPresenter(gfv.menu, gfv);
		

		// creates the Player Area and the Movement
		//player = new CluedoPlayer(Persons.red, PlayerStates.do_nothing, new CluedoPosition(5,5));
		
		beweger = new DerBeweger(gfv.board, gfv.ballEbene, player);
		vorschlager = new Vorschlaege(gfv.board, player);
		pathfinder = new WahnsinnigTollerPathfinder(gfv.board, gfv.ballEbene, player);
		
		sucher = new Sucher(gfv.board, gfv.ballEbene, beweger, vorschlager, pathfinder,  player, anweisungen);
		ausloeser = new Ausloeser(gfv.board, beweger, gfv.ballEbene, pathfinder, sucher, player);
		
		DicePresenter dice = new DicePresenter(gfv.dice, ausloeser, gfv.board, sucher);
		
		test();
		
		System.out.println("test vorbei");

	}

	

	//Getter and Setters
	public GameFrameView getGfv() {
		return gfv;
	}

	public void setGfv(GameFrameView gfv) {
		this.gfv = gfv;
	}
		
	public void test(){
		beweger.anfangsPositionSetzen();
		System.out.println("test");
		ausloeser.zuweisung();
	}
	

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	
}
