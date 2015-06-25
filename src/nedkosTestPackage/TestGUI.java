package nedkosTestPackage;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;



/**
 * @since 24.05.2015
 * @version 27.05.2015
 * @author Benedikt
 *

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
	
}
