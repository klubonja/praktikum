package view;

import view.spielfeld.GameFrameView;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
	private BackgroundSize backgroundSize;
	private Image image;
	
	private BorderStroke backgroundStroke;
	private Border backgroundBorder;
	
	private GameFrameView gameFrameView;
	
	public InGameMainMenuView(GameFrameView gameFrameView){
		
		this.gameFrameView = gameFrameView;
		
		this.image = new Image("media/ringBack.jpg");
		this.backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, 
				false, false, false, true);
		this.backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT , 
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
	    this.background = new Background(backgroundImage);
	    
	    this.backgroundStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, 
				new CornerRadii(10), new BorderWidths(1.3));
		this.backgroundBorder = new Border(backgroundStroke);
		this.setBorder(backgroundBorder);
	    
	    this.setBackground(background);
		
		Rectangle rect = new Rectangle(400,300);
		rect.setArcHeight(20);
		rect.setArcWidth(20);

		this.setClip(rect);
		
		this.getRowConstraints().add(new RowConstraints(100));
		this.getRowConstraints().add(new RowConstraints(100));
		this.getColumnConstraints().add(new ColumnConstraints(200));
		this.getColumnConstraints().add(new ColumnConstraints(200));
		this.setHgap(20);
		
		question = new Label("Do you want to return to the Main Menu?");
		question.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 14));
        question.setTextFill(Color.WHITESMOKE);
		yes = new Button("Yes");
		no = new Button("No");
		
		
		
		GridPane.setConstraints(question, 0, 0);
		GridPane.setColumnSpan(question, 2);
		GridPane.setHalignment(question, HPos.CENTER);
		GridPane.setValignment(question, VPos.BOTTOM);
		GridPane.setConstraints(yes, 0, 1);
		GridPane.setHalignment(yes, HPos.RIGHT);
		GridPane.setValignment(yes, VPos.CENTER);
		GridPane.setConstraints(no, 1, 1);
		GridPane.setHalignment(no, HPos.LEFT);
		GridPane.setValignment(no, VPos.CENTER);
		this.getChildren().addAll(question, yes, no);
		
	}
	
	public void start(){
		
		scene = new Scene (this, 400,300);
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

	public Background getOurBackground() {
		return background;
	}

	public void setOurBackground(Background background) {
		this.background = background;
	}

	public BackgroundImage getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(BackgroundImage backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public BackgroundSize getBackgroundSize() {
		return backgroundSize;
	}

	public void setBackgroundSize(BackgroundSize backgroundSize) {
		this.backgroundSize = backgroundSize;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public BorderStroke getStroke() {
		return backgroundStroke;
	}

	public void setStroke(BorderStroke stroke) {
		this.backgroundStroke = stroke;
	}

	public Border getBackgroundBorder() {
		return backgroundBorder;
	}

	public void setBackgroundBorder(Border border) {
		this.backgroundBorder = border;
	}
	

}
