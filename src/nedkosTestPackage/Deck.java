package nedkosTestPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Eine Model Klasse die das Deck repraesentiert und alle Karten auffasst.
 * 
 * @author NedkoChulev
 *
 */
public class Deck {
	private final String GRUEN = "GRUEN";
	private final String GATOW = "GATOW";
	private final String PORZ = "PORZ";
	private final String BLOOM = "BLOOM";
	private final String GLORIA = "GLORIA";
	private final String WEISS = "WEISS";
	private final String ROHRZANGE = "ROHRZANGE";
	private final String LEUCHTER = "LEUCHTER";
	private final String DOLCH = "DOLCH";
	private final String PISTOLE = "PISTOLE";
	private final String HEIZUNGSROHR = "HEIZUNGSROHR";
	private final String SEIL = "SEIL";
	private final String BADEZIMMER = "BADEZIMMER";
	private final String ARBEITSZIMMER = "ARBEITSZIMMER";
	private final String SPEISEZIMMER = "SPEISEZIMMER";
	private final String SPIELERAUM = "SPIELERAUM";
	private final String GARAGE = "GARAGE";
	private final String SCHLAFZIMMER = "SCHLAFZIMMER";
	private final String WOHNZIMMER = "WOHNZIMMER";
	private final String KUECHE = "KUECHE";
	private final String HOF = "HOF";

	String[] antwort = new String[3];
	private List<String> personen = new ArrayList<String>();
	private List<String> waffen = new ArrayList<String>();
	private List<String> zimmer = new ArrayList<String>();
	private List<String> deck = new ArrayList<String>();
	private List<String> personenOrdered;
	private List<String> waffenOrdered;
	private List<String> zimmerOrdered;

	public Deck() {
		chooseThree();
		deckShullfe();
	}

	/**
	 * Fuegt den jeweiligen Typ von Karten in den jeweiligen Stapel ein.
	 */
	public void addKartenTypen() {
		personen.add(GRUEN);
		personen.add(GATOW);
		personen.add(PORZ);
		personen.add(BLOOM);
		personen.add(GLORIA);
		personen.add(WEISS);
		waffen.add(ROHRZANGE);
		waffen.add(LEUCHTER);
		waffen.add(DOLCH);
		waffen.add(PISTOLE);
		waffen.add(HEIZUNGSROHR);
		waffen.add(SEIL);
		zimmer.add(BADEZIMMER);
		zimmer.add(ARBEITSZIMMER);
		zimmer.add(SPEISEZIMMER);
		zimmer.add(SPIELERAUM);
		zimmer.add(GARAGE);
		zimmer.add(SCHLAFZIMMER);
		zimmer.add(WOHNZIMMER);
		zimmer.add(KUECHE);
		zimmer.add(HOF);

		personenOrdered = new ArrayList<String>(personen);
		waffenOrdered = new ArrayList<String>(waffen);
		zimmerOrdered = new ArrayList<String>(zimmer);
	}

	/**
	 * Waehlt die ersten 3 Karten, die den Mord repraesentieren und speichert
	 * sie in einem Array.
	 */
	public void chooseThree() {
		addKartenTypen();
		Collections.shuffle(personen);
		Collections.shuffle(waffen);
		Collections.shuffle(zimmer);
		antwort[0] = personen.get(0);
		antwort[1] = waffen.get(0);
		antwort[2] = zimmer.get(0);
		personen.remove(0);
		waffen.remove(0);
		zimmer.remove(0);
	}

	/**
	 * Intialisiert ein Deck und mischt es.
	 */
	public void deckShullfe() {
		deck.addAll(personen);
		deck.addAll(waffen);
		deck.addAll(zimmer);
		Collections.shuffle(deck);
		Collections.shuffle(deck);
	}

	public void getAntwort() {
		System.out.println("Gewinner Karten: " + this.antwort[0] + ", "
				+ this.antwort[1] + ", " + this.antwort[2] + ".");
	}

	public List<String> getDeck() {
		return this.deck;
	}

	public List<String> getPersonenOrdered() {
		return this.personenOrdered;
	}

	public List<String> getWaffenOrdered() {
		return this.waffenOrdered;
	}

	public List<String> getZimmerOrdered() {
		return this.zimmerOrdered;
	}
}
