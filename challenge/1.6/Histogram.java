import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.IllegalArgumentException;

/**
 * ヒストグラムを表示するクラス.
 * @author a13613
 */
public class Histogram {

    // 出力文字の定義
    private static final String STAR = "*";
    private static final String SPACE = " ";
    private static final String LINE_SEP = System.lineSeparator();

    /**
     * 使用方法を表示する.
     */
    private static void printUsage() {
        System.out.println("グラフにプロットする値を引数に指定してください。");
        System.out.println("例）java Histogram 1 2 3 3 2 1");
    }

    /**
     * String の数値をパースし、リストとして返す.
     * @param args 数字の配列
     * @return 整数のリスト
     */
    private List<Integer> parseArgs(String[] args) {
        List<Integer> histList = new ArrayList<Integer>();
        try {
            for (String arg : args) {
                int value = Integer.parseInt(arg);
                if (value < 0) {
                    throw new IllegalArgumentException("引数に指定できるのは正の数だけです。");
                }
                histList.add(value);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("引数に指定できるのは数値のみです。", e);
        }
        return histList;
    }

    /*
     * 引数の最大値を基に、フォーマットされたヒストグラムを出力する.
     * @param histList 数値のリスト
     */
    private void printHistogram(List<Integer> histList) {
         // リストの最大値を取得
        int maxValue = Collections.max(histList);

        // フォーマット
        StringBuilder sb = new StringBuilder();
        for (int line = maxValue; line > 0; line--) {
            for (int value : histList) {
                if (value >= line) {
                    sb.append(STAR);
                } else {
                    sb.append(SPACE);
                }
            }
            sb.append(LINE_SEP);
        } 

        // まとめて出力
        System.out.print(sb.toString());
    }

    /**
     * 実行時の引数で指定された値のヒストグラムをフォーマットして表示する.
     * @param args スペース区切りの数列
     */
    private void runHistogram(String[] args) {
        try {
            // 引数をパースし、数値のリストを取得
            List<Integer> list = parseArgs(args);

            // 結果を出力
            printHistogram(list);

        } catch (IllegalArgumentException e) {
            // 不正な引数
            System.out.println(e.getMessage());
        }
    }

    /**
     * 実行時の引数の値をもとに、ヒストグラムの表示を行う.
     * @param args スペース区切りの数列
     */
    public static void main(String[] args) {
        
        // 引数がない場合、使用方法を表示
        if (args.length == 0) {
            printUsage();
            return;
        }

        Histogram hist = new Histogram();
        hist.runHistogram(args);
    }
}
