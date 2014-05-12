package poker;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import poker.Poker.Rank;

public class PokerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getRankはロイヤルストレートフラッシュを返す() throws Exception {
//        try {
            Method method = Poker.class.getDeclaredMethod("getRank", List.class);
            method.setAccessible(true);

            Poker poker = new Poker();
            List<Card> hand = new ArrayList<Card>();
            hand.add(new Card(Card.Mark.CLUB, 1));
            hand.add(new Card(Card.Mark.CLUB, 10));
            hand.add(new Card(Card.Mark.CLUB, 11));
            hand.add(new Card(Card.Mark.CLUB, 12));
            hand.add(new Card(Card.Mark.CLUB, 13));

            Rank expected = Rank.ROYAL_STRAIGHT_FLUSH;
            Rank actual = (Rank) method.invoke(poker, hand);
            assertEquals(expected, actual);

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void getRankはストレートフラッシュを返す() {
        try {
            Method method = Poker.class.getDeclaredMethod("getRank", List.class);
            method.setAccessible(true);

            Poker poker = new Poker();
            List<Card> hand = new ArrayList<Card>();
            hand.add(new Card(Card.Mark.CLUB, 5));
            hand.add(new Card(Card.Mark.CLUB, 6));
            hand.add(new Card(Card.Mark.CLUB, 7));
            hand.add(new Card(Card.Mark.CLUB, 8));
            hand.add(new Card(Card.Mark.CLUB, 9));

            Rank expected = Rank.STRAIGHT_FLUSH;
            Rank actual = (Rank) method.invoke(poker, hand);
            assertEquals(expected, actual);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void getRankはフルハウスを返す() {
        try {
            Method method = Poker.class.getDeclaredMethod("getRank", List.class);
            method.setAccessible(true);

            Poker poker = new Poker();
            List<Card> hand = new ArrayList<Card>();
            hand.add(new Card(Card.Mark.CLUB, 5));
            hand.add(new Card(Card.Mark.HEART, 5));
            hand.add(new Card(Card.Mark.SPADE, 7));
            hand.add(new Card(Card.Mark.HEART, 7));
            hand.add(new Card(Card.Mark.DIAMOND, 7));

            Rank expected = Rank.FULL_HOUSE;
            Rank actual = (Rank) method.invoke(poker, hand);
            assertEquals(expected, actual);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void getRankはストレートを返す() {
        try {
            Method method = Poker.class.getDeclaredMethod("getRank", List.class);
            method.setAccessible(true);

            Poker poker = new Poker();
            List<Card> hand = new ArrayList<Card>();
            hand.add(new Card(Card.Mark.CLUB, 1));
            hand.add(new Card(Card.Mark.HEART, 2));
            hand.add(new Card(Card.Mark.CLUB, 3));
            hand.add(new Card(Card.Mark.SPADE, 4));
            hand.add(new Card(Card.Mark.DIAMOND, 5));

            Rank expected = Rank.STRAIGHT;
            Rank actual = (Rank) method.invoke(poker, hand);
            assertEquals(expected, actual);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRankはフラッシュを返す() {
        try {
            Method method = Poker.class.getDeclaredMethod("getRank", List.class);
            method.setAccessible(true);

            Poker poker = new Poker();
            List<Card> hand = new ArrayList<Card>();
            hand.add(new Card(Card.Mark.CLUB, 1));
            hand.add(new Card(Card.Mark.CLUB, 3));
            hand.add(new Card(Card.Mark.CLUB, 4));
            hand.add(new Card(Card.Mark.CLUB, 6));
            hand.add(new Card(Card.Mark.CLUB, 11));

            Rank expected = Rank.FLUSH;
            Rank actual = (Rank) method.invoke(poker, hand);
            assertEquals(expected, actual);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRankはフォーカードを返す() {
        try {
            Method method = Poker.class.getDeclaredMethod("getRank", List.class);
            method.setAccessible(true);

            Poker poker = new Poker();
            List<Card> hand = new ArrayList<Card>();
            hand.add(new Card(Card.Mark.CLUB, 8));
            hand.add(new Card(Card.Mark.SPADE, 8));
            hand.add(new Card(Card.Mark.DIAMOND, 8));
            hand.add(new Card(Card.Mark.CLUB, 11));
            hand.add(new Card(Card.Mark.HEART, 8));

            Rank expected = Rank.FOUR_OF_A_KIND;
            Rank actual = (Rank) method.invoke(poker, hand);
            assertEquals(expected, actual);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void getRankはスリーカードを返す() {
        try {
            Method method = Poker.class.getDeclaredMethod("getRank", List.class);
            method.setAccessible(true);

            Poker poker = new Poker();
            List<Card> hand = new ArrayList<Card>();
            hand.add(new Card(Card.Mark.CLUB, 11));
            hand.add(new Card(Card.Mark.SPADE, 11));
            hand.add(new Card(Card.Mark.DIAMOND, 11));
            hand.add(new Card(Card.Mark.CLUB, 8));
            hand.add(new Card(Card.Mark.CLUB, 13));

            Rank expected = Rank.THREE_OF_A_KIND;
            Rank actual = (Rank) method.invoke(poker, hand);
            assertEquals(expected, actual);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void getRankはツーペアを返す() {
        try {
            Method method = Poker.class.getDeclaredMethod("getRank", List.class);
            method.setAccessible(true);

            Poker poker = new Poker();
            List<Card> hand = new ArrayList<Card>();
            hand.add(new Card(Card.Mark.CLUB, 5));
            hand.add(new Card(Card.Mark.DIAMOND, 5));
            hand.add(new Card(Card.Mark.SPADE, 1));
            hand.add(new Card(Card.Mark.DIAMOND, 1));
            hand.add(new Card(Card.Mark.CLUB, 0));

            Rank expected = Rank.TWO_PAIR;
            Rank actual = (Rank) method.invoke(poker, hand);
            assertEquals(expected, actual);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void getRankはワンペアを返す() {
        try {
            Method method = Poker.class.getDeclaredMethod("getRank", List.class);
            method.setAccessible(true);

            Poker poker = new Poker();
            List<Card> hand = new ArrayList<Card>();
            hand.add(new Card(Card.Mark.CLUB, 5));
            hand.add(new Card(Card.Mark.DIAMOND, 5));
            hand.add(new Card(Card.Mark.CLUB, 7));
            hand.add(new Card(Card.Mark.CLUB, 8));
            hand.add(new Card(Card.Mark.CLUB, 9));

            Rank expected = Rank.ONE_PAIR;
            Rank actual = (Rank) method.invoke(poker, hand);
            assertEquals(expected, actual);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void getRankはノーペアを返す() {
        try {
            Method method = Poker.class.getDeclaredMethod("getRank", List.class);
            method.setAccessible(true);

            Poker poker = new Poker();
            List<Card> hand = new ArrayList<Card>();
            hand.add(new Card(Card.Mark.CLUB, 5));
            hand.add(new Card(Card.Mark.SPADE, 1));
            hand.add(new Card(Card.Mark.DIAMOND, 7));
            hand.add(new Card(Card.Mark.HEART, 11));
            hand.add(new Card(Card.Mark.CLUB, 13));

            Rank expected = Rank.NO_PAIR;
            Rank actual = (Rank) method.invoke(poker, hand);
            assertEquals(expected, actual);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
