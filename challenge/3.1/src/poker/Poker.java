package poker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

/**
 * ポーカゲームを行うクラス main を実行すると、Poker オブジェクトが生成し、execute でゲームを開始します。
 * 
 * @author a13613
 * 
 */
public class Poker {

    /**
     * 手持ちのカード枚数を定義
     */
    public static final int NUM_HAND = 5;

    /**
     * トランプの山札 (デッキ) ジョーカを含まない
     */
    private Deck deck = new Deck(0);

    /**
     * ポーカーの役と強さを表す列挙型 ランクが小さい方が役として強い
     * 
     * @author a13613
     */
    public enum Rank {
        ROYAL_STRAIGHT_FLUSH("ロイヤルストレートフラッシュ", 1), 
        STRAIGHT_FLUSH("ストレートフラッシュ",2), FOUR_OF_A_KIND("フォーカード", 3), 
        FULL_HOUSE("フルハウス", 4), FLUSH("フラッシュ", 5), STRAIGHT("ストレート", 6), 
        THREE_OF_A_KIND("スリーカード", 7), TWO_PAIR("ツーペア", 8),
        ONE_PAIR("ワンペア", 9), NO_PAIR("ノーペア", 10);

        private String rankName;
        private int rank;

        private Rank(String name, int rank) {
            this.rankName = name;
            this.rank = rank;
        }

        /**
         * 役の日本語名を返す
         * 
         * @return 役の日本語名
         */
        public String getRankName() {
            return rankName;
        }

        /**
         * 役のランクを返す
         * 
         * @return 役のランク
         */
        public int getRankNumber() {
            return rank;
        }
    }

