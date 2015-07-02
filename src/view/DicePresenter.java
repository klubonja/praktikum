
package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import staticClasses.Sounds;
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
	
	private GameFrameView view;
	private DiceView dices;
	
	private Ausloeser ausloeser;
	private BoardView gui;
	private Sucher sucher;
	
	private int zielWuerfelEins;
	private int zielWuerfelZwei;
	
	public DicePresenter(DiceView dices, GameFrameView view, Ausloeser ausloeser, BoardView gui, Sucher sucher){
		this.ausloeser = ausloeser;
		this.view = view;
		this.dices = dices;
		this.sucher = sucher;
		this.gui = gui;
		startEvents();
	}
	
	public void startEvents(){
		
		view.getKomplettesFeld().getZug().YESwurfelImage.setOnMouseClicked(e -> rollTheDice());
	}
	
	public void rollTheDice(){
		Sounds.diceSound();
		view.getKomplettesFeld().getChildren().remove(view.getKomplettesFeld().getZug());
		diceCounter = 0;
		KeyFrame keyFrame = new KeyFrame(new Duration(250), event -> changeFrame("yourself"));
		Timeline t = new Timeline(keyFrame);
		t.setCycleCount(10);
		t.play();
		
		}
	
	public void rollTheDiceForSomeone(int ersterWuerfel, int zweiterWuerfel){
		this.zielWuerfelEins = ersterWuerfel;
		this.zielWuerfelZwei = zweiterWuerfel;
		diceCounter = 0;
		KeyFrame keyFrame = new KeyFrame(new Duration(250), event -> changeFrame("someone"));
		Timeline t = new Timeline(keyFrame);
		t.setCycleCount(10);
		t.play();
		
	}
	
	public void changeFrame(String who){
		
		diceCounter = diceCounter + 1;
		
		dices.getChildren().remove(dices.getD1());
		dices.getChildren().remove(dices.getD2());
		
		int first = 1 + (int)(Math.random()*6);
		int second = 1 + (int)(Math.random()*6);
		
		if (who == "someone" && diceCounter == 10){
			first = zielWuerfelEins;
			second = zielWuerfelZwei;
		}
		
		if (diceCounter == 10){
			dice[0] = first;
			dice[1] = second;
			wuerfeln();
		}
		
		switch(first){
		case 1: dices.getD1().setImage(dices.getDice1());break;
		case 2: dices.getD1().setImage(dices.getDice2());break;
		case 3: dices.getD1().setImage(dices.getDice3());break;
		case 4: dices.getD1().setImage(dices.getDice4());break;
		case 5: dices.getD1().setImage(dices.getDice5());break;
		case 6: dices.getD1().setImage(dices.getDice6());break;
		}
		switch(second){
		case 1: dices.getD2().setImage(dices.getDice1());break;
		case 2: dices.getD2().setImage(dices.getDice2());break;
		case 3: dices.getD2().setImage(dices.getDice3());break;
		case 4: dices.getD2().setImage(dices.getDice4());break;
		case 5: dices.getD2().setImage(dices.getDice5());break;
		case 6: dices.getD2().setImage(dices.getDice6());break;
		}
		
		dices.getChildren().addAll(dices.getD1(), dices.getD2());
		
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

