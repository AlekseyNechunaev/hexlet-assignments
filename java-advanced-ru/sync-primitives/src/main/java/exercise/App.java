package exercise;

class App {

    public static void main(String[] args) {
        getListInfo();
    }

    public static void getListInfo() {
        final SafetyList safetyList = new SafetyList();
        ListThread firstListThread = new ListThread(safetyList);
        ListThread secondListThread = new ListThread(safetyList);
        firstListThread.start();
        secondListThread.start();
        try {
            firstListThread.join();
            secondListThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(safetyList.getSize());
        System.out.println(safetyList.get(5));
    }
}

