import java.io.File
import java.util.ArrayList
import java.util.Scanner

public class CardGame {
    private int players;
    private List<Integer> values;

    public CardGame() {
        this.values = new ArrayList<>();
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of players: ");
        players = Scanner.nextInt();

        System.out.println("Enter location of the pack file: ");
        String packfile = scanner.next(); //player enters file used for the card values

        readPackFile(packFile);

        if (values.size() != players * 8) {
            System.out.println("Invalid number of cards in the deck");
            return; // validating size of card pack
        }

        List<Card> pack = cardConversion(values);

        List<Deck> playerDecks = new ArrayList<>();

        for (int i = 0; i < players; i++) {
            playerDecks.add(new Deck()); // Gives each player their own deck
        }

        
    }
        
    private void readPackFile(String packFile) {
        try {
            Scanner fileScan = new Scanner(new File(packfile)); //scanner reads file
            
            while (fileScan.hasNextInt()) {
            values.add(fileScan.nextInt()); // file values are entered into list
        }
            catch (Exception e) {
            System.out.println("File could not be read" + e.getMessage());
        }
            
            
    private List<Card> cardConversion(List<Integer> values) {
        List<Card> pack = new ArrayList<>();

        for(int value : values) {
            pack.add(new Card(value)); //Turn integers from the file into card objects
        }
        return pack;

    
    }








    

