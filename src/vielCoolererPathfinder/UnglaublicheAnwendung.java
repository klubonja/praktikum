package vielCoolererPathfinder;

import javafx.application.Application;
import javafx.stage.Stage;

public class UnglaublicheAnwendung extends Application{

	private GUI gui;
	private BallEbene ballEbene;
	private KrasserStack krasserStack;
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		
		gui = new GUI(3,3);
		ballEbene = new BallEbene();
		krasserStack = new KrasserStack(ballEbene, gui);
		krasserStack.start();
		
		WahnsinnigTollerPathfinder pathfinder = new WahnsinnigTollerPathfinder(gui, ballEbene);
		
		System.out.println(ballEbene.getSpieler().getLayoutX());
		System.out.println(ballEbene.getSpieler().getLayoutY());
		
		
		
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
