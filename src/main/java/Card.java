import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Card {
    private final Rank rank;
    private final Suit suit;

    private final static Map<Character, Rank> rankMap;
    private final static Map<Character, Suit> suitMap;


    static {
        rankMap = new HashMap<>();
        rankMap.put('A', Rank.A);
        rankMap.put('2', Rank.Two);
        rankMap.put('3', Rank.Three);
        rankMap.put('4', Rank.Four);
        rankMap.put('5', Rank.Five);
        rankMap.put('6', Rank.Six);
        rankMap.put('7', Rank.Seven);
        rankMap.put('8', Rank.Eight);
        rankMap.put('9', Rank.Nine);
        rankMap.put('0', Rank.Ten);
        rankMap.put('J', Rank.Jack);
        rankMap.put('Q', Rank.Queen);
        rankMap.put('K', Rank.King);

        suitMap = new HashMap<>();
        suitMap.put('C', Suit.CLUB);
        suitMap.put('D', Suit.DIAMOND);
        suitMap.put('H', Suit.HEART);
        suitMap.put('S', Suit.SPADE);
    }

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Suit getSuit() {
        return this.suit;
    }

    public Rank getRank() {
        return this.rank;
    }

    public int getValue() {
        return this.rank.getValue();
    }

    public boolean sameSuit(Suit suit){
        return this.suit == suit;
    }

    public boolean sameRank(Rank rank){
        return this.rank == rank;
    }

    @Override
    public String toString() {
        return "rank: " + this.getRank() + " suit: " + this.getSuit();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }

    public static Card fromString(String card) throws NotACardException {
        if (card.length() != 2) {
            throw new NotACardException("Card string not well formatted");
        }
        Rank rank = rankMap.get(card.charAt(0));
        Suit suit = suitMap.get(card.charAt(1));

        if (rank == null || suit == null) {
            throw new NotACardException("Invalid Rank/Suit");
        }

        return new Card(rank, suit);
    }
}
