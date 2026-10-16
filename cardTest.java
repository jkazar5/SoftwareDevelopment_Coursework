import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    //Check if Card can store numbers
    public void test1() {
        Card c1 = new Card(1);
        assertEquals(1, c1.getDenomination());
    }

    @Test(expected = IllegalArgumentException.class)
    //Check if card rejects negative numbers
    public void test2() {
        new Card(-2);
    }

    @Test
    // Testing toString()
    public void test3() {
        Card c3 = new Card(3);
        assertEquals("3", c3.toString());
    }
}
