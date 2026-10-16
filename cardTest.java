import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void test1() {
        Card c = new Card(1);
        assertEquals(1, c.getDenomination());
    }

    //Check if card rejects negative numbers
    try {
        Card test2 = new Card(-2);
        System.out.println("negative number was accepted");
    }
    catch (IllegalArgumentException e) {
        System.out.println("negative number rejected");
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
