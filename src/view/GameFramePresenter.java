package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Player;

public class GameFramePresenter {
	
	private GameFrameView gfv;
	
	public GameFramePresenter(GameFrameView gfv){
		
		this.gfv = gfv;
		
		startEvents();
		
	}
	
	@SuppressWarnings("unused")
	public void startEvents(){

		Player player = new Player("test", 0, 0);

		Circle testSpieler = new Circle(13);
		testSpieler.setFill(Color.ROYALBLUE);
		gfv.board.add(testSpieler, player.getxCoord(),player.getyCoord());
		DicePresenter dice = new DicePresenter(gfv.dice);
		BoardPresenter board = new BoardPresenter(gfv.board, testSpieler, player);
		
	}
	
}
