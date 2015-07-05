package cluedoNetworkLayer;

import java.util.ArrayList;

import cluedoServer.TurnDFA;
import enums.PlayerStates;

public class CluedoStateMachine {
	TurnDFA dfa;
	PlayerStates currentState;
	
	public CluedoStateMachine(PlayerStates initState) {
		dfa = new TurnDFA(initState);
		currentState = initState;
	}
	
	public ArrayList<PlayerStates> getPossibleStatesFromCurrent(){		
		return getPossibleStatesFromState(currentState);
	}
	
	public ArrayList<PlayerStates> getPossibleStatesFromState(PlayerStates state){
		ArrayList<PlayerStates> ps = new ArrayList<PlayerStates>();
		ArrayList<Number> stateList = dfa.getPossibleStates(state.ordinal());
		for (Number i: stateList)
			ps.add(PlayerStates.getPlayerStateByOrdinal(i.intValue()));
		
		return ps;
	}
	
	
}
