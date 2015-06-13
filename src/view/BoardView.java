package view;




import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import kacheln.FeldKachel;
import kacheln.Kachel;
import kacheln.RaumKachel;
import kacheln.TuerKachel;
import model.Player;
import enums.Orientation;



/**
 * @since 24.05.2015
 * @version 27.05.2015
 * @author Benedikt
 *
 * Hier wird die vorl�ufige GUI - eine GridPane - modelliert.
 * Sie ist 24 x 25 Felder Gro� und hat ein labelArray, in welchem
 * alle Kacheln gespeichert sind, zuerst in Reihen, dann Spalten.
 */
public class BoardView extends GridPane {
		
	private Kachel [][] kachelArray;
	private PlayerView testSpieler;
	private BackgroundFill fill;
	private int rowSize;
	private int columnSize;
	private Player spieler;
	private Kachel red,pink,blue,green,white,yellow;

	private char [][] keineMoeglichkeiten;
	
	private Stage stage;
	private Scene scene;
	
	/**
	 * Der Konstruktor f�r die GUI. Hier wird die Gr��e festgelegt und das
	 * labelArray erzeugt.
	 * @param columnSize
	 * @param rowSize
	 */
	public BoardView(int columnSize, int rowSize){
		 
		this.rowSize = rowSize ;
		this.columnSize = columnSize ;
		kachelArray = new Kachel[rowSize][columnSize];
		setLayout();
	}	
	
