package view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InGameMainMenuView extends GridPane{
	
	private Button yes;
	private Button no;
	private Label question;
	
	private Scene scene;
	private Stage stage;
	
	private Background background;
	private BackgroundImage backgroundImage;
	private Image image;
	
	private GameFrameView gameFrameView;
	
	public InGameMainMenuView(GameFrameView gameFrameView){
		
		this.gameFrameView = gameFrameView;
		
		this.image = new Image("http://orig07.deviantart.net/05ff/f/2010/161/a/0/rivendell_balcony_by_filiusdracul.jpg");
		
		
		Rectangle rect = new Rectangle(400,200);
		rect.setArcHeight(20);
		rect.setArcWidth(20);

		this.setClip(rect);
		
		this.getRowConstraints().add(new RowConstraints(100));
		this.getRowConstraints().add(new RowConstraints(100));
		this.getColumnConstraints().add(new ColumnConstraints(200));
		this.getColumnConstraints().add(new ColumnConstraints(200));
		this.setHgap(20);
		
		question = new Label("Do you want to return to the Main Menu?");
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
		scene.setFill(Color.TRANSPARENT);
		stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setFullScreen(false);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.toFront();
		stage.centerOnScreen();
		stage.setAlwaysOnTop(true);
		stage.setOpacity(0.95);
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
