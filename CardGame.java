import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CardGame {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int numberOfPlayers =
                getNumberOfPlayers(input);

        ArrayList<Card> pack = null;

        while (pack == null) {

            System.out.println(
                    "Please enter location of pack to load:"
            );

            String filename =
                    input.nextLine();

            pack = loadPack(
                    filename,
                    numberOfPlayers
            );

            if (pack == null) {

                System.out.println(
                        "Invalid pack. Please try again."
                );
            }
        }

        // CREATE DECKS

        ArrayList<Deck> decks =
                new ArrayList<>();

        for (int i = 1;
             i <= numberOfPlayers;
             i++) {

            decks.add(new Deck(i));
        }

        // CREATE PLAYERS

        ArrayList<Player> players =
                new ArrayList<>();

        for (int i = 0;
             i < numberOfPlayers;
             i++) {

            Deck drawDeck =
                    decks.get(i);

            Deck discardDeck =
                    decks.get(
                            (i + 1)
                            % numberOfPlayers
                    );

            Player player =
                    new Player(
                            i + 1,
                            drawDeck,
                            discardDeck
                    );

            players.add(player);
        }

        // DEAL PLAYER HANDS

        int packPosition = 0;

        for (int round = 0;
             round < 4;
             round++) {

            for (int player = 0;
                 player < numberOfPlayers;
                 player++) {

                Card card =
                        pack.get(packPosition);

                players.get(player)
                        .addCardToHand(card);

                packPosition++;
            }
        }

        // DEAL REMAINING CARDS
        // TO DECKS

        int deckNumber = 0;

        while (packPosition < pack.size()) {

            decks.get(deckNumber)
                    .addCard(
                            pack.get(packPosition)
                    );

            packPosition++;

            deckNumber =
                    (deckNumber + 1)
                    % numberOfPlayers;
        }

        // CHECK FOR INITIAL WINNER

        Player winner = null;

        for (Player player : players) {

            if (player.hasWon()) {

                winner = player;
                break;
            }
        }

        if (winner != null) {

            System.out.println(
                    "player "
                    + winner.getPlayerId()
                    + " wins"
            );

            ArrayList<Thread> threads = new ArrayList<>();

            for (Player player : players) {
            
                Thread thread = new Thread(player);
            
                threads.add(thread);
            
                thread.start();
            }

            input.close();
            return;
        }

        // DISPLAY TEST INFORMATION

        System.out.println();
        System.out.println("Game successfully created.");
        System.out.println();

        for (Player player : players) {

            System.out.println(
                    "Player "
                    + player.getPlayerId()
                    + " hand: "
                    + player.handToString()
            );
        }

        System.out.println();

        for (Deck deck : decks) {

            System.out.println(
                    "Deck "
                    + deck.getDeckId()
                    + ": "
                    + deck
            );
        }

        input.close();
    }

    // --------------------------------
    // GET NUMBER OF PLAYERS
    // --------------------------------

    public static int getNumberOfPlayers(
            Scanner input) {

        int numberOfPlayers = 0;

        while (numberOfPlayers <= 0) {

            System.out.println(
                    "Please enter the number of players:"
            );

            String entered =
                    input.nextLine();

            try {

                numberOfPlayers =
                        Integer.parseInt(entered);

                if (numberOfPlayers <= 0) {

                    System.out.println(
                            "Number of players must be greater than 0."
                    );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid integer."
                );
            }
        }

        return numberOfPlayers;
    }

    // --------------------------------
    // LOAD AND VALIDATE PACK
    // --------------------------------

    public static ArrayList<Card> loadPack(
            String filename,
            int numberOfPlayers) {

        ArrayList<Card> pack =
                new ArrayList<>();

        try {

            File packFile =
                    new File(filename);

            Scanner fileScanner =
                    new Scanner(packFile);

            while (fileScanner.hasNextLine()) {

                String line =
                        fileScanner
                                .nextLine()
                                .trim();

                try {

                    int denomination =
                            Integer.parseInt(line);

                    if (denomination < 0) {

                        fileScanner.close();

                        return null;
                    }

                    pack.add(
                            new Card(denomination)
                    );

                } catch (
                        NumberFormatException e) {

                    fileScanner.close();

                    return null;
                }
            }

            fileScanner.close();

        } catch (FileNotFoundException e) {

            return null;
        }

        if (pack.size()
                != 8 * numberOfPlayers) {

            return null;
        }

        return pack;
    }
}
