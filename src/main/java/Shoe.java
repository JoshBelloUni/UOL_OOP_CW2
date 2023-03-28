import java.util.Collections;
import java.util.LinkedList;

public class Shoe {

    private LinkedList<BaccaratCard> cards;

    public Shoe(int decks) {
        if (decks != 6 && decks != 8) {
            throw new CardException("Invalid number of decks");
        }
        cards = new LinkedList<>();

        for (int i = 0; i < decks; i++) {
            for (BaccaratCard.Suit suit : BaccaratCard.Suit.values()) {
                for (BaccaratCard.Rank rank : BaccaratCard.Rank.values()) {
                    BaccaratCard card = new BaccaratCard(rank, suit);
                    cards.add(card);
                }
            }
        }    
    }

    public int size() {
        return cards.size();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public BaccaratCard deal() {
        if (cards.isEmpty()) {
            throw new CardException("Shoe is empty");
        }
        
        return cards.removeFirst();
    }
}
