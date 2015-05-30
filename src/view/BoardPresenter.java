package view;

import view.Kachel;
import javafx.animation.PathTransition;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.Player;


/**
 * @since 24.05.2015
 * @version 28.05.2015
 * @author Benedikt Mayer
 *
 * 
 */	
public class BoardPresenter{

	
	private Player player;
	private int rowSize = 25;
	private int columnSize = 24;
	
	private int xDistanz;
	private int yDistanz;	
	private int xErlaubt;
	private int yErlaubt;
	
	private int jetzigeReihe;
	private int jetzigeColumn;
	private Kachel jetzigesFeld;
	
	private Circle playerDarstellung;
	
	private int xPositionFuerPath;
	private int yPositionFuerPath;	
	private int xStreckeFuerPath;
	private int yStreckeFuerPath;
	
	private int xRichtung;
	private int yRichtung;
	private int xZiel;
	private int yZiel;
	
	private String textbuffer;
	private Background backgroundbuffer, backgroundbuffer2;;
	private Font fontbuffer;

	private int ausweichen;
	private boolean aussenrum;
	
	private int first;
	private int second;
	private int augenzahl;
	
	BoardView view;
	
	/**
	 * Hier wird die Stage gestartet und den Kacheln eine Methode für 
	 * setOnMouseClicked zugewiesen.
	 * @param primaryStage 
	 * @throws Exception
	 */
	public BoardPresenter(BoardView view, Circle Playerdarstellung, Player player){
		this.player = player;
		this.view=view;
		zuweisung();
		this.playerDarstellung = Playerdarstellung;
	}
		

	public void zuweisung(){
		dice(view.getLabelArray()[player.getyCoord()][player.getxCoord()]);
		for (int i = 0; i<view.getLabelArray().length-1;i++){
			for (int j = 0; j<view.getLabelArray()[j].length-1;j++){
				Kachel momentaneKachel = view.getLabelArray()[i][j];
					if (momentaneKachel.isIstRaum()==false){
						momentaneKachel.setOnMouseClicked(e -> dasIstEinFeld(momentaneKachel));
					}
					else{
						momentaneKachel.setOnMouseClicked(e -> dasIsEinRaum());						
					}
				momentaneKachel.setOnMouseExited(e -> persil(momentaneKachel));
				momentaneKachel.setOnMouseEntered(e -> einfaerben(momentaneKachel));				
			}
		}		
	}
	
	/**
	 * Färbt die Kachel ein bzw. setzt den Text auf "nope".
	 * @param momentaneKachel
	 */
	public void einfaerben(Kachel momentaneKachel){
		if (momentaneKachel.isIstRaum()==true){
			textbuffer = momentaneKachel.getText();
			fontbuffer = momentaneKachel.getFont();
			momentaneKachel.setFont(Font.font ("Regular", 17));
			momentaneKachel.setText("nope");
		}
		else {
		backgroundbuffer = momentaneKachel.getBackground();
		momentaneKachel.setBackgroundColor(momentaneKachel, Color.GREEN);
		}
	}
	/**
	 * Färbt die begehbaren Kacheln ein
	 * @param begehbareKachel
	 */
	public void einfaerben2(Kachel begehbareKachel){
		backgroundbuffer2 = begehbareKachel.getBackground();
		begehbareKachel.setBackgroundColor(begehbareKachel, Color.DARKORANGE);
	}
	
	/**
	 * Bereinigt die Kachel von Schmutz und Farben aller Art!
	 * @param momentaneKachel
	 */
	public void persil(Kachel momentaneKachel){
		if (momentaneKachel.isIstRaum()==true){
			momentaneKachel.setText(textbuffer);
			momentaneKachel.setFont(fontbuffer);
		}
		else {
			momentaneKachel.setBackground(backgroundbuffer);			
		}
	}
	
	/**
	 * Bereinigt die Kacheln, die im letzten Zug begehbar waren
	 * @param momentaneKachel
	 */
	public void persil2(Kachel momentaneKachel){
		momentaneKachel.setBackground(backgroundbuffer2);			
	}
	/**
	 * Wird von der Kachel.OnClick aufgerufen und löst die movePlayer Methode aus.
	 */
	public void dasIstEinFeld(Kachel ziel){
		movePlayer(ziel);
	}
	

