package gui1_0;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
<<<<<<< HEAD
import javafx.stage.Stage;


=======
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

>>>>>>> master
/**
 * @since 24.05.2015
 * @version 27.05.2015
 * @author Benedikt
 *
<<<<<<< HEAD
 * Hier wird die vorläufige GUI - eine GridPane - modelliert.
 * Sie ist 24 x 25 Felder Groß und hat ein labelArray, in welchem
 * alle Kacheln gespeichert sind, zuerst in Reihen, dann Spalten.
 */
public class TestGUI extends GridPane {
		
	private Kachel [][] labelArray;
	private Stage stage;
	private Scene sceneYo;
	private Circle testSpieler;
	private BackgroundFill fill;
	private Kachel kachelAnfang;
	private int rowSize;
	private int columnSize;
	private Spieler spieler;
	
	/**
	 * Der Konstruktor für die GUI. Hier wird die Größe festgelegt und das
	 * labelArray erzeugt.
	 * @param columnSize
	 * @param rowSize
	 */
	public TestGUI(int columnSize, int rowSize){
		 this.rowSize = rowSize ;
		 this.columnSize = columnSize ;
		labelArray = new Kachel[rowSize][columnSize];
	}	
	
	/**
	 * Hier wird die GUI gestartet als Stage mit vorläufiger Auflösung 600 x 600
	 * Die Methode setLayout() wird ausgelöst.
	 */
	public void start(){
	this.stage = new Stage();
	this.sceneYo = new Scene(this,600,600);
	this.stage.setScene(sceneYo);
	setLayout();
	this.stage.show();
	}
	
	/**
	 * Hier werden die Kacheln erzeugt und ihnen ein Platz zugewiesen.
	 * Auch wird die Größe der Kacheln festgelegt auf 40 x 40 und ihnen
	 * wird eine Aufschrift gegeben, je nach Position. 
	 */
	public void setLayout(){
		this.setGridLinesVisible(true);

		for (int iReihen = 0; iReihen< rowSize;iReihen++){
			for (int jSpalten = 0; jSpalten<columnSize;jSpalten++){
				String aufschrift = +iReihen +"" +jSpalten;
				if ( (iReihen == 0 && jSpalten == 4) || (iReihen == 8 && jSpalten == 8) || (iReihen == 4 && jSpalten == 0) ){
					labelArray[iReihen][jSpalten] = new Raumkachel(aufschrift, iReihen, jSpalten, true);
				}
				else {
					labelArray[iReihen][jSpalten] = new Feldkachel(aufschrift, iReihen, jSpalten, false);	
				}
				
				this.add(labelArray[iReihen][jSpalten], jSpalten, iReihen);
				System.out.println(iReihen +" - "+jSpalten);
				// this.setConstraints(labelArray[iReihen][jSpalten], jSpalten, iReihen);
				
				labelArray[iReihen][jSpalten].setMaxHeight(40);
				labelArray[iReihen][jSpalten].setMaxWidth(40);
				labelArray[iReihen][jSpalten].setMinHeight(40);
				labelArray[iReihen][jSpalten].setMinWidth(40);
			}
		}
		kachelAnfang = labelArray[0][0];
		spieler = new Spieler(0,0);
		testSpieler = new Circle(20);
		testSpieler.setFill(Color.ROYALBLUE);
		this.add(testSpieler, spieler.getxPosition(), spieler.getyPosition());
		
		
	}

	public Kachel[][] getLabelArray() {
		return labelArray;
	}

	public void setLabelArray(Kachel[][] labelArray) {
		this.labelArray = labelArray;
	}

	/**
	 * Hiermit kann die Zielkachel auf die Farbe Color.ROYALBLUE gesetzt werden.
	 * @param kachel
	 */
	public void setBackground(Kachel kachel){
		fill = new BackgroundFill(Color.ROYALBLUE, null, null);
		Background hintergrund = new Background(fill);
		kachel.setBackground(hintergrund);
	}
	
	public void revertBackground(Kachel label){
		label.setBackground(null);
	}
	
		public Circle getTestSpieler() {
		return testSpieler;
	}

	public void setTestSpieler(Circle testSpieler) {
		this.testSpieler = testSpieler;
	}
	
	public Kachel getKachelAnfang() {
		return kachelAnfang;
	}

