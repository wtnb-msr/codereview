import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    /** 受付可能文字 */
    private static final Pattern acceptablePtn = Pattern.compile("");
    private static final Pattern numberPtn = Pattern.compile("a*b");
    private static final Pattern operatorPtn = Pattern.compile("a*b");
    private static final char ADD = '+';
    private static final char SUB = '-';
    private static final char MUL = '*';
    private static final char DIV = '/';
    private static final char LEFT_PAR = '(';
    private static final char RIGHT_PAR = ')';

    /**
     * 使用方法を表示する
     */
    private static void printUsage() {
        System.out.println("Usage : ");
    }

    /**
     * 引数から条件を設定し、計算機を起動する.
     * 
     * @param args
     */
    public static void main(String[] args) {

        if (args.length == 0) {
            printUsage();
            return;
        }

        int index = 0;
        boolean isDouble = false;
        if ("-d".equals(args[index])) {
            isDouble = true;
            index++;
        }

        try {
            Calculator calc = new Calculator();
            calc.execute(isDouble, args[index]);
        } catch (Exception e) {
            //
            e.printStackTrace();
        }
    }

    /**
     * 指定された条件で、与えられた式を計算する
     * 
     * @param isDouble
     * @param formula
     */
    private void execute(boolean isDouble, String formula) {
        try {
            // 引数をパース
            formula = formula.replaceAll(" ", "");
            List<String> list = parseFormula(formula);

            System.out.println(list);
            // 結果を出力
            Fraction ans = calcWithFraction(list);
            System.out.println("ans : " + ans.toString(isDouble));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 与えらた数式をパースし、再帰的に演算記号による数式の分割を行い、逆ポーランド記法に則ったリストを返す.
     * e.g. (1+2)*((3-4)/5) => [1, 2, +, 3, 4, -, 5, /, *]
     * @param formula
     * @return
     */
    private List<String> parseFormula(String formula) {

        // 外側の '(', ')' を取り除く. e.g. (1+2) => 1+2
        formula = trimParthesis(formula);

        // '+', '-' 演算子で数式を分割
        int index = -1;
        index = indexOfOperators(formula, ADD, SUB);
        if (index > 0) {
            return splitFormula(formula, index);
        }

        // '*', '/' 演算子で数式を分割
        index = indexOfOperators(formula, MUL, DIV);
        if (index > 0) {
            return splitFormula(formula, index);
        }

        // 分割できなければ、そのままリストにして返す
        List<String> list = new ArrayList<String>();
        list.add(formula);
        return list;
    }

    /**
     * 外側が '(', ')' で囲まれていれば、それを取り除く.
     * e.g. ((1+2)*(3+4)) => (1+2)*(3+4)
     * @param formula
     * @return
     */
    private String trimParthesis(String formula) {
        int length = formula.length();
        
        // 両端が '(' と ')' でなければトリムしない
        if (formula.charAt(0) != LEFT_PAR || formula.charAt(length - 1) != RIGHT_PAR) {
            return formula;
        }
        
        // 両端の '(' ')' をトリムしても数式として問題ないかチェック
        // ダメな例. (1+2)*(3+4) =>  1+2)*(3+4
        int parCount = 0;
        for (int i = 1; i < length - 1; i++) {
            char c = formula.charAt(i);
            if (c == LEFT_PAR) {
                parCount++;
                continue;
            }
            if (c == RIGHT_PAR) {
                parCount--;
                continue;
            } 
            if (parCount < 0) {
                return formula;
            }
        }
        return formula.substring(1, length - 1);
    }

    /**
     * 指定された演算記号を後ろから探索し、最初に発見されたインデックスを返す.
     * 見つからなければ、-1 を返す
     * e.g. formula = (1+2)*(3+4), ops = (*,/) => 5
     * @param formula
     * @param ops
     * @return
     */
    private int indexOfOperators(String formula, char... ops) {
        int length = formula.length();
        int parCount = 0;
        for (int i = length - 1; i >= 0; i--) {
            char c = formula.charAt(i);
            if (c == RIGHT_PAR) {
                parCount++;
                continue;
            }
            if (c == LEFT_PAR) {
                parCount--;
                continue;
            }
            if (parCount == 0) {
                for (char op : ops) {
                    if (c == op) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * 指定されたインデックスで文字列を分割し、逆ポーランド記法に変換したリストを返す 
     * @param formula
     * @param index
     * @return
     */
    private List<String> splitFormula(String formula, int index) {
        if (formula == null) {
            throw new IllegalArgumentException();
        }
        int length = formula.length();
        if (index < 0 && length < length) {
            throw new IllegalArgumentException();
        }
        List<String> list = new ArrayList<String>();
        String forward = formula.substring(0, index);
        String backward = formula.substring(index + 1, length);
        list.addAll(parseFormula(forward));
        list.addAll(parseFormula(backward));
        list.add(String.valueOf(formula.charAt(index)));
        return list;
    }

    /**
     * 
     * @param isDouble
     * @param list
     * @return
     */
    private Fraction calcWithFraction(List<String> list) {
       
        Stack<Fraction> stack = new Stack<Fraction>();
        for (String ele : list) {
            try {
                Fraction fraction = new Fraction(Integer.parseInt(ele));
                stack.add(fraction);
                continue;
            } catch (NumberFormatException e) {
                Fraction i = stack.pop();
                Fraction j = stack.pop();
                switch (ele.charAt(0)) {
                case (ADD):
                    j.add(i);
                    break;
                case (SUB):
                    j.sub(i);
                    break;
                case (MUL):
                    j.mul(i);
                    break;
                case (DIV):
                    j.div(i);
                    break;
                }
                stack.add(j);
            }
        }
        return stack.pop();   
    }
}
