import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// A class to represent the Hand
// A hand is composed by 4 hand card and a 1 starter card
public class Hand {

    private final Card[] cards = new Card[4];
    private final String strCard;
    private Card starter;

    public Hand(String cards) throws CardException {
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

        // Create a Dictionary with the number of occurrences per rank
        Map<Rank, Integer> hashmap = new HashMap<>();

        Card c;
        for (int i = 0; i < this.cards.length + 1; i++) {
            c = (i < this.cards.length) ? this.cards[i] : this.starter;
            if (!hashmap.containsKey(c.getRank())) {
                hashmap.put(c.getRank(), 1);
            } else {
                Rank rank = c.getRank();
                hashmap.put(rank, hashmap.get(rank) + 1);
            }

        }

        // Assign points based on the number of occurrences
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
        Card[] x = Arrays.copyOf(this.cards, 5);
        x[4] = this.starter;
        // the greatest scale achieved at the moment
        int maxScaleLen = 1;
        int i = 0;
        // current number of cards at the moment in the scale
        int currScaleLen = 1;

        Arrays.sort(x);

        while (i < x.length - 1) {

            if (x[i].getValue() == x[i + 1].getValue() - 1) {
                currScaleLen += 1;
            } else {
                maxScaleLen = Math.max(maxScaleLen, currScaleLen);
                currScaleLen = 1;
            }

            i++;
        }

        maxScaleLen = Math.max(maxScaleLen, currScaleLen);

        return maxScaleLen < 3 ? 0 : maxScaleLen;
    }


    // Recursive function that returns the number of combinations of two or more cards that sum up to 15
    // target is the sum we want to reach (in this case 15)
    // cardIdx is the index of the card we are considering
    private int numCombSumFifteen(int target, int cardIdx, int[] cards) {

        // This function divide ,the problem of summing up to Target, in sub problems of finding the sub_target

        /*
         *  Let's consider the cards with value [ 5, 5, 5, 10, 5]
         *
         *  Each node of this binary tree is the current target obtained by [ previous target - taken card ]
         *  The left branch represent the choice to take/consider the current card
         *  The right branch represent the choice to NOT take/consider the current card
         *
         *                                                                   15
         *cardIdx:0      current card: 5             take /                                   \ not take
         *                                              10                                     15
         *cardIdx:1      current card: 5         /              \                    /                     \
         *                                      5                10                 10                     15
         *cardIdx:2      current card: 5       / \            /     \           /       \            /           \
         *                                    0   5           5     10         5         10        10             15
         *cardIdx:3      current card: 10        / \         / \    / \       / \       /  \      /  \          /   \
         *                                      -5  5      -5   5  0   10    -5  5     0    10   0   10        5     15
         *cardIdx:4      current card: 5           / \         / \     /\       / \        /  \      / \      / \   /  \
         *                                        0   5       0   5   5  10    0   5      5    10   5   10   0   5 10  15
         *cardIdx:5
         *
         *
         *   The number of ways to sum up to 15 with two or more cards is represented by the number of zeros in this binary tree
         *   When we find a node with target == 0, we return 1. In all other case (negative target, target != 0 after last iteration) we return 0
         *   A node that receive the returns of the children has to sum them up and return it to the parent node
         *
         *   E.g. To sum up to 15 we can take the following card [first 5, second 5 , third 5] (the left most branch)
         *        or we could [first 5, not second 5, third 5, not 10, last 5] (the third zero from the left)
         * */


        // There is only 1 way to sum up to the target 0 (base case)
        if (target == 0) {
            return 1;
        }

        // There are 0 ways to sum up to a negative number since the card values are not negative (base case)
        if (target < 0) {
            return 0;
        }

        // if after considering all cards, the target is still not zero then we have 0 ways to the target
        if (cardIdx >= cards.length) {
            return 0;
        }

        // The target of the new sub problem
        int sub_target = target - cards[cardIdx];

        // We can consider the card in position cardIdex and subtract from the target then move to the next card
        // Or we could ignore the current card and move to the next one.
        int countWays = numCombSumFifteen(sub_target, cardIdx + 1, cards) + numCombSumFifteen(target, cardIdx + 1, cards);

        return countWays;
    }

    // 2 points for each separate combination of two or more cards totalling exactly 15
    private int checkFifteenTwos() {

        // Array that contains only the cards value
        int[] cardValue = new int[5];

        Card c;

        for (int i = 0; i < this.cards.length + 1; i++) {
            c = (i < this.cards.length) ? this.cards[i] : this.starter;
            // The jack, queen and king have value 10
            if (c.getRank() != Rank.Jack && c.getRank() != Rank.Queen && c.getRank() != Rank.King) {
                cardValue[i] = c.getValue();
            } else {
                cardValue[i] = 10;
            }
        }

        return 2 * numCombSumFifteen(15, 0, cardValue);
    }
}
