import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Player implements Runnable{

    private final int playerId;
    private final ArrayList<Card> hand;
    private static final AtomicInteger winner = new AtomicInteger(0);

    private final Deck drawDeck;
    private final Deck discardDeck;

    private final Random random;

    public Player(int playerId, Deck drawDeck, Deck discardDeck) {

        this.playerId = playerId;
        this.drawDeck = drawDeck;
        this.discardDeck = discardDeck;

        this.hand = new ArrayList<>();
        this.random = new Random();
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getPreferredDenomination() {
        return playerId;
    }

    public void addCardToHand(Card card) {

        if (hand.size() >= 4) {
            throw new IllegalStateException(
                    "Player already has 4 cards");
        }

        hand.add(card);
    }

    public ArrayList<Card> getHand() {
        return new ArrayList<>(hand);
    }

    public boolean hasWon() {

        if (hand.size() != 4) {
            return false;
        }

        int firstDenomination =
                hand.get(0).getDenomination();

        for (Card card : hand) {

            if (card.getDenomination()
                    != firstDenomination) {

                return false;
            }
        }

        return true;
    }

    public Card selectCardToDiscard() {

        ArrayList<Card> possibleCards =
                new ArrayList<>();

        for (Card card : hand) {

            if (card.getDenomination()
                    != getPreferredDenomination()) {

                possibleCards.add(card);
            }
        }

        if (possibleCards.isEmpty()) {
            return null;
        }

        int randomIndex =
                random.nextInt(possibleCards.size());

        return possibleCards.get(randomIndex);
    }

    public void takeTurn() {

        Card drawnCard = drawDeck.drawCard();

        if (drawnCard == null) {
            return;
        }

        // Temporarily have 5 cards
        hand.add(drawnCard);

        Card discard =
                selectCardToDiscard();

        if (discard != null) {

            hand.remove(discard);

            discardDeck.addCard(discard);
        }
    }

    public String handToString() {

        StringBuilder result =
                new StringBuilder();

        for (Card card : hand) {

            result.append(
                    card.getDenomination()
            ).append(" ");
        }

        return result.toString().trim();
    }
    @Override
    public void run() {
    
        while (!hasWon()) {
    
            takeTurn();
    
        }
    
        System.out.println(
                "player " + playerId + " wins"
        );
    }
}
