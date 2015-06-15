package finderOfPaths;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Player;
import view.BoardView;
import enums.Orientation;

public class UnglaublicheAnwendung extends Application{

	private BoardView boardView;
	private BallEbene2 ballEbene;
	private KrasserStack krasserStack;
	
	private DerBeweger beweger;
	private Vorschlaege vorschlager;
	private WahnsinnigTollerPathfinder pathfinder;
	private Ausloeser ausloeser;
	
	private Sucher sucher;
	
	private int wuerfelZahl;
	
	private Player player;
	
	private char [][] anweisungen;
	private Orientation [][] anweisungenOrientations = new Orientation [244000][12];
	
	public UnglaublicheAnwendung() {

		player = new Player("Hans",5,5, Color.AQUAMARINE);
		
		boardView = new BoardView(24,25);
		ballEbene = new BallEbene2();
		krasserStack = new KrasserStack(ballEbene, boardView);

		beweger = new DerBeweger(boardView, krasserStack, ballEbene, player);
		vorschlager = new Vorschlaege(boardView, player);
		pathfinder = new WahnsinnigTollerPathfinder(boardView, ballEbene, player);
		
		sucher = new Sucher(boardView, ballEbene, krasserStack, beweger, vorschlager, pathfinder,  player, anweisungen);
		ausloeser = new Ausloeser(boardView, beweger, ballEbene, pathfinder, sucher, player);
	}
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		krasserStack.start();		

		ausloeser.zuweisung();
				
		player = new Player("Hans", 5, 5, Color.AQUAMARINE);
				
		
		
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
