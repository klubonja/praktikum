package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;


public class NotesPresenter {
	
	NotesView view;
	
	//Creating new Radial Gradients for the color of the Buttons
	Stop[] redlist = new Stop[] { new Stop(0, Color.CRIMSON), new Stop(1, Color.TOMATO)};
	RadialGradient redGrad = new RadialGradient(1, 0, 0, 0, 1, true, CycleMethod.NO_CYCLE, redlist);
	Stop[] bluelist = new Stop[] { new Stop(0, Color.ROYALBLUE), new Stop(1, Color.LIGHTSKYBLUE)};
	RadialGradient blueGrad = new RadialGradient(1, 0, 0, 0, 1, true, CycleMethod.NO_CYCLE, bluelist);
	Stop[] greenlist = new Stop[] { new Stop(0, Color.LIMEGREEN), new Stop(1, Color.MEDIUMSPRINGGREEN)};
	RadialGradient greenGrad = new RadialGradient(1, 0, 0, 0, 1, true, CycleMethod.NO_CYCLE, greenlist);
	
	//Creates three different BackgroundFills
	BackgroundFill redFill = new BackgroundFill(redGrad, 
			new CornerRadii(4), new Insets(0.2));
	BackgroundFill blueFill = new BackgroundFill(blueGrad, 
			new CornerRadii(4), new Insets(0.2));
	BackgroundFill greenFill = new BackgroundFill(greenGrad, 
			new CornerRadii(4), new Insets(0.2));
	
	// Assigns the created Background Fills to Backgrounds
	Background red = new Background(redFill);
	Background blue = new Background(blueFill);
	Background green = new Background(greenFill);
	
	// Default Button Fill and Border from class IntroView
	Background defaultButton;
	Border defaultBorder;
	Background defaultTxt;
	
