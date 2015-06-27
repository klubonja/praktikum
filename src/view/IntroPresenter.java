package view;

import java.util.LinkedList;

import staticClasses.auxx;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;
import enums.Persons;
import enums.PlayerStates;

public class IntroPresenter {
	
	 IntroView view;
	 CluedoPlayer player;
	 LinkedList<CluedoPlayer> players;
//	 CluedoPlayer player1;
//	 CluedoPlayer player2;
//	 CluedoPlayer player3;
//	 CluedoPlayer player4;
//	 CluedoPlayer player5;
//	 CluedoPlayer player6;
	 GameFrameView viewPl1;
	 GameFrameView viewPl2;
	 GameFrameView viewPl3;
	 GameFrameView viewPl4;
	 GameFrameView viewPl5;
	 GameFrameView viewPl6;
	 StackPane gameStack;
	 
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
		
		System.out.println("!!!");
		
		
		//auxx.logsevere("null? : " +player.getPosition().getY() +player.getPosition().getX());
		
		//try{
		CluedoPlayer playerHans = new CluedoPlayer(null,null,null);
			//addPlayers();
		CluedoPlayer player1 = new CluedoPlayer(Persons.blue, PlayerStates.do_nothing, new CluedoPosition(0,18));
		CluedoPlayer player2 = new CluedoPlayer(Persons.green, PlayerStates.do_nothing, new CluedoPosition(9,24));
		CluedoPlayer player3 = new CluedoPlayer(Persons.purple, PlayerStates.do_nothing, new CluedoPosition(0,5));
		CluedoPlayer player4 = new CluedoPlayer(Persons.red, PlayerStates.do_nothing, new CluedoPosition(16,0));
		CluedoPlayer player5 = new CluedoPlayer(Persons.white, PlayerStates.do_nothing, new CluedoPosition(23,17));
		CluedoPlayer player6 = new CluedoPlayer(Persons.yellow, PlayerStates.do_nothing, new CluedoPosition(23,7));
		//}
		if(view.pl1.isSelected()){ 
			playerHans = new CluedoPlayer(Persons.blue, PlayerStates.do_nothing, new CluedoPosition(0,18));
		}
		if(view.pl2.isSelected()){ 
			playerHans = player2;
		}
		if(view.pl3.isSelected()){ 
			playerHans = player3;
		}
		if(view.pl4.isSelected()){ 
			playerHans = player4;
		}
		if(view.pl5.isSelected()){ 
			playerHans = player5;
		}
		if(view.pl6.isSelected()){ 
			playerHans = player6;
		}

		view.close();
		
		auxx.logsevere("null im Intro? : " +playerHans.getPosition().getY() +playerHans.getPosition().getX());
		
		//CluedoPlayer playerHans = new CluedoPlayer(Persons.red, PlayerStates.do_nothing, new CluedoPosition(5,5));
		
		GameFrameView gameView = new GameFrameView(playerHans);
		gameView.start();
		GameFramePresenter pres = new GameFramePresenter(gameView, playerHans);

//		}
//		
//		catch(NullPointerException e){
//			errorStr.set("Please select a character!"); 
//			}
//		
		
		}
	
//	public void addPlayers(){
//		this.player1 = new CluedoPlayer(Persons.blue, PlayerStates.do_nothing, new CluedoPosition(0,18));
//		this.player2 = new CluedoPlayer(Persons.green, PlayerStates.do_nothing, new CluedoPosition(9,24));
//		this.player3 = new CluedoPlayer(Persons.purple, PlayerStates.do_nothing, new CluedoPosition(0,5));
//		this.player4 = new CluedoPlayer(Persons.red, PlayerStates.do_nothing, new CluedoPosition(16,0));
//		this.player5 = new CluedoPlayer(Persons.white, PlayerStates.do_nothing, new CluedoPosition(23,17));
//		this.player6 = new CluedoPlayer(Persons.yellow, PlayerStates.do_nothing, new CluedoPosition(23,7));
//		players = new LinkedList<CluedoPlayer>();
//		players.add(player1);
//		players.add(player2);
//		players.add(player3);
//		players.add(player4);
//		players.add(player5);
//		players.add(player6);
//
//	}
	
	
	public void quitGame(){
		Platform.exit();
	}

}
