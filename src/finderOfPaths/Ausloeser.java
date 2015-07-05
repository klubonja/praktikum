package finderOfPaths;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import kacheln.Kachel;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import view.AussergewohnlichesZugfenster;
import view.BoardView;
import view.GameFrameView;
import cluedoNetworkLayer.CluedoField;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;
import enums.Orientation;

/**
 * Hier werden die MouseEvents der ballEbene ausgeloest und verwaltet.
 * ---------------------------------------------- || HIER WIRD AUCH DIE BEWEGUNG
 * AUSGELOEST!!!! || ----------------------------------------------
 * 
 * @since 13.06.2015
 * @version 25.06.2015
 * @author Benedikt Mayer, Maximilian Lammel
 *
 */
public class Ausloeser {

	private boolean gewuerfelt;

	private BoardView gui;
	private BallEbene2 ballEbene;
	private AussergewohnlichesZugfenster zug;
	private KrasserStack stack;
	private DerBeweger beweger;
	private char[] moeglichkeiten;
	private Orientation[] anweisungenOrientations = new Orientation[12];
	private Orientation[] anweisungenOrientationsVonHier = new Orientation[12];
	private WahnsinnigTollerPathfinder pathfinder;
	private CluedoPosition aufServerWarten;

	private int nullSchritte;

	private int schritte;

	private CluedoPlayer currentPlayer;

	private int xInsgesamt;
	private int yInsgesamt;

	private Sucher sucher;

	private int wuerfelZahl;

	public final PlayerCircleManager pcManager;
	private int gameid;
	private CluedoGameClient network;

	/**
	 * Konstruktor fuer den Ausloeser, welcher ballEbenen-clicks mit Bewegungen
	 * verlinkt.
	 * 
	 * @param gui
	 *            um aufs KachelArray zuzugreifen
	 * @param beweger
	 *            um den Spieler/Ball zu bewegen
	 * @param ballEbene
	 *            um auf die click-events zugreifen zu koennen
	 * @param pathfinder
	 *            um den Weg berechnen zu lassen
	 */
	public Ausloeser(KrasserStack stack, AussergewohnlichesZugfenster zug, BoardView gui, DerBeweger beweger, BallEbene2 ballEbene,
			WahnsinnigTollerPathfinder pathfinder, Sucher sucher,
			PlayerCircleManager pcm, int gameid, CluedoGameClient network) {
		this.gui = gui;
		this.gameid = gameid;
		this.network = network;
		this.ballEbene = ballEbene;
		this.zug = zug;
		this.stack = stack;
		this.beweger = beweger;
		this.pathfinder = pathfinder;
		this.sucher = sucher;
		pcManager = pcm;
	}

	/**
	 * nimmt clicks aus der ballEbene und weist ihnen hier eine Methode zu.
	 */
	public void zuweisung() {
		System.out.println("zuweisung");
		zug.YESgangImage.setOnMouseClicked(e -> {beweger
				.useSecretPassage();
	});
		ballEbene.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				click(event);
			}
		});
	}

	/**
	 * berechnetdie Anzahl der zu gehenden Schritte in jegliche Richtung
	 * 
	 * @param moeglichkeitenEingabe
	 *            das auszuwertende moeglichkeiten-Array
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
	 * Wird ausgeloest, wenn irgendwo in der ballEbene geclickt wird
	 */
	public void click(MouseEvent event) {

		if (gewuerfelt) {
			gewuerfelt = false;

			for (int iReihen = 0; iReihen < gui.getKachelArray().length - 1; iReihen++) {
				for (int jSpalten = 0; jSpalten < gui.getKachelArray()[iReihen].length - 1; jSpalten++) {
					if ((gui.getKachelArray()[iReihen][jSpalten].getLayoutX() <= event
							.getX())
							&& (event.getX() < gui.getKachelArray()[iReihen][jSpalten]
									.getLayoutX() + 29)
							&& ((gui.getKachelArray()[iReihen][jSpalten]
									.getLayoutY() <= event.getY()) && (event
									.getY() < gui.getKachelArray()[iReihen][jSpalten]
									.getLayoutY() + 29))) {
						aufServerWarten = new CluedoPosition(jSpalten, iReihen);
						auxx.loginfo("positionen im ausloeser y : " +iReihen +" || x : " +jSpalten);
						network.sendMsgToServer(NetworkMessages.moveMsg(gameid, new CluedoField(aufServerWarten)));
						
						//ausloesen(iReihen, jSpalten);

					}
				}
			}
		}
	}

	public void ausloesen(int iReihe, int jSpalte) {
		try {

			Kachel momentaneKachel = gui.getKachelArray()[iReihe][jSpalte];

			System.out.println("Reihe : " + iReihe + "  ||  Spalte : "
					+ jSpalte);

			char[] moeglichkeitenHierher = momentaneKachel
					.getMoeglichkeitenHierher();

			Kachel startKachel = momentaneKachel.getVonHier();
			startKachel.setVonHier(null);

			auxx.logsevere("anfangs Kachel laut Auslöser x : "
					+ gui.getColumnIndex(startKachel) + "  ||  y : "
					+ gui.getRowIndex(startKachel));

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

			beweger.bewegen(anweisungenOrientations, schritte, nullSchritte);

			nullSchritte = 0;
			pathfinder.setWelcheKachel(0);

			// HIER WIRD NEXT GEMACHT
			pcManager.next();
			if (gui.getKachelArray()[beweger.getCurrentPlayer().getPosition().getY()][beweger.getCurrentPlayer().getPosition().getX()].isIstRaum()){
				network.sendMsgToServer(NetworkMessages.end_turnMsg(gameid));
			}
		} catch (NullPointerException e) {
		}

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
				nullSchritte++;
				anweisungenOrientationsVerarbeitet[counterInnen] = null;
			}

		}

		return anweisungenOrientationsVerarbeitet;
	}

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

	public int getWuerfelZahl() {
		return wuerfelZahl;
	}

	public void setWuerfelZahl(int wuerfelZahl) {
		this.wuerfelZahl = wuerfelZahl;
	}

	public void resetAnweisungen() {

		for (int i = 0; i < anweisungenOrientationsVonHier.length; i++) {
			anweisungenOrientationsVonHier[i] = null;
		}

		for (int i = 0; i < anweisungenOrientations.length; i++) {
			anweisungenOrientations[i] = null;
		}
	}

	public boolean isGewuerfelt() {
		return gewuerfelt;
	}

	public void setGewuerfelt(boolean gewuerfelt) {
		this.gewuerfelt = gewuerfelt;
	}

}
