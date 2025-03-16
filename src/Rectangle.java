public class Rectangle extends Shape{
    private int width, height;

    public Rectangle(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public String toSVG() {
        return String.format("<rect x='%d' y='%d' width='%d' height='%d' stroke='black' fill='none'/>", x, y, width, height);
    }
}
