import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println("A card is represented as [Rank][Suit]");
        System.out.println("The possible ranks are: A 2 3 4 5 6 7 8 9 0 J Q K");
        System.out.println("The possible suits are: C(CLUB) D(DIAMOND) H(HEART) S(SPADE)");
        System.out.println("Insert the 5 cards (4-Hand 1-Starter) [Rank][Suit] [Rank][Suit] [Rank][Suit] [Rank][Suit] [Rank][Suit]");
        String cards = input.nextLine().toUpperCase();
        try{
            Hand hand = new Hand(cards);
            System.out.println(hand);
//            Card[] foundCards = hand.getCards(Suit.CLUB);
//            System.out.println( (foundCards == null)? "Not found" : foundCards.length);
            System.out.println("Score: " + hand.calculateScore());
        }catch(CardException e){
            System.out.println(e.getMessage());
        }
//        Card card1 = Card.fromString("AC");
//        Card card2 = Card.fromString("AD");
//        System.out.println(card1.equals(card2));
    }
}
