package view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InGameQuitView extends GridPane{
	
	private Button yes;
	private Button no;
	private Label question;
	
	private Scene scene;
	private Stage stage;
	
	private GameFrameView gameFrameView;
	
	public InGameQuitView(GameFrameView gameFrameView){
		
		this.gameFrameView = gameFrameView;
		
		this.getRowConstraints().add(new RowConstraints(100));
		this.getRowConstraints().add(new RowConstraints(100));
		this.getColumnConstraints().add(new ColumnConstraints(200));
		this.getColumnConstraints().add(new ColumnConstraints(200));
		this.setHgap(20);
		
		question = new Label("Do you want to QUIT the game?");
		yes = new Button("Yes");
		no = new Button("No");
		
		GridPane.setConstraints(question, 0, 0);
		GridPane.setColumnSpan(question, 2);
		GridPane.setHalignment(question, HPos.CENTER);
		GridPane.setValignment(question, VPos.CENTER);
		GridPane.setConstraints(yes, 0, 1);
		GridPane.setHalignment(yes, HPos.RIGHT);
		GridPane.setValignment(yes, VPos.CENTER);
		GridPane.setConstraints(no, 1, 1);
		GridPane.setHalignment(no, HPos.LEFT);
		GridPane.setValignment(no, VPos.CENTER);
		this.getChildren().addAll(question, yes, no);
		
	}
	
	public void start(){
		
		scene = new Scene (this, 400,200);
		stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setFullScreen(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.toFront();
		stage.show();
		
		}
	
	public void close(){
		
		this.stage.close();
		
	}
	
	
	
	//Getter and Setter Methods
	public GameFrameView getGameFrameView() {
		return gameFrameView;
	}

	public void setGameFrameView(GameFrameView gameFrameView) {
		this.gameFrameView = gameFrameView;
	}

	public Button getYes() {
		return yes;
	}

	public void setYes(Button yes) {
		this.yes = yes;
	}

	public Button getNo() {
		return no;
	}

	public void setNo(Button no) {
		this.no = no;
	}

	public Label getQuestion() {
		return question;
	}

	public void setQuestion(Label question) {
		this.question = question;
	}

	public Scene getThisScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	

}
