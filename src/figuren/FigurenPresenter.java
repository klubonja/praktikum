package figuren;

import javafx.scene.shape.Circle;
import view.BoardView;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.util.Duration;
import kacheln.Kachel;
import model.Player;



public class FigurenPresenter {

	private BoardView spielfeld;

	private Player player;
	private int rowSize = 25;
	private int columnSize = 24;

	private Color [] farben = new Color [30];
	private int farbenzaehler;
	
	private int xDistanz;
	private int yDistanz;
	private int xErlaubt;
	private int yErlaubt;

	private int jetzigeReihe;
	private int jetzigeColumn;
	private Kachel jetzigesFeld;

	private Circle playerDarstellung;

	private int xPositionFuerPath;
	private int yPositionFuerPath;
	private int xStreckeFuerPath;
	private int yStreckeFuerPath;

	private int xRichtung;
	private int yRichtung;
	private int xZiel;
	private int yZiel;

	private String textbuffer;
	private Background backgroundbuffer, backgroundbuffer2;;
	private Font fontbuffer;

	private int ausweichen;
	private boolean aussenrum;

	private int first;
	private int second;
	private int augenzahl;

	public FigurenPresenter(BoardView spielfeld, Circle playerDarstellung){
		this.player = player;
		this.spielfeld = spielfeld;
		this.playerDarstellung = playerDarstellung;
		anfangsPositionSetzen();
		
	}
	
	


		/**
		 * Hier werden die Anfangspositionen der Spieler gesetzt.
		 */
		public void anfangsPositionSetzen() {
			if (player.getFirstName()=="Player1"){
				moveWithPath(0, 5);	
				System.out.println("Du bist Spieler 1");
			}
			if (player.getFirstName()=="Player2"){
				moveWithPath(0, 18);
				System.out.println("Du bist Spieler 2");
			}
			if (player.getFirstName()=="Player3"){
				moveWithPath(9, 24);
				System.out.println("Du bist Spieler 3");
			}
			if (player.getFirstName()=="Player4"){
				moveWithPath(14, 24);
				System.out.println("Du bist Spieler 4");
			}
			if (player.getFirstName()=="Player5"){
				moveWithPath(23, 7);
				System.out.println("Du bist Spieler 5");
			}
			if (player.getFirstName()=="Player6"){
				moveWithPath(16, 0);
				System.out.println("Du bist Spieler 6");
			}
			
			
			
		}


		/**
		 * Wird von der Kachel.OnClick aufgerufen und l�ｿｽst die movePlayer Methode
		 * aus.
		 */
		public void dasIstEinFeld(Kachel ziel) {
			farbenzaehler = 0;
			movePlayer(ziel);
		}

		/**
		 * Wird ausgel�ｿｽst, wenn jemand auf einen Raum clickt und gibt eine nette
		 * Nachricht zur�ｿｽck.
		 */
		public void dasIsEinRaum() {
			System.out.println("Das ist ein Raum, alter");
		}

