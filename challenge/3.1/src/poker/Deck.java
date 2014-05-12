package poker;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import poker.Card.Mark;

/**
 * トランプのデッキを表現するクラス
 * 基本52枚にジョーカーが指定枚数追加される
 * @author a13613
 */
public class Deck {
	
    // デッキのトップカードを示すインデックス
	private static final int TOP = 0;
	
	// デッキを表現するカードのリスト
	private List<Card> deckCards = new ArrayList<>();
	
	/**
	 * 指定枚数のジョーカを追加したデッキを生成するコンストラクタ
	 * @param numJoker ジョーカーの枚数
	 */
	public Deck(int numJoker) {
	    
	    if (numJoker < 0) {
	        throw new IllegalArgumentException("負の数が渡されました。");
	    }
	    
		// 基本カード52枚を追加
		for (Mark mark : Mark.values()) {
			if (mark == Mark.JOKER) continue;
			for (int number = Card.MIN_NUMBER; number <= Card.MAX_NUMBER; number++) {
				deckCards.add(new Card(mark, number));
			}
		}
		
		// ジョーカーを指定枚数追加
		for(int i = 0; i < numJoker; i++) {
			deckCards.add(new Card(Mark.JOKER, Card.MAX_NUMBER));
		}
		
		// デッキをシャッフルして保持
		Collections.shuffle(deckCards);
	}

	/**
	 * デッキのカード枚数を取得する
	 * @return デッキの残りカード枚数
	 */
	public int getNumCards() {
		return deckCards.size();
	}
	
	/**
	 * 指定枚数のカードをリストにして返します
	 * @param numDraw - デッキからカードを引く枚数
	 * @return デッキから引かれたカードのリスト
	 * @throws IllegalArgumentException - 残りカード枚数以上が指定されたときに例外を投げる
	 */
	public List<Card> drawCards(int numDraw) {
		
		if (numDraw > getNumCards()) {
			throw new IllegalArgumentException("そんなに引けません。");
		}
		
		if (numDraw < 0) {
			throw new IllegalArgumentException("不正な値です。");
		}
		
		// デッキのトップから指定枚数ドロー
		List<Card> drawnCards = new ArrayList<Card>();
		for (int i = 0; i < numDraw; i++) {
		    drawnCards.add(deckCards.get(TOP));
		    deckCards.remove(TOP);
		}
		
		return drawnCards;
	}
	
}
