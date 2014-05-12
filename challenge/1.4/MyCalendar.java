import java.util.Calendar;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


/**
 * ユーザ指定の年月からフォーマットされたカレンダーを出力するクラス
 */
public class MyCalendar {

    // 受付可能文字のパターン
    private Pattern acceptablePtn = Pattern.compile("^[0-9]*$");

    /**
     * 受付可能なユーザの入力を取得し、年を返す 
     * @return 入力年
     */
    private int getInputYear() throws IllegalInputException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        if (!acceptablePtn.matcher(input).find()) {
            throw new IllegalInputException("数値の形式が正しくありません。");
        }
        int year = Integer.parseInt(input);
        if (year < 1) {
            throw new IllegalInputException("年は１以上の自然数で入力してください。");
        }
        return year;        
    }

    /**
     * 受付可能なユーザの入力を取得し、月を返す
     * @return 入力月 
     */
    private int getInputMonth() throws IllegalInputException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        if (!acceptablePtn.matcher(input).find()) {
            throw new IllegalInputException("数値の形式が正しくありません。");
        }
        int month = Integer.parseInt(input);
        if (month < 1 || 12 < month) {
            throw new IllegalInputException("月は１～１２の間で入力してください。");
        }
        return month;        
    }

    /**
     * 指定された年月のカレンダーをフォーマットして出力
     * @param year 指定年
     * @param month 指定月
     */
    private void printCalendar(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        
        // ヘッダ
        sb.append(String.format("%d年 %d月\n", year, month));
        sb.append("日 月 火 水 木 金 土\n");

        // 指定された年月をカレンダーにセットし、1日の曜日を取得
        calendar.set(year, month - 1, 1);
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        // 月末の日付を取得
        int numDays = calendar.getActualMaximum(Calendar.DATE);

        // 週末に改行を行うためのカウンタ
        int dayCount = 0;

        // 最初の曜日まで空白で埋める
        for (int i = 0; i < day - 1; i++) {
            sb.append("   ");
            dayCount++;
        }

        // すべての日付をフォーマット
        for (int i = 1; i <= numDays; i++) {
            sb.append(String.format("%2d ", i));
            // 右端まできたら改行する
            dayCount++;
            if (dayCount % 7 == 0) {
                sb.append("\n");
            }
        }

        // まとめて出力
        System.out.println(sb.toString());
    }


    /**
     * 入力とカレンダーの出力を行う
     * @throws IOException 入出力エラー
     */
    private void runCalendar() throws IOException {
        try {
            System.out.println("カレンダーを出力します");

            // 年を取得
            System.out.println("年を入力してください（例：2014）");
            int year = getInputYear();

            // 月を取得
            System.out.println("月を入力してください（例：4）");
            int month = getInputMonth();

            // カレンダーを出力
            printCalendar(year, month);

        } catch (IllegalInputException e) {
            // 不正な入力
            System.out.println(e.getMessage());
        } catch (IOException e) {
            // 入出力エラー
            throw e;    
        }
    }
    
    /**
     * カレンダーを表示する
     */
    public static void main(String[] args) {
        try {
            MyCalendar cal = new MyCalendar();
            cal.runCalendar();
        } catch (IOException e) {
            // 入出力エラー
            e.printStackTrace();
        }
    }

    /**
     * 不正な入力に対する例外
     */
    class IllegalInputException extends Exception {
        public IllegalInputException(String str) {
            super(str);
        }
    }

}
