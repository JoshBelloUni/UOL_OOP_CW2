public class BaccaratCard extends Card {

    public BaccaratCard(Rank r, Suit s) {
        super(r, s);
    }
    
    public Rank getRank() {
        return super.getRank();
    }

    public Suit getSuit() {
        return super.getSuit();
    }

    public String toString() {
        return super.toString();
    }

    public boolean equals(BaccaratCard other) {
        return super.equals(other);
    }

    public int compareTo(Card other) {
        return super.compareTo(other);
    }

    public int value() {
        if (this.getRank().ordinal() >= 9) {
            return 0;
        }
        else {
            return this.getRank().ordinal() + 1;
        }
    }
}
