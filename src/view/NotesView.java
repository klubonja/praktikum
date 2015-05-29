package view;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class NotesView extends TabPane {
	
	Tab userNotes;
	Tab checklist;
	
	@SuppressWarnings("unused")
	public NotesView(){
		 
		checklist = new Tab("Checklist");
		userNotes = new Tab("User Notes");
		
		this.getTabs().addAll(checklist, userNotes);
		
		Label who = new Label("WHO?");
		Label how = new Label("HOW?");
		Label where = new Label("WHERE?");
		Label green = new Label("Green");
		Label red = new Label("Red");
		Label blue = new Label("Blue");
		Label pink = new Label("Pink");
		Label orange = new Label("Orange");
		Label white = new Label("White");
	}

}
