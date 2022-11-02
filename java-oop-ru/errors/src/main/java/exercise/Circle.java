package exercise;

public class Circle {

    private final Point point;
    private final int radius;

    public Circle(Point point, int radius) {
        this.point = point;
        this.radius = radius;
    }

    public Point getPoint() {
        return point;
    }

    public int getRadius() {
        return radius;
    }

    public double getSquare() throws NegativeRadiusException {
        if (this.radius < 0) {
            throw new NegativeRadiusException("Не удалось посчитать площадь");
        }
        return Math.PI * Math.pow(radius, 2);
    }
}