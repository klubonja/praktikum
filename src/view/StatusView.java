package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class StatusView extends VBox {
	
	Text yourstates;
	Text othersstates;
	Label currentEventsTitle = new Label("Current Events:");
	Label serverStateMessagesTitle = new Label("Server Says:");
	
	
	
	public StatusView(){
		super();
		yourstates = new Text("no events");
		othersstates = new Text("no statemsg");
		othersstates.setStyle("-fx-text-fill: red;");
		setAlignment(Pos.CENTER);
		setPadding(new Insets(10, 10, 10, 10));
		setSpacing(5);
		getChildren().addAll(othersstates,yourstates);
	}
	
	public void setothersstatemsg(String msg){
		othersstates.setText(msg);
	}
	
	public void setyourstatesmsg(String msg){
		yourstates.setText(msg);
	}
	
	
}
