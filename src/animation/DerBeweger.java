package animation;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import kacheln.Kachel;
import kacheln.KachelContainer;
import kommunikation.PlayerCircleManager;
import staticClasses.auxx;
import view.AussergewohnlichesZugfenster;
import view.spielfeld.BoardView;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;
import enums.Orientation;
import enums.Persons;
import enums.Rooms;

/**
 * @version 21.07.2015
 * @author Benedikt Mayer, Maximilian Lammel
 *
 *         Fuehrt die Animation bzw. Bewegung der Figur aus.
 */
public class DerBeweger {

	private BoardView boardView;
	private RaumBeweger raumBeweger;
	private AussergewohnlichesZugfenster zugFenster;

	private int zaehler;

	private Orientation[] anweisungen;
	private int momentaneAnweisung;
	private int wieVieleAnweisungen;

	private int jetzigeSpalte;
	private int jetzigeReihe;

	private int yDistanz;
	private int xDistanz;

    private int schritte;
    private int nullSchritte;
    
    private Kachel spielerPositionKachel;
    private Label spielerPositionLabel;
    private Kachel zielKachel;
    private Label zielLabel;
    
    public PlayerCircleManager pcManager;
    private KachelContainer kachelContainer;
    
    /**
     * Konstruktor für den Beweger bzw. Animierer
     * @param zugFenster
     * @param boardView
     * @param ballEbene
     * @param raumBeweger
     * @param pcManager
     * @param kachelContainer
     */
	public DerBeweger(AussergewohnlichesZugfenster zugFenster, BoardView boardView, RaumBeweger raumBeweger,PlayerCircleManager pcManager, KachelContainer kachelContainer){
		this.pcManager = pcManager;
		this.kachelContainer = kachelContainer;
		this.boardView = boardView;
		this.zugFenster = zugFenster;
		this.raumBeweger = raumBeweger;
		
		spielerPositionKachel = kachelContainer.getKacheln()[pcManager.getCurrentPlayer().getPosition().getY()][pcManager.getCurrentPlayer().getPosition().getX()];
		spielerPositionLabel = boardView.getLabelArray()[pcManager.getCurrentPlayer().getPosition().getY()][pcManager.getCurrentPlayer().getPosition().getX()];
	}
	
	/**
	 * Die grundlegende Animations-start-methode
	 * @param anweisungenEingabe die Anweisungen, welche animiert werden sollen
	 * @param schritteEingabe die Anzahl der tatsaechlichen auszufuehrenden Schritte
	 * @param nullSchritteEingabe die Anzahl der Nullschritte
	 */
	public void bewegen(Kachel spielerPositionKachel, Orientation [] anweisungenEingabe, int schritteEingabe, int nullschritteEingabe){
		
		if(spielerPositionKachel.isIstTuer()){
			Kachel anfangsRaumKachel = kachelContainer.getKacheln()[spielerPositionKachel.getPosition().getY()][spielerPositionKachel.getPosition().getX()];
			Label anfangsRaumLabel = boardView.getLabelHier(anfangsRaumKachel);
			Rooms room = raumBeweger.checkRaum(anfangsRaumKachel);
			Kachel raumStartKachel = raumBeweger.positionInRaum(pcManager.getCurrentPlayer(), room);
			Label raumStartLabel = boardView.getLabelHier(raumStartKachel);
			ausRaumBewegen(spielerPositionKachel, raumStartLabel, anfangsRaumLabel, anweisungenEingabe, schritteEingabe, nullschritteEingabe);
		}
		else{
			bewegenOhneRaum(anweisungenEingabe, schritteEingabe, nullschritteEingabe);
		}
	}
	
