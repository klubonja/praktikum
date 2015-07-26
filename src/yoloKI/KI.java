package yoloKI;

import java.util.ArrayList;
import java.util.List;

import staticClasses.Config;
import staticClasses.NetworkMessages;
import kacheln.KIKachel;
import kacheln.KIKachelContainer;
import kommunikation.Communicator;
import kommunikation.PlayerCircleManager;
import staticClasses.auxx;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;
import cluedoNetworkLayer.CluedoStatement;

public class KI {

	String name = "yolo KI " + auxx.getRandomString(5);
	
	private ArrayList<KIPerson> personen;
	private KIKarten safeKarten;
	private KIKarten eigeneKarten;
	private ArrayList<KIRaum> raeume;
	private KILogbuch aktionen;
	private Communicator communicator;
	private CluedoGameClient network;
	private WhereDoIGo whereDoIGo;
	private WhatDoIGuess whatDoIGuess;
	private KIKachelContainer kiKacheln;
	private CluedoPlayer yourOwnPlayer;
	private PlayerCircleManager pcManager;

	public KI(CluedoGameClient n,PlayerCircleManager pcManager,Communicator communi){
		this.pcManager = pcManager;
		network = n;
		communicator = communi;
		yourOwnPlayer = pcManager.getPlayerByNick(network.getMyNick());
		safeKarten = new KIKarten(yourOwnPlayer.getCards(), pcManager.getSize());
		eigeneKarten = new KIKarten(yourOwnPlayer.getCards(), pcManager.getSize());
		this.whereDoIGo = new WhereDoIGo(communicator.getPathfinder(), kiKacheln);
		this.kiKacheln = new KIKachelContainer(communicator.getKacheln());
	}
	
	public void startTurn(){
		if (shallwefinish()) {//accuse aka mostprobably fuck up your game
			accuse();
			return;			
		}
		else if (false){ //take secret passage
			
		}
		else {// roll dice
			communicator.getDicePresenter().iWantToRollTheDice();
		}
		
	}
	
	public void move(){
		
		//com.getAusloeser().suggestMoveToServer(pos);
	}
	
	public KIKachel getClosestKachelFromList(List<KIKachel> klist,CluedoPosition pos){
		KIKachel kachel = new KIKachel();
		int d = 0;
		for (KIKachel k: klist){
			int dtmp = Math.abs((k.getPosition().getX()+k.getPosition().getY()) - (pos.getX()+pos.getY()));
			if (dtmp < d) kachel = k;
		}
		
		return kachel; //für alle kacheln die gleichweit und am weitesten weg sind gilt wer höhere y werte hat wird bevorzugt und dann wer höhere x werte hat
	}
	
	public boolean shallwefinish(){
		return safeKarten.wieVielProzentHabenWir()  > Config.HOWMANYOFDEMCARDS/Config.ALLOFDEMCARDS;			
	}
	
	public void accuse(){
		CluedoStatement finalstatement = safeKarten.makeAccusingRandStatement();
		network.sendMsgToServer(NetworkMessages.accuseMsg(network.getGameId(),
				NetworkMessages.statement(finalstatement.getPerson().getPersonName(), finalstatement.getRoom().getName(), finalstatement.getWeapon().getName())));
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
