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
import staticClasses.Config;
import view.KrassesLabel;
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
	
	private KrassesLabel [][] labelArray;
	
	private char [] keineMoeglichkeiten;
	private char [][] keineMoeglichkeiten2;
	
	public PlayerCircleManager pcManager;
	
	public BallEbene2(PlayerCircleManager pcm){
		pcManager = pcm;
		layoutStuff();
		
	}
	
	public void start(){
		stage = new Stage();
		scene = new Scene(this, 700, 700);
		stage.setScene(scene);
	}
	
	public void layoutStuff(){
		
		labelArray = new KrassesLabel [Config.ROWS][Config.COLUMNS];
		
		BackgroundFill fills = new BackgroundFill(Color.TRANSPARENT, null, null);
		Background hintergrund = new Background(fills);
		this.setBackground(hintergrund);
		
		for (int iReihen = 0; iReihen < labelArray.length;iReihen++){
			for (int jSpalten = 0; jSpalten < labelArray[iReihen].length;jSpalten++ ){
				String aufschrift = +iReihen +"" +jSpalten;
				labelArray[iReihen][jSpalten] = new KrassesLabel(aufschrift);
				
				this.add(labelArray[iReihen][jSpalten], jSpalten, iReihen);
				
				labelArray[iReihen][jSpalten].setMinHeight(29);
				labelArray[iReihen][jSpalten].setMinWidth(29);
				labelArray[iReihen][jSpalten].setVisible(false);
				
			}
		}
		int size = pcManager.getSize();
		for (int i = 0; i < size;i++){
			this.add( pcManager.getCircleByIndex(i), 0, 0);
		}
		
	}

	public KrassesLabel[][] getLabelArray() {
		return labelArray;
	}

	public void setLabelArray(KrassesLabel[][] labelArray) {
		this.labelArray = labelArray;
	}
	
	
	
}

