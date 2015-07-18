package stateManager;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import enums.PlayerStates;
// CHECK ORDER OF PLAYERSTATES IF THIS DOES NOT RENDER EXPECTED RESULTS
// ITS STATEINDEXES ARE HARDCODED
public class TurnDFA {
	int currentState;
	int initStateOrdinal;
 	//int transitions[][] = {{1,2,5}, {4,7},	{3,7}, 	{4,5,7},	{6,7}, {0},	{0},	{},{9},{8}};
		 //						0		 1	  	2			3	  	  4	    5	 6		7  8  9
 	int transitions[][] = {
 	 		{//do nothing
 		 		PlayerStates.use_secret_passage.ordinal(),
 		 		PlayerStates.roll_dice.ordinal(),
 		 		PlayerStates.accuse.ordinal(),
 		 		PlayerStates.disprove.ordinal()
 	 		},
 	 		{ //use secret passage
 		 		PlayerStates.suspect.ordinal(),
 		 		PlayerStates.accuse.ordinal()
 	 		},	
 	 		{//rolldice
 	 			PlayerStates.move.ordinal(),
 	 			PlayerStates.accuse.ordinal()
 	 		}, 	
 	 		{//move
 	 			PlayerStates.suspect.ordinal(),
 	 			PlayerStates.end_turn.ordinal(),
 	 			PlayerStates.accuse.ordinal()
 	 		},	
 	 		{//suspect
 	 			//PlayerStates.disprove.ordinal(),
 	 			PlayerStates.accuse.ordinal(),
 	 			PlayerStates.end_turn.ordinal()
 	 		}, 
 	 		{//endturn
 	 			PlayerStates.do_nothing.ordinal()
 	 		},	
 	 		{//disprove
 	 			PlayerStates.do_nothing.ordinal()
 	 		},	
 	 		{
 	 			PlayerStates.do_nothing.ordinal()
 	 		},
 	 		{9},//donothing trapstate not protokoll covered
 	 		{8}//disprove trapstate not ...
 	 		};
 	 
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
 	
 	public int[] getSucStates(PlayerStates sstate){
 		return transitions[sstate.ordinal()];
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
