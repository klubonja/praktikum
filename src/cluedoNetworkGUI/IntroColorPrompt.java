package cluedoNetworkGUI;

import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.util.ArrayList;

import staticClasses.Config;
import cluedoNetworkLayer.CluedoGame;

public class IntroColorPrompt extends GridPane{
	Stage parent;
	public ArrayList<String> colors = new ArrayList<String>();
	ToggleButton tempButton;
	String selectedColor = null;


	public IntroColorPrompt(Stage p, ArrayList<String> colorlist){
		super();
		colors = colorlist;
		setHandler();
		
		parent = p;
	}


	private void setHandler() {
		int counter = 0;
		for(String temp : colors){
			Config.COLOR_SELECT_WINDOW_WIDTH += 250;
			Image imageButton = new Image ("media/" + temp + ".jpg");
			tempButton = new ToggleButton("", new ImageView(imageButton));
			this.add(tempButton, counter, 0);
			tempButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
				public void handle (MouseEvent arg0){
					selectedColor = temp;
					parent.close();
				}
			});
			
			counter++;
		}
		
		
		
		for (int i = 0; i < counter; i++){
			ColumnConstraints col0 = new ColumnConstraints();
	        col0.setHgrow(Priority.ALWAYS);
	        col0.setPercentWidth(100/counter);
	        this.getColumnConstraints().add(col0);
		}
		
	}
	
	public String returnColor(){
		return selectedColor;
	}

}
