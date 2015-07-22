package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * @since 26.05.2015
 * @author YinYanYolos
 * 
 * The Class DiceView implemented as a GridPane.
 *
 */

public class DiceView extends GridPane{

	private ImageView d1;
	private ImageView d2;
	
	private final Image dice1 = new Image("media/1c.gif");
	private final Image dice2 = new Image("media/2c.gif");
	private final Image dice3 = new Image("media/3c.gif");
	private final Image dice4 = new Image("media/4c.gif");
	private final Image dice5 = new Image("media/5c.gif");
	private final Image dice6 = new Image("media/6c.gif");
	
	/**
	 * Constructor of the Class.
	 * Creates a GridPane with a Button to roll the dices and two ImageViews for the dices frames.
	 */
	public DiceView(){
	
		//Edits the layout of the main GridPane.
		this.setGridLinesVisible(false);
		this.setVgap(5);
		this.setHgap(20);
		
		RowConstraints row0 = new RowConstraints();
		row0.setPercentHeight(82.75);
		this.getRowConstraints().add(row0);
		
		ColumnConstraints column0 = new ColumnConstraints();
		column0.setPercentWidth(50);
		this.getColumnConstraints().add(column0);
		
		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(17.25);
		this.getRowConstraints().add(row1);
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		this.getColumnConstraints().add(column1);
		
//		this.getRowConstraints().add(new RowConstraints(120));
//		this.getRowConstraints().add(new RowConstraints(25));
//		this.getColumnConstraints().add(new ColumnConstraints(150));
//		this.getColumnConstraints().add(new ColumnConstraints(150));
		this.setPadding(new Insets(5));
		
		//Creates an ImageView for the first dice.
		d1 = new ImageView();
		d1.setImage(dice1);
		d1.setFitHeight(100);
		d1.setFitWidth(100);
		GridPane.setConstraints(d1,0,0);
		GridPane.setHalignment(d1, HPos.RIGHT);
		GridPane.setValignment(d1, VPos.CENTER);
		
		//Creates an ImageView for the second dice.
		d2 = new ImageView();
		d2.setImage(dice1);
		d2.setFitHeight(100);
		d2.setFitWidth(100);
		GridPane.setHalignment(d2, HPos.LEFT);
		GridPane.setValignment(d2, VPos.CENTER);
		GridPane.setConstraints(d2,1,0);
		
		//Adds all the above elements to the GridPane
		this.getChildren().addAll(d1, d2);
	}
	
	
	//Getters and Setters
	public ImageView getD1() {
		return d1;
	}


	public void setD1(ImageView d1) {
		this.d1 = d1;
	}


	public ImageView getD2() {
		return d2;
	}


	public void setD2(ImageView d2) {
		this.d2 = d2;
	}

	public Image getDice1() {
		return dice1;
	}

	public Image getDice2() {
		return dice2;
	}

	public Image getDice3() {
		return dice3;
	}

	public Image getDice4() {
		return dice4;
	}

	public Image getDice5() {
		return dice5;
	}

	public Image getDice6() {
		return dice6;
	}
}
