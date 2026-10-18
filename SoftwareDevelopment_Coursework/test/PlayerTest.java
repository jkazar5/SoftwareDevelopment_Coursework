import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PlayerTest {

    @Test
    public void testAddCardToHand() {
        Player p = new Player(1, new Deck(1), new Deck(2));

        p.addCardToHand(new Card(1));
        p.addCardToHand(new Card(2));
        p.addCardToHand(new Card(3));
        p.addCardToHand(new Card(4));

        assertEquals(4, p.getHand().size());
    }

    

}
