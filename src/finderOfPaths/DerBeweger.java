package finderOfPaths;



import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import kacheln.Kachel;
import kacheln.TuerKachel;
import model.Player;
import staticClasses.auxx;
import view.BoardView;
import cluedoNetworkLayer.CluedoPlayer;
import enums.Orientation;
import enums.Rooms;

/**
 * @version 25.06.2015
 * @author Benedikt Mayer
 *
 * Führt die Animation bzw. Bewegung der Figur aus.
 */
public class DerBeweger {

	private BoardView gui;
	private BallEbene2 ballEbene;
	
	private CluedoPlayer player;
	
	private Orientation [] anweisungen;
	private Orientation [] anweisungenVonHier;
	private int momentaneAnweisung;
	private int wieVieleAnweisungen;
	
	private int jetzigeSpalte;
	private int jetzigeReihe;
	
	private Kachel raumZielKachel;
	private Kachel raumAnfangsKachel;
	
	private Kachel tuerZielKachel;
	private TuerKachel anfangsTuerKachel;
	private Rooms room;
	private RaumBeweger rB;

	private Kachel startKachel;
	
	private int yDistanz;
	private int xDistanz;
	
	private Circle spieler;
	
    private int schritte;
    private int nullSchritte;
    
    private Kachel anfangsKachel;
    private Kachel zielKachel;
	
	
	public DerBeweger(BoardView gui, BallEbene2 ballEbene, CluedoPlayer player){
		this.gui = gui;
		this.ballEbene = ballEbene;
		this.player = player;
		anfangsKachel = gui.getKachelArray()[player.getPosition().getY()][player.getPosition().getX()];

//		if(anfangsKachel.isIstTuer()){
//			ausRaumBewegen();
//		}
		
		spieler = ballEbene.getSpieler();
	}
	
