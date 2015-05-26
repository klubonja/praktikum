package cluedoNetworkGUI;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class LoginPrompt extends GridPane {
	
	
	String[] labelNames = {"Nickname","Groupname"};
	LoginTextField[] iFields = new LoginTextField[labelNames.length];
	int[] reqLength = {3,1};
	int nickMinLength = 3;
	
	TextArea errs = new TextArea();
	Text title = new Text("Login");
	final Button submit = new Button("login");
	String[] returnData = new String[labelNames.length];
	Stage stage;
	
	
	public LoginPrompt(Stage s) {
		super();
		stage = s;
		setFields();
		init();
		
		
	}
	
	private void setFields(){
		for (int i = 0;i < labelNames.length; i++){					
			iFields[i] = new LoginTextField(errs,labelNames[i],reqLength[i]);
		}		
	}
	
	private void init(){		
		
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		this.add(title, 0, 0);
		errs.setStyle("-fx-text-fill: red;");
		this.add(errs,1,0);		
		
		for (int i = 0;i < labelNames.length; i++){				
			this.add(iFields[i],0,i+2);
		}	
		
		
		this.add(submit, labelNames.length+2, 0);
		
		this.setPadding(new Insets(25, 25, 25, 25));
        this.setGridLinesVisible(true);	
        
        addHandlers();
	 }
	
	public String[] returnLoginData(){
		return returnData;
	}
	
	private final boolean isValidInput(){
		for (int i = 0;i < iFields.length;i++) 
			if (!iFields[i].validate()) return false;
		
		return true;
	}
	
	public final boolean putLoginData(){
		if (isValidInput()){
			for (int i = 0; i < labelNames.length; i++){
				returnData[i] = (iFields[i].getText());					
			}	
			return true;
		}		
		return false;
	}
	
	private void addHandlers(){
		submit.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				if (putLoginData()) stage.close();				
			}
		});
		
		this.setOnKeyPressed(new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent t) {
	            if (t.getCode() == KeyCode.ENTER){
	            	//returnLoginData();
	            };
	        }
	    });
	}
	
}
