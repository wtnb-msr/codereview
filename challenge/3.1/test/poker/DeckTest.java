package poker;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DeckTest {


	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
    @Test
    public void Deckに負の数を渡すと例外を投げる() throws Exception {
    	expectedException.expect(IllegalArgumentException.class);
    	expectedException.expectMessage("負の数が渡されました。");
		new Deck(-1);
    }
	
	@Test 
	public void Deckは基本52枚に指定枚数のジョーカーを追加して生成される() {
	    int numJoker = 2;
	    Deck deck = new Deck(numJoker);
	    int actual = deck.getNumCards();
	    int matcher = 52 + numJoker;
	    assertThat(actual, is(matcher));
	}
		
	@Test	
    public void getNumCardsはデッキの残り枚数を返す() {
		int numJoker = 1;
		int numDraw = 20;
		Deck deck = new Deck(numJoker);
		try {
			deck.drawCards(numDraw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int actual = deck.getNumCards();
        int matcher = 52 + numJoker - numDraw;

        assertThat(actual, is(matcher));
    }
	
    @Test
    public void drawCardsに負の数を渡すと例外を投げる() throws Exception {
    	expectedException.expect(IllegalArgumentException.class);
    	expectedException.expectMessage("不正な値です。");
		Deck deck = new Deck(1);
		deck.drawCards(-1);
    }

    @Test
    public void drawCardsは残り枚数より大きな数を引くと例外を投げる() throws Exception {
    	expectedException.expect(IllegalArgumentException.class);
    	expectedException.expectMessage("そんなに引けません。");
		Deck deck = new Deck(1);
		deck.drawCards(54);
    }
    
    @Test
    public void Deckはジョーカに負の枚数を指定すると例外を投げる() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("負の数が渡されました。");
        new Deck(-1);
    }
}