	/**
	 * Wird ausgelöst, wenn jemand auf einen Raum clickt und gibt eine nette Nachricht zurück.
	 */
	public void dasIsEinRaum(){
		System.out.println("Das ist ein Raum, alter");
	}
	
	
	/**
	 * Die Methode, welche durch das ClickEvent ausgelöst wird.
	 * hier wird pathfinder und moveWithPath ausgelöst.
	 * @param PlayerDarstellung Der zu bewegende Player
	 * @param ziel Die Zielkachel
	 */	
	public void movePlayer(Kachel ziel){
		System.out.println("----------------------");
		System.out.println("move whatever");
		clearDice(view.getLabelArray()[player.getyCoord()][player.getxCoord()]);
		persil2(view.getLabelArray()[player.getyCoord()][player.getxCoord()]);
		dice(ziel);
		reset(ziel);
			
		System.out.println("vorher : x Distanz   " +xDistanz);
		System.out.println("vorher : y Distanz   " +yDistanz);
		
		
		while( (xDistanz != 0) && (yDistanz != 0) ){
			pathfinder(xDistanz, yDistanz, jetzigesFeld);
			//newPathfinder(xDistanz, yDistanz, jetzigesFeld);
		}
		
		while( (xDistanz != 0) ^ (yDistanz != 0) ){
			pathfinder(xDistanz, yDistanz, jetzigesFeld);
		}
		/*
		while( (xDistanz != 0) && (yDistanz != 0) ){
			if (xDistanz > 0 || xDistanz < 0){
				pathfinder...
				movewithpath
			}
			...y
		}
		*/
			

		System.out.println("nachher : x Distanz   " +xDistanz);
		System.out.println("nachher : y Distanz   " +yDistanz);
			
		// Animation
	    moveWithPath(this.xErlaubt, this.yErlaubt);				
		
		System.out.println("Reihe : " +(view.getRowIndex(this.jetzigesFeld)+1));
		System.out.println("Spalte : " +(view.getColumnIndex(this.jetzigesFeld)+1));
		
		System.out.println("x Player : " +player.getxCoord() +"| x Ziel : " +xZiel);
		System.out.println("y Player : " +player.getyCoord() +"| y Ziel : " +yZiel);
			
			
		if (player.getxCoord() == xZiel && player.getyCoord() == yZiel){
			System.out.println("Sie haben ihr Ziel erreicht");
		}	
			
		if ((player.getxCoord() != xZiel) || (player.getyCoord() != yZiel) ){
	    	System.out.println("recursion");		    	
	    	
	    	movePlayer(view.getLabelArray()[yZiel][xZiel]);	    	
	     }
		
		
		
	     
	}
	
	/**
	 * Setzt die Werte wieder zurück.
	 * @param PlayerDarstellung
	 * @param ziel
	 */
	public void reset(Kachel ziel){
		System.out.println("reset whatever");
		this.yDistanz = view.getRowIndex(ziel) - player.getyCoord();
		this.xDistanz = view.getColumnIndex(ziel) - player.getxCoord();
		this.xRichtung = xDistanz;
		this.yRichtung = yDistanz;
		this.xZiel = view.getColumnIndex(ziel);
		this.yZiel = view.getRowIndex(ziel) + ausweichen;
		this.jetzigesFeld =  view.getLabelArray()[player.getyCoord()][player.getxCoord()];
		this.xErlaubt = 0;
		this.yErlaubt = 0;
		this.ausweichen = 0;
		
		
	}
	
	public void newPathfinder(int xDistanzeingegeben, int yDistanzeingegeben, Kachel jetzigesFeld){
		this.xDistanz = xDistanzeingegeben;
		this.yDistanz = yDistanzeingegeben;
		pathfinderX(jetzigesFeld);
		//animationX();
		pathfinderY(jetzigesFeld);
		//animationY();
	}
	
	public void pathfinderX(Kachel jetzigesFeld){
		
		if (moveErlaubtX()){
			if (xDistanz>0){
				animationX(1);
				xDistanz--;	
			}
			if (xDistanz<0){
				animationX(-1);
				xDistanz++;	
			}
		}
	}
	
	public void pathfinderY(Kachel jetzigesFeld){
		
		if (moveErlaubtY()){
			if (yDistanz>0){
				animationY(1);
				yDistanz--;	
			}
			if (yDistanz<0){
				animationY(-1);
				yDistanz++;	
			}
		}
	}
	
	public void animationX(int richtungX){
		
	}
	
