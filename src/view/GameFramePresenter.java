package view;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import view.*;

public class GameFramePresenter {
	
	private GameFrameView gfv;
	
	public GameFramePresenter(GameFrameView gfv){
		
		this.gfv = gfv;
		
		startEvents();
		
	}
	
	public void startEvents(){
		DicePresenter dice = new DicePresenter(gfv.dice);
		
	}
	
}