		/**
		 * Wird ausgel�ｿｽst, wenn jemand auf einen Raum clickt und
		 * l�ｿｽst den Vermutungs-Screen aus.
		 */
		public void dasIstEineTuer(){
		}

		
		/**
		 * Die Methode, welche durch das ClickEvent ausgel�ｿｽst wird. hier wird
		 * pathfinder und moveWithPath ausgel�ｿｽst.
		 * 
		 * @param PlayerDarstellung
		 *            Der zu bewegende Player
		 * @param ziel
		 *            Die Zielkachel
		 */
		public void movePlayer(Kachel ziel) {
			System.out.println("----------------------");
			System.out.println("move whatever");

			//clearDice(view.getKachelArray()[player.getyCoord()][player.getxCoord()]);
			//persil2(view.getKachelArray()[player.getyCoord()][player.getxCoord()]);
			//dice(ziel);
			reset(ziel);

			System.out.println("vorher : x Distanz   " + xDistanz);
			System.out.println("vorher : y Distanz   " + yDistanz);

			while ((xDistanz != 0) && (yDistanz != 0)) {
				newPathfinder(xDistanz, yDistanz, jetzigesFeld);
				// newPathfinder(xDistanz, yDistanz, jetzigesFeld);
			}

			while ((xDistanz != 0) ^ (yDistanz != 0)) {
				newPathfinder(xDistanz, yDistanz, jetzigesFeld);
			}
			/*
			 * while( (xDistanz != 0) && (yDistanz != 0) ){ if (xDistanz > 0 ||
			 * xDistanz < 0){ pathfinder... movewithpath } ...y }
			 */

			System.out.println("nachher : x Distanz   " + xDistanz);
			System.out.println("nachher : y Distanz   " + yDistanz);

			// Animation
			moveWithPath(this.xErlaubt, this.yErlaubt);

			System.out.println("Reihe : "
					+ (spielfeld.getRowIndex(this.jetzigesFeld) + 1));
			System.out.println("Spalte : "
					+ (spielfeld.getColumnIndex(this.jetzigesFeld) + 1));

			System.out.println("x Player : " + player.getxCoord() + "| x Ziel : "
					+ xZiel);
			System.out.println("y Player : " + player.getyCoord() + "| y Ziel : "
					+ yZiel);

			if (player.getxCoord() == xZiel && player.getyCoord() == yZiel) {
				System.out.println("Sie haben ihr Ziel erreicht");

			}

			if ((player.getxCoord() != xZiel) || (player.getyCoord() != yZiel)) {
				System.out.println("recursion");

				movePlayer(spielfeld.getKachelArray()[yZiel][xZiel]);
			}


		}

		/**
		 * Setzt die Werte wieder zur�ｿｽck.
		 * 
		 * @param PlayerDarstellung
		 * @param ziel
		 */
		public void reset(Kachel ziel) {
			System.out.println("reset whatever");
			this.yDistanz = spielfeld.getRowIndex(ziel) - player.getyCoord();
			this.xDistanz = spielfeld.getColumnIndex(ziel) - player.getxCoord();
			this.xRichtung = xDistanz;
			this.yRichtung = yDistanz;
			this.xZiel = spielfeld.getColumnIndex(ziel);
			this.yZiel = spielfeld.getRowIndex(ziel) + ausweichen;
			this.jetzigesFeld = spielfeld.getKachelArray()[player.getyCoord()][player
					.getxCoord()];
			this.xErlaubt = 0;
			this.yErlaubt = 0;
			this.ausweichen = 0;

		}

		/**
		 * Eine Blaupause f�ｿｽr den Neuen Pathfinder
		 * @param xDistanzeingegeben
		 * @param yDistanzeingegeben
		 * @param jetzigesFeld
		 */
		public void newPathfinder(int xDistanzeingegeben, int yDistanzeingegeben,
				Kachel jetzigesFeld) {
			
			
			this.xDistanz = xDistanzeingegeben;
			this.yDistanz = yDistanzeingegeben;
			refresh();
			
			while (xDistanz != 0){
				pathfinderX(jetzigesFeld);
			}
			
			while (yDistanz != 0){
				pathfinderY(jetzigesFeld);
			}
			
			/*
			if (jetzigesFeld != view.getKachelArray()[yZiel][xZiel]
					&& ( (moveErlaubtX() == false) || (moveErlaubtY() == false) ) ){
				dodge();
			}
			*/
			
			
			
			
			// animationX();
			//pathfinderY(jetzigesFeld);
			// animationY();
		}
		
		/**
		 * Der neue Pathfinder in X Richung
		 * @param jetzigesFeld
		 */
		public void pathfinderX(Kachel jetzigesFeld) {
			refresh();
			if (moveErlaubtX()) {
				if (xDistanz > 0) {
					animationX(1);
					updateXDistanz(-1);
				}
				if (xDistanz < 0) {
					animationX(-1);
					updateXDistanz(1);
				}
			}
			
			else {
				xDistanz = 0;
			}
		}

		/**
		 * Der neue Pathfinder in Y Richtung
		 * @param jetzigesFeld
		 */
		public void pathfinderY(Kachel jetzigesFeld) {
			refresh();
			if (moveErlaubtY()) {
				if (yDistanz > 0) {
					animationY(1);
					updateYDistanz(-1);
				}
				if (yDistanz < 0) {
					animationY(-1);
					updateYDistanz(1);
				}
			}
			
			else {
				yDistanz = 0;
			}
			
		}

