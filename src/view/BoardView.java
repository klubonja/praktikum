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
import enums.Rooms;



/**
 * @since 24.05.2015
 * @version 27.05.2015
 * @author Benedikt
 *
 * Hier wird die vorläufige GUI - eine GridPane - modelliert.
 * Sie ist 24 x 25 Felder Groß und hat ein labelArray, in welchem
 * alle Kacheln gespeichert sind, zuerst in Reihen, dann Spalten.
 */
public class BoardView extends GridPane {
		
	private Kachel [][] kachelArray;
	private final Background [][] backgroundKachelArray = new Background [26][25];
	private PlayerView testSpieler;
	private BackgroundFill fill;
	private int rowSize;
	private int columnSize;
	private Player spieler;
	private Kachel red,pink,blue,green,white,yellow;

	private char [] keineMoeglichkeiten;
	private char [][] keineMoeglichkeiten2;
	
	private Stage stage;
	private Scene scene;
	
	/**
	 * Der Konstruktor für die GUI. Hier wird die Größe festgelegt und das
	 * labelArray erzeugt.
	 * @param columnSize
	 * @param rowSize
	 */
	public BoardView(int columnSize, int rowSize){
		 
		this.rowSize = rowSize ;
		this.columnSize = columnSize ;
		kachelArray = new Kachel[rowSize][columnSize];
		setLayout();

		for( int iSpalten = 0; iSpalten < backgroundKachelArray.length;iSpalten++){
			
			for (int jReihen = 0; jReihen < backgroundKachelArray[iSpalten].length;jReihen++){
				backgroundKachelArray[iSpalten][jReihen] = kachelArray[iSpalten][jReihen].getBackground();
			}
			
		}
		 
	}	
	
	public void resetBackground(){

		System.out.println("reset Background");
		
		for( int iSpalten = 0; iSpalten < kachelArray.length;iSpalten++){
			
			for (int jReihen = 0; jReihen < kachelArray[iSpalten].length;jReihen++){
				kachelArray[iSpalten][jReihen].setBackground(backgroundKachelArray[iSpalten][jReihen]);
			}
			
		}
		 
	}	

	public void resetMoeglichkeiten(){
		
		for( int iSpalten = 0; iSpalten < kachelArray.length;iSpalten++){
			
			for (int jReihen = 0; jReihen < kachelArray[iSpalten].length;jReihen++){
				kachelArray[iSpalten][jReihen].setMoeglichkeitenHierher(null);
			}
			
		}
		
	}
	
	
	
