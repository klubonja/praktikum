package nedkosTestPackage;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TestAnimation extends Application {

	private Group group;
	private Circle testSpieler;
	
	private Boolean nachOben;
	private Boolean nachUnten;
	private Boolean nachRechts;
	private Boolean nachLinks;
	
	private Scene scene;
	private Stage stage;
	
	private Timeline timeline;
	private KeyValue keyValue;
	private KeyFrame keyFrame;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.testSpieler = new Circle(50,25,25);
		this.testSpieler.setFill(Color.ROYALBLUE);
		this.group = new Group();
		this.group.getChildren().add(testSpieler);
		this.scene = new Scene(group,400,400);
		this.stage = new Stage();
		this.stage.setScene(scene);
		this.stage.show();
		//while(true){
			this.nachRechts=true;
		
		moveSpielerNachRechts();
		moveSpielerNachUnten();
		moveSpielerNachLinks();
		moveSpielerNachOben();
		//}
	}
	
	public void moveSpielerNachRechts(){
		//if (nachRechts){
		this.timeline = new Timeline();
		this.keyValue = new KeyValue(testSpieler.centerXProperty(), 300);
		this.keyFrame = new KeyFrame(Duration.millis(2000), keyValue);
		this.timeline.getKeyFrames().add(keyFrame);
		this.timeline.play();
		this.nachRechts = false;
		this.nachUnten = true;
		//}
	}
	public void moveSpielerNachUnten(){
		//if (nachUnten){
		this.timeline = new Timeline();
		this.keyValue = new KeyValue(testSpieler.centerYProperty(), 300);
		this.keyFrame = new KeyFrame(Duration.millis(2000), keyValue);
		this.timeline.setDelay(Duration.millis(2000));
		this.timeline.getKeyFrames().add(keyFrame);
		this.timeline.play();
		this.nachUnten = false;
		this.nachLinks = true;
		//}
	}

	public void moveSpielerNachLinks(){
		//if (nachLinks){
		this.timeline = new Timeline();
		this.keyValue = new KeyValue(testSpieler.centerXProperty(), 25);
		this.keyFrame = new KeyFrame(Duration.millis(2000), keyValue);
		this.timeline.setDelay(Duration.millis(4000));
		this.timeline.getKeyFrames().add(keyFrame);
		this.timeline.play();
		this.nachLinks = false;
		this.nachOben = true;		
		//}
	}
	public void moveSpielerNachOben(){
		//if (nachOben){
		this.timeline = new Timeline();
		this.keyValue = new KeyValue(testSpieler.centerYProperty(), 25);
		this.keyFrame = new KeyFrame(Duration.millis(2000), keyValue);
		this.timeline.setDelay(Duration.millis(6000));
		this.timeline.getKeyFrames().add(keyFrame);
		this.timeline.play();
		this.nachOben = false;
		this.nachRechts = true;
		//}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
