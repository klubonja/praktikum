package yoloKI;

import java.util.ArrayList;
import java.util.List;

import kacheln.KIKachel;
import kacheln.KIKachelContainer;
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
import enums.PlayerStates;
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
		kiKacheln = new KIKachelContainer(communicator.getKacheln());
		whereDoIGo = new WhereDoIGo(communicator.getPathfinder(), kiKacheln);
		
	}
	
	public void startTurn(){
		if (shallwefinish()) {
			accuseRand();//accuse aka mostprobably fuck up your game
			return;			
		}
		else if (yourOwnPlayer.getPossibleStates().contains(PlayerStates.use_secret_passage) && auxx.getRandInt(1, 2)%2 == 0){ //take secret passage	
			communicator.requestUseSecretPassge();
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
		else if(shallwefinish()){
			accuseRand();
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
//		communicator.getAusloeser().suggestMoveToServer(pos);
		communicator.sendKIMove(pos);
	}
	
	
	
	public boolean shallwefinish(){
		return safeKarten.wieVielProzentHabenWir()  > Config.HOWMANYOFDEMCARDS/Config.ALLOFDEMCARDS;			
	}
	
	public void accuseRand(){
		CluedoStatement finalstatement = safeKarten.makeAccusingRandStatement();
		communicator.accuse(finalstatement.getPerson().getPersonName(), finalstatement.getRoom().getName(), finalstatement.getWeapon().getName());
	}
	
	public void chooseDisprove(ArrayList<String> disprover) {
		String card = disprover.get(auxx.getRandInt(0, disprover.size()-1));		
		communicator.sendDisproveMsg(card);
	}
	
	public void moved() {
		if (shallwefinish()) accuseRand();
		else {
			network.sendMsgToServer(NetworkMessages.chatMsg("you are all pretty dumb, it seems",network.getGameId(), auxx.now()));
			communicator.endTurn();
		}
		
	}

	
	////////////////////////////////////HELPER////////////////////////////////////////////////
	
	
	public static int getDist(KIKachel k,CluedoPosition pos){
		return Math.abs((k.getPosition().getX()+k.getPosition().getY()) - (pos.getX()+pos.getY()));
	}
	
//	public CluedoPosition tellMeWhereToGo(){
//		KIKachel iGoHere = new KIKachel();
//		ArrayList <KIKachel> moeglicheKacheln = whereDoIGo.kachelnSuchen();
//		iGoHere = whereDoIGo.getBestKachel(moeglicheKacheln);
//		if (whereDoIGo.gibtEsTueren(whereDoIGo.tuerKIKachelnSuchen(moeglicheKacheln))){
//			ArrayList <KIKachel> tuerKacheln = whereDoIGo.tuerKIKachelnSuchen(moeglicheKacheln);
//			iGoHere = whereDoIGo.getBestKachel(tuerKacheln);
//		}
//		return iGoHere.getPosition();
//	}
	
	public static KIKachel getClosestKachelFromList(List<KIKachel> klist,CluedoPosition pos){
		KIKachel kachel = klist.get(0);
		int d = getDist(kachel, pos);
		for (KIKachel k: klist){
			int dtmp = getDist(k, pos);//Math.abs((k.getPosition().getX()+k.getPosition().getY()) - (pos.getX()+pos.getY()));
			if (dtmp < d) kachel = k;
			System.out.println("checking closest kachel to : x " +k.getX()+" y "+k.getY()+" from  x"+pos.getX()+" y"+pos.getY());
		}
		
		System.out.println("closest kachel to "+pos.getX()+" y"+pos.getY()+ " is x"+kachel.getX()+" y"+kachel.getY());
		return kachel; //für alle kacheln die gleichweit und am weitesten weg sind gilt wer höhere y werte hat wird bevorzugt und dann wer höhere x werte hat
	}
	public CluedoPosition asCloseAspossibleToNextDoor() {
		ArrayList <KIKachel> moeglicheKacheln = whereDoIGo.kachelnSuchen();
		ArrayList <KIKachel> tuerKacheln = whereDoIGo.tuerKIKachelnSuchen(moeglicheKacheln);
		if (tuerKacheln.size() > 0){
			return whereDoIGo.getBestKachel(tuerKacheln).getPosition();
		}
		for (KIKachel l: moeglicheKacheln) System.out.println(l.getPosition().getX()+" : "+l.getPosition().getY());
		ArrayList<KIKachel> nextToDoors = new ArrayList<KIKachel>();
		ArrayList<KIKachel> doorsinfield = kiKacheln.getDoors();
		
		for (KIKachel door: doorsinfield){
			nextToDoors.add(getClosestKachelFromList(moeglicheKacheln, door.getPosition()));
		}
		
		return getClosestKachelFromList(nextToDoors, yourOwnPlayer.getPosition()).getPosition();			
	}
	

	
	
}
