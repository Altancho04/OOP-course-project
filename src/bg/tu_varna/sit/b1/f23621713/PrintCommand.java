package bg.tu_varna.sit.b1.f23621713;

/**
 * Клас PrintCommand – команда за извеждане на всички съществуващи фигури в конзолата.
 */

public class PrintCommand implements Command{
    private ShapeManager shapeManager;
    public PrintCommand(ShapeManager shapeManager) {
        this.shapeManager = shapeManager;
    }

    /** Изпълнява командата, подадена от потребителя. @param parts аргументи от командния ред */
    @Override
    public void execute(String[] parts) {
        if (shapeManager.getShapes().isEmpty()) {
            System.out.println("No shapes loaded.");
            return;
        }

        System.out.println("Shapes:");
        for (int i = 0; i < shapeManager.getShapes().size(); i++) {
            Shape shape = shapeManager.getShapes().get(i);
            if (shape instanceof Rectangle rect) {
                System.out.printf("%d. rectangle %.0f %.0f %.0f %.0f %s\n", (i + 1), rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), rect.getFill());
            } else if (shape instanceof Circle circle) {
                System.out.printf("%d. circle %.0f %.0f %.0f %s\n", (i + 1), circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circle.getFill());
            } else if (shape instanceof Line line) {
                System.out.printf("%d. line %d %d %d %d\n", (i + 1), line.getX1(), line.getY1(), line.getX2(), line.getY2());
            }
        }
    }
}