	public void animationY(int richtungY){
		
	}
	
	
	/**
	 * Berechnet die Erlaubte x und y Distanz bis zum Ziel
	 * @param xDistanzeingegeben Die übermittelte Distanz in X Richtung
	 * @param yDistanzeingegeben Die übermittelte Distanz in Y Richtung
	 * @param jetzigesFeld
	 */
	public void pathfinder(int xDistanzeingegeben, int yDistanzeingegeben, Kachel jetzigesFeld){
		
		System.out.println("find whatever");
		System.out.println("x Player : " +player.getxCoord() +"| x Ziel : " +xZiel);
		System.out.println("y Player : " +player.getyCoord() +"| y Ziel : " +yZiel);
		
		this.xDistanz = xDistanzeingegeben;
		this.yDistanz = yDistanzeingegeben;
		this.jetzigesFeld = jetzigesFeld;
		if (yDistanz != 0 && xDistanz != 0){
			while (yDistanz>0 || (yDistanz == 0 && xDistanz != 0)){
					while (xDistanz>0){
						refresh();
						if (moveErlaubtX()){
							raiseErlaubtX();		
							updateCurrentField(0, 1);
							
						}
						xDistanz--;
					}
					while (xDistanz<0){
						refresh();
						if (moveErlaubtX()){
							raiseErlaubtX();		
							updateCurrentField(0, -1);
							
						}
						xDistanz++;
					}
					
				refresh();
				if (moveErlaubtY()){
					raiseErlaubtY();					
					updateCurrentField(1, 0);
					
				}
				yDistanz--;
			}
			while (yDistanz<0){
					while (xDistanz>0){
						refresh();
						if (moveErlaubtX()){
							raiseErlaubtX();		
							updateCurrentField(0, 1);
							
						}
						xDistanz--;
					}
					while (xDistanz<0){
						refresh();
						if (moveErlaubtX()){
							raiseErlaubtX();		
							updateCurrentField(0, -1);
							
						}
						xDistanz++;
					}
				refresh();
				if (moveErlaubtY()){
					raiseErlaubtY();					
					updateCurrentField(-1, 0);
					
				}
				yDistanz++;
			}
		//moveWithPath(playerDarstellung, this.xErlaubt, this.yErlaubt);
		}
		else{	
		
			while (yDistanz == 0 && xDistanz < 0){
				refresh();
				if (moveErlaubtX()){
					raiseErlaubtX();
					updateCurrentField(0, -1);
					
				}
				xDistanz++;
			}
			
			while (yDistanz == 0 && xDistanz > 0){
				refresh();
				if (moveErlaubtX()){
					raiseErlaubtX();
					updateCurrentField(0, 1);
					
				}
				else {
					dodge();
				}
				xDistanz--;
			}
			
			while (yDistanz > 0 && xDistanz == 0){
				refresh();
				if (moveErlaubtY()){
					raiseErlaubtY();					
					updateCurrentField(1, 0);
					
				}
				yDistanz--;
			}
			
			while (yDistanz < 0 && xDistanz == 0){
				refresh();
				if (moveErlaubtY()){
					raiseErlaubtY();					
					updateCurrentField(-1, 0);
					
				}
				yDistanz++;
			}
		//moveWithPath(playerDarstellung, this.xErlaubt, this.yErlaubt);
		}
		//moveWithPath(playerDarstellung, this.xErlaubt, this.yErlaubt);
	}
	
	/**
	 * setzt die jetzigeReihe / jetzigeSpalte auf den aktuellen Wert.
	 */
	public void refresh(){
		this.jetzigeReihe = view.getRowIndex(this.jetzigesFeld);
		this.jetzigeColumn = view.getColumnIndex(this.jetzigesFeld);			
		
	}
	
	/**
	 * setzt die Kacheln wieder in den Ausgangszustand zurück
	 * @param jetzigesFeld
	 */
	public void clearDice(Kachel jetzigesFeld){
		for (int iReihe = 0; iReihe< rowSize;iReihe++){
			for (int jSpalte = 0; jSpalte<columnSize;jSpalte++){
				for(int i=0;(augenzahl-2*i>=0);i++){
					if((jetzigesFeld.getxKoordinate()+jetzigesFeld.getyKoordinate()+(augenzahl-2*i))==(iReihe+jSpalte) || 
							(jetzigesFeld.getxKoordinate()+jetzigesFeld.getyKoordinate()-(augenzahl-2*i))==(iReihe+jSpalte)){
						
							Kachel erreichbareKachel = view.getLabelArray()[iReihe][jSpalte];
							if(erreichbareKachel.isIstRaum()==false){
							persil2(erreichbareKachel);
							}
					}
				}
			}
				
		}
	}
	/**
	 * Augenzahl -> Felder die man erreichen kann werden eingefärbt
	 */
	public void dice(Kachel jetzigesFeld){
		
		first = 1 + (int)(Math.random()*6);
		second = 1 + (int)(Math.random()*6);
		augenzahl = first + second;
		System.out.println("Augenzahl: " + augenzahl);
		
		for (int iReihe = 0; iReihe< rowSize;iReihe++){
			for (int jSpalte = 0; jSpalte<columnSize;jSpalte++){
				for(int i=0;(augenzahl-2*i>=0);i++){
					if((jetzigesFeld.getxKoordinate()+jetzigesFeld.getyKoordinate()+(augenzahl-2*i))==(iReihe+jSpalte) || 
							(jetzigesFeld.getxKoordinate()+jetzigesFeld.getyKoordinate()-(augenzahl-2*i))==(iReihe+jSpalte)){
							Kachel erreichbareKachel = view.getLabelArray()[iReihe][jSpalte];
							int abstandX = erreichbareKachel.getxKoordinate()-jetzigesFeld.getxKoordinate();
							int abstandY = erreichbareKachel.getyKoordinate()-jetzigesFeld.getyKoordinate();
							if(abstandX<0){abstandX *= -1;}
							if(abstandY<0){abstandY *= -1;}
								if(abstandX+abstandY<=augenzahl){
								if(erreichbareKachel.isIstRaum()==false){
								einfaerben2(erreichbareKachel);
								}
							}
					}
				}
			}
				
		}
	}
	
