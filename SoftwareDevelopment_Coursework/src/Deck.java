import java.util.LinkedList;
import java.util.Queue;

public class Deck {

    // private deck variables
    private final int deckId;
    private final Queue<Card> cards;

    // class creating a new deck object
    public Deck(int deckId) {
        this.deckId = deckId;
        this.cards = new LinkedList<>();
    }

    // methods used to add or remove cards from the deck object
    public synchronized void addCard(Card card) {
        cards.add(card);
    }

    public synchronized Card drawCard() {
        return cards.poll();
    }

    // getting methods used by the deck
    public int getDeckId() {
        return deckId;
    }

    public synchronized int getSize() {
        return cards.size();
    }

    // used to check if the deck is empty
    public synchronized boolean isEmpty() {
        return cards.isEmpty();
    }

    // overide method to change the deck to a string
    @Override
    public synchronized String toString() {
        StringBuilder result = new StringBuilder();

        for (Card card : cards) {
            result.append(card.getDenomination()).append(" ");
        }

        return result.toString().trim();
    }
}
