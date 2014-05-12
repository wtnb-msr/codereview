/**
 * 分数を扱うことの出来るクラス
 * @author a13613
 *
 */
public class Fraction {

    // 分子と分母を整数で定義
    private int numer = 0;
    private int denon = 1;
    
    public int getNumer() {
        return numer;
    }
    
    public int getDenon() {
        return denon;
    }
    
    public Fraction(int i) {
        numer = i;
        denon = 1;
    }
    
    /**
     * 加算
     * @param other
     */
    public void add(Fraction other) {
        this.numer = this.denon * other.getNumer() + this.numer * other.getDenon();
        this.denon = this.denon * other.getDenon();
    }
    
    /**
     * 減算
     * @param other
     */
    public void sub(Fraction other) {
        this.numer = this.numer * other.getDenon() - this.denon * other.getNumer();
        this.denon = this.denon * other.getDenon();
    }
    
    /**
     * 乗算
     * @param other
     */
    public void mul(Fraction other) {
        this.numer = this.numer * other.getNumer();
        this.denon = this.denon * other.getDenon();
    }
    
    /**
     * 除算
     * @param other
     */
    public void div(Fraction other) {
        this.numer = this.numer * other.getDenon();
        this.denon = this.denon * other.getNumer();
    }
   
    /**
     * 約分を行う
     */
    public void reduce() {
       int gcd = Math.abs(gcd(numer, denon));
       numer /= gcd;
       denon /= gcd;
    }
    
    /**
     * 数値を文字列にして返す
     * @param isDouble
     * @return
     */
    public String toString(boolean isDouble) {
        if (isDouble) {
            return Double.toString(1.0d * numer / denon);
        } else {
            if (denon == 1) {
                return Integer.toString(numer);
            } else {
                reduce();
                return String.format("%d / %d", numer, denon);
            }
        }
    }
    
    /**
     * 2つの整数の最大公約数を求める.
     * @param i
     * @param j
     * @return
     */
    private static int gcd(int i, int j) {
        if (j == 0) {
            return i;
        } else {
            return gcd(j, i % j);
        }
    }
}