    /**
     * ユーザからの入力をパースし、ユーザが交換したいカードのインデックスをリストにして返す
     * 
     * @return 交換するカードのインデックスのリスト
     */
    private List<Integer> getInput() throws IOException {
        List<Integer> list = new ArrayList<Integer>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            // 入力が不正でないかチェックするフラグ
            boolean isInvalid = true;

            // 不正でない入力を得るまで、入力させる
            input: while (isInvalid) {
                // 読み込み前の初期化
                list.clear();
                isInvalid = false;

                // 入力を得る
                String input = br.readLine();

                if (input.length() == 0) {
                    isInvalid = true;
                    continue input; // 再入力
                }

                // 入力をパースして、リストに追加する
                for (int i = 0; i < input.length(); i++) {
                    int number = input.charAt(i) - 48;
                    if (list.contains(number)) {
                        System.out.println("入力に重複があります。");
                        isInvalid = true;
                        continue input;
                    }
                    list.add(number);
                }

                // 入力が不正でないかチェックする
                if (list.size() > 1 && list.contains(0)) {
                    System.out.println("0番のカードはありません。");
                    isInvalid = true;
                    continue input;
                }
                for (int index : list) {
                    if (index < 0 || 9 < index) {
                        System.out.println("入力は数値のみです。");
                        isInvalid = true;
                        continue input;
                    } else if (NUM_HAND < index) {
                        System.out.println(index + "番のカードはありません。");
                        isInvalid = true;
                        continue input;
                    }
                }

                if (!isInvalid) {
                    break input;
                }
            }
            br.close();
        } catch (IOException e) {
            // 入出力エラー
            throw e;
        }
        return list;
    }

    /**
     * 手札を表示する
     * 
     * @param hand
     */
    private void printHandCards(List<Card> hand) {
        for (int i = 0; i < NUM_HAND; i++) {
            System.out.println((i + 1) + ":" + hand.get(i).toString());
        }
    }

    /**
     * 指定された番号（インデックス）のカードを交換する
     * 
     * @param hand
     * @param indices
     */
    private void exchangeHandCards(List<Card> hand, List<Integer> indices) {

        // 入力が 0 のみであれば、交換しない
        if (indices.size() == 1 && indices.get(0) == 0) {
            return;
        }
        
        // デッキからカードを引き、手札と交換
        List<Card> tmpCards = deck.drawCards(indices.size());
        for (int i = 0; i < indices.size(); i++) {
            int index = indices.get(i) - 1;
            hand.set(index, tmpCards.get(i));
        }
        
        // 手札をソート
        Collections.sort(hand, new NumberFirstSort());
    }

    /**
     * 手札の役がフラッシュかどうか (ジョーカーを許容しない)
     * 
     * @param hand
     *            手札のカードのリスト
     * @return 手札のカードのマークがすべて同じであれば true、そうでなければ false
     */
    private boolean isFlush(List<Card> hand) {

        if (hand.size() == 0) {
            return false;
        }

        Card.Mark preMark = hand.get(0).getMark();
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).getMark() != preMark) {
                return false;
            }
        }
        return true;
    }

    /**
     * 手札の役がロイヤル (10, J, Q, K, A) の並びかどうか カードの枚数は5枚とする (ジョーカーは許容しない)
     * 
     * @param hand
     * @return 特定の並びでカードの数字が並んでいれば true、そうでなければ false
     */
    private boolean isRoyal(List<Card> hand) {

        if (hand.size() != NUM_HAND) {
            return false;
        }

        // 先頭の数字が 1 でなければ false
        if (hand.get(0).getNumber() != 1) {
            return false;
        }
        // 2番目の数字が 10 でなければ false
        if (hand.get(1).getNumber() != 10) {
            return false;
        }

        // 3番目以降の数字の並びを確認する
        int preNumber = hand.get(1).getNumber();
        for (int i = 2; i < NUM_HAND; i++) {
            int number = hand.get(i).getNumber();
            if (number != preNumber + 1) {
                return false;
            }
            preNumber = number;
        }
        return true;
    }

    /**
     * 手札の役がストレートかどうか（ジョーカーを許容しない）
     * 
     * @param hand
     * @return 手札のカードの数字が隙間なく並んでいれば true、そうでなければ false
     */
    private boolean isStraight(List<Card> hand) {

        if (hand.size() == 0) {
            return false;
        }

        int preNumber = hand.get(0).getNumber();
        for (int i = 1; i < hand.size(); i++) {
            int number = hand.get(i).getNumber();
            if (number != preNumber + 1) {
                return false;
            }
            preNumber = number;
        }
        return true;
    }

    /**
     * 手札に含まれるカードの数字が何種類あるか返す
     * 
     * @param hand
     * @return 種類数
     */
    private int getNumKindNumber(List<Card> hand) {
        Set<Integer> set = new HashSet<Integer>();
        for (Card card : hand) {
            set.add(card.getNumber());
        }
        return set.size();
    }

    /**
     * 重複した数字を持つカードの最大枚数を返す
     * 
     * @param hand
     * @return 重複した数字を持つカードの最大枚数
     */
    private int getMaxNumOfKind(List<Card> hand) {
        int[] countArray = new int[Card.MAX_NUMBER];
        for (Card card : hand) {
            countArray[card.getNumber() - 1]++;
        }
        int maxSame = 0;
        for (int i = 0; i < Card.MAX_NUMBER; i++) {
            if (countArray[i] > maxSame) {
                maxSame = countArray[i];
            }
        }
        return maxSame;
    }

    /**
     * 手札の役の文字列を返す (ジョーカーは許容されない)
     * 
     * @param hand
     * @return 役を返す
     */
    private Rank getRank(List<Card> hand) {
        // 10, J, Q, K, A となっているかどうか
        boolean isRoyal = isRoyal(hand);

        // すべてのカードが同じマークかどうか
        boolean isFlush = isFlush(hand);

        if (isRoyal && isFlush) {
            return Rank.ROYAL_STRAIGHT_FLUSH;
        }

        // 数字が並んでいるかどうか
        boolean isStraight = isRoyal ? true : isStraight(hand);

        if (isStraight && isFlush) {
            return Rank.STRAIGHT_FLUSH;
        }

        // 同数字のカード枚数の最大値を取得
        int numMax = getMaxNumOfKind(hand);

        if (numMax == 4) {
            return Rank.FOUR_OF_A_KIND;
        }

        // 手札に含まれる数字の種類数を取得
        int numKind = getNumKindNumber(hand);

        if (numKind == 2 && numMax == 3) {
            return Rank.FULL_HOUSE;
        }
        if (isFlush) {
            return Rank.FLUSH;
        }
        if (isStraight) {
            return Rank.STRAIGHT;
        }
        if (numMax == 3) {
            return Rank.THREE_OF_A_KIND;
        }
        if (numKind == 3) {
            return Rank.TWO_PAIR;
        }
        if (numKind == 4) {
            return Rank.ONE_PAIR;
        }
        return Rank.NO_PAIR;
    }

    /**
     * ポーカーを実行するメソッド
     * 
     * @throws IOException
     */
    public void execute() throws IOException {
        try {
            // 遊び方
            System.out.println("交換するカードの番号を入力してください(例：135)。");
            System.out.println("0を入力するとカードを交換しません。");

            // ジョーカなしでデッキを生成
            Deck deck = new Deck(0);

            // デッキから手札を引き、手札をソート
            List<Card> hand = deck.drawCards(NUM_HAND);
            Collections.sort(hand, new NumberFirstSort());

            // 手札を表示
            printHandCards(hand);

            // 交換する手札の番号を取得
            List<Integer> exchangeIndices = getInput();

            // 手札を交換する
            exchangeHandCards(hand, exchangeIndices);

            // 手札を表示
            printHandCards(hand);

            // 役を取得
            Rank result = getRank(hand);

            // 結果を表示
            System.out.println("役は " + result.getRankName() + " でした。");

        } catch (IOException e) {
            // 入出力エラー
            throw e;
        }
    }

    /**
     * ポーカーを起動
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            Poker poker = new Poker();
            poker.execute();
        } catch (IOException e) {
            // 入出力エラー
            e.printStackTrace();
        }
    }

    /**
     * 数字を優先し手札をソートするための、Comparatorクラス。
     */
    class NumberFirstSort implements Comparator<Card> {
        @Override
        public int compare(Card o1, Card o2) {
            if (o1.getNumber() != o2.getNumber()) {
                return o1.getNumber() - o2.getNumber();
            } else {
                return o1.getMark().ordinal() - o2.getMark().ordinal();
            }
        }
    }
}
