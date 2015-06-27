package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class IntroView extends StackPane{
	
	GridPane primary;
	GridPane secondary;
	Label title;
	Label selectChar;
	Button char1;
	Button char2;
	Button char3;
	Button char4;
	Button char5;
	Button char6;
	ToggleGroup group;
	RadioButton pl1;
	RadioButton pl2;
	RadioButton pl3;
	RadioButton pl4;
	RadioButton pl5;
	RadioButton pl6;
	Label nickname;
	TextField nick;
	private Button newGame;
	Button options;
	Button quit;
	DropShadow labelShadow;
	DropShadow radioShadow;
	Label error;
	ToggleButton soundsON;
	ToggleButton soundsOFF;
	Stage primaryStage;
	
	Button back;
	Label volumeLabel;
	Slider volumeSlider;

	private Stage stage;
	private Scene scene;
	
	BorderStroke defaultStroke = new BorderStroke(Color.DARKSLATEGREY, BorderStrokeStyle.SOLID, 
			new CornerRadii(4), new BorderWidths(0.5));
	Border defaultBorder = new Border(defaultStroke);
	
	final Image player1 = new Image("http://vignette3.wikia.nocookie.net/ageofempires/images/2/23/Hades.jpg/revision/latest?cb=20110606234954");
	Image player2 = new Image("http://www.davidrevoy.com/data/images/blog/2012/01/boromir_speedpainting_david-revoy_net.jpg");
	final Image player3 = new Image("http://vignette3.wikia.nocookie.net/ageofempires/images/2/23/Hades.jpg/revision/latest?cb=20110606234954");
	final Image player4 = new Image("http://vignette3.wikia.nocookie.net/ageofempires/images/2/23/Hades.jpg/revision/latest?cb=20110606234954");
	final Image player5 = new Image("http://vignette3.wikia.nocookie.net/ageofempires/images/2/23/Hades.jpg/revision/latest?cb=20110606234954");
	final Image player6 = new Image("http://vignette3.wikia.nocookie.net/ageofempires/images/2/23/Hades.jpg/revision/latest?cb=20110606234954");
	
	final ImageView imageView = new ImageView(player1);
	
	final BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, 
			false, false, true, false);
	
	final BackgroundImage playerOne = new BackgroundImage(player1, BackgroundRepeat.NO_REPEAT, 
			BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
	final Background playerOneBackground = new Background(playerOne);
	
	final BackgroundImage playerTwo = new BackgroundImage(player2, BackgroundRepeat.NO_REPEAT, 
			BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
	final Background playerTwoBackground = new Background(playerTwo);
	
	final BackgroundImage playerThree = new BackgroundImage(player3, BackgroundRepeat.NO_REPEAT, 
			BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
	final Background playerThreeBackground = new Background(playerThree);
	
	final BackgroundImage playerFour = new BackgroundImage(player4, BackgroundRepeat.NO_REPEAT, 
			BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
	final Background playerFourBackground = new Background(playerFour);
	
	final BackgroundImage playerFive = new BackgroundImage(player5, BackgroundRepeat.NO_REPEAT, 
			BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
	final Background playerFiveBackground = new Background(playerFive);
	
	final BackgroundImage playerSix = new BackgroundImage(player6, BackgroundRepeat.NO_REPEAT, 
			BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
	final Background playerSixBackground = new Background(playerSix);
	
	
	//Constructor of the Class
	public IntroView(){
		
		primary = new GridPane();	
		primary.setAlignment(Pos.CENTER);
		primary.setVgap(5);
		primary.setHgap(20);
		primary.setPadding(new Insets(20));
		primary.getRowConstraints().add(new RowConstraints(100));
		primary.getRowConstraints().add(new RowConstraints(50));
		primary.getRowConstraints().add(new RowConstraints(200));
		primary.getRowConstraints().add(new RowConstraints(70));
		primary.getRowConstraints().add(new RowConstraints(50));
		primary.getRowConstraints().add(new RowConstraints(70));
		primary.getRowConstraints().add(new RowConstraints(30));
		primary.getColumnConstraints().add(new ColumnConstraints(150));
		primary.getColumnConstraints().add(new ColumnConstraints(150));
		primary.getColumnConstraints().add(new ColumnConstraints(150));
		primary.getColumnConstraints().add(new ColumnConstraints(150));
		primary.getColumnConstraints().add(new ColumnConstraints(150));
		primary.getColumnConstraints().add(new ColumnConstraints(150));
		primary.setMinHeight(600);
		primary.setMinWidth(1000);
		primary.setStyle("-fx-background-image: url('http://i.huffpost.com/gen/1375606/images/o-DETECTIVE-facebook.jpg');"
				+ " -fx-background-repeat: stretch;  -fx-background-size: 1050 680;");
		
		labelShadow = new DropShadow();
		labelShadow.setRadius(2.0);
		labelShadow.setBlurType(BlurType.GAUSSIAN);
		labelShadow.setColor(Color.BLUE);
		
		radioShadow = new DropShadow();
		radioShadow.setRadius(1.0);
		radioShadow.setBlurType(BlurType.THREE_PASS_BOX);
		
		DropShadow buttonShadow = new DropShadow();
		buttonShadow.setRadius(13);
		
		title = new Label("Cluedo");
		title.setTextFill(Color.WHITE);
		title.setEffect(new Glow(0.7));
		title.setStyle("-fx-font-size: 80; -fx-font-weight: bold;");
		
		selectChar = new Label("Select your character:");
		selectChar.setTextFill(Color.WHITE);
		selectChar.setEffect(labelShadow);
		selectChar.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
		
		char1 = new Button("");
		char1.setBackground(playerOneBackground);
		char1.setPrefSize(150, 200);
		char1.setMaxSize(150, 200);
		//char1.setBorder(defaultBorder);
		char2 = new Button("");
		char2.setBackground(playerTwoBackground);
		char2.setMaxSize(200, 200);
		//char2.setBorder(defaultBorder);
		char3 = new Button("");
		char3.setBackground(playerThreeBackground);
		char3.setMaxSize(300, 200);
		char4 = new Button("");
		char4.setBackground(playerFourBackground);
		char4.setPrefSize(200, 200);
		char5 = new Button("");
		char5.setBackground(playerFiveBackground);
		char5.setPrefSize(200, 200);
		char6 = new Button("");
		char6.setBackground(playerSixBackground);
		char6.setPrefSize(200, 200);
		
		nickname = new Label("Enter your nickname:");
		nickname.setTextFill(Color.WHITE);
		nickname.setEffect(labelShadow);
		nickname.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
		
		nick = new TextField();
		nick.setPrefWidth(300);
		nick.setMaxWidth(300);
		
		group = new ToggleGroup();
		
		pl1 = new RadioButton();
		pl1.setEffect(radioShadow);
		pl1.setToggleGroup(group);
		
		pl2 = new RadioButton();
		pl2.setEffect(radioShadow);
		pl2.setToggleGroup(group);
		
		pl3 = new RadioButton();
		pl3.setEffect(radioShadow);
		pl3.setToggleGroup(group);
		
		pl4 = new RadioButton();
		pl4.setEffect(radioShadow);
		pl4.setToggleGroup(group);
		
		pl5 = new RadioButton();
		pl5.setEffect(radioShadow);
		pl5.setToggleGroup(group);
		
		pl6 = new RadioButton();
		pl6.setEffect(radioShadow);
		pl6.setToggleGroup(group);
		
		newGame = new Button("New Game");
		newGame.setPrefSize(200,70);
		newGame.setMaxSize(200,70);
		newGame.setStyle("-fx-padding: 8 15 15 15;"
				+ " -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0; "
				+ "-fx-background-radius: 8;"
				+ "-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),"
				+ " #9d4024, #d86e3a,"
				+ "radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);"
				+ "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"
				+ "-fx-font-weight: bold; -fx-font-size: 25;");
		
		options = new Button("Options");
		options.setPrefSize(200,70);
		options.setMaxSize(200,70);
		options.setStyle("-fx-padding: 8 15 15 15;"
				+ " -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0; "
				+ "-fx-background-radius: 8;"
				+ "-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),"
				+ " #9d4024, #d86e3a,"
				+ "radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);"
				+ "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"
				+ "-fx-font-weight: bold; -fx-font-size: 25;");
		
		quit = new Button("Quit");
		quit.setPrefSize(200,70);
		quit.setMaxSize(200,70);
		quit.setStyle("-fx-padding: 8 15 15 15;"
				+ " -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0; "
				+ "-fx-background-radius: 8;"
				+ "-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),"
				+ " #9d4024, #d86e3a,"
				+ "radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);"
				+ "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"
				+ "-fx-font-weight: bold; -fx-font-size: 25;");
		
		
		error = new Label("");
		error.setStyle("-fx-font-size: 18");
		error.setTextFill(Color.TOMATO);
		
		/*soundsON = new ToggleButton("Sounds");
		GridPane.setConstraints(soundsON, 5, 0);
		GridPane.setHalignment(soundsON, HPos.RIGHT);
		GridPane.setValignment(soundsON, VPos.TOP);
		soundsOFF = new ToggleButton("Sounds");
		GridPane.setConstraints(soundsON, 5, 0);
		GridPane.setHalignment(soundsON, HPos.CENTER);
		GridPane.setValignment(soundsON, VPos.TOP);
		ToggleGroup gr = new ToggleGroup();
		soundsON.setToggleGroup(gr);
		soundsOFF.setToggleGroup(gr);*/

		GridPane.setConstraints(title, 0, 0);
		GridPane.setColumnSpan(title, 6);
		GridPane.setHalignment(title, HPos.CENTER);
		GridPane.setValignment(title, VPos.CENTER);
		GridPane.setConstraints(selectChar,0,1);
		GridPane.setColumnSpan(selectChar, 6);
		GridPane.setHalignment(selectChar, HPos.CENTER);
		GridPane.setValignment(selectChar, VPos.BOTTOM);
		GridPane.setConstraints(char1, 0, 2);
		GridPane.setConstraints(char2, 1, 2);
		GridPane.setConstraints(char3, 2, 2);
		GridPane.setConstraints(char4, 3, 2);
		GridPane.setConstraints(char5, 4, 2);
		GridPane.setConstraints(char6, 5, 2);
		GridPane.setConstraints(pl1, 0, 3);
		GridPane.setHalignment(pl1, HPos.CENTER);
		GridPane.setValignment(pl1, VPos.TOP);
		GridPane.setConstraints(pl2, 1, 3);
		GridPane.setHalignment(pl2, HPos.CENTER);
		GridPane.setValignment(pl2, VPos.TOP);
		GridPane.setConstraints(pl3, 2, 3);
		GridPane.setHalignment(pl3, HPos.CENTER);
		GridPane.setValignment(pl3, VPos.TOP);
		GridPane.setConstraints(pl4, 3, 3);
		GridPane.setHalignment(pl4, HPos.CENTER);
		GridPane.setValignment(pl4, VPos.TOP);
		GridPane.setConstraints(pl5, 4, 3);
		GridPane.setHalignment(pl5, HPos.CENTER);
		GridPane.setValignment(pl5, VPos.TOP);
		GridPane.setConstraints(pl6, 5, 3);
		GridPane.setHalignment(pl6, HPos.CENTER);
		GridPane.setValignment(pl6, VPos.TOP);
		GridPane.setConstraints(newGame, 0, 5);
		GridPane.setColumnSpan(newGame, 2);
		GridPane.setHalignment(newGame, HPos.RIGHT);
		GridPane.setValignment(newGame, VPos.BOTTOM);
		GridPane.setConstraints(options, 2, 5);
		GridPane.setColumnSpan(options, 2);
		GridPane.setHalignment(options, HPos.CENTER);
		GridPane.setValignment(options, VPos.BOTTOM);
		GridPane.setConstraints(quit, 4, 5);
		GridPane.setColumnSpan(quit, 2);
		GridPane.setHalignment(quit, HPos.LEFT);
		GridPane.setValignment(quit, VPos.BOTTOM);
		GridPane.setConstraints(nickname, 0, 4);
		GridPane.setColumnSpan(nickname, 3);
		GridPane.setHalignment(nickname, HPos.RIGHT);
		GridPane.setValignment(nickname, VPos.CENTER);
		GridPane.setConstraints(nick, 3, 4);
		GridPane.setColumnSpan(nick, 2);
		GridPane.setHalignment(nick, HPos.CENTER);
		GridPane.setValignment(nick, VPos.CENTER);
		GridPane.setConstraints(error, 0, 6);
		GridPane.setColumnSpan(error, 6);
		GridPane.setHalignment(error, HPos.CENTER);
		GridPane.setValignment(error, VPos.BOTTOM);
		primary.getChildren().addAll(title,selectChar,
				char1,char2,char3,char4,char5,char6,
				pl1,pl2,pl3,pl4,pl5,pl6,newGame, nickname, nick, options, quit, error);
		
		secondary = new GridPane();
		secondary.setAlignment(Pos.CENTER);
		secondary.setVgap(5);
		secondary.setHgap(20);
		secondary.setPadding(new Insets(20));
		secondary.getRowConstraints().add(new RowConstraints(100));
		secondary.getRowConstraints().add(new RowConstraints(50));
		secondary.getRowConstraints().add(new RowConstraints(200));
		secondary.getRowConstraints().add(new RowConstraints(70));
		secondary.getRowConstraints().add(new RowConstraints(50));
		secondary.getRowConstraints().add(new RowConstraints(70));
		secondary.getRowConstraints().add(new RowConstraints(30));
		secondary.getColumnConstraints().add(new ColumnConstraints(150));
		secondary.getColumnConstraints().add(new ColumnConstraints(150));
		secondary.getColumnConstraints().add(new ColumnConstraints(150));
		secondary.getColumnConstraints().add(new ColumnConstraints(150));
		secondary.getColumnConstraints().add(new ColumnConstraints(150));
		secondary.getColumnConstraints().add(new ColumnConstraints(150));
		secondary.setMinHeight(600);
		secondary.setMinWidth(1000);
		secondary.setStyle("-fx-background-image: url('http://i.huffpost.com/gen/1375606/images/o-DETECTIVE-facebook.jpg');"
				+ " -fx-background-repeat: stretch;  -fx-background-size: 1050 680;");
		
		volumeLabel = new Label("Sound Volume");
		volumeSlider = new Slider();
		volumeSlider.setMin(0);
		volumeSlider.setMax(100);
		volumeSlider.setValue(50);
		volumeSlider.setShowTickLabels(true);
		volumeSlider.setShowTickMarks(true);
		volumeSlider.setMajorTickUnit(50);
		volumeSlider.setMinorTickCount(5);
		volumeSlider.setBlockIncrement(10);
		
		back = new Button("Back");
		back.setPrefSize(200,70);
		back.setMaxSize(200,70);
		back.setStyle("-fx-padding: 8 15 15 15;"
				+ " -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0; "
				+ "-fx-background-radius: 8;"
				+ "-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),"
				+ " #9d4024, #d86e3a,"
				+ "radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);"
				+ "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"
				+ "-fx-font-weight: bold; -fx-font-size: 25;");
	
		GridPane.setConstraints(back, 0, 5);
		GridPane.setColumnSpan(back, 6);
		GridPane.setValignment(back, VPos.CENTER);
		GridPane.setHalignment(back, HPos.CENTER);
		GridPane.setConstraints(volumeLabel, 0, 3);
		GridPane.setColumnSpan(volumeLabel, 2);
		GridPane.setValignment(volumeLabel, VPos.CENTER);
		GridPane.setHalignment(volumeLabel, HPos.CENTER);
		GridPane.setConstraints(volumeSlider, 2, 3);
		GridPane.setColumnSpan(volumeSlider, 3);
		GridPane.setValignment(volumeSlider, VPos.CENTER);
		GridPane.setHalignment(volumeSlider, HPos.CENTER);
		
		secondary.getChildren().addAll(volumeLabel, volumeSlider, back);
		
		StackPane.setAlignment(primary, Pos.CENTER);
		StackPane.setAlignment(secondary, Pos.CENTER);
		this.getChildren().addAll(primary, secondary);
		primary.toFront();
		secondary.toBack();
	}
	

	public void start(){
		this.scene = new Scene(this, 1000, 650);
		this.stage = new Stage();
		this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.setTitle("YinYanYolos present:");
        this.stage.show();
	}
	
	public void close(){
		this.stage.close();
	}
	
	
	//Getter and Setter Methods
	public Button getOptions() {
		return options;
	}

	public void setOptions(Button options) {
		this.options = options;
	}

	public Button getQuit() {
		return quit;
	}

	public void setQuit(Button quit) {
		this.quit = quit;
	}

		//Getter and Setter Methods

		public Label getTitleLabel() {
			return title;
		}


		public void setTitleLabel(Label title) {
			this.title = title;
		}


		public Label getSelectChar() {
			return selectChar;
		}


		public void setSelectChar(Label selectChar) {
			this.selectChar = selectChar;
		}


		public Button getChar1() {
			return char1;
		}


		public void setChar1(Button char1) {
			this.char1 = char1;
		}


		public Button getChar2() {
			return char2;
		}


		public void setChar2(Button char2) {
			this.char2 = char2;
		}


		public Button getChar3() {
			return char3;
		}


		public void setChar3(Button char3) {
			this.char3 = char3;
		}


		public Button getChar4() {
			return char4;
		}


		public void setChar4(Button char4) {
			this.char4 = char4;
		}


		public Button getChar5() {
			return char5;
		}


		public void setChar5(Button char5) {
			this.char5 = char5;
		}


		public Button getChar6() {
			return char6;
		}


		public void setChar6(Button char6) {
			this.char6 = char6;
		}


		public ToggleGroup getGroup() {
			return group;
		}


		public void setGroup(ToggleGroup group) {
			this.group = group;
		}


		public RadioButton getPl1() {
			return pl1;
		}


		public void setPl1(RadioButton pl1) {
			this.pl1 = pl1;
		}


		public RadioButton getPl2() {
			return pl2;
		}


		public void setPl2(RadioButton pl2) {
			this.pl2 = pl2;
		}


		public RadioButton getPl3() {
			return pl3;
		}


		public void setPl3(RadioButton pl3) {
			this.pl3 = pl3;
		}


		public RadioButton getPl4() {
			return pl4;
		}


		public void setPl4(RadioButton pl4) {
			this.pl4 = pl4;
		}


		public RadioButton getPl5() {
			return pl5;
		}


		public void setPl5(RadioButton pl5) {
			this.pl5 = pl5;
		}


		public RadioButton getPl6() {
			return pl6;
		}


		public void setPl6(RadioButton pl6) {
			this.pl6 = pl6;
		}


		public Label getNickname() {
			return nickname;
		}


		public void setNickname(Label nickname) {
			this.nickname = nickname;
		}


		public TextField getNick() {
			return nick;
		}


		public void setNick(TextField nick) {
			this.nick = nick;
		}


		public Button getNewGame() {
			return newGame;
		}


		public void setNewGame(Button newGame) {
			this.newGame = newGame;
		}


		public DropShadow getLabelShadow() {
			return labelShadow;
		}


		public void setLabelShadow(DropShadow labelShadow) {
			this.labelShadow = labelShadow;
		}


		public DropShadow getRadioShadow() {
			return radioShadow;
		}


		public void setRadioShadow(DropShadow radioShadow) {
			this.radioShadow = radioShadow;
		}


		public Label getError() {
			return error;
		}


		public void setError(Label error) {
			this.error = error;
		}

}