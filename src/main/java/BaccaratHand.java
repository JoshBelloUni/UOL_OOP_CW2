
public class BaccaratHand extends CardCollection {

    public BaccaratHand() {
        super();
    }

    public int size() {
        return super.size();
    }

    public void add(BaccaratCard card) {
        super.add(card);
    }

    public int value() {
        int total = super.value();
        return total % 10;

    }

    public boolean isNatural() {
        if (this.size() != 2 || (this.value() != 8 && this.value() != 9)) {
            return false;
        }

        else {
            return true;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card.toString());
            sb.append(" ");
        }
        return sb.toString().trim();
    }

}
