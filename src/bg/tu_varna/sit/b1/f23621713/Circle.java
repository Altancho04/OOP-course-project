package bg.tu_varna.sit.b1.f23621713;

/**
 * Клас, който представя кръг във векторна графика SVG.
 * Реализира интерфейса Shape и осигурява операции като преместване,
 * проверка за съдържание в регион и генериране на SVG елемент.
 */

public class Circle implements Shape {
    private double centerX;
    private double centerY;
    private double radius;
    private String fill;

    public Circle(double centerX, double centerY, double radius, String fill) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.fill = fill;
    }

    @Override
    public boolean isWithinCircle(double cx, double cy, double radius) {
        double distance = Math.sqrt(Math.pow(centerX - cx, 2) + Math.pow(centerY - cy, 2));
        return distance + this.radius <= radius;
    }

    @Override
    public boolean isWithinRectangle(double x, double y, double width, double height) {
        return (centerX - radius >= x && centerX + radius <= x + width &&
                centerY - radius >= y && centerY + radius <= y + height);
    }

    @Override
    public String toSVG() {
        return String.format("<circle cx=\"%f\" cy=\"%f\" r=\"%f\" fill=\"%s\" />", centerX, centerY, radius, fill);
    }

    @Override
    public void move(int deltaX, int deltaY) {
        centerX += deltaX;
        centerY += deltaY;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getRadius() {
        return radius;
    }

    public String getFill() {
        return fill;
    }
}
