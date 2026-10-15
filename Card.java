public class Card {

    private final int denomination;

    public Card(int denomination) {
        if (denomination < 0) {
            throw new IllegalArgumentException(
                    "Denomination cannot be negative");
        }

        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }

    @Override
    public String toString() {
        return Integer.toString(denomination);
    }
}
