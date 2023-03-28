import java.util.Scanner;

public class Baccarat {
    public static void main(String[] args) {
        // checking size of args
        if (args.length > 1) {
            throw new CardException("Invalid Arguments");
        }

        // checks if the flag is correct
        if (args.length == 1 && (args[0] != "-i" || args[0] != "-interactive")) {
            throw new CardException("Invalid flag");
        }

        // if no flags, run normal mode
        if (args.length == 0) {
            Baccarat game = new Baccarat();
            game.BasicGame();
            System.exit(0);
        }

        // if flags exist, run interactive mode
        if (args[0] == "-i" || args[0] == "interactive") {
            Baccarat game = new Baccarat();
            game.InteractiveGame();
            System.exit(0);
        }
    }

    private void BasicGame() {
        int round = 1;
        int pWins = 0;
        int bWins = 0;
        int ties = 0;

        // creating instance of shoe object
        Shoe shoe = new Shoe(6);
        shoe.shuffle();

        while (true) {

            // creating instances of the hands for the player and banker
            BaccaratHand playerHand = new BaccaratHand();
            BaccaratHand bankerHand = new BaccaratHand();

            // adding cards from the shoe to the hands
            for (int i = 0; i < 2; i++) {
                playerHand.add(shoe.deal());
                bankerHand.add(shoe.deal());
            }

            System.out.println("Round " + round);

            // printing the string
            System.out.println("Player: " + playerHand.toString() + " = " + playerHand.value());
            System.out.println("Banker: " + bankerHand.toString() + " = " + bankerHand.value());

            // checks for a tie
            if (playerHand.value() == bankerHand.value()) {
                System.out.println("Tie");
                ties++;
            }

            // checks if either the player or banker has a natural hand
            if (playerHand.isNatural() && !bankerHand.isNatural()) {
                System.out.println("Player win!");
                pWins++;
            }
            if (bankerHand.isNatural() && !playerHand.isNatural()) {
                System.out.println("Banker win!");
                bWins++;
            }

            // if both are natural, but one is bigger, then the bigger one wins
            if (playerHand.isNatural() && bankerHand.isNatural()) {
                if (playerHand.value() > bankerHand.value()) {
                    System.out.println("Player win!");
                    pWins++;
                }
                else if (bankerHand.value() > playerHand.value()) {
                    System.out.println("Banker win!");
                    bWins++;
                }
            }

            // if no tie or winner is found, then the rules are run
            if (!playerHand.isNatural() && !bankerHand.isNatural() && playerHand.value() != bankerHand.value()) {
                boolean playerDrew = playerRule(playerHand, shoe, "player");
                if (playerDrew == true) {
                    bankerRule(bankerHand, shoe, playerHand.getThirdCard().value());
                }
                if (playerDrew == false) {
                    playerRule(bankerHand, shoe, "banker");
                }

                // printing the string
                System.out.println("Player: " + playerHand.toString() + " = " + playerHand.value());
                System.out.println("Banker: " + bankerHand.toString() + " = " + bankerHand.value());

                // checks for a tie
                if (playerHand.value() == bankerHand.value()) {
                    System.out.println("Tie");
                    ties++;
                }

                // checks if either the player or banker has a natural hand
                if (playerHand.isNatural() && !bankerHand.isNatural()) {
                    System.out.println("Player win!");
                    pWins++;
                }
                if (bankerHand.isNatural() && !playerHand.isNatural()) {
                    System.out.println("Banker win!");
                    bWins++;
                }

                // checks the creater value, whichever is larger wins
                if (playerHand.value() > bankerHand.value()) {
                    System.out.println("Player win!");
                    pWins++;
                }
                if (bankerHand.value() > playerHand.value()) {
                    System.out.println("Banker win!");
                    bWins++;
                }
            }

            playerHand.discard();
            bankerHand.discard();

            // if shoe gets below 6 cards then output the game results
            if (shoe.size() < 6) {
                break;
            }

            System.out.println(shoe.size());
            round++;

            System.out.println();
        }

        System.out.println("\n------------------\n" + round + " rounds played");
        System.out.println(pWins + " player wins");
        System.out.println(bWins + " banker wins");
        System.out.println(ties + " ties\n------------------");
    }

    private void InteractiveGame() {
    }

    private boolean playerRule(BaccaratHand hand, Shoe shoe, String caller) {
        if (hand.value() <= 5) {
            System.out.println("Dealing third card to " + caller + "...");
            hand.add(shoe.deal());
            return true;
        }
        if (hand.value() == 6 || hand.value() == 7) {
            System.out.println(caller + " is standing.");
            return false;
        }
        return false;
    }

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
}
