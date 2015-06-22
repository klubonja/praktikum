package finderOfPaths;

import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import kacheln.Kachel;
import model.Player;
import view.BoardView;
import enums.Orientation;

public class DerBeweger {

	private BoardView gui;
	private KrasserStack krasserStack;
	private BallEbene2 ballEbene;
	
	private Player player;
	
	private Orientation [] anweisungen;
	private int momentaneAnweisung;
	private int wieVieleAnweisungen;
	
	private int jetzigeSpalte;
	private int jetzigeReihe;
	
	private Kachel raumZielKachel;
	private Kachel raumAnfangsKachel;
	
	private Kachel startKachel;
	
	private int yDistanz;
	private int xDistanz;
	
	private Circle spieler;
	
    private KeyValue keyValueX;
    private KeyValue keyValueY;
    
    private int schritte;
    private int nullSchritte;
    
    private Kachel anfangsKachel;
    private Kachel zielKachel;
	
	
	public DerBeweger(BoardView gui, KrasserStack krasserStack, BallEbene2 ballEbene, Player player){
		this.gui = gui;
		this.krasserStack = krasserStack;
		this.ballEbene = ballEbene;
		this.player = player;
		anfangsKachel = gui.getKachelArray()[player.getyCoord()][player.getxCoord()];
		
		spieler = ballEbene.getSpieler();
	}
	
	public void bewegen(Orientation [] anweisungenEingabe, int schritteEingabe, int nullSchritteEingabe){
		
		System.out.println("Beweger active!");
		
		this.schritte = schritteEingabe;
		
		this.nullSchritte = nullSchritteEingabe;
		
		this.anweisungen = anweisungenEingabe;
		
		wieVieleAnweisungen = 0;
		
		
		
		for (int i = 0; i < anweisungen.length;i++)
			{
				if (anweisungen[i] != null){
					wieVieleAnweisungen++;
				}
			}
			
//		System.out.println("��������������������������");
//		System.out.println("wievieleAnweisungen : " +wieVieleAnweisungen);
//		System.out.println("schritte : "+schritte);
//		System.out.println("momentaneAnweisung : "+momentaneAnweisung);

		momentaneAnweisung = wieVieleAnweisungen + nullSchritte - schritte;
				
		
		if (schritte>0){
			
			System.out.println("while-anfang");
			
			jetzigeSpalte = player.getxCoord();
			jetzigeReihe = player.getyCoord();
			
			System.out.println("??????????????????");
			
			for (int i = 0; i < anweisungen.length; i++){
				
				System.out.println("null?   " +anweisungen[i]);
			}
			
			
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			System.out.println("player y : " +player.getyCoord());
			System.out.println("player x : " +player.getxCoord());
			
			
			System.out.println(anweisungen[momentaneAnweisung]);
			
			if (anweisungen[momentaneAnweisung] == Orientation.S){
				yDistanz = 1;
				xDistanz = 0;
			}
			
			else if (anweisungen[momentaneAnweisung] == Orientation.O){
				xDistanz = 1;
				yDistanz = 0;
			}
			
			else if (anweisungen[momentaneAnweisung] == Orientation.N){
				yDistanz = -1;
				xDistanz = 0;
			}
			
			else if (anweisungen[momentaneAnweisung] == Orientation.W){
				xDistanz = -1;
				yDistanz = 0;
			}
			
			else {
				xDistanz = 0;
				yDistanz = 0;
			}
			
			if (jetzigeReihe+yDistanz != 26 && jetzigeSpalte + xDistanz != 25 && jetzigeReihe+yDistanz >= 0 && jetzigeSpalte + xDistanz >= 0) 
			{
				zielKachel = gui.getKachelArray()[jetzigeReihe+yDistanz][jetzigeSpalte+xDistanz];
			}
			
			System.out.println("yDistanz : " +yDistanz +"   xDistanz : " +xDistanz);
			
//			System.out.println("anfangsKachel X : " +anfangsKachel.getLayoutX() +"  anfangsKachel Y : " +anfangsKachel.getLayoutY());
//			System.out.println("zielKachel X : " +zielKachel.getLayoutX() +"  zielKachel Y : " +zielKachel.getLayoutY());
			
			Path path = new Path();
			path.getElements().add(new MoveTo(anfangsKachel.getLayoutX(), anfangsKachel.getLayoutY()));

			path.getElements().add(new LineTo(zielKachel.getLayoutX(), zielKachel.getLayoutY()));

			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(Math.abs(yDistanz) * 100 + Math.abs(xDistanz)
					* 100));
			pathTransition.setNode(spieler);
			pathTransition.setPath(path);
			pathTransition.play();
			pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					anfangsKachel = zielKachel;
					schritte--;
					bewegen(anweisungen, schritte, nullSchritte);
				}
			});
			
			player.setyCoord(jetzigeReihe + yDistanz);
			player.setxCoord(jetzigeSpalte + xDistanz);
//			System.out.println("///////////////////////////////////");
//			System.out.println("player x : " +player.getyCoord());
//			System.out.println("player y : " +player.getxCoord());
			
			}
		
//			for (Orientation momentanesElement : anweisungen){
//				momentanesElement = null; 
//			}
		
			
			
			
		}
	
		public void anfangsPositionSetzen(){

			System.out.println("��������������������");
			System.out.println("anfangs positionen!");
			System.out.println("player y : " +player.getyCoord() +"   player x : " +player.getxCoord());
			
			startKachel = gui.getKachelArray()[player.getyCoord()][player.getxCoord()];
			
			System.out.println(" test " + startKachel.getLayoutX());
			
			System.out.println("layout y : " +gui.getKachelArray()[player.getyCoord()][player.getxCoord()].getLayoutX() +"  layout x : " +gui.getKachelArray()[player.getyCoord()][player.getxCoord()].getLayoutY());
			
			Path path = new Path();
			path.getElements().add(new MoveTo(0,0));
			path.getElements().add(new LineTo(gui.getKachelArray()[player.getyCoord()][player.getxCoord()].getLayoutX(), gui.getKachelArray()[player.getyCoord()][player.getxCoord()].getLayoutY()));

			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(Math.abs(player.getyCoord()) * 500 + Math.abs(player.getxCoord()) * 500));
			pathTransition.setNode(spieler);
			pathTransition.setPath(path);
			pathTransition.play();

			
		}
		
		public void inRaumBewegen(String raum, Player player){
			
			if (raum == "Eingangshalle"){
				if (player.getColor() == Color.PURPLE){
					raumZielKachel = gui.getKachelArray()[11][1];
				}
				
				else if (player.getColor() == Color.BLUE){
					
				}
			}
			
			raumAnfangsKachel = gui.getKachelArray()[player.getyCoord()][player.getxCoord()];
			
			System.out.println(" test " + startKachel.getLayoutX());
			
			System.out.println("layout y : " +gui.getKachelArray()[player.getyCoord()][player.getxCoord()].getLayoutX() +"  layout x : " +gui.getKachelArray()[player.getyCoord()][player.getxCoord()].getLayoutY());
			
			Path path = new Path();
			path.getElements().add(new MoveTo(raumAnfangsKachel.getLayoutX(),raumAnfangsKachel.getLayoutY()));
			path.getElements().add(new LineTo(raumZielKachel.getLayoutX(), raumZielKachel.getLayoutY()));

			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(2000));
			pathTransition.setNode(spieler);
			pathTransition.setPath(path);
			pathTransition.play();
			pathTransition.setOnFinished(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					
					System.out.println("Spieler " +player.getFirstName() + " ist jetzt im Raum " +raum );
					
				}
				
			});

		}
	
	}

