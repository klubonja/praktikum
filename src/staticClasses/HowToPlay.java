package staticClasses;

public class HowToPlay {
	

	public static final String GOAL = "GOAL: \nTo correctly name the murderer, murder weapon, and murder location. \n \n";
	
	
	public static final String PLAY = "PLAY: \nMiss Scarlett takes the first turn, and turns continue clockwise around the table. "
    		+ "On your turn, roll the die, and move your playing piece that many spaces on the yellow squares. "
    		+ "You can move only horizontally or vertically, never diagonally, and can't enter a space or doorway you have already entered this turn. "
    		+ "You can move through a doorway to enter a room, but this ends your movement. "
    		+ "You can't move through a yellow space occupied by another player, but multiple players can be in the same room. "
    		+ "If you start your turn in a room with a secret passage, you can use the secret passage instead of rolling the die. "
    		+ "This will put your character in another room across the board, ending your movement. \n \n"; 

	
	public static final String SUGGESTION = "SUGGESTION: \nIf you end your movement in a room, you get to make a suggestion. "
			+ "To do this, name a suspect, a murder weapon, and the room you just entered. "
			+ "For example, if you just entered the lounge, you might say, I suggest the crime was committed by Colonel Mustard, in the lounge, with a dagger. "
			+ "The named suspect and murder weapon are both moved into your current room. "
			+ "The player to your left must, if able, disprove your suggestion by showing you one card from her hand that matches your suggestion. "
			+ "If that player can't do so, the player to her left must disprove your suggestion by showing you one card from his hand. "
			+ "This responsibility passes clockwise until someone shows you a card, or until all players have passed. "
			+ "If someone shows you a card, you should cross it off on your detective notebook as a possibility. "
			+ "Any cards you hold should also be crossed off as possibilities. "
			+ "Don't let other players see your notebook. \n\n";
	
	
	public static final String ACCUSING = "ACCUSING: \nIf you think you have solved the case by eliminating all the false possibilities, " 
			+ "and have not just had your suggestion disproved this turn, you can end your turn by making an accusation. "
			+ "Announce that you are making an accusation, and state your final guess of the murderer, the murder weapon, and the murder location. "
			+ "Once this is done, secretly look at the three cards in the murder envelope. "
			+ "If you are correct, lay the cards face-up on the table, proving to all players that you have won the game. "
			+ "If you are wrong, you lose the game! Secretly replace the three cards back in the murder envelope without revealing them. "
			+ "Your turn is over, and you are now eliminated from the game. "
			+ "You no longer take any turns, but must stay at the table to disprove the suggestions of others. "
			+ "If your piece is blocking a doorway, it is moved into the room. \n";
			
}
