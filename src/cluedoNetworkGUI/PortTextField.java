package cluedoNetworkGUI;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class PortTextField extends TextField {

	private final int maxLength;
	private StringBuffer err;
	public PortTextField() {
		super();
		setFocusTraversable(true);
		maxLength = 6;
		err = new StringBuffer();
		this.textProperty().addListener(new ChangeListener <String>(){
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
					validateInput(newValue);				
			}			
		});
	}
	
	private void  validateInput(String s){
		if (!s.matches("[0-9]")) {
			raiseErr("Nur Zahlen sind erlaubt");
		}
		if (s.length() > maxLength-1){
			this.setText(s.substring(0, maxLength));
			raiseErr("Nur "+maxLength+" Ziffern");
			System.out.println(s.substring(0,maxLength));
		}
	}
	
	private void raiseErr(String e){
		err.append(e);
	}
	
	public String getErr(){
		return err.toString();
	}
}
