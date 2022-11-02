package exercise;

public class ReversedSequence implements CharSequence {

    private final String text;

    public ReversedSequence(String text) {
        this.text = reverse(text);
    }

    private String reverse(String inputText) {
        return new StringBuilder(inputText).reverse().toString();
    }

    @Override
    public int length() {
        return text.length();
    }

    @Override
    public char charAt(int i) {
        return text.charAt(i);
    }

    @Override
    public CharSequence subSequence(int beginIndex, int endIndex) {
        return text.subSequence(beginIndex, endIndex);
    }

    @Override
    public String toString() {
        return text;
    }
}