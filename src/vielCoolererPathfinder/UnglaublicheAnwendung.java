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
	
	private DerBeweger beweger;
	private Vorschlaege vorschlager;
	private WahnsinnigTollerPathfinder pathfinder;
	private Ausloeser ausloeser;
	
	Player player;
	
	private char [][] anweisungen;
	private Orientation [][] anweisungenOrientations = new Orientation [244000][12];
	
	public UnglaublicheAnwendung() {

		player = new Player("Hans",1,2, Color.AQUAMARINE);
		
		gui = new GUI(3,5);
		ballEbene = new BallEbene2();
		krasserStack = new KrasserStack(ballEbene, gui);

		beweger = new DerBeweger(gui, krasserStack, ballEbene, player);
		vorschlager = new Vorschlaege(gui);
		pathfinder = new WahnsinnigTollerPathfinder(gui, ballEbene, player);
		ausloeser = new Ausloeser(gui, beweger, ballEbene, pathfinder);
		
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		krasserStack.start();		
		
		suchen();
		zuweisen();
		
		player = new Player("Hans", 1, 2, Color.AQUAMARINE);
		
		
		
		
		
		
		
		
		
		

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

	public void suchen(){
		
		pathfinder.findThatPathBetter(2);
		
		anweisungen = pathfinder.getMoeglichkeiten();		
		
		vorschlager.vorschlaegeMachen(anweisungen, gui.getKachelArray()[1][2]);
		
	}
	
	public void zuweisen(){
		ausloeser.zuweisung(); // ruft Bewegung auf
	}
	
	public static void main(String[] args) {
		launch(args);
	}


	
}
