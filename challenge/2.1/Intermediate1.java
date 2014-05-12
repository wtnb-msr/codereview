package intermediate;

import java.lang.StringBuilder;

/**
 * 2進数に変換するクラス
 * @author a13613
 *
 */
public class Intermediate1 {

	/**
	 * 引数で与えられた数値を2進数表記の文字列にして返す
	 * @param src 10進数
	 * @return 2進数に変換後の文字列
	 */
	public static String toBinaryString(int src) {
		
		if (src < 0) {
			throw new IllegalArgumentException("非負整数を入力してください");
		}

		if (src == 0) {
			return "0";
		}
		
		StringBuilder sb = new StringBuilder();
		while (src > 0) {
			sb.insert(0, Integer.toString(src & 1));
			src >>>= 1;
		}
		return sb.toString();
	}
}
