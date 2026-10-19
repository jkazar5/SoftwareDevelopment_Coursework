ECM2414 Software Development Coursework — Multithreaded Card Game

Overview

This project is a Java card game in which multiple players run on separate threads. Each player starts with four cards and attempts to collect four cards of the same denomination. Players draw cards from their own deck and discard cards into the next player's deck. The first player to hold four matching cards wins.

The project includes the game source code, a pack generator, and JUnit 4 tests.

Requirements

Java Development Kit (JDK), with java and javac available in the terminal.

No external libraries are required to run the game.

The supplied JUnit 4.13.2 and Hamcrest JAR files in lib/ are required to run the tests.

Project structure

SoftwareDevelopment_Coursework/
├── src/
│   ├── Card.java
│   ├── CardGame.java
│   ├── Deck.java
│   ├── PackGenerator.java
│   └── Player.java
├── test/
│   ├── CardGameTest.java
│   ├── CardTest.java
│   ├── DeckTest.java
│   ├── PlayerTest.java
│   ├── validPack.txt
│   ├── negativeValue.txt
│   └── wrongPackSize.txt
└── lib/
    ├── junit-4.13.2.jar
    └── hamcrest-core-1.3.jar

Main classes

Class

Purpose

Card

Stores a card's non-negative integer denomination.

Deck

Stores cards in a FIFO queue; provides synchronized methods for adding and drawing cards.

Player

Implements Runnable, maintains a four-card hand, draws and discards cards, checks for a win, and writes a player log.

CardGame

Handles user input, pack validation, card distribution, player-thread creation, winner handling, and final deck files.

PackGenerator

Optionally generates a shuffled pack for the chosen number of players.

How to compile and run

Open a terminal inside the SoftwareDevelopment_Coursework folder (the folder containing src, test, and lib).

1. Compile the source files

javac -d out src/*.java

This creates compiled .class files in the out directory.

2. Run the game

java -cp out CardGame

3. Follow the prompts

Enter the number of players (a positive integer).

Choose whether to generate a new pack by entering yes or no.

Enter the path to the pack text file.

If you chose yes, the program creates Pack.txt in the current working directory. Enter:

Pack.txt

If you chose no, you can use the supplied example pack for four players by entering:

test/validPack.txt

The game rejects invalid pack files and prompts again.

Pack file format

A pack is a plain-text file containing one integer denomination per line.

For n players, the pack must contain exactly 8 × n cards. All denominations must be non-negative integers; they do not have to be limited to the numbers 1 through n.

For example, four players require 32 lines. The program deals the first 16 cards to the players, four each in round-robin order. It then distributes the remaining 16 cards among the four decks, also in round-robin order.

The optional generator creates eight cards of each denomination from 1 to n, shuffles them, and writes the result to Pack.txt.

Game rules and threading

Player i draws from deck i and discards into the next deck (wrapping from the final deck to deck 1).

Each player prefers cards matching their own player number.

The discard strategy selects the oldest non-preferred card when available.

A player wins by holding any four cards of the same denomination, not necessarily their preferred denomination.

A winning starting hand is detected before normal gameplay begins.

Each player is represented by a Java thread.

Deck operations use synchronization, and the player implementation uses a shared game lock to coordinate turns and winner detection.

If a draw deck is temporarily empty, the player waits briefly before trying again.

Concurrency note: The shared game lock currently serializes complete turns even though players run on separate threads. This is a deliberate characteristic of the current implementation, not fully parallel turn execution.

Output files

The program writes output files to the working directory from which it is run.

For each player:

player1_output.txt
player2_output.txt
...

Player files record the starting hand, draws, discards, updated hands, winner/notification information, exit, and final hand.

For each deck:

deck1_output.txt
deck2_output.txt
...

Deck files record the cards remaining in the deck at the end of the game, in queue order. The program also prints a card-count check after normal gameplay.

JUnit tests

The test folder contains JUnit 4 tests for:

Card: storing denominations, rejecting negative values, and converting to a string.

Deck: FIFO drawing, deck IDs, and size.

Player: starting hands, maintaining four cards, preferred denominations, and winning hands.

CardGame: valid packs, incorrect pack sizes, and negative values.

Current test-source fixes required

The test suite in the uploaded ZIP does not currently compile. Before running it, fix the following in test/PlayerTest.java:

Change new card(4) to new Card(4).

Change both occurrences of haswon() to hasWon().

Remove the extra closing } at the end of the file.

The CardGameTest methods also use filenames such as validPack.txt without the test/ prefix. The commands below run the tests from inside the test folder so those paths resolve correctly.

Compile tests (Windows PowerShell)

From the project folder, after compiling the main source files:

javac -cp "out;lib/*" -d out test/*.java

Run tests (Windows PowerShell)

cd test
java -cp "../out;../lib/*" org.junit.runner.JUnitCore CardTest DeckTest PlayerTest CardGameTest
cd ..

On macOS/Linux, replace the classpath separator ; with :.

Testing status: The main source files in the uploaded ZIP compile successfully. The test suite has not been verified as passing; the errors listed above must be corrected first.

Notes and known limitations

The game requires a valid user-supplied pack file, even when pack generation is selected; enter Pack.txt after generation.

Output files are created relative to the directory where the game is launched.

The current design uses a shared game lock around turns, so turn execution is serialized.

The current empty-deck approach uses a short sleep and retry rather than condition-based waiting.

The existing tests should be expanded to cover complete-game termination, immediate wins, output files, and multithreaded behavior.

Coursework submission

This repository contains the Java source and JUnit test files. Check the coursework brief separately for the required executable JAR, test archive, written design report, and development-process documentation; these submission artifacts are not included in the uploaded ZIP.
