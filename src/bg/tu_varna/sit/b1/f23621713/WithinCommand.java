package bg.tu_varna.sit.b1.f23621713;

/**
 * Клас WithinCommand – команда за показване на фигури в зададен обхват (кръг или правоъгълник).
 */

public class WithinCommand implements Command{
    private ShapeManager shapeManager;
    public WithinCommand(ShapeManager shapeManager) {
        this.shapeManager = shapeManager;
    }

    /** Изпълнява командата, подадена от потребителя. @param parts аргументи от командния ред */

    @Override
    public void execute(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: within <option> <params>");
            return;
        }

        String option = parts[1];

        switch (option) {
            case "circle":
                if (parts.length < 5) {
                    System.out.println("Usage: within circle <cx> <cy> <radius>");
                    return;
                }
                double cx = Double.parseDouble(parts[2]);
                double cy = Double.parseDouble(parts[3]);
                double radius = Double.parseDouble(parts[4]);
                System.out.println("Shapes within circle:");
                boolean foundCircle = false;
                for (Shape shape : shapeManager.getShapes()) {
                    if (shape.isWithinCircle(cx, cy, radius)) {
                        System.out.println(shape.toSVG());
                        foundCircle = true;
                    }
                }
                if (!foundCircle) {
                    System.out.printf("No figures are located within circle %.0f %.0f %.0f\n", cx, cy, radius);
                }
                break;

            case "rectangle":
                if (parts.length < 6) {
                    System.out.println("Usage: within rectangle <x> <y> <width> <height>");
                    return;
                }
                double x = Double.parseDouble(parts[2]);
                double y = Double.parseDouble(parts[3]);
                double width = Double.parseDouble(parts[4]);
                double height = Double.parseDouble(parts[5]);
                System.out.println("Shapes within rectangle:");

                boolean foundRectangle = false;
                int count = 0;

                for (Shape shape : shapeManager.getShapes()) {
                    if (shape.isWithinRectangle(x, y, width, height)) {
                        if (shape instanceof Rectangle rect) {
                            System.out.printf("%d. rectangle %.0f %.0f %.0f %.0f %s%n",
                                    ++count, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), rect.getFill());
                        } else if (shape instanceof Circle circle) {
                            System.out.printf("%d. circle %.0f %.0f %.0f %s%n",
                                    ++count, circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circle.getFill());
                        } else if (shape instanceof Line line) {
                            System.out.printf("%d. line %d %d %d %d%n",
                                    ++count, line.getX1(), line.getY1(), line.getX2(), line.getY2());
                        }

                        foundRectangle = true;
                    }
                }
                if (!foundRectangle) {
                    System.out.printf("No figures are located within rectangle %.1f %.1f %.1f %.1f\n", x, y, width, height);
                }
                break;

            default:
                System.out.println("Unknown option.");
                break;
        }
    }
}
