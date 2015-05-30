package view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * @since 26.05.2015
 * @version 2.0
 * @author Kristi Lubonja
 * 
 * 
 *
 */

public class DiceView extends GridPane{

	Button roll;
	ImageView d1;
	ImageView d2;
	
	final Image dice1 = new Image("http://dobbelsteen.virtuworld.net/img/1c.gif");
	final Image dice2 = new Image("http://dobbelsteen.virtuworld.net/img/2c.gif");
	final Image dice3 = new Image("http://dobbelsteen.virtuworld.net/img/3c.gif");
	final Image dice4 = new Image("http://dobbelsteen.virtuworld.net/img/4c.gif");
	final Image dice5 = new Image("http://dobbelsteen.virtuworld.net/img/5c.gif");
	final Image dice6 = new Image("http://dobbelsteen.virtuworld.net/img/6c.gif");
	
	
	public DiceView(){
	
		this.setGridLinesVisible(true);
		this.setVgap(5);
		this.setHgap(5);
		this.getRowConstraints().add(new RowConstraints(150));
		this.getColumnConstraints().add(new ColumnConstraints(150));
		this.getColumnConstraints().add(new ColumnConstraints(150));
		roll = new Button("Roll!");
		GridPane.setConstraints(roll,0,0);
		GridPane.setColumnSpan(roll, 2);
		GridPane.setHalignment(roll, HPos.CENTER);
		GridPane.setValignment(roll, VPos.BOTTOM);
		d1 = new ImageView();
		d1.setImage(dice1);
		d1.setFitHeight(100);
		d1.setFitWidth(100);
		GridPane.setConstraints(d1,0,0);
		GridPane.setHalignment(d1, HPos.CENTER);
		GridPane.setValignment(d1, VPos.CENTER);
		d2 = new ImageView();
		d2.setImage(dice1);
		d2.setFitHeight(100);
		d2.setFitWidth(100);
		GridPane.setHalignment(d2, HPos.CENTER);
		GridPane.setValignment(d2, VPos.CENTER);
		GridPane.setConstraints(d2,1,0);
		this.getChildren().addAll(d1, d2, roll);
	}
}
