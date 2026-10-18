import java.util.LinkedList;
import java.util.Queue;

public class Deck {

    private final int deckId;
    private final Queue<Card> cards;

    public Deck(int deckId) {
        this.deckId = deckId;
        this.cards = new LinkedList<>();
    }

    public synchronized void addCard(Card card) {
        cards.add(card);
    }

    public synchronized Card drawCard() {
        return cards.poll();
    }

    public int getDeckId() {
        return deckId;
    }

    public synchronized int getSize() {
        return cards.size();
    }

    public synchronized boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public synchronized String toString() {
        StringBuilder result = new StringBuilder();

        for (Card card : cards) {
            result.append(card.getDenomination()).append(" ");
        }

        return result.toString().trim();
    }
}
