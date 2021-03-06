package stateManager;

import java.util.Stack;

import kommunikation.PlayerCircleManager;
import kommunikation.ServerBeweger;
import cluedoNetworkLayer.CluedoPlayer;
import enums.GameStates;
import enums.PlayerStates;

public class StateManager {
	GameStates currentState;
	CluedoStateMachine stateMachine = new CluedoStateMachine(PlayerStates.do_nothing);
	PlayerCircleManager pcm;
	ServerBeweger beweger;
	
	public StateManager(PlayerCircleManager pcm,ServerBeweger beweger){
		this.pcm = pcm;
		this.beweger = beweger;
	}
	
	public void setNextTurnRec(){
		Stack<CluedoPlayer> players  = pcm.getPlayers();
		for (int i = 0;i < pcm.getSize(); i++){
			if (i == pcm.getCurrentPlayerIndex()){
				CluedoPlayer nextplayer = players.get(i);
				nextplayer.setPossibleStates(stateMachine.getSucStates(PlayerStates.do_nothing));
				System.out.println("currentplayer :"+ nextplayer.getNick()+" has states :"+nextplayer.getStatesAsStringFormatted());
				if (nextplayer.hasAccused()){
					nextplayer.setPossibleState(PlayerStates.do_nothing);
					pcm.next(); // HIER WIRD DAS EINZIGE MAL IN DIESER KLASSE DER SPIELERPOINTER VERÄNDERT
					setNextTurnRec();
					break;
				}
				else if (!beweger.secretPassagePossible(nextplayer.getCluedoPerson())) {
					nextplayer.removeFromPossibleStates(PlayerStates.use_secret_passage);
				}
				nextplayer.removeFromPossibleStates(PlayerStates.disprove);
			}
			else{
				players.get(i).setPossibleState(PlayerStates.do_nothing); // hier werden possible moves geleert und do nothing hinzugefügt
			}			
		}
	}
	
	public void transitionByAction(PlayerStates actionstate){
		CluedoPlayer curplayer = pcm.getCurrentPlayer();
		curplayer.setPossibleStates(
					stateMachine.getSucStates(actionstate)
					);
		switch (actionstate) {
			case move :
				if (!beweger.isRaum(curplayer.getPosition())){
					curplayer.removeFromPossibleStates(PlayerStates.suspect);
				}
				break;
		}
		System.out.println("currentplayer :"+ curplayer.getNick()+" has states :"+curplayer.getStatesAsStringFormatted());
	}
	
	public void handleDisprove(int curindex){
		for (int i = 0; i < pcm.getSize(); i++ ){
			if (curindex == i) pcm.getPlayerByIndex(i).setPossibleState(PlayerStates.disprove);
			else pcm.getPlayerByIndex(i).setPossibleState(PlayerStates.do_nothing);
		}
	}
	
}
