package view;

import finderOfPaths.Ausloeser;
import finderOfPaths.DerBeweger;
import finderOfPaths.Sucher;
import finderOfPaths.UnglaublicheAnwendung;
import finderOfPaths.Vorschlaege;
import finderOfPaths.WahnsinnigTollerPathfinder;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Player;

public class GameFramePresenter {
	
	private GameFrameView gfv;
	Player player;
	Circle playerCircle;
	private Stage stage;
	
	private Ausloeser ausloeser;
	private Sucher sucher;
	private DerBeweger beweger;
	private Vorschlaege vorschlager;
	private WahnsinnigTollerPathfinder pathfinder;
	private char [][] anweisungen;
	
	
	public GameFramePresenter(GameFrameView gfv , Player player){
		
		this.gfv = gfv;
		this.player = player;
		this.playerCircle = new Circle(12);
		this.playerCircle.setFill(player.getColor());
		//this.primaryStage = primaryStage;
		
		startEvents();
		
	}
	
	@SuppressWarnings("unused")
	public void startEvents(){
		
//		GridPane.setConstraints(playerCircle, this.player.getxCoord(), this.player.getyCoord());
//		gfv.board.getChildren().add(playerCircle);
		
		DicePresenter dice = new DicePresenter(gfv.dice);
		
//		UnglaublicheAnwendung wow = new UnglaublicheAnwendung(gfv.board, gfv.ballEbene, gfv.komplettesFeld);
//		wow.test();

//		BoardPresenter board = new BoardPresenter(gfv.board, playerCircle, this.player);

		
		NotesPresenter notes = new NotesPresenter(gfv.notes);
		HandFramePresenter hand = new HandFramePresenter(gfv.hand);
		MenuBarPresenter menuBar = new MenuBarPresenter(gfv.menu, gfv);
		
		// creates the Player Area and the Movement
		player = new Player("Hans",4,5, Color.AQUAMARINE);
		
		beweger = new DerBeweger(gfv.board, gfv.komplettesFeld, gfv.ballEbene, player);
		vorschlager = new Vorschlaege(gfv.board, player);
		pathfinder = new WahnsinnigTollerPathfinder(gfv.board, gfv.ballEbene, player);
		
		sucher = new Sucher(gfv.board, gfv.ballEbene, gfv.komplettesFeld, beweger, vorschlager, pathfinder,  player, anweisungen);
		ausloeser = new Ausloeser(gfv.board, beweger, gfv.ballEbene, pathfinder, sucher, player);
		
		test();
		
		System.out.println("test vorbei");
	}
	
	
		
		
	
	
	public void test(){
		System.out.println("test");
		ausloeser.zuweisung();
	}
	
	public void changeFramePl2(){
		gfv.view2.setOnAction(e -> changeFramePl2());
	}
	
}
