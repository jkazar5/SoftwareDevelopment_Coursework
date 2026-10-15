@Test

public static void main(String[] args) {
    // Check if card can store numbers
    Card test1 = new Card(5);
    if (test1.getDenomination() == 5) {
        System.out.println("Card stores numbers")
    }
    else {
        System.out.println("Card cannot store numbers") 
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
