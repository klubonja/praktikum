package stateManager;

import java.util.Stack;

import kommunikation.ServerBeweger;
import kommunikation.ServerBoard;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;
import enums.GameStates;
import enums.PlayerStates;
import finderOfPaths.PlayerCircleManager;

public class StateManager {
	GameStates currentState;
	CluedoStateMachine stateMachine;
	PlayerCircleManager pcm;
	ServerBeweger beweger;
	
	public StateManager(PlayerCircleManager pcm,ServerBeweger beweger){
		this.pcm = pcm;
		beweger = beweger;
	}
	
	public void setNextTurn(){
		Stack<CluedoPlayer> players  = pcm.getPlayerManager();
		for (int i = 0;i < pcm.getSize(); i++){
			if (i == pcm.getIndex()){
				CluedoPlayer nextplayer = players.get(i);
				nextplayer.setPossibleStates(stateMachine.getSucStates(PlayerStates.do_nothing));
				if (nextplayer.hasAccused()){
					nextplayer.setPossibleState(PlayerStates.do_nothing);
					pcm.next();
					setNextTurn();
					break;
				}
				else if (!beweger.secretPassagePossible(nextplayer.getCluedoPerson())) {
					nextplayer.removeFromPossibleStates(PlayerStates.use_secret_passage);
				}
				nextplayer.removeFromPossibleStates(PlayerStates.disprove);
			}
			else{
				players.get(i).setDoNothing(); // hier werden possible moves geleert und do nothing hinzugefügt
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
					curplayer.addPossibleState(PlayerStates.end_turn);
				}
				break;
		}
	}
	
	public void handleDisprove(int curindex){
		for (int i = 0; i < pcm.getSize(); i++ ){
			if (curindex == i) pcm.getPlayerByIndex(curindex).setPossibleState(PlayerStates.disprove);
			else pcm.getPlayerByIndex(curindex).setPossibleState(PlayerStates.do_nothing);
		}
	}
	
}
