package finderOfPaths;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import kacheln.Kachel;
import kacheln.KachelContainer;
import staticClasses.auxx;
import view.AussergewohnlichesZugfenster;
import view.BoardView;
import cluedoNetworkLayer.CluedoPosition;
import enums.Orientation;
import enums.Rooms;

/**
 * @version 25.06.2015
 * @author Benedikt Mayer
 *
 *         Führt die Animation bzw. Bewegung der Figur aus.
 */
public class DerBeweger {

	private BoardView gui;
	private BallEbene2 ballEbene;
	private RaumBeweger raumBeweger;
	private KrasserStack stack;
	private AussergewohnlichesZugfenster zug;

	private int zaehler;

	private Kachel geheimGangKachel;
	private Label geheimGangLabel;

	private Orientation[] anweisungen;
	private Orientation[] anweisungenVonHier;
	private int momentaneAnweisung;
	private int wieVieleAnweisungen;

	private int jetzigeSpalte;
	private int jetzigeReihe;

	private Kachel raumZielKachel;
	private Label  raumZielLabel;
	private Kachel raumAnfangsKachel;
	private Label raumAnfangsLabel;
	private Kachel raumStartKachel;
	private Label raumStartLabel;

	private Kachel gangKachel;
	private Label gangLabel;

	private Kachel tuerZielKachel;
	private Label tuerZielLabel;
	private Kachel anfangsRaumKachel;
	private Label anfangsRaumLabel;
	private Rooms room;
	private Rooms zielRaum;

	private Kachel startKachel;
	private Label startLabel;

	private int yDistanz;
	private int xDistanz;

    private int schritte;
    private int nullSchritte;
    
    private Kachel anfangsKachel;
    private Label anfangsLabel;
    private Kachel zielKachel;
    private Label zielLabel;
    
    public PlayerCircleManager pcManager;
    private KachelContainer kachelContainer;
    
	public DerBeweger(KrasserStack stack, AussergewohnlichesZugfenster zug, BoardView gui, BallEbene2 ballEbene, RaumBeweger raumBeweger,PlayerCircleManager pcm, KachelContainer kachelContainer){
		pcManager = pcm;
		this.kachelContainer = kachelContainer;
		this.gui = gui;
		this.ballEbene = ballEbene;
		this.stack = stack;
		this.zug = zug;
		this.raumBeweger = raumBeweger;
		
		anfangsKachel = kachelContainer.getKacheln()[pcManager.getCurrentPlayer().getPosition().getY()][pcManager.getCurrentPlayer().getPosition().getX()];
		anfangsLabel = gui.getLabelArray()[pcManager.getCurrentPlayer().getPosition().getY()][pcManager.getCurrentPlayer().getPosition().getX()];
	}
	
	public void bewegen(Orientation [] anweisungenEingabe, int schritteEingabe, int nullSchritteEingabe){
		
		this.schritte = schritteEingabe;		
		this.nullSchritte = nullSchritteEingabe;		
		this.anweisungen = anweisungenEingabe;
		
		if(anfangsKachel.isIstTuer()){
			anfangsRaumKachel = kachelContainer.getKacheln()[anfangsKachel
			         								.getPosition().getY()][anfangsKachel
			         								.getPosition().getX()];
			anfangsRaumLabel = gui.getLabelHier(anfangsRaumKachel);
			Rooms room = raumBeweger.checkRaum(anfangsRaumKachel);
			raumStartKachel = raumBeweger.positionInRaum(this.pcManager.getCurrentPlayer(), room);
			raumStartLabel = gui.getLabelHier(raumStartKachel);
			ausRaumBewegen();
		}
		else{
			bewegenOhneRaum(anweisungen, schritte, nullSchritte);
		}
	}
	
