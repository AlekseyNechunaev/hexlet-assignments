package exercise;

public class SafetyList {
    private int[] array;
    private int size;
    private final Object addElementLock;
    private int capacity = 10;

    public SafetyList() {
        this.array = new int[capacity];
        this.addElementLock = new Object();
    }

    public void add(int element) {
        synchronized (addElementLock) {
            final int position = size != 0 ? size - 1 : size;
            if (size == capacity) {
                final int[] newArray = cloneArray(array);
                newArray[position] = element;
                array = newArray;
            }
            array[position] = element;
            size++;
        }
    }

    public int get(int index) {
        return array[index];
    }

    public int getSize() {
        return size;
    }

    private int[] cloneArray(int[] fromArray) {
        capacity = capacity * 2;
        int[] newArray = new int[capacity];
        if (size >= 0) System.arraycopy(fromArray, 0, newArray, 0, size);
        return newArray;
    }
}
