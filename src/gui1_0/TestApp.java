package gui1_0;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @since 24.05.2015
 * @version 27.05.2015
 * @author Benedikt Mayer
 *
 * 
 */
public class TestApp extends Application {

	private TestGUI testGUI;
	private Timeline timeline;
	private KeyFrame keyFrame;
	private KeyValue keyValue;
	private Background hintergrund;
	private BackgroundFill fill;
	private int rowSize = 25;
	private int columnSize = 24;
	private int xDistanz;
	private int yDistanz;
	private Kachel altePosition;
	private Spieler spieler;
	private int xErlaubt;
	private int yErlaubt;
	private int jetzigeReihe;
	private int jetzigeColumn;
	private Kachel jetzigesFeld;
	private Circle playerDarstellung;
	private int xPosition;
	private int yPosition;
	
	private int xStreckeFuerPath;
	private int yStreckeFuerPath;
	
	private int xRichtung;
	private int yRichtung;
	
	private int xZiel;
	private int yZiel;
	
	/**
	 * Hier wird die Stage gestartet und den Kacheln eine Methode für 
	 * setOnMouseClicked zugewiesen.
	 * @param primaryStage 
	 * @throws Exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		testGUI = new TestGUI(columnSize, rowSize);
		testGUI.start();
		spieler = testGUI.getSpieler();
		for (int i = 0; i<testGUI.getLabelArray().length-1;i++){
			for (int j = 0; j<testGUI.getLabelArray()[j].length-1;j++){
				Kachel momentaneKachel = testGUI.getLabelArray()[i][j];
				if (momentaneKachel.isIstRaum()==false){
				momentaneKachel.setOnMouseClicked(e -> dasIstEinFeld(testGUI.getTestSpieler(), momentaneKachel));
				}
				else{
					momentaneKachel.setOnMouseClicked(e -> dasIsEinRaum());
				}
			}
		}		
	}
	
	public void dasIsEinRaum(){
		System.out.println("Das ist ein Raum, alter");
	}
	
	public void dasIstEinFeld(Circle spielerDarstellung, Kachel ziel){
		movePlayer(spielerDarstellung, ziel);
	}
	
	/**
	 * Die Methode, welche durch das ClickEvent ausgelöst wird.
	 * hier wird pathfinder und moveWithPath ausgelöst.
	 * @param spielerDarstellung Der zu bewegende Spieler
	 * @param ziel Die Zielkachel
	 */	
	public void movePlayer(Circle spielerDarstellung, Kachel ziel){
		System.out.println("----------------------");
		System.out.println("move whatever");
		
		reset(spielerDarstellung,ziel);
			
			System.out.println("vorher : x Distanz   " +xDistanz);
			System.out.println("vorher : y Distanz   " +yDistanz);
			
			
			while( (xDistanz != 0) && (yDistanz != 0) ){
				pathfinder(xDistanz, yDistanz, jetzigesFeld);
			}
			
			while( (xDistanz != 0) ^ (yDistanz != 0) ){
				pathfinder(xDistanz, yDistanz, jetzigesFeld);
			}

			System.out.println("nachher : x Distanz   " +xDistanz);
			System.out.println("nachher : y Distanz   " +yDistanz);
			
		// moveWithPath(playerDarstellung, this.xErlaubt, this.yErlaubt);				
		
			System.out.println("Reihe : " +(testGUI.getRowIndex(this.jetzigesFeld)+1));
			System.out.println("Spalte : " +(testGUI.getColumnIndex(this.jetzigesFeld)+1));
			
			System.out.println("x Spieler : " +testGUI.getSpieler().getxPosition() +"| x Ziel : " +xZiel);
			System.out.println("y Spieler : " +testGUI.getSpieler().getyPosition() +"| y Ziel : " +yZiel);
			
		
		if ((testGUI.getSpieler().getxPosition() != xZiel) || (testGUI.getSpieler().getyPosition() != yZiel) ){
	    	System.out.println("recursion");		    	
	    	
	    	movePlayer(testGUI.getTestSpieler(), testGUI.getLabelArray()[yZiel][xZiel]);
	    	
	     }
		
	     
	}
	
