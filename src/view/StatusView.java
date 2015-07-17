package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class StatusView extends VBox {
	
	Text currentEvents;
	Text serverStateMessages;
	Label currentEventsTitle = new Label("Current Events:");
	Label serverStateMessagesTitle = new Label("Server Says:");
	
	
	
	public StatusView(){
		super();
		currentEvents = new Text("no events");
		serverStateMessages = new Text("no statemsg");
		serverStateMessages.setStyle("-fx-text-fill: red;");
		setAlignment(Pos.CENTER);
		setPadding(new Insets(10, 10, 10, 10));
		setSpacing(10);
		getChildren().addAll(serverStateMessages,currentEvents);
	}
	
	public void setSStateMsg(String msg){
		serverStateMessages.setText(msg);
	}
	
	public void setCurEventsMsg(String msg){
		currentEvents.setText(msg);
	}
	
	
}
