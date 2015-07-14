package view;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class KrassesLabel extends Label {

	private BackgroundFill hintergrundfarbe;
	
	public KrassesLabel(){
		super();
	}
	
	public KrassesLabel(String text){
		super(text);
	}
	
	public void setBackgroundColor(Label label, Color farbe){
		hintergrundfarbe = new BackgroundFill(farbe, null, null);
		Background hintergrund = new Background(hintergrundfarbe);
		label.setBackground(hintergrund);
	}
	
	
	
}