	/**
	 * Setzt die Werte wieder zurück.
	 * @param spielerDarstellung
	 * @param ziel
	 */
	public void reset(Circle spielerDarstellung, Kachel ziel){
		System.out.println("reset whatever");
		this.playerDarstellung = spielerDarstellung;
		this.yDistanz = testGUI.getRowIndex(ziel) - testGUI.getSpieler().getyPosition();
		this.xDistanz = testGUI.getColumnIndex(ziel) - testGUI.getSpieler().getxPosition();
		this.xRichtung = xDistanz;
		this.yRichtung = yDistanz;
		this.xZiel = testGUI.getColumnIndex(ziel);
		this.yZiel = testGUI.getRowIndex(ziel);
		this.jetzigesFeld =  testGUI.getLabelArray()[spieler.getyPosition()][spieler.getxPosition()];
		this.xErlaubt = 0;
		this.yErlaubt = 0;
		
		
	}
	/**
	 * Sucht den Weg für die Spielfigur unter Berücksichtigung der Raumkacheln.
	 * @param xDistanzeingegeben Die übermittelte Distanz in X Richtung
	 * @param yDistanzeingegeben Die übermittelte Distanz in Y Richtung
	 * @param jetzigesFeld
	 */
	public void pathfinder(int xDistanzeingegeben, int yDistanzeingegeben, Kachel jetzigesFeld){
		
		System.out.println("find whatever");
		this.xDistanz = xDistanzeingegeben;
		this.yDistanz = yDistanzeingegeben;
		this.jetzigesFeld = jetzigesFeld;
		if (yDistanz != 0 && xDistanz != 0){
			while (yDistanz>0){
					while (xDistanz>0){
						refresh();
						if (moveErlaubtX()){
							this.xErlaubt = this.xErlaubt+1;
							this.jetzigesFeld = testGUI.getLabelArray()[this.jetzigeReihe][this.jetzigeColumn+1];
							
						}
						xDistanz--;
					}
					while (xDistanz<0){
						refresh();
						if (moveErlaubtX()){
							this.xErlaubt = this.xErlaubt+1;
							this.jetzigesFeld = testGUI.getLabelArray()[this.jetzigeReihe][this.jetzigeColumn-1];
							
						}
						xDistanz++;
					}
					
				refresh();
				if (moveErlaubtY()){
					this.yErlaubt = this.yErlaubt+1;
					this.jetzigesFeld = testGUI.getLabelArray()[this.jetzigeReihe+1][this.jetzigeColumn];
					
				}
				yDistanz--;
			}
			while (yDistanz<0){
					while (xDistanz>0){
						refresh();
						if (moveErlaubtX()){
							this.xErlaubt = this.xErlaubt+1;
							this.jetzigesFeld = testGUI.getLabelArray()[this.jetzigeReihe][this.jetzigeColumn+1];
							
						}
						xDistanz--;
					}
					while (xDistanz<0){
						refresh();
						if (moveErlaubtX()){
							this.xErlaubt = this.xErlaubt+1;
							this.jetzigesFeld = testGUI.getLabelArray()[this.jetzigeReihe][this.jetzigeColumn-1];
							
						}
						xDistanz++;
					}
				refresh();
				if (moveErlaubtY()){
					this.yErlaubt = this.yErlaubt+1;
					this.jetzigesFeld = testGUI.getLabelArray()[this.jetzigeReihe-1][this.jetzigeColumn];
					
				}
				yDistanz++;
			}
		moveWithPath(playerDarstellung, this.xErlaubt, this.yErlaubt);
		}
		else{	
		
			while (yDistanz == 0 && xDistanz < 0){
				refresh();
				if (moveErlaubtX()){
					this.xErlaubt = this.xErlaubt+1;
					this.jetzigesFeld = testGUI.getLabelArray()[this.jetzigeReihe][this.jetzigeColumn-1];
					
				}
				xDistanz++;
			}
			while (yDistanz == 0 && xDistanz > 0){
				refresh();
				if (moveErlaubtX()){
					this.xErlaubt = this.xErlaubt+1;
					this.jetzigesFeld = testGUI.getLabelArray()[this.jetzigeReihe][this.jetzigeColumn+1];
					
				}
				xDistanz--;
			}
		moveWithPath(playerDarstellung, this.xErlaubt, this.yErlaubt);
		}
		
	}
	
	public void refresh(){
		this.jetzigeReihe = testGUI.getRowIndex(this.jetzigesFeld);
		this.jetzigeColumn = testGUI.getColumnIndex(this.jetzigesFeld);			
		
	}
		
