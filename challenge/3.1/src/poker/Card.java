package poker;

/**
 * トランプのカードを表現するクラス
 * @author a13613
 */
public class Card implements Comparable<Card> {
	
	/**
	 * トランプのマークを表現する列挙型
	 * @author a13613
	 */
	public enum Mark { 
		CLUB("クラブ"), DIAMOND("ダイヤ"), HEART("ハート"), SPADE("スペード"), JOKER("ジョーカー");
		
		private final String name;
		
		private Mark(String name) {
			this.name = name;
		}
		
		public String getMarkName() {
			return name;
		}
	}
	
	/**
	 * トランプの数字の最小値と最大値
	 */
	public static final int MIN_NUMBER = 1;
	public static final int MAX_NUMBER = 13;
	
	/**
	 * トランプの数字から文字列を得る
	 */
	public static final String[] NUM_STR = 
	    {"", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

	/**
	 * トランプのマークと数字を表すフィールド
	 */
	private Mark mark;
	private int number;

	/**
	 * トランプを生成するコンストラクタ
	 * @param m マーク
	 * @param n 数字 (1~13)
	 */
	public Card(Mark m, int n) {
	    if (n < MIN_NUMBER || MAX_NUMBER < n) {
	        throw new IllegalArgumentException("範囲外の数字です。");
	    }
		this.mark = m;
		this.number = n;
	}
	
	/**
	 * トランプのマーク Mark を返す
	 * @return トランプのマーク
	 */
	public Mark getMark() {
		return mark;
	}
	
	/**
	 * トランプの数字 Number を返す
	 * @return トランプの数字
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 * 2つのカードを比較します。比較は、マークが優先され、次に数字によって比較されます。
	 * @return 同等のカードであれば0。カードのランクが引数カードのランクより小さい場合は、0より小さい値。大きい場合は、0より大きい値。
	 */
	@Override
	public int compareTo(Card o) {
		Card other = (Card) o;
		if (this.mark != other.mark) {
			return this.mark.ordinal() - other.mark.ordinal();
		} else {
			return this.number - other.number;
		}
	}
	
	/**
	 * トランプの情報を文字列で返します
	 * @return このカードを表すマークと数字の文字列
	 */
	@Override
	public String toString() {
		if (mark == Mark.JOKER) {
			return mark.getMarkName();
		} else {
			return mark.getMarkName() + "の" + NUM_STR[number];
		}
	}

	/**
	 * このカードのハッシュコードを返します (eclipse による自動生成)
	 * @return このオブジェクトのハッシュコード値
	 */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mark == null) ? 0 : mark.hashCode());
        result = prime * result + number;
        return result;
    }

    /**
     * このカードと指定されたオブジェクトを比較します (eclipse による自動生成)
     * @param obj - この Card と比較されるオブジェクト
     * @return 指定されたオブジェクトがこの Card と同様のマークと数字を持つ場合は true、そうでない場合は false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Card other = (Card) obj;
        if (mark != other.mark)
            return false;
        if (number != other.number)
            return false;
        return true;
    }	
	
	
}
