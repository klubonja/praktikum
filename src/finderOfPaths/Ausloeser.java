package finderOfPaths;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import kacheln.Kachel;
import kacheln.KachelContainer;
import kommunikation.PlayerCircleManager;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import view.AussergewohnlichesZugfenster;
import view.spielfeld.BallEbene;
import view.spielfeld.BoardView;
import animation.DerBeweger;
import cluedoNetworkLayer.CluedoField;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPosition;
import enums.Orientation;

/**
 * Hier werden die MouseEvents der ballEbene ausgeloest und verwaltet.
 * ---------------------- || HIER WIRD AUCH DIE BEWEGUNG AUSGELOEST!!!! || --------------------------------
 * 
 * @since 13.06.2015
 * @version 21.07.2015
 * @author Benedikt Mayer, Maximilian Lammel
 *
 */
public class Ausloeser {

	private BoardView gui;
	private BallEbene ballEbene;
	private AussergewohnlichesZugfenster zug;
	private DerBeweger beweger;
	private char[] moeglichkeiten;
	private Orientation[] anweisungenOrientations = new Orientation[12];
	private Orientation[] anweisungenOrientationsVonHier = new Orientation[12];
	private WahnsinnigTollerPathfinder pathfinder;

	private int nullSchritte;
	private int schritte;

	private int xInsgesamt;
	private int yInsgesamt;

	public PlayerCircleManager pcManager;
	private int gameid;
	private CluedoGameClient network;

	private KachelContainer kachelContainer;
	/**
	 * Konstruktor fuer den Ausloeser, welcher ballEbenen-clicks mit Bewegungen verlinkt.
	 * 
	 * @param kachelContainer enthaelt die ganzen tollen Kacheln
	 * @param gui um aufs KachelArray zuzugreifen
	 * @param beweger um den Spieler/Ball zu bewegen
	 * @param ballEbene um auf die click-events zugreifen zu koennen
	 * @param pathfinder um den Weg berechnen zu lassen
	 */
	public Ausloeser(AussergewohnlichesZugfenster zug, BoardView gui, DerBeweger beweger, BallEbene ballEbene,
			WahnsinnigTollerPathfinder pathfinder,PlayerCircleManager pcManager, int gameid, CluedoGameClient network, KachelContainer kachelContainer) {
		this.kachelContainer = kachelContainer;
		this.gui = gui;
		this.gameid = gameid;
		this.network = network;
		this.ballEbene = ballEbene;
		this.zug = zug;
		this.beweger = beweger;
		this.pathfinder = pathfinder;
		this.pcManager = pcManager;
	}
	
	/**
	 * Der Server will auch Ausloeser haben
	 * @param kachelContainer enthaelt die ganzen tollen Kacheln 
	 * @param gameid damit wir auch wissen welches Spiel hier abgeht.
	 * @param pathfinder finder of most amazing paths
	 * @param pcManager weils so schoen ist.
	 */
	public Ausloeser(KachelContainer kachelContainer, int gameid, WahnsinnigTollerPathfinder pathfinder, PlayerCircleManager pcManager){
		this.kachelContainer = kachelContainer;
		this.gameid = gameid;
		this.pathfinder = pathfinder;
		this.pcManager = pcManager;
	}