	/**
	 * Hier wird überprüft, ob die nächstgelegene Kachel in X Richtung ein Raum ist.
	 * @return true falls dort keine Raumkachel ist. false falls dort eine Raumkachel ist.
	 */
	public boolean moveErlaubtX (){
		refresh();
		if (xDistanz>0){
			if (testGUI.getLabelArray()[jetzigeReihe][jetzigeColumn+1].isIstRaum()==false){
				System.out.println("x check true");
				return true;	
			}
			else {
				System.out.println("x check false");
				return false;
			}		
		}
		else {
			if (testGUI.getLabelArray()[jetzigeReihe][jetzigeColumn-1].isIstRaum()==false){
				System.out.println("x check true");
				return true;	
			}
			else {
				System.out.println("x check false");
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
			if (testGUI.getLabelArray()[jetzigeReihe+1][jetzigeColumn].isIstRaum() == false){
				System.out.println("y check true");
				return true;	
			}
			else {
				System.out.println("y check false");
				return false;
			}		
		}
		else {
			if (testGUI.getLabelArray()[jetzigeReihe-1][jetzigeColumn].isIstRaum() == false){
				System.out.println("y check true");
				return true;	
			}
			else {
				System.out.println("y check false");
				return false;
			}
		}
	}
	
	
	/**
	 * Hier findet die Animation statt mithilfe von Paths.
	 * @param spielerDarstellung Die zu bewegende Node
	 * @param xStrecke Die Strecke in X Richtung
	 * @param yStrecke Die Strecke in Y Richtung
	 */
	public void moveWithPath(Circle spielerDarstellung, int xStrecke, int yStrecke){
		System.out.println("path whatever");
		this.xStreckeFuerPath = xStrecke;
		this.yStreckeFuerPath = yStrecke;
		
		if (xRichtung<0){
			this.xStreckeFuerPath = - this.xStreckeFuerPath;
		}
		if (yRichtung<0){
			this.yStreckeFuerPath = - this.yStreckeFuerPath;
		}
		
		this.xPosition = testGUI.getSpieler().getxPosition();
		this.yPosition = testGUI.getSpieler().getyPosition();
		
		Kachel anfangsKachel = testGUI.getLabelArray()[yPosition][xPosition];
		Kachel zielKachel = testGUI.getLabelArray()[yPosition + yStreckeFuerPath][xPosition + xStreckeFuerPath];
		
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

	     testGUI.getSpieler().setxPosition(testGUI.getSpieler().getxPosition()+xStreckeFuerPath);
	     testGUI.getSpieler().setyPosition(testGUI.getSpieler().getyPosition()+yStreckeFuerPath);

	      
	}
	
	/*public void movePlayer(Circle spieler, Kachel ziel){
			
		Kachel anfangsLabel = testGUI.getKachelAnfang();				
		
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
	    testGUI.setKachelAnfang(ziel);
	    
	    
	     int umfang = 5;
	     int rowIndex = testGUI.getRowIndex(ziel);
	     int columnIndex = testGUI.getColumnIndex(ziel);
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
	     Kachel links = testGUI.getLabelArray()[columnIndex][linksInt];
	     Kachel rechts = testGUI.getLabelArray()[columnIndex][rechtsInt];
	     Kachel oben = testGUI.getLabelArray()[obenInt][rowIndex];
	     Kachel unten = testGUI.getLabelArray()[untenInt][rowIndex];
	     testGUI.setBackground(links);
	     testGUI.setBackground(rechts);
	     testGUI.setBackground(unten);
	     testGUI.setBackground(oben);
	     
	     int rowIndexAlt = testGUI.getRowIndex(testGUI.getLabelAnfang());
	     int columnIndexAlt = testGUI.getColumnIndex(testGUI.getLabelAnfang());
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
	     
	     Kachel linksAlt = testGUI.getLabelArray()[columnIndexAlt][linksIntAlt];
	     Kachel rechtsAlt = testGUI.getLabelArray()[columnIndexAlt][rechtsIntAlt];
	     Kachel obenAlt = testGUI.getLabelArray()[obenIntAlt][rowIndexAlt];
	     Kachel untenAlt = testGUI.getLabelArray()[untenIntAlt][rowIndexAlt];
	     testGUI.revertBackground(linksAlt);
	     testGUI.revertBackground(rechtsAlt);
	     testGUI.revertBackground(untenAlt);
	     testGUI.revertBackground(obenAlt);
	    
	    
	    
	    
	   
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