	/**
	 * Initiiert die Bewegung / Animation durch den Geheimgang
	 * @param pcManager falls er noch nicht uebergeben wurde aus irgend einem Grund
	 */
	public void useSecretPassage(PlayerCircleManager pcManager) {

		this.pcManager = pcManager;
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

	//////////////////////////////// Animation innerhalb des Normalen Felds //////////////////////////////////
	
	public void getCarriedAlong(Rooms zielRaum, CluedoPlayer player){

		System.out.println("get carried??!?!?!?!??!?!?");
		
		Kachel spielerPositionKachel = kachelContainer.getKachelAt(player.getPosition().getY(), player.getPosition().getX());		
		
		Rooms anfangsRaum = spielerPositionKachel.getRaum();
		
		Label spielerAnfangsPositionLabel = new Label();
		
		if (kachelContainer.getKachelAt(player.getPosition().getY(), player.getPosition().getX()).isIstRaum()){
			Kachel spielerAnfangsPositionKachel = raumBeweger.positionInRaum(player, anfangsRaum);
			spielerAnfangsPositionLabel = boardView.getLabelHier(spielerAnfangsPositionKachel);
		
		}
		else {
			spielerAnfangsPositionLabel = boardView.getLabelHier(spielerPositionKachel);
		}
		
		Kachel spielerPositionImRaumKachel = raumBeweger.positonInDerTuer(zielRaum);
		
		Kachel spielerZielPositionImRaumKachel = raumBeweger.positionInRaum(player, zielRaum);
		Label spielerZielPositionImRaumLabel = boardView.getLabelHier(spielerZielPositionImRaumKachel);
		
		Persons person = player.getCluedoPerson();
		
		pcManager.getPlayerByPerson(player.getCluedoPerson()).setNewPosition(spielerPositionImRaumKachel.getPosition());
		
//		player.setNewPosition(spielerPositionImRaumKachel.getPosition());
		
		Path path = new Path();
		path.getElements().add(
				new MoveTo(spielerAnfangsPositionLabel.getLayoutX(), spielerAnfangsPositionLabel
						.getLayoutY()));
		path.getElements().add(
				new LineTo(spielerZielPositionImRaumLabel.getLayoutX(), spielerZielPositionImRaumLabel
						.getLayoutY()));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(2000));
		pathTransition.setNode(pcManager.getCharacterByPerson(person.getColor()));
		pathTransition.setPath(path);
		pathTransition.play();

	}
	
	
	/**
	 * Bewegt den momentanen Spieler über die FeldKacheln
	 * @param anweisungenEingabe die Anweisungen, welche übergeben werden und animiert werden sollen
	 * @param schritteEingabe die Anzahl der zu animierenden Schritte
	 * @param nullSchritteEingabe die Anzahl der nicht-zu-animierenden Schritte
	 */
	public void bewegenOhneRaum(Orientation[] anweisungenEingabe, int schritteEingabe,int nullSchritteEingabe) {

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
				zielLabel = boardView.getLabelHier(zielKachel);
			}

			Path path = new Path();
			
