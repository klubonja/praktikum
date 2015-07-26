package staticClasses;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Images {
	public static ImageView bg;
	public static ImageView zug;
	
	public static ImageView weiss;
	public static ImageView green;
	public static ImageView porz;
	public static ImageView bloom;
	public static ImageView gloria;
	public static ImageView gatow;

	public static ImageView disabled;
	public static ImageView enabledSuspect;
	public static ImageView enabledRoll;
	public static ImageView enabledPassage;
	public static ImageView suspect;
	public static ImageView passage;
	public static ImageView roll;
	public static ImageView suspectNOW;
	public static ImageView suspectLATER;
	
	public static void initImages(){
	bg = new ImageView(new Image("media/Field_0.png"));
	zug = new ImageView(new Image("media/ZugFensterResized3.png"));
	
	weiss = new ImageView(new Image("media/gameObjects/weiss_n.png"));
	green = new ImageView(new Image("media/gameObjects/green_n.png"));
	porz = new ImageView(new Image("media/gameObjects/porz_o.png"));
	bloom = new ImageView(new Image("media/gameObjects/bloom_o.png"));
	gloria = new ImageView(new Image("media/gameObjects/gloria_s.png"));
	gatow = new ImageView(new Image("media/gameObjects/gatow_w.png"));
	
	disabled = new ImageView(new Image("media/buttons/disabled.png"));
	enabledSuspect = new ImageView(new Image("media/buttons/enabled.png"));
	enabledRoll = new ImageView(new Image("media/buttons/enabled.png"));
	enabledPassage = new ImageView(new Image("media/buttons/enabled.png"));
	suspect = new ImageView(new Image("media/buttons/suspect.png"));
	roll = new ImageView(new Image("media/buttons/roll.png"));
	passage = new ImageView(new Image("media/buttons/passage.png"));
	
	suspectNOW = new ImageView(new Image("media/buttons/suspectNOW.png"));
	suspectLATER = new ImageView(new Image("media/buttons/suspectLATER.png"));
	}
}
