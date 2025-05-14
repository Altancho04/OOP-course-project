package bg.tu_varna.sit.b1.f23621713;

/**
 * Клас, който представя правоъгълник във векторна графика SVG.
 * Реализира интерфейса Shape и предоставя функционалности
 * за движение на правоъгълника, проверка за съдържание в регион
 * и генериране на SVG представяне.
 */

/**
 * Клас Rectangle – представлява SVG фигура от тип rectangle, имплементираща интерфейса Shape.
 */

public class Rectangle implements Shape {
    private double x;
    private double y;
    private double width;
    private double height;
    private String fill;

    public Rectangle(double x, double y, double width, double height, String fill) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fill = fill;
    }

    /** Проверява дали фигурата попада в зададения обхват. @return true ако попада, false ако не */

    @Override
    public boolean isWithinCircle(double cx, double cy, double radius) {
        return (x >= cx - radius && (x + width) <= cx + radius &&
                y >= cy - radius && (y + height) <= cy + radius);
    }

    /** Проверява дали фигурата попада в зададения обхват. @return true ако попада, false ако не */
    @Override
    public boolean isWithinRectangle(double rx, double ry, double rWidth, double rHeight) {
        return (x >= rx && (x + width) <= (rx + rWidth) &&
                y >= ry && (y + height) <= (ry + rHeight));
    }

    /** Генерира SVG представяне на всички фигури. @return SVG съдържание като низ */
    @Override
    public String toSVG() {
        return String.format("<rect x=\"%.0f\" y=\"%.0f\" width=\"%.0f\" height=\"%.0f\" fill=\"%s\" />", x, y, width, height, fill);
    }

    @Override
    public void move(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public String getFill() {
        return fill;
    }
}
