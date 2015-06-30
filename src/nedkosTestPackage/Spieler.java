package nedkosTestPackage;

/**
 * @since 26.05.2015
 * @version 26.05.2015
 * @author Benedikt Mayer
 *
 * Ein Spieler mit Positionen
 */
public class Spieler {

	private int xPosition;
	private int yPosition;
	public Spieler (int xPosition, int yPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	
	
}
