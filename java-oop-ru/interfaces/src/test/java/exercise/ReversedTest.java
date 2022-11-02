package exercise;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReversedTest {

    @Test
    void reversedTestFullScenario() {
        CharSequence text = new ReversedSequence("abcdef");
        String resultToString = text.toString();
        assertThat(resultToString).isEqualTo("fedcba");
        char resultCharAt = text.charAt(1);
        assertThat(resultCharAt).isEqualTo('e');
        int resultLength = text.length();
        assertThat(resultLength).isEqualTo(6);
        String resultSubSequence = text.subSequence(1, 4).toString();
        assertThat(resultSubSequence).isEqualTo("edc");
    }

    @Test
    void reversedCharAtTest() {
        CharSequence text = new ReversedSequence("abcdef");
        assertThat(text.charAt(5)).isEqualTo('a');
        assertThat(text.charAt(0)).isEqualTo('f');
        assertThatThrownBy(() -> text.charAt(6))
                .isInstanceOf(StringIndexOutOfBoundsException.class)
                .hasMessageContaining("String index out of range: 6");
        assertThatThrownBy(() -> text.charAt(-1))
                .isInstanceOf(StringIndexOutOfBoundsException.class)
                .hasMessageContaining("String index out of range: -1");
    }

    @Test
    void reversedSubSequenceTest() {
        CharSequence text = new ReversedSequence("abcdef");
        assertThat(text.subSequence(0, 6).toString()).isEqualTo("fedcba");
        assertThat(text.subSequence(1, 2).toString()).isEqualTo("e");
        assertThatThrownBy(() -> text.subSequence(0, 7))
                .isInstanceOf(StringIndexOutOfBoundsException.class);
        assertThatThrownBy(() -> text.subSequence(-1, 6))
                .isInstanceOf(StringIndexOutOfBoundsException.class);

    }
}
