package view;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import kommunikation.PlayerCircleManager;


public class IntroPresenter {
	
	 PlayerCircleManager pcManager;
	 IntroView view;
	 
	 StringProperty errorStr = new SimpleStringProperty("");
	 
	
		public IntroPresenter(IntroView view){
		
		this.view = view;

		
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
		//view.getNewGame().setOnMouseExited(e -> removeEffect(view.newGame));
		view.getNewGame().setOnAction(e -> startNewGame());
		view.quit.setOnAction(e -> quitGame());
		view.options.setOnAction(e -> optionsFrame());
		view.back.setOnAction(e -> mainFrame());
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
	
	public void optionsFrame(){
		view.secondary.toFront();
	}
	
	public void mainFrame(){
		view.primary.toFront();
	}

	
	public void startNewGame(){
		
		
		if(view.pl1.isSelected()){
			// GANZ TOLLE AUSWAHL
		}
		if(view.pl2.isSelected()){ 
			// GANZ TOLLE AUSWAHL
		}
		if(view.pl3.isSelected()){
			// GANZ TOLLE AUSWAHL
		}
		if(view.pl4.isSelected()){
			// GANZ TOLLE AUSWAHL
		}
		if(view.pl5.isSelected()){
			// GANZ TOLLE AUSWAHL
		}
		if(view.pl6.isSelected()){
			// GANZ TOLLE AUSWAHL
		}
		
		view.close();
		
		addPeople();
		
		//GameFrameView gameView = new GameFrameView(pcManager);
		
		// HIER BEGINNT DAS SPIEL
		//gameView.start();
		//GameFramePresenter pres = new GameFramePresenter(gameView,null,pcManager); // hier muss das netzwerk rein null ist nur verlegenheitslösung

		}
	
	public void addPeople(){
		
		Circle spieler1 = new Circle(0,0,14);
		spieler1.setFill(Color.BLUE);
		Circle spieler2 = new Circle(0,0,14);
		spieler2.setFill(Color.GREEN);
		Circle spieler3 = new Circle(0,0,14);
		spieler3.setFill(Color.PURPLE);
		Circle spieler4 = new Circle(0,0,14);
		spieler4.setFill(Color.RED);
		Circle spieler5 = new Circle(0,0,14);
		spieler5.setFill(Color.WHITE);
		Circle spieler6 = new Circle(0,0,14);
		spieler6.setFill(Color.YELLOW);
		
		
	}
	
	
	public void quitGame(){
		Platform.exit();
	}

}
