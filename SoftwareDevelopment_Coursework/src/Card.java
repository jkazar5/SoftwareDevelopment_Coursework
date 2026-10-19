public class Card {

    // private variables for card
    private final int denomination;

    // exception used to check if denomination is negative
    public Card(int denomination) {
        if (denomination < 0) {
            throw new IllegalArgumentException(
                    "Denomination cannot be negative");
        }

        this.denomination = denomination;
    }

    // getting method for each denomination
    public int getDenomination() {
        return denomination;
    }

    // override used to turn the number into a string
    @Override
    public String toString() {
        return Integer.toString(denomination);
    }
}
