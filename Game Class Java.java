Scanner scanner = new Scanner(System.in);
System.out.println("Enter number of players: ");
int players = scanner.nextInt(); //scanner reads number of players


System.out.println("Enter location of the pack file: ");
String packfile = scanner.next(); //player enters file used for the curd values


Scanner fileScan = new Scanner(new File(packfile)); //scanner reads file
List<Integer> values = new ArrayList<>();

while (fileScan.hasNextInt()) {
    values.add(fileScan.nextInt) // file values are entered into a list
}

if (values.size != players * 8) {
        System.out.println("Invalid number of cards in the deck");
        return; // validates the correct number of cards
    }

    private List<Card> cardConversion(List<Integer> values) {
        List<Card> pack = new ArrayList<>();

    }








    

