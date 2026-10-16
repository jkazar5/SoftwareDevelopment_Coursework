import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {

    @Test
    //Check if Card can store numbers
    public void test1() {
        Card c = new Card(1);
        assertEquals(1, c.getDenomination());
    }

    @Test(expected = IllegalArgumentException.class)
    //Check if card rejects negative numbers
    public void test2() {
        new Card(-2);
    }

    // Testing toString()
    Card test3 = new Card(3);
    if (test3.toString().equals("3")) {
        System.out.println("toString works");
    }
    else {
        System.out.println("toString does not work")
            }
        }
