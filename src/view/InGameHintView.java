package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import staticClasses.HowToPlay;

public class InGameHintView extends GridPane {
	
	private Button close;
	private TextArea rules;
	
	private Scene scene;
	private Stage stage;
	
	private BorderStroke backgroundStroke;
	private Border backgroundBorder;
	
	private GameFrameView gameFrameView;
	
	public InGameHintView(GameFrameView gameFrameView){
		
		this.gameFrameView = gameFrameView;
		
	    this.backgroundStroke = new BorderStroke(Color.DARKSLATEGREY, BorderStrokeStyle.SOLID, 
				new CornerRadii(10), new BorderWidths(1.3));
		this.backgroundBorder = new Border(backgroundStroke);
		this.setBorder(backgroundBorder);
	    
		
		Rectangle rect = new Rectangle(500,380);
		rect.setArcHeight(20);
		rect.setArcWidth(20);
		this.setClip(rect);
		
		this.setAlignment(Pos.CENTER);
		
		this.getRowConstraints().add(new RowConstraints(380));
		this.getRowConstraints().add(new RowConstraints(100));
		this.getColumnConstraints().add(new ColumnConstraints(380));
		this.setVgap(5);
		this.setPadding(new Insets(10));
		

		InnerShadow innerShadow6 = new InnerShadow();
		innerShadow6.setOffsetX(0);
		innerShadow6.setOffsetY(0);
		innerShadow6.setWidth(15);
		innerShadow6.setHeight(15);
		innerShadow6.setColor(Color.DARKSLATEGREY);
		
		rules = new TextArea();
	    rules.setPrefHeight(300);
	    rules.setMaxHeight(300);
	    rules.setWrapText(true);
	    rules.setEditable(false);
	    rules.setOpacity(0.9);
	    rules.setEffect(innerShadow6);
	    rules.setText(HowToPlay.GOAL + HowToPlay.PLAY + HowToPlay.SUGGESTION 
	        		+ HowToPlay.ACCUSING);
	        
	    GridPane.setConstraints(rules, 0, 0);
	    GridPane.setValignment(rules, VPos.BOTTOM);
	    GridPane.setHalignment(rules, HPos.CENTER);
	    
		close = new Button("Close");
		close.setOpacity(0.9);
		
		GridPane.setConstraints(close, 0, 1);
		GridPane.setHalignment(close, HPos.RIGHT);
		GridPane.setValignment(close, VPos.TOP);
		
		this.getChildren().addAll(rules, close);
		
		close.setOnAction( e -> close());
		
	}
	
	public void start(){
		
		scene = new Scene (this, 500, 380);
		scene.setFill(Color.TRANSPARENT);
		stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setFullScreen(false);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.toFront();
		stage.centerOnScreen();
		stage.setAlwaysOnTop(true);
		stage.setOpacity(0.95);
		stage.show();
		
		}
	
	public void close(){
		
		this.stage.close();
		
	}

	public Button getClose() {
		return close;
	}

	public void setClose(Button close) {
		this.close = close;
	}

	public TextArea getRules() {
		return rules;
	}

	public void setRules(TextArea rules) {
		this.rules = rules;
	}

	public GameFrameView getGameFrameView() {
		return gameFrameView;
	}

	public void setGameFrameView(GameFrameView gameFrameView) {
		this.gameFrameView = gameFrameView;
	}

}
