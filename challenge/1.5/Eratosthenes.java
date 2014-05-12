import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.BitSet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * 指定された値以下の素数リストを出力するクラス
 */
public class Eratosthenes {

    // 受付可能文字のパターン
    private Pattern acceptablePtn;

    /**
     * 受付可能なユーザの入力を取得し、自然数を返す
     * @return 入力された自然数
     * @throws IOException - 入出力エラー
     * @throws IllegalInputException - 不正な入力フォーマット
     */
    private int getInput() throws IOException, IllegalInputException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        if (!acceptablePtn.matcher(input).find()) {
            throw new IllegalInputException("不正な値が入力されました。");
        }
        int number = Integer.parseInt(input);
        if (number < 0) {
            throw new IllegalInputException("自然数を入力してください。");
        }
        return number;
    }

   /**
     * エラトステネスのふるい
     * 素数でないもののビットを下げる
     * @param bitset - 素数管理のビットマップ
     * @param maxBound - 探索の最大値
     */
    private void sievePrime(BitSet bitset, int maxBound) {
        // 平方根を取得
        int rootBound = (int) Math.sqrt(maxBound);
        
        // ふるい落とし - 素数でない整数のビットを下げる
        for (int i = 2; i <= rootBound; i++) {
            // 素数であれば、すべての倍数のビットを下げる
            if (bitset.get(i)) {
                for (int j = i * i; j <= maxBound; j += i) {
                    bitset.set(j, false);
                }
            }
        }
    }

    /**
     * 探索した素数を出力する
     * @param bitset - 探索済み素数のビットマップ
     */
    private void printResult(BitSet bitset) {
        StringBuilder sb = new StringBuilder();
        // true ビットを持つインデックス（素数）をすべて出力
        for (int i = bitset.nextSetBit(0); i >= 0; i = bitset.nextSetBit(i+1)) {
            sb.append(i).append(" ");
        }
        System.out.println(sb.toString());
    }

 
    /**
     * 素数を計算して出力するメソッド
     */
    public void calcPrime() throws IOException {
        try {
            // 説明
            System.out.println("素数リストを出力するプログラムです。");
            System.out.println("リストの最大値を整数で入力してください。");
            
            // 受付可能文字を設定
            acceptablePtn = Pattern.compile("^[0-9]*$");
            
            // 素数探索の最大値を取得
            int maxBound = getInput();
       
            // 入力が 2 未満であれば、終了
            if (maxBound < 2) {
                return;
            }

            // 素数管理のビットマップを作成
            // インデックスで素数を表す
            BitSet bitset = new BitSet(maxBound);

            // 2から探索最大値までのビットを立てる
            bitset.set(2, maxBound + 1, true);

            // ふるい
            sievePrime(bitset, maxBound);

            // 結果を出力
            printResult(bitset);
   
        } catch (IllegalInputException e) {
            // 不正な値を検出
            System.out.println(e.getMessage());
        } catch (IOException e) {
            // 入出力エラー
            throw e;
        }

    }

    /**
     * 素数を列挙する
     */ 
    public static void main(String[] args) {
        try {
            Eratosthenes eratosthenes = new Eratosthenes();
            eratosthenes.calcPrime(); 
        } catch (Exception e) {
            // 例外終了
            e.printStackTrace();
        }
    }

    /**
     * 不正な入力に対する例外クラス
     */
    class IllegalInputException extends Exception {
        public IllegalInputException(String str) {
            super(str);
        }
    }
}

