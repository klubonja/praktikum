
package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * 
 * @since 26.05.2015
 * @author YinYanYolos
 * 
 * The class DicePresenter. Makes able to trigger all the events of the DiceView class.
 *
 */

public class DicePresenter {
	
	public static int diceCounter = 0;
	
	DiceView view;
	
	public DicePresenter(DiceView view){
		
		this.view = view;
		startEvents();
	}
	
	public void startEvents(){
		
		view.getrollBtn().setOnMouseClicked(e -> rollTheDice());
		view.getmoveBtn().setOnAction(e -> getMove());
	}
	
	public void rollTheDice(){
		diceCounter = 0;
		KeyFrame keyFrame = new KeyFrame(new Duration(250), event -> changeFrame());
		Timeline t = new Timeline(keyFrame);
		t.setCycleCount(10);
		t.play();
		
		}
	
	public void changeFrame(){
		
		diceCounter = diceCounter + 1;
		
		view.getChildren().remove(view.getD1());
		view.getChildren().remove(view.getD2());
		
		int first = 1 + (int)(Math.random()*6);
		int second = 1 + (int)(Math.random()*6);
		
		switch(first){
		case 1: view.getD1().setImage(view.getDice1());break;
		case 2: view.getD1().setImage(view.getDice2());break;
		case 3: view.getD1().setImage(view.getDice3());break;
		case 4: view.getD1().setImage(view.getDice4());break;
		case 5: view.getD1().setImage(view.getDice5());break;
		case 6: view.getD1().setImage(view.getDice6());break;
		}
		switch(second){
		case 1: view.getD2().setImage(view.getDice1());break;
		case 2: view.getD2().setImage(view.getDice2());break;
		case 3: view.getD2().setImage(view.getDice3());break;
		case 4: view.getD2().setImage(view.getDice4());break;
		case 5: view.getD2().setImage(view.getDice5());break;
		case 6: view.getD2().setImage(view.getDice6());break;
		}
		
		view.getChildren().addAll(view.getD1(), view.getD2());
		
		if (diceCounter == 10){
			
			view.getChildren().remove(view.getrollBtn());
			view.getChildren().add(view.getmoveBtn());
			}
		
		}
	
	
	public int getMove(){
		 
		int a = 0;
		int b = 0;
		
		if(view.getD1().getImage().equals(view.getDice1())){
			a=1;
		}
		if(view.getD1().getImage().equals(view.getDice2())){
			a=2;
		}
		if(view.getD1().getImage().equals(view.getDice3())){
			a=3;
		}
		if(view.getD1().getImage().equals(view.getDice4())){
			a=4;
		}
		if(view.getD1().getImage().equals(view.getDice5())){
			a=5;
		}
		if(view.getD1().getImage().equals(view.getDice6())){
			a=6;
		}
		
		if(view.getD2().getImage().equals(view.getDice1())){
			b=1;
		}
		if(view.getD2().getImage().equals(view.getDice2())){
			b=2;
		}
		if(view.getD2().getImage().equals(view.getDice3())){
			b=3;
		}
		if(view.getD2().getImage().equals(view.getDice4())){
			b=4;
		}
		if(view.getD2().getImage().equals(view.getDice5())){
			b=5;
		}
		if(view.getD2().getImage().equals(view.getDice6())){
			b=6;
		}
		
		
		
		System.out.println(a + b);
		view.getChildren().remove(view.getmoveBtn());
		view.getChildren().add(view.getrollBtn());
		return a + b;
		
		
		
	}

}

