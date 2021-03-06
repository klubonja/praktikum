package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
	

	private Label chatLabel;
	private HBox chatBox;
	//Button chatSend;
	private TextField chatField;

	
	
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
	
	InnerShadow innerShadow6 = new InnerShadow();
	innerShadow6.setOffsetX(0);
	innerShadow6.setOffsetY(0);
	innerShadow6.setWidth(15);
	innerShadow6.setHeight(15);
	innerShadow6.setColor(Color.DARKSLATEGREY);
	
	chatField.setEffect(innerShadow6);
	
	}


	public TextArea getChatArea() {
		return chatArea;
	}


	public void setChatArea(TextArea chatArea) {
		this.chatArea = chatArea;
	}


	public Label getChatLabel() {
		return chatLabel;
	}


	public void setChatLabel(Label chatLabel) {
		this.chatLabel = chatLabel;
	}


	public HBox getChatBox() {
		return chatBox;
	}


	public void setChatBox(HBox chatBox) {
		this.chatBox = chatBox;
	}


	public TextField getChatField() {
		return chatField;
	}


	public void setChatField(TextField chatField) {
		this.chatField = chatField;
	}
	
	

}
