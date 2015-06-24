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

public class BallEbene2 extends GridPane{


	private Circle spieler;
	private Stage stage;
	private Scene scene;
	
	private Button wuerfeln;
	private Button beginnen;
	
	private Kachel [][] kachelArray;
	
	private char [] keineMoeglichkeiten;
	
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
		
		beginnen = new Button("!");
		
		wuerfeln = new Button("");
		this.add(wuerfeln, 1, 1);
		this.add(beginnen, 2, 2);
		wuerfeln.setMaxHeight(29);
		wuerfeln.setMaxWidth(29);
		wuerfeln.setMinHeight(29);
		wuerfeln.setMinWidth(29);
		beginnen.setMaxHeight(29);
		beginnen.setMaxWidth(29);
		beginnen.setMinHeight(29);
		beginnen.setMinWidth(29);
		
		BackgroundFill fills = new BackgroundFill(Color.TRANSPARENT, null, null);
		Background hintergrund = new Background(fills);
		this.setBackground(hintergrund);
		
		for (int iReihen = 0; iReihen < kachelArray.length;iReihen++){
			for (int jSpalten = 0; jSpalten < kachelArray[iReihen].length;jSpalten++ ){
				String aufschrift = +iReihen +"" +jSpalten;
				kachelArray[iReihen][jSpalten] = new FeldKachel(aufschrift, iReihen, jSpalten, false, null,null, false, keineMoeglichkeiten, keineMoeglichkeiten);
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

	public Button getWuerfeln() {
		return wuerfeln;
	}

	public void setWuerfeln(Button wuerfeln) {
		this.wuerfeln = wuerfeln;
	}

	public Button getBeginnen() {
		return beginnen;
	}

	public void setBeginnen(Button beginnen) {
		this.beginnen = beginnen;
	}

	
	

	
}
