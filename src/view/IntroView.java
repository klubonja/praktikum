package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class IntroView extends GridPane{
	
	
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
	Button newGame;
	DropShadow labelShadow;
	DropShadow radioShadow;
	Label error;

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


	final ImageView imageView = new ImageView(
		      new Image("http://vignette3.wikia.nocookie.net/ageofempires/images/2/23/Hades.jpg/revision/latest?cb=20110606234954"));
	
	//Constructor of the Class
	public IntroView(){
		
		
		this.setAlignment(Pos.CENTER);
		this.setVgap(5);
		this.setHgap(20);
		this.setPadding(new Insets(20));
		this.getRowConstraints().add(new RowConstraints(100));
		this.getRowConstraints().add(new RowConstraints(50));
		this.getRowConstraints().add(new RowConstraints(200));
		this.getRowConstraints().add(new RowConstraints(70));
		this.getRowConstraints().add(new RowConstraints(70));
		this.getColumnConstraints().add(new ColumnConstraints(150));
		this.getColumnConstraints().add(new ColumnConstraints(150));
		this.getColumnConstraints().add(new ColumnConstraints(150));
		this.getColumnConstraints().add(new ColumnConstraints(150));
		this.getColumnConstraints().add(new ColumnConstraints(150));
		this.getColumnConstraints().add(new ColumnConstraints(150));
		this.setMinHeight(600);
		this.setMinWidth(1000);
		this.setStyle("-fx-background-image: url('http://i.huffpost.com/gen/1375606/images/o-DETECTIVE-facebook.jpg');"
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
		char1.setGraphic(imageView);
		imageView.setFitHeight(200);
		imageView.setFitWidth(150);
		imageView.setPreserveRatio(true);
		char1.setPrefSize(150, 200);
		char1.setMaxSize(150, 200);
		char2 = new Button("Pl1");
		char2.setMaxSize(200, 200);
		char3 = new Button("Pl1");
		char3.setMaxSize(300, 200);
		char4 = new Button("Pl1");
		char4.setPrefSize(200, 200);
		char5 = new Button("Pl1");
		char5.setPrefSize(200, 200);
		char6 = new Button("Pl1");
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
		/*newGame.setMinSize(150, 150);
		newGame.setTextFill(Color.WHITE);
		newGame.setStyle("-fx-font-size: 30 ;"
				+ "-fx-background-color: radial-gradient(radius 100%, lightskyblue, cornflowerblue);"
				+ "-fx-background-radius: 20");*/
		
		
		error = new Label("");
		error.setStyle("-fx-font-size: 25");
		error.setTextFill(Color.RED);

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
		GridPane.setColumnSpan(newGame, 6);
		GridPane.setHalignment(newGame, HPos.CENTER);
		GridPane.setValignment(newGame, VPos.BOTTOM);
		GridPane.setConstraints(nickname, 0, 4);
		GridPane.setColumnSpan(nickname, 3);
		GridPane.setHalignment(nickname, HPos.RIGHT);
		GridPane.setValignment(nickname, VPos.CENTER);
		GridPane.setConstraints(nick, 3, 4);
		GridPane.setColumnSpan(nick, 2);
		GridPane.setHalignment(nick, HPos.CENTER);
		GridPane.setValignment(nick, VPos.CENTER);
		this.getChildren().addAll(title,selectChar,
				char1,char2,char3,char4,char5,char6,
				pl1,pl2,pl3,pl4,pl5,pl6,newGame, nickname, nick);
	}

}