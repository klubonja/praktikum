package view;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameFramePresenter {
	
	private GameFrameView gfv;
	
	public GameFramePresenter(GameFrameView gfv){
		
		this.gfv = gfv;
		
		startEvents();
		
	}
	
	public void startEvents(){
		gfv.rollDice.setOnMouseClicked(e -> rollTheDice());
	}
	
	public void rollTheDice(){
	
		KeyFrame keyFrame = new KeyFrame(new Duration(250), event -> changeFrame());
		Timeline t = new Timeline(keyFrame);
		t.setCycleCount(10);
		t.play();

	}
	
	public void changeFrame(){
		
		gfv.dice.getChildren().remove(gfv.d1);
		gfv.dice.getChildren().remove(gfv.d2);
		
		int first = 1 + (int)(Math.random()*6);
		int second = 1 + (int)(Math.random()*6);
		
		switch(first){
		case 1: gfv.d1.setImage(gfv.dice1);break;
		case 2: gfv.d1.setImage(gfv.dice2);break;
		case 3: gfv.d1.setImage(gfv.dice3);break;
		case 4: gfv.d1.setImage(gfv.dice4);break;
		case 5: gfv.d1.setImage(gfv.dice5);break;
		case 6: gfv.d1.setImage(gfv.dice6);break;
		}
		switch(second){
		case 1: gfv.d2.setImage(gfv.dice1);break;
		case 2: gfv.d2.setImage(gfv.dice2);break;
		case 3: gfv.d2.setImage(gfv.dice3);break;
		case 4: gfv.d2.setImage(gfv.dice4);break;
		case 5: gfv.d2.setImage(gfv.dice5);break;
		case 6: gfv.d2.setImage(gfv.dice6);break;
		}
		
		gfv.dice.getChildren().addAll(gfv.d1, gfv.d2);
		}

}
