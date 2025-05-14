package bg.tu_varna.sit.b1.f23621713;

/**
 * Клас, който представя кръг във векторна графика SVG.
 * Реализира интерфейса Shape и осигурява операции като преместване,
 * проверка за съдържание в регион и генериране на SVG елемент.
 */

/**
 * Клас Circle – представлява SVG фигура от тип circle, имплементираща интерфейса Shape.
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

    /** Проверява дали фигурата попада в дадена зона. */
    @Override
    public boolean isWithinCircle(double cx, double cy, double radius) {
        double distance = Math.sqrt(Math.pow(centerX - cx, 2) + Math.pow(centerY - cy, 2));
        return distance + this.radius <= radius;
    }

    /** Проверява дали фигурата попада в дадена зона. */
    @Override
    public boolean isWithinRectangle(double x, double y, double width, double height) {
        return centerX >= x &&
                centerY >= y &&
                centerX <= x + width &&
                centerY <= y + height;
    }

    /** Генерира SVG низ на всички фигури. @return SVG текст */
    @Override
    public String toSVG() {
        return String.format("<circle cx=\"%.0f\" cy=\"%.0f\" r=\"%.0f\" fill=\"%s\" />", centerX, centerY, radius, fill);
    }

    @Override
    public void move(int deltaX, int deltaY) {
        centerX += deltaX;
        centerY += deltaY;
    }

    /** Гет метод за поле. @return стойността */
    public double getCenterX() {
        return centerX;
    }

    /** Гет метод за поле. @return стойността */
    public double getCenterY() {
        return centerY;
    }

    /** Гет метод за поле. @return стойността */
    public double getRadius() {
        return radius;
    }

    /** Гет метод за поле. @return стойността */
    public String getFill() {
        return fill;
    }
}
