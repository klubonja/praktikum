package view.label;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

/**
 * Ein Label, bei welchem man ganz einfach die Hintergrundfarbe setzen kann?!?!? 
 * Magic
 * @since ca. 15.06.2015
 * @version 21.07.2015
 * @author Benedikt Mayer
 *
 *
 */
public class KrassesLabel extends Label {

	private BackgroundFill hintergrundfarbe;
	
	public KrassesLabel(){
		super();
	}
	
	public KrassesLabel(String text){
		super(text);
	}
	
	/**
	 * Oh mein Gott man kann direkt die Farbe setzen.
	 * Genial.
	 * @param label das Label, wessen Farbe gesetzt werden soll
	 * @param farbe die Farbe, auf die das Label gesetzt werden soll.
	 */
	public void setBackgroundColor(Label label, Color farbe){
		hintergrundfarbe = new BackgroundFill(farbe, null, null);
		Background hintergrund = new Background(hintergrundfarbe);
		label.setBackground(hintergrund);
	}
	
	
	
}