	public void bewegenOhneRaum(Orientation[] anweisungenEingabe, int schritteEingabe,
			int nullSchritteEingabe) {

		this.schritte = schritteEingabe;
		this.nullSchritte = nullSchritteEingabe;
		this.anweisungen = anweisungenEingabe;

		wieVieleAnweisungen = 0;
		for (int i = 0; i < anweisungen.length; i++) {
			if (anweisungen[i] != null) {
				wieVieleAnweisungen++;
			}
		}

		momentaneAnweisung = wieVieleAnweisungen + nullSchritte - schritte;

		if (schritte > 0) {

			jetzigeSpalte = pcManager.getCurrentPlayer().getPosition().getX();
			jetzigeReihe = pcManager.getCurrentPlayer().getPosition().getY();

			distanzBerechnen();

			if (jetzigeReihe + yDistanz != 26 && jetzigeSpalte + xDistanz != 25
					&& jetzigeReihe + yDistanz >= 0
					&& jetzigeSpalte + xDistanz >= 0) {
				zielKachel = kachelContainer.getKacheln()[jetzigeReihe + yDistanz][jetzigeSpalte
						+ xDistanz];
				zielLabel = gui.getLabelHier(zielKachel);
			}

			Path path = new Path();
			path.getElements().add(
					new MoveTo(anfangsLabel.getLayoutX(), anfangsLabel
							.getLayoutY()));

			path.getElements()
					.add(new LineTo(zielLabel.getLayoutX(), zielLabel
							.getLayoutY()));

			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(Math.abs(yDistanz) * 300
					+ Math.abs(xDistanz) * 300));
			pathTransition.setNode(pcManager.getCurrentCircle());
			pathTransition.setPath(path);
			pathTransition.play();
			pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					anfangsKachel = zielKachel;
					anfangsLabel = gui.getLabelHier(anfangsKachel);
					schritte--;
					if ((schritte == 0 && anfangsKachel.isIstTuer())
							|| (schritte != 0 && nullSchritte != 0 && anfangsKachel
									.isIstTuer())) {
						anfangsRaumKachel = kachelContainer.getKacheln()[anfangsKachel
								.getPosition().getY()][anfangsKachel
								.getPosition().getX()];
						anfangsRaumLabel = gui.getLabelHier(anfangsRaumKachel);
						Rooms room = raumBeweger.checkRaum(anfangsRaumKachel);
						System.out.println("RIGHT HERE BOSS");
						zug.setZimmer(room.getName());
						raumZielKachel = raumBeweger.positionInRaum(
								pcManager.getCurrentPlayer(), room);
						raumZielLabel = gui.getLabelHier(raumZielKachel);
						inRaumBewegen();
						stack.getChildren()
						.add(zug);
				zug.toFront();
					}

					bewegenOhneRaum(anweisungen, schritte, nullSchritte);

				}
			});

			positionUpdaten();

		}

	}

	public void anfangsKachelSetzen(Kachel neueAnfangsKachel) {

		anfangsKachel = neueAnfangsKachel;
		anfangsLabel = gui.getLabelHier(anfangsKachel);
		auxx.logsevere("anfangs Kachel x : "
				+ anfangsKachel.getPosition().getX() + " ||  y : "
				+ anfangsKachel.getPosition().getY());
		pcManager.getCurrentPlayer().getPosition().setX(anfangsKachel.getPosition().getX());
		pcManager.getCurrentPlayer().getPosition().setY(anfangsKachel.getPosition().getY());
	}

	public void positionUpdaten() {
		pcManager.getCurrentPlayer().getPosition().setY(
				pcManager.getCurrentPlayer().getPosition().getY() + yDistanz);
		pcManager.getCurrentPlayer().getPosition().setX(
				pcManager.getCurrentPlayer().getPosition().getX() + xDistanz);
	}

	public void distanzBerechnen() {
		if (anweisungen[momentaneAnweisung] == Orientation.S) {
			yDistanz = 1;
			xDistanz = 0;
		}

		else if (anweisungen[momentaneAnweisung] == Orientation.O) {
			xDistanz = 1;
			yDistanz = 0;
		}

		else if (anweisungen[momentaneAnweisung] == Orientation.N) {
			yDistanz = -1;
			xDistanz = 0;
		}

		else if (anweisungen[momentaneAnweisung] == Orientation.W) {
			xDistanz = -1;
			yDistanz = 0;
		}

		else {
			xDistanz = 0;
			yDistanz = 0;
		}

	}

	public void anfangsPositionSetzen(int iEingabe) {
		zaehler = iEingabe;
		int size = pcManager.getSize();
		if (zaehler < size){
		
			auxx.logsevere("anfangspositionen");
			
			
			
			startKachel = kachelContainer.getKacheln()[pcManager.getCurrentPlayer().getPosition().getY()][pcManager.getCurrentPlayer().getPosition().getX()];
			startLabel = gui.getLabelHier(startKachel);
			
			Path path = new Path();
			path.getElements().add(
					new MoveTo(gui.getLabelArray()[11][12].getLayoutX(), gui
							.getLabelArray()[11][12].getLayoutY()));
			path.getElements().add(
					new LineTo(gui.getLabelArray()[pcManager.getCurrentPlayer().getPosition()
							.getY()][pcManager.getCurrentPlayer().getPosition().getX()]
							.getLayoutX(), gui.getLabelArray()[pcManager.getCurrentPlayer()
							.getPosition().getY()][pcManager.getCurrentPlayer().getPosition()
							.getX()].getLayoutY()));

			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(1000));
			pathTransition.setNode(pcManager.getCurrentCircle());
			pathTransition.setPath(path);
			pathTransition.play();
			
				
				pathTransition.setOnFinished(new EventHandler <ActionEvent> (){

					@Override
					public void handle(ActionEvent event) {
						pcManager.next();
						zaehler++;
						anfangsPositionSetzen(zaehler);
					}
					
				});
			}

	}

	/**
	 * animiert die Bewegung zu dem Platz des Spielers im Raum
	 */
	public void inRaumBewegen() {

		raumAnfangsKachel = kachelContainer.getKacheln()[pcManager.getCurrentPlayer().getPosition()
				.getY()][pcManager.getCurrentPlayer().getPosition().getX()];
		raumAnfangsLabel = gui.getLabelHier(raumAnfangsKachel);
		
		
		System.out.println(" test " + startLabel.getLayoutX());

		System.out
				.println("layout y : "
						+ gui.getLabelArray()[pcManager.getCurrentPlayer().getPosition()
								.getY()][pcManager.getCurrentPlayer().getPosition().getX()]
								.getLayoutX()
						+ "  layout x : "
						+ gui.getLabelArray()[pcManager.getCurrentPlayer().getPosition()
								.getY()][pcManager.getCurrentPlayer().getPosition().getX()]
								.getLayoutY());

		Path path = new Path();
		path.getElements().add(
				new MoveTo(raumAnfangsLabel.getLayoutX(), raumAnfangsLabel
						.getLayoutY()));
		path.getElements().add(
				new LineTo(raumZielLabel.getLayoutX(), raumZielLabel
						.getLayoutY()));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(2000));
		pathTransition.setNode(pcManager.getCurrentCircle());
		pathTransition.setPath(path);
		pathTransition.play();
	}

	
	public void ausRaumBewegen(){
		
		
		
		Path path = new Path();

		path.getElements().add(
				new MoveTo(raumStartLabel.getLayoutX(), raumStartLabel
						.getLayoutY()));
		path.getElements().add(
				new LineTo(anfangsLabel.getLayoutX(), anfangsLabel
						.getLayoutY()));
		
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(2000));
		pathTransition.setNode(pcManager.getCurrentCircle());
		pathTransition.setPath(path);
		pathTransition.play();
		pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				bewegenOhneRaum(anweisungen, schritte, nullSchritte);
			}
		});
	}
	/**
	 * Wandelt char [] mit anweisungen in Orientation [] mit anweisungen um
	 * 
	 * @param anweisungen
	 *            umzuwandeldes char []
	 * @return umgewandeltes Orientation []
	 */
	public Orientation[] charToOrientation(char[] anweisungen) {

		Orientation[] anweisungenOrientationsVerarbeitet = new Orientation[12];

		for (int counterInnen = 0; counterInnen < anweisungen.length; counterInnen++) {
			if (anweisungen[counterInnen] == 'S') {
				anweisungenOrientationsVerarbeitet[counterInnen] = Orientation.S;
			}

			if (anweisungen[counterInnen] == 'E') {
				anweisungenOrientationsVerarbeitet[counterInnen] = Orientation.O;
			}

			if (anweisungen[counterInnen] == 'N') {
				anweisungenOrientationsVerarbeitet[counterInnen] = Orientation.N;
			}

			if (anweisungen[counterInnen] == 'W') {
				anweisungenOrientationsVerarbeitet[counterInnen] = Orientation.W;
			}

			if (anweisungen[counterInnen] == 'T') {
				anweisungenOrientationsVerarbeitet[counterInnen] = null;
			}
		}

		return anweisungenOrientationsVerarbeitet;
	}

	public void useSecretPassage(PlayerCircleManager pcManager) {

		this.pcManager = pcManager;
		auxx.logsevere("PAAAAAAAAAAANIIIIIIIIIIIK");
		System.out.println(pcManager.getCurrentPlayer().getPosition().getX());
		System.out.println(pcManager.getCurrentPlayer().getPosition().getY());
		CluedoPosition position = pcManager.getCurrentPlayer().getPosition();
		if (position.getX() == 6 && position.getY() == 3) {
			auxx.logsevere("study");
			geheimgangBewegerEingang(Rooms.study);
		} else if (position.getX() == 19 && position.getY() == 18) {
			auxx.logsevere("kitchen");
			geheimgangBewegerEingang(Rooms.kitchen);
		} else if (position.getX() == 17 && position.getY() == 5) {
			auxx.logsevere("lounge");
			geheimgangBewegerEingang(Rooms.lounge);
		} else if (position.getX() == 4 && position.getY() == 19) {
			auxx.logsevere("conservatory");
			geheimgangBewegerEingang(Rooms.conservatory);
		}
	}

	public void geheimgangBewegerEingang(Rooms raum) {

		gangKachel = geheimGangKachel(raum, "anfang");
		gangLabel = gui.getLabelHier(gangKachel);
		
		Path path = new Path();
		path.getElements().add(
				new MoveTo(raumZielLabel.getLayoutX(), raumZielLabel
						.getLayoutY()));
		path.getElements().add(
				new LineTo(gangLabel.getLayoutX(), gangLabel.getLayoutY()));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(2000));
		pathTransition.setNode(pcManager.getCurrentCircle());
		pathTransition.setPath(path);
		pathTransition.play();
		pathTransition.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				anfangsRaumKachel = kachelContainer.getKacheln()[pcManager.getCurrentPlayer()
						.getPosition().getY()][pcManager.getCurrentPlayer().getPosition()
						.getX()];
				anfangsRaumLabel = gui.getLabelHier(anfangsRaumKachel);
				Rooms room = raumBeweger.checkRaum(anfangsRaumKachel);
				raumZielKachel = raumBeweger.positionInRaum(pcManager.getCurrentPlayer(), room);
				raumZielLabel = gui.getLabelHier(raumZielKachel);
				geheimgangBewegerAusgang(raumZielKachel, raum);

				stack.getChildren()
				.add(zug);
		zug.toFront();
			}

		});
	}

	public void geheimgangBewegerAusgang(Kachel raumZielKachelEingabe, Rooms raum) {

		gangKachel = geheimGangKachel(raum, "ziel");
		gangLabel = gui.getLabelHier(gangKachel);
		raumZielKachel = raumZielKachelEingabe;
		raumZielLabel = gui.getLabelHier(raumZielKachel);
		
		Path path = new Path();
		path.getElements().add(
				new MoveTo(gangLabel.getLayoutX(), gangLabel.getLayoutY()));
		path.getElements().add(
				new LineTo(raumZielLabel.getLayoutX(), raumZielLabel
						.getLayoutY()));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(2000));
		pathTransition.setNode(pcManager.getCurrentCircle());
		pathTransition.setPath(path);
		pathTransition.play();
	}

	public Kachel geheimGangKachel(Rooms raum, String vonWo) {

		if (vonWo == "anfang") {
			if (raum == Rooms.study) {
				geheimGangKachel = kachelContainer.getKacheln()[0][0];
				geheimGangLabel = gui.getLabelHier(geheimGangKachel);
				pcManager.getCurrentPlayer().getPosition().setX(19);
				pcManager.getCurrentPlayer().getPosition().setY(18);
			} else if (raum == Rooms.kitchen) {
				geheimGangKachel = kachelContainer.getKacheln()[24][23];
				pcManager.getCurrentPlayer().getPosition().setX(6);
				pcManager.getCurrentPlayer().getPosition().setY(3);
			} else if (raum == Rooms.lounge) {
				geheimGangKachel = kachelContainer.getKacheln()[0][23];
				pcManager.getCurrentPlayer().getPosition().setX(4);
				pcManager.getCurrentPlayer().getPosition().setY(19);
			} else if (raum == Rooms.conservatory) {
				geheimGangKachel = kachelContainer.getKacheln()[24][0];
				pcManager.getCurrentPlayer().getPosition().setX(17);
				pcManager.getCurrentPlayer().getPosition().setY(5);
			}
		} else {
			if (raum == Rooms.study) {
				geheimGangKachel = kachelContainer.getKacheln()[24][23];
			} else if (raum == Rooms.kitchen) {
				geheimGangKachel = kachelContainer.getKacheln()[0][0];
			} else if (raum == Rooms.lounge) {
				geheimGangKachel = kachelContainer.getKacheln()[24][0];
			} else if (raum == Rooms.conservatory) {
				geheimGangKachel = kachelContainer.getKacheln()[0][23];
			}
		}
		geheimGangLabel = gui.getLabelHier(geheimGangKachel);
		return geheimGangKachel;
		
	}

}
