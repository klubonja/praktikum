
package view;

import kacheln.KachelContainer;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoNetworkLayer.CluedoGameClient;
import finderOfPaths.Ausloeser;
import finderOfPaths.PlayerCircleManager;
import finderOfPaths.Sucher;

/**
 * 
 * @since 26.05.2015
 * @author YinYanYolos
 * 
 * The class DicePresenter. Makes able to trigger all the events of the DiceView class.
 *
 */

public class DicePresenter {
	
	public static int diceCounter = 0;
	
	private int [] dice = new int [2];
	
	private GameFrameView view;
	private DiceView dices;
	
	private boolean istSpieler;
	
	private Ausloeser ausloeser;
	private BoardView gui;
	private Sucher sucher;
	
	private int zielWuerfelEins;
	private int zielWuerfelZwei;
	public PlayerCircleManager pcManager;
	private int gameid;
	private CluedoGameClient networkGame;
	private KachelContainer kacheln;
	
	public DicePresenter(DiceView dices, GameFrameView view, Ausloeser ausloeser, BoardView gui, Sucher sucher, PlayerCircleManager pcManager, int gameid, CluedoGameClient networkGame, KachelContainer kacheln){
		this.pcManager = pcManager;
		this.gameid = gameid;
		this.networkGame = networkGame;
		this.ausloeser = ausloeser;
		this.view = view;
		this.dices = dices;
		this.sucher = sucher;
		this.gui = gui;
		this.kacheln = kacheln;
		startEvents();
		istSpieler = true;
	}
	
	public DicePresenter(PlayerCircleManager pcManager, Ausloeser ausloeser, Sucher sucher){
		this.pcManager = pcManager;
		this.ausloeser = ausloeser;
		this.sucher = sucher;
		istSpieler = false;
	}
	
	public void startEvents(){
		
		view.getZugView().YESwurfelImage.setOnMouseClicked(e -> iWantToRollTheDice());
	}
	
	public void iWantToRollTheDice(){
		networkGame.sendMsgToServer(NetworkMessages.roll_diceMsg(gameid));
		
	}
	
	public void rollTheDiceForSomeone(int ersterWuerfel, int zweiterWuerfel, PlayerCircleManager pcManager){
		this.zielWuerfelEins = ersterWuerfel;
		this.zielWuerfelZwei = zweiterWuerfel;
		this.pcManager = pcManager;
		auxx.logsevere("DicePresenter.rollTheDiceForSomeone");
		auxx.logsevere("currentPlayer Color : " +pcManager.getCurrentPlayer().getCluedoPerson().getColor());
		auxx.logsevere("currentPlayer x : " +pcManager.getCurrentPlayer().getPosition().getX() + "  ||  y : " +pcManager.getCurrentPlayer().getPosition().getY());
		speedWuerfeln(pcManager, "someone");
		if (istSpieler){view.getKomplettesFeld().getChildren().remove(view.getZugView());}
	}
	
	public void speedWuerfeln(PlayerCircleManager pcManager, String who){
		
		this.pcManager = pcManager;
		int first = 1 + (int)(Math.random()*6);
		int second = 1 + (int)(Math.random()*6);
		if (who == "someone"){
			first = zielWuerfelEins;
			second = zielWuerfelZwei;
		}
		dice[0] = first;
		dice[1] = second;
		wuerfeln(pcManager);
		
	}
	
	/**
	 * Testmethode zum Würfeln
	 */
	public void wuerfeln(PlayerCircleManager pcManager){
		this.pcManager = pcManager;
		
		auxx.logsevere("DicePresenter.wuerfeln");
		auxx.logsevere("currentPlayer Color : " +pcManager.getCurrentPlayer().getCluedoPerson().getColor());
		auxx.logsevere("currentPlayer x : " +pcManager.getCurrentPlayer().getPosition().getX() + "  ||  y : " +pcManager.getCurrentPlayer().getPosition().getY());
		
		if (istSpieler){gui.resetBackground();}
		
		kacheln.resetMoeglichkeiten();
		
		ausloeser.setWuerfelZahl(dice[0] + dice[1]);
		System.out.println("==================================");
		System.out.println("==================================");
		System.out.println("Würfelzahl : " +ausloeser.getWuerfelZahl());
		System.out.println("==================================");
		System.out.println("==================================");
		
		sucher.suchen(ausloeser.getWuerfelZahl(), pcManager);
		ausloeser.zuweisung(pcManager);
		ausloeser.setGewuerfelt(true);
	}
	

	public int[] getDice() {
		return dice;
	}

	public void setDice(int[] dice) {
		this.dice = dice;
	}

	
	
}

