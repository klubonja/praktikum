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
	
	public static ImageView YESanklageImage;
	public static ImageView NOanklageImage;
	public static ImageView YESgangImage;
	public static ImageView NOgangImage;
	public static ImageView YESwurfelImage;
	public static ImageView NOwurfelImage;
	public static ImageView OFFanklage;
	public static ImageView ONanklage;
	
	public static void initImages(){
	bg = new ImageView(new Image("media/Field_0.png"));
	zug = new ImageView(new Image("media/ZugFensterResized3.png"));
	
	weiss = new ImageView(new Image("media/gameObjects/weiss_n.png"));
	green = new ImageView(new Image("media/gameObjects/green_n.png"));
	porz = new ImageView(new Image("media/gameObjects/porz_o.png"));
	bloom = new ImageView(new Image("media/gameObjects/bloom_o.png"));
	gloria = new ImageView(new Image("media/gameObjects/gloria_s.png"));
	gatow = new ImageView(new Image("media/gameObjects/gatow_w.png"));
	
	YESanklageImage = new ImageView(new Image("media/YESanklage.png"));
	NOanklageImage = new ImageView(new Image("media/NOanklage.png"));
	YESgangImage = new ImageView(new Image("media/YESgang.png"));
	NOgangImage = new ImageView(new Image("media/NOgang.png"));
	YESwurfelImage = new ImageView(new Image("media/YESwurfeln.png"));
	NOwurfelImage = new ImageView(new Image("media/NOwurfeln.png"));
	OFFanklage = new ImageView(new Image("media/OFFanklage.png"));
	ONanklage = new ImageView(new Image("media/ONanklage.png"));
	}
}
