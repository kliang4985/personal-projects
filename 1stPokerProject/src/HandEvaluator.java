import java.util.*;
public class HandEvaluator {
	/* Class that defines how to evaluate a poker hand based on 
	 * 5 card Texas Hold-em Rules. While 5 cards will ultimately be evaluated
	 * there will be 7 cards in total to consider. (5 cards on the board and 
	 * 2 cards dealt to the player.
	 * When the game is played the function setGame will set up the game with
	 * the correct number of players and create a new deck.
	 * The deal method can then be called to deal out the cards. 
	 * Finally after dealing the cards the playGame method will be called to 
	 * evaluate who won. 
	 */
	private static final int HIGHCARD = 0; 
	private static final int PAIR = 1;
	private static final int TWOPAIR= 2;
	private static final int SET = 3;
	private static final int STRAIGHT = 4;
	private static final int FLUSH = 5;
	private static final int QUADS = 6;
	private static final int STRAIGHTFLUSH = 7;
	private static final int ROYALFLUSH = 8;
	
	/* ArrayList to combine the players hand with the game board */
	ArrayList<Card> cards;
	
	/*These are variables that are used in a game */
	
	private final int MAX_PLAYERS = 8; 
	// The game board, 5 cards will be dealt in the game board */
	ArrayList<Card> board; 
	// Deck for the game, standard 52 cards no Joker
	Deck gameDeck;
	// ArrayList for the players
	ArrayList<Player> players; 
	int numPlayers; 
	/*Inner class that defines a player within the game */
	class Player {
		public String name;
		public ArrayList<Card> hand;
		public int handStrength;
		public Player(String name) {
			this.name = name;
			hand = new ArrayList<Card>();
		}
		
		public ArrayList<Card> recieveCards() { 
			hand.add(gameDeck.deal());
			hand.add(gameDeck.deal());
			return hand;
		}
		public void reset() {
			hand = new ArrayList<Card>();
		}
		public void evaluateHand() {
			handStrength = evaluate(this);
		}
		public int compareTo (Player other) {
			return this.handStrength - other.handStrength;
		}
	}
	/*Sequence of calls to run the game will be setGame, deal, then playGame */
	
	public void setGame(int numPlayers) {
		if (numPlayers > MAX_PLAYERS || numPlayers < 1) {
			System.out.println("Too many players. Please choose a number 1-8.");
		}
		//Set up gameDeck
		Scanner scanner = new Scanner(System.in);
		this.gameDeck = new Deck();
		gameDeck.shuffle();
		//Set up players and empty board
		this.numPlayers = numPlayers;
		board = new ArrayList<Card>();
		players = new ArrayList<Player>();
		for (int i = 0; i < numPlayers; i++) {
			System.out.println("Please enter player name: ");
			String name = scanner.nextLine();
			players.add(new Player(name));
		}
		scanner.close();
	}
	
	public void deal() {
		/*Each player will receive 2 cards */
		for(Player p : players) {
			p.recieveCards();
		}
		/*Deals the cards onto the board, 5 cards */
		for(int i = 0; i < 5; i++) {
			board.add(gameDeck.deal());
		}
	}
	
	public void playGame(ArrayList<Player>players) {
		String winner = players.get(0).name;
		for (int i = 0; i < numPlayers; i ++) {
			Player curr = players.get(i);
			Player next = players.get(i+1);
			if (curr.compareTo(next) < 0) {
				winner = next.name;
			} else {
				winner = curr.name; 
			}
		}
		System.out.println("Winner of this round is: " + winner);
		System.out.println("Next round!");
	}	
	/*This method will be called when the game is ran in order to evaluate the hand 
	 * that the player receives
	 */
	
