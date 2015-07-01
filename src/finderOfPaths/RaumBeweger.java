package finderOfPaths;


import kacheln.Kachel;
import kacheln.RaumKachel;
import kacheln.TuerKachel;
import view.BoardView;
import cluedoNetworkLayer.CluedoPlayer;
import enums.Rooms;

public class RaumBeweger {

private BoardView gui;
private CluedoPlayer currentPlayer;
private Kachel aufenthalt;
private Kachel zielImRaum;


	public RaumBeweger(BoardView gui){
		this.gui = gui;
		this.currentPlayer = (CluedoPlayer) GanzTolleSpielerliste.playerManager.getCurrentObject();
		zielImRaum = gui.getKachelArray()[0][0];
	}

	public Rooms checkRaum( CluedoPlayer currentPlayer, Kachel aufenthalt) {
		this.aufenthalt = aufenthalt;
		this.currentPlayer = currentPlayer;
		if(aufenthalt.getRaum() != null){
			return aufenthalt.getRaum();
		}
		else{
			return null;
		}
	}
	
	
	public Kachel positionInRaum(CluedoPlayer player2, Rooms raum) {
		if(currentPlayer.getCluedoPerson().getColor() == "yellow"){
			if(raum == Rooms.hall){
				System.out.println("haaaaaaaaaaaaall");
				zielImRaum.getPosition().setX(11);
				zielImRaum.getPosition().setY(1);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.lounge){
				System.out.println("loooooounge");
				zielImRaum.getPosition().setX(19);
				zielImRaum.getPosition().setY(2);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.diningroom){
				System.out.println("diningrooom");
				zielImRaum.getPosition().setX(19);
				zielImRaum.getPosition().setY(11);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.kitchen){
				System.out.println("kiiiiiiiitchen");
				zielImRaum.getPosition().setX(20);
				zielImRaum.getPosition().setY(20);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.ballroom){
				System.out.println("baaaaaaaallroom");
				zielImRaum.getPosition().setX(11);
				zielImRaum.getPosition().setY(19);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.conservatory){
				System.out.println("cooooonservaaatory");
				zielImRaum.getPosition().setX(2);
				zielImRaum.getPosition().setY(21);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.billiard){
				System.out.println("biiiilliiard");
				zielImRaum.getPosition().setX(2);
				zielImRaum.getPosition().setY(13);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.library){
				System.out.println("libraryyyyyy");
				zielImRaum.getPosition().setX(2);
				zielImRaum.getPosition().setY(7);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.study){
				System.out.println("stuuuuuuuuuuudy");
				zielImRaum.getPosition().setX(2);
				zielImRaum.getPosition().setY(1);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.pool){
				System.out.println("poooooooooool");
				zielImRaum.getPosition().setX(10);
				zielImRaum.getPosition().setY(9);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
		}
		else if(currentPlayer.getCluedoPerson().getColor() == "blue"){
			if(raum == Rooms.hall){
				zielImRaum.getPosition().setX(11);
				zielImRaum.getPosition().setY(2);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.lounge){
				zielImRaum.getPosition().setX(20);
				zielImRaum.getPosition().setY(2);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.diningroom){
				zielImRaum.getPosition().setX(19);
				zielImRaum.getPosition().setY(12);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.kitchen){
				zielImRaum.getPosition().setX(21);
				zielImRaum.getPosition().setY(20);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.ballroom){
				zielImRaum.getPosition().setX(11);
				zielImRaum.getPosition().setY(20);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.conservatory){
				zielImRaum.getPosition().setX(3);
				zielImRaum.getPosition().setY(21);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.billiard){
				zielImRaum.getPosition().setX(2);
				zielImRaum.getPosition().setY(14);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.library){
				zielImRaum.getPosition().setX(2);
				zielImRaum.getPosition().setY(8);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.study){
				zielImRaum.getPosition().setX(3);
				zielImRaum.getPosition().setY(1);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.pool){
				zielImRaum.getPosition().setX(11);
				zielImRaum.getPosition().setY(9);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
		
		}
		else if(currentPlayer.getCluedoPerson().getColor() == "green"){
			if(raum == Rooms.hall){
				zielImRaum.getPosition().setX(11);
				zielImRaum.getPosition().setY(3);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.lounge){
				zielImRaum.getPosition().setX(21);
				zielImRaum.getPosition().setY(2);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.diningroom){
				zielImRaum.getPosition().setX(19);
				zielImRaum.getPosition().setY(13);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.kitchen){
				zielImRaum.getPosition().setX(22);
				zielImRaum.getPosition().setY(20);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.ballroom){
				zielImRaum.getPosition().setX(11);
				zielImRaum.getPosition().setY(21);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.conservatory){
				zielImRaum.getPosition().setX(4);
				zielImRaum.getPosition().setY(21);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.billiard){
				zielImRaum.getPosition().setX(2);
				zielImRaum.getPosition().setY(15);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.library){
				zielImRaum.getPosition().setX(2);
				zielImRaum.getPosition().setY(9);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.study){
				zielImRaum.getPosition().setX(4);
				zielImRaum.getPosition().setY(1);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.pool){
				zielImRaum.getPosition().setX(12);
				zielImRaum.getPosition().setY(9);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
		
		}
		else if(currentPlayer.getCluedoPerson().getColor() == "white"){
			if(raum == Rooms.hall){
				zielImRaum.getPosition().setX(12);
				zielImRaum.getPosition().setY(1);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.lounge){
				zielImRaum.getPosition().setX(19);
				zielImRaum.getPosition().setY(3);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.diningroom){
				zielImRaum.getPosition().setX(20);
				zielImRaum.getPosition().setY(11);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.kitchen){
				zielImRaum.getPosition().setX(20);
				zielImRaum.getPosition().setY(21);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.ballroom){
				zielImRaum.getPosition().setX(12);
				zielImRaum.getPosition().setY(19);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.conservatory){
				zielImRaum.getPosition().setX(2);
				zielImRaum.getPosition().setY(22);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.billiard){
				zielImRaum.getPosition().setX(3);
				zielImRaum.getPosition().setY(13);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.library){
				zielImRaum.getPosition().setX(3);
				zielImRaum.getPosition().setY(7);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.study){
				zielImRaum.getPosition().setX(2);
				zielImRaum.getPosition().setY(2);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.pool){
				zielImRaum.getPosition().setX(10);
				zielImRaum.getPosition().setY(13);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
		
		}
		else if(currentPlayer.getCluedoPerson().getColor() == "yellow"){
			if(raum == Rooms.hall){
				zielImRaum.getPosition().setX(12);
				zielImRaum.getPosition().setY(2);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.lounge){
				zielImRaum.getPosition().setX(20);
				zielImRaum.getPosition().setY(3);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.diningroom){
				zielImRaum.getPosition().setX(20);
				zielImRaum.getPosition().setY(12);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.kitchen){
				zielImRaum.getPosition().setX(21);
				zielImRaum.getPosition().setY(21);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.ballroom){
				zielImRaum.getPosition().setX(12);
				zielImRaum.getPosition().setY(20);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.conservatory){
				zielImRaum.getPosition().setX(3);
				zielImRaum.getPosition().setY(22);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.billiard){
				zielImRaum.getPosition().setX(3);
				zielImRaum.getPosition().setY(14);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.library){
				zielImRaum.getPosition().setX(3);
				zielImRaum.getPosition().setY(8);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.study){
				zielImRaum.getPosition().setX(3);
				zielImRaum.getPosition().setY(2);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.pool){
				zielImRaum.getPosition().setX(11);
				zielImRaum.getPosition().setY(13);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
		
		}
		else if(currentPlayer.getCluedoPerson().getColor() == "red"  || currentPlayer.getCluedoPerson().getColor() == "Peter"){
			if(raum == Rooms.hall){
				zielImRaum.getPosition().setX(12);
				zielImRaum.getPosition().setY(3);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.lounge){
				zielImRaum.getPosition().setX(21);
				zielImRaum.getPosition().setY(3);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.diningroom){
				zielImRaum.getPosition().setX(20);
				zielImRaum.getPosition().setY(13);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.kitchen){
				zielImRaum.getPosition().setX(22);
				zielImRaum.getPosition().setY(21);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.ballroom){
				zielImRaum.getPosition().setX(12);
				zielImRaum.getPosition().setY(21);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.conservatory){
				zielImRaum.getPosition().setX(4);
				zielImRaum.getPosition().setY(22);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.billiard){
				zielImRaum.getPosition().setX(3);
				zielImRaum.getPosition().setY(15);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.library){
				zielImRaum.getPosition().setX(3);
				zielImRaum.getPosition().setY(9);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.study){
				zielImRaum.getPosition().setX(4);
				zielImRaum.getPosition().setY(2);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
			else if(raum == Rooms.pool){
				zielImRaum.getPosition().setX(12);
				zielImRaum.getPosition().setY(13);
				zielImRaum = gui.getKachelArray()[zielImRaum.getPosition().getY()][zielImRaum.getPosition().getX()];
			}
		
		}
		return zielImRaum;
	}
}	

