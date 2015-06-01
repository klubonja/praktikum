package view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Player;

public class IntroPresenter {
	
	 IntroView iv;
	 Player player;
	 
	
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
		iv.char1.setOnMouseEntered(e -> addEffectRed(iv.char1));
		iv.char2.setOnMouseEntered(e -> addEffectYellow(iv.char2));
		iv.char3.setOnMouseEntered(e -> addEffectWhite(iv.char3));
		iv.char4.setOnMouseEntered(e -> addEffectGreen(iv.char4));
		iv.char5.setOnMouseEntered(e -> addEffectBlue(iv.char5));
		iv.char6.setOnMouseEntered(e -> addEffectPurple(iv.char6));
		//iv.newGame.setOnMouseEntered(e -> addEffect(iv.newGame));
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
	
	public void addEffectRed(Node node){
		Glow glow = new Glow(0.4);
		DropShadow dropShadow = new DropShadow();
		 dropShadow.setOffsetY(5.0);
		 dropShadow.setColor(Color.CRIMSON);
		 glow.setInput(dropShadow);
		node.setEffect(glow);
	}
	public void addEffectYellow(Node node){
		Glow glow = new Glow(0.4);
		DropShadow dropShadow = new DropShadow();
		 dropShadow.setOffsetY(5.0);
		 dropShadow.setColor(Color.GOLD);
		 glow.setInput(dropShadow);
		node.setEffect(glow);
	}
	public void addEffectWhite(Node node){
		Glow glow = new Glow(0.4);
		DropShadow dropShadow = new DropShadow();
		 dropShadow.setOffsetY(5.0);
		 dropShadow.setColor(Color.FLORALWHITE);
		 glow.setInput(dropShadow);
		node.setEffect(glow);
	}
	public void addEffectGreen(Node node){
		Glow glow = new Glow(0.4);
		DropShadow dropShadow = new DropShadow();
		 dropShadow.setOffsetY(5.0);
		 dropShadow.setColor(Color.LIMEGREEN);
		 glow.setInput(dropShadow);
		node.setEffect(glow);
	}
	public void addEffectPurple(Node node){
		Glow glow = new Glow(0.4);
		DropShadow dropShadow = new DropShadow();
		 dropShadow.setOffsetY(5.0);
		 dropShadow.setColor(Color.MEDIUMORCHID);
		 glow.setInput(dropShadow);
		node.setEffect(glow);
	}
	public void addEffectBlue(Node node){
		Glow glow = new Glow(0.4);
		DropShadow dropShadow = new DropShadow();
		dropShadow.setOffsetY(5.0);
		dropShadow.setColor(Color.ROYALBLUE);
		glow.setInput(dropShadow);
		node.setEffect(glow);
	}
	
	public void removeEffect(Node node){
		node.setEffect(null);
	}
	
	
	
	@SuppressWarnings("unused")
	public void startNewGame(){
		
		if(iv.pl1.isSelected()){ 
			this.player = new Player("Player1", 0, 0);
		}
		if(iv.pl2.isSelected()){ 
			this.player = new Player("Player2", 0, 0);
		}
		if(iv.pl3.isSelected()){ 
			this.player = new Player("Player3", 0, 0);
		}
		if(iv.pl4.isSelected()){ 
			this.player = new Player("Player4", 0, 0);
		}
		if(iv.pl5.isSelected()){ 
			this.player = new Player("Player5", 0, 0);
		}
		if(iv.pl6.isSelected()){ 
			this.player = new Player("Player6", 0, 0);
		}
		
		GameFrameView view = new GameFrameView();
		GameFramePresenter pres = new GameFramePresenter(view, player);
		Scene scene = new Scene(view, 1300, 700);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setTitle("Cluedo");
        primaryStage.show();
		}

}