	/**
	 * Inkrementiert xErlaubt um 1
	 */
	public void raiseErlaubtX(){
		this.xErlaubt = xErlaubt+1;
	}
	
	/**
	 * Inkrementiert yErlaubt um 1
	 */
	public void raiseErlaubtY(){
		this.yErlaubt = yErlaubt+1;
	}
	
	/**
	 * Updatet das jetzigeFeld um den jeweiligen Wert
	 * @param y Der Wert um welchen die Reihe erhöht wird
	 * @param x Der Wert um welchen die Spalte erhöht wird
	 */
	public void updateCurrentField(int y, int x){
		this.jetzigesFeld = view.getLabelArray()[this.jetzigeReihe+y][this.jetzigeColumn+x];
	}
	
	
	/**
	 * Hier wird überprüft, ob die nächstgelegene Kachel in X Richtung ein Raum ist.
	 * @return true falls dort keine Raumkachel ist. false falls dort eine Raumkachel ist.
	 */
	public boolean moveErlaubtX (){
		refresh();
		if (xDistanz>0){
			if (view.getLabelArray()[jetzigeReihe][jetzigeColumn+1].isIstRaum()==false){
				System.out.println("x check true");
				return true;	
			}
			else {
				System.out.println("x check false");
				System.out.println("wurde blockiert");
				return false;
			}		
		}
		else {
			if (view.getLabelArray()[jetzigeReihe][jetzigeColumn-1].isIstRaum()==false){
				System.out.println("x check true");
				return true;
				
			}
			else {
				System.out.println("x check false");
				System.out.println("wurde blockiert");
				return false;
			}
		}
	}
	
	/**
	 * Hier wird überprüft, ob die nächstgelegene Kachel in Y Richtung ein Raum ist.
	 * @return true falls dort keine Raumkachel ist. false falls dort eine Raumkachel ist.
	 */
	public boolean moveErlaubtY (){
		refresh();
		if (yDistanz>0){
			if (view.getLabelArray()[jetzigeReihe+1][jetzigeColumn].isIstRaum() == false){
				
				System.out.println("y check true");
				
				return true;	
			}
			else {
				System.out.println("y check false");
				System.out.println("wurde blockiert");
				return false;
			}		
		}
		else {
			if (view.getLabelArray()[jetzigeReihe-1][jetzigeColumn].isIstRaum() == false){
				System.out.println("y check true");
				return true;	
			}
			else {
				System.out.println("y check false");
				System.out.println("wurde blockiert");
				return false;
			}
		}
	}
	
	public void dodge(){
		
	}
	
	
	
	/**
	 * Hier findet die Animation statt mithilfe von Paths.
	 * @param PlayerDarstellung Die zu bewegende Node
	 * @param xStrecke Die Strecke in X Richtung
	 * @param yStrecke Die Strecke in Y Richtung
	 */
	public void moveWithPath( int xStrecke, int yStrecke){
		
		System.out.println("path whatever");
		this.xStreckeFuerPath = xStrecke;
		this.yStreckeFuerPath = yStrecke;
		
		if (xRichtung<0){
			this.xStreckeFuerPath = - this.xStreckeFuerPath;
		}
		if (yRichtung<0){
			this.yStreckeFuerPath = - this.yStreckeFuerPath;
		}
		
		this.xPositionFuerPath = player.getxCoord();
		this.yPositionFuerPath = player.getyCoord();
		
		Kachel anfangsKachel = view.getLabelArray()[yPositionFuerPath][xPositionFuerPath];
		Kachel zielKachel = view.getLabelArray()[yPositionFuerPath + yStreckeFuerPath][xPositionFuerPath + xStreckeFuerPath];
		
		Path path = new Path();
	     path.getElements().add (new MoveTo (anfangsKachel.getLayoutX(), anfangsKachel.getLayoutY()));
	     path.getElements().add (new LineTo (zielKachel.getLayoutX(), anfangsKachel.getLayoutY()));
	     path.getElements().add (new LineTo (zielKachel.getLayoutX(), zielKachel.getLayoutY()));  
	   
	     PathTransition pathTransition = new PathTransition();	     
	     pathTransition.setDuration(Duration.millis(xStrecke * 200 + yStrecke * 200));
	     pathTransition.setNode(playerDarstellung);
	     pathTransition.setPath(path);
	     	//pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);	 
	     pathTransition.play();

	     player.setxCoord(player.getxCoord()+xStreckeFuerPath);
	     player.setyCoord(player.getyCoord()+yStreckeFuerPath);

	     
	      
	}
	
