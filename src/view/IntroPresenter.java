package view;

import java.util.LinkedList;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Player;

public class IntroPresenter {
	
	 IntroView view;
	 Player player;
	 LinkedList<Player> players;
	 Player player1;
	 Player player2;
	 Player player3;
	 Player player4;
	 Player player5;
	 Player player6;
	 
	 StringProperty errorStr = new SimpleStringProperty("");
	 
	
		public IntroPresenter(IntroView view){
		
		this.view = view;
		player1 = new Player("Player 1", 0, 0, Color.RED);
		player2 = new Player("Player 2", 0, 0, Color.GREEN);
		player3 = new Player("Player 3", 0, 0, Color.BLUE);
		player4 = new Player("Player 4", 0, 0, Color.YELLOW);
		player5 = new Player("Player 5", 0, 0, Color.WHITE);
		player6 = new Player("Player 6", 0, 0, Color.PURPLE);
		players = new LinkedList<Player>();
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		players.add(player5);
		players.add(player6);
		
		view.error.textProperty().bind(errorStr);
	
		activateEvents();
		
		}
	
	public void activateEvents(){
		view.char1.setOnAction(e-> selection(1));
		view.char2.setOnAction(e-> selection(2));
		view.char3.setOnAction(e-> selection(3));
		view.char4.setOnAction(e-> selection(4));
		view.char5.setOnAction(e-> selection(5));
		view.char6.setOnAction(e-> selection(6));
		view.char1.setOnMouseEntered(e -> addEffectRed(view.char1));
		view.char2.setOnMouseEntered(e -> addEffectYellow(view.char2));
		view.char3.setOnMouseEntered(e -> addEffectWhite(view.char3));
		view.char4.setOnMouseEntered(e -> addEffectGreen(view.char4));
		view.char5.setOnMouseEntered(e -> addEffectBlue(view.char5));
		view.char6.setOnMouseEntered(e -> addEffectPurple(view.char6));
		//view.newGame.setOnMouseEntered(e -> addEffect(view.newGame));
		view.char1.setOnMouseExited(e -> removeEffect(view.char1));
		view.char2.setOnMouseExited(e -> removeEffect(view.char2));
		view.char3.setOnMouseExited(e -> removeEffect(view.char3));
		view.char4.setOnMouseExited(e -> removeEffect(view.char4));
		view.char5.setOnMouseExited(e -> removeEffect(view.char5));
		view.char6.setOnMouseExited(e -> removeEffect(view.char6));
		view.newGame.setOnMouseExited(e -> removeEffect(view.newGame));
		view.newGame.setOnAction(e -> startNewGame());
		view.quit.setOnAction(e -> quitGame());
	}
	
	public void selection(int n){
		switch(n) {
		case 1: view.pl1.setSelected(true); break;
		case 2: view.pl2.setSelected(true); break;
		case 3: view.pl3.setSelected(true); break;
		case 4: view.pl4.setSelected(true); break;
		case 5: view.pl5.setSelected(true); break;
		case 6: view.pl6.setSelected(true); break;
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
	public void startNewGame() throws NullPointerException{
		

		if(view.pl1.isSelected()){ 
			this.player = player1;
		}
		if(view.pl2.isSelected()){ 
			this.player = player2;
		}
		if(view.pl3.isSelected()){ 
			this.player = player3;
		}
		if(view.pl4.isSelected()){ 
			this.player = player4;
		}
		if(view.pl5.isSelected()){ 
			this.player = player5;
		}
		if(view.pl6.isSelected()){ 
			this.player = player6;
		}else{
			errorStr.set("Please select a character!");
		}
		
		view.close();
		
		GameFrameView view = new GameFrameView(this.player);
		GameFramePresenter pres = new GameFramePresenter(view, this.player);
		GameFramePresenter pres2 = new GameFramePresenter(view, player2);

		Scene scene = new Scene(view, 1300, 700);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setTitle("Cluedo");
        //primaryStage.setFullScreen(true);
        primaryStage.show();
		}
	
	public void quitGame(){
		Platform.exit();
	}

}
