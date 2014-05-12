import java.util.Random;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * 対話型じゃんけんをするクラス
 */
public class RPS {

    /**
     * じゃんけんの勝ち負けを表す定数文字列
     */
    private static final String STAT_WIN  = "勝ち";
    private static final String STAT_LOSE = "負け";
    private static final String STAT_DRAW = "あいこ";
    private static final String STAT_NONE = "";

    /**
     * じゃんけんの手を表現する列挙型
     */
    public enum HAND {
        NONE("なし", 0), ROCK("グー", 1), SCISSORS("チョキ", 2), PAPER("パー", 3);

        private String name;
        private int value;
        
        private HAND(String n, int v) {
            name = n;
            value = v;
        }
        
        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }
       
        /**
         * 引数の値（1-3）から対応する手を返す
         * @param じゃんけんの手に対応する値（1-3）
         * @return 値に対応する手
         */
        public static HAND getHandFromValue(int value) {
            switch (value) {
                case 1: 
                    return HAND.ROCK;
                case 2: 
                    return HAND.SCISSORS;
                case 3: 
                    return HAND.PAPER;
                default:
                    return HAND.NONE;
            }
        }
    }

    /**
     * ユーザが出す手を標準入力から取得する
     */
    private HAND getUserHand() throws IOException {
        HAND user_hand = HAND.NONE;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (user_hand==HAND.NONE) {
                System.out.print("出す手を入力 => ");
                int input_num;
                try {
                    input_num = Integer.parseInt(br.readLine());
                } catch (NumberFormatException e) {
                    continue;
                }
                user_hand = HAND.getHandFromValue(input_num);
            }
        } catch (IOException e) {
            // 入出力エラー
            throw e;
        }
        return user_hand;
    }

    /**
     * コンピュータが出す手を乱数で取得
     */
    private HAND getComHand() {
        int com_random = (int)Math.random() * 3 + 1;
        HAND com_hand = HAND.getHandFromValue(com_random);
        return com_hand;
    }

    /**
     * 手を比較して、勝敗を返す
     */
    private String judge(HAND user_hand, HAND com_hand) {
        int user_value = user_hand.getValue();
        int com_value = com_hand.getValue();
        int result = (user_value + 3 - com_value) % 3;
        switch (result) {
            case 0:
                return STAT_DRAW;
            case 1:
                return STAT_LOSE;
            case 2:
                return STAT_WIN;
            default:
                return STAT_NONE;
        }
    }

    /**
     * じゃんけんを行うメソッド
     */
    private void startRPS() throws IOException {
        // 挨拶
        System.out.println("じゃんけんをしましょう！");
        System.out.println("1: グー、2: チョキ、3: パー です。");

        // 繰り返しの回数と、初期状態を宣言
        int round = 0;
        String result = STAT_NONE;

        // 決着がつくまで繰り返す
        while (result != STAT_WIN || result == STAT_LOSE) {

            // 掛け声
            if (round == 0) {
                System.out.println("じゃーんけーん・・");
            } else {
                System.out.println("あーいこーで・・");
            }

            // 出す手を取得する
            HAND user_hand = getUserHand();
            HAND com_hand = getComHand();

            // 掛け声
            if (round == 0) {
                System.out.println("ぽん！");
            } else {
                System.out.println("しょ！");
            }

            // 勝敗を決める
            result = judge(user_hand, com_hand);
            System.out.println("あなた：" + user_hand.getName() + "、コンピュータ：" + com_hand.getName());

            // 勝敗が決まったら結果を出力し、終了
            if (result == STAT_WIN || result == STAT_LOSE) {
                System.out.println("あなたの" + result + "です！");
                break;
            } else {
                // あいこであれば、次のラウンドへ
                round++;
            }
        }
    }

    /**
     * メイン
     */
    public static void main(String args[]){
        try {
            RPS rps = new RPS();
            rps.startRPS();
        } catch (IOException e) {
            // 入出力エラー
            e.printStackTrace();
        }
    }
}
