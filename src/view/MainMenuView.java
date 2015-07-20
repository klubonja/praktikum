package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainMenuView extends StackPane{
	
	private Stage stage;
	private Scene scene;
	
	private GridPane main;
	private Button play;
	private Button howToPlay;
	
	private Background mainBackground;
	private BackgroundImage mainBackgroundImage;
	private Image mainImage;
	
	public MainMenuView(){
		
		
		main = new GridPane();
		main.setAlignment(Pos.CENTER);
		main.setVgap(5);
		main.setHgap(5);
		main.setPadding(new Insets(20));
		main.setGridLinesVisible(true);
		
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
	    row0.setPercentHeight(5); 
	    main.getRowConstraints().add(row0);
	    
	    RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(15);        
	    main.getRowConstraints().add(row1);
	    
	    RowConstraints row2 = new RowConstraints();
	    row2.setPercentHeight(60);        
	    main.getRowConstraints().add(row2);
	    
	    RowConstraints row3 = new RowConstraints();
	    row3.setPercentHeight(15);        
	    main.getRowConstraints().add(row3);
	    
	    RowConstraints row4 = new RowConstraints();
	    row4.setPercentHeight(5);        
	    main.getRowConstraints().add(row4);

		
		mainImage = new Image("http://www.migrantyouthproject.org/wp-content/uploads/2013/05/under_construction.jpg");
		mainBackgroundImage = new BackgroundImage(mainImage, BackgroundRepeat.NO_REPEAT , BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		mainBackground = new Background(mainBackgroundImage);
		main.setBackground(mainBackground);
		play = new Button("Play");
		howToPlay = new Button("How to Play");
		GridPane.setConstraints(play, 0, 3);
		main.getChildren().addAll(play);
		
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
