
package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import finderOfPaths.Ausloeser;
import finderOfPaths.Sucher;

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
	
	private int [] dice = new int [2];
	
	DiceView view;
	
	private Ausloeser ausloeser;
	private BoardView gui;
	private Sucher sucher;
	
	public DicePresenter(DiceView view, Ausloeser ausloeser, BoardView gui, Sucher sucher){
		this.ausloeser = ausloeser;
		this.view = view;
		this.sucher = sucher;
		this.gui = gui;
		startEvents();
	}
	
	public void startEvents(){
		
		view.getrollBtn().setOnMouseClicked(e -> rollTheDice());
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
		if (diceCounter == 10){
			dice[0] = first;
			dice[1] = second;
			wuerfeln();
		}
		
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
		
		}
	

	/**
	 * Testmethode zum Würfeln
	 */
	public void wuerfeln(){
		gui.resetBackground();
		gui.resetMoeglichkeiten();
		ausloeser.setWuerfelZahl(dice[0] + dice[1]);
		System.out.println("==================================");
		System.out.println("==================================");
		System.out.println("Würfelzahl : " +ausloeser.getWuerfelZahl());
		System.out.println("==================================");
		System.out.println("==================================");
		
		
		sucher.suchen(ausloeser.getWuerfelZahl());
		ausloeser.zuweisung();
		ausloeser.setGewuerfelt(true);
	}
	

	public int[] getDice() {
		return dice;
	}

	public void setDice(int[] dice) {
		this.dice = dice;
	}

	
	
}

