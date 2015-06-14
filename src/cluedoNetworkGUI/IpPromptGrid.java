package cluedoNetworkGUI;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class IpPromptGrid extends GridPane {
	
	final int fieldNumber = 4;
	final Button submit = new Button("Submit");
	String inputIp = new String();
	String port;
	ArrayList<IpTextField> iFields = new ArrayList<>();
	ArrayList<PortTextField> pField = new ArrayList<>();
	Text title = new Text("Connect to Server IP:");
	Text portNumber = new Text("Enter Port:");
	Stage parent;
	
	public IpPromptGrid(Stage p) {
		super();
		init();
		parent = p;
	}
	
	private void init(){		
		submit.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				getIp();
				
			}
		});
		
		this.setOnKeyPressed(new EventHandler<KeyEvent>() {

	        @Override
	        public void handle(KeyEvent t) {
	            if (t.getCode() == KeyCode.ENTER){
	            	getIp();
	            };
	        }
	    });
		
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		for (int i = 0;i < fieldNumber; i++){
			iFields.add(new IpTextField());
		}
		
		for (int i = 0; i < iFields.size(); i++){
			this.add(iFields.get(i),i,1);
		}
		portNumber.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		pField.add(new PortTextField());
		this.add(portNumber, 0, 2);
		this.add(pField.get(0), 0, 3, 1, 1);
		
		this.add(title, 0, 0,fieldNumber,1);
		this.add(submit, 0, 4);
		
		this.setPadding(new Insets(25, 25, 25, 25));
        this.setGridLinesVisible(false);		
	 }
	
	public String returnIp(){
		return inputIp;
	}
	
	public String returnPort(){
		return port;
	}
	
	private final void getIp(){
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < fieldNumber; i++){
			b.append(iFields.get(i).getText());
			if (i < fieldNumber-1) b.append(".");					
		}	
		
		port = pField.get(0).getText();
		
		inputIp =  b.toString();
		System.out.println("InputIP: " + inputIp);
		System.out.println("Port: " + port);
		parent.close();
	}
	
}
