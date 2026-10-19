import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Player implements Runnable{

    // all the private variables used by the player
    private final int playerId;
    private final ArrayList<Card> hand;
    private static final AtomicInteger winner = new AtomicInteger(0);

    private final Deck drawDeck;
    private final Deck discardDeck;

    private final Random random;
    
    // player class that creates a new player at the start of each round
    public Player(int playerId, Deck drawDeck, Deck discardDeck) {

        this.playerId = playerId;
        this.drawDeck = drawDeck;
        this.discardDeck = discardDeck;

        this.hand = new ArrayList<>();
        this.random = new Random();
    }

    // getting methods
    public int getPlayerId() {
        return playerId;
    }

    public int getPreferredDenomination() {
        return playerId;
    }

    // exception used so that handsize cant be above 4
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

    // method that is used to figure out if any of the players have won yet
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

    // Method used to figure out the best card for the player to discard in that round
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

    // take turn method discards and takes a card from each of the decks on either side of the player to complete the players current hand
    public void takeTurn() {

        Deck firstDeck;
        Deck secondDeck;
    
        // Always lock decks in ID order
        if (drawDeck.getDeckId() < discardDeck.getDeckId()) {
            firstDeck = drawDeck;
            secondDeck = discardDeck;
        } else {
            firstDeck = discardDeck;
            secondDeck = drawDeck;
        }
    
        synchronized (firstDeck) {
    
            synchronized (secondDeck) {
    
                Card drawnCard = drawDeck.drawCard();
    
                if (drawnCard == null) {
                    return;
                }
    
                hand.add(drawnCard);
    
                Card discard = selectCardToDiscard();
    
                if (discard != null) {
                    hand.remove(discard);
                    discardDeck.addCard(discard);
                }
            }
        }
    }

    // converting the hand to a string to make it easier for the code to deal with than the integers
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

    // running the class with override
    @Override
    public void run() {
    
        // Check starting hand
        if (hasWon()) {
    
            if (winner.compareAndSet(0, playerId)) {
                System.out.println(
                        "player " + playerId + " wins"
                );
            }
    
            return;
        }
    
    
        // Normal gameplay
        while (winner.get() == 0) {
    
            takeTurn();
    
            if (hasWon()) {
    
                if (winner.compareAndSet(0, playerId)) {
    
                    System.out.println(
                            "player " + playerId + " wins"
                    );
                }
    
                break;
            }
        }
    }
}