			path.getElements().add(new MoveTo(spielerPositionLabel.getLayoutX(), spielerPositionLabel.getLayoutY()));
			path.getElements().add(new LineTo(zielLabel.getLayoutX(), zielLabel.getLayoutY()));

			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(Math.abs(yDistanz) * 300	+ Math.abs(xDistanz) * 300));
			pathTransition.setNode(pcManager.getCurrentCharacter());
			pathTransition.setPath(path);
			pathTransition.play();
			pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					spielerPositionKachel = zielKachel;
					spielerPositionLabel = boardView.getLabelHier(spielerPositionKachel);
					schritte--;
					if ((schritte == 0 && spielerPositionKachel.isIstTuer()) || (schritte != 0 && nullSchritte != 0 && spielerPositionKachel.isIstTuer())) {
						Kachel anfangsRaumKachel = kachelContainer.getKacheln()[spielerPositionKachel.getPosition().getY()][spielerPositionKachel.getPosition().getX()];
						Label anfangsRaumLabel = boardView.getLabelHier(anfangsRaumKachel);
						Rooms room = raumBeweger.checkRaum(anfangsRaumKachel);
						zugFenster.setZimmer(room.getName());
						Kachel raumZielKachel = raumBeweger.positionInRaum(pcManager.getCurrentPlayer(), room);
						Label raumZielLabel = boardView.getLabelHier(raumZielKachel);
						inRaumBewegen(raumZielLabel);
						
					}

					bewegenOhneRaum(anweisungen, schritte, nullSchritte);

				}
			});

			positionUpdaten(xDistanz, yDistanz);

		}

	}

		
	/**
	 * animiert die Bewegung zu dem Platz des Spielers im Raum
	 * @param raumZielLabel hier solls hin.
	 */
	public void inRaumBewegen(Label raumZielLabel) {
		pcManager.getCurrentCharacter().setImage(
				new Image("media/gameObjects/" +
						pcManager.getCurrentPlayer().getCluedoPerson().getColor() +
						"_s.png"));
		
		Kachel raumAnfangsKachel = kachelContainer.getKacheln()[pcManager.getCurrentPlayer().getPosition().getY()][pcManager.getCurrentPlayer().getPosition().getX()];
		Label raumAnfangsLabel = boardView.getLabelHier(raumAnfangsKachel);
		
		auxx.logsevere("in Raum Beweger denkt das Person : " +pcManager.getCurrentPlayer().getCluedoPerson().getColor() +" ist \n"
				+ "an der Position y : " +pcManager.getCurrentPlayer().getPosition().getY() +"  x : " +pcManager.getCurrentPlayer().getPosition().getX());
		
		Path path = new Path();
		path.getElements().add(
				new MoveTo(raumAnfangsLabel.getLayoutX(), raumAnfangsLabel
						.getLayoutY()));
		path.getElements().add(
				new LineTo(raumZielLabel.getLayoutX(), raumZielLabel
						.getLayoutY()));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(2000));
		pathTransition.setNode(pcManager.getCurrentCharacter());
		pathTransition.setPath(path);
		pathTransition.play();
	}

	/**
	 * Hier wird sich aus dem Raum raus animiert
	 * @param raumStartLabel Hier geht's los
	 * @param anfangsLabel Hier geht's hin
	 */
	public void ausRaumBewegen(Kachel spielerPositionKachel, Label raumStartLabel, Label anfangsLabel, Orientation [] anweisungen, int schritte, int nullschritte){
		pcManager.getCurrentCharacter().setImage(
				new Image("media/gameObjects/" +
						pcManager.getCurrentPlayer().getCluedoPerson().getColor() +
						"_s.png"));
		
		anfangsKachelSetzen(spielerPositionKachel);
		
		Path path = new Path();

		path.getElements().add(
				new MoveTo(raumStartLabel.getLayoutX(), raumStartLabel
						.getLayoutY()));
		path.getElements().add(
				new LineTo(anfangsLabel.getLayoutX(), anfangsLabel
						.getLayoutY()));
		
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(2000));
		pathTransition.setNode(pcManager.getCurrentCharacter());
		pathTransition.setPath(path);
		pathTransition.play();
		pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				bewegenOhneRaum(anweisungen, schritte, nullschritte);
			}
		});
	}
	
	//////////////////////////////// Animation von Geheimgaengen //////////////////////////////////	
	
	/**
	 * Der erste Teil der Animation durch den Geheimgang
	 * @param anfangsRaum Der Raum von welchem man startet
	 */
	public void geheimgangBewegerEingang(Rooms anfangsRaum) {
		pcManager.getCurrentCharacter().setImage(
				new Image("media/gameObjects/" +
						pcManager.getCurrentPlayer().getCluedoPerson().getColor() +
						"_s.png"));

		System.out.println("Der currentPlayer ist bei y : " +pcManager.getCurrentPlayer().getPosition().getY() +"  ||  x : " +pcManager.getCurrentPlayer().getPosition().getX());
		
		System.out.println("anfangsRaum : " +anfangsRaum);
		
		Kachel gangKachel1 = kachelContainer.getKacheln()[pcManager.getCurrentPlayer().getPosition().getY()][pcManager.getCurrentPlayer().getPosition().getX()];
		Label gangLabel1 = boardView.getLabelHier(gangKachel1);
		
		Kachel gangKachel1_1 = raumBeweger.positionInRaum(pcManager.getCurrentPlayer(), anfangsRaum);
		Label gangLabel1_1 = boardView.getLabelHier(gangKachel1_1);
		
		System.out.println("gangKachel1 ist bei y : " +gangKachel1.getPosition().getY() +"  ||  x : " +gangKachel1.getPosition().getX());
		
		System.out.println("gangKachel1_1 ist bei y : " +gangKachel1_1.getPosition().getY() +"  ||  x : " +gangKachel1_1.getPosition().getX());
		
		Kachel gangKachel2 = geheimGangKachel(anfangsRaum, "anfang");
		Label gangLabel2 = boardView.getLabelHier(gangKachel2);
		
		System.out.println("gangKachel2 ist bei y : " +gangKachel2.getPosition().getY() +"  ||  x : " +gangKachel2.getPosition().getX());
		
		Path path = new Path();
		path.getElements().add(
				new MoveTo(gangLabel1_1.getLayoutX(), gangLabel1_1.getLayoutY()));
		path.getElements().add(
				new LineTo(gangLabel2.getLayoutX(), gangLabel2.getLayoutY()));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(2000));
		pathTransition.setNode(pcManager.getCurrentCharacter());
		pathTransition.setPath(path);
		pathTransition.play();
		pathTransition.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Kachel gangKachel2_1 = kachelContainer.getKacheln()[pcManager.getCurrentPlayer()
						.getPosition().getY()][pcManager.getCurrentPlayer().getPosition()
						.getX()];
				Label gangLabel2_1 = boardView.getLabelHier(gangKachel2_1);
				System.out.println("gangKachel2_1 ist bei y : " +gangKachel2_1.getPosition().getY() +"  ||  x : " +gangKachel2_1.getPosition().getX());
				Rooms room2 = raumBeweger.checkRaum(gangKachel2);
				Rooms room2_1 = raumBeweger.checkRaum(gangKachel2_1);
				System.out.println("room2 : " +room2);
				System.out.println("room2_1 : " +room2_1);
				Kachel gangKachel3 = raumBeweger.positionInRaum(pcManager.getCurrentPlayer(), room2_1);
				Label gangLabel3 = boardView.getLabelHier(gangKachel3);
				System.out.println("gangKachel3 ist bei y : " +gangKachel3.getPosition().getY() +"  ||  x : " +gangKachel3.getPosition().getX());
				geheimgangBewegerAusgang(gangKachel3, anfangsRaum);
				zugFenster.setZimmer(room2.getName());
