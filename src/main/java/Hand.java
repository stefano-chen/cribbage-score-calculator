import java.util.ArrayList;
import java.util.Objects;

public class Hand {

    private final Card[] cards = new Card[4];
    private Card starter;
    private final String strCard;

    public Hand(String cards) throws NotACardException {
        this.strCard = cards;
        fromString(this.strCard);
    }

    public Card getStarter() {
        return this.starter;
    }

    public Card getCard(Card card) {
        for (Card c : this.cards) {
            if (c.equals(card)) {
                return c;
            }
        }
        return null;
    }

    public Card[] getCards(Rank rank) {
        ArrayList<Card> result = new ArrayList<>();
        for (Card c : this.cards) {
            if (c.sameRank(rank)) {
                result.add(c);
            }
        }
        return result.isEmpty() ? null : result.toArray(new Card[result.size()]);
    }

    public Card[] getCards(Suit suit) {
        ArrayList<Card> result = new ArrayList<>();
        for (Card c : this.cards) {
            if (c.sameSuit(suit)) {
                result.add(c);
            }
        }
        return result.isEmpty() ? null : result.toArray(new Card[result.size()]);
    }

    private void fromString(String strCard) throws NotACardException {
        String[] token = this.strCard.split(" ");
        if (token.length != 5) {
            throw new NotACardException("Card string not well formatted");
        }
        for (int i = 0; i < token.length - 1; i++) {
            this.cards[i] = Card.fromString(token[i]);
        }
        this.starter = Card.fromString(token[4]);
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < cards.length; i++) {
            str += ("hand card " + (i + 1) + System.lineSeparator() + ("    " + this.cards[i]) + System.lineSeparator());
        }

        str += ("starter card " + System.lineSeparator() + ("    " + this.starter) + System.lineSeparator());

        return str;
    }

    public int calculateScore() {
        return checkFlush() + checkPairs() + checkRuns() + checkFifteenTwos();
    }

    private int checkFlush() {
        int points = 0;


        return points;
    }

    private int checkPairs() {
        return 0;
    }

    private int checkRuns() {
        return 0;
    }

    private int checkFifteenTwos() {
        return 0;
    }
}
