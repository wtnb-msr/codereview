package poker;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import poker.Card.Mark;

public class CardTest {

    @Test(expected=IllegalArgumentException.class)
    public void Cardは範囲外の数値を渡すとIllegalArgumentExceptionを投げる() {
        new Card(Mark.CLUB, 0);
        new Card(Mark.CLUB, 14);
    }
    
    @Test
    public void 同じカードのランクは等しい() {
        Card card1 = new Card(Mark.DIAMOND, 1);
        Card card2 = new Card(Mark.DIAMOND, 1);
        assertThat(card1, is(card2));
    }
    
    @Test
    public void sortはカードを昇順に並べる() {
        
        // 比較用のリストを作成
        List<Card> matcher = new ArrayList<Card>();
        List<Card> actual = new ArrayList<Card>();
        
        // 昇順に並んだデッキを作成
        for (Mark mark : Mark.values()) {
            if (mark==Mark.JOKER) continue;
            for (int number = Card.MIN_NUMBER; number < Card.MAX_NUMBER; number++) {
                matcher.add(new Card(mark, number));
                actual.add(new Card(mark, number));
            }
        }
        matcher.add(new Card(Mark.JOKER, Card.MAX_NUMBER));
        actual.add(new Card(Mark.JOKER, Card.MAX_NUMBER));
        
        // シャッフル & ソート
        Collections.shuffle(matcher);
        Collections.sort(matcher);
        
        // チェック
        for (int i = 0; i < matcher.size(); i++) {
            Card actualCard = actual.get(i);
            Card matcherCard = matcher.get(i);
            assertThat(actualCard, is(matcherCard));
        }
    }
}
