package yoloKI;

import java.util.ArrayList;

import kacheln.KIKachel;
import kacheln.KIKachelContainer;
import kommunikation.Communicator;
import kommunikation.PlayerCircleManager;
import staticClasses.auxx;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;

public class KI {

	String name = "yolo KI " + auxx.getRandomString(5);
	
	private ArrayList<KIPerson> personen;
	private KIKarten eigeneKarten;
	private KIKarten safeKarten;
	private ArrayList<KIRaum> raeume;
	private KILogbuch aktionen;
	private CluedoGameClient game;
	private Communicator communicator; 
	private PlayerCircleManager pcManager;
	private WhereDoIGo whereDoIGo;
	private WhatDoIGuess whatDoIGuess;
	private KIKachelContainer kiKacheln;
	private CluedoPlayer yourOwnPlayer;
	
	
	public KI(CluedoGameClient game){
		this.game = game;
		this.yourOwnPlayer = game.getPlayerByNick(game.getMyNick());
		this.eigeneKarten = new KIKarten(yourOwnPlayer.getCards());
		this.safeKarten = new KIKarten(yourOwnPlayer.getCards());
		this.pcManager = new PlayerCircleManager(game.getPlayers());
		this.communicator = new Communicator(game);
		this.kiKacheln = new KIKachelContainer(communicator.getKacheln());
		this.eigeneKarten = new KIKarten();
		this.whereDoIGo = new WhereDoIGo(communicator.getPathfinder(), kiKacheln);
	}
	
	public CluedoPosition tellMeWhereToGo(){
		KIKachel iGoHere = new KIKachel();
		ArrayList <KIKachel> moeglicheKacheln = whereDoIGo.kachelnSuchen();
		iGoHere = whereDoIGo.getBestKachel(moeglicheKacheln);
		if (whereDoIGo.gibtEsTueren(whereDoIGo.tuerKIKachelnSuchen(moeglicheKacheln))){
			ArrayList <KIKachel> tuerKacheln = whereDoIGo.tuerKIKachelnSuchen(moeglicheKacheln);
			iGoHere = whereDoIGo.getBestKachel(tuerKacheln);
		}
		return iGoHere.getPosition();
	}
	
}
