public class Baccarat {
    public static void main(String[] args) {
        
        // creating instance of shoe object
        Shoe shoe = new Shoe(6);
        shoe.shuffle();

        // creating instances of the hands for the player and banker
        BaccaratHand playerHand = new BaccaratHand();
        BaccaratHand bankerHand = new BaccaratHand();

        // adding cards from the shoe to the hands
        for (int i = 0; i < 2; i++) {
            playerHand.add(shoe.deal());
            bankerHand.add(shoe.deal());
        }

        // printing the string
        System.out.println("Player: " + playerHand.toString());
        System.out.println("Banker: " + bankerHand.toString());

        // checks if either the player or banker has a natural hand
        if (playerHand.isNatural() == true) {
            System.out.println("Player has a Natural");
        }

        if(bankerHand.isNatural() == true) {
            System.out.println("Banker has a Natural");
        }
    }
}
