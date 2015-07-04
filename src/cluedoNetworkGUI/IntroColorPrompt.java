package cluedoNetworkGUI;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import staticClasses.Config;

public class IntroColorPrompt extends HBox{
	Stage parent;
	public ArrayList<String> colors = new ArrayList<String>();
	Button tempButton;
	String selectedColor = null;


	public IntroColorPrompt(Stage p, ArrayList<String> colorlist){
		super();
		colors = colorlist;
		setHandler();
		
		parent = p;
	}


	private void setHandler() {
		Config.COLOR_SELECT_WINDOW_WIDTH = 0;
		int counter = 0;
		for(String temp : colors){
			Config.COLOR_SELECT_WINDOW_WIDTH += 100;
			Image imageButton = new Image ("media/" + temp + ".png");
			tempButton = new Button("", new ImageView(imageButton));
			this.getChildren().add(tempButton);
			tempButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
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
//	        this.getColumnConstraints().add(col0);
		}
		
	}
	

	public String returnColor(){
		return selectedColor;
	}

}
