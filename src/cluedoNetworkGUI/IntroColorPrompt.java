package cluedoNetworkGUI;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

import cluedoNetworkLayer.CluedoGame;

public class IntroColorPrompt extends GridPane{
	Stage parent;
	public ArrayList<String> colors = CluedoGame.getAvailableColors();
	ToggleButton tempButton;


	public IntroColorPrompt(Stage p){
		super();
		setHandler();
		parent = p;
	}


	private void setHandler() {
		int counter = 0;
		for(String temp : colors){
			tempButton = new ToggleButton(temp);
			this.add(tempButton, 0, counter);
			counter++;
		}
		
	}

}
