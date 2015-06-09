package vielCoolererPathfinder;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class DerBeweger {

	private GUI gui;
	
	private char [] anweisungen;
	
	private Timeline timeline;
	private KeyFrame keyFrame;
	private KeyValue keyValue;
	private AnimationTimer timer;
	
	
    private KeyValue keyValueX;
    private KeyValue keyValueY;
	 
	    //variable for storing actual frame
	    private Integer frame=0;
	
	
	public DerBeweger(GUI gui){
		this.gui = gui;
	}
	
	public void bewegen(char [] anweisungen){
		
		
		
		
		
		
		this.anweisungen = anweisungen;
		
		
		
		//create a timeline for moving the circle
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
 
//You can add a specific action when each frame is started.
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                gui.doThatTitleThang(frame.toString());
                frame++;
            }
 
        };
 
        //create a keyValue with factory: scaling the circle 2times
        //KeyValue keyValueX = new KeyValue(gui.getKachelArray()[1][1].getLayoutX());
        //KeyValue keyValueY = new KeyValue(gui.getKachelArray()[1][1].getLayoutY());
        
        //create a keyFrame, the keyValue is reached at time 2s
        Duration duration = Duration.millis(2000);
        //one can add a specific action when the keyframe is reached
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //falls es noch mehr gibt: weitermachen! 
            	/*
                 if (anweisungen[i+1] != ' '){
                	 
                 }
                 */
            }
        };
 
  KeyFrame keyFrame = new KeyFrame(duration, onFinished , keyValueX, keyValueY);
 
        //add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame);
 
        timeline.play();
        timer.start();
		
		for (int i = 0; anweisungen[i] != ' ';i++){
			if (anweisungen[i] == 'S'){
				
			}
			
			if (anweisungen[i] == 'E'){
				
			}

			if (anweisungen[i] == 'N'){
				
			}

			if (anweisungen[i] == 'W'){
				
			}

			
		}
		
	}
	
	
}
