package finderOfPaths;

import java.awt.Font;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import kacheln.FeldKachel;
import kacheln.Kachel;
import cluedoNetworkLayer.CluedoPosition;

/**
 * @version 25.06.2015
 * @author Benedikt Mayer
 * 
 * Die Ebene, in welcher die Figuren sich befinden und in welcher die click-Events stattfinden
 *
 */
public class BallEbene2 extends GridPane{


	private Circle spieler;
	private Stage stage;
	private Scene scene;
	private Button geheimgang;
	private Button fremdWuerfeln;
	private Button fremdBewegen;
	
	private Kachel [][] kachelArray;
	
	private char [] keineMoeglichkeiten;
	private char [][] keineMoeglichkeiten2;
	
	public PlayerCircleManager pcManager;
	
	public BallEbene2(PlayerCircleManager pcm){
		pcManager = pcm;
		kachelArray = new Kachel[25][24];
		layoutStuff();
		
	}
	
	public void start(){
		stage = new Stage();
		scene = new Scene(this, 700, 700);
		stage.setScene(scene);
	}
	
	public void layoutStuff(){
		
		BackgroundFill fills = new BackgroundFill(Color.TRANSPARENT, null, null);
		Background hintergrund = new Background(fills);
		this.setBackground(hintergrund);
		
		for (int iReihen = 0; iReihen < kachelArray.length;iReihen++){
			for (int jSpalten = 0; jSpalten < kachelArray[iReihen].length;jSpalten++ ){
				String aufschrift = +iReihen +"" +jSpalten;
				kachelArray[iReihen][jSpalten] = new FeldKachel(aufschrift,  new CluedoPosition(jSpalten,iReihen), false, null,null, false, keineMoeglichkeiten, keineMoeglichkeiten2, null);
				this.add(kachelArray[iReihen][jSpalten], jSpalten, iReihen);
				
				kachelArray[iReihen][jSpalten].setMinHeight(29);
				kachelArray[iReihen][jSpalten].setMinWidth(29);
				kachelArray[iReihen][jSpalten].setVisible(false);
				
			}
		}
		int size = pcManager.getSize();
		for (int i = 0; i < size;i++){
			this.add( pcManager.getCircleByIndex(i), 0, 0);
		}
		
		geheimgang = new Button("G");
//		geheimgang.setFont(Font.font("Regular", 17));
//		geheimgang.setFont(Font.);
		fremdWuerfeln = new Button("W");
		fremdBewegen = new Button("B");
		geheimgang.setStyle("-fx-font: 12 arial -fx-base: #ff0000");
		fremdWuerfeln.setStyle("-fx-font: 12 arial");
		fremdBewegen.setStyle("-fx-font: 12 arial");
		this.add(geheimgang, 0, 0);
		this.add(fremdWuerfeln, 0, 1);
		this.add(fremdBewegen, 0, 2);
		
		
	}

	public Button getGeheimgang() {
		return geheimgang;
	}

	public void setGeheimgang(Button geheimgang) {
		this.geheimgang = geheimgang;
	}

	public Button getFremdWuerfeln() {
		return fremdWuerfeln;
	}

	public void setFremdWuerfeln(Button fremdWuerfeln) {
		this.fremdWuerfeln = fremdWuerfeln;
	}

	public Button getFremdBewegen() {
		return fremdBewegen;
	}

	public void setFremdBewegen(Button fremdBewegen) {
		this.fremdBewegen = fremdBewegen;
	}
	
	
}

