package enums;

import java.util.ArrayList;
import java.util.Arrays;

import model.Deck;

import org.json.JSONObject;

import staticClasses.auxx;
import cluedoServer.TurnDFA;

public class myclass {
	public myclass() {
		Persons[] persons = Persons.values();
		for (Persons p: persons){
			auxx.logfine(p.getColor());
			auxx.logfine(p.getPersonName());
			JSONObject json = new JSONObject();
			json.put("gameID", 3);
			json.put("nick", "nick");
			json.put("playerstate", PlayerStates.move);
			json.toString();
			//aux.sendTCPMsg(null, NetworkMessages.stateupdateMsg(gameID, nick, state);
			
			
		}
		
		
			
	}
	
	public static void main(String [] args)		{
		Deck cdeck = new Deck(4);
		cdeck.dealCluedoCards();
		
		auxx.loginfo(Arrays.toString(cdeck.getWinningHand()));
		auxx.loginfo(Arrays.toString(cdeck.getPoolCards()));
		
		TurnDFA dfa = new TurnDFA(PlayerStates.do_nothing);
		ArrayList<Number> l = dfa.getPossibleStates(7);
		for (Number i : l)
			System.out.println(i);
		
	}
}
