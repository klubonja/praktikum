package view;

import java.util.LinkedList;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class GameFrameView extends GridPane{
	
	MenuBar menu;
	Menu options;
	Menu help;
	MenuItem startGame;
	GridPane notizien;
	GridPane diceFrame;
	GridPane dice;
	Button[][] buttons;
	LinkedList<Label> labels = new LinkedList<Label>();
	Button rollDice;
	TextArea chatArea;
	HBox hBox0, hBox1, hBox2, hBox3;
    VBox vBox1, vBox2;
	Button roll, secretPassage, suspect;
	Label hint1,hint2,hint3;
	
	//Pictures Bottom (Portrait,Cards)
	final Image portrait1 = new Image("/ColMustardDummy2.jpg");
	final Image card1 = new Image("/fragezeichenDummy.jpg");
	final Image card2 = new Image("/fragezeichenDummy.jpg");
	final Image card3 = new Image("/fragezeichenDummy.jpg");
	final Image blank1 = new Image("/kartenDummy.jpg");
	final Image blank2 = new Image("/kartenDummy.jpg");
	final Image blank3 = new Image("/kartenDummy.jpg");
	
	//Pictures dice
	final Image dice1 = new Image("http://dobbelsteen.virtuworld.net/img/1c.gif");
	final Image dice2 = new Image("http://dobbelsteen.virtuworld.net/img/2c.gif");
	final Image dice3 = new Image("http://dobbelsteen.virtuworld.net/img/3c.gif");
	final Image dice4 = new Image("http://dobbelsteen.virtuworld.net/img/4c.gif");
	final Image dice5 = new Image("http://dobbelsteen.virtuworld.net/img/5c.gif");
	final Image dice6 = new Image("http://dobbelsteen.virtuworld.net/img/6c.gif");
	
	ImageView d1 = new ImageView(dice1);
	ImageView d2 = new ImageView(dice1);
	ImageView portrait = new ImageView(portrait1);
	ImageView card_1 = new ImageView(card1);
	ImageView card_2 = new ImageView(card2);
	ImageView card_3 = new ImageView(card3);
	ImageView blank_1 = new ImageView(blank1);
	ImageView blank_2 = new ImageView(blank2);
	ImageView blank_3 = new ImageView(blank3);
	
	public GameFrameView(){
		
		//size of Columns and Rows
		this.getRowConstraints().add(new RowConstraints(25));
		this.getRowConstraints().add(new RowConstraints(600));
		this.getRowConstraints().add(new RowConstraints(200));
		this.getColumnConstraints().add(new ColumnConstraints(600));
		this.getColumnConstraints().add(new ColumnConstraints(600));
		
		menu = new MenuBar();
		menu.setPrefWidth(1200);
		menu.setMaxWidth(1200);
		options = new Menu("Options");
		help = new Menu("Help");
		menu.getMenus().addAll(options, help);
		
		notizien = new GridPane();
		HBox row1 = new HBox(5);
		Button b1 = new Button("B1");
		row1.getChildren().add(b1);
		notizien.getChildren().add(row1);
		
		Label who = new Label("WHO?");
		Label how = new Label("HOW?");
		Label where = new Label("WHERE?");
		Label green = new Label("Green");
		Label red = new Label("Red");
		Label blue = new Label("Blue");
		Label pink = new Label("Pink");
		Label orange = new Label("Orange");
		Label white = new Label("White");
		
		
		//chat area
		VBox chat = new VBox(3);
		Label chatLabel = new Label("Chat");
		chatArea = new TextArea();
		chatArea.setEditable(false);
		HBox chatBox = new HBox(3);
		Button chatSend = new Button("Send");
		TextField chatMsg = new TextField();
		chatMsg.setPrefWidth(200);
		chatMsg.setMaxWidth(200);
		chatBox.getChildren().addAll(chatMsg, chatSend);
		chat.getChildren().addAll(chatLabel,chatArea,chatBox);
		
		//dice area
		dice = new GridPane();
		dice.setGridLinesVisible(true);
		dice.setVgap(10);
		dice.setHgap(10);
		dice.getRowConstraints().add(new RowConstraints(150));
		dice.getRowConstraints().add(new RowConstraints(150));
		dice.getColumnConstraints().add(new ColumnConstraints(150));
		dice.getColumnConstraints().add(new ColumnConstraints(150));
		rollDice = new Button("Roll!");
		GridPane.setConstraints(rollDice,0,0);
		GridPane.setColumnSpan(rollDice, 2);
		GridPane.setHalignment(rollDice, HPos.CENTER);
		GridPane.setValignment(rollDice, VPos.BOTTOM);
		GridPane.setConstraints(d1,0,0);
		d1.setFitHeight(100);
		d1.setFitWidth(100);
		GridPane.setHalignment(d1, HPos.CENTER);
		GridPane.setValignment(d1, VPos.CENTER);
		GridPane.setConstraints(d2,1,0);
		d2.setFitHeight(100);
		d2.setFitWidth(100);
		GridPane.setHalignment(d2, HPos.CENTER);
		GridPane.setValignment(d2, VPos.CENTER);
		dice.getChildren().addAll(d1, d2, rollDice);
		
		
		
		//left area that contains chat and dices
		GridPane left = new GridPane();
		left.setGridLinesVisible(true);
		left.getRowConstraints().add(new RowConstraints(300));
		left.getColumnConstraints().add(new ColumnConstraints(300));
		GridPane.setConstraints(chat,1,0);
		GridPane.setConstraints(dice,0,0);
		left.getChildren().addAll(chat,dice);
		
		
		//Bottom View Buttons
		roll = new Button("Roll!");
		roll.setPrefWidth(120);
		roll.setMaxWidth(120);
		roll.setStyle("-fx-font-size: 15 ;"
				+ "-fx-background-color: radial-gradient(radius 75%, red, lightgrey);");
		
		suspect = new Button("Suspect!");
		suspect.setPrefWidth(120);
		suspect.setMaxWidth(120);
		suspect.setStyle("-fx-font-size: 15 ;"
				+ "-fx-background-color: radial-gradient(radius 75%, red, lightgrey);");

		secretPassage = new Button("Secret Passage");
		secretPassage.setPrefWidth(120);
		secretPassage.setMaxWidth(120);
		secretPassage.setStyle("-fx-font-size: 15 ;"
				+ "-fx-background-color: radial-gradient(radius 75%, red, lightgrey);");


		//Bottom View
		//Porträt -- linker Block
		HBox hBox1 = new HBox();
		hBox1.setPrefWidth(180);
		hBox1.setMaxWidth(180);
		hBox1.setPrefHeight(200);
		hBox1.setMaxHeight(200);
		hBox1.getChildren().add(portrait);
		
		//die unteren 3 Karten
		HBox hBox3 = new HBox();
		hBox3.setPrefWidth(300);
		hBox3.setMaxWidth(300);
		hBox3.setPrefHeight(100);
		hBox3.setMaxHeight(100);
		hBox3.getChildren().addAll(blank_1,blank_2,blank_3);
		hBox3.setSpacing(20);
		hBox3.setAlignment(Pos.TOP_CENTER);
		
		//die oberen 3 Karten
		HBox hBox2 = new HBox();
		hBox2.setPrefWidth(300);
		hBox2.setMaxWidth(300);
		hBox2.setPrefHeight(100);
		hBox2.setMaxHeight(100);
		hBox2.getChildren().addAll(card_1,card_2,card_3);
		hBox2.setSpacing(20);
		hBox2.setAlignment(Pos.TOP_CENTER);
		
		//der mittlere Block
		VBox vBox2 = new VBox();
		vBox2.getChildren().addAll(hBox2,hBox3);
		
		//der rechte Block
		VBox vBox1 = new VBox();
		vBox1.setPrefWidth(120);
		vBox1.getChildren().addAll(roll, suspect, secretPassage);
		vBox1.setSpacing(50);
		vBox1.setAlignment(Pos.TOP_LEFT);
		
		//der ganze Block unter dem Spielfeld
		HBox hBox0 = new HBox();
		hBox0.setPrefWidth(600);
		hBox0.setMaxWidth(600);
		hBox0.setPrefHeight(200);
		hBox0.setMaxHeight(200);
		hBox0.getChildren().addAll(hBox1,vBox2, vBox1);
	
		
		
		GridPane.setConstraints(hBox0, 0, 2);
		GridPane.setConstraints(menu,0,0);
		GridPane.setColumnSpan(menu, 2);
		GridPane.setValignment(menu, VPos.TOP);
		//GridPane.setConstraints(notizien,1,1);
		GridPane.setConstraints(left, 1,1);
		this.getChildren().addAll(menu, hBox0, notizien, left);
		this.setGridLinesVisible(true);
		
		
	}
	public void createButtons(){
		for(int i=0; i<132; i++){
			
		}
	}

}
