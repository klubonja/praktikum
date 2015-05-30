package testGui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TestApp extends Application {

	private TestGUI testGUI;
	private Timeline timeline;
	private KeyFrame keyFrame;
	private KeyValue keyValue;
	private Background hintergrund;
	private BackgroundFill fill;

	@Override
	public void start(Stage primaryStage) throws Exception {
		testGUI = new TestGUI();
		testGUI.start();

		for (int i = 0; i<testGUI.getLabelArray().length;i++){
			for (int j = 0; j<testGUI.getLabelArray()[j].length;j++){
				Label momentanesLabel = testGUI.getLabelArray()[i][j];
				momentanesLabel.setOnMouseClicked(e -> movePlayer(testGUI.getTestSpieler(), momentanesLabel));
				
			}
		}		
	}

	public void movePlayer(Circle spieler, Label ziel){
		
		int umfang = 5;
		
		Label anfangsLabel = testGUI.getLabelAnfang();
		Label aktuellesLabel;
		Path path = new Path();
	     path.getElements().add (new MoveTo (anfangsLabel.getLayoutX(), anfangsLabel.getLayoutY()));
	     path.getElements().add (new LineTo (ziel.getLayoutX(), anfangsLabel.getLayoutY()));
	     path.getElements().add (new LineTo (ziel.getLayoutX(), ziel.getLayoutY()));
	     
	     /*for(int i = 0; i <= testGUI.getLabelArray().length/4; i++){
	    	 aktuellesLabel = testGUI.getLabelArray()[i];
	    	 testGUI.setBackground(aktuellesLabel);
	     }	
	     */     
	     int rowIndex = testGUI.getRowIndex(ziel);
	     int columnIndex = testGUI.getColumnIndex(ziel);
	     int linksInt = rowIndex - umfang;
	     int rechtsInt = rowIndex + umfang;
	     int obenInt = columnIndex - umfang;
	     int untenInt = columnIndex + umfang;
	     if (rowIndex-umfang<0){
	    	 linksInt = 0;
	     }
	     if (columnIndex-umfang<0){
	    	 rechtsInt = 0;
	     }
	     if (rowIndex+umfang>24){
	    	 obenInt = 0;
	     }
	     if (columnIndex+umfang>24){
	    	 untenInt = 0;
	     }
	     
	     Label links = testGUI.getLabelArray()[columnIndex][linksInt];
	     Label rechts = testGUI.getLabelArray()[columnIndex][rechtsInt];
	     Label oben = testGUI.getLabelArray()[obenInt][rowIndex];
	     Label unten = testGUI.getLabelArray()[untenInt][rowIndex];
	     testGUI.setBackground(links);
	     testGUI.setBackground(rechts);
	     testGUI.setBackground(unten);
	     testGUI.setBackground(oben);
	     PathTransition pathTransition = new PathTransition();	     
	     pathTransition.setDuration(Duration.millis(3000));
	     pathTransition.setNode(spieler);
	     pathTransition.setPath(path);
	     	//pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);	 
	     pathTransition.play();
	    testGUI.setLabelAnfang(ziel);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
