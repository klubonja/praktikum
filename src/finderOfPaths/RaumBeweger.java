package finderOfPaths;


import kacheln.Kachel;
import kacheln.TuerKachel;
import model.Player;
import view.BoardView;
import cluedoNetworkLayer.CluedoPlayer;
import enums.Rooms;

public class RaumBeweger {

private BoardView gui;
private CluedoPlayer player;
private TuerKachel aufenthalt;
private Kachel zielImRaum;


	public RaumBeweger(BoardView gui, CluedoPlayer player, TuerKachel aufenthalt){
		this.gui = gui;
		this.player = player;
		this.aufenthalt = aufenthalt;
		
		zielImRaum = gui.getKachelArray()[0][0];
		
		
	}

	public Rooms checkRaum(TuerKachel aufenthalt2) {
		if(aufenthalt.getRaum() == Rooms.hall){
			return Rooms.hall;
		}
		else if(aufenthalt.getRaum() == Rooms.lounge){
			return Rooms.lounge;
		}
		else if(aufenthalt.getRaum() == Rooms.diningroom){
			return Rooms.diningroom;
		}
		else if(aufenthalt.getRaum() == Rooms.kitchen){
			return Rooms.kitchen;
		}
		else if(aufenthalt.getRaum() == Rooms.ballroom){
			return Rooms.ballroom;
		}
		else if(aufenthalt.getRaum() == Rooms.conservatory){
			return Rooms.conservatory;
		}
		else if(aufenthalt.getRaum() == Rooms.billiard){
			return Rooms.billiard;
		}
		else if(aufenthalt.getRaum() == Rooms.library){
			return Rooms.library;
		}
		else if(aufenthalt.getRaum() == Rooms.study){
			return Rooms.study;
		}
		else if(aufenthalt.getRaum() == Rooms.pool){
			return Rooms.pool;
		}
		else{
			return null;
		}
	}
	
	public Kachel positionInRaum(CluedoPlayer player2, Rooms raum) {
		if(player.getCluedoPerson().getColor() == "yellow"){
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
		else if(player.getCluedoPerson().getColor() == "blue"){
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
		else if(player.getCluedoPerson().getColor() == "green"){
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
		else if(player.getCluedoPerson().getColor() == "white"){
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
		else if(player.getCluedoPerson().getColor() == "yellow"){
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
		else if(player.getCluedoPerson().getColor() == "red"  || player.getCluedoPerson().getColor() == "Peter"){
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

//	public void xySetzen(Kachel momentaneKachel){
//
//		for (int iReihen = 0; iReihen < gui.getKachelArray().length; iReihen++){
//			for (int jSpalten = 0; jSpalten < gui.getKachelArray()[iReihen].length; jSpalten++){
//				Kachel beginnKachel = gui.getKachelArray()[iReihen][jSpalten];
//				
//				if (beginnKachel.getMoeglichkeitenVonHier()!= null){
//					System.out.println("*********** party");
//					System.out.println("*********** iReihen : " +iReihen +"   ||  jSpalten : " +jSpalten);
//					int yStelle = gui.getRowIndex(momentaneKachel);
//					int xStelle = gui.getColumnIndex(momentaneKachel);
//					if ( ( (xStelle - xInsgesamt) == jSpalten) && ( (yStelle - yInsgesamt) == iReihen) ){
//						System.out.println("muhahahaha");
//						player.setxCoord(jSpalten);
//						player.setyCoord(iReihen);
//					}
//				}
//			}
//		}
//	
//	}
//	
//}
