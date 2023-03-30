import java.util.Scanner;
import java.lang.String;

public class Baccarat {

    int round = 1;
    int pWins = 0;
    int bWins = 0;
    int ties = 0;
    
    public static void main(String[] args) {

        // command line check if there is too much args
        if (args.length > 1) {
            throw new CardException("Invalid Number of Arguments");
        }
        
        // creating instance of shoe object
        Shoe shoe = new Shoe(6);
        shoe.shuffle();

        // creating game object
        Baccarat gameRound = new Baccarat();

        // creating scanner
        Scanner inputScanner = new Scanner(System.in);
        
        // running basic mode
        if (args.length == 0) {
            while (shoe.size() > 6) {   // end of game checks
                gameRound.BasicRound(shoe);
            }
        } 

        // running interacive mode
        else if (args.length == 1 && (args[0].equals("-i") || args[0].equals("--interactive"))) {
            while (true) {
                gameRound.BasicRound(shoe);
                if (gameRound.checkExit(inputScanner) == 1 || shoe.size() < 6) { // end of game checks
                    break;
                }
            }
        } 
        else {  // args are incorrect
            inputScanner.close();
            throw new CardException("Invalid Arguments");
        }

        // close Scanner
        inputScanner.close();

        // print game results
        gameRound.printEndingScreen(gameRound.round, gameRound.pWins, gameRound.bWins, gameRound.ties);
    }

    private void BasicRound(Shoe shoe) {
        // printing the round number
        System.out.println("Round " + this.round);

        // creating instances of the hand objects for the player and banker
        BaccaratHand playerHand = new BaccaratHand();
        BaccaratHand bankerHand = new BaccaratHand();

        // initialise the round with 2 cards per person and print them out
        initGame(playerHand, bankerHand, shoe);

        int winner = checkWinner(playerHand, bankerHand);
        switch (winner) {
            case 0: // if there is a tie return 0
                this.ties++;
                break;
            case 1: // if there is a player win return 1
                this.pWins++;
                break;
            case 2: // if there is a banker win return 2
                this.bWins++;
                break;
        }

        // if no tie or winner is found, then the rules are run
        if (!playerHand.isNatural() && !bankerHand.isNatural() && playerHand.value() != bankerHand.value()) {
            boolean playerDrew = playerRule(playerHand, shoe, "player");
            if (playerDrew == true) { // if the player drew, then the bankers rules apply
                bankerRule(bankerHand, shoe, playerHand.getThirdCard().value());
            }
            if (playerDrew == false) { // if the player did not draw, then the banker user the player rules
                playerRule(bankerHand, shoe, "banker");
            }

            printHands(playerHand, bankerHand);

            // check again for winner
            winner = checkWinner(playerHand, bankerHand);
            switch (winner) {
                case 0: // if there is a tie return 0
                    this.ties++;
                    break;
                case 1: // if there is a player win return 1
                    this.pWins++;
                    break;
                case 2: // if there is a banker win return 2
                    this.bWins++;
                    break;
            }

            // additional check for winner
            // only happens if there is a third drawn card
            // whatever the higer value wins
            winner = checkHigher(playerHand, bankerHand);
            switch (winner) {
                case 1: // if there is a player win return 1
                    this.pWins++;
                    break;
                case 2: // if there is a banker win return 2
                    this.bWins++;
                    break;
            }
        }

        // discard the hand and start over
        playerHand.discard();
        bankerHand.discard();

        // if shoe gets below 6 cards then output the game results
        if (shoe.size() < 6) {
            return;
        }
        this.round++;
        System.out.println();
    }

    /*
     * this is the method for the player rule
     * true/false is returned depending if the player or banker
     * meets the requirements to pull a third card
     * 
     * as both player and banker can use this,
     * if i want to print who gets the card/ who is standing,
     * i pass a string arguement in
     */
    private boolean playerRule(BaccaratHand hand, Shoe shoe, String caller) {
        if (hand.value() <= 5) {
            System.out.println("Dealing third card to " + caller + "...");
            hand.add(shoe.deal());
            return true;
        }
        if (hand.value() == 6 || hand.value() == 7) {
            String capitalizedCaller = caller.substring(0, 1).toUpperCase() + caller.substring(1).toLowerCase();
            System.out.println(capitalizedCaller + " is standing.");
            return false;
        }
        return false;
    }

