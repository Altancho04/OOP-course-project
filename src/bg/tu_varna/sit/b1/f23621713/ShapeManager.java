package bg.tu_varna.sit.b1.f23621713;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас за управление на списък от фигури (Shape).
 * Позволява добавяне на нови фигури, премахване, преместване на фигури
 * и извеждане на съдържанието като валиден SVG документ.
 */

/**
 * Клас ShapeManager – управлява всички фигури в приложението – добавяне, преместване, премахване, SVG извеждане.
 */

public class ShapeManager {
    private List<Shape> shapes = new ArrayList<>();

    /** Добавя нова фигура в ShapeManager. @param shape фигурата за добавяне */
    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    /** Премахва фигура от ShapeManager. @param shape фигурата за премахване */
    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    /** Връща списъка с всички текущи фигури. @return списък от фигури */
    public List<Shape> getShapes() {
        return shapes;
    }

    /** Премества фигура с определено изместване. @param shape фигурата @param deltaX хор. изместване @param deltaY верт. изместване */
    public void moveShape(Shape shape, int deltaX, int deltaY) {
        shape.move(deltaX, deltaY);
    }

    /** Генерира SVG представяне на всички фигури. @return SVG съдържание като низ */
    public String toSVG() {
        StringBuilder svg = new StringBuilder("<svg xmlns=\"http://www.w3.org/2000/svg\">\n");
        for (Shape shape : shapes) {
            svg.append(shape.toSVG()).append("\n");
        }
        svg.append("</svg>");
        return svg.toString();
    }
}
