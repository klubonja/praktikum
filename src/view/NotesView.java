package view;

import java.util.LinkedList;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import cluedoNetworkLayer.CluedoGameClient;

/**
 * 
 * @author YinYanYolos
 *
 * NotesView implemented as a TabPane. 
 * The class responsible for the user notes.
 */
public class NotesView extends TabPane {
	
	private CluedoGameClient client;
	
	private Tab userNotes;
	private Tab checklist;
	private TextArea notesField;
	private GridPane layoutGrid;
	
	Label who;
	Label how;
	Label where;
	Label green;
	Label red;
	Label blue;
	Label purple;
	Label yellow;
	Label white;
	Label dagger;
	Label candlestick;
	Label revolver;
	Label rope;
	Label pipe;
	Label spanner;
	Label hall;
	Label lounge;
	Label diningroom;
	Label kitchen;
	Label ballroom;
	Label conservatory;
	Label billiard;
	Label library;
	Label study;
	
	Button button1;
	Button button2;
	Button button3;
	Button button4;
	Button button5;
	Button button6;
	Button button7;
	Button button8;
	Button button9;
	Button button10;
	Button button11;
	Button button12;
	Button button13;
	Button button14;
	Button button15;
	Button button16;
	Button button17;
	Button button18;
	Button button19;
	Button button20;
	Button button21;
	
	TextField txt1;
	TextField txt2;
	TextField txt3;
	TextField txt4;
	TextField txt5;
	TextField txt6;
	TextField txt7;
	TextField txt8;
	TextField txt9;
	TextField txt10;
	TextField txt11;
	TextField txt12;
	TextField txt13;
	TextField txt14;
	TextField txt15;
	TextField txt16;
	TextField txt17;
	TextField txt18;
	TextField txt19;
	TextField txt20;
	TextField txt21;
	
	private LinkedList<Label> labels;
	private LinkedList<Button> buttons;
	private LinkedList<TextField> textFlds;
	
