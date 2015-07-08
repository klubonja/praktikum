package cluedoServer;

import java.util.ArrayList;
import java.util.LinkedHashSet;

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
 	
 	public ArrayList<Number> getPossibleStates(int initS){
 		ArrayList<Number> states = new ArrayList<Number>();
 		//System.out.println("checking state "+initS);
 		for (int stateOrdinal : transitions[initS]){ // iteriere alle nachfolgestates 
 			//System.out.println("in state :"+ initS +" handling :"+stateOrdinal);
 			if (checkfortrans(stateOrdinal,initS)){ 
 				states.add(stateOrdinal); // jeder folgezustand ist ein möglicher zustand
 				if (stateOrdinal != initStateOrdinal) // wenn anfangsstate erreicht ist breche ab
 					states.addAll(getPossibleStates(stateOrdinal));//gehe alle kanten
 			}
 		}
 		
 		return new ArrayList<Number>(new LinkedHashSet<Number>(states)); // nasty way of removing dups
 	}
 	
 	public ArrayList<Number> getSucStates(PlayerStates sstate){
 		return null;
 	}
 	
 	public ArrayList<Number> getPossibleStates(ArrayList<Number> statelist){
 		ArrayList<Number> pstates = new ArrayList<Number>();
 		for (Number initS : statelist){
 			pstates.addAll(getPossibleStates(initS.intValue()));
 		}
 		
 		return pstates;
 	}

 	public boolean consumeInput(PlayerStates input){ // if is successor of current state set new input state
 		if (contains(input.ordinal(),transitions[currentState]) || transitions[currentState].length ==  0 ) {
 			currentState = input.ordinal();
 			return true;
 		}
 		return false;
 	}
 	
 	
 	public boolean checkfortrans(int stateOrdinal,int currentState){ // if is successor of current state set new input state
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
 	
 	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}
}
