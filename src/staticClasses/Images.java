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
	
	weiss = new ImageView(new Image("media/gameObjects/weiss_n.png"));
	green = new ImageView(new Image("media/gameObjects/green_n.png"));
	porz = new ImageView(new Image("media/gameObjects/porz_o.png"));
	bloom = new ImageView(new Image("media/gameObjects/bloom_o.png"));
	gloria = new ImageView(new Image("media/gameObjects/gloria_s.png"));
	gatow = new ImageView(new Image("media/gameObjects/gatow_w.png"));
	
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
