package intermediate;

import java.util.List;
import java.util.ArrayList;

public class Intermediate2 {

	public static int[] uniq(int[] src) {
		
		if (src == null) {
			throw new NullPointerException("nullが渡されました");
		}

		if (src.length == 0) {
		    return new int[0];
		}
	
		// 直前の要素との重複を許さないリスト
		List<Integer> uniqList = new ArrayList<Integer>();

		// 先頭の値を追加
		uniqList.add(src[0]);
		int preValue = src[0];
		
		// 重複しない値のみ追加する
		for (int i = 1; i < src.length; i++) {
			if (src[i] != preValue) {
				uniqList.add(src[i]);
				preValue = src[i];
			}
		}

		// int 型の配列を作成し、コピーしたものを返す
		int[] uniqSrc = new int[uniqList.size()];
		for (int i = 0; i < uniqList.size(); i++) {
		    uniqSrc[i] = uniqList.get(i);
		}
		return uniqSrc;
	}
}
