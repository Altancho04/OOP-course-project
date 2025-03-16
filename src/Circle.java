public class Circle extends Shape{
    private int radius;

    public Circle(int x, int y, int radius) {
        super(x, y);
        this.radius = radius;
    }

    @Override
    public String toSVG() {
        return String.format("<circle cx='%d' cy='%d' r='%d' stroke='black' fill='none'/>", x, y, radius);
    }
}
