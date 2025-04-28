package bg.tu_varna.sit.b1.f23621713;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас за управление на списък от фигури (Shape).
 * Позволява добавяне на нови фигури, премахване, преместване на фигури
 * и извеждане на съдържанието като валиден SVG документ.
 */

public class ShapeManager {
    private List<Shape> shapes = new ArrayList<>();

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void moveShape(Shape shape, int deltaX, int deltaY) {
        shape.move(deltaX, deltaY);
    }

    public String toSVG() {
        StringBuilder svg = new StringBuilder("<svg xmlns=\"http://www.w3.org/2000/svg\">\n");
        for (Shape shape : shapes) {
            svg.append(shape.toSVG()).append("\n");
        }
        svg.append("</svg>");
        return svg.toString();
    }
}
