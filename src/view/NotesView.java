package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NotesView extends TabPane {
	
	Tab userNotes;
	Tab checklist;
	TextArea notesField;
	
	
	public NotesView(){
		 
		checklist = new Tab("Checklist");
		
		
		userNotes = new Tab("User Notes");
		notesField = new TextArea();
		notesField.setPadding(new Insets(5));
		userNotes.setContent(notesField);
		
		Label who = new Label("WHO?");
		Label how = new Label("HOW?");
		Label where = new Label("WHERE?");
		Label green = new Label("Green");
		Label red = new Label("Red");
		Label blue = new Label("Blue");
		Label purple = new Label("Purple");
		Label yellow = new Label("Yellow");
		Label white = new Label("White");
		Label dagger = new Label("Dagger");
		Label candlestick = new Label("Candlestick");
		Label revolver = new Label("Revolver");
		Label rope = new Label("Rope");
		Label pipe = new Label("Pipe");
		Label spanner = new Label("Spanner");
		Label hall = new Label("Hall");
		Label loungue = new Label("Loungue");
		Label diningroom = new Label("Diningroom");
		Label kitchen = new Label("Kitchen");
		Label ballroom = new Label("Ballroom");
		Label conservatory = new Label("Conservatory");
		Label billiard = new Label("Billiard");
		Label library = new Label("Library");
		Label study = new Label("Study");
		
		VBox firstColumn = new VBox(0.5);
		firstColumn.getChildren().addAll(who, red, yellow, white, green, blue, purple,
				how, dagger, candlestick, revolver, rope, pipe, spanner,
				where, hall, loungue, diningroom, kitchen, ballroom, conservatory, billiard, library, study);
		
		Label space11 = new Label("");
		Label space12 = new Label("");
		Label space13 = new Label("");
		Button button11 = new Button("");
		Button button12 = new Button("");
		Button button13 = new Button("");
		Button button14 = new Button("");
		Button button15 = new Button("");
		Button button16 = new Button("");
		Button button17 = new Button("");
		Button button18 = new Button("");
		Button button19 = new Button("");
		Button button110 = new Button("");
		Button button111 = new Button("");
		Button button112 = new Button("");
		Button button113 = new Button("");
		Button button114 = new Button("");
		Button button115 = new Button("");
		Button button116 = new Button("");
		Button button117 = new Button("");
		Button button118 = new Button("");
		Button button119 = new Button("");
		Button button120 = new Button("");
		Button button121 = new Button("");
		
		VBox secondColumn = new VBox();
		secondColumn.getChildren().addAll(space11, button11, button12, button13, button14, button15, button16,
				space12, button17, button18, button19, button110, button111, button112, space13,
				button113,button114,button115,button116,button117,button118,button119,button120,
				button121);
		
		Label space21 = new Label("");
		Label space22 = new Label("");
		Label space23 = new Label("");
		Button button21 = new Button("");
		Button button22 = new Button("");
		Button button23 = new Button("");
		Button button24 = new Button("");
		Button button25 = new Button("");
		Button button26 = new Button("");
		Button button27 = new Button("");
		Button button28 = new Button("");
		Button button29 = new Button("");
		Button button210 = new Button("");
		Button button211 = new Button("");
		Button button212 = new Button("");
		Button button213 = new Button("");
		Button button214 = new Button("");
		Button button215 = new Button("");
		Button button216 = new Button("");
		Button button217 = new Button("");
		Button button218 = new Button("");
		Button button219 = new Button("");
		Button button220 = new Button("");
		Button button221 = new Button("");
		
		VBox thirdColumn = new VBox();
		thirdColumn.getChildren().addAll(space21, button21, button22, button23, button24, button25, button26,
				space22, button27, button28, button29, button210, button211, button212, space23,
				button213,button214,button215,button216,button217,button218,button219,button220,
				button221);
		
		Label space31 = new Label("");
		Label space32 = new Label("");
		Label space33 = new Label("");
		Button button31 = new Button("");
		Button button32 = new Button("");
		Button button33 = new Button("");
		Button button34 = new Button("");
		Button button35 = new Button("");
		Button button36 = new Button("");
		Button button37 = new Button("");
		Button button38 = new Button("");
		Button button39 = new Button("");
		Button button310 = new Button("");
		Button button311 = new Button("");
		Button button312 = new Button("");
		Button button313 = new Button("");
		Button button314 = new Button("");
		Button button315 = new Button("");
		Button button316 = new Button("");
		Button button317 = new Button("");
		Button button318 = new Button("");
		Button button319 = new Button("");
		Button button320 = new Button("");
		Button button321 = new Button("");
		
		VBox fourthColumn = new VBox();
		fourthColumn.getChildren().addAll(space31, button31, button32, button33, button34, button35, button36,
				space32, button37, button38, button39, button310, button311, button312, space33,
				button313,button314,button315,button316,button317,button318,button319,button320,
				button321);
		
		Label space41 = new Label("");
		Label space42 = new Label("");
		Label space43 = new Label("");
		TextField txt1 = new TextField();
		TextField txt2 = new TextField();
		TextField txt3 = new TextField();
		TextField txt4 = new TextField();
		TextField txt5 = new TextField();
		TextField txt6 = new TextField();
		TextField txt7 = new TextField();
		TextField txt8 = new TextField();
		TextField txt9 = new TextField();
		TextField txt10 = new TextField();
		TextField txt11= new TextField();
		TextField txt12 = new TextField();
		TextField txt13 = new TextField();
		TextField txt14 = new TextField();
		TextField txt15 = new TextField();
		TextField txt16 = new TextField();
		TextField txt17 = new TextField();
		TextField txt18 = new TextField();
		TextField txt19 = new TextField();
		TextField txt20 = new TextField();
		TextField txt21 = new TextField();
		
		VBox fifthColumn = new VBox(0.2);
		fifthColumn.getChildren().addAll(space41, txt1, txt2, txt3, txt4, txt5, txt6, space42,
				txt7, txt8, txt9, txt10, txt11, txt12, space43, txt13, txt14, txt15, txt16, txt17,
				txt18, txt19, txt20, txt21);
		
		
		HBox checklistFrame = new HBox(1);
		checklistFrame.getChildren().addAll(firstColumn, secondColumn, thirdColumn, fourthColumn, fifthColumn);
		
		checklist.setContent(checklistFrame);
		
		
		this.getTabs().addAll(checklist, userNotes);
		
		
	}

}
