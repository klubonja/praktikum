package gui1_0;

public class Spieler {

	private int xPosition;
	private int yPosition;
	private int xNochZuLaufen;
	private int yNochZuLaufen;
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

	public int getxNochZuLaufen() {
		return xNochZuLaufen;
	}

	public void setxNochZuLaufen(int xNochZuLaufen) {
		this.xNochZuLaufen = xNochZuLaufen;
	}

	public int getyNochZuLaufen() {
		return yNochZuLaufen;
	}

	public void setyNochZuLaufen(int yNochZuLaufen) {
		this.yNochZuLaufen = yNochZuLaufen;
	}
	
	
	
}
