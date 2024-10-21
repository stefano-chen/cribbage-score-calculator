import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CribbageTest {

    @Test
    void cardSuitTest() {
        Card card = new Card(Rank.A, Suit.CLUB);

        assertEquals(Suit.CLUB, card.getSuit());
    }

    @Test
    void cardRankTest() {
        Card card = new Card(Rank.A, Suit.CLUB);

        assertEquals(Rank.A, card.getRank());
    }
}
