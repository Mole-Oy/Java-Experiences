/**
 * COMPSCI 1027B Assignment 2
 * Counter.java
 * Due: February 27, 2023
 * 
 * @author Matthew Molloy
 * 
 * Calculates the points in a Cribbage hand
 */

public class Counter {
	private PowerSet<Card> cardps;
	private Card starter;
	
	/**
	 * 
	 * @param hand; array of card objects
	 * @param starter; a single card object
	 */
	public Counter(Card[] hand, Card starter) {
		cardps = new PowerSet<Card>(hand);
		this.starter = starter;
	}
	
	
	/**
	 * 
	 * @return The total points scored by the hand through pairs, runs, sums to 15
	 * a Jack matching the starter suit, and flushes
	 */
	public int countPoints() {
		int totalPoints = 0;
		totalPoints += Pairs();
		totalPoints += Runs();
		totalPoints += Fifteen();
		totalPoints += HisKnobs();
		totalPoints += Flush();
		return totalPoints;
	}
	
	/**
	 *
	 * @return 2pts for each pair in the hand
	 */
	private int Pairs() {
		int numPairs = 0; // Tracks number of pairs in set
		int numSets = cardps.getLength();
		// iterate through each set in cardps
		for (int i = 0; i < numSets; i++) {
			Set<Card> CurrentSet = cardps.getSet(i);
			// Set must be 2 elements long to check for pair
			if (CurrentSet.getLength() == 2) {
				// Get each card's label
				String c1Label = CurrentSet.getElement(0).getLabel();
				String c2Label = CurrentSet.getElement(1).getLabel();
				// Do the label's match?
				if (c1Label == c2Label) numPairs++;
			}	 
		}
		return 2 * numPairs;
	}
	
	/**
	 * 
	 * @return pts for each occurrence of the longest run
	 */
	private int Runs() {
		int num3Runs = 0;
		int num4Runs = 0;
		int numSets = cardps.getLength();
		// Iterate through each set
		for (int i = 0; i < numSets; i++) {
			Set<Card> CurrentSet = cardps.getSet(i);
			// Is the set a run?
			if (isRun(CurrentSet)) {
				// Match the length of the set with a case. if run of 5, return 5 pts
				switch(CurrentSet.getLength()) {
				case 3:
					num3Runs++;
					break;
				case 4:
					num4Runs++;
					break;
				case 5:
					return 5;
				}
			}	
		}
		// Run of 4 has higher priority over run of 3
		if (num4Runs > 0) return 4 * num4Runs;
		else return 3 * num3Runs;
	}
	
	/**
	 * 
	 * @param set of card objects
	 * @return true if all cards in set form a run; false if not
	 */
	private boolean isRun (Set<Card> set) { 
			int n = set.getLength();
			
			if (n <= 2) return false; // Run must be at least 3 in length.
			
			int[] rankArr = new int[13];
			for (int i = 0; i < 13; i++) rankArr[i] = 0; // Ensure the default values are all 0.
			
			for (int i = 0; i < n; i++) {
				rankArr[set.getElement(i).getRunRank()-1] += 1;
			}
	
			// Now search in the array for a sequence of n consecutive 1's.
			int streak = 0;
			int maxStreak = 0;
			for (int i = 0; i < 13; i++) {
				if (rankArr[i] == 1) {
					streak++;
					if (streak > maxStreak) maxStreak = streak;
				} else {
					streak = 0;
				}
			}
			if (maxStreak == n) { // Check if this is the maximum streak.
				return true;
			} else {
				return false;
			}
		}
	/**
	 * 
	 * @return pts for each subset that totals to 15 
	 */
	private int Fifteen() {
		int fifteenCounter = 0;
		int numSets = cardps.getLength();
		// Iterate through each subset
		for (int i = 0; i < numSets; i++) {
			Set<Card> CurrentSet = cardps.getSet(i);
			// No single card is worth 15 pts, so exclude all sets with 1 cards
			if(CurrentSet.getLength() >= 2) {
				int labelTotal = 0;
				for (int j = 0; j < CurrentSet.getLength(); j++) {
					labelTotal += CurrentSet.getElement(j).getFifteenRank();
				}
				if (labelTotal == 15) fifteenCounter++; 
			}
		}
		return 2 * fifteenCounter;
	}
	
	/**
	 * 
	 * @return pts for each Jack that matches the suit of the starter card
	 */
	private int HisKnobs() {
		int numSets = cardps.getLength();
		for (int i = 0; i < numSets; i++) {
			Set<Card> CurrentSet = cardps.getSet(i);
			if (CurrentSet.getLength() == 1) {
				Card c = CurrentSet.getElement(0);
				String cLabel = c.getLabel();
				String cSuit = c.getSuit();
				
				// Ensure that the current card is not the starter card
				if (!c.equals(starter)) {
					// The card's label must be J and it's suit must match the starter's
					if (cLabel == "J" && cSuit == starter.getSuit()){
						return 1;
					}
				}				
			}
		}
		return 0;
	}
	
	/**
	 * 
	 * @return 4 pts for a flush in the hand; 5 pts including the starter
	 */
	private int Flush() {
		int numSets = cardps.getLength();
		// Last set in power set will contain all five cards
		Set<Card> fiveElementSet = cardps.getSet(numSets - 1);
		// Get the suit of the starter and the first hand in the set
		String starterSuit = starter.getSuit();
		//getElement(4) takes the second card in the cardArray (Array from test file)
		String handSuit = fiveElementSet.getElement(4).getSuit();
		for (int i = 1; i < 4; i++) {
			// if the other cards in the hand do not have the same suit, immediately return 0 pts
			if (fiveElementSet.getElement(i).getSuit() != handSuit) return 0;
		}
		
		// Return 5 pts if the starter card is part of the flush
		if (handSuit == starterSuit) return 5;
		else return 4;
	}

}



	
