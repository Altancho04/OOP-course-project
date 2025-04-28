package bg.tu_varna.sit.b1.f23621713;

/**
 * Клас, който представя линия във векторна графика SVG.
 * Реализира интерфейса Shape и позволява генериране на SVG представяне
 * и преместване на линията по координатите.
 */


public class Line implements Shape {
    private int x1, y1, x2, y2;

    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public String toSVG() {
        return String.format("<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"black\" />", x1, y1, x2, y2);
    }

    @Override
    public void move(int deltaX, int deltaY) {
        x1 += deltaX;
        y1 += deltaY;
        x2 += deltaX;
        y2 += deltaY;
    }

    @Override
    public boolean isWithinCircle(double cx, double cy, double radius) {
        return false;
    }

    @Override
    public boolean isWithinRectangle(double x, double y, double width, double height) {
        return false;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }
}
