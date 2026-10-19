import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PlayerTest {

    @Test
    //checking initial hand of the player
    public void testAddCardToHand() {
        Player player = new Player(1, new Deck(1), new Deck(2));

        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(2));
        player.addCardToHand(new Card(3));
        player.addCardToHand(new Card(4));

        assertEquals(4, player.getHand().size());
    }

    @Test
    //Checking if player hand maintains 4 cards after each action

    public void testMaintainsFourCards() {
        
        Deck drawDeck = new Deck(1);
        Deck discardDeck = new Deck(2);

        drawDeck.addCard(new Card(5)); // add a card to be drawn

        Player player = new Player(1, drawDeck, discardDeck);
        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(2));
        player.addCardToHand(new Card(3));
        player.addCardToHand(new card(4));
        // adding initial player hand

        player.takeTurn();

        assertEquals(4, player.getHand().size());
        // check if hand still has 4 cards
        

        

        
        
        
            
    

    

}
