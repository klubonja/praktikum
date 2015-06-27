package finderOfPaths;

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
	
	private Kachel [][] kachelArray;
	
	private char [] keineMoeglichkeiten;
	private char [][] keineMoeglichkeiten2;
	
	public BallEbene2(){
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
				kachelArray[iReihen][jSpalten] = new FeldKachel(aufschrift, iReihen, jSpalten, false, null,null, false, keineMoeglichkeiten, keineMoeglichkeiten2, null);
				this.add(kachelArray[iReihen][jSpalten], jSpalten, iReihen);
				
				kachelArray[iReihen][jSpalten].setMinHeight(29);
				kachelArray[iReihen][jSpalten].setMinWidth(29);
				kachelArray[iReihen][jSpalten].setVisible(false);
				
			}
		}
		
		spieler = new Circle(0,0,14);
		spieler.setFill(Color.RED);
		this.add(spieler, 0, 0);		
	}
	
	public Circle getSpieler() {
		return spieler;
	}

	public void setSpieler(Circle spieler) {
		this.spieler = spieler;
	}

}
