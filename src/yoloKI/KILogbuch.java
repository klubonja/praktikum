package yoloKI;

import java.util.ArrayList;

public class KILogbuch {

	private int anzahlTurns = 0;
	private int anzahlAktionen = 0;
	private int anzahlSpieler = 0;
	
	private ArrayList<KIAktion> aktionen = new ArrayList<KIAktion>();
	private ArrayList<KIPerson> personen = new ArrayList<KIPerson>();
	
	public KILogbuch(int anzahlSpieler){
		this.anzahlSpieler = anzahlSpieler;
	}
	
	public int wieVielteRunde(){
		int dieseRunde = 0;
		dieseRunde = (anzahlTurns / anzahlSpieler) +1;
		return dieseRunde;
	}
	
	public ArrayList<KIAktion> getLastAktionenByRounds(int rounds){
		ArrayList<KIAktion> letzteAktionen = new ArrayList<KIAktion>();
		int turns = rounds * anzahlSpieler;
		for (int i = anzahlTurns-turns; i < anzahlTurns; i++){
			letzteAktionen.add(getAktionByAnzahlAktionen(i));
		}
		return letzteAktionen;
	}
	
	public ArrayList<KIAktion> getLastAktionenByTurns(int turns){
		ArrayList<KIAktion> letzteAktionen = new ArrayList<KIAktion>();
		for (int i = anzahlTurns-turns; i < anzahlTurns; i++){
			letzteAktionen.add(getAktionByAnzahlAktionen(i));
		}
		return letzteAktionen;
	}
	
	public boolean iKnowWhatYouDidLastSummer(KIAktion aktion, KIPerson person){
		if (getAktionByPerson(person).contains(aktion)){
			return true;
		}
		else {
			return false;
		}
	}
	
	public ArrayList<KIAktion> getAktionByPerson(KIPerson person){
		ArrayList<KIAktion> dieseAktionen = new ArrayList<KIAktion>();
		for (int zaehler = 0; zaehler < aktionen.size(); zaehler++)
		{
			if (personen.get(zaehler).equals(person)){
				dieseAktionen.add(aktionen.get(zaehler));
			}
		}
		return dieseAktionen;
	}
	
	public ArrayList<KIPerson> getPersonenByAktion(KIAktion aktion){
		ArrayList<KIPerson> diesePersonen = new ArrayList<KIPerson>();
		for (int zaehler = 0; zaehler < aktionen.size(); zaehler++)
		{
			if (aktionen.get(zaehler).equals(aktion)){
				diesePersonen.add(personen.get(zaehler));
			}
		}
		return diesePersonen;
	}
	
	public void nextAktion(){
		anzahlAktionen++;
	}
	public void nextTurn(){
		anzahlTurns++;
	}
	
	public KIAktion getCurrentAktion(){
		return aktionen.get(anzahlAktionen);
	}
	
	public KIPerson getCurrentPerson(){
		return personen.get(anzahlAktionen);
	}
	
	public KIAktion getAktionByAnzahlAktionen(int anzahlAktionen){
		return aktionen.get(anzahlAktionen);
	}
	
	public KIPerson getPersonByAnzahlAktionen(int anzahlAktionen){
		return personen.get(anzahlAktionen);
	}
	
	public int getAnzahlTurns(){
		return anzahlTurns;
	}
	
	public int getAnzahlAktionen() {
		return anzahlAktionen;
	}

	public void addAktion(KIAktion aktion, KIPerson person){
		aktionen.add(aktion);
		personen.add(person);
		nextAktion();
	}
	
}