    /*
     * this method deals with the banker rules
     * 
     * as only the value of the player third card is important,
     * only that is passed as an argument.
     */
    private void bankerRule(BaccaratHand hand, Shoe shoe, int thirdCardValue) {
        if (hand.value() <= 2) {
            // draws a third card regardless of player's third card
            System.out.println("Dealing third card to banker...");
            BaccaratCard card = shoe.deal();
            hand.add(card);

        } else if (hand.value() == 3) {
            // draws a third card unless player's third card is 8
            if (thirdCardValue != 8) {
                System.out.println("Dealing third card to banker...");
                BaccaratCard card = shoe.deal();
                hand.add(card);
            }

        } else if (hand.value() == 4) {
            // draws a third card if player's third card is 2, 3, 4, 5, 6, or 7
            if (thirdCardValue >= 2 && thirdCardValue <= 7) {
                System.out.println("Dealing third card to banker...");
                BaccaratCard card = shoe.deal();
                hand.add(card);
            }

        } else if (hand.value() == 5) {
            // draws a third card if player's third card is 4, 5, 6, or 7
            if (thirdCardValue >= 4 && thirdCardValue <= 7) {
                System.out.println("Dealing third card to banker...");
                BaccaratCard card = shoe.deal();
                hand.add(card);
            }

        } else if (hand.value() == 6) {
            // draws a third card if player's third card is 6 or 7
            if (thirdCardValue == 6 || thirdCardValue == 7) {
                System.out.println("Dealing third card to banker...");
                BaccaratCard card = shoe.deal();
                hand.add(card);
            }

        } else if (hand.value() == 7) {
            // banker stands
            System.out.println("Banker is standing.");
        }
    }

    private void printHands(BaccaratHand pHand, BaccaratHand bHand) {
        // printing the string
        System.out.println("Player: " + pHand.toString() + " = " + pHand.value());
        System.out.println("Banker: " + bHand.toString() + " = " + bHand.value());
    }

    private void initGame(BaccaratHand pHand, BaccaratHand bHand, Shoe shoe) {
        // adding cards from the shoe to the hands
        for (int i = 0; i < 2; i++) {
            pHand.add(shoe.deal());
            bHand.add(shoe.deal());
        }

        printHands(pHand, bHand);
    }

    private int checkWinner(BaccaratHand pHand, BaccaratHand bHand) {
        // checks for a tie
        if (pHand.value() == bHand.value()) {
            System.out.println("Tie");
            return 0;
        }

        // checks if either the player or banker has a natural hand
        if (pHand.isNatural() && !bHand.isNatural()) {
            System.out.println("Player win!");
            return 1;
        }
        if (bHand.isNatural() && !pHand.isNatural()) {
            System.out.println("Banker win!");
            return 2;
        }

        // if both are natural, but one is bigger, then the bigger one wins
        if (pHand.isNatural() && bHand.isNatural()) {
            if (pHand.value() > bHand.value()) {
                System.out.println("Player win!");
                return 1;
            } else if (bHand.value() > pHand.value()) {
                System.out.println("Banker win!");
                return 2;
            }
        }
        return -1;
    }

    private int checkHigher(BaccaratHand pHand, BaccaratHand bHand) {
        // checks the creater value, whichever is larger wins
        if (pHand.value() > bHand.value()) {
            System.out.println("Player win!");
            return 1;
        } else if (bHand.value() > pHand.value()) {
            System.out.println("Banker win!");
            return 2;
        }
        return -1;
    }

    private void printEndingScreen(int rounds, int pWins, int bWins, int ties) {
        System.out.println("\n------------------\n" + (rounds - 1) + " rounds played");
        System.out.println(pWins + " player wins");
        System.out.println(bWins + " banker wins");
        System.out.println(ties + " ties\n------------------");
    }

    private int checkExit(Scanner checkExit) {
        System.out.print("Another Round? (y/n): ");
        String check = checkExit.nextLine();
        check = check.toLowerCase();
        while (!check.equals("y") && !check.equals("n")) {
            System.out.print("Enter y or n ");
            check = checkExit.nextLine();
            check = check.toLowerCase();
        }
        if (check.equals("n")) {
            return 1;
        }
        return -1;
    }
}
