package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
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

	private Button createClient;
	private Button createServer;
	private Button howToPlay;
	private Label title;
	
	private Group background;
	private MediaPlayer backgroundVideo;
	
	private Background mainBackground;
	private BackgroundImage mainBackgroundImage;
	private Image mainImage;
	
	private Background buttonBackground;
	private BackgroundFill buttonBackgroundFill;
	private LinearGradient lin1;
	private LinearGradient lin2;
	private RadialGradient rad1;
	
	private Slider volume;
	private Label volumeLabel;
	private HBox volumeBox;
	
	private final String buttonStyle = "-fx-padding: 8 15 15 15; -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0; " 
					+ "-fx-background-radius: 8; -fx-background-color: "
					+ "linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),  #9d4024, #d86e3a,"
					+ "radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);"
					+ "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"
					+ "-fx-font-weight: bold; -fx-font-size: 1.1em;"
					+ "-fx-font-family: Arial; -fx-text-fill: linear-gradient(white, #d0d0d0); -fx-font-size: 15px;";
	
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
		title.setEffect(new Glow(0.4));
		title.setStyle("-fx-font-size: 80; -fx-font-weight: bold;");

		//Creates the background of the grid
//		mainImage = new Image("http://www.migrantyouthproject.org/wp-content/uploads/2013/05/under_construction.jpg");
//		mainBackgroundImage = new BackgroundImage(mainImage, BackgroundRepeat.NO_REPEAT , BackgroundRepeat.NO_REPEAT,
//				BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
//		mainBackground = new Background(mainBackgroundImage);
//		main.setBackground(mainBackground);
		
		
		createClient = new Button("Create Client");
		createClient.setPrefSize(200, 75);
		createClient.setMaxSize(200, 75);
		
		createServer = new Button("Create Server");
		createServer.setPrefSize(200, 75);
		createServer.setMaxSize(200, 75);
		
		createClient.setStyle(buttonStyle);
		createServer.setStyle(buttonStyle);
		
 
		howToPlay = new Button("How to createClient");
		GridPane.setConstraints(createClient, 0, 3);
		GridPane.setHalignment(createClient, HPos.CENTER);
		GridPane.setValignment(createClient, VPos.CENTER);
		
		GridPane.setConstraints(createServer, 1, 3);
		GridPane.setHalignment(createServer, HPos.CENTER);
		GridPane.setValignment(createServer, VPos.CENTER);
		
		GridPane.setConstraints(title, 0, 1);
		GridPane.setColumnSpan(title, 3);
		GridPane.setHalignment(title, HPos.CENTER);
		GridPane.setValignment(title, VPos.CENTER);
		
		main.getChildren().addAll(createClient, title, createServer);
		
		Stop darkgrey = new Stop(0,Color.DARKSLATEGREY);
		Stop blue = new Stop(1, Color.MIDNIGHTBLUE);
		Stop green = new Stop(2, Color.PALEGREEN);
		Stop[] stops = new Stop[] { darkgrey, blue, green};
		RadialGradient rg1 = new RadialGradient(1, 0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		
		
		//Setup for the video background
		backgroundVideo = new MediaPlayer(
			      new Media("http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv")
			    );	
		
		background = new Group(new MediaView(backgroundVideo));

		backgroundVideo.setMute(false);
		backgroundVideo.setRate(50);
		backgroundVideo.setCycleCount(MediaPlayer.INDEFINITE);
		backgroundVideo.play();
			    
			    
		//Creates the volume slider
		volume = new Slider(0, 1, 0.5);
		volumeLabel = new Label("Volume");
		volumeBox = new HBox(4);
		volumeBox.setPrefSize(200, 50);
		volumeBox.setMaxSize(200, 50);
		volumeBox.getChildren().addAll(volumeLabel, volume);
		
		GridPane.setConstraints(volumeBox, 2, 1);
		GridPane.setValignment(volumeBox, VPos.TOP);
		GridPane.setHalignment(volumeBox, HPos.RIGHT);
		main.getChildren().add(volumeBox);
			   
  
		
		StackPane.setAlignment(main, Pos.CENTER);
		StackPane.setAlignment(background, Pos.CENTER);
		this.getChildren().addAll(main, background);
		main.toFront();
		
	}
	
	
	
	
	
	public void start(){

		this.scene = new Scene(this, 1100, 650);
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




	public Button getCreateClient() {
		
		return createClient;
	}




	public void setCreateClient(Button createClient) {
		
		this.createClient = createClient;
	}






	public Button getHowTocreateClient() {
		return howToPlay;

	}






	public void setHowTocreateClient(Button howTocreateClient) {
		this.howToPlay = howTocreateClient;

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





	public Button getCreateServer() {
		return createServer;
	}





	public void setCreateServer(Button createServer) {
		this.createServer = createServer;
	}





	public Label getTitle() {
		return title;
	}





	public void setTitle(Label title) {
		this.title = title;
	}





	public Group getOurBackground() {
		return background;
	}





	public void setBackground(Group background) {
		this.background = background;
	}





	public MediaPlayer getBackgroundVideo() {
		return backgroundVideo;
	}





	public void setBackgroundVideo(MediaPlayer backgroundVideo) {
		this.backgroundVideo = backgroundVideo;
	}





	public Background getButtonBackground() {
		return buttonBackground;
	}





	public void setButtonBackground(Background buttonBackground) {
		this.buttonBackground = buttonBackground;
	}





	public BackgroundFill getButtonBackgroundFill() {
		return buttonBackgroundFill;
	}





	public void setButtonBackgroundFill(BackgroundFill buttonBackgroundFill) {
		this.buttonBackgroundFill = buttonBackgroundFill;
	}





	public LinearGradient getLin1() {
		return lin1;
	}





	public void setLin1(LinearGradient lin1) {
		this.lin1 = lin1;
	}





	public LinearGradient getLin2() {
		return lin2;
	}





	public void setLin2(LinearGradient lin2) {
		this.lin2 = lin2;
	}





	public RadialGradient getRad1() {
		return rad1;
	}





	public void setRad1(RadialGradient rad1) {
		this.rad1 = rad1;
	}





	public Scene getOurScene() {
		return scene;
	}


	public Slider getVolume() {
		return volume;
	}


	public void setVolume(Slider volume) {
		this.volume = volume;
	}


	public Label getVolumeLabel() {
		return volumeLabel;
	}


	public void setVolumeLabel(Label volumeLabel) {
		this.volumeLabel = volumeLabel;
	}


	public HBox getVolumeBox() {
		return volumeBox;
	}


	public void setVolumeBox(HBox volumeBox) {
		this.volumeBox = volumeBox;
	}


	public String getButtonStyle() {
		return buttonStyle;
	}


}
