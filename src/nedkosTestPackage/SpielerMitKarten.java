package nedkosTestPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Eine Model Klasse fuer den Spieler, der eine Hand mit Karten halten kann.
 * 
 * @author NedkoChulev
 *
 */
public class SpielerMitKarten {
	private final String name;
	private List<String> kartenInHand = new ArrayList<String>();

	/**
	 * Constructor fuer den Spieler
	 * 
	 * @param name
	 *            String - der Name des Spielers
	 */
	public SpielerMitKarten(String name) {
		this.name = name;
	}

	/**
	 * Getter fuer den Namen des Spielers.
	 * 
	 * @return String - der Name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Eine Methode, die die verteilten Karten in die Hand des Spielers
	 * hinzufuegt.
	 * 
	 * @param karten
	 */
	public void giveCards(List<String> karten) {
		kartenInHand.addAll(karten);
	}

	/**
	 * Listet die Karten des Spielers auf.
	 */
	public String showCards() {
		String karten = "";
		for (String str : kartenInHand) {
			karten = karten + str + ", ";
		}
		return karten;
	}

}
