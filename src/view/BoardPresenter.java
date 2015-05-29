package view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import view.PlayerView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @since 24.05.2015
 * @version 28.05.2015
 * @author Benedikt Mayer
 *
 * 
 */	
public class BoardPresenter extends Application {

	
	private PlayerView spieler;
	private BoardView BoardView;
	private int rowSize = 25;
	private int columnSize = 24;
	
	private int xDistanz;
	private int yDistanz;	
	private int xErlaubt;
	private int yErlaubt;
	
	private int jetzigeReihe;
	private int jetzigeColumn;
	private Kachel jetzigesFeld;
	
	private PlayerView playerDarstellung;
	
	private int CenterXFuerPath;
	private int CenterYFuerPath;	
	private int xStreckeFuerPath;
	private int yStreckeFuerPath;
	
	private int xRichtung;
	private int yRichtung;
	private int xZiel;
	private int yZiel;
	
	private String textbuffer;
	private Background backgroundbuffer;
	private Font fontbuffer;

	private int ausweichen;
	private boolean aussenrum;
	
	/**
	 * Hier wird die Stage gestartet und den Kacheln eine Methode für 
	 * setOnMouseClicked zugewiesen.
	 * @param primaryStage 
	 * @throws Exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BoardView = new BoardView(columnSize, rowSize);
		spieler = BoardView.getSpieler();
		for (int i = 0; i<BoardView.getLabelArray().length-1;i++){
			for (int j = 0; j<BoardView.getLabelArray()[j].length-1;j++){
				Kachel momentaneKachel = BoardView.getLabelArray()[i][j];
					if (momentaneKachel.isIstRaum()==false){
						momentaneKachel.setOnMouseClicked(e -> dasIstEinFeld(BoardView.getTestSpieler(), momentaneKachel));
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
	 * Wird von der Kachel.OnClick aufgerufen und löst die movePlayer Methode aus.
	 */
	public void dasIstEinFeld(PlayerView spielerDarstellung, Kachel ziel){
		movePlayer(spielerDarstellung, ziel);
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
	 * @param spielerDarstellung Der zu bewegende Spieler
	 * @param ziel Die Zielkachel
	 */	
	public void movePlayer(PlayerView spielerDarstellung, Kachel ziel){
		System.out.println("----------------------");
		System.out.println("move whatever");
		
		reset(spielerDarstellung,ziel);
			
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
	    moveWithPath(playerDarstellung, this.xErlaubt, this.yErlaubt);				
		
		System.out.println("Reihe : " +(BoardView.getRowIndex(this.jetzigesFeld)+1));
		System.out.println("Spalte : " +(BoardView.getColumnIndex(this.jetzigesFeld)+1));
		
		System.out.println("x Spieler : " +BoardView.getSpieler().getCenterX() +"| x Ziel : " +xZiel);
		System.out.println("y Spieler : " +BoardView.getSpieler().getCenterY() +"| y Ziel : " +yZiel);
			
			
		if (BoardView.getSpieler().getCenterX() == xZiel && BoardView.getSpieler().getCenterY() == yZiel){
			System.out.println("Sie haben ihr Ziel erreicht");
		}	
			
		if ((BoardView.getSpieler().getCenterX() != xZiel) || (BoardView.getSpieler().getCenterY() != yZiel) ){
	    	System.out.println("recursion");		    	
	    	
	    	movePlayer(BoardView.getTestSpieler(), BoardView.getLabelArray()[yZiel][xZiel]);	    	
	     }
		
		
		
	     
	}
	
	/**
	 * Setzt die Werte wieder zurück.
	 * @param spielerDarstellung
	 * @param ziel
	 */
	public void reset(PlayerView spielerDarstellung, Kachel ziel){
		System.out.println("reset whatever");
		this.playerDarstellung = spielerDarstellung;
		this.yDistanz = BoardView.getRowIndex(ziel) - (int)BoardView.getSpieler().getCenterY();
		this.xDistanz = BoardView.getColumnIndex(ziel) - (int)BoardView.getSpieler().getCenterX();
		this.xRichtung = xDistanz;
		this.yRichtung = yDistanz;
		this.xZiel = BoardView.getColumnIndex(ziel);
		this.yZiel = BoardView.getRowIndex(ziel) + ausweichen;
		this.jetzigesFeld =  BoardView.getLabelArray()[(int) spieler.getCenterY()][(int) spieler.getCenterX()];
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
		System.out.println("x Spieler : " +BoardView.getSpieler().getCenterX() +"| x Ziel : " +xZiel);
		System.out.println("y Spieler : " +BoardView.getSpieler().getCenterY() +"| y Ziel : " +yZiel);
		
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
		this.jetzigeReihe = BoardView.getRowIndex(this.jetzigesFeld);
		this.jetzigeColumn = BoardView.getColumnIndex(this.jetzigesFeld);			
		
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
		this.jetzigesFeld = BoardView.getLabelArray()[this.jetzigeReihe+y][this.jetzigeColumn+x];
	}
	
	
	/**
	 * Hier wird überprüft, ob die nächstgelegene Kachel in X Richtung ein Raum ist.
	 * @return true falls dort keine Raumkachel ist. false falls dort eine Raumkachel ist.
	 */
	public boolean moveErlaubtX (){
		refresh();
		if (xDistanz>0){
			if (BoardView.getLabelArray()[jetzigeReihe][jetzigeColumn+1].isIstRaum()==false){
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
			if (BoardView.getLabelArray()[jetzigeReihe][jetzigeColumn-1].isIstRaum()==false){
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
			if (BoardView.getLabelArray()[jetzigeReihe+1][jetzigeColumn].isIstRaum() == false){
				
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
			if (BoardView.getLabelArray()[jetzigeReihe-1][jetzigeColumn].isIstRaum() == false){
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
	 * @param spielerDarstellung Die zu bewegende Node
	 * @param xStrecke Die Strecke in X Richtung
	 * @param yStrecke Die Strecke in Y Richtung
	 */
	public void moveWithPath(PlayerView spielerDarstellung, int xStrecke, int yStrecke){
		
		System.out.println("path whatever");
		this.xStreckeFuerPath = xStrecke;
		this.yStreckeFuerPath = yStrecke;
		
		if (xRichtung<0){
			this.xStreckeFuerPath = - this.xStreckeFuerPath;
		}
		if (yRichtung<0){
			this.yStreckeFuerPath = - this.yStreckeFuerPath;
		}
		
		this.CenterXFuerPath = (int) BoardView.getSpieler().getCenterX();
		this.CenterYFuerPath = (int) BoardView.getSpieler().getCenterY();
		
		Kachel anfangsKachel = BoardView.getLabelArray()[CenterYFuerPath][CenterXFuerPath];
		Kachel zielKachel = BoardView.getLabelArray()[CenterYFuerPath + yStreckeFuerPath][CenterXFuerPath + xStreckeFuerPath];
		
		Path path = new Path();
	     path.getElements().add (new MoveTo (anfangsKachel.getLayoutX(), anfangsKachel.getLayoutY()));
	     path.getElements().add (new LineTo (zielKachel.getLayoutX(), anfangsKachel.getLayoutY()));
	     path.getElements().add (new LineTo (zielKachel.getLayoutX(), zielKachel.getLayoutY()));  
	   
	     PathTransition pathTransition = new PathTransition();	     
	     pathTransition.setDuration(Duration.millis(xStrecke * 200 + yStrecke * 200));
	     pathTransition.setNode(spielerDarstellung);
	     pathTransition.setPath(path);
	     	//pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);	 
	     pathTransition.play();

	     BoardView.getSpieler().setCenterX(BoardView.getSpieler().getCenterX()+xStreckeFuerPath);
	     BoardView.getSpieler().setCenterY(BoardView.getSpieler().getCenterY()+yStreckeFuerPath);

	     
	      
	}
	
	/**
	 * Inaktive alte Bewegungsmethode, welche auch einen "Kreis" um Ziel gezeichnet hat 
	 */
	/*public void movePlayer(PlayerView spieler, Kachel ziel){
			
		Kachel anfangsLabel = BoardView.getKachelAnfang();				
		
		System.out.println("X Distanz:  " + xDistanz + "Y Distanz:  " + yDistanz);
		
		Path path = new Path();
	     path.getElements().add (new MoveTo (anfangsLabel.getLayoutX(), anfangsLabel.getLayoutY()));
	     path.getElements().add (new LineTo (ziel.getLayoutX(), anfangsLabel.getLayoutY()));
	     path.getElements().add (new LineTo (ziel.getLayoutX(), ziel.getLayoutY()));  
	    
	     
	     PathTransition pathTransition = new PathTransition();	     
	     pathTransition.setDuration(Duration.millis(3000));
	     pathTransition.setNode(spieler);
	     pathTransition.setPath(path);
	     	//pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);	 
	     pathTransition.play();
	    BoardView.setKachelAnfang(ziel);
	    
	    
	     int umfang = 5;
	     int rowIndex = BoardView.getRowIndex(ziel);
	     int columnIndex = BoardView.getColumnIndex(ziel);
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
	     Kachel links = BoardView.getLabelArray()[columnIndex][linksInt];
	     Kachel rechts = BoardView.getLabelArray()[columnIndex][rechtsInt];
	     Kachel oben = BoardView.getLabelArray()[obenInt][rowIndex];
	     Kachel unten = BoardView.getLabelArray()[untenInt][rowIndex];
	     BoardView.setBackground(links);
	     BoardView.setBackground(rechts);
	     BoardView.setBackground(unten);
	     BoardView.setBackground(oben);
	     
	     int rowIndexAlt = BoardView.getRowIndex(BoardView.getLabelAnfang());
	     int columnIndexAlt = BoardView.getColumnIndex(BoardView.getLabelAnfang());
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
	     
	     Kachel linksAlt = BoardView.getLabelArray()[columnIndexAlt][linksIntAlt];
	     Kachel rechtsAlt = BoardView.getLabelArray()[columnIndexAlt][rechtsIntAlt];
	     Kachel obenAlt = BoardView.getLabelArray()[obenIntAlt][rowIndexAlt];
	     Kachel untenAlt = BoardView.getLabelArray()[untenIntAlt][rowIndexAlt];
	     BoardView.revertBackground(linksAlt);
	     BoardView.revertBackground(rechtsAlt);
	     BoardView.revertBackground(untenAlt);
	     BoardView.revertBackground(obenAlt);
	    
	    
	    
	    
	   
	}
*/
	
	/**
	 * Hier wird gelauncht.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}