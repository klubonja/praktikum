package view;




import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.Player;



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
		
	private Kachel [][] labelArray;
	private PlayerView testSpieler;
	private BackgroundFill fill;
	private Kachel kachelAnfang;
	private int rowSize;
	private int columnSize;
	private Player spieler;
	private Kachel red,pink,blue,green,white,yellow;

	
	/**
	 * Der Konstruktor für die GUI. Hier wird die Größe festgelegt und das
	 * labelArray erzeugt.
	 * @param columnSize
	 * @param rowSize
	 */
	public BoardView(int columnSize, int rowSize){
		 
		this.rowSize = rowSize ;
		this.columnSize = columnSize ;
		labelArray = new Kachel[rowSize][columnSize];
		setLayout();
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
				if ( (iReihen < 4 && jSpalten < 7) //Arbeitszimmer
						|| (iReihen == 4 && jSpalten == 0) ){
					if (iReihen == 3 && jSpalten == 6){
						labelArray[iReihen][jSpalten] = new TuerKachel("S", iReihen, jSpalten, true, "SOUTH", "Arbeitszimmer", true);
					}
					
					else {
						labelArray[iReihen][jSpalten] = new RaumKachel(aufschrift, iReihen, jSpalten, true, "Arbeitszimmer", false);
					}
					
				}
					else if ( (5 < iReihen && iReihen < 11  && jSpalten < 6) //Bibliothek
						|| (6 < iReihen && iReihen < 10  && jSpalten == 6)
						|| (iReihen==11 && jSpalten == 0) ){
						if (iReihen == 10 && jSpalten == 3){
							labelArray[iReihen][jSpalten] = new TuerKachel("S", iReihen, jSpalten, true, "SOUTH", "Bibliothek", true);
						}
						
						else if (iReihen == 8  && jSpalten == 6){
							labelArray[iReihen][jSpalten] = new TuerKachel("O", iReihen, jSpalten, true, "EAST", "Bibliothek", true);
						}
						
						else {
							labelArray[iReihen][jSpalten] = new RaumKachel(aufschrift, iReihen, jSpalten, true, "Bibliothek", false);
						}
						
					}
				
					else if ( (11 < iReihen && iReihen < 17 && jSpalten < 6) //Billardzimmer
						|| (iReihen == 17 && jSpalten == 0) ){
						if  (iReihen == 15 && jSpalten == 5){
							labelArray[iReihen][jSpalten] = new TuerKachel("O", iReihen, jSpalten, true, "EAST", "Billiardzimmer", true);
						}
						
						else if(iReihen == 12 && jSpalten == 1) {
							labelArray[iReihen][jSpalten] = new TuerKachel("N", iReihen, jSpalten, true, "NORTH", "Billiardzimmer", true);
						}
						
						else {
							labelArray[iReihen][jSpalten] = new RaumKachel(aufschrift, iReihen, jSpalten, true, "Billiardzimmer", false);
						}
						
					}
				
					else if ( (18 < iReihen  && jSpalten < 5) //Wintergarten
						|| (19 < iReihen  && jSpalten < 6)
						|| (23 == iReihen  && jSpalten == 6)
						|| (iReihen == 24 && 5 < jSpalten && jSpalten < 9) ){
						if (iReihen == 19 && jSpalten == 4){
							labelArray[iReihen][jSpalten] = new TuerKachel("O", iReihen, jSpalten, true, "EAST", "Wintergarten", true);
						}
						
						else {
							labelArray[iReihen][jSpalten] = new RaumKachel(aufschrift, iReihen, jSpalten, true, "Wintergarten", false);
						}
						
					}
				
					else if ( (iReihen < 7  && 8 < jSpalten && jSpalten < 15) //Eingangshalle
						|| (iReihen == 0 && jSpalten == 8)
						|| (iReihen == 0 && jSpalten == 15) ){
						if  (iReihen == 6 && (jSpalten == 11 || jSpalten == 12) ){
							labelArray[iReihen][jSpalten] = new TuerKachel("S", iReihen, jSpalten, true, "SOUTH", "Eingangshalle", true);
						}
						
						else if (iReihen == 4 && jSpalten == 9) {
							labelArray[iReihen][jSpalten] = new TuerKachel("W", iReihen, jSpalten, true, "WEST", "Eingangshalle", true);
						}
						
						else {
							labelArray[iReihen][jSpalten] = new RaumKachel(aufschrift, iReihen, jSpalten, true, "Eingangshalle", false);
						}
						
					}
				
					else if ( (7 < iReihen && iReihen < 15  && 8 < jSpalten && jSpalten < 14) ){//Schwimmbad
						if  (iReihen == 14 && jSpalten == 11){
							labelArray[iReihen][jSpalten] = new TuerKachel("S", iReihen, jSpalten, true, "SOUTH", "Schwimmbad", true);
						}
						
						else if(iReihen == 9  && jSpalten == 13){
							labelArray[iReihen][jSpalten] = new TuerKachel("O", iReihen, jSpalten, true, "EAST", "Schwimmbad", true);
						}
						
						else if(iReihen == 8  && jSpalten == 11){
							labelArray[iReihen][jSpalten] = new TuerKachel("N", iReihen, jSpalten, true, "NORTH", "Schwimmbad", true);
						}
						
						else if(iReihen == 12 && jSpalten == 9) {
							labelArray[iReihen][jSpalten] = new TuerKachel("W", iReihen, jSpalten, true, "WEST", "Schwimmbad", true);
						}
						
						else {
							labelArray[iReihen][jSpalten] = new RaumKachel(aufschrift, iReihen, jSpalten, true, "Schwimmbad", false);
						}
					}
				
					else if ((16 < iReihen && iReihen < 23  && 7 < jSpalten && jSpalten < 16) //Musikzimmer
						|| (22 < iReihen  && 9 < jSpalten && jSpalten < 14) ){
						if  (iReihen == 19 && jSpalten == 15){
							labelArray[iReihen][jSpalten] = new TuerKachel("O", iReihen, jSpalten, true, "EAST", "Musikzimmer", true);
						}
						
						else if (iReihen == 17 && (jSpalten == 9 || jSpalten == 14) ){
							labelArray[iReihen][jSpalten] = new TuerKachel("N", iReihen, jSpalten, true, "NORTH", "Musikzimmer", true);
						}
						
						else if (iReihen == 19 && jSpalten == 8 ) {
							labelArray[iReihen][jSpalten] = new TuerKachel("W", iReihen, jSpalten, true, "WEST", "Musikzimmer", true);
						}
						
						else {
							labelArray[iReihen][jSpalten] = new RaumKachel(aufschrift, iReihen, jSpalten, true, "Musikzimmer", false);
						}
					}
				
					else if ( (iReihen < 6  && 16 < jSpalten) //Salon
						|| (iReihen == 6 && jSpalten == 23)
						|| (iReihen == 8 && jSpalten == 23) ){
						if (iReihen == 5 && jSpalten == 17){
							labelArray[iReihen][jSpalten] = new TuerKachel("S", iReihen, jSpalten, true, "SOUTH", "Salon", true);
						}
						
						else {
							labelArray[iReihen][jSpalten] = new RaumKachel(aufschrift, iReihen, jSpalten, true, "Salon", false);
						}
					}
				
					else if ( (8 < iReihen && iReihen < 15  && 15 < jSpalten) //Speisezimmer
						|| (iReihen == 15 && 18 < jSpalten)
						|| (iReihen == 16 && jSpalten==23) ){
						if (iReihen == 9 && jSpalten == 17){
							labelArray[iReihen][jSpalten] = new TuerKachel("N", iReihen, jSpalten, true, "NORTH", "Speisezimmer", true);
						}
						
						else if (iReihen == 11 && jSpalten == 16){
							labelArray[iReihen][jSpalten] = new TuerKachel("W", iReihen, jSpalten, true, "WEST", "Speisezimmer", true);
						}
						
						else {
							labelArray[iReihen][jSpalten] = new RaumKachel(aufschrift, iReihen, jSpalten, true, "Speisezimmer", false);
						}
					}
				
					else if ( (17 < iReihen  &&  17 < jSpalten) //Kueche
						|| (iReihen == 23 && jSpalten == 17)
						|| (iReihen == 24 && 14 < jSpalten) ){
						if (iReihen == 18 && jSpalten == 19) {
							labelArray[iReihen][jSpalten] = new TuerKachel("N", iReihen, jSpalten, true, "NORTH", "Küche", true);
						}
						
						else {
							labelArray[iReihen][jSpalten] = new RaumKachel(aufschrift, iReihen, jSpalten, true, "Küche", false);
						}
					}
				
				
				    else {				    //Feldkacheln
				    	labelArray[iReihen][jSpalten] = new FeldKachel(aufschrift, iReihen, jSpalten, false, false);	
				    }
				
				this.add(labelArray[iReihen][jSpalten], jSpalten, iReihen);
				System.out.println(iReihen +" - "+jSpalten);
				// this.setConstraints(labelArray[iReihen][jSpalten], jSpalten, iReihen);
				
				labelArray[iReihen][jSpalten].setMaxHeight(29);
				labelArray[iReihen][jSpalten].setMaxWidth(29);
				labelArray[iReihen][jSpalten].setMinHeight(29);
				labelArray[iReihen][jSpalten].setMinWidth(29);

			}
		}
		kachelAnfang = labelArray[0][0];
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
	
		public PlayerView getTestSpieler() {
		return testSpieler;
	}

	public void setTestSpieler(PlayerView testSpieler) {
		this.testSpieler = testSpieler;
	}
	
	public Kachel getKachelAnfang() {
		return kachelAnfang;
	}

	public void setKachelAnfang(Kachel labelAnfang) {
		this.kachelAnfang = labelAnfang;
	}

	public Player getSpieler() {
		return spieler;
	}

	public void setSpieler(Player spieler) {
		this.spieler = spieler;
	}
	
}