	public void bewegen(Orientation [] anweisungenEingabe, int schritteEingabe, int nullSchritteEingabe){
		
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

		momentaneAnweisung = wieVieleAnweisungen + nullSchritte - schritte;
		
		if (schritte>0){
			
			jetzigeSpalte = player.getPosition().getX();
			jetzigeReihe = player.getPosition().getY();
			
			distanzBerechnen();
			
			
			if (jetzigeReihe+yDistanz != 26 && jetzigeSpalte + xDistanz != 25 && jetzigeReihe+yDistanz >= 0 && jetzigeSpalte + xDistanz >= 0) 
			{
				zielKachel = gui.getKachelArray()[jetzigeReihe+yDistanz][jetzigeSpalte+xDistanz];
			}
			
			
			Path path = new Path();
			path.getElements().add(new MoveTo(anfangsKachel.getLayoutX(), anfangsKachel.getLayoutY()));

			path.getElements().add(new LineTo(zielKachel.getLayoutX(), zielKachel.getLayoutY()));

			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(Math.abs(yDistanz) * 300 + Math.abs(xDistanz)
					 * 300));
			pathTransition.setNode(spieler);
			pathTransition.setPath(path);
			pathTransition.play();
			pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					anfangsKachel = zielKachel;
					schritte--;
					if((schritte == 0 && anfangsKachel.isIstTuer()) || (schritte != 0 && nullSchritte != 0 && anfangsKachel.isIstTuer())) {
						anfangsTuerKachel = (TuerKachel) gui.getKachelArray()[anfangsKachel.getyKoordinate()][anfangsKachel.getxKoordinate()];
						RaumBeweger rB = new RaumBeweger(gui, player, anfangsTuerKachel);
						Rooms room = rB.checkRaum(anfangsTuerKachel);
						raumZielKachel = rB.positionInRaum(player, room);
						inRaumBewegen();
					}
					bewegen(anweisungen, schritte, nullSchritte);
				}
			});
			
			positionUpdaten();
			
			}
		}

		public void anfangsKachelSetzen(Kachel neueAnfangsKachel){
			
			anfangsKachel = neueAnfangsKachel;
			auxx.logsevere("anfangs Kachel x : " +anfangsKachel.getxKoordinate() + " ||  y : " +anfangsKachel.getyKoordinate());
			player.getPosition().setX(anfangsKachel.getxKoordinate());
			player.getPosition().setY(anfangsKachel.getyKoordinate());
		}
	
		public void positionUpdaten(){
			player.getPosition().setY(player.getPosition().getY() + yDistanz);
			player.getPosition().setX(player.getPosition().getX() + xDistanz);
		}
		
		public void distanzBerechnen(){
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
			
		}
		
		public void anfangsPositionSetzen(){

			auxx.logsevere("hamana");
			
			startKachel = gui.getKachelArray()[player.getPosition().getY()][player.getPosition().getX()];
			
			Path path = new Path();
			path.getElements().add(new MoveTo(0,0));
			path.getElements().add(new LineTo(gui.getKachelArray()[player.getPosition().getY()][player.getPosition().getX()].getLayoutX(), gui.getKachelArray()[player.getPosition().getY()][player.getPosition().getX()].getLayoutY()));

			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(Math.abs(player.getPosition().getY()) * 500 + Math.abs(player.getPosition().getX()) * 500));
			pathTransition.setNode(spieler);
			pathTransition.setPath(path);
			pathTransition.play();
			
		}
		
		
		/**
		 * animiert die Bewegung zu dem Platz des Spielers im Raum
		 */
		public void inRaumBewegen(){
			
			
			raumAnfangsKachel = gui.getKachelArray()[player.getPosition().getY()][player.getPosition().getX()];
			
			System.out.println(" test " + startKachel.getLayoutX());
			
			System.out.println("layout y : " + gui.getKachelArray()[player.getPosition().getY()][player.getPosition().getX()].getLayoutX() +"  layout x : " +gui.getKachelArray()[player.getPosition().getY()][player.getPosition().getX()].getLayoutY());
			
			Path path = new Path();
			path.getElements().add(new MoveTo(raumAnfangsKachel.getLayoutX(),raumAnfangsKachel.getLayoutY()));
			path.getElements().add(new LineTo(raumZielKachel.getLayoutX(), raumZielKachel.getLayoutY()));

			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(2000));
			pathTransition.setNode(spieler);
			pathTransition.setPath(path);
			pathTransition.play();

		}
		
		/**
		public void ausRaumBewegen(){
			
			Path path = new Path();
			path.getElements().add(new MoveTo(gui.getKachelArray()[player.getPosition().getY()][player.getPosition().getX()].getLayoutX(), gui.getKachelArray()[player.getPosition().getY()][player.getPosition().getX()].getLayoutY()));
			path.getElements().add(new LineTo(anfangsKachel.getLayoutX(), anfangsKachel.getLayoutY()));

			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(2000));
			pathTransition.setNode(spieler);
			pathTransition.setPath(path);
			pathTransition.play();
		}
		*/
		/**
		 * Wandelt char [] mit anweisungen in Orientation [] mit anweisungen um
		 * @param anweisungen umzuwandeldes char []
		 * @return umgewandeltes Orientation []
		 */
		public Orientation [] charToOrientation(char [] anweisungen){
			
			Orientation [] anweisungenOrientationsVerarbeitet = new Orientation [12];
			
				for (int counterInnen = 0; counterInnen < anweisungen.length; counterInnen++){
					if (anweisungen[counterInnen] == 'S'){
						anweisungenOrientationsVerarbeitet[counterInnen] = Orientation.S;
					}
					
					if (anweisungen[counterInnen] == 'E'){
						anweisungenOrientationsVerarbeitet[counterInnen] = Orientation.O;
					}
					
					if (anweisungen[counterInnen] == 'N'){
						anweisungenOrientationsVerarbeitet[counterInnen] = Orientation.N;
					}
					
					if (anweisungen[counterInnen] == 'W'){
						anweisungenOrientationsVerarbeitet[counterInnen] = Orientation.W;
					}
					
					if (anweisungen[counterInnen] == 'T'){
						anweisungenOrientationsVerarbeitet[counterInnen] = null;
					}
					  
				}
			
			return anweisungenOrientationsVerarbeitet;
		}

		
	}

