import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Player implements Runnable{

    // all the private variables used by the player
    private final int playerId;
    private final ArrayList<Card> hand;
    private static final AtomicInteger winner = new AtomicInteger(0);
    private static final Object gameLock = new Object();

    private final Deck drawDeck;
    private final Deck discardDeck;
    
    // player class that creates a new player at the start of each round
    public Player(int playerId, Deck drawDeck, Deck discardDeck) {

        this.playerId = playerId;
        this.drawDeck = drawDeck;
        this.discardDeck = discardDeck;

        this.hand = new ArrayList<>();
    }

    public static void resetGame() {
        winner.set(0);
    }

    public static void declareWinner(int playerId) {
        winner.compareAndSet(0, playerId);
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

        // Find the oldest non-preferred card
        for (Card card : hand) {
    
            if (card.getDenomination()
                    != getPreferredDenomination()) {
    
                return card;
            }
        }
    
        // If every card is preferred, discard the last card
        return hand.get(hand.size() - 1);
    }

    private void writeToFile(String message) {

        try (PrintWriter writer = new PrintWriter(
                new FileWriter(
                    "player" + playerId + "_output.txt",
                    true
                ))) {
    
            writer.println(message);
    
        } catch (IOException e) {
    
            System.out.println(
                "Error writing to player " + playerId + " output file."
            );
        }
    }
    
    // take turn method discards and takes a card from each of the decks on either side of the player to complete the players current hand
   public boolean takeTurn() {

    synchronized (gameLock) {

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
    
                // Stop if another player has won
                if (winner.get() != 0) {
                    return false;
                }
    
                // Draw a card
                Card drawnCard = drawDeck.drawCard();
    
                if (drawnCard == null) {
                    return false;
                }
    
                hand.add(drawnCard);
    
                // Record the drawn card
                String drawMessage =
                    "player " + playerId +
                    " draws a " + drawnCard.getDenomination() +
                    " from deck " + drawDeck.getDeckId();
    
                System.out.println(drawMessage);
                writeToFile(drawMessage);
    
                // Select a card to discard
                Card discard = selectCardToDiscard();
    
                if (discard != null) {
    
                    hand.remove(discard);
                    discardDeck.addCard(discard);
    
                    // Record the discarded card
                    String discardMessage =
                        "player " + playerId +
                        " discards a " + discard.getDenomination() +
                        " to deck " + discardDeck.getDeckId();
    
                    System.out.println(discardMessage);
                    writeToFile(discardMessage);
                }
    
                // Record the player's current hand
               String handMessage =
                    "player " + playerId +
                    " current hand is " + handToString();
                
                System.out.println(handMessage);
                writeToFile(handMessage);
                
                // Check for victory before another player takes a turn
                if (hasWon() && winner.compareAndSet(0, playerId)) {
                
                    String winMessage =
                        "player " + playerId + " wins";
                
                    System.out.println(winMessage);
                    writeToFile(winMessage);
                } 
                return true;
            }
        }
    } // end of gamelock
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

    public void writeInitialWinResult(int winningPlayerId) {

        // Create a new output file
        try (PrintWriter writer = new PrintWriter(
                new FileWriter(
                    "player" + playerId + "_output.txt"
                ))) {
    
            writer.println(
                "player " + playerId +
                " initial hand " + handToString()
            );
    
            if (playerId == winningPlayerId) {
    
                writer.println(
                    "player " + playerId + " wins"
                );
    
            } else {
    
                writer.println(
                    "player " + winningPlayerId +
                    " has informed player " + playerId +
                    " that player " + winningPlayerId + " has won"
                );
            }
    
            writer.println(
                "player " + playerId + " exits"
            );
    
            writer.println(
                "player " + playerId +
                " final hand: " + handToString()
            );
    
        } catch (IOException e) {
    
            System.out.println(
                "Error creating player " + playerId + " output file."
            );
        }
    }

    // running the class with override
    @Override
    public void run() {
    
        // Create output file and record initial hand
        try (PrintWriter writer = new PrintWriter(
                new FileWriter(
                    "player" + playerId + "_output.txt"
                ))) {
    
            writer.println(
                "player " + playerId +
                " initial hand " + handToString()
            );
    
        } catch (IOException e) {
            System.out.println("Error creating player output file.");
        }
    
        // Check for an immediate win
        if (hasWon()) {
    
            if (winner.compareAndSet(0, playerId)) {
    
                System.out.println(
                    "player " + playerId + " wins"
                );
    
                writeToFile(
                    "player " + playerId + " wins"
                );
            }
    
        } else {
    
            // Normal gameplay
            while (winner.get() == 0) {

                boolean completedTurn = takeTurn();
            
                if (!completedTurn) {
            
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
    
        // Record the end of the game
        int winningPlayer = winner.get();
    
        if (winningPlayer != playerId && winningPlayer != 0) {
    
            writeToFile(
                "player " + winningPlayer +
                " has informed player " + playerId +
                " that player " + winningPlayer + " has won"
            );
        }
    
        writeToFile(
            "player " + playerId + " exits"
        );
    
        writeToFile(
            "player " + playerId +
            " final hand: " + handToString()
        );
    }
}
