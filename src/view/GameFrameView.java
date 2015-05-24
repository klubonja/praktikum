package view;

import java.util.LinkedList;

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
	Button[][] buttons;
	LinkedList<Label> labels = new LinkedList<Label>();
	Button rollDice;
	
	final Image dice1 = new Image("http://dobbelsteen.virtuworld.net/img/1c.gif");
	final Image dice2 = new Image("http://dobbelsteen.virtuworld.net/img/2c.gif");
	final Image dice3 = new Image("http://dobbelsteen.virtuworld.net/img/3c.gif");
	final Image dice4 = new Image("http://dobbelsteen.virtuworld.net/img/4c.gif");
	final Image dice5 = new Image("http://dobbelsteen.virtuworld.net/img/5c.gif");
	final Image dice6 = new Image("http://dobbelsteen.virtuworld.net/img/6c.gif");
	
	ImageView d1 = new ImageView(dice1);
	ImageView d2 = new ImageView(dice1);
	ImageView d3 = new ImageView(dice1);
	ImageView d4 = new ImageView(dice1);
	ImageView d5 = new ImageView(dice1);
	ImageView d6 = new ImageView(dice1);
	
	public GameFrameView(){
		
		//size of Columns and Rows
		this.getRowConstraints().add(new RowConstraints(25));
		this.getRowConstraints().add(new RowConstraints(600));
		this.getRowConstraints().add(new RowConstraints(100));
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
		TextArea chatArea = new TextArea();
		chatArea.setEditable(false);
		HBox chatBox = new HBox(3);
		Button chatSend = new Button("Send");
		TextField chatMsg = new TextField();
		chatMsg.setPrefWidth(200);
		chatMsg.setMaxWidth(200);
		chatBox.getChildren().addAll(chatMsg, chatSend);
		chat.getChildren().addAll(chatLabel,chatArea,chatBox);
		
		//dice area
		GridPane dice = new GridPane();
		dice.setGridLinesVisible(true);
		dice.setVgap(10);
		dice.setHgap(10);
		dice.getRowConstraints().add(new RowConstraints(150));
		dice.getRowConstraints().add(new RowConstraints(150));
		dice.getColumnConstraints().add(new ColumnConstraints(150));
		dice.getColumnConstraints().add(new ColumnConstraints(150));
		rollDice = new Button("Roll!");
		GridPane.setConstraints(rollDice,0,1);
		GridPane.setColumnSpan(rollDice, 2);
		GridPane.setHalignment(rollDice, HPos.CENTER);
		GridPane.setValignment(rollDice, VPos.CENTER);
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
		
		
		
		GridPane.setConstraints(menu,0,0);
		GridPane.setColumnSpan(menu, 2);
		GridPane.setValignment(menu, VPos.TOP);
		//GridPane.setConstraints(notizien,1,1);
		GridPane.setConstraints(left, 1,1);
		this.getChildren().addAll(menu, notizien, left);
		this.setGridLinesVisible(true);
		
		}
	
	public void createButtons(){
		for(int i=0; i<132; i++){
			
		}
	}

}
