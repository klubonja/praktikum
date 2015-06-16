package view;

import finderOfPaths.UnglaublicheAnwendung;
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
		
		UnglaublicheAnwendung wow = new UnglaublicheAnwendung(gfv.board, gfv.ballEbene, gfv.komplettesFeld);
		//BoardPresenter board = new BoardPresenter(gfv.board, playerCircle, this.player);
		wow.test();
		
		NotesPresenter notes = new NotesPresenter(gfv.notes);
		HandFramePresenter hand = new HandFramePresenter(gfv.hand);
		MenuBarPresenter menuBar = new MenuBarPresenter(gfv.menu, gfv);
		
		gfv.view2.setOnAction(e -> changeFramePl2());
		
	}
	
	public void changeFramePl2(){
		
	}
	
}
