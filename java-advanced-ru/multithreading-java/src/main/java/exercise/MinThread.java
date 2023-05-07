package exercise;

public class MinThread extends Thread {
    private final int[] array;
    private int minNumber;
    public MinThread(int[] array) {
        this.array = array;
    }

    @Override
    public void run() {
        int min = Integer.MAX_VALUE;
        for(int number: array) {
            if(number < min) {
                min = number;
            }
        }
        minNumber = min;
    }

    public int getMinNumber() {
        return minNumber;
    }
}