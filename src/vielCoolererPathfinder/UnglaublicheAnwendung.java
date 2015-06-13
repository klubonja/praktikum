package vielCoolererPathfinder;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Player;
import enums.Orientation;

public class UnglaublicheAnwendung extends Application{

	private GUI gui;
	private BallEbene2 ballEbene;
	private KrasserStack krasserStack;
	
	
	private char [][] anweisungen;
	private Orientation [][] anweisungenOrientations = new Orientation [244000][12];
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		Player player = new Player("Hans", 1, 2, Color.AQUAMARINE);
		
		gui = new GUI(3,5);
		ballEbene = new BallEbene2();
		krasserStack = new KrasserStack(ballEbene, gui);
		krasserStack.start();
		
		
		
		DerBeweger beweger = new DerBeweger(gui, krasserStack, ballEbene);
		Vorschlaege vorschlager = new Vorschlaege(gui);
		WahnsinnigTollerPathfinder pathfinder = new WahnsinnigTollerPathfinder(gui, ballEbene);
		Ausloeser ausloeser = new Ausloeser(gui, beweger, ballEbene, pathfinder);
		
		
		pathfinder.findThatPathBetter();
		
		anweisungen = pathfinder.getMoeglichkeiten();		
		
		vorschlager.vorschlaegeMachen(anweisungen, gui.getKachelArray()[1][2]);
		
		ausloeser.zuweisung(); // ruft Bewegung auf
		
		
		
		
		
		
		
		
		
		

		for (int i = 0; i < 10; i++){
			for (int j = 0; j < anweisungen[i].length; j++){
				//System.out.println(anweisungen.length);
				System.out.println("Möglichkeiten : " +anweisungen[i][j]);
			}
		}
		
		
		
//		
//		for (int counter = 0; counter < anweisungen.length; counter++){
//			for (int counterInnen = 0; counterInnen < anweisungen[counter].length; counterInnen++){
//				if (anweisungen[counter][counterInnen] == 'S'){
//					anweisungenOrientations[counter][counterInnen] = Orientation.S;
//				}
//				
//				if (anweisungen[counter][counterInnen] == 'E'){
//					anweisungenOrientations[counter][counterInnen] = Orientation.O;
//				}
//				
//				if (anweisungen[counter][counterInnen] == 'N'){
//					anweisungenOrientations[counter][counterInnen] = Orientation.N;
//				}
//				
//				if (anweisungen[counter][counterInnen] == 'W'){
//					anweisungenOrientations[counter][counterInnen] = Orientation.W;
//				}
//				 
//			}
//		}
//		
//		for (int i = 0; i < anweisungenOrientations[1].length;i++)
//		{
//			System.out.println(anweisungenOrientations[1][i]);
//		}
//		
//		for (int i = 0; i < anweisungenKonkret.length; i++){
//			System.out.println(anweisungenKonkret[i]);
//		}
//		
		
	}

	public static void main(String[] args) {
		launch(args);
	}


	
}
