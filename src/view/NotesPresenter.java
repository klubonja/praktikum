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
import cluedoNetworkLayer.CluedoGameClient;
import cluedoNetworkLayer.CluedoPlayer;


/**
 * 
 * @author YinYanYolos
 * 
 * Presenter for the NotesView Class.
 *
 */
public class NotesPresenter {
	
	private NotesView view;
	
//	private CluedoPlayer currentPlayer;
	
	private CluedoGameClient client;
	
	//Creating new Radial Gradients for the color of the Buttons.
	private final Stop[] redlist = new Stop[] { new Stop(0, Color.CRIMSON), new Stop(1, Color.TOMATO)};
	private final RadialGradient redGrad = new RadialGradient(1, 0, 0, 0, 1, true, CycleMethod.NO_CYCLE, redlist);
	private final Stop[] bluelist = new Stop[] { new Stop(0, Color.ROYALBLUE), new Stop(1, Color.LIGHTSKYBLUE)};
	private final RadialGradient blueGrad = new RadialGradient(1, 0, 0, 0, 1, true, CycleMethod.NO_CYCLE, bluelist);
	private final Stop[] greenlist = new Stop[] { new Stop(0, Color.LIMEGREEN), new Stop(1, Color.MEDIUMSPRINGGREEN)};
	private final RadialGradient greenGrad = new RadialGradient(1, 0, 0, 0, 1, true, CycleMethod.NO_CYCLE, greenlist);
	
	//Creates three different BackgroundFills.
	private final BackgroundFill redFill = new BackgroundFill(redGrad, 
			new CornerRadii(4), new Insets(0.2));
	private final BackgroundFill blueFill = new BackgroundFill(blueGrad, 
			new CornerRadii(4), new Insets(0.2));
	private final BackgroundFill greenFill = new BackgroundFill(greenGrad, 
			new CornerRadii(4), new Insets(0.2));
	
	// Assigns the created Background Fills to Backgrounds.
	private final Background red = new Background(redFill);
	private final Background blue = new Background(blueFill);
	private final Background green = new Background(greenFill);
	
	// Default Button Fill and Border from class IntroView.
	private final Background defaultButton;
	@SuppressWarnings("unused")
	private final Border defaultBorder;
	private final Background defaultTxt;
	