	/**
	 * Hier werden die Kacheln erzeugt und ihnen ein Platz zugewiesen.
	 * Auch wird die Gr��e der Kacheln festgelegt auf 29 x 29 und ihnen
	 * wird eine "Aufschrift" gegeben, je nach Position. 
	 * setLayout() weist zu, welche Art Kachel an welcher Position ist.
	 */
	public void setLayout(){
		this.setGridLinesVisible(true);

		for (int iReihen = 0; iReihen< rowSize;iReihen++){
			for (int jSpalten = 0; jSpalten<columnSize;jSpalten++){
				String aufschrift = +iReihen +"" +jSpalten;
				
				///////////////// RAUMKACHELN /////////////////
				
				if ( (iReihen < 4 && jSpalten < 7) //Arbeitszimmer
						|| (iReihen == 4 && jSpalten == 0) ){
					if (iReihen == 3 && jSpalten == 6){ /////////////////// T�R ///////////////////
						kachelArray[iReihen][jSpalten] = new TuerKachel(" S ", iReihen, jSpalten, true, Orientation.S, "Arbeitszimmer", true, keineMoeglichkeiten);
					}
					
					else {///////////// RAUM ////////////////
						kachelArray[iReihen][jSpalten] = new RaumKachel("", iReihen, jSpalten, true, null, "Arbeitszimmer", false, keineMoeglichkeiten);
					}
					
				}
					else if ( (5 < iReihen && iReihen < 11  && jSpalten < 6) //Bibliothek
						|| (6 < iReihen && iReihen < 10  && jSpalten == 6)
						|| (iReihen==11 && jSpalten == 0) ){
						if (iReihen == 10 && jSpalten == 3){/////////////////// T�R ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" S ", iReihen, jSpalten, true, Orientation.S, "Bibliothek", true, keineMoeglichkeiten);
						}
						
						else if (iReihen == 8  && jSpalten == 6){/////////////////// T�R ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" O ", iReihen, jSpalten, true, Orientation.O, "Bibliothek", true, keineMoeglichkeiten);
						}
						
						else {///////////// RAUM ////////////////
							kachelArray[iReihen][jSpalten] = new RaumKachel("", iReihen, jSpalten, true, null, "Bibliothek", false, keineMoeglichkeiten);
						}
						
					}
				
					else if ( (11 < iReihen && iReihen < 17 && jSpalten < 6) //Billardzimmer
						|| (iReihen == 17 && jSpalten == 0) ){
						if  (iReihen == 15 && jSpalten == 5){/////////////////// T�R ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" O ", iReihen, jSpalten, true, Orientation.O, "Billiardzimmer", true, keineMoeglichkeiten);
						}
						
						else if(iReihen == 12 && jSpalten == 1) {/////////////////// T�R ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" N ", iReihen, jSpalten, true, Orientation.N, "Billiardzimmer", true, keineMoeglichkeiten);
						}
						
						else {///////////// RAUM ////////////////
							kachelArray[iReihen][jSpalten] = new RaumKachel("", iReihen, jSpalten, true, null, "Billiardzimmer", false, keineMoeglichkeiten);
						}
						
					}
				
					else if ( (18 < iReihen  && jSpalten < 5) //Wintergarten
						|| (19 < iReihen  && jSpalten < 6)
						|| (23 == iReihen  && jSpalten == 6)
						|| (iReihen == 24 && 5 < jSpalten && jSpalten < 9) ){
						if (iReihen == 19 && jSpalten == 4){/////////////////// T�R ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" O ", iReihen, jSpalten, true, Orientation.O, "Wintergarten", true, keineMoeglichkeiten);
						}
						
						else {///////////// RAUM ////////////////
							kachelArray[iReihen][jSpalten] = new RaumKachel("", iReihen, jSpalten, true, null, "Wintergarten", false, keineMoeglichkeiten);
						}
						
					}
				
					else if ( (iReihen < 7  && 8 < jSpalten && jSpalten < 15) //Eingangshalle
						|| (iReihen == 0 && jSpalten == 8)
						|| (iReihen == 0 && jSpalten == 15) ){
						if  (iReihen == 6 && (jSpalten == 11 || jSpalten == 12) ){/////////////////// T�R ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" S ", iReihen, jSpalten, true, Orientation.S, "Eingangshalle", true, keineMoeglichkeiten);
						}
						
						else if (iReihen == 4 && jSpalten == 9) {/////////////////// T�R ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" W ", iReihen, jSpalten, true, Orientation.W, "Eingangshalle", true, keineMoeglichkeiten);
						}
						
						else {///////////// RAUM ////////////////
							kachelArray[iReihen][jSpalten] = new RaumKachel("", iReihen, jSpalten, true, null, "Eingangshalle", false, keineMoeglichkeiten);
						}
						
					}
				
					else if ( (7 < iReihen && iReihen < 15  && 8 < jSpalten && jSpalten < 14) ){//Schwimmbad
						if  (iReihen == 14 && jSpalten == 11){/////////////////// T�R ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" S ", iReihen, jSpalten, true, Orientation.S, "Schwimmbad", true, keineMoeglichkeiten);
						}
						
						else if(iReihen == 9  && jSpalten == 13){/////////////////// T�R ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" O ", iReihen, jSpalten, true, Orientation.O, "Schwimmbad", true, keineMoeglichkeiten);
						}
						
						else if(iReihen == 8  && jSpalten == 11){/////////////////// T�R ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" N ", iReihen, jSpalten, true, Orientation.N, "Schwimmbad", true, keineMoeglichkeiten);
						}
						
						else if(iReihen == 12 && jSpalten == 9) {/////////////////// T�R ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" W ", iReihen, jSpalten, true, Orientation.W, "Schwimmbad", true, keineMoeglichkeiten);
						}
						
						else {///////////// RAUM ////////////////
							kachelArray[iReihen][jSpalten] = new RaumKachel("", iReihen, jSpalten, true, null, "Schwimmbad", false, keineMoeglichkeiten);
						}
					}
				
					else if ((16 < iReihen && iReihen < 23  && 7 < jSpalten && jSpalten < 16) //Musikzimmer
						|| (22 < iReihen  && 9 < jSpalten && jSpalten < 14) ){
						if  (iReihen == 19 && jSpalten == 15){/////////////////// T�R ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" O ", iReihen, jSpalten, true, Orientation.O, "Musikzimmer", true, keineMoeglichkeiten);
						}
						
						else if (iReihen == 17 && (jSpalten == 9 || jSpalten == 14) ){/////////////////// T�R ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" N ", iReihen, jSpalten, true, Orientation.N, "Musikzimmer", true, keineMoeglichkeiten);
						}
						
						else if (iReihen == 19 && jSpalten == 8 ) {/////////////////// T�R ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" W ", iReihen, jSpalten, true, Orientation.W, "Musikzimmer", true, keineMoeglichkeiten);
						}
						
						else {///////////// RAUM ////////////////
							kachelArray[iReihen][jSpalten] = new RaumKachel("", iReihen, jSpalten, true, null, "Musikzimmer", false, keineMoeglichkeiten);
						}
					}
				
					else if ( (iReihen < 6  && 16 < jSpalten) //Salon
						|| (iReihen == 6 && jSpalten == 23)
						|| (iReihen == 8 && jSpalten == 23) ){
						if (iReihen == 5 && jSpalten == 17){/////////////////// T�R ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" S ", iReihen, jSpalten, true, Orientation.S, "Salon", true, keineMoeglichkeiten);
						}
						
						else {///////////// RAUM ////////////////
							kachelArray[iReihen][jSpalten] = new RaumKachel("", iReihen, jSpalten, true, null, "Salon", false, keineMoeglichkeiten);
						}
					}
				
					else if ( (8 < iReihen && iReihen < 15  && 15 < jSpalten) //Speisezimmer
						|| (iReihen == 15 && 18 < jSpalten)
						|| (iReihen == 16 && jSpalten==23) ){
						if (iReihen == 9 && jSpalten == 17){/////////////////// T�R ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" N ", iReihen, jSpalten, true, Orientation.N, "Speisezimmer", true, keineMoeglichkeiten);
						}
						
						else if (iReihen == 11 && jSpalten == 16){/////////////////// T�R ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" W ", iReihen, jSpalten, true, Orientation.W, "Speisezimmer", true, keineMoeglichkeiten);
						}
						
						else {///////////// RAUM ////////////////
							kachelArray[iReihen][jSpalten] = new RaumKachel("", iReihen, jSpalten, true, null, "Speisezimmer", false, keineMoeglichkeiten);
						}
					}
				
					else if ( (17 < iReihen  &&  17 < jSpalten) //Kueche
						|| (iReihen == 23 && jSpalten == 17)
						|| (iReihen == 24 && 14 < jSpalten) ){
						if (iReihen == 18 && jSpalten == 19) {/////////////////// T�R ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" N ", iReihen, jSpalten, true, Orientation.N, "K�che", true, keineMoeglichkeiten);
						}
						
						else {///////////// RAUM ////////////////
							kachelArray[iReihen][jSpalten] = new RaumKachel("", iReihen, jSpalten, true, null, "K�che", false, keineMoeglichkeiten);
						}
					}
				
				///////////////////// FELDKACHELN ////////////////////////////
				
				    else {				    //Feldkacheln
				    	kachelArray[iReihen][jSpalten] = new FeldKachel(aufschrift, iReihen, jSpalten, false, null, false, keineMoeglichkeiten);
				    	kachelArray[iReihen][jSpalten].setFont(Font.font("Regular", 12));
				    	//kachelArray[iReihen][jSpalten] = new FeldKachel("", iReihen, jSpalten, false, false);
				    }
				
				this.add(kachelArray[iReihen][jSpalten], jSpalten, iReihen);
				System.out.println(iReihen +" - "+jSpalten);
				
				kachelArray[iReihen][jSpalten].setMaxHeight(29);
				kachelArray[iReihen][jSpalten].setMaxWidth(29);
				kachelArray[iReihen][jSpalten].setMinHeight(29);
				kachelArray[iReihen][jSpalten].setMinWidth(29);

			}
		}
		/*
		//Startpunkte
		blue = new Kachel("B",0,18,false, false);
		blue.setBackgroundColor(labelArray[18][0], Color.BLUE);
		pink = new Kachel("P",0,5,false, false);
		pink.setBackgroundColor(labelArray[5][0], Color.MAGENTA);
		white = new Kachel("W",14,24,false, false);
		white.setBackgroundColor(labelArray[24][14], Color.WHITE);
		red = new Kachel("R",16,0,false, false);
		red.setBackgroundColor(labelArray[0][16], Color.MAROON);
		yellow = new Kachel("Y",23,7,false, false);
		yellow.setBackgroundColor(labelArray[7][23], Color.YELLOW);
		green = new Kachel("G",9,24,false, false);
		green.setBackgroundColor(labelArray[24][9], Color.GREEN);
*/
		
	}
	

	public void start(){
		this.stage = new Stage();
		this.scene = new Scene(this, 700, 700);
		this.stage.setScene(scene);
		// this.stage.show();
	}


	public Kachel[][] getKachelArray() {
		return kachelArray;
	}

	public void setKachelArray(Kachel[][] kachelArray) {
		this.kachelArray = kachelArray;
	}

	/**
	 * Hiermit kann die Zielkachel auf die Farbe Color.ROYALBLUE gesetzt werden.
	 * @param kachel
	 */
	public void setBackgroundToRoyalBlue(Kachel kachel){
		fill = new BackgroundFill(Color.ROYALBLUE, null, null);
		Background hintergrund = new Background(fill);
		kachel.setBackground(hintergrund);
	}
	
	public void revertBackground(Kachel label){
		label.setBackground(null);
	}
	
	public PlayerView getTestSpieler() {
		return testSpieler;
	}

	public void setTestSpieler(PlayerView testSpieler) {
		this.testSpieler = testSpieler;
	}
	
	public Player getSpieler() {
		return spieler;
	}

	public void setSpieler(Player spieler) {
		this.spieler = spieler;
	}
	
}
