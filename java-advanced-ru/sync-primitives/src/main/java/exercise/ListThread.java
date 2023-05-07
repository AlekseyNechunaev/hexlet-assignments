package exercise;

public class ListThread extends Thread {

    private final SafetyList list;
    private static final int COUNT_ELEMENTS = 1000;

    public ListThread(SafetyList list) {
        this.list = list;
    }

    @Override
    public void run() {
        for (int i = 0; i < COUNT_ELEMENTS; i++) {
            try {
                Thread.sleep(1);
                list.add(i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}