	/**
	 * Inaktive alte Bewegungsmethode, welche auch einen "Kreis" um Ziel gezeichnet hat 
	 */
	/*public void movePlayer(Circle Player, Kachel ziel){
			
		Kachel anfangsLabel = view.getKachelAnfang();				
		
		System.out.println("X Distanz:  " + xDistanz + "Y Distanz:  " + yDistanz);
		
		Path path = new Path();
	     path.getElements().add (new MoveTo (anfangsLabel.getLayoutX(), anfangsLabel.getLayoutY()));
	     path.getElements().add (new LineTo (ziel.getLayoutX(), anfangsLabel.getLayoutY()));
	     path.getElements().add (new LineTo (ziel.getLayoutX(), ziel.getLayoutY()));  
	    
	     
	     PathTransition pathTransition = new PathTransition();	     
	     pathTransition.setDuration(Duration.millis(3000));
	     pathTransition.setNode(Player);
	     pathTransition.setPath(path);
	     	//pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);	 
	     pathTransition.play();
	    view.setKachelAnfang(ziel);
	    
	    
	     int umfang = 5;
	     int rowIndex = view.getRowIndex(ziel);
	     int columnIndex = view.getColumnIndex(ziel);
	     int linksInt = rowIndex - umfang;
	     int rechtsInt = rowIndex + umfang;
	     int obenInt = columnIndex - umfang;
	     int untenInt = columnIndex + umfang;
	     if (linksInt<0){
	    	 linksInt=0;
	     }
	     if (rechtsInt>columnSize){
	    	 rechtsInt = 0;
	     }
	     if (obenInt<0){
	    	 obenInt = 0;
	     }
	     if (untenInt>rowSize){
	    	 untenInt = 0;
	     }
	     Kachel links = view.getLabelArray()[columnIndex][linksInt];
	     Kachel rechts = view.getLabelArray()[columnIndex][rechtsInt];
	     Kachel oben = view.getLabelArray()[obenInt][rowIndex];
	     Kachel unten = view.getLabelArray()[untenInt][rowIndex];
	     view.setBackground(links);
	     view.setBackground(rechts);
	     view.setBackground(unten);
	     view.setBackground(oben);
	     
	     int rowIndexAlt = view.getRowIndex(view.getLabelAnfang());
	     int columnIndexAlt = view.getColumnIndex(view.getLabelAnfang());
	     int linksIntAlt = rowIndexAlt - umfang;
	     int rechtsIntAlt = rowIndexAlt + umfang;
	     int obenIntAlt = columnIndexAlt - umfang;
	     int untenIntAlt = columnIndexAlt + umfang;
	     if (linksIntAlt<0){
	    	 linksIntAlt=0;
	     }
	     if (rechtsIntAlt>columnSize){
	    	 rechtsIntAlt = columnSize;
	     }
	     if (obenIntAlt<0){
	    	 obenIntAlt = 0;
	     }
	     if (untenIntAlt>rowSize){
	    	 untenIntAlt = rowSize;
	     }
	     
	     Kachel linksAlt = view.getLabelArray()[columnIndexAlt][linksIntAlt];
	     Kachel rechtsAlt = view.getLabelArray()[columnIndexAlt][rechtsIntAlt];
	     Kachel obenAlt = view.getLabelArray()[obenIntAlt][rowIndexAlt];
	     Kachel untenAlt = view.getLabelArray()[untenIntAlt][rowIndexAlt];
	     view.revertBackground(linksAlt);
	     view.revertBackground(rechtsAlt);
	     view.revertBackground(untenAlt);
	     view.revertBackground(obenAlt);
	    
	    
	    
	    
	   
	}*/
}