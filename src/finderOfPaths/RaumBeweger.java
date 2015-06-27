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
		if(player.getCluedoPerson().getColor() == "Hans"){
			if(raum == Rooms.hall){
				System.out.println("haaaaaaaaaaaaall");
				zielImRaum.setKoordinate(11);
				zielImRaum.setyKoordinate(1);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.lounge){
				System.out.println("loooooounge");
				zielImRaum.setKoordinate(19);
				zielImRaum.setyKoordinate(2);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.diningroom){
				System.out.println("diningrooom");
				zielImRaum.setKoordinate(19);
				zielImRaum.setyKoordinate(11);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.kitchen){
				System.out.println("kiiiiiiiitchen");
				zielImRaum.setKoordinate(20);
				zielImRaum.setyKoordinate(20);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.ballroom){
				System.out.println("baaaaaaaallroom");
				zielImRaum.setKoordinate(11);
				zielImRaum.setyKoordinate(19);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.conservatory){
				System.out.println("cooooonservaaatory");
				zielImRaum.setKoordinate(2);
				zielImRaum.setyKoordinate(21);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.billiard){
				System.out.println("biiiilliiard");
				zielImRaum.setKoordinate(2);
				zielImRaum.setyKoordinate(13);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.library){
				System.out.println("libraryyyyyy");
				zielImRaum.setKoordinate(2);
				zielImRaum.setyKoordinate(7);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.study){
				System.out.println("stuuuuuuuuuuudy");
				zielImRaum.setKoordinate(2);
				zielImRaum.setyKoordinate(1);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.pool){
				System.out.println("poooooooooool");
				zielImRaum.setKoordinate(10);
				zielImRaum.setyKoordinate(9);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
		}
		else if(player.getCluedoPerson().getColor() == "blue"){
			if(raum == Rooms.hall){
				zielImRaum.setKoordinate(11);
				zielImRaum.setyKoordinate(2);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.lounge){
				zielImRaum.setKoordinate(20);
				zielImRaum.setyKoordinate(2);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.diningroom){
				zielImRaum.setKoordinate(19);
				zielImRaum.setyKoordinate(12);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.kitchen){
				zielImRaum.setKoordinate(21);
				zielImRaum.setyKoordinate(20);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.ballroom){
				zielImRaum.setKoordinate(11);
				zielImRaum.setyKoordinate(20);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.conservatory){
				zielImRaum.setKoordinate(3);
				zielImRaum.setyKoordinate(21);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.billiard){
				zielImRaum.setKoordinate(2);
				zielImRaum.setyKoordinate(14);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.library){
				zielImRaum.setKoordinate(2);
				zielImRaum.setyKoordinate(8);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.study){
				zielImRaum.setKoordinate(3);
				zielImRaum.setyKoordinate(1);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.pool){
				zielImRaum.setKoordinate(11);
				zielImRaum.setyKoordinate(9);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
		
		}
		else if(player.getCluedoPerson().getColor() == "green"){
			if(raum == Rooms.hall){
				zielImRaum.setKoordinate(11);
				zielImRaum.setyKoordinate(3);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.lounge){
				zielImRaum.setKoordinate(21);
				zielImRaum.setyKoordinate(2);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.diningroom){
				zielImRaum.setKoordinate(19);
				zielImRaum.setyKoordinate(13);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.kitchen){
				zielImRaum.setKoordinate(22);
				zielImRaum.setyKoordinate(20);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.ballroom){
				zielImRaum.setKoordinate(11);
				zielImRaum.setyKoordinate(21);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.conservatory){
				zielImRaum.setKoordinate(4);
				zielImRaum.setyKoordinate(21);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.billiard){
				zielImRaum.setKoordinate(2);
				zielImRaum.setyKoordinate(15);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.library){
				zielImRaum.setKoordinate(2);
				zielImRaum.setyKoordinate(9);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.study){
				zielImRaum.setKoordinate(4);
				zielImRaum.setyKoordinate(1);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.pool){
				zielImRaum.setKoordinate(12);
				zielImRaum.setyKoordinate(9);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
		
		}
		else if(player.getCluedoPerson().getColor() == "white"){
			if(raum == Rooms.hall){
				zielImRaum.setKoordinate(12);
				zielImRaum.setyKoordinate(1);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.lounge){
				zielImRaum.setKoordinate(19);
				zielImRaum.setyKoordinate(3);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.diningroom){
				zielImRaum.setKoordinate(20);
				zielImRaum.setyKoordinate(11);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.kitchen){
				zielImRaum.setKoordinate(20);
				zielImRaum.setyKoordinate(21);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.ballroom){
				zielImRaum.setKoordinate(12);
				zielImRaum.setyKoordinate(19);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.conservatory){
				zielImRaum.setKoordinate(2);
				zielImRaum.setyKoordinate(22);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.billiard){
				zielImRaum.setKoordinate(3);
				zielImRaum.setyKoordinate(13);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.library){
				zielImRaum.setKoordinate(3);
				zielImRaum.setyKoordinate(7);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.study){
				zielImRaum.setKoordinate(2);
				zielImRaum.setyKoordinate(2);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.pool){
				zielImRaum.setKoordinate(10);
				zielImRaum.setyKoordinate(13);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
		
		}
		else if(player.getCluedoPerson().getColor() == "yellow"){
			if(raum == Rooms.hall){
				zielImRaum.setKoordinate(12);
				zielImRaum.setyKoordinate(2);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.lounge){
				zielImRaum.setKoordinate(20);
				zielImRaum.setyKoordinate(3);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.diningroom){
				zielImRaum.setKoordinate(20);
				zielImRaum.setyKoordinate(12);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.kitchen){
				zielImRaum.setKoordinate(21);
				zielImRaum.setyKoordinate(21);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.ballroom){
				zielImRaum.setKoordinate(12);
				zielImRaum.setyKoordinate(20);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.conservatory){
				zielImRaum.setKoordinate(3);
				zielImRaum.setyKoordinate(22);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.billiard){
				zielImRaum.setKoordinate(3);
				zielImRaum.setyKoordinate(14);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.library){
				zielImRaum.setKoordinate(3);
				zielImRaum.setyKoordinate(8);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.study){
				zielImRaum.setKoordinate(3);
				zielImRaum.setyKoordinate(2);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.pool){
				zielImRaum.setKoordinate(11);
				zielImRaum.setyKoordinate(13);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
		
		}
		else if(player.getCluedoPerson().getColor() == "red"  || player.getCluedoPerson().getColor() == "Peter"){
			if(raum == Rooms.hall){
				zielImRaum.setKoordinate(12);
				zielImRaum.setyKoordinate(3);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.lounge){
				zielImRaum.setKoordinate(21);
				zielImRaum.setyKoordinate(3);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.diningroom){
				zielImRaum.setKoordinate(20);
				zielImRaum.setyKoordinate(13);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.kitchen){
				zielImRaum.setKoordinate(22);
				zielImRaum.setyKoordinate(21);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.ballroom){
				zielImRaum.setKoordinate(12);
				zielImRaum.setyKoordinate(21);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.conservatory){
				zielImRaum.setKoordinate(4);
				zielImRaum.setyKoordinate(22);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.billiard){
				zielImRaum.setKoordinate(3);
				zielImRaum.setyKoordinate(15);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.library){
				zielImRaum.setKoordinate(3);
				zielImRaum.setyKoordinate(9);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.study){
				zielImRaum.setKoordinate(4);
				zielImRaum.setyKoordinate(2);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
			}
			else if(raum == Rooms.pool){
				zielImRaum.setKoordinate(12);
				zielImRaum.setyKoordinate(13);
				zielImRaum = gui.getKachelArray()[zielImRaum.getyKoordinate()][zielImRaum.getxKoordinate()];
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
