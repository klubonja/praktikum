package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Player;

public class GameFramePresenter {
	
	private GameFrameView gfv;
	Player player;
	
	public GameFramePresenter(GameFrameView gfv , Player player){
		
		this.gfv = gfv;
		this.player = player;
		
		startEvents();
		
	}
	
	@SuppressWarnings("unused")
	public void startEvents(){
		Circle testSpieler = new Circle(14);
		testSpieler.setFill(Color.ROYALBLUE);
		gfv.board.add(testSpieler, player.getxCoord(),this.player.getyCoord());
		DicePresenter dice = new DicePresenter(gfv.dice);
		BoardPresenter board = new BoardPresenter(gfv.board, testSpieler, this.player);
		NotesPresenter notes = new NotesPresenter(gfv.notes);
		
	}
	
}
