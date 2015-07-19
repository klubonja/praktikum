package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class MainMenuView extends StackPane{
	
	private Stage stage;
	private Scene scene;
	
	private GridPane main;
	private Button play;
	private Button howToPlay;
	private Label title;
	
	private Background mainBackground;
	private BackgroundImage mainBackgroundImage;
	private Image mainImage;
	
	private Background buttonBackground;
	private BackgroundFill buttonBackgroundFill;
	private LinearGradient lin1;
	private LinearGradient lin2;
	private RadialGradient rad1;
	
	public MainMenuView(){
		
		//Creates a GridPane that serves as the MainMenu
		main = new GridPane();
		main.setAlignment(Pos.CENTER);
		main.setVgap(5);
		main.setHgap(5);
		main.setPadding(new Insets(20));
		main.setGridLinesVisible(true);
		
		
		//Creates the Rows and columns of the grid
		ColumnConstraints column0 = new ColumnConstraints();
        column0.setPercentWidth(33.3);
        main.getColumnConstraints().add(column0);
        
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(33.3);
        main.getColumnConstraints().add(column1);
        
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(33.3);
        main.getColumnConstraints().add(column2);
        
        RowConstraints row0 = new RowConstraints(); 
	    row0.setPercentHeight(10); 
	    main.getRowConstraints().add(row0);
	    
	    RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(15);        
	    main.getRowConstraints().add(row1);
	    
	    RowConstraints row2 = new RowConstraints();
	    row2.setPercentHeight(50);        
	    main.getRowConstraints().add(row2);
	    
	    RowConstraints row3 = new RowConstraints();
	    row3.setPercentHeight(15);        
	    main.getRowConstraints().add(row3);
	    
	    RowConstraints row4 = new RowConstraints();
	    row4.setPercentHeight(10);        
	    main.getRowConstraints().add(row4);
	    
	    title = new Label("Cluedo");
		title.setTextFill(Color.WHITE);
		title.setEffect(new Glow(0.7));
		title.setStyle("-fx-font-size: 80; -fx-font-weight: bold;");

		//Creates the background of the grid
		mainImage = new Image("http://www.migrantyouthproject.org/wp-content/uploads/2013/05/under_construction.jpg");
		mainBackgroundImage = new BackgroundImage(mainImage, BackgroundRepeat.NO_REPEAT , BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		mainBackground = new Background(mainBackgroundImage);
		main.setBackground(mainBackground);
		play = new Button("Play");
		play.setStyle("-fx-background-color: #090a0c, linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%)," 
        + "linear-gradient(#20262b, #191d22),"
        + "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
     + "-fx-background-radius: 5,4,3,5;"
     + "-fx-background-insets: 0,1,2,0;"
    + "-fx-text-fill: white;"
    + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
     + "-fx-font-family: Arial;"
    + "-fx-text-fill: linear-gradient(white, #d0d0d0);"
    + "-fx-font-size: 12px;"
    + "-fx-padding: 10 20 10 20;" );
		play.maxWidthProperty().bind(column0.maxWidthProperty());
		play.prefWidthProperty().bind(column0.prefWidthProperty());
		play.minWidthProperty().bind(column0.minWidthProperty());
 
		howToPlay = new Button("How to Play");
		GridPane.setConstraints(play, 0, 3);
		GridPane.setHalignment(play, HPos.CENTER);
		GridPane.setValignment(play, VPos.CENTER);
		GridPane.setConstraints(title, 0, 1);
		GridPane.setColumnSpan(title, 3);
		GridPane.setHalignment(title, HPos.CENTER);
		GridPane.setValignment(title, VPos.CENTER);
		main.getChildren().addAll(play, title);
		
		
		Stop[] stops = new Stop[] { new Stop(0, Color.LAVENDER), new Stop(1, Color.GHOSTWHITE)};
		RadialGradient rg1 = new RadialGradient(1, 0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		
		
		
		StackPane.setAlignment(main, Pos.CENTER);
		this.getChildren().addAll(main);
		main.toFront();
		
	}
	
	
	
	
	
	public void start(){
		this.scene = new Scene(this, 1000, 650);
		this.stage = new Stage();
		this.stage.setScene(scene);
        this.stage.setResizable(true);
        this.stage.setTitle("YinYanYolos present:");
        this.stage.show();
	}
	
	public void close(){
		this.stage.close();
	}





	public Stage getStage() {
		return stage;
	}





	public void setStage(Stage stage) {
		this.stage = stage;
	}





	public Scene getMyScene() {
		return scene;
	}





	public void setScene(Scene scene) {
		this.scene = scene;
	}





	public GridPane getMain() {
		return main;
	}





	public void setMain(GridPane main) {
		this.main = main;
	}





	public Button getPlay() {
		return play;
	}





	public void setPlay(Button play) {
		this.play = play;
	}





	public Button getHowToPlay() {
		return howToPlay;
	}





	public void setHowToPlay(Button howToPlay) {
		this.howToPlay = howToPlay;
	}





	public Background getMainBackground() {
		return mainBackground;
	}





	public void setMainBackground(Background mainBackground) {
		this.mainBackground = mainBackground;
	}





	public BackgroundImage getMainBackgroundImage() {
		return mainBackgroundImage;
	}





	public void setMainBackgroundImage(BackgroundImage mainBackgroundImage) {
		this.mainBackgroundImage = mainBackgroundImage;
	}





	public Image getMainImage() {
		return mainImage;
	}





	public void setMainImage(Image mainImage) {
		this.mainImage = mainImage;
	}

}
