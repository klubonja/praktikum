package cluedoNetworkGUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


public class LoginTextField extends TextField{
	int minLength;
	private StringBuffer err;
	public Text errsArea;
	private String labelName;
	
	public LoginTextField(Text e,String s,int i) {
		super(s);
		errsArea = e;
		setFocusTraversable(true);
		err = new StringBuffer();
		labelName = s;
		minLength = i;
		setStyle("-fx-font-style: italic;"
				+ "fx-text-inner-color: lightgrey;");
		setListener();
		
	}
	
	public final boolean validate(){		
		if (getText().length() < minLength){
			raiseErr(labelName+" muss mindestens "+minLength+" Zeichen haben");
			setStyle("-fx-text-inner-color: red;"
				+    "-fx-font-style: italic;"
				+ "");

			return false;
		}
//		else if (getText().equals(labelName)){
//			raiseErr(labelName+" muss mindestens "+minLength+" Zeichen haben");
//			setStyle("-fx-text-inner-color: red;"
//				+    "-fx-font-style: italic;"
//				+ "");
//
//			return false;
//			
//		}
		return true;	
		
	}
	
	
	private void raiseErr(String e){
		String s = errsArea.getText();
		errsArea.setText(s+"\n"+e);
	}
	
	public String getErr(){
		return err.toString();
	}
	
	private void setListener(){		
		
		this.textProperty().addListener(new ChangeListener <String>(){
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
					if(newValue.length() < minLength) setStyle("-fx-text-fill: red;");
					else setStyle("-fx-text-fill: black;");
			}			
		});
		
		this.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		        /*
		    	if (newPropertyValue && getText().equals(labelName)) 
		        	setText("");
		        */		        
		        if (!newPropertyValue && getText().equals("")){
		        	setText(labelName);
		        	setStyle("-fx-font-style: italic;");
		        }		        	
		    }
		});
		
		this.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				 if (getText().equals(labelName)) {
			        	setText("");
			        	//setStyle("-fx-font-style: normal;");
			     }
				 //else if (getText().length() == 0) setText(labelName);
			}
			
		});
	}
	
	
}