//				stack.getChildren()
//				.add(zug);
//				zug.toFront();
			}

		});
	}

	/**
	 * Der zweite Teil der Animation durch den Geheimgang
	 * @param raumZielKachelEingabe die Kachel, zu welcher man will
	 * @param raum der Raum zu welchem man will
	 */
	public void geheimgangBewegerAusgang(Kachel raumZielKachelEingabe, Rooms raum) {
		pcManager.getCurrentCharacter().setImage(
				new Image("media/gameObjects/" +
						pcManager.getCurrentPlayer().getCluedoPerson().getColor() +
						"_s.png"));
		
		Kachel gangKachel4 = geheimGangKachel(raum, "ziel");
		Label gangLabel4 = boardView.getLabelHier(gangKachel4);
		
		System.out.println("gangKachel4 ist bei y : " +gangKachel4.getPosition().getY() +"  ||  x : " +gangKachel4.getPosition().getX());
		
		Kachel gangKachel5 = raumZielKachelEingabe;
		Label gangLabel5 = boardView.getLabelHier(gangKachel5);
		
		System.out.println("gangKachel5 ist bei y : " +gangKachel5.getPosition().getY() +"  ||  x : " +gangKachel5.getPosition().getX());
		
		Path path = new Path();
		path.getElements().add(
				new MoveTo(gangLabel4.getLayoutX(), gangLabel4.getLayoutY()));
		path.getElements().add(
				new LineTo(gangLabel5.getLayoutX(), gangLabel5
						.getLayoutY()));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(2000));
		pathTransition.setNode(pcManager.getCurrentCharacter());
		pathTransition.setPath(path);
		pathTransition.play();
	}

	/**
	 * ermittelt die Geheimgang-Kachel auf welche sich bewegt werden soll
	 * @param raum der Raum in welchem man jetzt ist 
	 * @param vonWo "anfang" falls man vom Raum zum Geheimgang geht. Sonst etwas anderes
	 * @return Die Kachel, wo man vom momentanen Ort auf dem Weg durch den Geheimgang hin will.
	 */
	public Kachel geheimGangKachel(Rooms raum, String vonWo) {

		Kachel geheimGangKachel = new Kachel();
		
		if (vonWo == "anfang") {
			if (raum == Rooms.study) {
				geheimGangKachel = kachelContainer.getKacheln()[0][0];
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
		return geheimGangKachel;
		
	}

	//////////////////////////////// Animation fuer die Anfangspositionen //////////////////////////////////
	
	/**
	 * Setzt und animiert die Positionen der Spieler zu Spielbeginn
	 * @param iEingabe
	 */
	public void anfangsPositionSetzen(int iEingabe) {
		zaehler = iEingabe;
		int size = pcManager.getSize();
		if (zaehler < size){
		
			auxx.logsevere("anfangspositionen");
			
			
			
			Kachel startKachel = kachelContainer.getKacheln()[pcManager.getCurrentPlayer().getPosition().getY()][pcManager.getCurrentPlayer().getPosition().getX()];
			Label startLabel = boardView.getLabelHier(startKachel);
			
			Path path = new Path();
			path.getElements().add(
					new MoveTo(boardView.getLabelArray()[11][12].getLayoutX(), boardView
							.getLabelArray()[11][12].getLayoutY()));
			path.getElements().add(
					new LineTo(boardView.getLabelArray()[pcManager.getCurrentPlayer().getPosition()
							.getY()][pcManager.getCurrentPlayer().getPosition().getX()]
							.getLayoutX(), boardView.getLabelArray()[pcManager.getCurrentPlayer()
							.getPosition().getY()][pcManager.getCurrentPlayer().getPosition()
							.getX()].getLayoutY()));

			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(1000));
			pathTransition.setNode(pcManager.getCurrentCharacter());
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

	//////////////////////////////// Misc. Berechnungen //////////////////////////////////
	
	/**
	 * Setzt die Kachel auf welcher sich der Spieler befindet auf den richtigen Ort
	 * @param neueAnfangsKachel die neue Kachel auf welcher sich der Spieler befindet
	 */
	public void anfangsKachelSetzen(Kachel neueAnfangsKachel) {

		spielerPositionKachel = neueAnfangsKachel;
		spielerPositionLabel = boardView.getLabelHier(spielerPositionKachel);
		auxx.logsevere("anfangs Kachel x : "
				+ spielerPositionKachel.getPosition().getX() + " ||  y : "
				+ spielerPositionKachel.getPosition().getY());
		pcManager.getCurrentPlayer().getPosition().setX(spielerPositionKachel.getPosition().getX());
		pcManager.getCurrentPlayer().getPosition().setY(spielerPositionKachel.getPosition().getY());
	}
	
	/**
	 * Setzt die Position des currentPlayer auf den aktuellen Wert 
	 * @param xDistanz die x-Distanz um welche der Spieler jetzt verschoben ist
	 * @param yDistanz die y-Distanz um welche der Spieler jetzt verschoben ist
	 */
	public void positionUpdaten(int xDistanz, int yDistanz) {
		pcManager.getCurrentPlayer().getPosition().setY(
				pcManager.getCurrentPlayer().getPosition().getY() + yDistanz);
		pcManager.getCurrentPlayer().getPosition().setX(
				pcManager.getCurrentPlayer().getPosition().getX() + xDistanz);
	}

	/**
	 * Berechnet die zu gehende Distanz und setzt die Werte xDistanz und yDistanz
	 */
	public void distanzBerechnen() {
		if (anweisungen[momentaneAnweisung] == Orientation.S) {
			yDistanz = 1;
			xDistanz = 0;
			pcManager.getCurrentCharacter().setImage(
					new Image("media/gameObjects/" +
							pcManager.getCurrentPlayer().getCluedoPerson().getColor() +
							"_s.png"));
		}

		else if (anweisungen[momentaneAnweisung] == Orientation.O) {
			xDistanz = 1;
			yDistanz = 0;
			pcManager.getCurrentCharacter().setImage(
					new Image("media/gameObjects/" +
							pcManager.getCurrentPlayer().getCluedoPerson().getColor() +
							"_o.png"));
		}

		else if (anweisungen[momentaneAnweisung] == Orientation.N) {
			yDistanz = -1;
			xDistanz = 0;
			pcManager.getCurrentCharacter().setImage(
					new Image("media/gameObjects/" +
							pcManager.getCurrentPlayer().getCluedoPerson().getColor() +
							"_n.png"));
		}

		else if (anweisungen[momentaneAnweisung] == Orientation.W) {
			xDistanz = -1;
			yDistanz = 0;
			pcManager.getCurrentCharacter().setImage(
					new Image("media/gameObjects/" +
							pcManager.getCurrentPlayer().getCluedoPerson().getColor() +
							"_w.png"));
		}

		else {
			xDistanz = 0;
			yDistanz = 0;
		}

	}

	/**
	 * Wandelt char [] mit anweisungen in Orientation [] mit anweisungen um	 * 
	 * @param anweisungen umzuwandeldes char []
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


	
}
