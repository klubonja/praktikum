package enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import model.Deck;

import org.json.JSONObject;

import stateManager.TurnDFA;
import staticClasses.auxx;
import cluedoNetworkLayer.CluedoFieldInt;

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
		
		Stack<Persons> pl = new Stack<Persons>();
		pl.addAll(Arrays.asList(Persons.values()));
		//Collections.shuffle(pl);
		for (Persons pe: pl)
			System.out.print(pe.getColor()+"\n");
		System.out.print("\n");
		//Collections.shuffle(pl);
		for (Persons pe: pl)
			System.out.print(pe.getColor()+"\n");
		System.out.print("\n");
		//setStart(pl,Color.YELLOW);
		for (Persons pe: pl)
			System.out.print(pe.getColor()+"\n");
		
		//diceresult
		CluedoFieldInt start = new CluedoFieldInt(1, 1);
		int i = 2;
		//playfield
		ArrayList<CluedoFieldInt> fields = makeField(23, 23);
		//maxfield limited by dicevalue
		ArrayList<CluedoFieldInt> maxfields = makeMaxField(i, start);
		CluedoFieldInt startpos = new CluedoFieldInt(0,0);

		
		
	}
	
	public static ArrayList<CluedoFieldInt> makeMaxField(int i, CluedoFieldInt start){
		ArrayList<CluedoFieldInt> fields = new ArrayList<CluedoFieldInt>();
		for(int height = start.getY()-i; height < start.getY()+i; height++){
			for(int width = start.getX()-i; width < start.getX()+i; width++){
				fields.add(new CluedoFieldInt(width, height));
			}
		}
		
		return fields;
	}
	
//	ArrayList<CluedoFieldInt> findPath(int i,ArrayList<CluedoFieldInt> field,CluedoFieldInt start){
//		ArrayList<CluedoFieldInt> pos = new ArrayList<CluedoFieldInt>();
//		ArrayList<CluedoFieldInt> context = makeField(start.getX()-1, start.getY()+1);
//		
//			for (i = i; i < 23; i++){
//				ArrayList<CluedoFieldInt> context = makeField(start.getX()-1, start.getY()+1);
//			}
//		}
//		if (i == 0) return pos;
//		
//		return findPath(i-1,)
//	}
	
//	public static ArrayList<CluedoFieldInt> checkContext(ArrayList<CluedoFieldInt> field,CluedoPosition pos){
//		ArrayList<CluedoFieldInt> fields = new ArrayList<CluedoFieldInt>();
//		for (int i = 0;)
//	}
//	
	public static ArrayList<CluedoFieldInt> makeField(int width,int height){
		ArrayList<CluedoFieldInt> fields = new ArrayList<CluedoFieldInt>();
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				fields.add(new CluedoFieldInt(x,y));
			}
		}
		
		return fields;
	}
	
}
