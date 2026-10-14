import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class PackGenerator {

    public static void generatePack(int numberOfPlayers, String filename) {

        Random random = new Random();

        try {
            FileWriter writer = new FileWriter(filename);

            for (int i = 0; i < 8 * numberOfPlayers; i++) {

                int denomination =
                        random.nextInt(numberOfPlayers) + 1;

                writer.write(
                        denomination + System.lineSeparator()
                );
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Could not create pack file.");
        }
    }
    public static void main(String[] args) {
        generatePack(4, "pack.txt");
        System.out.println("pack.txt created.");
    }
}