	/**
	 * nimmt clicks aus der ballEbene und weist ihnen hier eine Methode zu.
	 */
	public void zuweisung(PlayerCircleManager pcManager) {
		this.pcManager = pcManager;
		ballEbene.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				click(event);
			}
		});
	}


	/**
	 * Wird ausgeloest, wenn irgendwo in der ballEbene geclickt wird und schickt dann Anfragen an den Server
	 * Wenn er dort nicht hindarf ist er ganz traurig.
	 */
	public void click(MouseEvent event) {
		CluedoPosition pos = new CluedoPosition((int) event.getX(), (int) event.getY());
		suggestMoveToServer(pos);
	}
	
	public void suggestMoveToServer(CluedoPosition pos) {

		for (int iReihen = 0; iReihen < gui.getLabelArray().length - 1; iReihen++) {
			for (int jSpalten = 0; jSpalten < gui.getLabelArray()[iReihen].length - 1; jSpalten++) {
				if ((gui.getLabelArray()[iReihen][jSpalten].getLayoutX() <= pos
						.getX())
						&& (pos.getX() < gui.getLabelArray()[iReihen][jSpalten]
								.getLayoutX() + 29)
						&& ((gui.getLabelArray()[iReihen][jSpalten]
								.getLayoutY() <= pos.getY()) && (pos
								.getY() < gui.getLabelArray()[iReihen][jSpalten]
								.getLayoutY() + 29))) {
					CluedoPosition aufServerWarten = new CluedoPosition(jSpalten, iReihen);
					auxx.loginfo("positionen im ausloeser y : " +iReihen +" || x : " +jSpalten);
					network.sendMsgToServer(NetworkMessages.moveMsg(gameid, new CluedoField(aufServerWarten)));
				}
			}
		}
		
	}

	/**
	 * PAM! Hier geht's ab! Hier wird DerBeweger ausgeloest und alles fuer die Bewegungs-Animation in Gang gesetzt
	 * @param yKoordinate hier solls hin (y)
	 * @param xKoordinate hier solls hin (x)
	 * @param person Die Person welche abgeht
	 * @param pcManager der pcManager falls er noch nicht da ist. Sicher ist sicher.
	 */
	public void ausloesen(int yKoordinate, int xKoordinate, String person, PlayerCircleManager pcManager) {
		this.pcManager = pcManager;
		try {

			Kachel momentaneKachel = kachelContainer.getKacheln()[yKoordinate][xKoordinate];

			char[] moeglichkeitenHierher = momentaneKachel.getMoeglichkeitenHierher();

			Kachel startKachel = momentaneKachel.getVonHier();
			startKachel.setVonHier(null);

			auxx.logsevere("anfangs Kachel laut Auslöser x : "
					+ startKachel.getPosition().getX() + "  ||  y : "
					+ startKachel.getPosition().getY());

			resetAnweisungen();
			anweisungenOrientations = charToOrientation(moeglichkeitenHierher);
			schritte = wieVieleSchritte(moeglichkeitenHierher);

			insgesamteDistanz();

			for (int i = 0; i < anweisungenOrientations.length
					&& anweisungenOrientations[i] != null; i++) {
				System.out.println("anweisungen : "
						+ anweisungenOrientations[i]);
			}

			beweger.anfangsKachelSetzen(startKachel);

			beweger.bewegen(startKachel, anweisungenOrientations, schritte, nullSchritte);

			nullSchritte = 0;
			pathfinder.setWelcheKachel(0);

		} catch (NullPointerException e) {
		}

	}

	/**
	 * Wandelt char [] mit anweisungen in Orientation [] mit anweisungen um
	 * 
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
				nullSchritte++;
				anweisungenOrientationsVerarbeitet[counterInnen] = null;
			}

		}

		return anweisungenOrientationsVerarbeitet;
	}

	/**
	 * berechnetdie Anzahl der zu gehenden Schritte in jegliche Richtung
	 * @param moeglichkeitenEingabe das auszuwertende moeglichkeiten-Array
	 * @return wie viele Schritte zu gehen sind
	 */
	public int wieVieleSchritte(char[] moeglichkeitenEingabe) {
		this.moeglichkeiten = moeglichkeitenEingabe;
		int counter = 0;
		for (int i = 0; i < moeglichkeiten.length; i++) {
			if (moeglichkeiten[i] != ' ') {
				counter++;
			}
		}
		return counter;
	}

	
	/**
	 * Berechnet xInsgesamt und yInsgesamt basierend auf den anweisungenOrientations
	 */
	public void insgesamteDistanz() {

		xInsgesamt = 0;
		yInsgesamt = 0;

		for (int i = 0; i < anweisungenOrientations.length; i++) {
			if (anweisungenOrientations[i] == Orientation.W) {
				xInsgesamt--;
			}

			else if (anweisungenOrientations[i] == Orientation.O) {
				xInsgesamt++;
			}

			else if (anweisungenOrientations[i] == Orientation.N) {
				yInsgesamt--;
			}

			else if (anweisungenOrientations[i] == Orientation.S) {
				yInsgesamt++;
			}
		}
	}

	/**
	 * Setzt die anweisungenOrientationsVonHier[ganzTolleZahl] wieder alle auf null
	 */
	public void resetAnweisungen() {

		for (int i = 0; i < anweisungenOrientationsVonHier.length; i++) {
			anweisungenOrientationsVonHier[i] = null;
		}

		for (int i = 0; i < anweisungenOrientations.length; i++) {
			anweisungenOrientations[i] = null;
		}
	}

}
