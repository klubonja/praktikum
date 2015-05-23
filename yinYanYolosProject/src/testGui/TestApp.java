package testGui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.control.Label;
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
	private BackgroundFill fill;

	@Override
	public void start(Stage primaryStage) throws Exception {
		testGUI = new TestGUI();
		testGUI.start();

		for (int i = 0; i<testGUI.getLabelArray().length;i++){
			Label momentanesLabel = testGUI.getLabelArray()[i];
			momentanesLabel.setOnMouseClicked(e -> movePlayer(testGUI.getTestSpieler(), momentanesLabel));
		}		
	}

	public void movePlayer(Circle spieler, Label ziel){
		Label anfangsLabel = testGUI.getLabelAnfang();
		Path path = new Path();
	     path.getElements().add (new MoveTo (anfangsLabel.getLayoutX(), anfangsLabel.getLayoutY()));
	     path.getElements().add (new LineTo (ziel.getLayoutX(), anfangsLabel.getLayoutY()));
	     path.getElements().add (new LineTo (ziel.getLayoutX(), ziel.getLayoutY()));
	     
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
