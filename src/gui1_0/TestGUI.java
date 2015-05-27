package gui1_0;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class TestGUI extends GridPane {
		
	private Kachel [][] labelArray;
	private Stage stage;
	private Scene sceneYo;
	private Circle testSpieler;
	private BackgroundFill fill;
	private Kachel kachelAnfang;
	private int rowSize;
	private int columnSize;
	private Kachel obstacle;
	private int obstacleRow;
	private int obstacleColumn;
	private Spieler spieler;
	
	public TestGUI(int columnSize, int rowSize){
		 this.rowSize = rowSize ;
		 this.columnSize = columnSize ;
		labelArray = new Kachel[rowSize][columnSize];
	}	
	
	public void start(){
	this.stage = new Stage();
	this.sceneYo = new Scene(this,600,600);
	this.stage.setScene(sceneYo);
	setLayout();
	this.stage.show();
	}
	
	public void setLayout(){
		this.setGridLinesVisible(true);

		for (int iReihen = 0; iReihen< rowSize;iReihen++){
			for (int jSpalten = 0; jSpalten<columnSize;jSpalten++){
				String aufschrift = +iReihen +"" +jSpalten;
				if (iReihen == 0 && jSpalten == 4){
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

	public void setBackground(Kachel label){
		fill = new BackgroundFill(Color.ROYALBLUE, null, null);
		Background hintergrund = new Background(fill);
		label.setBackground(hintergrund);
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

	public int getObstacleRow() {
		return obstacleRow;
	}

	public void setObstacleRow(int obstacleRow) {
		this.obstacleRow = obstacleRow;
	}

	public int getObstacleColumn() {
		return obstacleColumn;
	}

	public void setObstacleColumn(int obstacleColumn) {
		this.obstacleColumn = obstacleColumn;
	}

	public Spieler getSpieler() {
		return spieler;
	}

	public void setSpieler(Spieler spieler) {
		this.spieler = spieler;
	}
	
}
