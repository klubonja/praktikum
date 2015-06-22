package cluedoNetworkGUI;

import java.util.ArrayList;

import staticClasses.Config;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class LoginPrompt extends GridPane {
	
	
	String[] labelNames = {"Nickname",Config.GROUP_NAME};
	int[] reqLength = {3,1};
	LoginTextField[] iFields = new LoginTextField[labelNames.length];
	
	
	Text errs = new Text();
	Text title = new Text("Login");
	final Button submit = new Button("login");
	String[] returnData = new String[labelNames.length];
	Stage stage;
	
	
	public LoginPrompt(Stage s) {
		super();
		stage = s;
		init();		
	}
	
	private void setFields(){
		for (int i = 0;i < labelNames.length; i++){					
			iFields[i] = new LoginTextField(errs,labelNames[i],reqLength[i]);
		}		
	}
	
	private void init(){
		setFields();
		requestFocus();
		
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		this.add(title, 0, 0);
		errs.setStyle("-fx-text-fill: red;");		
		this.add(errs,0,1);		
		
		for (int i = 0;i < labelNames.length; i++){				
			this.add(iFields[i],0,i+2);
		}			
		
		this.add(submit, 0,labelNames.length+2);
		
		this.setPadding(new Insets(25, 25, 25, 25));
        ColumnConstraints col0 = new ColumnConstraints();
		col0.setPercentWidth(100);
		this.getColumnConstraints().add(col0);
        this.setGridLinesVisible(false);	
        
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
