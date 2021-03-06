package view.spielfeld;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import kacheln.FeldKachel;
import kacheln.Kachel;
import kacheln.KachelContainer;
import kacheln.RaumKachel;
import kacheln.TuerKachel;
import model.Player;
import staticClasses.Config;
import view.PlayerView;
import view.label.KrassesLabel;
import cluedoNetworkLayer.CluedoPosition;
import enums.Orientation;
import enums.Rooms;



/**
 * @since 24.05.2015
 * @version 25.06.2015
 * @author Benedikt Mayer
 *
 * Hier wird die vorläufige GUI - eine GridPane - modelliert.
 * Sie ist 24 x 25 Felder groß und hat ein labelArray, in welchem
 * alle Kacheln gespeichert sind, zuerst in Reihen, dann Spalten.
 */
public class BoardView extends GridPane {
		
//	private Kachel [][] kachelArray;
	private KachelContainer kachelContainer;
	private KrassesLabel [][] labelArray;
	private final Background [][] backgroundKachelArray = new Background [26][25];
	private PlayerView testSpieler;
	private BackgroundFill fill;
	private Player spieler;

	private char [] keineMoeglichkeiten;
	private char [][] keineMoeglichkeiten2;
	
	private Stage stage;
	private Scene scene;
	
	/**
	 * Der Konstruktor f�r die GUI. Hier wird die Gr��e festgelegt und das
	 * labelArray erzeugt.
	 * @param columnSize
	 * @param rowSize
	 */
	public BoardView(KachelContainer kacheln){
		 
		kachelContainer = kacheln;
		setLayout();

		for( int iSpalten = 0; iSpalten < backgroundKachelArray.length;iSpalten++){
			
			for (int jReihen = 0; jReihen < backgroundKachelArray[iSpalten].length;jReihen++){
				backgroundKachelArray[iSpalten][jReihen] = labelArray[iSpalten][jReihen].getBackground();
			}
			
		}
		 
	}	
	
	public void resetBackground(){

		for( int iSpalten = 0; iSpalten < kachelContainer.getKacheln().length;iSpalten++){
			
			for (int jReihen = 0; jReihen < kachelContainer.getKacheln()[iSpalten].length;jReihen++){
				labelArray[iSpalten][jReihen].setBackground(backgroundKachelArray[iSpalten][jReihen]);
			}
			
		}
		 
	}	