		/**
		 * Aktiviert die Animation in X Richtung
		 * @param richtungX
		 */
		public void animationX(int richtungX) {
			moveWithPath(richtungX, 0);
			updateCurrentField(0, richtungX);
		}

		/**
		 * Aktiviert die Animation in Y Richtung
		 * @param richtungY
		 */
		public void animationY(int richtungY) {
			moveWithPath(0, richtungY);
			updateCurrentField(richtungY, 0);
		}

		/**
		 * Erh�ｿｽht / Verringert die yDistanz
		 * @param yChange Wie viel die yDistanz ver�ｿｽndert wird.
		 */
		public void updateYDistanz(int yChange){
			yDistanz = yDistanz + yChange;
		}
		
		/**
		 * Erh�ｿｽht / Verringert die xDistanz
		 * @param xChange Wie viel die xDistanz ver�ｿｽndert wird.
		 */
		public void updateXDistanz(int xChange){
			xDistanz = xDistanz + xChange;
		}
		
		/**
		 * Berechnet die Erlaubte x und y Distanz bis zum Ziel
		 * 
		 * @param xDistanzeingegeben
		 *            Die �ｿｽbermittelte Distanz in X Richtung
		 * @param yDistanzeingegeben
		 *            Die �ｿｽbermittelte Distanz in Y Richtung
		 * @param jetzigesFeld
		 */
		public void pathfinder(int xDistanzeingegeben, int yDistanzeingegeben,
				Kachel jetzigesFeld) {

			System.out.println("find whatever");
			System.out.println("x Player : " + player.getxCoord() + "| x Ziel : "
					+ xZiel);
			System.out.println("y Player : " + player.getyCoord() + "| y Ziel : "
					+ yZiel);

			this.xDistanz = xDistanzeingegeben;
			this.yDistanz = yDistanzeingegeben;
			this.jetzigesFeld = jetzigesFeld;
			if (yDistanz != 0 && xDistanz != 0) {
				while (yDistanz > 0 || (yDistanz == 0 && xDistanz != 0)) {
					while (xDistanz > 0) {
						refresh();
						if (moveErlaubtX()) {
							raiseErlaubtX();
							updateCurrentField(0, 1);

						}
						xDistanz--;
					}
					while (xDistanz < 0) {
						refresh();
						if (moveErlaubtX()) {
							raiseErlaubtX();
							updateCurrentField(0, -1);

						}
						xDistanz++;
					}

					refresh();
					if (moveErlaubtY()) {
						raiseErlaubtY();
						updateCurrentField(1, 0);

					}
					yDistanz--;
				}
				while (yDistanz < 0) {
					while (xDistanz > 0) {
						refresh();
						if (moveErlaubtX()) {
							raiseErlaubtX();
							updateCurrentField(0, 1);

						}
						xDistanz--;
					}
					while (xDistanz < 0) {
						refresh();
						if (moveErlaubtX()) {
							raiseErlaubtX();
							updateCurrentField(0, -1);

						}
						xDistanz++;
					}
					refresh();
					if (moveErlaubtY()) {
						raiseErlaubtY();
						updateCurrentField(-1, 0);

					}
					yDistanz++;
				}
				// moveWithPath(playerDarstellung, this.xErlaubt, this.yErlaubt);
			} else {

				while (yDistanz == 0 && xDistanz < 0) {
					refresh();
					if (moveErlaubtX()) {
						raiseErlaubtX();
						updateCurrentField(0, -1);

					}
					xDistanz++;
				}

				while (yDistanz == 0 && xDistanz > 0) {
					refresh();
					if (moveErlaubtX()) {
						raiseErlaubtX();
						updateCurrentField(0, 1);

					} else {
						dodge();
					}
					xDistanz--;
				}

				while (yDistanz > 0 && xDistanz == 0) {
					refresh();
					if (moveErlaubtY()) {
						raiseErlaubtY();
						updateCurrentField(1, 0);

					}
					yDistanz--;
				}

				while (yDistanz < 0 && xDistanz == 0) {
					refresh();
					if (moveErlaubtY()) {
						raiseErlaubtY();
						updateCurrentField(-1, 0);

					}
					yDistanz++;
				}
				
			}
			
		}