	public void setKachelAnfang(Kachel labelAnfang) {
		this.kachelAnfang = labelAnfang;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Scene getSceneYo() {
		return sceneYo;
	}

	public void setSceneYo(Scene sceneYo) {
		this.sceneYo = sceneYo;
	}

	public Spieler getSpieler() {
		return spieler;
	}

	public void setSpieler(Spieler spieler) {
		this.spieler = spieler;
	}
	
=======
 *         Hier wird die vorläufige GUI - eine GridPane - modelliert. Sie ist 24
 *         x 25 Felder Groß und hat ein labelArray, in welchem alle Kacheln
 *         gespeichert sind, zuerst in Reihen, dann Spalten.
 */
public class TestGUI extends GridPane {
//	
//	private Kachel[][] labelArray;
//	private Stage stage;
//	private Scene sceneYo;
//	private Circle testSpieler;
//	private BackgroundFill fill;
//	private Kachel kachelAnfang;
//	private int rowSize;
//	private int columnSize;
//	private Spieler spieler;
//
//
//	private Kachel red,pink,blue,green,white,yellow;
//
//	
//
//	/**
//	 * Der Konstruktor für die GUI. Hier wird die Größe festgelegt und das
//	 * labelArray erzeugt.
//	 * 
//	 * @param columnSize
//	 * @param rowSize
//	 */
//	public TestGUI(int columnSize, int rowSize) {
//		this.rowSize = rowSize;
//		this.columnSize = columnSize;
//		labelArray = new Kachel[rowSize][columnSize];
//	}
//
//	/**
//	 * Hier wird die GUI gestartet als Stage mit vorläufiger Auflösung 600 x 600
//	 * Die Methode setLayout() wird ausgelöst.
//	 */
//	public void start(){
//	this.stage = new Stage();
//	this.sceneYo = new Scene(this,625,625);
//
//	this.stage.setScene(sceneYo);
//	setLayout();
//	this.stage.show();
//
//	}
//
//	/**
//	 * Hier werden die Kacheln erzeugt und ihnen ein Platz zugewiesen. Auch wird
//	 * die Größe der Kacheln festgelegt auf 40 x 40 und ihnen wird eine
//	 * Aufschrift gegeben, je nach Position.
//	 */
//	public void setLayout() {
//		this.setGridLinesVisible(true);
//
//<<<<<<< HEAD
//		for (int iReihen = 0; iReihen < rowSize; iReihen++) {
//			for (int jSpalten = 0; jSpalten < columnSize; jSpalten++) {
//				String aufschrift = +iReihen + "" + jSpalten;
//				if ((iReihen == 0 && jSpalten == 4)
//						|| (iReihen == 8 && jSpalten == 8)
//						|| (iReihen == 4 && jSpalten == 0)) {
//					labelArray[iReihen][jSpalten] = new Raumkachel(aufschrift,
//							iReihen, jSpalten, true);
//				} else {
//					labelArray[iReihen][jSpalten] = new Feldkachel(aufschrift,
//							iReihen, jSpalten, false);
//=======
//		for (int iReihen = 0; iReihen< rowSize;iReihen++){
//			for (int jSpalten = 0; jSpalten<columnSize;jSpalten++){
//				String aufschrift = +iReihen +"" +jSpalten;
//				if ( (iReihen < 4 && jSpalten < 7) //Arbeitszimmer
//						|| (iReihen == 4 && jSpalten == 0)
//						|| (5 < iReihen && iReihen < 11  && jSpalten < 6) //Bibliothek
//						|| (6 < iReihen && iReihen < 10  && jSpalten == 6)
//						|| (iReihen==11 && jSpalten == 0)
//						|| (11 < iReihen && iReihen < 17 && jSpalten < 6) //Billardzimmer
//						|| (iReihen == 17 && jSpalten == 0)
//						|| (18 < iReihen  && jSpalten < 5) //Wintergarten
//						|| (19 < iReihen  && jSpalten < 6)
//						|| (23 == iReihen  && jSpalten == 6)
//						|| (iReihen == 24 && 5 < jSpalten && jSpalten < 9)
//						|| (iReihen < 7  && 8 < jSpalten && jSpalten < 15) //Eingangshalle
//						|| (iReihen == 0 && jSpalten == 8)
//						|| (iReihen == 0 && jSpalten == 15)
//						|| (7 < iReihen && iReihen < 15  && 8 < jSpalten && jSpalten < 14) //Schwimmbad
//						|| (16 < iReihen && iReihen < 23  && 7 < jSpalten && jSpalten < 16) //Musikzimmer
//						|| (22 < iReihen  && 9 < jSpalten && jSpalten < 14)
//						|| (iReihen < 6  && 16 < jSpalten) //Salon
//						|| (iReihen == 6 && jSpalten == 23)
//						|| (iReihen == 8 && jSpalten == 23)
//						|| (8 < iReihen && iReihen < 15  && 15 < jSpalten) //Speisezimmer
//						|| (iReihen == 15 && 18 < jSpalten)
//						|| (iReihen == 16 && jSpalten==23)
//						|| (17 < iReihen  &&  17 < jSpalten) //Kueche
//						|| (iReihen == 23 && jSpalten == 17)
//						|| (iReihen == 24 && 14 < jSpalten)
//						){
//
//					labelArray[iReihen][jSpalten] = new Raumkachel(aufschrift, iReihen, jSpalten, true);
//>>>>>>> Benedikts_Branch
//				}
//
//				this.add(labelArray[iReihen][jSpalten], jSpalten, iReihen);
//<<<<<<< HEAD
//				System.out.println(iReihen + " - " + jSpalten);
//				// this.setConstraints(labelArray[iReihen][jSpalten], jSpalten,
//				// iReihen);
//
//				labelArray[iReihen][jSpalten].setMaxHeight(40);
//				labelArray[iReihen][jSpalten].setMaxWidth(40);
//				labelArray[iReihen][jSpalten].setMinHeight(40);
//				labelArray[iReihen][jSpalten].setMinWidth(40);
//			}
//		}
//		kachelAnfang = labelArray[0][0];
//		spieler = new Spieler(0, 0);
//		testSpieler = new Circle(20);
//=======
//				System.out.println(iReihen +" - "+jSpalten);
//				// this.setConstraints(labelArray[iReihen][jSpalten], jSpalten, iReihen);
//				
//				labelArray[iReihen][jSpalten].setMaxHeight(25);
//				labelArray[iReihen][jSpalten].setMaxWidth(25);
//				labelArray[iReihen][jSpalten].setMinHeight(25);
//				labelArray[iReihen][jSpalten].setMinWidth(25);
//			}
//		}
//		kachelAnfang = labelArray[0][0];
//		spieler = new Spieler(7,0);
//		//Startpunkte
//		blue = new Kachel("B",0,18,false);
//		blue.setBackgroundColor(labelArray[18][0], Color.BLUE);
//		pink = new Kachel("P",0,5,false);
//		pink.setBackgroundColor(labelArray[5][0], Color.MAGENTA);
//		white = new Kachel("W",14,24,false);
//		white.setBackgroundColor(labelArray[24][14], Color.WHITE);
//		red = new Kachel("R",16,0,false);
//		red.setBackgroundColor(labelArray[0][16], Color.MAROON);
//		yellow = new Kachel("Y",23,7,false);
//		yellow.setBackgroundColor(labelArray[7][23], Color.YELLOW);
//		green = new Kachel("G",9,24,false);
//		green.setBackgroundColor(labelArray[24][9], Color.GREEN);
//		
//		testSpieler = new Circle(10);
//>>>>>>> Benedikts_Branch
//		testSpieler.setFill(Color.ROYALBLUE);
//		this.add(testSpieler, spieler.getxPosition(), spieler.getyPosition());
//
//	}
//
//	public Kachel[][] getLabelArray() {
//		return labelArray;
//	}
//
//	public void setLabelArray(Kachel[][] labelArray) {
//		this.labelArray = labelArray;
//	}
//
//	/**
//	 * Hiermit kann die Zielkachel auf die Farbe Color.ROYALBLUE gesetzt werden.
//	 * 
//	 * @param kachel
//	 */
//	public void setBackground(Kachel kachel) {
//		fill = new BackgroundFill(Color.ROYALBLUE, null, null);
//		Background hintergrund = new Background(fill);
//		kachel.setBackground(hintergrund);
//	}
//
//	public void revertBackground(Kachel label) {
//		label.setBackground(null);
//	}
//
//	public Circle getTestSpieler() {
//		return testSpieler;
//	}
//
//	public void setTestSpieler(Circle testSpieler) {
//		this.testSpieler = testSpieler;
//	}
//
//	public Kachel getKachelAnfang() {
//		return kachelAnfang;
//	}
//
//	public void setKachelAnfang(Kachel labelAnfang) {
//		this.kachelAnfang = labelAnfang;
//	}
//
//	public Stage getStage() {
//		return stage;
//	}
//
//	public void setStage(Stage stage) {
//		this.stage = stage;
//	}
//
//	public Scene getSceneYo() {
//		return sceneYo;
//	}
//
//	public void setSceneYo(Scene sceneYo) {
//		this.sceneYo = sceneYo;
//	}
//
//	public Spieler getSpieler() {
//		return spieler;
//	}
//
//	public void setSpieler(Spieler spieler) {
//		this.spieler = spieler;
//	}

>>>>>>> master
}