	private final Stop[] stops = new Stop[] { new Stop(0, Color.LAVENDER), new Stop(1, Color.GHOSTWHITE)};
	private final RadialGradient rg1 = new RadialGradient(1, 0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
	
	private final BackgroundFill defaultFill = new BackgroundFill(rg1, new CornerRadii(1), new Insets(0.2));
	private final Background defaultButton = new Background(defaultFill);
	private final BorderStroke defaultStroke = new BorderStroke(Color.DARKSLATEGREY, BorderStrokeStyle.SOLID, 
			new CornerRadii(4), new BorderWidths(0.5));
	private final Border defaultBorder = new Border(defaultStroke);
	
	private final BackgroundFill	defaulttxt = new BackgroundFill(Color.GHOSTWHITE, new CornerRadii(1), new Insets(0.2));
	private final Background defaultTxt = new Background(defaulttxt);
	
	
	
	public NotesView(CluedoGameClient client){
		
		this.client = client;
		 
		this.checklist = new Tab("Checklist");
		
		this.userNotes = new Tab("User Notes");
		this.notesField = new TextArea();
		this.notesField.setPadding(new Insets(5));
		this.notesField.setPromptText("Use it with caution!");
		this.userNotes.setContent(notesField);
		
		// Crates the Labels for every card.
		this.who = new Label("WHO?");
		this.how = new Label("HOW?");
		this.where = new Label("WHERE?");
		this.green = new Label("Green");
		this.red = new Label("Gloria");
		this.blue = new Label("Bloom");
		this.purple = new Label("Porz");
		this.yellow = new Label("Gatow");
		this.white = new Label("Weiss");
		
		this.dagger = new Label("Dagger");
		this.candlestick = new Label("Candlestick");
		this.revolver = new Label("Revolver");
		this.rope = new Label("Rope");
		this.pipe = new Label("Pipe");
		this.spanner = new Label("Spanner");
		
		this.hall = new Label("Hall");
		this.lounge = new Label("Loungue");
		this.diningroom = new Label("Diningroom");
		this.kitchen = new Label("Kitchen");
		this.ballroom = new Label("Ballroom");
		this.conservatory = new Label("Conservatory");
		this.billiard = new Label("Billiard");
		this.library = new Label("Library");
		this.study = new Label("Study");
		
		// Creates a list and adds the Labels in it.
		labels = new LinkedList<Label>();
		labels.add(who);
		labels.add(where);
		labels.add(how);
		labels.add(red);
		labels.add(yellow);
		labels.add(white);
		labels.add(green);
		labels.add(blue);
		labels.add(purple);
		labels.add(dagger);
		labels.add(candlestick);
		labels.add(revolver);
		labels.add(rope);
		labels.add(pipe);
		labels.add(spanner);
		labels.add(hall);
		labels.add(lounge);
		labels.add(diningroom);
		labels.add(kitchen);
		labels.add(ballroom);
		labels.add(conservatory);
		labels.add(billiard);
		labels.add(library);
		labels.add(study);
		
		// Crates the Buttons for every card.
		this.button1 = new Button();
		this.button2 = new Button();
		this.button3 = new Button();
		this.button4 = new Button();
		this.button5 = new Button("");
		this.button6 = new Button("");
		this.button7 = new Button("");
		this.button8 = new Button("");
		this.button9 = new Button("");
		this.button10 = new Button("");
		this.button11 = new Button("");
		this.button12 = new Button("");
		this.button13 = new Button("");
		this.button14 = new Button("");
		this.button15 = new Button("");
		this.button16 = new Button("");
		this.button17 = new Button("");
		this.button18 = new Button("");
		this.button19 = new Button("");
		this.button20 = new Button("");
		this.button21 = new Button("");
		
		// Creates a list and adds the Buttons in it.
		buttons = new LinkedList<Button>();
		buttons.add(button1);
		buttons.add(button2);
		buttons.add(button3);
		buttons.add(button4);
		buttons.add(button5);
		buttons.add(button6);
		buttons.add(button7);
		buttons.add(button8);
		buttons.add(button9);
		buttons.add(button10);
		buttons.add(button11);
		buttons.add(button12);
		buttons.add(button13);
		buttons.add(button14);
		buttons.add(button15);
		buttons.add(button16);
		buttons.add(button17);
		buttons.add(button18);
		buttons.add(button19);
		buttons.add(button20);
		buttons.add(button21);
		
		// Crates the textFields for every card.
		this.txt1 = new TextField();
		this.txt2 = new TextField();
		this.txt3 = new TextField();
		this.txt4 = new TextField();
		this.txt5 = new TextField();
		this.txt6 = new TextField();
		this.txt7 = new TextField();
		this.txt8 = new TextField();
		this.txt9 = new TextField();
		this.txt10 = new TextField();
		this.txt11= new TextField();
		this.txt12 = new TextField();
		this.txt13 = new TextField();
		this.txt14 = new TextField();
		this.txt15 = new TextField();
		this.txt16 = new TextField();
		this.txt17 = new TextField();
		this.txt18 = new TextField();
		this.txt19 = new TextField();
		this.txt20 = new TextField();
		this.txt21 = new TextField();
		
		// Creates a list and adds the textFields in it.
		textFlds = new LinkedList<TextField>();
		textFlds.add(txt1);
		textFlds.add(txt2);
		textFlds.add(txt3);
		textFlds.add(txt4);
		textFlds.add(txt5);
		textFlds.add(txt6);
		textFlds.add(txt7);
		textFlds.add(txt8);
		textFlds.add(txt9);
		textFlds.add(txt10);
		textFlds.add(txt11);
		textFlds.add(txt12);
		textFlds.add(txt13);
		textFlds.add(txt14);
		textFlds.add(txt15);
		textFlds.add(txt16);
		textFlds.add(txt17);
		textFlds.add(txt18);
		textFlds.add(txt19);
		textFlds.add(txt20);
		textFlds.add(txt21);
		
		// Creates the GridPane with the measures for every row.
		this.layoutGrid = new GridPane();
		layoutGrid.setPadding(new Insets(2));
		layoutGrid.setVgap(0.4);
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getRowConstraints().add(new RowConstraints(20));
		layoutGrid.getColumnConstraints().add(new ColumnConstraints(80));
		layoutGrid.getColumnConstraints().add(new ColumnConstraints(40));
		layoutGrid.getColumnConstraints().add(new ColumnConstraints(175));
		
		//Sets the positions of the labels, buttons and textFields in the GridPane.
		GridPane.setConstraints(who, 0, 0);
		GridPane.setColumnSpan(who, 3);
		GridPane.setHalignment(who, HPos.CENTER);
		GridPane.setConstraints(green, 0, 1);
		GridPane.setConstraints(button1, 1, 1);
		GridPane.setConstraints(txt1, 2, 1);
		GridPane.setConstraints(blue, 0, 2);
		GridPane.setConstraints(button2, 1, 2);
		GridPane.setConstraints(txt2, 2, 2);
		GridPane.setConstraints(red, 0, 3);
		GridPane.setConstraints(button3, 1, 3);
		GridPane.setConstraints(txt3, 2, 3);
		GridPane.setConstraints(purple, 0, 4);
		GridPane.setConstraints(button4, 1, 4);
		GridPane.setConstraints(txt4, 2, 4);
		GridPane.setConstraints(white, 0, 5);
		GridPane.setConstraints(button5, 1, 5);
		GridPane.setConstraints(txt5, 2, 5);
		GridPane.setConstraints(yellow, 0, 6);
		GridPane.setConstraints(button6, 1, 6);
		GridPane.setConstraints(txt6, 2, 6);
		GridPane.setConstraints(how, 0, 7);
		GridPane.setColumnSpan(how, 3);
		GridPane.setHalignment(how, HPos.CENTER);
		GridPane.setConstraints(dagger, 0, 8);
		GridPane.setConstraints(button7, 1, 8);
		GridPane.setConstraints(txt7, 2, 8);
		GridPane.setConstraints(candlestick, 0, 9);
		GridPane.setConstraints(button8, 1, 9);
		GridPane.setConstraints(txt8, 2, 9);
		GridPane.setConstraints(revolver, 0, 10);
		GridPane.setConstraints(button9, 1, 10);
		GridPane.setConstraints(txt9, 2, 10);
		GridPane.setConstraints(rope, 0, 11);
		GridPane.setConstraints(button10, 1, 11);
		GridPane.setConstraints(txt10, 2, 11);
		GridPane.setConstraints(pipe, 0, 12);
		GridPane.setConstraints(button11, 1, 12);
		GridPane.setConstraints(txt11, 2, 12);
		GridPane.setConstraints(spanner, 0, 13);
		GridPane.setConstraints(button12, 1, 13);
		GridPane.setConstraints(txt12, 2, 13);
		GridPane.setConstraints(where, 0, 14);
		GridPane.setColumnSpan(where, 3);
		GridPane.setHalignment(where, HPos.CENTER);
		GridPane.setConstraints(hall, 0, 15);
		GridPane.setConstraints(button13, 1, 15);
		GridPane.setConstraints(txt13, 2, 15);
		GridPane.setConstraints(lounge, 0, 16);
		GridPane.setConstraints(button14, 1, 16);
		GridPane.setConstraints(txt14, 2, 16);
		GridPane.setConstraints(diningroom, 0, 17);
		GridPane.setConstraints(button15, 1, 17);
		GridPane.setConstraints(txt15, 2, 17);
		GridPane.setConstraints(kitchen, 0, 18);
		GridPane.setConstraints(button16, 1, 18);
		GridPane.setConstraints(txt16, 2, 18);
		GridPane.setConstraints(ballroom, 0, 19);
		GridPane.setConstraints(button17, 1, 19);
		GridPane.setConstraints(txt17, 2, 19);
		GridPane.setConstraints(conservatory, 0, 20);
		GridPane.setConstraints(button18, 1, 20);
		GridPane.setConstraints(txt18, 2, 20);
		GridPane.setConstraints(billiard, 0, 21);
		GridPane.setConstraints(button19, 1, 21);
		GridPane.setConstraints(txt19, 2, 21);
		GridPane.setConstraints(library, 0, 22);
		GridPane.setConstraints(button20, 1, 22);
		GridPane.setConstraints(txt20, 2, 22);
		GridPane.setConstraints(study, 0, 23);
		GridPane.setConstraints(button21, 1, 23);
		GridPane.setConstraints(txt21, 2, 23);
		
		editSize();
		addtoGUI();
		
		this.checklist.setContent(layoutGrid);
		this.getTabs().addAll(this.checklist, this.userNotes);
		
		
	}
	
	/**
	 * Edits the size of the Buttons and TextFields to fit the Grid.
	 */
	public void editSize(){
		
		for(Button button : buttons){
			button.setMaxSize(39, 19);
			button.setMinSize(39, 19);
			button.setPrefSize(39, 19);
			button.setBackground(defaultButton);
			button.setBorder(defaultBorder);
			
		}
		
		for(TextField txtField : textFlds){
			txtField.setMaxHeight(20);
			txtField.setMinHeight(20);
			txtField.setPrefHeight(20);
			txtField.setOpacity(0.7);
			txtField.setBackground(defaultTxt);
			txtField.setBorder(defaultBorder);
		}
		
	}
	
	
	/**
	 * Adds the Buttons, TextFields and Labels to the GridPane.
	 */
	public void addtoGUI(){
		for(Label label : labels){
			layoutGrid.getChildren().add(label);
		}
		
		for(Button button : buttons){
			layoutGrid.getChildren().add(button);
		}
		
		for(TextField txtField : textFlds){
			layoutGrid.getChildren().add(txtField);
		}
		
	}

	
	
	// Getters and Setter Methods.
	 
	public Tab getUserNotes() {
		return userNotes;
	}


	public void setUserNotes(Tab userNotes) {
		this.userNotes = userNotes;
	}


	public Tab getChecklist() {
		return checklist;
	}


	public void setChecklist(Tab checklist) {
		this.checklist = checklist;
	}


	public TextArea getNotesField() {
		return notesField;
	}


	public void setNotesField(TextArea notesField) {
		this.notesField = notesField;
	}


	public GridPane getLayoutGrid() {
		return layoutGrid;
	}


	public void setLayoutGrid(GridPane layoutGrid) {
		this.layoutGrid = layoutGrid;
	}


	public Label getWho() {
		return who;
	}


	public void setWho(Label who) {
		this.who = who;
	}


	public Label getHow() {
		return how;
	}


	public void setHow(Label how) {
		this.how = how;
	}


	public Label getWhere() {
		return where;
	}


	public void setWhere(Label where) {
		this.where = where;
	}


	public Label getGreen() {
		return green;
	}


	public void setGreen(Label green) {
		this.green = green;
	}


	public Label getRed() {
		return red;
	}


	public void setRed(Label red) {
		this.red = red;
	}


	public Label getBlue() {
		return blue;
	}


	public void setBlue(Label blue) {
		this.blue = blue;
	}


	public Label getPurple() {
		return purple;
	}


	public void setPurple(Label purple) {
		this.purple = purple;
	}


	public Label getYellow() {
		return yellow;
	}


	public void setYellow(Label yellow) {
		this.yellow = yellow;
	}


	public Label getWhite() {
		return white;
	}


	public void setWhite(Label white) {
		this.white = white;
	}


	public Label getDagger() {
		return dagger;
	}


	public void setDagger(Label dagger) {
		this.dagger = dagger;
	}


	public Label getCandlestick() {
		return candlestick;
	}


	public void setCandlestick(Label candlestick) {
		this.candlestick = candlestick;
	}


	public Label getRevolver() {
		return revolver;
	}


	public void setRevolver(Label revolver) {
		this.revolver = revolver;
	}


	public Label getRope() {
		return rope;
	}


	public void setRope(Label rope) {
		this.rope = rope;
	}


	public Label getPipe() {
		return pipe;
	}


	public void setPipe(Label pipe) {
		this.pipe = pipe;
	}


	public Label getSpanner() {
		return spanner;
	}


	public void setSpanner(Label spanner) {
		this.spanner = spanner;
	}


	public Label getHall() {
		return hall;
	}


	public void setHall(Label hall) {
		this.hall = hall;
	}


	public Label getLoungue() {
		return lounge;
	}


	public void setLoungue(Label loungue) {
		this.lounge = loungue;
	}


	public Label getDiningroom() {
		return diningroom;
	}


	public void setDiningroom(Label diningroom) {
		this.diningroom = diningroom;
	}


	public Label getKitchen() {
		return kitchen;
	}


	public void setKitchen(Label kitchen) {
		this.kitchen = kitchen;
	}


	public Label getBallroom() {
		return ballroom;
	}


	public void setBallroom(Label ballroom) {
		this.ballroom = ballroom;
	}


	public Label getConservatory() {
		return conservatory;
	}


	public void setConservatory(Label conservatory) {
		this.conservatory = conservatory;
	}


	public Label getBilliard() {
		return billiard;
	}


	public void setBilliard(Label billiard) {
		this.billiard = billiard;
	}


	public Label getLibrary() {
		return library;
	}


	public void setLibrary(Label library) {
		this.library = library;
	}


	public Label getStudy() {
		return study;
	}


	public void setStudy(Label study) {
		this.study = study;
	}


	public Button getButton1() {
		return button1;
	}


	public void setButton1(Button button1) {
		this.button1 = button1;
	}


	public Button getButton2() {
		return button2;
	}


	public void setButton2(Button button2) {
		this.button2 = button2;
	}


	public Button getButton3() {
		return button3;
	}


	public void setButton3(Button button3) {
		this.button3 = button3;
	}


	public Button getButton4() {
		return button4;
	}


	public void setButton4(Button button4) {
		this.button4 = button4;
	}


	public Button getButton5() {
		return button5;
	}


	public void setButton5(Button button5) {
		this.button5 = button5;
	}


	public Button getButton6() {
		return button6;
	}


	public void setButton6(Button button6) {
		this.button6 = button6;
	}


	public Button getButton7() {
		return button7;
	}


	public void setButton7(Button button7) {
		this.button7 = button7;
	}


	public Button getButton8() {
		return button8;
	}


	public void setButton8(Button button8) {
		this.button8 = button8;
	}


	public Button getButton9() {
		return button9;
	}


	public void setButton9(Button button9) {
		this.button9 = button9;
	}


	public Button getButton10() {
		return button10;
	}


	public void setButton10(Button button10) {
		this.button10 = button10;
	}


	public Button getButton11() {
		return button11;
	}


	public void setButton11(Button button11) {
		this.button11 = button11;
	}


	public Button getButton12() {
		return button12;
	}


	public void setButton12(Button button12) {
		this.button12 = button12;
	}


	public Button getButton13() {
		return button13;
	}


	public void setButton13(Button button13) {
		this.button13 = button13;
	}


	public Button getButton14() {
		return button14;
	}


	public void setButton14(Button button14) {
		this.button14 = button14;
	}


	public Button getButton15() {
		return button15;
	}


	public void setButton15(Button button15) {
		this.button15 = button15;
	}


	public Button getButton16() {
		return button16;
	}


	public void setButton16(Button button16) {
		this.button16 = button16;
	}


	public Button getButton17() {
		return button17;
	}


	public void setButton17(Button button17) {
		this.button17 = button17;
	}


	public Button getButton18() {
		return button18;
	}


	public void setButton18(Button button18) {
		this.button18 = button18;
	}


	public Button getButton19() {
		return button19;
	}


	public void setButton19(Button button19) {
		this.button19 = button19;
	}


	public Button getButton20() {
		return button20;
	}


	public void setButton20(Button button20) {
		this.button20 = button20;
	}


	public Button getButton21() {
		return button21;
	}


	public void setButton21(Button button21) {
		this.button21 = button21;
	}


	public TextField getTxt1() {
		return txt1;
	}


	public void setTxt1(TextField txt1) {
		this.txt1 = txt1;
	}


	public TextField getTxt2() {
		return txt2;
	}


	public void setTxt2(TextField txt2) {
		this.txt2 = txt2;
	}


	public TextField getTxt3() {
		return txt3;
	}


	public void setTxt3(TextField txt3) {
		this.txt3 = txt3;
	}


	public TextField getTxt4() {
		return txt4;
	}


	public void setTxt4(TextField txt4) {
		this.txt4 = txt4;
	}


	public TextField getTxt5() {
		return txt5;
	}


	public void setTxt5(TextField txt5) {
		this.txt5 = txt5;
	}


	public TextField getTxt6() {
		return txt6;
	}


	public void setTxt6(TextField txt6) {
		this.txt6 = txt6;
	}


	public TextField getTxt7() {
		return txt7;
	}


	public void setTxt7(TextField txt7) {
		this.txt7 = txt7;
	}


	public TextField getTxt8() {
		return txt8;
	}


	public void setTxt8(TextField txt8) {
		this.txt8 = txt8;
	}


	public TextField getTxt9() {
		return txt9;
	}


	public void setTxt9(TextField txt9) {
		this.txt9 = txt9;
	}


	public TextField getTxt10() {
		return txt10;
	}


	public void setTxt10(TextField txt10) {
		this.txt10 = txt10;
	}


	public TextField getTxt11() {
		return txt11;
	}


	public void setTxt11(TextField txt11) {
		this.txt11 = txt11;
	}


	public TextField getTxt12() {
		return txt12;
	}


	public void setTxt12(TextField txt12) {
		this.txt12 = txt12;
	}


	public TextField getTxt13() {
		return txt13;
	}


	public void setTxt13(TextField txt13) {
		this.txt13 = txt13;
	}


	public TextField getTxt14() {
		return txt14;
	}


	public void setTxt14(TextField txt14) {
		this.txt14 = txt14;
	}


	public TextField getTxt15() {
		return txt15;
	}


	public void setTxt15(TextField txt15) {
		this.txt15 = txt15;
	}


	public TextField getTxt16() {
		return txt16;
	}


	public void setTxt16(TextField txt16) {
		this.txt16 = txt16;
	}


	public TextField getTxt17() {
		return txt17;
	}


	public void setTxt17(TextField txt17) {
		this.txt17 = txt17;
	}


	public TextField getTxt18() {
		return txt18;
	}


	public void setTxt18(TextField txt18) {
		this.txt18 = txt18;
	}


	public TextField getTxt19() {
		return txt19;
	}


	public void setTxt19(TextField txt19) {
		this.txt19 = txt19;
	}


	public TextField getTxt20() {
		return txt20;
	}


	public void setTxt20(TextField txt20) {
		this.txt20 = txt20;
	}


	public TextField getTxt21() {
		return txt21;
	}


	public void setTxt21(TextField txt21) {
		this.txt21 = txt21;
	}


	public LinkedList<Label> getLabels() {
		return labels;
	}


	public void setLabels(LinkedList<Label> labels) {
		this.labels = labels;
	}


	public LinkedList<Button> getButtons() {
		return buttons;
	}


	public void setButtons(LinkedList<Button> buttons) {
		this.buttons = buttons;
	}


	public LinkedList<TextField> getTextfld() {
		return textFlds;
	}


	public void setTextfld(LinkedList<TextField> textfld) {
		this.textFlds = textfld;
	}

	public LinkedList<TextField> getTextFlds() {
		return textFlds;
	}

	public void setTextFlds(LinkedList<TextField> textFlds) {
		this.textFlds = textFlds;
	}

	public BackgroundFill getDefaultFill() {
		return defaultFill;
	}

	public Background getDefaultButton() {
		return defaultButton;
	}

	public BorderStroke getDefaultStroke() {
		return defaultStroke;
	}

	public Border getDefaultBorder() {
		return defaultBorder;
	}

	public BackgroundFill getDefaulttxt() {
		return defaulttxt;
	}

	public Background getDefaultTxt() {
		return defaultTxt;
	}

	public CluedoGameClient getClient() {
		return client;
	}

	public void setClient(CluedoGameClient client) {
		this.client = client;
	}
	
	

}
