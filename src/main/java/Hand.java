import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// A class to represent the Hand
// A hand is composed by 4 hand card and a 1 starter card
public class Hand {

    private final Card[] cards = new Card[4];
    private Card starter;
    private final String strCard;

    public Hand(String cards) throws CardException {
        this.strCard = cards;
        fromString(this.strCard);
        Arrays.sort(this.cards);
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

    // Returns null if the 4 hand cards do not contain the specified rank
    // otherwise it returns all the found cards
    public Card[] getCards(Rank rank) {
        ArrayList<Card> result = new ArrayList<>();
        for (Card c : this.cards) {
            if (c.sameRank(rank)) {
                result.add(c);
            }
        }
        return result.isEmpty() ? null : result.toArray(new Card[result.size()]);
    }


    // Returns null if the 4 hand cards do not contain the specified suit
    // otherwise it returns all the found cards
    public Card[] getCards(Suit suit) {
        ArrayList<Card> result = new ArrayList<>();
        for (Card c : this.cards) {
            if (c.sameSuit(suit)) {
                result.add(c);
            }
        }
        return result.isEmpty() ? null : result.toArray(new Card[result.size()]);
    }

    // Given a string representing the 5 cards
    // It creates an array containing the 4 hand cards and initialize the starter card
    private void fromString(String strCard) throws CardException {
        String[] token = strCard.split(" ");
        if (token.length != 5) {
            throw new CardException("Cards string not well formatted");
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


    // Returns the Score associated to this Hand
    public int calculateScore() {
        return checkFlush() + checkPairs() + checkRuns() + checkFifteenTwos();
    }

    // 4 points where all 4 hand cards have the same suit
    // 5 points where all 4 hand cards and the starter card have the same suit
    // An additional 1 point if in the 4 hand cards there is a Jack with the same suit as the starter
    private int checkFlush() {
        int points = 0;
        Card[] handCards = this.getCards(this.cards[0].getSuit());
        if (handCards.length == 4) {
            points += 4;
            if (handCards[0].sameSuit(this.starter.getSuit())) {
                points += 1;
            }
        }
        handCards = this.getCards(Rank.Jack);
        if (handCards != null) {
            for (Card c : handCards) {
                if (c.sameSuit(this.starter.getSuit())) {
                    points += 1;
                }
            }
        }
        return points;
    }

    // 2 points for a pair of cards with the same rank
    // 6 points for three cards with the same rank
    // 12 points for four cards with the same rank
    private int checkPairs() {
        int points = 0;
        Map<Rank, Integer> hashmap = new HashMap<>();
        for (Card c : this.cards) {
            if (!hashmap.containsKey(c.getRank())) {
                hashmap.put(c.getRank(), 1);
            } else {
                Rank rank = c.getRank();
                hashmap.put(rank, hashmap.get(rank) + 1);
            }
        }

        for (Map.Entry<Rank, Integer> entry : hashmap.entrySet()) {
            switch (entry.getValue()) {
                case 2:
                    points += 2;
                    break;
                case 3:
                    points += 6;
                    break;
                case 4:
                    points += 12;
                    break;
            }
        }
        return points;
    }


    // 3 points for a run of three consecutive cards (regardless of suit)
    // 4 points for a run of four
    // 5 points for a run of five
    private int checkRuns() {

        return 0;
    }

    // 2 points for each separate combination of two or more cards totalling exactly 15
    private int checkFifteenTwos() {
        return 0;
    }
}
