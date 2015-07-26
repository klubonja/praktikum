package cluedoNetworkGUI;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import staticClasses.Config;

public class IntroColorPrompt extends GridPane{
	
	public Stage parent;
	public ArrayList<String> colors = new ArrayList<String>();
	public Button tempButton;
	public String selectedColor = null;


	public IntroColorPrompt(Stage p, ArrayList<String> colorlist){
		super();
		this.colors = colorlist;
		this.parent = p;
		setHandler();
		layoutGrid();
	}
	
	public void layoutGrid(){
		
		  this.setPadding(new Insets(15));
		  this.setHgap(20);
		  this.setVgap(5);
	      this.setGridLinesVisible(false);
	      this.setAlignment(Pos.CENTER);
	      
	      Rectangle rect = new Rectangle(415, 400);
	      rect.setFill(Color.WHITE);
	      rect.setArcHeight(30);
	      rect.setArcWidth(30);

	      this.setClip(rect);
	        
	      ColumnConstraints col0 = new ColumnConstraints();
	      col0.setMaxWidth(160);
	      col0.setPrefWidth(160);
	      this.getColumnConstraints().add(col0);
	        
	      ColumnConstraints col1 = new ColumnConstraints();
	      col1.setMaxWidth(160);
	      col1.setPrefWidth(160);
	      this.getColumnConstraints().add(col1);
	        
	      ColumnConstraints col2 = new ColumnConstraints();
	      col2.setMaxWidth(160);
	      col2.setPrefWidth(160);
	      this.getColumnConstraints().add(col2);
	        
	      RowConstraints row0 = new RowConstraints(); 
		  row0.setMaxHeight(190); 
		  row0.setPrefHeight(190); 
		  this.getRowConstraints().add(row0);
		    
		  RowConstraints row1 = new RowConstraints();
		  row1.setMaxHeight(190); 
		  row1.setPrefHeight(190);      
		  this.getRowConstraints().add(row1);
		  
//		  RowConstraints row2 = new RowConstraints();
//		  row2.setMaxHeight(200); 
//		  row2.setPrefHeight(200);      
//		  this.getRowConstraints().add(row2);
		  
		  BorderStroke stroke = new BorderStroke(Color.DARKSLATEGREY, BorderStrokeStyle.SOLID, 
					new CornerRadii(15), new BorderWidths(1));
		  Border border = new Border(stroke);
		  this.setBorder(border);
		    
	}


	private void setHandler() {
		
		Config.COLOR_SELECT_WINDOW_WIDTH = 0;
		int counter = 0;
		int columnCounter = 0;
		int rowCounter = 0;
		for(String temp : colors){
			Config.COLOR_SELECT_WINDOW_WIDTH += 100;
			Image imageButton = new Image ("media/cards/" + temp + ".png");
			tempButton = new Button("", new ImageView(imageButton));
			tempButton.setPrefSize(140, 190);
			tempButton.setMaxSize(140, 190);
			//if(columnCounter==1){
				GridPane.setHalignment(tempButton, HPos.CENTER);
			//}else{
			//	GridPane.setHalignment(tempButton, HPos.RIGHT);	
			//}
			GridPane.setValignment(tempButton, VPos.CENTER);
			GridPane.setConstraints(tempButton, columnCounter , rowCounter);
			this.getChildren().add(tempButton);
			if(columnCounter==2){
				rowCounter++;
				columnCounter=-1;
			}
			columnCounter++;
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


//	public Stage getOurParent() {
//		return parent;
//	}
//
//
//	public void setParent(Stage parent) {
//		this.parent = parent;
//	}


	public ArrayList<String> getColors() {
		return colors;
	}


	public void setColors(ArrayList<String> colors) {
		this.colors = colors;
	}


	public Button getTempButton() {
		return tempButton;
	}


	public void setTempButton(Button tempButton) {
		this.tempButton = tempButton;
	}


	public String getSelectedColor() {
		return selectedColor;
	}


	public void setSelectedColor(String selectedColor) {
		this.selectedColor = selectedColor;
	}

}