	/**
	 * Hier werden die Kacheln erzeugt und ihnen ein Platz zugewiesen.
	 * Auch wird die Gr��e der Kacheln festgelegt auf 29 x 29 und ihnen
	 * wird eine "Aufschrift" gegeben, je nach Position. 
	 * setLayout() weist zu, welche Art Kachel an welcher Position ist.
	 */
	public void setLayout(){
		labelArray = new KrassesLabel[Config.ROWS][Config.COLUMNS];
		this.setGridLinesVisible(false);

//		Color color = new Color(0,0,0,0.1);
//		BackgroundFill backgroundFillBlueViolet = new BackgroundFill(color, null, null);
//		Background backgroundBlueViolet = new Background(backgroundFillBlueViolet);
//		
//		
//		BackgroundFill backgroundFillBrown = new BackgroundFill(Color.BROWN, null, null);
//		Background backgroundBrown = new Background(backgroundFillBrown);
		
		for (int iReihen = 0; iReihen< Config.ROWS;iReihen++){
			for (int jSpalten = 0; jSpalten<Config.COLUMNS;jSpalten++){
				String aufschrift = +iReihen +"" +jSpalten;
				
				//////////////// Au�erhalb /////////////////
				
				if (jSpalten == 24){
					labelArray[iReihen][jSpalten] = new KrassesLabel();
					kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(new CluedoPosition(jSpalten,iReihen), true, null, null, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
				}
				
				else if ( iReihen == 25){
					kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(new CluedoPosition(jSpalten,iReihen), true, null, null, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
					labelArray[iReihen][jSpalten] = new KrassesLabel();
				}
				
				
				
				///////////////// RAUMKACHELN /////////////////
				
				else if ( (iReihen < 4 && jSpalten < 7) //Arbeitszimmer
						|| (iReihen == 4 && jSpalten == 0) ){
					if (iReihen == 3 && jSpalten == 6){ /////////////////// T�R ///////////////////
						labelArray[iReihen][jSpalten] = new KrassesLabel();
						kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.S, Rooms.study, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
					}
					
					else {///////////// RAUM ////////////////
						labelArray[iReihen][jSpalten] = new KrassesLabel();
//						labelArray[iReihen][jSpalten].setBackground(backgroundBlueViolet);
						kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(  new CluedoPosition(jSpalten,iReihen), true, null, Rooms.study, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
					}
					
				}
					else if ( (5 < iReihen && iReihen < 11  && jSpalten < 6) //Bibliothek
						|| (6 < iReihen && iReihen < 10  && jSpalten == 6)
						|| (iReihen==11 && jSpalten == 0) ){
						if (iReihen == 10 && jSpalten == 3){/////////////////// T�R ///////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.S, Rooms.library, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else if (iReihen == 8  && jSpalten == 6){/////////////////// T�R ///////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.O, Rooms.library, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else {///////////// RAUM ////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(  new CluedoPosition(jSpalten,iReihen), true, null, Rooms.library, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
					}
				
					else if ( (11 < iReihen && iReihen < 17 && jSpalten < 6) //Billardzimmer
						|| (iReihen == 17 && jSpalten == 0) ){
						if  (iReihen == 15 && jSpalten == 5){/////////////////// T�R ///////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.O, Rooms.billiard, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else if(iReihen == 12 && jSpalten == 1) {/////////////////// T�R ///////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.N, Rooms.billiard, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else {///////////// RAUM ////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(new CluedoPosition(jSpalten,iReihen), true, null, Rooms.billiard, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
					}
				
					else if ( (18 < iReihen  && jSpalten < 5) //Wintergarten
						|| (19 < iReihen  && jSpalten < 6)
						|| (23 == iReihen  && jSpalten == 6)
						|| (iReihen == 24 && 5 < jSpalten && jSpalten < 9) ){
						if (iReihen == 19 && jSpalten == 4){/////////////////// T�R ///////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.O, Rooms.conservatory, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else {///////////// RAUM ////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(  new CluedoPosition(jSpalten,iReihen), true, null, Rooms.conservatory, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
					}
				
					else if ( (iReihen < 7  && 8 < jSpalten && jSpalten < 15) //Eingangshalle
						|| (iReihen == 0 && jSpalten == 8)
						|| (iReihen == 0 && jSpalten == 15) ){
						if  (iReihen == 6 && (jSpalten == 11 || jSpalten == 12) ){/////////////////// T�R ///////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.S, Rooms.hall, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else if (iReihen == 4 && jSpalten == 9) {/////////////////// T�R ///////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.W, Rooms.hall, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else {///////////// RAUM ////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(  new CluedoPosition(jSpalten,iReihen), true, null, Rooms.hall, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
					}
				
					else if ( (7 < iReihen && iReihen < 15  && 8 < jSpalten && jSpalten < 14) ){//Schwimmbad
						if  (iReihen == 14 && jSpalten == 11){/////////////////// T�R ///////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.S, Rooms.pool, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else if(iReihen == 9  && jSpalten == 13){/////////////////// T�R ///////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.O, Rooms.pool, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else if(iReihen == 8  && jSpalten == 11){/////////////////// T�R ///////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.N, Rooms.pool, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else if(iReihen == 12 && jSpalten == 9) {/////////////////// T�R ///////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.W, Rooms.pool, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else {///////////// RAUM ////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(  new CluedoPosition(jSpalten,iReihen), true, null, Rooms.pool, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
					}
				
					else if ((16 < iReihen && iReihen < 23  && 7 < jSpalten && jSpalten < 16) //Musikzimmer
						|| (22 < iReihen  && 9 < jSpalten && jSpalten < 14) ){
						if  (iReihen == 19 && jSpalten == 15){/////////////////// T�R ///////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.O, Rooms.ballroom, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else if (iReihen == 17 && (jSpalten == 9 || jSpalten == 14) ){/////////////////// T�R ///////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.N, Rooms.ballroom, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else if (iReihen == 19 && jSpalten == 8 ) {/////////////////// T�R ///////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.W, Rooms.ballroom, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else {///////////// RAUM ////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(  new CluedoPosition(jSpalten,iReihen), true, null, Rooms.ballroom, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
					}
				
					else if ( (iReihen < 6  && 16 < jSpalten) //Salon
						|| (iReihen == 6 && jSpalten == 23)
						|| (iReihen == 8 && jSpalten == 23) ){
						if (iReihen == 5 && jSpalten == 17){/////////////////// T�R ///////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.S, Rooms.lounge, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else {///////////// RAUM ////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(  new CluedoPosition(jSpalten,iReihen), true, null, Rooms.lounge, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
					}
				
					else if ( (8 < iReihen && iReihen < 15  && 15 < jSpalten) //Speisezimmer
						|| (iReihen == 15 && 18 < jSpalten)
						|| (iReihen == 16 && jSpalten==23) ){
						if (iReihen == 9 && jSpalten == 17){/////////////////// T�R ///////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.N, Rooms.diningroom, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else if (iReihen == 11 && jSpalten == 16){/////////////////// T�R ///////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.W, Rooms.diningroom, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else {///////////// RAUM ////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(  new CluedoPosition(jSpalten,iReihen), true, null, Rooms.diningroom, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
					}
				
					else if ( (17 < iReihen  &&  17 < jSpalten) //Kueche
						|| (iReihen == 23 && jSpalten == 17)
						|| (iReihen == 24 && 14 < jSpalten) ){
						if (iReihen == 18 && jSpalten == 19) {/////////////////// T�R ///////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.N, Rooms.kitchen, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else {///////////// RAUM ////////////////
							labelArray[iReihen][jSpalten] = new KrassesLabel();
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(new CluedoPosition(jSpalten,iReihen), true, null, Rooms.kitchen, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
					}
				
				///////////////////// FELDKACHELN ////////////////////////////
				
				    else {				    //Feldkacheln
						labelArray[iReihen][jSpalten] = new KrassesLabel();
						labelArray[iReihen][jSpalten].setText(aufschrift);
						labelArray[iReihen][jSpalten].setFont(Font.font("Regular", 12));
				    	kachelContainer.setNewKachelPosition(iReihen, jSpalten, new FeldKachel(new CluedoPosition(jSpalten,iReihen), false, null,null, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
				    }
				
				
				if (iReihen != 25 && jSpalten != 24){
					this.add(labelArray[iReihen][jSpalten], jSpalten, iReihen);
				}
				labelArray[iReihen][jSpalten].setMaxHeight(29);
				labelArray[iReihen][jSpalten].setMaxWidth(29);
				labelArray[iReihen][jSpalten].setMinHeight(29);
				labelArray[iReihen][jSpalten].setMinWidth(29);
			}
		}
		
	}
	

	public void start(){
		this.stage = new Stage();
		this.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
		this.scene = new Scene(this, 696, 725);
		this.stage.setScene(scene);
	}



	/**
	 * Hiermit kann die Zielkachel auf die Farbe Color.ROYALBLUE gesetzt werden.
	 * @param kachel
	 */
	public void setBackgroundToRoyalBlue(KrassesLabel kachel){
		fill = new BackgroundFill(Color.ROYALBLUE, null, null);
		Background hintergrund = new Background(fill);
		kachel.setBackground(hintergrund);
	}
	
	public void revertBackground(KrassesLabel label){
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

	public KrassesLabel[][] getKrassesLabelArray() {
		return labelArray;
	}

	public void setKrassesLabelArray(KrassesLabel[][] labelArray) {
		this.labelArray = labelArray;
	}

	public KrassesLabel[][] getLabelArray() {
		return labelArray;
	}

	public void setLabelArray(KrassesLabel[][] labelArray) {
		this.labelArray = labelArray;
	}
	
	public KrassesLabel getLabelHier(Kachel kachel){
		return labelArray[kachel.getPosition().getY()][kachel.getPosition().getX()];
	}

	public KachelContainer getKachelContainer() {
		return kachelContainer;
	}

	public void setKachelContainer(KachelContainer kachelContainer) {
		this.kachelContainer = kachelContainer;
	}
	
}
