package vielCoolererPathfinder;

public class Kombinationen {
	

	public static void possibleStrings(int maxLength, char[] alphabet, String curr) {
		
		WahnsinnigTollerPathfinder pathfinder = new WahnsinnigTollerPathfinder();

        // If the current string has reached it's maximum length
        if(curr.length() == maxLength) {
            System.out.println(curr);
            pathfinder.setJetzigeReihe(0);
            pathfinder.setJetzigeSpalte(0);

        // Else add each letter from the alphabet to new strings and process these new strings again
        } else {
            for(int i = 0; i < alphabet.length; i++) {
                String oldCurr = curr;
                curr += alphabet[i];
                if (pathfinder.detectHimmelsrichtung(alphabet[i])){
                	possibleStrings(maxLength,alphabet,curr);
                    
                }              
                	curr = oldCurr;
                }
        }
	}
}
