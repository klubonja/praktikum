package yoloKI;

import java.util.ArrayList;
import java.util.List;

import kacheln.KIKachel;
import kacheln.KIKachelContainer;
import kacheln.Kachel;
import kommunikation.Communicator;
import kommunikation.PlayerCircleManager;
import staticClasses.Config;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;
import cluedoNetworkLayer.CluedoStatement;
import enums.Persons;
import enums.Rooms;
import enums.Weapons;

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
	
	public void postmove(){
		CluedoPosition pos = yourOwnPlayer.getPosition();
		if (kiKacheln.getKachelAt(pos.getX(), pos.getY()).isIstRaum()){
			suspectRand();
		}
		else {
			communicator.endTurn();
		}		
	}
	
	public void suspectRand(){
		Persons pers = eigeneKarten.welchePersonenHabenWirNicht().get(
				auxx.getRandInt(
						0,eigeneKarten.welchePersonenHabenWirNicht().size()-1 
						)
				);
		Weapons wpn = eigeneKarten.welcheWaffenHabenWirNicht().get(
				auxx.getRandInt(
						0,eigeneKarten.welcheWaffenHabenWirNicht().size()-1 
						)
				);
		Rooms room = eigeneKarten.welcheRaeumeHabenWirNicht().get(
				auxx.getRandInt(
						0,eigeneKarten.welcheRaeumeHabenWirNicht().size()-1 
						)
				);
		communicator.suspect(pers.getColor(), wpn.getName(), room.getName());
	}

	
	public void move(){
		CluedoPosition pos = asCloseAspossibleToNextDoor();		
		communicator.getAusloeser().suggestMoveToServer(pos);
	}
	
	public CluedoPosition asCloseAspossibleToNextDoor() {
		ArrayList <KIKachel> moeglicheKacheln = whereDoIGo.kachelnSuchen();
		ArrayList <KIKachel> tuerKacheln = whereDoIGo.tuerKIKachelnSuchen(moeglicheKacheln);
		if (tuerKacheln.size() > 0){
			return whereDoIGo.getBestKachel(tuerKacheln).getPosition();
		}
		ArrayList<KIKachel> nextToDoors = new ArrayList<KIKachel>();
		for (KIKachel door: tuerKacheln){
			nextToDoors.add(getClosestKachelFromList(moeglicheKacheln, door.getPosition()));
		}
		
		return getClosestKachelFromList(nextToDoors, yourOwnPlayer.getPosition()).getPosition();			
	}
	
	public boolean shallwefinish(){
		return safeKarten.wieVielProzentHabenWir()  > Config.HOWMANYOFDEMCARDS/Config.ALLOFDEMCARDS;			
	}
	
	public void accuse(){
		CluedoStatement finalstatement = safeKarten.makeAccusingRandStatement();
		network.sendMsgToServer(NetworkMessages.accuseMsg(network.getGameId(),
				NetworkMessages.statement(finalstatement.getPerson().getPersonName(), finalstatement.getRoom().getName(), finalstatement.getWeapon().getName())));
	}

	
	////////////////////////////////////HELPER////////////////////////////////////////////////
	
	
	public static int getDist(Kachel k,CluedoPosition pos){
		return Math.abs((k.getPosition().getX()+k.getPosition().getY()) - (pos.getX()+pos.getY()));
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
	
	public static KIKachel getClosestKachelFromList(List<KIKachel> klist,CluedoPosition pos){
		KIKachel kachel = klist.get(0);
		int d = getDist(kachel, pos);
		for (KIKachel k: klist){
			int dtmp = Math.abs((k.getPosition().getX()+k.getPosition().getY()) - (pos.getX()+pos.getY()));
			if (dtmp < d) kachel = k;
		}
		
		return kachel; //für alle kacheln die gleichweit und am weitesten weg sind gilt wer höhere y werte hat wird bevorzugt und dann wer höhere x werte hat
	}
	
}
