package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;


public class NotesPresenter {
	
	NotesView view;
	
	BackgroundFill redFill = new BackgroundFill(Color.CRIMSON, 
			new CornerRadii(1), new Insets(0.2));
	BackgroundFill blueFill = new BackgroundFill(Color.BLUE, 
			new CornerRadii(1), new Insets(0.2));
	BackgroundFill greenFill = new BackgroundFill(Color.FORESTGREEN, 
			new CornerRadii(1), new Insets(0.2));
	Background red = new Background(redFill);
	Background blue = new Background(blueFill);
	Background green = new Background(greenFill);
	Background defaultButton;
	Border defaultBorder;
	
	public NotesPresenter(NotesView view){
		
		this.view = view;
		defaultButton = view.defaultButton;
		defaultBorder = view.defaultBorder;
		
		startEvents();
		
	}
	
	public void startEvents(){
		
		for(Button button : view.buttons){
			button.setOnMouseClicked(e -> redBack(button));
		}
		
	}
	
	public void redBack(Button button){
		
		button.setBackground(red);
		button.setBorder(defaultBorder);
		
		if(button.getBackground().equals(red)){
			button.setOnMouseClicked(e -> blueBack(button));
			}
		}
	
	public void blueBack(Button button){
		button.setBackground(blue);
		button.setBorder(defaultBorder);
		
		if(button.getBackground().equals(blue)){
			button.setOnMouseClicked(e -> greenBack(button));
			}
	}
	
	public void greenBack(Button button){
		button.setBackground(green);
		button.setBorder(defaultBorder);
		
		if(button.getBackground().equals(green)){
			button.setOnMouseClicked(e -> defaultBack(button));
			}
	}
	
	public void defaultBack(Button button){
		button.setBackground(defaultButton);
		button.setBorder(defaultBorder);
		
		if(button.getBackground().equals(defaultButton)){
			button.setOnMouseClicked(e -> redBack(button));
			}
	}

}
