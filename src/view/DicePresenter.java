
package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class DicePresenter {
	
	DiceView dice;
	
	private int ergebnis;
	private int first;
	private int second;
	
	
	public DicePresenter(DiceView dice){
		
		this.dice = dice;
		startEvents();
	}
	
	public void startEvents(){
		dice.roll.setOnMouseClicked(e -> rollTheDice());
	}
	
	public void rollTheDice(){
		KeyFrame keyFrame = new KeyFrame(new Duration(250), event -> changeFrame());
		Timeline t = new Timeline(keyFrame);
		t.setCycleCount(10);
		t.play();
		ergebnis = first + second;
		//System.out.println(ergebnis);

	}
	
	public void changeFrame(){
		int [] zahlen = new int[10];
		
		dice.getChildren().remove(dice.d1);
		dice.getChildren().remove(dice.d2);
		
		this.first = 1 + (int)(Math.random()*6);
		this.second = 1 + (int)(Math.random()*6);
		this.ergebnis = first + second;
		switch(first){
		case 1: dice.d1.setImage(dice.dice1);break;
		case 2: dice.d1.setImage(dice.dice2);break;
		case 3: dice.d1.setImage(dice.dice3);break;
		case 4: dice.d1.setImage(dice.dice4);break;
		case 5: dice.d1.setImage(dice.dice5);break;
		case 6: dice.d1.setImage(dice.dice6);break;
		}
		switch(second){
		case 1: dice.d2.setImage(dice.dice1);break;
		case 2: dice.d2.setImage(dice.dice2);break;
		case 3: dice.d2.setImage(dice.dice3);break;
		case 4: dice.d2.setImage(dice.dice4);break;
		case 5: dice.d2.setImage(dice.dice5);break;
		case 6: dice.d2.setImage(dice.dice6);break;
		}
		
		dice.getChildren().addAll(dice.d1, dice.d2);
		System.out.println(ergebnis);
		}

}
=======
package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class DicePresenter {
	
	DiceView dice;
	
	public DicePresenter(DiceView dice){
		
		this.dice = dice;
		startEvents();
	}
	
	public void startEvents(){
		dice.roll.setOnMouseClicked(e -> rollTheDice());
	}
	
	public void rollTheDice(){
		KeyFrame keyFrame = new KeyFrame(new Duration(250), event -> changeFrame());
		Timeline t = new Timeline(keyFrame);
		t.setCycleCount(10);
		t.play();

	}
	
	public void changeFrame(){
		
		dice.getChildren().remove(dice.d1);
		dice.getChildren().remove(dice.d2);
		
		int first = 1 + (int)(Math.random()*6);
		int second = 1 + (int)(Math.random()*6);
		
		switch(first){
		case 1: dice.d1.setImage(dice.dice1);break;
		case 2: dice.d1.setImage(dice.dice2);break;
		case 3: dice.d1.setImage(dice.dice3);break;
		case 4: dice.d1.setImage(dice.dice4);break;
		case 5: dice.d1.setImage(dice.dice5);break;
		case 6: dice.d1.setImage(dice.dice6);break;
		}
		switch(second){
		case 1: dice.d2.setImage(dice.dice1);break;
		case 2: dice.d2.setImage(dice.dice2);break;
		case 3: dice.d2.setImage(dice.dice3);break;
		case 4: dice.d2.setImage(dice.dice4);break;
		case 5: dice.d2.setImage(dice.dice5);break;
		case 6: dice.d2.setImage(dice.dice6);break;
		}
		
		dice.getChildren().addAll(dice.d1, dice.d2);
		}

}

