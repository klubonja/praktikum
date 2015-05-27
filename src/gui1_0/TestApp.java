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
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		testGUI = new TestGUI(columnSize, rowSize);
		testGUI.start();
		spieler = testGUI.getSpieler();
		for (int i = 0; i<testGUI.getLabelArray().length-1;i++){
			for (int j = 0; j<testGUI.getLabelArray()[j].length-1;j++){
				Kachel momentanesLabel = testGUI.getLabelArray()[i][j];
				momentanesLabel.setOnMouseClicked(e -> movePlayer(testGUI.getTestSpieler(), momentanesLabel));
				
			}
		}		
	}
	
	public void movePlayer(Circle spielerDarstellung, Kachel ziel){
		System.out.println("move whatever");
		reset(spielerDarstellung,ziel);
		
		/*while ((testGUI.getColumnIndex(ziel) - testGUI.getColumnIndex(spielerDarstellung) + testGUI.getRowIndex(ziel)-testGUI.getRowIndex(spielerDarstellung)) != 0){
				xErlaubt = 0;
				yErlaubt = 0;
				pathfinder(testGUI.getColumnIndex(ziel) - testGUI.getColumnIndex(spielerDarstellung), testGUI.getRowIndex(ziel)-testGUI.getRowIndex(spielerDarstellung), testGUI.getLabelArray()[spieler.getyPosition()][spieler.getxPosition()]);
		}*/
		while(xDistanz + yDistanz != 0){
			pathfinder(xDistanz, yDistanz, jetzigesFeld);
		}
		System.out.println("Reihe : " +testGUI.getRowIndex(this.jetzigesFeld));
		
		moveWithPath(playerDarstellung, this.xErlaubt, this.yErlaubt);
	}
	
	public void reset(Circle spielerDarstellung, Kachel ziel){
		System.out.println("reset whatever");
		this.playerDarstellung = spielerDarstellung;
		this.yDistanz = testGUI.getRowIndex(ziel) - testGUI.getSpieler().getyPosition();
		this.xDistanz = testGUI.getColumnIndex(ziel) - testGUI.getSpieler().getxPosition();
		this.xRichtung = xDistanz;
		this.yRichtung = yDistanz;
		this.jetzigesFeld =  testGUI.getLabelArray()[spieler.getyPosition()][spieler.getxPosition()];
		this.xErlaubt = 0;
		this.yErlaubt = 0;
		
		
	}
	
	public void pathfinder(int xDistanzeingegeben, int yDistanzeingegeben, Kachel jetzigesFeld){
		
		System.out.println("find whatever");
		this.xDistanz = xDistanzeingegeben;
		this.yDistanz = yDistanzeingegeben;
		this.jetzigesFeld = jetzigesFeld;
		while (yDistanz>0){
			while (xDistanz>0){
				this.jetzigeReihe = testGUI.getRowIndex(this.jetzigesFeld);
				this.jetzigeColumn = testGUI.getColumnIndex(this.jetzigesFeld);			
				if (moveErlaubtX()){
					this.xErlaubt = this.xErlaubt+1;
					this.jetzigesFeld = testGUI.getLabelArray()[this.jetzigeReihe][this.jetzigeColumn+1];
					
				}
				xDistanz--;
			}
			while (xDistanz<0){
				this.jetzigeReihe = testGUI.getRowIndex(this.jetzigesFeld);
				this.jetzigeColumn = testGUI.getColumnIndex(this.jetzigesFeld);			
				if (moveErlaubtX()){
					this.xErlaubt = this.xErlaubt+1;
					this.jetzigesFeld = testGUI.getLabelArray()[this.jetzigeReihe][this.jetzigeColumn-1];
					
				}
				xDistanz++;
			}
			
			this.jetzigeReihe = testGUI.getRowIndex(this.jetzigesFeld);
			this.jetzigeColumn = testGUI.getColumnIndex(this.jetzigesFeld);			
			if (moveErlaubtY()){
				this.yErlaubt = this.yErlaubt+1;
				this.jetzigesFeld = testGUI.getLabelArray()[this.jetzigeReihe+1][this.jetzigeColumn];
				
			}
			yDistanz--;
		}
		while (yDistanz<0){
			while (xDistanz>0){
				this.jetzigeReihe = testGUI.getRowIndex(this.jetzigesFeld);
				this.jetzigeColumn = testGUI.getColumnIndex(this.jetzigesFeld);			
				if (moveErlaubtX()){
					this.xErlaubt = this.xErlaubt+1;
					this.jetzigesFeld = testGUI.getLabelArray()[this.jetzigeReihe][this.jetzigeColumn+1];
					
				}
				xDistanz--;
			}
			while (xDistanz<0){
				this.jetzigeReihe = testGUI.getRowIndex(this.jetzigesFeld);
				this.jetzigeColumn = testGUI.getColumnIndex(this.jetzigesFeld);			
				if (moveErlaubtX()){
					this.xErlaubt = this.xErlaubt+1;
					this.jetzigesFeld = testGUI.getLabelArray()[this.jetzigeReihe][this.jetzigeColumn-1];
					
				}
				xDistanz++;
			}
			
			this.jetzigeReihe = testGUI.getRowIndex(this.jetzigesFeld);
			this.jetzigeColumn = testGUI.getColumnIndex(this.jetzigesFeld);			
			if (moveErlaubtY()){
				this.yErlaubt = this.yErlaubt+1;
				this.jetzigesFeld = testGUI.getLabelArray()[this.jetzigeReihe-1][this.jetzigeColumn];
				
			}
			yDistanz++;
		}
		
		
	}
		
			
	public boolean moveErlaubtX (){
		System.out.println("x check");
		this.jetzigeReihe = testGUI.getRowIndex(jetzigesFeld);
		this.jetzigeColumn = testGUI.getColumnIndex(jetzigesFeld);
		if (xDistanz>0){
			if (testGUI.getLabelArray()[jetzigeReihe][jetzigeColumn+1].isIstRaum()==false){
				System.out.println("x check true");
				return true;	
			}
			else {
				System.out.println("jetzige Kachel " + testGUI.getColumnIndex(jetzigesFeld));
				System.out.println("xErlaubt " + xErlaubt);
				System.out.println("xDistanz " + xDistanz);
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
				System.out.println("jetzige Kachel " + testGUI.getColumnIndex(jetzigesFeld));
				System.out.println("xErlaubt " + xErlaubt);
				System.out.println("xDistanz " + xDistanz);
				System.out.println("x check false");
				return false;
			}
		}
	}
	public boolean moveErlaubtY (){
		System.out.println("y check");
		this.jetzigeReihe = testGUI.getRowIndex(jetzigesFeld);
		this.jetzigeColumn = testGUI.getColumnIndex(jetzigesFeld);
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
		
		System.out.println("x Strecke " + xStrecke);
		
		System.out.println("x Für Path" + xStreckeFuerPath);
		
		this.xPosition = testGUI.getSpieler().getxPosition();
		this.yPosition = testGUI.getSpieler().getyPosition();
		
		Kachel anfangsKachel = testGUI.getLabelArray()[yPosition][xPosition];
		Kachel zielKachel = testGUI.getLabelArray()[yPosition + yStreckeFuerPath][xPosition + xStreckeFuerPath];
		System.out.println("path: y insgesamt" +(yStreckeFuerPath+yPosition));
		
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
	     
	    this.xErlaubt = 0;
		this.yErlaubt = 0;
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
