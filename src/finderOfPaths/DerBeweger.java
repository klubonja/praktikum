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
	
	private int zaehler;
	
	private CluedoPlayer currentPlayer;
	
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
	
	private Circle currentCircle;
	
    private int schritte;
    private int nullSchritte;
    
    private Kachel anfangsKachel;
    private Kachel zielKachel;
    
	public DerBeweger(BoardView gui, BallEbene2 ballEbene){
		this.gui = gui;
		this.ballEbene = ballEbene;
		
		this.currentPlayer = (CluedoPlayer) GanzTolleSpielerliste.playerManager.get(0);
		
		anfangsKachel = gui.getKachelArray()[currentPlayer.getPosition().getY()][currentPlayer.getPosition().getX()];
		currentCircle = (Circle) GanzTolleSpielerliste.circleManager.get(0);
		
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
			
			jetzigeSpalte = currentPlayer.getPosition().getX();
			jetzigeReihe = currentPlayer.getPosition().getY();
			
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
			pathTransition.setNode(currentCircle);
			pathTransition.setPath(path);
			pathTransition.play();
			pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					anfangsKachel = zielKachel;
					schritte--;
					if((schritte == 0 && anfangsKachel.isIstTuer()) || (schritte != 0 && nullSchritte != 0 && anfangsKachel.isIstTuer())) {
						anfangsTuerKachel = (TuerKachel) gui.getKachelArray()[anfangsKachel.getPosition().getY()][anfangsKachel.getPosition().getX()];
						RaumBeweger rB = new RaumBeweger(gui, currentPlayer, anfangsTuerKachel);
						Rooms room = rB.checkRaum(anfangsTuerKachel);
						raumZielKachel = rB.positionInRaum(currentPlayer, room);
						inRaumBewegen();
						
					}
					
					
					bewegen(anweisungen, schritte, nullSchritte);
					
					//currentCircle = (Circle) circleManager.getCurrentObject();
					//currentPlayer = (CluedoPlayer) playerManager.getCurrentObject();
				}
			});
			
			positionUpdaten();
			
			}
			
		//currentCircle = (Circle) circleManager.getCurrentObject();
		//currentPlayer = (CluedoPlayer) playerManager.getCurrentObject();
		
		}

	public void anfangsKachelSetzen(Kachel neueAnfangsKachel){
		
		anfangsKachel = neueAnfangsKachel;
		auxx.logsevere("anfangs Kachel x : " +anfangsKachel.getPosition().getX() + " ||  y : " +anfangsKachel.getPosition().getY());
		currentPlayer.getPosition().setX(anfangsKachel.getPosition().getX());
		currentPlayer.getPosition().setY(anfangsKachel.getPosition().getY());
	}
	
	public void positionUpdaten(){
		currentPlayer.getPosition().setY(currentPlayer.getPosition().getY() + yDistanz);
		currentPlayer.getPosition().setX(currentPlayer.getPosition().getX() + xDistanz);
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
	
	public void anfangsPositionSetzen(int iEingabe){
		zaehler = iEingabe;
		
		if (zaehler < GanzTolleSpielerliste.playerManager.size()){
		
			auxx.logsevere("hamana");
			
			startKachel = gui.getKachelArray()[currentPlayer.getPosition().getY()][currentPlayer.getPosition().getX()];
			
			currentPlayer = (CluedoPlayer) GanzTolleSpielerliste.playerManager.get(zaehler);
			currentCircle = (Circle) GanzTolleSpielerliste.circleManager.get(zaehler);
			
			
			Path path = new Path();
			path.getElements().add(new MoveTo(gui.getKachelArray()[11][12].getLayoutX(),gui.getKachelArray()[11][12].getLayoutY()));
			path.getElements().add(new LineTo(gui.getKachelArray()[currentPlayer.getPosition().getY()][currentPlayer.getPosition().getX()].getLayoutX(), gui.getKachelArray()[currentPlayer.getPosition().getY()][currentPlayer.getPosition().getX()].getLayoutY()));

			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(1000));
			pathTransition.setNode(currentCircle);
			pathTransition.setPath(path);
			pathTransition.play();
			
				
				pathTransition.setOnFinished(new EventHandler <ActionEvent> (){

					@Override
					public void handle(ActionEvent event) {
						zaehler++;
						anfangsPositionSetzen(zaehler);
					}
					
				});
			}
			else {
				
				currentPlayer = (CluedoPlayer) GanzTolleSpielerliste.playerManager.getCurrentObject();
				currentCircle = (Circle) GanzTolleSpielerliste.circleManager.getCurrentObject();
				GanzTolleSpielerliste.playerManager.next();
				GanzTolleSpielerliste.circleManager.next();
			}
				
		}
		
		
	/**
	 * animiert die Bewegung zu dem Platz des Spielers im Raum
	 */
	public void inRaumBewegen(){
		
		
		raumAnfangsKachel = gui.getKachelArray()[currentPlayer.getPosition().getY()][currentPlayer.getPosition().getX()];
		
		System.out.println(" test " + startKachel.getLayoutX());
		
		System.out.println("layout y : " + gui.getKachelArray()[currentPlayer.getPosition().getY()][currentPlayer.getPosition().getX()].getLayoutX() +"  layout x : " +gui.getKachelArray()[currentPlayer.getPosition().getY()][currentPlayer.getPosition().getX()].getLayoutY());
		
		Path path = new Path();
		path.getElements().add(new MoveTo(raumAnfangsKachel.getLayoutX(),raumAnfangsKachel.getLayoutY()));
		path.getElements().add(new LineTo(raumZielKachel.getLayoutX(), raumZielKachel.getLayoutY()));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(2000));
		pathTransition.setNode(currentCircle);
		pathTransition.setPath(path);
		pathTransition.play();
	}
		
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

	public void useSecretPassage(){
	///////////////////////////////
	////////BRAUCHT PERSONEN///////
	///////////////////////////////
	}
		
	public CluedoPlayer getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(CluedoPlayer currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Circle getCurrentCircle() {
		return currentCircle;
	}

	public void setCurrentCircle(Circle circle) {
		this.currentCircle = circle;
	}
		
	
	
}