	// Creates new BorderStrokes for the three types of buttons and assigns them to the Borders.
	private final BorderStroke redStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, 
			new CornerRadii(4), new BorderWidths(0.7));
	private final Border redBorder = new Border(redStroke);
	private final BorderStroke blueStroke = new BorderStroke(Color.DARKSLATEBLUE, BorderStrokeStyle.SOLID, 
			new CornerRadii(4), new BorderWidths(0.7));
	private final Border blueBorder = new Border(blueStroke);
	private final BorderStroke greenStroke = new BorderStroke(Color.DARKGREEN, BorderStrokeStyle.SOLID, 
			new CornerRadii(4), new BorderWidths(0.7));
	private final Border greenBorder = new Border(greenStroke);
	
	//Creates the Background Fills for the TextFields
	private final BackgroundFill redtxt = new BackgroundFill(Color.TOMATO, 
			new CornerRadii(4), new Insets(0.2));
	private final BackgroundFill bluetxt = new BackgroundFill(Color.LIGHTSKYBLUE, 
			new CornerRadii(4), new Insets(0.2));
	private final BackgroundFill greentxt = new BackgroundFill(Color.MEDIUMSPRINGGREEN, 
			new CornerRadii(4), new Insets(0.2));
	private final Background redTxt = new Background(redtxt);
	private final Background blueTxt = new Background(bluetxt);
	private final Background greenTxt = new Background(greentxt);
	
	
	
	public NotesPresenter(NotesView view){
		
		this.view = view;
		this.client = view.getClient();
		//this.currentPlayer = currentPlayer;
		this.defaultButton = view.getDefaultButton();
		this.defaultBorder = view.getDefaultBorder();
		this.defaultTxt = view.getDefaultTxt();
		
		startEvents();
		
	}
	
	/**
	 * Triggers the events.
	 */
	public void startEvents(){
		
		
		for(Button button : view.getButtons()){
			button.setOnMouseClicked(e -> redBack(button));
			button.setOnMouseEntered(e -> glowButton(button));
			button.setOnMouseExited(e -> removeGlow(button));
			
			updateNotes();
		}
		
	}
	
	/**
	 * Highlights the buttons that correspond to the actual cards in hand.
	 */
	public void updateNotes(){
		
		for (CluedoPlayer p : client.getPlayersConnected()) {
			if (p.getNick().equals(client.getMyNick())) {
				for (String str : p.getCards()) {
					
					if(str.equals("Reverend Green")){
						redBack(view.getButton1());
						
					}if(str.equals("Fräulein Gloria")){
						redBack(view.getButton2());
						
					}if(str.equals("Professor Bloom")){
						redBack(view.getButton3());
						
					}if(str.equals("Baronin von Porz")){
						redBack(view.getButton4());
						
					}if(str.equals("Oberts von Gatow")){
						redBack(view.getButton5());
						
					}if(str.equals("Frau Weiss")){
						redBack(view.getButton6());
						
					}if(str.equals("dagger")){
						redBack(view.getButton7());
						
					}if(str.equals("candlestick")){
						redBack(view.getButton8());
						
					}if(str.equals("revolver")){
						redBack(view.getButton9());
						
					}if(str.equals("rope")){
						redBack(view.getButton10());
						
					}if(str.equals("pipe")){
						redBack(view.getButton11());
						
					}if(str.equals("spanner")){
						redBack(view.getButton12());
						
					}if(str.equals("hall")){
						redBack(view.getButton13());
						
					}if(str.equals("lounge")){
						redBack(view.getButton14());
						
					}if(str.equals("diningroom")){
						redBack(view.getButton15());
						
					}if(str.equals("kitchen")){
						redBack(view.getButton16());
						
					}if(str.equals("ballroom")){
						redBack(view.getButton17());
						
					}if(str.equals("conservatory")){
						redBack(view.getButton18());
						
					}if(str.equals("billiard")){
						redBack(view.getButton19());
						
					}if(str.equals("library")){
						redBack(view.getButton20());
						
					}if(str.equals("study")){
						redBack(view.getButton21());
						
					}
					
				}
			}
		}
//		for(String card : currentPlayer.getCards()){
//			
//			if(card.equals("green")){
//				redBack(view.getButton1());
//				
//			}if(card.equals("red")){
//				redBack(view.getButton2());
//				
//			}if(card.equals("blue")){
//				redBack(view.getButton3());
//				
//			}if(card.equals("purple")){
//				redBack(view.getButton4());
//				
//			}if(card.equals("yellow")){
//				redBack(view.getButton5());
//				
//			}if(card.equals("white")){
//				redBack(view.getButton6());
//				
//			}if(card.equals("dagger")){
//				redBack(view.getButton7());
//				
//			}if(card.equals("candlestick")){
//				redBack(view.getButton8());
//				
//			}if(card.equals("revolver")){
//				redBack(view.getButton9());
//				
//			}if(card.equals("rope")){
//				redBack(view.getButton10());
//				
//			}if(card.equals("pipe")){
//				redBack(view.getButton11());
//				
//			}if(card.equals("spanner")){
//				redBack(view.getButton12());
//				
//			}if(card.equals("hall")){
//				redBack(view.getButton13());
//				
//			}if(card.equals("loungue")){
//				redBack(view.getButton14());
//				
//			}if(card.equals("diningroom")){
//				redBack(view.getButton15());
//				
//			}if(card.equals("kitchen")){
//				redBack(view.getButton16());
//				
//			}if(card.equals("ballroom")){
//				redBack(view.getButton17());
//				
//			}if(card.equals("conservatory")){
//				redBack(view.getButton18());
//				
//			}if(card.equals("billiard")){
//				redBack(view.getButton19());
//				
//			}if(card.equals("library")){
//				redBack(view.getButton20());
//				
//			}if(card.equals("study")){
//				redBack(view.getButton21());
//				
//			}
//		}
		
	}
	
	/**
	 * Sets the background of a selected Button and of the corresponding TextField to red.
	 * @param button 
	 * 	The Button, which background has to be colored in red.
	 */
	public void redBack(Button button){
		
		button.setBackground(red);
		button.setBorder(redBorder);
		if(button.equals(view.getButton1())){
			view.getTxt1().setBackground(redTxt);
			view.getTxt1().setBorder(redBorder);
		}
		if(button.equals(view.getButton1())){
			view.txt1.setBackground(redTxt);
			view.txt1.setBorder(redBorder);
		}
		if(button.equals(view.getButton2())){
			view.txt2.setBackground(redTxt);
			view.txt2.setBorder(redBorder);
		}
		if(button.equals(view.getButton3())){
			view.txt3.setBackground(redTxt);
			view.txt3.setBorder(redBorder);
		}
		if(button.equals(view.getButton4())){
			view.txt4.setBackground(redTxt);
			view.txt4.setBorder(redBorder);
		}
		if(button.equals(view.getButton5())){
			view.txt5.setBackground(redTxt);
			view.txt5.setBorder(redBorder);
		}
		if(button.equals(view.getButton6())){
			view.txt6.setBackground(redTxt);
			view.txt6.setBorder(redBorder);
		}
		if(button.equals(view.getButton7())){
			view.txt7.setBackground(redTxt);
			view.txt7.setBorder(redBorder);
		}
		if(button.equals(view.getButton8())){
			view.txt8.setBackground(redTxt);
			view.txt8.setBorder(redBorder);
		}
		if(button.equals(view.getButton9())){
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
	
	/**
	 * Sets the background of a selected Button and of the corresponding TextField to blue.
	 * @param button The Button, which background has to be colored in blue.
	 */
	public void blueBack(Button button){
		button.setBackground(blue);
		button.setBorder(blueBorder);
		
		if(button.equals(view.getButton1())){
			view.txt1.setBackground(blueTxt);
			view.txt1.setBorder(redBorder);
		}
		if(button.equals(view.getButton2())){
			view.txt2.setBackground(blueTxt);
			view.txt2.setBorder(redBorder);
		}
		if(button.equals(view.getButton3())){
			view.txt3.setBackground(blueTxt);
			view.txt3.setBorder(redBorder);
		}
		if(button.equals(view.getButton4())){
			view.txt4.setBackground(blueTxt);
			view.txt4.setBorder(redBorder);
		}
		if(button.equals(view.getButton5())){
			view.txt5.setBackground(blueTxt);
			view.txt5.setBorder(redBorder);
		}
		if(button.equals(view.getButton6())){
			view.txt6.setBackground(blueTxt);
			view.txt6.setBorder(redBorder);
		}
		if(button.equals(view.getButton7())){
			view.txt7.setBackground(blueTxt);
			view.txt7.setBorder(redBorder);
		}
		if(button.equals(view.getButton8())){
			view.txt8.setBackground(blueTxt);
			view.txt8.setBorder(redBorder);
		}
		if(button.equals(view.getButton9())){
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
	
	/**
	 * Sets the background of a selected Button and of the corresponding TextField to green.
	 * @param button 
	 * 	The Button, which background has to be colored in green.
	 */
	public void greenBack(Button button){
		button.setBackground(green);
		button.setBorder(greenBorder);
		
		if(button.equals(view.getButton1())){
			view.getTxt1().setBackground(greenTxt);
			view.getTxt1().setBorder(redBorder);
		}
		if(button.equals(view.getButton2())){
			view.txt2.setBackground(greenTxt);
			view.txt2.setBorder(redBorder);
		}
		if(button.equals(view.getButton3())){
			view.txt3.setBackground(greenTxt);
			view.txt3.setBorder(redBorder);
		}
		if(button.equals(view.getButton4())){
			view.txt4.setBackground(greenTxt);
			view.txt4.setBorder(redBorder);
		}
		if(button.equals(view.getButton5())){
			view.txt5.setBackground(greenTxt);
			view.txt5.setBorder(redBorder);
		}
		if(button.equals(view.getButton6())){
			view.txt6.setBackground(greenTxt);
			view.txt6.setBorder(redBorder);
		}
		if(button.equals(view.getButton7())){
			view.txt7.setBackground(greenTxt);
			view.txt7.setBorder(redBorder);
		}
		if(button.equals(view.getButton8())){
			view.txt8.setBackground(greenTxt);
			view.txt8.setBorder(redBorder);
		}
		if(button.equals(view.getButton9())){
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
	
	/**
	 * Sets the background of a selected Button and of the corresponding TextField to default.
	 * @param button 
	 * 	The Button, which background has to be set to default color.
	 */
	public void defaultBack(Button button){
		button.setBackground(defaultButton);
		button.setBorder(redBorder);
		
		if(button.equals(view.getButton1())){
			view.txt1.setBackground(defaultTxt);
			view.txt1.setBorder(redBorder);
		}
		if(button.equals(view.getButton2())){
			view.txt2.setBackground(defaultTxt);
			view.txt2.setBorder(redBorder);
		}
		if(button.equals(view.getButton3())){
			view.txt3.setBackground(defaultTxt);
			view.txt3.setBorder(redBorder);
		}
		if(button.equals(view.getButton4())){
			view.txt4.setBackground(defaultTxt);
			view.txt4.setBorder(redBorder);
		}
		if(button.equals(view.getButton5())){
			view.txt5.setBackground(defaultTxt);
			view.txt5.setBorder(redBorder);
		}
		if(button.equals(view.getButton6())){
			view.txt6.setBackground(defaultTxt);
			view.txt6.setBorder(redBorder);
		}
		if(button.equals(view.getButton7())){
			view.txt7.setBackground(defaultTxt);
			view.txt7.setBorder(redBorder);
		}
		if(button.equals(view.getButton8())){
			view.txt8.setBackground(defaultTxt);
			view.txt8.setBorder(redBorder);
		}
		if(button.equals(view.getButton9())){
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
	
	/**
	 * Add a glow effect to the selected Button.
	 * @param button 
	 * 	The Button, which the glow effect should be added.
	 */
	public void glowButton(Button button){
		button.setEffect(new Glow(0.4));
		
		if(button.equals(view.getButton1())){
			view.txt1.setEffect(new Glow(3.0));
		}
		if(button.equals(view.getButton2())){
			view.txt2.setEffect(new Glow(3.0));
		}
		if(button.equals(view.getButton3())){
			view.txt3.setEffect(new Glow(3.0));
		}
		if(button.equals(view.getButton4())){
			view.txt4.setEffect(new Glow(3.0));
		}
		if(button.equals(view.getButton5())){
			view.txt5.setEffect(new Glow(3.0));
		}
		if(button.equals(view.getButton6())){
			view.txt6.setEffect(new Glow(3.0));
		}
		if(button.equals(view.getButton7())){
			view.txt7.setEffect(new Glow(3.0));
		}
		if(button.equals(view.getButton8())){
			view.txt8.setEffect(new Glow(3.0));
		}
		if(button.equals(view.getButton9())){
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
	
	/**
	 * Removes the glow effect to the selected Button.
	 * @param button 
	 * 	The Button, which the glow effect should be removed.
	 */
	public void removeGlow(Button button){
		button.setEffect(null);
		
		if(button.equals(view.getButton1())){
			view.txt1.setEffect(null);
		}
		if(button.equals(view.getButton2())){
			view.txt2.setEffect(null);
		}
		if(button.equals(view.getButton3())){
			view.txt3.setEffect(null);
		}
		if(button.equals(view.getButton4())){
			view.txt4.setEffect(null);
		}
		if(button.equals(view.getButton5())){
			view.txt5.setEffect(null);
		}
		if(button.equals(view.getButton6())){
			view.txt6.setEffect(null);
		}
		if(button.equals(view.getButton7())){
			view.txt7.setEffect(null);
		}
		if(button.equals(view.getButton8())){
			view.txt8.setEffect(null);
		}
		if(button.equals(view.getButton9())){
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

}
