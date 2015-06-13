package view;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Player;

public class GameFramePresenter {
	
	private GameFrameView gfv;
	Player player;
	Circle playerCircle;
	
	public GameFramePresenter(GameFrameView gfv , Player player){
		
		this.gfv = gfv;
		this.player = player;
		this.playerCircle = new Circle(12);
		this.playerCircle.setFill(player.getColor());
		
		startEvents();
		
	}
	
	@SuppressWarnings("unused")
	public void startEvents(){
		
		GridPane.setConstraints(playerCircle, this.player.getxCoord(), this.player.getyCoord());
		gfv.board.getChildren().add(playerCircle);
		
		DicePresenter dice = new DicePresenter(gfv.dice);

		BoardPresenter board = new BoardPresenter(gfv.board, playerCircle, this.player);

		NotesPresenter notes = new NotesPresenter(gfv.notes);
		HandFramePresenter hand = new HandFramePresenter(gfv.hand);
		
	}
	
}
