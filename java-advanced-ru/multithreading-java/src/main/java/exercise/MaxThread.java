package exercise;

public class MaxThread extends Thread {

    private final int[] array;
    private int maxNumber;

    public MaxThread(int[] array) {
        this.array = array;
    }

    @Override
    public void run() {
        int max = Integer.MIN_VALUE;
        for (int number: array) {
            if(number > max) {
                max = number;
            }
        }
        maxNumber = max;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

}