	// Creates new BorderStrokes for the three types of buttons and assigns them to the Borders
	BorderStroke redStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, 
			new CornerRadii(4), new BorderWidths(0.7));
	Border redBorder = new Border(redStroke);
	BorderStroke blueStroke = new BorderStroke(Color.DARKSLATEBLUE, BorderStrokeStyle.SOLID, 
			new CornerRadii(4), new BorderWidths(0.7));
	Border blueBorder = new Border(blueStroke);
	BorderStroke greenStroke = new BorderStroke(Color.DARKGREEN, BorderStrokeStyle.SOLID, 
			new CornerRadii(4), new BorderWidths(0.7));
	Border greenBorder = new Border(greenStroke);
	
	//Creates the Background Fills for the TextFields
	BackgroundFill redtxt = new BackgroundFill(Color.TOMATO, 
			new CornerRadii(4), new Insets(0.2));
	BackgroundFill bluetxt = new BackgroundFill(Color.LIGHTSKYBLUE, 
			new CornerRadii(4), new Insets(0.2));
	BackgroundFill greentxt = new BackgroundFill(Color.MEDIUMSPRINGGREEN, 
			new CornerRadii(4), new Insets(0.2));
	Background redTxt = new Background(redtxt);
	Background blueTxt = new Background(bluetxt);
	Background greenTxt = new Background(greentxt);
	
	
	
	public NotesPresenter(NotesView view){
		
		this.view = view;
		defaultButton = view.defaultButton;
		defaultBorder = view.defaultBorder;
		defaultTxt = view.defaultTxt;
		
		startEvents();
		
	}
	
	public void startEvents(){
		
		for(Button button : view.buttons){
			button.setOnMouseClicked(e -> redBack(button));
			button.setOnMouseEntered(e -> glowButton(button));
			button.setOnMouseExited(e -> removeGlow(button));
		}
		
	}
	
	public void redBack(Button button){
		
		button.setBackground(red);
		button.setBorder(redBorder);
		if(button.equals(view.button1)){
			view.txt1.setBackground(redTxt);
			view.txt1.setBorder(redBorder);
		}
		if(button.equals(view.button1)){
			view.txt1.setBackground(redTxt);
			view.txt1.setBorder(redBorder);
		}
		if(button.equals(view.button2)){
			view.txt2.setBackground(redTxt);
			view.txt2.setBorder(redBorder);
		}
		if(button.equals(view.button3)){
			view.txt3.setBackground(redTxt);
			view.txt3.setBorder(redBorder);
		}
		if(button.equals(view.button4)){
			view.txt4.setBackground(redTxt);
			view.txt4.setBorder(redBorder);
		}
		if(button.equals(view.button5)){
			view.txt5.setBackground(redTxt);
			view.txt5.setBorder(redBorder);
		}
		if(button.equals(view.button6)){
			view.txt6.setBackground(redTxt);
			view.txt6.setBorder(redBorder);
		}
		if(button.equals(view.button7)){
			view.txt7.setBackground(redTxt);
			view.txt7.setBorder(redBorder);
		}
		if(button.equals(view.button8)){
			view.txt8.setBackground(redTxt);
			view.txt8.setBorder(redBorder);
		}
		if(button.equals(view.button9)){
			view.txt9.setBackground(redTxt);
			view.txt9.setBorder(redBorder);
		}
		if(button.equals(view.button10)){
			view.txt10.setBackground(redTxt);
			view.txt10.setBorder(redBorder);
		}
		if(button.equals(view.button11)){
			view.txt11.setBackground(redTxt);
			view.txt11.setBorder(redBorder);
		}
		if(button.equals(view.button12)){
			view.txt12.setBackground(redTxt);
			view.txt12.setBorder(redBorder);
		}
		if(button.equals(view.button13)){
			view.txt13.setBackground(redTxt);
			view.txt13.setBorder(redBorder);
		}
		if(button.equals(view.button14)){
			view.txt14.setBackground(redTxt);
			view.txt14.setBorder(redBorder);
		}
		if(button.equals(view.button15)){
			view.txt15.setBackground(redTxt);
			view.txt15.setBorder(redBorder);
		}
		if(button.equals(view.button16)){
			view.txt16.setBackground(redTxt);
			view.txt16.setBorder(redBorder);
		}
		if(button.equals(view.button17)){
			view.txt17.setBackground(redTxt);
			view.txt17.setBorder(redBorder);
		}
		if(button.equals(view.button18)){
			view.txt18.setBackground(redTxt);
			view.txt18.setBorder(redBorder);
		}
		if(button.equals(view.button19)){
			view.txt19.setBackground(redTxt);
			view.txt19.setBorder(redBorder);
		}
		if(button.equals(view.button20)){
			view.txt20.setBackground(redTxt);
			view.txt20.setBorder(redBorder);
		}
		if(button.equals(view.button21)){
			view.txt21.setBackground(redTxt);
			view.txt21.setBorder(redBorder);
		}
		
		
		if(button.getBackground().equals(red)){
			button.setOnMouseClicked(e -> blueBack(button));
			}
		}
	
	public void blueBack(Button button){
		button.setBackground(blue);
		button.setBorder(blueBorder);
		
		if(button.equals(view.button1)){
			view.txt1.setBackground(blueTxt);
			view.txt1.setBorder(redBorder);
		}
		if(button.equals(view.button2)){
			view.txt2.setBackground(blueTxt);
			view.txt2.setBorder(redBorder);
		}
		if(button.equals(view.button3)){
			view.txt3.setBackground(blueTxt);
			view.txt3.setBorder(redBorder);
		}
		if(button.equals(view.button4)){
			view.txt4.setBackground(blueTxt);
			view.txt4.setBorder(redBorder);
		}
		if(button.equals(view.button5)){
			view.txt5.setBackground(blueTxt);
			view.txt5.setBorder(redBorder);
		}
		if(button.equals(view.button6)){
			view.txt6.setBackground(blueTxt);
			view.txt6.setBorder(redBorder);
		}
		if(button.equals(view.button7)){
			view.txt7.setBackground(blueTxt);
			view.txt7.setBorder(redBorder);
		}
		if(button.equals(view.button8)){
			view.txt8.setBackground(blueTxt);
			view.txt8.setBorder(redBorder);
		}
		if(button.equals(view.button9)){
			view.txt9.setBackground(blueTxt);
			view.txt9.setBorder(redBorder);
		}
		if(button.equals(view.button10)){
			view.txt10.setBackground(blueTxt);
			view.txt10.setBorder(redBorder);
		}
		if(button.equals(view.button11)){
			view.txt11.setBackground(blueTxt);
			view.txt11.setBorder(redBorder);
		}
		if(button.equals(view.button12)){
			view.txt12.setBackground(blueTxt);
			view.txt12.setBorder(redBorder);
		}
		if(button.equals(view.button13)){
			view.txt13.setBackground(blueTxt);
			view.txt13.setBorder(redBorder);
		}
		if(button.equals(view.button14)){
			view.txt14.setBackground(blueTxt);
			view.txt14.setBorder(redBorder);
		}
		if(button.equals(view.button15)){
			view.txt15.setBackground(blueTxt);
			view.txt15.setBorder(redBorder);
		}
		if(button.equals(view.button16)){
			view.txt16.setBackground(blueTxt);
			view.txt16.setBorder(redBorder);
		}
		if(button.equals(view.button17)){
			view.txt17.setBackground(blueTxt);
			view.txt17.setBorder(redBorder);
		}
		if(button.equals(view.button18)){
			view.txt18.setBackground(blueTxt);
			view.txt18.setBorder(redBorder);
		}
		if(button.equals(view.button19)){
			view.txt19.setBackground(blueTxt);
			view.txt19.setBorder(redBorder);
		}
		if(button.equals(view.button20)){
			view.txt20.setBackground(blueTxt);
			view.txt20.setBorder(redBorder);
		}
		if(button.equals(view.button21)){
			view.txt21.setBackground(blueTxt);
			view.txt21.setBorder(redBorder);
		}
		
		if(button.getBackground().equals(blue)){
			button.setOnMouseClicked(e -> greenBack(button));
			}
	}
	
	public void greenBack(Button button){
		button.setBackground(green);
		button.setBorder(greenBorder);
		
		if(button.equals(view.button1)){
			view.txt1.setBackground(greenTxt);
			view.txt1.setBorder(redBorder);
		}
		if(button.equals(view.button2)){
			view.txt2.setBackground(greenTxt);
			view.txt2.setBorder(redBorder);
		}
		if(button.equals(view.button3)){
			view.txt3.setBackground(greenTxt);
			view.txt3.setBorder(redBorder);
		}
		if(button.equals(view.button4)){
			view.txt4.setBackground(greenTxt);
			view.txt4.setBorder(redBorder);
		}
		if(button.equals(view.button5)){
			view.txt5.setBackground(greenTxt);
			view.txt5.setBorder(redBorder);
		}
		if(button.equals(view.button6)){
			view.txt6.setBackground(greenTxt);
			view.txt6.setBorder(redBorder);
		}
		if(button.equals(view.button7)){
			view.txt7.setBackground(greenTxt);
			view.txt7.setBorder(redBorder);
		}
		if(button.equals(view.button8)){
			view.txt8.setBackground(greenTxt);
			view.txt8.setBorder(redBorder);
		}
		if(button.equals(view.button9)){
			view.txt9.setBackground(greenTxt);
			view.txt9.setBorder(redBorder);
		}
		if(button.equals(view.button10)){
			view.txt10.setBackground(greenTxt);
			view.txt10.setBorder(redBorder);
		}
		if(button.equals(view.button11)){
			view.txt11.setBackground(greenTxt);
			view.txt11.setBorder(redBorder);
		}
		if(button.equals(view.button12)){
			view.txt12.setBackground(greenTxt);
			view.txt12.setBorder(redBorder);
		}
		if(button.equals(view.button13)){
			view.txt13.setBackground(greenTxt);
			view.txt13.setBorder(redBorder);
		}
		if(button.equals(view.button14)){
			view.txt14.setBackground(greenTxt);
			view.txt14.setBorder(redBorder);
		}
		if(button.equals(view.button15)){
			view.txt15.setBackground(greenTxt);
			view.txt15.setBorder(redBorder);
		}
		if(button.equals(view.button16)){
			view.txt16.setBackground(greenTxt);
			view.txt16.setBorder(redBorder);
		}
		if(button.equals(view.button17)){
			view.txt17.setBackground(greenTxt);
			view.txt17.setBorder(redBorder);
		}
		if(button.equals(view.button18)){
			view.txt18.setBackground(greenTxt);
			view.txt18.setBorder(redBorder);
		}
		if(button.equals(view.button19)){
			view.txt19.setBackground(greenTxt);
			view.txt19.setBorder(redBorder);
		}
		if(button.equals(view.button20)){
			view.txt20.setBackground(greenTxt);
			view.txt20.setBorder(redBorder);
		}
		if(button.equals(view.button21)){
			view.txt21.setBackground(greenTxt);
			view.txt21.setBorder(redBorder);
		}
		
		if(button.getBackground().equals(green)){
			button.setOnMouseClicked(e -> defaultBack(button));
			}
	}
	
	public void defaultBack(Button button){
		button.setBackground(defaultButton);
		button.setBorder(redBorder);
		
		if(button.equals(view.button1)){
			view.txt1.setBackground(defaultTxt);
			view.txt1.setBorder(redBorder);
		}
		if(button.equals(view.button2)){
			view.txt2.setBackground(defaultTxt);
			view.txt2.setBorder(redBorder);
		}
		if(button.equals(view.button3)){
			view.txt3.setBackground(defaultTxt);
			view.txt3.setBorder(redBorder);
		}
		if(button.equals(view.button4)){
			view.txt4.setBackground(defaultTxt);
			view.txt4.setBorder(redBorder);
		}
		if(button.equals(view.button5)){
			view.txt5.setBackground(defaultTxt);
			view.txt5.setBorder(redBorder);
		}
		if(button.equals(view.button6)){
			view.txt6.setBackground(defaultTxt);
			view.txt6.setBorder(redBorder);
		}
		if(button.equals(view.button7)){
			view.txt7.setBackground(defaultTxt);
			view.txt7.setBorder(redBorder);
		}
		if(button.equals(view.button8)){
			view.txt8.setBackground(defaultTxt);
			view.txt8.setBorder(redBorder);
		}
		if(button.equals(view.button9)){
			view.txt9.setBackground(defaultTxt);
			view.txt9.setBorder(redBorder);
		}
		if(button.equals(view.button10)){
			view.txt10.setBackground(defaultTxt);
			view.txt10.setBorder(redBorder);
		}
		if(button.equals(view.button11)){
			view.txt11.setBackground(defaultTxt);
			view.txt11.setBorder(redBorder);
		}
		if(button.equals(view.button12)){
			view.txt12.setBackground(defaultTxt);
			view.txt12.setBorder(redBorder);
		}
		if(button.equals(view.button13)){
			view.txt13.setBackground(defaultTxt);
			view.txt13.setBorder(redBorder);
		}
		if(button.equals(view.button14)){
			view.txt14.setBackground(defaultTxt);
			view.txt14.setBorder(redBorder);
		}
		if(button.equals(view.button15)){
			view.txt15.setBackground(defaultTxt);
			view.txt15.setBorder(redBorder);
		}
		if(button.equals(view.button16)){
			view.txt16.setBackground(defaultTxt);
			view.txt16.setBorder(redBorder);
		}
		if(button.equals(view.button17)){
			view.txt17.setBackground(defaultTxt);
			view.txt17.setBorder(redBorder);
		}
		if(button.equals(view.button18)){
			view.txt18.setBackground(defaultTxt);
			view.txt18.setBorder(redBorder);
		}
		if(button.equals(view.button19)){
			view.txt19.setBackground(defaultTxt);
			view.txt19.setBorder(redBorder);
		}
		if(button.equals(view.button20)){
			view.txt20.setBackground(defaultTxt);
			view.txt20.setBorder(redBorder);
		}
		if(button.equals(view.button21)){
			view.txt21.setBackground(defaultTxt);
			view.txt21.setBorder(redBorder);
		}
		
		if(button.getBackground().equals(defaultButton)){
			button.setOnMouseClicked(e -> redBack(button));
			}
	}
	
	public void glowButton(Button button){
		button.setEffect(new Glow(0.4));
		
		if(button.equals(view.button1)){
			view.txt1.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button2)){
			view.txt2.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button3)){
			view.txt3.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button4)){
			view.txt4.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button5)){
			view.txt5.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button6)){
			view.txt6.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button7)){
			view.txt7.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button8)){
			view.txt8.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button9)){
			view.txt9.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button10)){
			view.txt10.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button11)){
			view.txt11.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button12)){
			view.txt12.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button13)){
			view.txt13.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button14)){
			view.txt14.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button15)){
			view.txt15.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button16)){
			view.txt16.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button17)){
			view.txt17.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button18)){
			view.txt18.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button19)){
			view.txt19.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button20)){
			view.txt20.setEffect(new Glow(3.0));
		}
		if(button.equals(view.button21)){
			view.txt21.setEffect(new Glow(3.0));
			
		}
	}
	
	public void removeGlow(Button button){
		button.setEffect(null);
		
		if(button.equals(view.button1)){
			view.txt1.setEffect(null);
		}
		if(button.equals(view.button2)){
			view.txt2.setEffect(null);
		}
		if(button.equals(view.button3)){
			view.txt3.setEffect(null);
		}
		if(button.equals(view.button4)){
			view.txt4.setEffect(null);
		}
		if(button.equals(view.button5)){
			view.txt5.setEffect(null);
		}
		if(button.equals(view.button6)){
			view.txt6.setEffect(null);
		}
		if(button.equals(view.button7)){
			view.txt7.setEffect(null);
		}
		if(button.equals(view.button8)){
			view.txt8.setEffect(null);
		}
		if(button.equals(view.button9)){
			view.txt9.setEffect(null);
		}
		if(button.equals(view.button10)){
			view.txt10.setEffect(null);
		}
		if(button.equals(view.button11)){
			view.txt11.setEffect(null);
		}
		if(button.equals(view.button12)){
			view.txt12.setEffect(null);
		}
		if(button.equals(view.button13)){
			view.txt13.setEffect(null);
		}
		if(button.equals(view.button14)){
			view.txt14.setEffect(null);
		}
		if(button.equals(view.button15)){
			view.txt15.setEffect(null);
		}
		if(button.equals(view.button16)){
			view.txt16.setEffect(null);
		}
		if(button.equals(view.button17)){
			view.txt17.setEffect(null);
		}
		if(button.equals(view.button18)){
			view.txt18.setEffect(null);
		}
		if(button.equals(view.button19)){
			view.txt19.setEffect(null);
		}
		if(button.equals(view.button20)){
			view.txt20.setEffect(null);
		}
		if(button.equals(view.button21)){
			view.txt21.setEffect(null);
			
		}
	}
	
	
    //Getters and Setters
	public NotesView getView() {
		return view;
	}

	public void setView(NotesView view) {
		this.view = view;
	}

	public Stop[] getRedlist() {
		return redlist;
	}

	public void setRedlist(Stop[] redlist) {
		this.redlist = redlist;
	}

	public RadialGradient getRedGrad() {
		return redGrad;
	}

	public void setRedGrad(RadialGradient redGrad) {
		this.redGrad = redGrad;
	}

	public Stop[] getBluelist() {
		return bluelist;
	}

	public void setBluelist(Stop[] bluelist) {
		this.bluelist = bluelist;
	}

	public RadialGradient getBlueGrad() {
		return blueGrad;
	}

	public void setBlueGrad(RadialGradient blueGrad) {
		this.blueGrad = blueGrad;
	}

	public Stop[] getGreenlist() {
		return greenlist;
	}

	public void setGreenlist(Stop[] greenlist) {
		this.greenlist = greenlist;
	}

	public RadialGradient getGreenGrad() {
		return greenGrad;
	}

	public void setGreenGrad(RadialGradient greenGrad) {
		this.greenGrad = greenGrad;
	}

	public BackgroundFill getRedFill() {
		return redFill;
	}

	public void setRedFill(BackgroundFill redFill) {
		this.redFill = redFill;
	}

	public BackgroundFill getBlueFill() {
		return blueFill;
	}

	public void setBlueFill(BackgroundFill blueFill) {
		this.blueFill = blueFill;
	}

	public BackgroundFill getGreenFill() {
		return greenFill;
	}

	public void setGreenFill(BackgroundFill greenFill) {
		this.greenFill = greenFill;
	}

	public Background getRed() {
		return red;
	}

	public void setRed(Background red) {
		this.red = red;
	}

	public Background getBlue() {
		return blue;
	}

	public void setBlue(Background blue) {
		this.blue = blue;
	}

	public Background getGreen() {
		return green;
	}

	public void setGreen(Background green) {
		this.green = green;
	}

	public Background getDefaultButton() {
		return defaultButton;
	}

	public void setDefaultButton(Background defaultButton) {
		this.defaultButton = defaultButton;
	}

	public Border getDefaultBorder() {
		return defaultBorder;
	}

	public void setDefaultBorder(Border defaultBorder) {
		this.defaultBorder = defaultBorder;
	}

	public Background getDefaultTxt() {
		return defaultTxt;
	}

	public void setDefaultTxt(Background defaultTxt) {
		this.defaultTxt = defaultTxt;
	}

	public BorderStroke getRedStroke() {
		return redStroke;
	}

	public void setRedStroke(BorderStroke redStroke) {
		this.redStroke = redStroke;
	}

	public Border getRedBorder() {
		return redBorder;
	}

	public void setRedBorder(Border redBorder) {
		this.redBorder = redBorder;
	}

	public BorderStroke getBlueStroke() {
		return blueStroke;
	}

	public void setBlueStroke(BorderStroke blueStroke) {
		this.blueStroke = blueStroke;
	}

	public Border getBlueBorder() {
		return blueBorder;
	}

	public void setBlueBorder(Border blueBorder) {
		this.blueBorder = blueBorder;
	}

	public BorderStroke getGreenStroke() {
		return greenStroke;
	}

	public void setGreenStroke(BorderStroke greenStroke) {
		this.greenStroke = greenStroke;
	}

	public Border getGreenBorder() {
		return greenBorder;
	}

	public void setGreenBorder(Border greenBorder) {
		this.greenBorder = greenBorder;
	}

	public BackgroundFill getRedtxt() {
		return redtxt;
	}

	public void setRedtxt(BackgroundFill redtxt) {
		this.redtxt = redtxt;
	}

	public BackgroundFill getBluetxt() {
		return bluetxt;
	}

	public void setBluetxt(BackgroundFill bluetxt) {
		this.bluetxt = bluetxt;
	}

	public BackgroundFill getGreentxt() {
		return greentxt;
	}

	public void setGreentxt(BackgroundFill greentxt) {
		this.greentxt = greentxt;
	}

	public Background getRedTxt() {
		return redTxt;
	}

	public void setRedTxt(Background redTxt) {
		this.redTxt = redTxt;
	}

	public Background getBlueTxt() {
		return blueTxt;
	}

	public void setBlueTxt(Background blueTxt) {
		this.blueTxt = blueTxt;
	}

	public Background getGreenTxt() {
		return greenTxt;
	}

	public void setGreenTxt(Background greenTxt) {
		this.greenTxt = greenTxt;
	}

}
