package kommunikation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import animation.RaumBeweger;
import kacheln.Kachel;
import kacheln.KachelContainer;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;
import enums.Persons;
import enums.Rooms;

/**
 * Unser Server will (berechtigterweise) auch mal bewegen
 * @since ca. 15.06.2015
 * @version 21.06.2015
 * @author Benedikt Mayer
 *
 */
public class ServerBeweger {
	
	private PlayerCircleManager pcManager;
	private KachelContainer kacheln;
	private RaumBeweger raumBeweger;

	/**
	 * Hier wird der Beweger fuer den Server erstellt.
	 * @param pcManager der pcManager, warum auch nicht.
	 * @param kacheln enthaelt alle Spielfeldkacheln und Informationen darueber
	 * @param raumBeweger bewegt Dinge in Raeumen. Creepy.
	 */
	public ServerBeweger(PlayerCircleManager pcManager, KachelContainer kacheln, RaumBeweger raumBeweger){
		this.pcManager = pcManager;
		this.kacheln = kacheln;
		this.raumBeweger = raumBeweger;
	}
	
	/**
	 * Setzt die Positionen (Ziemlich brachial) auf neue Positionen
	 * @param person wer gesetzt werden soll
	 * @param position da geht's hin
	 */
	public void setNewPosition(Persons person, CluedoPosition position){
		pcManager.getPlayerByPersonName(person.getPersonName()).getPosition().setX(position.getX());
		pcManager.getPlayerByPersonName(person.getPersonName()).getPosition().setY(position.getY());
	}
	
	/**
	 * Hier wird gecheckt ob es denn auch legal ist sich hier hin zu bewegen.
	 * Keine Macht den Drogen!
	 * @param position darf man hierhin?
	 * @return true, falls diese Position erreichbar ist.
	 */
	public boolean movePossible(CluedoPosition position){
		int xZiel = position.getX();
		int yZiel = position.getY();
		
		if (kacheln.getKacheln()[yZiel][xZiel].getMoeglichkeitenHierher() != null){
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/**
	 * Hier wird gecheckt ob man von hier aus den Geheimgang nutzen darf
	 * @param person wer versucht den Geheimgang zu nutzen?
	 * @return true, falls man von hier aus einen Geheimgang nutzen kann.
	 */
	public boolean secretPassagePossible(Persons person){
		CluedoPosition pos = pcManager.getPlayerByPerson(person).getPosition();
		Kachel k = kacheln.getKachelAt(pos.getY(), pos.getX());
		if (k.isIstRaum()){
			List<Rooms> corners = new ArrayList<Rooms>(Arrays.asList(Rooms.study,Rooms.kitchen,Rooms.lounge,Rooms.conservatory));
			return corners.contains(k.getRaum());
		}
		
		return false;
	}
	
	/**
	 * Ist das hier denn auch ein raum
	 * @param pos hier wird ueberprueft ob es hier Raeume gibt.
	 * @return true, falls hier ein raum ist. Sonst false.
	 */
	public boolean isRaum(CluedoPosition pos){
		return kacheln.getKacheln()[pos.getY()][pos.getX()].isIstRaum();	
	}
	
	/**
	 * Du willst also den Geheimgang benutzen? Gewagt, gewagt.
	 * @param player der wagemuetige, welcher den Gang benutzen will
	 * @return eine CluedoPosition, wo sich dann schoen hinbewegt wird.
	 */
	public CluedoPosition useSecretPassage(CluedoPlayer player){
		CluedoPosition position = player.getPosition();
		if (kacheln.getKacheln()[position.getY()][position.getX()].getRaum()==Rooms.study){
			position.setX(raumBeweger.positionInRaum(player, Rooms.kitchen).getPosition().getX());
			position.setY(raumBeweger.positionInRaum(player, Rooms.kitchen).getPosition().getY());
		}
		else if (kacheln.getKacheln()[position.getY()][position.getX()].getRaum()==Rooms.kitchen){
			position.setX(raumBeweger.positionInRaum(player, Rooms.study).getPosition().getX());
			position.setY(raumBeweger.positionInRaum(player, Rooms.study).getPosition().getY());
		}
		else if (kacheln.getKacheln()[position.getY()][position.getX()].getRaum()==Rooms.lounge){
			position.setX(raumBeweger.positionInRaum(player, Rooms.conservatory).getPosition().getX());
			position.setY(raumBeweger.positionInRaum(player, Rooms.conservatory).getPosition().getY());
		}
		else if (kacheln.getKacheln()[position.getY()][position.getX()].getRaum()==Rooms.conservatory){
			position.setX(raumBeweger.positionInRaum(player, Rooms.lounge).getPosition().getX());
			position.setY(raumBeweger.positionInRaum(player, Rooms.lounge).getPosition().getY());
		}
		else {
			return null;
		}
		return position;
	}
	
	
	

	
}