	public int evaluate (Player p) {
		/* Adding players hand and the cards from the game board */
		
		for (Card c: p.hand) {
			cards.add(c);
		}
		for (Card c: board) {
			cards.add(c);
		}
		/*Return the value based on the card evaluated. Will check in
		 * order to the strength of the hand starting from the 
		 * strongest hand in order to avoid overlapping between certain 
		 * hands. (Ex. Three pair includes two pair but three pair will be 
		 * checked first)*/
		if (hasRoyalFlush(cards)) 
			return ROYALFLUSH;
		 else if (hasStraightFlush(cards)) 
			return STRAIGHTFLUSH;
		 else if (hasQuads(cards)) 
			return QUADS;
		 else if (hasFlush(cards)) 
			return FLUSH;
		 else if (hasStraight(cards)) 
			return STRAIGHT;
		 else if (hasSet(cards)) 
			return SET;
		 else if (hasTwoP(cards)) 
			return TWOPAIR;
		 else if (hasPair(cards)) 
			return PAIR;
		 else 
			return HIGHCARD;
		
	}
	/* Checks if player has a pair of cards */
	public boolean hasPair(ArrayList<Card>cards) {
		Collections.sort(cards);
		for(int i = 0; i < cards.size() - 1; i++) {
			for(int j = i+1; j < cards.size(); j++) {
				if(cards.get(i).getValue() == cards.get(j).getValue()) {
					return true;
				}
			}
		}
		return false;
	
	}
	/*Checks for two distinct pairs */
	public boolean hasTwoP(ArrayList<Card> cards) {
		Collections.sort(cards);
		boolean retVal = false;
		int numPairs = 0;
		int counter;// counts how many of those cards are in the hand
		int pairVal = 0;
		for (int i = 0; i  <cards.size()-1; i++) {
			counter = 1;
			for (int j = i+1; j < cards.size(); j++) {
				if(cards.get(i).getValue() == cards.get(j).getValue() &&
						cards.get(i).getValue() != pairVal){
					counter++;
					pairVal = cards.get(j).getValue();
					if(counter > 1) {
						numPairs++;
						if (numPairs == 2) {
							retVal = true;
						}
					}
					break;
				}
			}
		}
		return retVal;
	}
	/*Checks for three of the same card */
	public boolean hasSet(ArrayList<Card> cards) {
		Collections.sort(cards);
		/*Once the cards are sorted, then the third card can be
		 * used to compare with others
		 */
		int compare = cards.get(2).getValue();
		int counter = 0;
		for(int i = 0; i < 5 ; i++) {
			if(cards.get(i).getValue() == compare) {
				counter++;
			}
		}
		return (counter == 3);
	}
	/* Checks for cards in increasing order */
	public boolean hasStraight(ArrayList<Card>cards) {
		Collections.sort(cards);
		boolean straight = false;
		if(cards.get(0).getValue() != 1) { //if the first card of sorted 5 cards is not Ace then need to check
			for (int i = 0; i < 4; i++) { //if sorted 5 hands goes up by one value each time. 
				if(cards.get(i).getValue() != cards.get(i+1).getValue()-1) {
					straight = false;
				}
			}
		} else { //Other wise check if it is Ace 10 j q k. 
			if(cards.get(0).getValue() == 1 && cards.get(1).getValue() == 10
					&& cards.get(2).getValue() == 11 && cards.get(3).getValue() == 12
					&& cards.get(4).getValue() == 13) {
				return true; 
			} else {
				return false;
			}
		}
	return straight;	
	}
	/*Checks for 5 of the same suit */
	public boolean hasFlush(ArrayList<Card>cards) {
		Collections.sort(cards);
		for(int i = 0; i < 4 ; i++) {
			if(cards.get(i).getSuit() != cards.get(i+1).getSuit()) {
				return false; 
			}
		}
		return true; 
	}
	/*Checks for four of a kind of a particular card */
	public boolean hasQuads(ArrayList<Card>cards) {
		Collections.sort(cards);
		int compare = cards.get(2).getValue();
		int counter = 0;
		for(int i = 0; i < 5 ; i++) {
			if(cards.get(i).getValue() == compare) {
				counter++;
			}
		}
		return (counter == 4);
		
		
	}
	/*Checks for a Straight Flush: same suit and any increasing order */
	public boolean hasStraightFlush(ArrayList<Card>cards) {
		if(hasFlush(cards) && hasStraight(cards)) {
			return true;
		} 
		return false;
			
	}
	/*Checks for a Royal Flush: same suit from 10, J, Q, K and A */
	public boolean hasRoyalFlush(ArrayList<Card>cards) {
		Collections.sort(cards);
		if(hasStraightFlush(cards) && hasAce(cards) && faceCard(cards)) {
			return true;
		}
		return false;

	}
	/*Check if the cards contains an Ace */
	private boolean hasAce(ArrayList<Card> cards) {
		for(Card card: cards) {
			if(card.getValue() == 1) {
				return true;
			}
		}
		return false;
	}
	/*Private method used to check if the arraylist has face cards. Face cards are:
	 * Jack Queen King and Ace by edfinition. 
	 */
	private boolean faceCard(ArrayList<Card> cards) {
		for (Card card: cards) {
			if (card.getValue() >= 11 && card.getValue() <= 13) 
				return true;
		}
		return false;
	}
	
}
