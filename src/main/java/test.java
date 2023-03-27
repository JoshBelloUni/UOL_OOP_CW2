public class test {
    public static void main(String[] args) {
        BaccaratCard c1 = new BaccaratCard(BaccaratCard.Rank.TEN, BaccaratCard.Suit.HEARTS);
        BaccaratCard c2 = new BaccaratCard(BaccaratCard.Rank.TEN, BaccaratCard.Suit.SPADES);

        BaccaratHand h1 = new BaccaratHand();

        h1.add(c1);
        h1.add(c2);

        int v = h1.value();
        System.out.println(v);
        System.out.println(h1.toString());

    }
}

