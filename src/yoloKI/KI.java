package yoloKI;

import java.util.ArrayList;

import kommunikation.Communicator;
import kommunikation.PlayerCircleManager;
import staticClasses.Config;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoStatement;

public class KI {

	String name = "yolo KI " + auxx.getRandomString(5);
	
	private ArrayList<KIPerson> personen;
	private KIKarten safeKarten;
	private ArrayList<KIRaum> raeume;
	private KILogbuch aktionen;
	private CluedoGameClient network;
	private PlayerCircleManager pcm;
	private Communicator com;
	CluedoPlayer me;
	
	public KI(CluedoGameClient n,PlayerCircleManager pcManager,Communicator communi){
		pcm = pcManager;
		network = n;
		com = communi;
		me = pcManager.getPlayerByNick(network.getMyNick());
		safeKarten = new KIKarten(me.getCards(),pcm.getSize());
	}
	
	public void startTurn(){
		if (shallwefinish()) {//accuse aka mostprobably fuck up your game
			accuse();
			return;			
		}
		else if (false){ //take secret passage
			
		}
		else {// roll dice
			com.getDicePresenter().iWantToRollTheDice();
		}
		
	}
	
	public void move(){
		com.getAusloeser().suggestMoveToServer(pos);
	}
	
	public boolean shallwefinish(){
		return safeKarten.wieVielProzentHabenWir()  > Config.HOWMANYOFDEMCARDS/Config.ALLOFDEMCARDS;			
	}
	
	public void accuse(){
		CluedoStatement finalstatement = safeKarten.makeAccusingRandStatement();
		network.sendMsgToServer(NetworkMessages.accuseMsg(network.getGameId(),
				NetworkMessages.statement(finalstatement.getPerson().getPersonName(), finalstatement.getRoom().getName(), finalstatement.getWeapon().getName())));
	}
	
}
