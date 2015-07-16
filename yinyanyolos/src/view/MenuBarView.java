package view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * @since 26.05.2015
 * @version 2.0
 * @author Kristi Lubonja
 * 
 * Menu at the top represented as a MenuBar
 *
 */

public class MenuBarView extends MenuBar{
	
	Menu options;
	Menu help;
	MenuItem sounds;
	
	public MenuBarView(){
		
		
	this.setPrefWidth(1200);
	this.setMaxWidth(1200);
	options = new Menu("Options");
	help = new Menu("Help");
	sounds = new MenuItem("Sound");
	options.getItems().addAll(sounds);
	this.getMenus().addAll(options, help);
	
	}

}