	/**
	 * Hier werden die Kacheln erzeugt und ihnen ein Platz zugewiesen.
	 * Auch wird die Größe der Kacheln festgelegt auf 29 x 29 und ihnen
	 * wird eine "Aufschrift" gegeben, je nach Position. 
	 * setLayout() weist zu, welche Art Kachel an welcher Position ist.
	 */
	public void setLayout(){
		this.setGridLinesVisible(true);

		for (int iReihen = 0; iReihen< rowSize;iReihen++){
			for (int jSpalten = 0; jSpalten<columnSize;jSpalten++){
				String aufschrift = +iReihen +"" +jSpalten;
				
				//////////////// Außerhalb /////////////////
				
				if (jSpalten == 24){
					kachelArray[iReihen][jSpalten] = new RaumKachel(null, iReihen, jSpalten, true, null, null, false, keineMoeglichkeiten, keineMoeglichkeiten2, null);
				}
				
				else if ( iReihen == 25){
					kachelArray[iReihen][jSpalten] = new RaumKachel(null, iReihen, jSpalten, true, null, null, false, keineMoeglichkeiten, keineMoeglichkeiten2, null);
				}
				
				
				
				///////////////// RAUMKACHELN /////////////////
				
				else if ( (iReihen < 4 && jSpalten < 7) //Arbeitszimmer
						|| (iReihen == 4 && jSpalten == 0) ){
					if (iReihen == 3 && jSpalten == 6){ /////////////////// TÜR ///////////////////
						kachelArray[iReihen][jSpalten] = new TuerKachel(" S ", iReihen, jSpalten, true, Orientation.S, Rooms.study, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
					}
					
					else {///////////// RAUM ////////////////
						kachelArray[iReihen][jSpalten] = new RaumKachel(null, iReihen, jSpalten, true, null, Rooms.study, false, keineMoeglichkeiten, keineMoeglichkeiten2, null);
					}
					
				}
					else if ( (5 < iReihen && iReihen < 11  && jSpalten < 6) //Bibliothek
						|| (6 < iReihen && iReihen < 10  && jSpalten == 6)
						|| (iReihen==11 && jSpalten == 0) ){
						if (iReihen == 10 && jSpalten == 3){/////////////////// TÜR ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" S ", iReihen, jSpalten, true, Orientation.S, Rooms.library, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
						else if (iReihen == 8  && jSpalten == 6){/////////////////// TÜR ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" O ", iReihen, jSpalten, true, Orientation.O, Rooms.library, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
						else {///////////// RAUM ////////////////
							kachelArray[iReihen][jSpalten] = new RaumKachel(null, iReihen, jSpalten, true, null, Rooms.library, false, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
					}
				
					else if ( (11 < iReihen && iReihen < 17 && jSpalten < 6) //Billardzimmer
						|| (iReihen == 17 && jSpalten == 0) ){
						if  (iReihen == 15 && jSpalten == 5){/////////////////// TÜR ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" O ", iReihen, jSpalten, true, Orientation.O, Rooms.billiard, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
						else if(iReihen == 12 && jSpalten == 1) {/////////////////// TÜR ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" N ", iReihen, jSpalten, true, Orientation.N, Rooms.billiard, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
						else {///////////// RAUM ////////////////
							kachelArray[iReihen][jSpalten] = new RaumKachel(null, iReihen, jSpalten, true, null, Rooms.billiard, false, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
					}
				
					else if ( (18 < iReihen  && jSpalten < 5) //Wintergarten
						|| (19 < iReihen  && jSpalten < 6)
						|| (23 == iReihen  && jSpalten == 6)
						|| (iReihen == 24 && 5 < jSpalten && jSpalten < 9) ){
						if (iReihen == 19 && jSpalten == 4){/////////////////// TÜR ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" O ", iReihen, jSpalten, true, Orientation.O, Rooms.conservatory, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
						else {///////////// RAUM ////////////////
							kachelArray[iReihen][jSpalten] = new RaumKachel(null, iReihen, jSpalten, true, null, Rooms.conservatory, false, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
					}
				
					else if ( (iReihen < 7  && 8 < jSpalten && jSpalten < 15) //Eingangshalle
						|| (iReihen == 0 && jSpalten == 8)
						|| (iReihen == 0 && jSpalten == 15) ){
						if  (iReihen == 6 && (jSpalten == 11 || jSpalten == 12) ){/////////////////// TÜR ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" S ", iReihen, jSpalten, true, Orientation.S, Rooms.hall, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
						else if (iReihen == 4 && jSpalten == 9) {/////////////////// TÜR ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" W ", iReihen, jSpalten, true, Orientation.W, Rooms.hall, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
						else {///////////// RAUM ////////////////
							kachelArray[iReihen][jSpalten] = new RaumKachel(null, iReihen, jSpalten, true, null, Rooms.hall, false, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
					}
				
					else if ( (7 < iReihen && iReihen < 15  && 8 < jSpalten && jSpalten < 14) ){//Schwimmbad
						if  (iReihen == 14 && jSpalten == 11){/////////////////// TÜR ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" S ", iReihen, jSpalten, true, Orientation.S, Rooms.pool, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
						else if(iReihen == 9  && jSpalten == 13){/////////////////// TÜR ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" O ", iReihen, jSpalten, true, Orientation.O, Rooms.pool, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
						else if(iReihen == 8  && jSpalten == 11){/////////////////// TÜR ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" N ", iReihen, jSpalten, true, Orientation.N, Rooms.pool, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
						else if(iReihen == 12 && jSpalten == 9) {/////////////////// TÜR ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" W ", iReihen, jSpalten, true, Orientation.W, Rooms.pool, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
						else {///////////// RAUM ////////////////
							kachelArray[iReihen][jSpalten] = new RaumKachel(null, iReihen, jSpalten, true, null, Rooms.pool, false, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
					}
				
					else if ((16 < iReihen && iReihen < 23  && 7 < jSpalten && jSpalten < 16) //Musikzimmer
						|| (22 < iReihen  && 9 < jSpalten && jSpalten < 14) ){
						if  (iReihen == 19 && jSpalten == 15){/////////////////// TÜR ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" O ", iReihen, jSpalten, true, Orientation.O, Rooms.ballroom, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
						else if (iReihen == 17 && (jSpalten == 9 || jSpalten == 14) ){/////////////////// TÜR ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" N ", iReihen, jSpalten, true, Orientation.N, Rooms.ballroom, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
						else if (iReihen == 19 && jSpalten == 8 ) {/////////////////// TÜR ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" W ", iReihen, jSpalten, true, Orientation.W, Rooms.ballroom, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
						else {///////////// RAUM ////////////////
							kachelArray[iReihen][jSpalten] = new RaumKachel(null, iReihen, jSpalten, true, null, Rooms.ballroom, false, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
					}
				
					else if ( (iReihen < 6  && 16 < jSpalten) //Salon
						|| (iReihen == 6 && jSpalten == 23)
						|| (iReihen == 8 && jSpalten == 23) ){
						if (iReihen == 5 && jSpalten == 17){/////////////////// TÜR ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" S ", iReihen, jSpalten, true, Orientation.S, Rooms.lounge, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
						else {///////////// RAUM ////////////////
							kachelArray[iReihen][jSpalten] = new RaumKachel(null, iReihen, jSpalten, true, null, Rooms.lounge, false, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
					}
				
					else if ( (8 < iReihen && iReihen < 15  && 15 < jSpalten) //Speisezimmer
						|| (iReihen == 15 && 18 < jSpalten)
						|| (iReihen == 16 && jSpalten==23) ){
						if (iReihen == 9 && jSpalten == 17){/////////////////// TÜR ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" N ", iReihen, jSpalten, true, Orientation.N, Rooms.diningroom, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
						else if (iReihen == 11 && jSpalten == 16){/////////////////// TÜR ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" W ", iReihen, jSpalten, true, Orientation.W, Rooms.diningroom, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
						else {///////////// RAUM ////////////////
							kachelArray[iReihen][jSpalten] = new RaumKachel(null, iReihen, jSpalten, true, null, Rooms.diningroom, false, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
					}
				
					else if ( (17 < iReihen  &&  17 < jSpalten) //Kueche
						|| (iReihen == 23 && jSpalten == 17)
						|| (iReihen == 24 && 14 < jSpalten) ){
						if (iReihen == 18 && jSpalten == 19) {/////////////////// TÜR ///////////////////
							kachelArray[iReihen][jSpalten] = new TuerKachel(" N ", iReihen, jSpalten, true, Orientation.N, Rooms.kitchen, true, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
						
						else {///////////// RAUM ////////////////
							kachelArray[iReihen][jSpalten] = new RaumKachel(null, iReihen, jSpalten, true, null, Rooms.kitchen, false, keineMoeglichkeiten, keineMoeglichkeiten2, null);
						}
					}
				
				///////////////////// FELDKACHELN ////////////////////////////
				
				    else {				    //Feldkacheln
				    	kachelArray[iReihen][jSpalten] = new FeldKachel(aufschrift, iReihen, jSpalten, false, null,null, false, keineMoeglichkeiten, keineMoeglichkeiten2, null);
				    	kachelArray[iReihen][jSpalten].setFont(Font.font("Regular", 12));
				    	//kachelArray[iReihen][jSpalten] = new FeldKachel(null, iReihen, jSpalten, false, false);
				    }
				
				
				if (iReihen != 25 && jSpalten != 24){
					this.add(kachelArray[iReihen][jSpalten], jSpalten, iReihen);
				}
				
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
