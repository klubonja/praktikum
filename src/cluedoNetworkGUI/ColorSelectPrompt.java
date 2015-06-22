package cluedoNetworkGUI;

import enums.Persons;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ColorSelectPrompt extends GridPane {
	
	//makes a Window which asks to choose a color
	//Test 2
	Stage parent;
	Text title = new Text("Select a color: ");
	ToggleButton red = new ToggleButton("Red");
	ToggleButton yellow = new ToggleButton("Yellow");
	ToggleButton white = new ToggleButton("White");
	ToggleButton green = new ToggleButton("Green");
	ToggleButton blue = new ToggleButton("Blue");
	ToggleButton purple = new ToggleButton("Purple");
	Button submit = new Button("Choose Color");
	StringProperty errorText = new SimpleStringProperty();
	Label error = new Label("");
	
	String selectedColor = null;
	
	
	public ColorSelectPrompt(Stage p){
		super();
		setHandler();
		parent = p;
		
//		Persons[] colors = Persons.values();
//		for (Persons pe: colors){
//			System.out.println(pe.getColor());
//			//System.out.println(pe.getPersonName());
//		}
//		
//		Persons p1 = Persons.blue;
//		p1.getPersonName();
		
	}

	private void setHandler() {
		red.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				selectedColor = "red";
				
			}
		});
		
		yellow.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				selectedColor = "yellow";
				
			}
		});
		
		white.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				selectedColor = "white";
				
			}
		});
		
		green.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				selectedColor = "green";
				
			}
		});
		
		blue.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				selectedColor = "blue";
				
			}
		});
		
		purple.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				selectedColor = "purple";
				
			}
		});
		
		submit.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				getColor();
				
			}
		});
		
		error.textProperty().bind(errorText);
		
		ColumnConstraints col0 = new ColumnConstraints();
        col0.setHgrow(Priority.ALWAYS);
        col0.setPercentWidth(16);
        this.getColumnConstraints().add(col0);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        col1.setPercentWidth(16);
        this.getColumnConstraints().add(col1);
        
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setPercentWidth(16);
        this.getColumnConstraints().add(col2);
        
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.ALWAYS);
        col3.setPercentWidth(16);
        this.getColumnConstraints().add(col3);
        
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHgrow(Priority.ALWAYS);
        col4.setPercentWidth(16);
        this.getColumnConstraints().add(col4);
        
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setHgrow(Priority.ALWAYS);
        col5.setPercentWidth(16);
        this.getColumnConstraints().add(col5);
        
		//grid.add(node,				col,row,colspan,rowspan)
		this.add(title, 				0, 0);
		this.add(red, 					0, 1);
		this.add(yellow, 				1, 1);
		this.add(white, 				2, 1);
		this.add(green, 				3, 1);
		this.add(blue, 					4, 1);
		this.add(purple, 				5, 1);
		this.add(error, 				0, 2, 6, 1);
		this.add(submit, 				0, 3, 2, 1);
		
	}
	
	public void getColor(){
		if (selectedColor == null){
			error.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
			error.setTextFill(Color.RED);
			this.errorText.set("Please select a color first.");
			
			
		}
		else{
			System.out.println("Selected Color: " + selectedColor);
			parent.close();
		}
	}
	
	public String returnColor(){
		return selectedColor;
	}

}
