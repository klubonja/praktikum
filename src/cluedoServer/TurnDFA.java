package cluedoServer;

import java.util.ArrayList;

import enums.PlayerStates;

public class TurnDFA {
	int currentState;
	int initStateOrdinal;

 	int transitions[][] = {{1,2,5}, {4,7},	{3,7}, 	{4,5,7},	{6,7}, {0},	{0},	{},{9},{8}};
 	//						0		 1	  	2			3	  	  4	    5	 6		7  8  9
 	//						do n     use s   roll d	  move   	  sus   disp end	acc
 	public TurnDFA(PlayerStates initState) {
		currentState = initState.ordinal();
		initStateOrdinal =  initState.ordinal();
		 
	}

 	public boolean consumeInput(PlayerStates input){ // if is successor of current state set new input state
 		if (contains(input.ordinal(),transitions[currentState]) || transitions[currentState].length ==  0 ) {
 			currentState = input.ordinal();
 			return true;
 		}
 		return false;
 	}
 	
 	
 	public boolean checkfortrans(int stateOrdinal){ // if is successor of current state set new input state
 		if (contains(stateOrdinal,transitions[currentState]) || transitions[currentState].length ==  0 ) {
 			return true;
 		}
 		return false;
 	}

 	private static boolean contains(int n,int[] ar){
 		for (int i = 0;i < ar.length; i++)
 			if (n == ar[i]) return true;
 		return false;
 	}
 	
 	public ArrayList<Number> getPossibleStates(int initS){
 		ArrayList<Number> states = new ArrayList<Number>();
 		for (int stateOrdinal : transitions[initS]){ // iteriere alle states 
 			if (checkfortrans(stateOrdinal)){ 
 				if (stateOrdinal== initStateOrdinal) return states; // wenn anfangsstate erreicht ist breche ab
 				states.add(stateOrdinal); // jeder folgezustand ist ein möglicher zustand
 				states.addAll(getPossibleStates(stateOrdinal));//gehe alle kanten
 			}
 		}
 		
 		return states;
 	}
}
