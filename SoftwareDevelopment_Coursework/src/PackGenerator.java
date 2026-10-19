
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class PackGenerator {

    public static void generatePack(int numberOfPlayers) {

        ArrayList<Integer> cards = new ArrayList<>();

        // Add 8 cards for each player denomination
        for (int i = 1; i <= numberOfPlayers; i++) {
            for (int j = 0; j < 8; j++) {
                cards.add(i);
            }
        }

        // Shuffle the cards
        Collections.shuffle(cards);

        // Save the pack to a text file
        try (FileWriter writer = new FileWriter("Pack.txt")) {

            for (int card : cards) {
                writer.write(card + System.lineSeparator());
            }

            System.out.println("Pack.txt generated successfully.");
            System.out.println("Total cards: " + cards.size());

        } catch (IOException e) {
            System.out.println(
                "Error generating pack: " + e.getMessage()
            );
        }
    }
}
