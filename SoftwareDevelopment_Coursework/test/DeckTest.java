import org.junit.Test;

import static org.junit.Assert.assertEquals;



public class DeckTest {

    @Test
    // testing drawCard and addCard
    public void drawTest() {
        Deck d = new Deck(1);

        //check if successfully adds cards to the deck
        d.addCard(new Card(1));
        d.addCard(new Card(2));


        //check if draw returns top card of the deck
        Card first = d.drawCard();
        assertEquals(1, first.getDenomination());

        //check if first card has been removed from deck
        assertEquals(1, d.getSize());

    }

    @Test
    //check if deck stores id
    public void testDeckid() {
        Deck d = new Deck(2);
        assertEquals(2, d.getDeckId());
    }

    @Test
    // check getSize()
    public void testSize() {
        Deck d = new Deck(1);

        assertEquals(0, d.getSize());

        d.addCard(new Card(1));
        assertEquals(1, d.getSize());
    }

 
    
}
