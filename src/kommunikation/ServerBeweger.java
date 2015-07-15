package kommunikation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kacheln.Kachel;
import kacheln.KachelContainer;
import cluedoNetworkLayer.CluedoPlayer;
import cluedoNetworkLayer.CluedoPosition;
import enums.Persons;
import enums.Rooms;
import finderOfPaths.PlayerCircleManager;
import finderOfPaths.RaumBeweger;

public class ServerBeweger {
	
	private PlayerCircleManager pcManager;
	private KachelContainer kacheln;
	private Kachel geheimGangKachel;
	private RaumBeweger raumBeweger;
	
	public ServerBeweger(PlayerCircleManager pcManager, KachelContainer kacheln, RaumBeweger raumBeweger){
		this.pcManager = pcManager;
		this.kacheln = kacheln;
		this.raumBeweger = raumBeweger;
	}
	
	public void setNewPosition(Persons person, CluedoPosition position){
		pcManager.getPlayerByPersonName(person.getPersonName()).getPosition().setX(position.getX());
		pcManager.getPlayerByPersonName(person.getPersonName()).getPosition().setY(position.getY());
	}
	
	/**
	 * 
	 * @param position
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
	 * 
	 * @param person
	 * @return true, falls man von hier aus einen Geheimgang nutzen kann.
	 */
	public boolean secretPassagePossible(Persons person){
		CluedoPosition pos = pcManager.
				getPlayerByPerson(
						person).
						getPosition();
		System.out.println("query kachal at y: "+pos.getY()+" x : "+pos.getX());
		Kachel k = kacheln.getKachelAt(pos.getY(), pos.getX());
		if (k.isIstRaum()){
			List<Rooms> corners = new ArrayList<Rooms>(Arrays.asList(Rooms.study,Rooms.kitchen,Rooms.lounge,Rooms.conservatory));
			return corners.contains(k.getRaum());
		}
		
		return false;
	}
	
	public boolean isRaum(CluedoPosition pos){
		return kacheln.getKacheln()[pos.getY()][pos.getX()].isIstRaum();	
	}
	
	public void useSecretPassage(Persons person){
		CluedoPlayer player = pcManager.getPlayerByPerson(person);
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
		
	}
	
	
	

	
}
