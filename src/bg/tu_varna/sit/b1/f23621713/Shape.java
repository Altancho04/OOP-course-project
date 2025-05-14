package bg.tu_varna.sit.b1.f23621713;

/**
 * Интерфейс, който дефинира общите операции за всички векторни фигури
 * като правоъгълници, кръгове и линии.
 * Фигурите трябва да могат да се движат, да се проверяват за съдържание
 * в определен регион и да се конвертират в SVG представяне.
 */

/**
 * Клас Shape – интерфейс за всички фигури – съдържа методи за преместване, SVG, и проверки по позиция.
 */

public interface Shape {
    String toSVG();
    void move(int deltaX, int deltaY);
    boolean isWithinCircle(double cx, double cy, double radius);
    boolean isWithinRectangle(double x, double y, double width, double height);
}
