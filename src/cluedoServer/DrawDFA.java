package cluedoServer;

import enums.PlayerStates;

public class DrawDFA {
	int currentState;

 	int transitions[][] = {{1,2,5},{4,7},{3,7},{4,7},{7,9},{0},{0},{},{9},{8}};
 	//						0		1	  2		3	  4	    5	6	7  8  9

 	public DrawDFA(PlayerStates initState) {
		currentState = initState.ordinal();
	}

 	public boolean consumeInput(PlayerStates input){
 		if (contains(input.ordinal(),transitions[currentState]) || transitions[currentState].length ==  0 ) {
 			currentState = input.ordinal();
 			return true;
 		}
 		return false;
 	}

 	private static boolean contains(int n,int[] ar){
 		for (int i = 0;i < ar.length; i++)
 			if (n == ar[i]) return true;
 		return false;
 	}
}
