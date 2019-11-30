
public class Card implements Comparable<Card> {
	
	/* This class specifies the properties of a Card within the poker game. 
	 * Suits will be Spade, Heart, Diamond, and Clubs. Each card also 
	 * contains a value. Value will range from 1 to 13 to indicate cards 
	 * Ace to King*/
	
	public enum Suit {
		SPADES("s"), HEARTS("h"),  DIAMONDS("d"), CLUBS ("c");
		Suit(String suit) {
			this.suit = suit;
		}
		public String getSuit() {
			return suit;
		}
		private String suit;

	}
	private final Suit cardSuit; 
		
	private final int value;
	/*
	 * Joker will not be used. Corresponding values range from 1 to 13. 
	 */
	private final static String[] valueNames = {"Joker", "A", "2", "3", "4", 
			"5", "6", "7", "8", "9", "10", "J", "Q", "K"};
	
	/*Constructor for creating a card with cardSuit Suit and value of value passed */
	
	public Card(Suit cardSuit, int value) {
		if(value < 1 || value > 13) {
			throw new RuntimeException("Illegal range of card values. Enter 1-13");
		}
		this.cardSuit = cardSuit;
		this.value = value;
	}
	/*Returns the value of the card */
	public int getValue() {
		return value;
	}
	/*Returns the card's suit */
	public Suit getSuit() {
		return cardSuit;
	}
	/*
	 * To string method that gives a string representation of the card. 
	 */
	public String toString() {
		return valueNames[value] + " of " + cardSuit.getSuit();
	}
	
	/* Definition of a card equaling another card. Typically shouldn't be true
	 * when one deck is utilized in a game.
	 */
	public boolean equals(Object other) {
		if(other == this) {
			return true;
		} else if (!(other instanceof Card)) {
			return false;
		} else {
			return this.cardSuit.equals(((Card)other).cardSuit) &&
					this.value == ((Card)other).value;
		}
		
	}
	public int hashcode() {
		return value* 5 + cardSuit.ordinal();
	}
	
	@Override
	public int compareTo (Card other) {
		if (this.value > other.value) {
			return 1; 
		} else if (this.value < other.value) {
			return -1;
		} else {
			//Spades = 0, Hearts = 1; Diamonds = 2 Clubs = 3
			if (this.cardSuit.ordinal() < other.cardSuit.ordinal()) {
				return 1;
			}else if (this.cardSuit.ordinal() > other.cardSuit.ordinal()) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
