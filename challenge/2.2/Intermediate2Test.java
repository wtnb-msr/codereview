package intermediate;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class Intermediate2Test {

        @Test
        public void uniqは重複を取り除いた配列を返す() {
                int[] src = {1, 1, 2, 2, 2, 3, 4, 4, 5, 5};
                int[] actual = Intermediate2.uniq(src);

                int[] matcher = {1, 2, 3, 4, 5};

                assertThat(actual, is(matcher));
        }

        @Test
        public void uniqは重複を取り除いた配列を返す2() {
                int[] src = {1, 1, 2, 3, 2, 4, 4, 5, 4};
                int[] actual = Intermediate2.uniq(src);

                int[] matcher = {1, 2, 3, 2, 4, 5, 4};

                assertThat(actual, is(matcher));
        }

        @Test
        public void uniqは要素0個でも例外は投げない() {
                int[] src = new int[0];
                int[] actual = Intermediate2.uniq(src);

                assertThat(actual.length, is(0));
        }

        @Test
        public void uniqは要素1個の場合コピーを返す() {
                int[] src = {10};
                int[] actual = Intermediate2.uniq(src);

                assertThat(actual, is(src));
                assertThat(actual, is(not(sameInstance(src))));
        }

        @Test(expected=NullPointerException.class)
        public void uniqはnullを渡すとNullPointerExceptionを投げる() {
                Intermediate2.uniq(null);
        }

}