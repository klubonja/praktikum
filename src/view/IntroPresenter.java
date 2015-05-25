package view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class IntroPresenter {
	
	 IntroView iv;
	 
	
		public IntroPresenter(IntroView iv){
		
		this.iv = iv;
	
		activateEvents();
		
		}
	
	public void activateEvents(){
		iv.char1.setOnAction(e-> selection(1));
		iv.char2.setOnAction(e-> selection(2));
		iv.char3.setOnAction(e-> selection(3));
		iv.char4.setOnAction(e-> selection(4));
		iv.char5.setOnAction(e-> selection(5));
		iv.char6.setOnAction(e-> selection(6));
		iv.char1.setOnMouseEntered(e -> addEffect(iv.char1));
		iv.char2.setOnMouseEntered(e -> addEffect(iv.char2));
		iv.char3.setOnMouseEntered(e -> addEffect(iv.char3));
		iv.char4.setOnMouseEntered(e -> addEffect(iv.char4));
		iv.char5.setOnMouseEntered(e -> addEffect(iv.char5));
		iv.char6.setOnMouseEntered(e -> addEffect(iv.char6));
		iv.newGame.setOnMouseEntered(e -> addEffect(iv.newGame));
		iv.char1.setOnMouseExited(e -> removeEffect(iv.char1));
		iv.char2.setOnMouseExited(e -> removeEffect(iv.char2));
		iv.char3.setOnMouseExited(e -> removeEffect(iv.char3));
		iv.char4.setOnMouseExited(e -> removeEffect(iv.char4));
		iv.char5.setOnMouseExited(e -> removeEffect(iv.char5));
		iv.char6.setOnMouseExited(e -> removeEffect(iv.char6));
		iv.newGame.setOnMouseExited(e -> removeEffect(iv.newGame));
		iv.newGame.setOnAction(e -> startNewGame());
	}
	
	public void selection(int n){
		switch(n) {
		case 1: iv.pl1.setSelected(true); break;
		case 2: iv.pl2.setSelected(true); break;
		case 3: iv.pl3.setSelected(true); break;
		case 4: iv.pl4.setSelected(true); break;
		case 5: iv.pl5.setSelected(true); break;
		case 6: iv.pl6.setSelected(true); break;
		}
	}
	
	public void addEffect(Node node){
		node.setEffect(new Glow(0.9));
	}
	
	public void removeEffect(Node node){
		node.setEffect(null);
	}
	
	public void startNewGame(){
		
		GameFrameView view = new GameFrameView();
		GameFramePresenter pres = new GameFramePresenter(view);
		Scene scene = new Scene(view, 1200, 825);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setTitle("Cluedo");
        primaryStage.show();
		}

}
