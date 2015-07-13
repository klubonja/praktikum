package kommunikation;

import kacheln.FeldKachel;
import kacheln.KachelContainer;
import kacheln.RaumKachel;
import kacheln.TuerKachel;
import staticClasses.Config;
import cluedoNetworkLayer.CluedoPosition;
import enums.Orientation;
import enums.Rooms;

public class ServerBoard {

	private KachelContainer kachelContainer;
	private char [] keineMoeglichkeiten;
	private char [][] keineMoeglichkeiten2;
	
	public ServerBoard(KachelContainer kacheln){
		this.kachelContainer = kacheln;
		setLayout();
	}
	
	/**
	 * Hier werden die Kacheln erzeugt und ihnen ein Platz zugewiesen.
	 * Auch wird die Gr��e der Kacheln festgelegt auf 29 x 29 und ihnen
	 * wird eine "Aufschrift" gegeben, je nach Position. 
	 * setLayout() weist zu, welche Art Kachel an welcher Position ist.
	 */
	public void setLayout(){
		
		for (int iReihen = 0; iReihen< Config.ROWS;iReihen++){
			for (int jSpalten = 0; jSpalten<Config.COLUMNS;jSpalten++){
				String aufschrift = +iReihen +"" +jSpalten;
				
				//////////////// Au�erhalb /////////////////
				
				if (jSpalten == 24){
					kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(new CluedoPosition(jSpalten,iReihen), true, null, null, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
				}
				
				else if ( iReihen == 25){
					kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(new CluedoPosition(jSpalten,iReihen), true, null, null, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
				}
				
				
				
				///////////////// RAUMKACHELN /////////////////
				
				else if ( (iReihen < 4 && jSpalten < 7) //Arbeitszimmer
						|| (iReihen == 4 && jSpalten == 0) ){
					if (iReihen == 3 && jSpalten == 6){ /////////////////// T�R ///////////////////
						kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.S, Rooms.study, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
					}
					
					else {///////////// RAUM ////////////////
						kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(  new CluedoPosition(jSpalten,iReihen), true, null, Rooms.study, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
					}
					
				}
					else if ( (5 < iReihen && iReihen < 11  && jSpalten < 6) //Bibliothek
						|| (6 < iReihen && iReihen < 10  && jSpalten == 6)
						|| (iReihen==11 && jSpalten == 0) ){
						if (iReihen == 10 && jSpalten == 3){/////////////////// T�R ///////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.S, Rooms.library, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else if (iReihen == 8  && jSpalten == 6){/////////////////// T�R ///////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.O, Rooms.library, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else {///////////// RAUM ////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(  new CluedoPosition(jSpalten,iReihen), true, null, Rooms.library, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
					}
				
					else if ( (11 < iReihen && iReihen < 17 && jSpalten < 6) //Billardzimmer
						|| (iReihen == 17 && jSpalten == 0) ){
						if  (iReihen == 15 && jSpalten == 5){/////////////////// T�R ///////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.O, Rooms.billiard, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else if(iReihen == 12 && jSpalten == 1) {/////////////////// T�R ///////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.N, Rooms.billiard, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else {///////////// RAUM ////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(new CluedoPosition(jSpalten,iReihen), true, null, Rooms.billiard, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
					}
				
					else if ( (18 < iReihen  && jSpalten < 5) //Wintergarten
						|| (19 < iReihen  && jSpalten < 6)
						|| (23 == iReihen  && jSpalten == 6)
						|| (iReihen == 24 && 5 < jSpalten && jSpalten < 9) ){
						if (iReihen == 19 && jSpalten == 4){/////////////////// T�R ///////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.O, Rooms.conservatory, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else {///////////// RAUM ////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(  new CluedoPosition(jSpalten,iReihen), true, null, Rooms.conservatory, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
					}
				
					else if ( (iReihen < 7  && 8 < jSpalten && jSpalten < 15) //Eingangshalle
						|| (iReihen == 0 && jSpalten == 8)
						|| (iReihen == 0 && jSpalten == 15) ){
						if  (iReihen == 6 && (jSpalten == 11 || jSpalten == 12) ){/////////////////// T�R ///////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.S, Rooms.hall, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else if (iReihen == 4 && jSpalten == 9) {/////////////////// T�R ///////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.W, Rooms.hall, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else {///////////// RAUM ////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(  new CluedoPosition(jSpalten,iReihen), true, null, Rooms.hall, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
					}
				
					else if ( (7 < iReihen && iReihen < 15  && 8 < jSpalten && jSpalten < 14) ){//Schwimmbad
						if  (iReihen == 14 && jSpalten == 11){/////////////////// T�R ///////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.S, Rooms.pool, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else if(iReihen == 9  && jSpalten == 13){/////////////////// T�R ///////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.O, Rooms.pool, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else if(iReihen == 8  && jSpalten == 11){/////////////////// T�R ///////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.N, Rooms.pool, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else if(iReihen == 12 && jSpalten == 9) {/////////////////// T�R ///////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.W, Rooms.pool, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else {///////////// RAUM ////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(  new CluedoPosition(jSpalten,iReihen), true, null, Rooms.pool, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
					}
				
					else if ((16 < iReihen && iReihen < 23  && 7 < jSpalten && jSpalten < 16) //Musikzimmer
						|| (22 < iReihen  && 9 < jSpalten && jSpalten < 14) ){
						if  (iReihen == 19 && jSpalten == 15){/////////////////// T�R ///////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.O, Rooms.ballroom, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else if (iReihen == 17 && (jSpalten == 9 || jSpalten == 14) ){/////////////////// T�R ///////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.N, Rooms.ballroom, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else if (iReihen == 19 && jSpalten == 8 ) {/////////////////// T�R ///////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.W, Rooms.ballroom, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else {///////////// RAUM ////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(  new CluedoPosition(jSpalten,iReihen), true, null, Rooms.ballroom, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
					}
				
					else if ( (iReihen < 6  && 16 < jSpalten) //Salon
						|| (iReihen == 6 && jSpalten == 23)
						|| (iReihen == 8 && jSpalten == 23) ){
						if (iReihen == 5 && jSpalten == 17){/////////////////// T�R ///////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.S, Rooms.lounge, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else {///////////// RAUM ////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(  new CluedoPosition(jSpalten,iReihen), true, null, Rooms.lounge, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
					}
				
					else if ( (8 < iReihen && iReihen < 15  && 15 < jSpalten) //Speisezimmer
						|| (iReihen == 15 && 18 < jSpalten)
						|| (iReihen == 16 && jSpalten==23) ){
						if (iReihen == 9 && jSpalten == 17){/////////////////// T�R ///////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.N, Rooms.diningroom, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else if (iReihen == 11 && jSpalten == 16){/////////////////// T�R ///////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.W, Rooms.diningroom, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else {///////////// RAUM ////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(  new CluedoPosition(jSpalten,iReihen), true, null, Rooms.diningroom, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
					}
				
					else if ( (17 < iReihen  &&  17 < jSpalten) //Kueche
						|| (iReihen == 23 && jSpalten == 17)
						|| (iReihen == 24 && 14 < jSpalten) ){
						if (iReihen == 18 && jSpalten == 19) {/////////////////// T�R ///////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new TuerKachel(new CluedoPosition(jSpalten,iReihen), true, Orientation.N, Rooms.kitchen, true, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
						
						else {///////////// RAUM ////////////////
							kachelContainer.setNewKachelPosition(iReihen, jSpalten, new RaumKachel(new CluedoPosition(jSpalten,iReihen), true, null, Rooms.kitchen, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
						}
					}
				
				///////////////////// FELDKACHELN ////////////////////////////
				
				    else {				    //Feldkacheln
				    	kachelContainer.setNewKachelPosition(iReihen, jSpalten, new FeldKachel(new CluedoPosition(jSpalten,iReihen), false, null,null, false, keineMoeglichkeiten, keineMoeglichkeiten2, null));
				    }
			}
		}
		
	}

	
}