		/**
		 * setzt die jetzigeReihe / jetzigeSpalte auf den aktuellen Wert.
		 */
		public void refresh() {
			this.jetzigeReihe = spielfeld.getRowIndex(this.jetzigesFeld);
			this.jetzigeColumn = spielfeld.getColumnIndex(this.jetzigesFeld);

		}

		/**
		 * Inkrementiert xErlaubt um 1
		 */
		public void raiseErlaubtX() {
			this.xErlaubt = xErlaubt + 1;
		}

		/**
		 * Inkrementiert yErlaubt um 1
		 */
		public void raiseErlaubtY() {
			this.yErlaubt = yErlaubt + 1;
		}

		/**
		 * Updatet das jetzigeFeld um den jeweiligen Wert
		 * 
		 * @param y  Der Wert um welchen die Reihe erh�ｿｽht wird
		 * @param x  Der Wert um welchen die Spalte erh�ｿｽht wird
		 */
		public void updateCurrentField(int y, int x) {
			this.jetzigesFeld = spielfeld.getKachelArray()[this.jetzigeReihe + y][this.jetzigeColumn + x];
		}

		/**
		 * Hier wird �ｿｽberpr�ｿｽft, ob die n�ｿｽchstgelegene Kachel in X Richtung ein Raum
		 * ist.
		 * 
		 * @return true falls dort keine Raumkachel ist. false falls dort eine
		 *         Raumkachel ist.
		 */
		public boolean moveErlaubtX() {
			refresh();
			if (xDistanz > 0) {
				if (spielfeld.getKachelArray()[jetzigeReihe][jetzigeColumn + 1]
						.isIstRaum() == false) {
					System.out.println("x check true");
					return true;
				} else {
					System.out.println("x check false");
					System.out.println("wurde blockiert bei X : " +player.getxCoord()+ "  Y : " +player.getyCoord());
					return false;
				}
			} else {
				if (spielfeld.getKachelArray()[jetzigeReihe][jetzigeColumn - 1]
						.isIstRaum() == false) {
					System.out.println("x check true");
					return true;

				} else {
					System.out.println("x check false");
					System.out.println("wurde blockiert bei X : " +player.getxCoord()+ "  Y : " +player.getyCoord());
					return false;
				}
			}
		}

		/**	
		 * Hier wird �ｿｽberpr�ｿｽft, ob die n�ｿｽchstgelegene Kachel in Y Richtung ein Raum
		 * ist.
		 * 
		 * @return true falls dort keine Raumkachel ist. false falls dort eine
		 *         Raumkachel ist.
		 */
		public boolean moveErlaubtY() {
			refresh();
			if (yDistanz > 0) {
				if (spielfeld.getKachelArray()[jetzigeReihe + 1][jetzigeColumn]
						.isIstRaum() == false) {

					System.out.println("y check true");

					return true;
				} else {
					System.out.println("y check false");
					System.out.println("wurde blockiert bei X : " +player.getxCoord()+ "  Y : " +player.getyCoord());
					return false;
				}
			} else {
				if (spielfeld.getKachelArray()[jetzigeReihe - 1][jetzigeColumn]
						.isIstRaum() == false) {
					System.out.println("y check true");
					return true;
				} else {
					System.out.println("y check false");
					System.out.println("wurde blockiert bei X : " +player.getxCoord()+ "  Y : " +player.getyCoord());
					return false;
				}
			}
		}

