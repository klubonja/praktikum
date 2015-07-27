package staticClasses;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Images {
	public static ImageView bg;
	public static ImageView zug;
	
	public static Image white_n;
	public static Image white_s;
	public static Image white_o;
	public static Image white_w;
	
	public static Image green_n;
	public static Image green_s;
	public static Image green_o;
	public static Image green_w;
	
	public static Image blue_n;
	public static Image blue_s;
	public static Image blue_o;
	public static Image blue_w;
	
	public static Image purple_n;
	public static Image purple_s;
	public static Image purple_o;
	public static Image purple_w;
	
	public static Image red_n;
	public static Image red_s;
	public static Image red_o;
	public static Image red_w;
	
	public static Image yellow_n;
	public static Image yellow_s;
	public static Image yellow_o;
	public static Image yellow_w;
	
	public static ImageView white;
	public static ImageView green;
	public static ImageView blue;
	public static ImageView purple;
	public static ImageView red;
	public static ImageView yellow;

	public static Image disabled;
	public static Image enabled;
	public static Image suspectImage;
	public static Image rollImage;
	public static Image passageImage;
	public static ImageView suspect;
	public static ImageView roll;
	public static ImageView passage;
	public static ImageView suspectNOW;
	public static ImageView suspectLATER;
	
	public static void initImages(){
	bg = new ImageView(new Image("media/Field_0.png"));
	zug = new ImageView(new Image("media/ZugFensterResized3.png"));
	
	white_n = new Image("media/gameObjects/white_n.png");
	white_s = new Image("media/gameObjects/white_s.png");
	white_o = new Image("media/gameObjects/white_o.png");
	white_w = new Image("media/gameObjects/white_w.png");
	
	green_n = new Image("media/gameObjects/green_n.png");
	green_s = new Image("media/gameObjects/green_s.png");
	green_o = new Image("media/gameObjects/green_o.png");
	green_w = new Image("media/gameObjects/green_w.png");
	
	blue_n = new Image("media/gameObjects/blue_n.png");
	blue_s = new Image("media/gameObjects/blue_s.png");
	blue_o = new Image("media/gameObjects/blue_o.png");
	blue_w = new Image("media/gameObjects/blue_w.png");
	
	purple_n = new Image("media/gameObjects/purple_n.png");
	purple_s = new Image("media/gameObjects/purple_s.png");
	purple_o = new Image("media/gameObjects/purple_o.png");
	purple_w = new Image("media/gameObjects/purple_w.png");
	
	red_n = new Image("media/gameObjects/red_n.png");
	red_s = new Image("media/gameObjects/red_s.png");
	red_o = new Image("media/gameObjects/red_o.png");
	red_w = new Image("media/gameObjects/red_w.png");
	
	yellow_n = new Image("media/gameObjects/yellow_n.png");
	yellow_s = new Image("media/gameObjects/yellow_s.png");
	yellow_o = new Image("media/gameObjects/yellow_o.png");
	yellow_w = new Image("media/gameObjects/yellow_w.png");
	
	white = new ImageView(white_n);
	green = new ImageView(green_n);
	blue = new ImageView(blue_o);
	purple = new ImageView(purple_o);
	red = new ImageView(red_s);
	yellow = new ImageView(yellow_w);
	
	disabled = new Image("media/buttons/disabled.png");
	enabled = new Image("media/buttons/enabled.png");
	suspectImage = new Image("media/buttons/suspect.png");
	rollImage = new Image("media/buttons/roll.png");
	passageImage = new Image("media/buttons/passage.png");
	suspect = new ImageView(enabled);
	roll = new ImageView(enabled);
	passage = new ImageView(enabled);
	
	suspectNOW = new ImageView(new Image("media/buttons/suspectNOW.png"));
	suspectLATER = new ImageView(new Image("media/buttons/suspectLATER.png"));
	}
}
