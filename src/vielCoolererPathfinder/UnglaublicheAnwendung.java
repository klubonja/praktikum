package vielCoolererPathfinder;

import javafx.application.Application;
import javafx.stage.Stage;

public class UnglaublicheAnwendung extends Application{

	private GUI gui;
	private BallEbene ballEbene;
	private KrasserStack krasserStack;
	
	private char [][] anweisungen;
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		
		gui = new GUI(5,5);
		ballEbene = new BallEbene();
		krasserStack = new KrasserStack(ballEbene, gui);
		krasserStack.start();
		
		
		
		DerBeweger beweger = new DerBeweger(gui, krasserStack, ballEbene);
		//char [] anweisungen = {'S','W', 'O'}; 
		
		//beweger.bewegen(anweisungen);
		
		WahnsinnigTollerPathfinder pathfinder = new WahnsinnigTollerPathfinder(gui, ballEbene);
		
		anweisungen = pathfinder.getMoeglichkeiten();
		
		beweger.cheaten(anweisungen);
		
		System.out.println(ballEbene.getSpieler().getLayoutX());
		System.out.println(ballEbene.getSpieler().getLayoutY());
		
		
		
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	public char[][] getAnweisungen() {
		return anweisungen;
	}

	public void setAnweisungen(char[][] anweisungen) {
		this.anweisungen = anweisungen;
	}
	
	
	
}
