package view;

import model.Weapon;
import javafx.scene.image.ImageView;

public class WeaponView extends ImageView{
	
	private Weapon model;
	
	public WeaponView(){
		//to be completed
		
		bindComponents();
	}
	
	
	public void bindComponents(){
		this.xProperty().bind(model.xCoordProperty());
		this.yProperty().bind(model.yCoordProperty());
	}
	
	

}