		/**
		 * Eine Blaupause zum Ausweichen
		 */
		public void dodge() {
			
			System.out.println("dodge whatever");
			
			if (moveErlaubtX()==false && yDistanz == 0){
				
				yDistanz = 1;	
				if (moveErlaubtY()==true){
					moveWithPath(0, 1);
					yDistanz = 0;
				}
				
				else {
					yDistanz = -1;
					if (moveErlaubtY()==true){
						moveWithPath(0, -1);
					}
					yDistanz = 0;
				}
				
				
			}
			
			if (moveErlaubtY()==false && xDistanz == 0){
				
				xDistanz = 1;			
				if (moveErlaubtX()==true){
					moveWithPath(1, 0);
					xDistanz = 0;
				}
				
				else {
					xDistanz = -1;
					if (moveErlaubtX()==true){
						moveWithPath(-1, 0);
					}
					xDistanz = 0;
				}
				
			}
		}


		/**
		 * Hier findet die Animation statt mithilfe von Paths.
		 * 
		 * @param PlayerDarstellung
		 *            Die zu bewegende Node
		 * @param xStrecke
		 *            Die Strecke in X Richtung
		 * @param yStrecke
		 *            Die Strecke in Y Richtung
		 */
		public void moveWithPath(int xStrecke, int yStrecke) {

			System.out.println("path whatever");
			this.xStreckeFuerPath = xStrecke;
			this.yStreckeFuerPath = yStrecke;

	/*
			if (xRichtung < 0) {
				this.xStreckeFuerPath = -this.xStreckeFuerPath;
			}
			if (yRichtung < 0) {
				this.yStreckeFuerPath = -this.yStreckeFuerPath;
			}
	*/
			this.xPositionFuerPath = player.getxCoord();
			this.yPositionFuerPath = player.getyCoord();
			// this.xPositionFuerPath = 0;
			// this.yPositionFuerPath = 0;

			System.out.println(" player x vorher : " + player.getxCoord());
			System.out.println(" player y vorher : " + player.getyCoord());
			System.out.println(" x Position f�ｿｽr path : " + xPositionFuerPath);
			System.out.println(" y Position f�ｿｽr path : " + yPositionFuerPath);

			System.out.println(" playerDarstellung X vorher : "
					+ playerDarstellung.getLayoutX());
			System.out.println(" playerDarstellung Y vorher : "
					+ playerDarstellung.getLayoutY());

			Kachel anfangsKachel = spielfeld.getKachelArray()[yPositionFuerPath][xPositionFuerPath];
			Kachel zielKachel = spielfeld.getKachelArray()[yPositionFuerPath
					+ yStreckeFuerPath][xPositionFuerPath + xStreckeFuerPath];

			System.out.println("anfangs layout X : " + anfangsKachel.getLayoutX());
			System.out.println("anfangs layout Y : " + anfangsKachel.getLayoutY());
			System.out.println("ziel layout X : " + zielKachel.getLayoutX());
			System.out.println("ziel layout Y : " + zielKachel.getLayoutY());

			Path path = new Path();
			path.getElements().add(
					new MoveTo(anfangsKachel.getLayoutX(), anfangsKachel
							.getLayoutY()));

			path.getElements()
					.add(new LineTo(zielKachel.getLayoutX(), anfangsKachel
							.getLayoutY()));
			path.getElements().add(
					new LineTo(zielKachel.getLayoutX(), zielKachel.getLayoutY()));

			
			
			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(1));
			//pathTransition.setDuration(Duration.millis(xStrecke * 500 + yStrecke* 500));
			pathTransition.setNode(playerDarstellung);
			pathTransition.setPath(path);
			// pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
			pathTransition.play();

			//Timeline timeline = new Timeline();
			//KeyValue keyValue = new KeyValue(null, null);
			//timeline.getKeyFrames().add(pathTransition);
			
			System.out.println(" playerDarstellung X nachher : "
					+ playerDarstellung.getLayoutX());
			System.out.println(" playerDarstellung Y nachher : "
					+ playerDarstellung.getLayoutY());

			player.setxCoord(player.getxCoord() + xStreckeFuerPath);
			player.setyCoord(player.getyCoord() + yStreckeFuerPath);

			spielfeld.getKachelArray()[player.getyCoord()][player.getxCoord()].setBackgroundColor(spielfeld.getKachelArray()[player.getyCoord()][player.getxCoord()], Color.GREENYELLOW);
			
			System.out.println(" player x nachher  : " + player.getxCoord());
			System.out.println(" player y nachher : " + player.getyCoord());

		}	
}
