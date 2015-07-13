package view;




import view.Kachel;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import view.PlayerView;
import model.*;



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
						|| (iReihen == 4 && jSpalten == 0)
						|| (5 < iReihen && iReihen < 11  && jSpalten < 6) //Bibliothek
						|| (6 < iReihen && iReihen < 10  && jSpalten == 6)
						|| (iReihen==11 && jSpalten == 0)
						|| (11 < iReihen && iReihen < 17 && jSpalten < 6) //Billardzimmer
						|| (iReihen == 17 && jSpalten == 0)
						|| (18 < iReihen  && jSpalten < 5) //Wintergarten
						|| (19 < iReihen  && jSpalten < 6)
						|| (23 == iReihen  && jSpalten == 6)
						|| (iReihen == 24 && 5 < jSpalten && jSpalten < 9)
						|| (iReihen < 7  && 8 < jSpalten && jSpalten < 15) //Eingangshalle
						|| (iReihen == 0 && jSpalten == 8)
						|| (iReihen == 0 && jSpalten == 15)
						|| (7 < iReihen && iReihen < 15  && 8 < jSpalten && jSpalten < 14) //Schwimmbad
						|| (16 < iReihen && iReihen < 23  && 7 < jSpalten && jSpalten < 16) //Musikzimmer
						|| (22 < iReihen  && 9 < jSpalten && jSpalten < 14)
						|| (iReihen < 6  && 16 < jSpalten) //Salon
						|| (iReihen == 6 && jSpalten == 23)
						|| (iReihen == 8 && jSpalten == 23)
						|| (8 < iReihen && iReihen < 15  && 15 < jSpalten) //Speisezimmer
						|| (iReihen == 15 && 18 < jSpalten)
						|| (iReihen == 16 && jSpalten==23)
						|| (17 < iReihen  &&  17 < jSpalten) //Kueche
						|| (iReihen == 23 && jSpalten == 17)
						|| (iReihen == 24 && 14 < jSpalten)

						){
					labelArray[iReihen][jSpalten] = new RaumKachel(aufschrift, iReihen, jSpalten, true);
				}
				else {
					labelArray[iReihen][jSpalten] = new FeldKachel(aufschrift, iReihen, jSpalten, false);	
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
		
		//Startpunkte
		blue = new Kachel("B",0,18,false);
		blue.setBackgroundColor(labelArray[18][0], Color.BLUE);
		pink = new Kachel("P",0,5,false);
		pink.setBackgroundColor(labelArray[5][0], Color.MAGENTA);
		white = new Kachel("W",14,24,false);
		white.setBackgroundColor(labelArray[24][14], Color.WHITE);
		red = new Kachel("R",16,0,false);
		red.setBackgroundColor(labelArray[0][16], Color.MAROON);
		yellow = new Kachel("Y",23,7,false);
		yellow.setBackgroundColor(labelArray[7][23], Color.YELLOW);
		green = new Kachel("G",9,24,false);
		green.setBackgroundColor(labelArray[24][9], Color.GREEN);

		
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
