package vielCoolererPathfinder;

import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import kacheln.FeldKachel;
import kacheln.Kachel;
import model.Player;

public class BallEbene2 extends GridPane{


	private Circle spieler;
	private Stage stage;
	private Scene scene;
	
	private Kachel [][] kachelArray;
	
	private char [] keineMoeglichkeiten;
	
	public BallEbene2(){
		kachelArray = new Kachel[5][3];
		layoutStuff();
	}
	
	public void start(){
		stage = new Stage();
		scene = new Scene(this, 500, 500);
		stage.setScene(scene);
	}
	
	public void layoutStuff(){
		
		
		BackgroundFill fills = new BackgroundFill(Color.TRANSPARENT, null, null);
		Background hintergrund = new Background(fills);
		this.setBackground(hintergrund);
		
		for (int iReihen = 0; iReihen < kachelArray.length;iReihen++){
			for (int jSpalten = 0; jSpalten < kachelArray[iReihen].length;jSpalten++ ){
				String aufschrift = +iReihen +"" +jSpalten;
				kachelArray[iReihen][jSpalten] = new FeldKachel(aufschrift, iReihen, jSpalten, false, null, false, keineMoeglichkeiten);
				this.add(kachelArray[iReihen][jSpalten], jSpalten, iReihen);
				
				kachelArray[iReihen][jSpalten].setMinHeight(80);
				kachelArray[iReihen][jSpalten].setMinWidth(80);
				kachelArray[iReihen][jSpalten].setVisible(false);
				
			}
		}
		
		spieler = new Circle(0,0,40);
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
