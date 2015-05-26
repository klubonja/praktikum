package cluedoNetworkGUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class LoginTextField extends TextField{
	int minLength;
	private StringBuffer err;
	public TextArea errsArea;
	private String labelName;
	
	public LoginTextField(TextArea e,String s,int i) {
		super(s);
		errsArea = e;
		setFocusTraversable(true);
		err = new StringBuffer();
		labelName = s;
		minLength = i;
		setListener();
	}
	
	public final boolean validate(){		
		if (getText().length() < minLength){
			//this.setText(s.substring(0, minLength));
			raiseErr(labelName+" muss mindestens "+minLength+" Zeichen haben");
			return false;
		}
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
					//validateInput(newValue);				
			}			
		});
		
		this.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		        if (newPropertyValue && getText().equals(labelName)) 
		        	setText("");		        
		        else if (!newPropertyValue && getText().equals(""))
		        	setText(labelName);
		    }
		});
	}
	
	
}

