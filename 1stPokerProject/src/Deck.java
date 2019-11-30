import java.util.*;
public class Deck {
	private ArrayList<Card> deck;
	/*
	 * Spades, Hearts, Clubs, Diamonds
	 */
	Deck(){
		deck = new ArrayList<Card>();
		
		for(int i = 0; i < 13; i++) {
			deck.set(i, new Card(Card.Suit.SPADES, i + 1));
		}
		for(int i = 0; i < 13; i++) {
			deck.set(i, new Card(Card.Suit.SPADES, i + 1));
		}
		for(int i = 0; i < 13; i++) {
			deck.set(i, new Card(Card.Suit.SPADES, i + 1));
		}
		for(int i = 0; i < 13; i++) {
			deck.set(i, new Card(Card.Suit.SPADES, i + 1));
		}
	}
	/*
	 * Returns the Card at given index. 
	 */
	public Card getCard(int index) {
		return deck.get(index);
	}
	/* Shuffles the cards using a random generator */
	public void shuffle() {
		Random random = new Random();
		ArrayList<Card> shuffledDeck = new ArrayList<Card>(deck);
		for(int i = 0; i<deck.size(); i++) {
			//i dictates the position of the card to be shuffled. 
			//Random integer bound by remaining cards in the deck to be shuffled.
			int index = i + random.nextInt(deck.size()-i);
			Card temp = deck.get(index);
			//Swap index and i. 
			shuffledDeck.set(index, deck.get(i));
			shuffledDeck.set(i, temp);
			
			deck = shuffledDeck;
		}
	}
	/* Deals one card (the top card) in the deck */
	public Card deal() {
		Card dealtCard = deck.remove(0);
		return dealtCard;
	}
	
	/* Size of cards */
	public int deckSize() {
		return deck.size();
	}
}
