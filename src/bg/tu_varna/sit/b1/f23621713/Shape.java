package bg.tu_varna.sit.b1.f23621713;

public interface Shape {
    String toSVG();
    void move(int deltaX, int deltaY);
    boolean isWithinCircle(double cx, double cy, double radius);
    boolean isWithinRectangle(double x, double y, double width, double height);
}
