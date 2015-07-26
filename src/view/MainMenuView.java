package view;

import java.io.File;

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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class MainMenuView extends StackPane{
	
	private Stage stage;
	private Scene scene;
	
	private GridPane main;

	private Button createClient;
	private Button createServer;
	private Button quit;
	private Label title;
	
	private Group background;
	private MediaPlayer backgroundVideo;

	
	private Background mainBackground;
	private BackgroundImage mainBackgroundImage;
	private Image mainImage;
	

	private Slider volume;
	private Label volumeLabel;
	private HBox volumeBox;
	
	private final String buttonStyle = "-fx-padding: 8 15 15 15; -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0; " 
					+ "-fx-background-radius: 8; -fx-background-color: "
					+ "linear-gradient(from 0% 93% to 0% 100%, #228b22 0%, #008000 100%),  #228b22, #008000,"
					+ "radial-gradient(center 50% -60%, radius 100%, #32cd32, #2e8b57);"
					+ "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"
					+ "-fx-font-weight: bold; -fx-font-size: 1.1em; font-stretch: ultra-expanded; "
					+ "-fx-font-family: Helvetica; -fx-text-fill: linear-gradient(white, #d0d0d0); -fx-font-size: 14px;";
	
	
	public MainMenuView(){
		
		
		//Creates a GridPane that serves as the MainMenu
		main = new GridPane();
		main.setAlignment(Pos.CENTER);
		main.setVgap(5);
		main.setHgap(5);
		main.setPadding(new Insets(45, 5 , 20, 5));
		main.setGridLinesVisible(false);
		
		
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
//	    row0.setPercentHeight(10); 
        row0.setPrefHeight(60);
	    main.getRowConstraints().add(row0);
	    
	    RowConstraints row1 = new RowConstraints();
//	    row1.setPercentHeight(20);
	    row1.setPrefHeight(60);
	    main.getRowConstraints().add(row1);
	    
	    RowConstraints row2 = new RowConstraints();
//	    row2.setPercentHeight(50);  
	    row2.setPrefHeight(340);
	    main.getRowConstraints().add(row2);
	    
	    RowConstraints row3 = new RowConstraints();
//	    row3.setPercentHeight(20);
	    row3.setPrefHeight(180);
	    main.getRowConstraints().add(row3);
	    
	    
	    title = new Label("Cluedo");
		title.setTextFill(Color.WHITE);
		title.setEffect(new Glow(0.4));
		title.setStyle("-fx-font-size: 80; -fx-font-weight: bold;");
		title.setAlignment(Pos.CENTER);
		
		Image lotr = new Image("media/introLogo.png");
		ImageView lotrBack = new ImageView(lotr);
		lotrBack.setFitHeight(90);
		lotrBack.setFitWidth(400);
		
		Image clue = new Image("http://wearegames.es/wp-content/uploads/2015/02/Logo-Cluedo.png");
		ImageView cluedo = new ImageView(clue);
//		clue.setFitHeight(100);
//		clue.setFitWidth(500);
		
		Image lmu = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/0/06/LMU_Muenchen_Logo.svg/760px-LMU_Muenchen_Logo.svg.png");
		ImageView lmuImg = new ImageView(lmu);
		lmuImg.setFitHeight(100);
		lmuImg.setFitWidth(200);
	

//		createImageBackground();
		
		
		createClient = new Button("START CLIENT");
		createClient.setPrefSize(200, 68);
		createClient.setMaxSize(200, 68);
		
		createServer = new Button("START SERVER");
		createServer.setPrefSize(200, 68);
		createServer.setMaxSize(200, 68);
		
		quit = new Button("QUIT");
		quit.setPrefSize(200, 68);
		quit.setMaxSize(200, 68);
		
		createClient.setStyle(buttonStyle);
		createServer.setStyle(buttonStyle);
		quit.setStyle(buttonStyle);
		
 
		GridPane.setConstraints(createClient, 0, 3);
		GridPane.setHalignment(createClient, HPos.RIGHT);
		GridPane.setValignment(createClient, VPos.CENTER);
		
		GridPane.setConstraints(createServer, 1, 3);
		GridPane.setHalignment(createServer, HPos.CENTER);
		GridPane.setValignment(createServer, VPos.CENTER);
		
		GridPane.setConstraints(quit, 2, 3);
		GridPane.setHalignment(quit, HPos.LEFT);
		GridPane.setValignment(quit, VPos.CENTER);
		
//		GridPane.setConstraints(title, 0, 1);
//		GridPane.setColumnSpan(title, 3);
//		GridPane.setHalignment(title, HPos.CENTER);
//		GridPane.setValignment(title, VPos.CENTER);
		
		GridPane.setConstraints(lotrBack, 0, 0);
		GridPane.setColumnSpan(lotrBack, 3);
		GridPane.setHalignment(lotrBack, HPos.CENTER);
		GridPane.setValignment(lotrBack, VPos.BOTTOM);
		
		GridPane.setConstraints(cluedo, 0, 1);
		GridPane.setColumnSpan(cluedo, 3);
		GridPane.setHalignment(cluedo, HPos.CENTER);
		GridPane.setValignment(cluedo, VPos.CENTER);
		
		GridPane.setConstraints(lmuImg, 0, 0);
		GridPane.setColumnSpan(lmuImg, 3);
		GridPane.setHalignment(lmuImg, HPos.LEFT);
		GridPane.setValignment(lmuImg, VPos.BOTTOM);
		
		
		main.getChildren().addAll(createClient, createServer, quit,  lotrBack, cluedo, lmuImg);
		
		File file = new File("src/media/splash.mp4");
		String url = file.toURI().toString();
		Media video = new Media(url);
        
		//Setup for the video background
		backgroundVideo = new MediaPlayer(video);	
		
		background = new Group(new MediaView(backgroundVideo));
		backgroundVideo.setStartTime(new Duration(0));
		backgroundVideo.setMute(false);
		//backgroundVideo.setRate(50);
		backgroundVideo.setCycleCount(MediaPlayer.INDEFINITE);
		backgroundVideo.play();
			    
			    
		//Creates the volume slider
		volume = new Slider(0, 1, 0.5);
		volumeLabel = new Label("Volume");
		volumeLabel.setTextFill(Color.WHITESMOKE);
		volumeBox = new HBox(4);
		volumeBox.setPrefSize(200, 50);
		volumeBox.setMaxSize(200, 50);
		volumeBox.getChildren().addAll(volumeLabel, volume);
		
		GridPane.setConstraints(volumeBox, 2, 0);
		GridPane.setValignment(volumeBox, VPos.TOP);
		GridPane.setHalignment(volumeBox, HPos.RIGHT);
		main.getChildren().add(volumeBox);
			   
		
		StackPane.setAlignment(main, Pos.CENTER);
		StackPane.setAlignment(background, Pos.CENTER);
		this.getChildren().addAll(main, background);

		main.toFront();
		
	}
	
	
	
	
	
	public void start(){

		this.scene = new Scene(this, 1100, 525);
		this.stage = new Stage();
		this.stage.setScene(scene);
        this.stage.setResizable(true);
        this.stage.setTitle("YinYanYolos present:");
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.stage.setAlwaysOnTop(true);
        this.stage.centerOnScreen();
        this.stage.show();
        
        @SuppressWarnings("unused")
		MainMenuPresenter pres = new MainMenuPresenter(this);
	}
	
	public void close(){
		this.stage.close();
	}
	
//	public void createImageBackground(){
//		
//		mainImage = new Image("http://www.migrantyouthproject.org/wp-content/uploads/2013/05/under_construction.jpg");
//		mainBackgroundImage = new BackgroundImage(mainImage, BackgroundRepeat.NO_REPEAT , BackgroundRepeat.NO_REPEAT,
//				BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
//		mainBackground = new Background(mainBackgroundImage);
//		main.setBackground(mainBackground);
//		
//	}


	//Getters und Setters
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





	public Background getMainBackground() {
		return mainBackground;
	}





	public void setMainBackground(Background mainBackground) {
		this.mainBackground = mainBackground;
	}






	public BackgroundImage getMainBackgroundImage() {
		return mainBackgroundImage;

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





	public Button getQuit() {
		return quit;
	}





	public void setQuit(Button credits) {
		this.quit = credits;
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
