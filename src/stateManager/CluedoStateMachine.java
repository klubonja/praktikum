package stateManager;

import java.util.ArrayList;

import enums.PlayerStates;

public class CluedoStateMachine {
	TurnDFA dfa;
	PlayerStates currentState;
	
	public CluedoStateMachine(PlayerStates initState) {
		dfa = new TurnDFA(initState);
		currentState = initState;
	}
	
	public ArrayList<PlayerStates> getPossibleStatesFromCurrentRecursive(){		
		return getPossibleStatesFromStateRecursive(currentState);
	}
	
	public ArrayList<PlayerStates> getPossibleStatesFromStateRecursive(PlayerStates state){
		ArrayList<PlayerStates> ps = new ArrayList<PlayerStates>();
		ArrayList<Number> stateList = dfa.getPossibleStates(state.ordinal());
		for (Number i: stateList)
			ps.add(PlayerStates.getPlayerStateByOrdinal(i.intValue()));
		
		return ps;
	}
	
	public String[] getSucStatesString(PlayerStates state){
		int[] states = dfa.getSucStates(state);
		String [] sucstates = new String[states.length];
		for (int i = 0; i < states.length; i++ )
			sucstates[i] = PlayerStates.getPlayerStateByOrdinal(states[i]).toString();
		
		return sucstates;
	}
	
	public ArrayList<PlayerStates> getSucStates(PlayerStates state){
		int[] states = dfa.getSucStates(state);
		ArrayList<PlayerStates> slist = new ArrayList<PlayerStates>();
		for (int i = 0; i < states.length; i++ )
			slist.add(PlayerStates.getPlayerStateByOrdinal(states[i]));
		
		return slist;
	}
	
	
}
