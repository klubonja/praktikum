package cluedoNetworkGUI;


import com.sun.media.jfxmedia.events.PlayerStateEvent.PlayerState;

import enums.PlayerStates;

public class DrawStateMachine {
	int currentState;
	PlayerStates state;
	
	PlayerStates[] states = PlayerStates.values();
	
 	int transitions[][] = {{1,2,5},{4,8},{3,8},{4,8},{8,9},{0},{7},{6},{7},{0}};
 	//						0		1	  2		3	  4	    5	6	7   8      9
 	
 	public DrawStateMachine(PlayerStates initState) {
		currentState = initState.ordinal();
	}
 	
 	public boolean consumeInput(PlayerState input){
 		if (contains(input.ordinal(),transitions[currentState])) {
 			currentState = input.ordinal();
 			return true;
 		}	
 		return false;
 	}
 	
 	private static boolean contains(int n,int[] ar){
 		for (int i = 0;i < ar.length; i++)
 			if (n == ar[i]) 
 				return true;
 		return false;
 	}
}
