import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CribbageTest {

    private final Card card = new Card(Rank.A, Suit.CLUB);
    ;

    @Test
    void cardSuitTest() {
        assertEquals(Suit.CLUB, card.getSuit());
    }

    @Test
    void cardRankTest() {
        assertEquals(Rank.A, card.getRank());
    }

    @Test
    void cardValueTest() {
        assertEquals(1, card.getValue());
    }

    @Test
    void scoreTest() {
        assertEquals(29, new Hand("5h 5d 5s jc 5c").calculateScore());
        assertEquals(4, new Hand("0d jh qs ac 9d").calculateScore());
    }
}
