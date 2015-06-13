package view;


import model.Player;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PlayerView extends Circle {
	
	@SuppressWarnings("unused")
	private Player model;
	
	public PlayerView(double radius){
		super(radius);
		this.setFill(Color.BLUE);
		
		//bindComponents();
	}
	
	public PlayerView(Player model, Color color){
		super();
		this.model= model;
		this.setFill(color);
		
		//bindComponents();
		
	}
	
	/*public void bindComponents(){
		
		this.centerXProperty().bind(model.xCoordProperty());
		this.centerYProperty().bind(model.yCoordProperty());
	}*/
	
	

}
