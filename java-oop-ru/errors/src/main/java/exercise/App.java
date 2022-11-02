package exercise;

public class App {

    public static void printSquare(Circle circle) {
        try {
            int square = (int) Math.ceil(circle.getSquare());
            System.out.println(square);
        } catch (NegativeRadiusException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Вычисление окончено");
        }
    }
}