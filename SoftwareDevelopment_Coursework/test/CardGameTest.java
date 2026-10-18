import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class CardGameTest {

    @Test
    public void testValidPack() {
        ArrayList<Card> pack = CardGame.loadPack("validPack.txt, 4);
        assertNotNull(pack);
        assertEquals(32, pack.size());
    }

    @Test
    //Testing with a pack of wrong number of cards
    public void testInvalidPack1() {
        ArrayList<Card> pack = CardGame.loadPack("wrongCount.txt", 4);
        assertNull(pack);
    }

    @Test
    //Test pack with negative number
    public void testInvalidPack2() {
        ArrayList<Card> pack = CardGame.loadPack("negativeValue.txt", 4);
        assertNull(pack);
    }
    

        
    
    
}
