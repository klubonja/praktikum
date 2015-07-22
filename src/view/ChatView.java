package view;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @since 26.05.2015
 * @version 2.0
 * @author Kristi Lubonja
 * 
 * Chat Window rapresented as a VBox
 *
 */



public class ChatView extends VBox {
	

	private TextArea chatArea;
	Label chatLabel;
	HBox chatBox;
	//Button chatSend;
	TextField chatField;
	
	
	public ChatView(){
	
	chatLabel = new Label("Chat");
	setChatArea(new TextArea());
	//chatArea.set
	getChatArea().setEditable(false);
	chatBox = new HBox(3);
	//chatSend = new Button("Send");
	chatField = new TextField();
	//chatField.setPrefWidth(200);
	chatField.setMaxWidth(200);
	//chatBox.getChildren().addAll(chatField, chatSend);
	this.getChildren().addAll(getChatArea(),chatField,chatBox);
	
	}


	public TextArea getChatArea() {
		return chatArea;
	}


	public void setChatArea(TextArea chatArea) {
		this.chatArea = chatArea;
	}

}
