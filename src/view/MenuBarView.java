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
	
	private Menu options;
	private Menu help;
	private MenuItem sounds;
	private MenuItem main;
	private MenuItem quit;
	private MenuItem hints;
	
	public MenuBarView(){
		
		
//	this.setPrefWidth(1400);
//	this.setMaxWidth(1400);
	options = new Menu("Options");
	help = new Menu("Help");
	sounds = new MenuItem("Sound");
	main = new MenuItem("Main Menu");
	quit = new MenuItem("Exit Game");
	hints = new MenuItem("Game Rules");
	options.getItems().addAll(sounds, main, quit);
	help.getItems().add(hints);
	this.getMenus().addAll(options, help);
	
	}

	public Menu getOptions() {
		return options;
	}

	public void setOptions(Menu options) {
		this.options = options;
	}

	public Menu getHelp() {
		return help;
	}

	public void setHelp(Menu help) {
		this.help = help;
	}

	public MenuItem getSounds() {
		return sounds;
	}

	public void setSounds(MenuItem sounds) {
		this.sounds = sounds;
	}

	public MenuItem getMain() {
		return main;
	}

	public void setMain(MenuItem main) {
		this.main = main;
	}

	public MenuItem getQuit() {
		return quit;
	}

	public void setQuit(MenuItem quit) {
		this.quit = quit;
	}

	public MenuItem getHints() {
		return hints;
	}

	public void setHints(MenuItem hints) {
		this.hints = hints;
	}

}
