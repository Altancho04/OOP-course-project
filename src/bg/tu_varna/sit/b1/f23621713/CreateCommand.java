package bg.tu_varna.sit.b1.f23621713;

/**
 * Клас CreateCommand – команда за създаване на SVG фигури (rectangle, circle, line).
 */

public class CreateCommand implements Command{
    private ShapeManager shapeManager;
    public CreateCommand(ShapeManager shapeManager) {
        this.shapeManager = shapeManager;
    }

    /** Изпълнява командата, подадена от потребителя. @param parts аргументи от командния ред */
    @Override
    public void execute(String[] parts) {
        if (parts.length < 6) {
            System.out.println("Usage: create <shape_type> <params> <color>");
            return;
        }

        String shapeType = parts[1];
        String color = parts[parts.length - 1];

        switch (shapeType) {
            case "line":
                shapeManager.addShape(new Line(
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]),
                        Integer.parseInt(parts[5])
                ));
                System.out.println("Successfully created: line");
                break;

            case "circle":
                shapeManager.addShape(new Circle(
                        Math.abs(Double.parseDouble(parts[2])),
                        Math.abs(Double.parseDouble(parts[3])),
                        Double.parseDouble(parts[4]),
                        color
                ));
                System.out.println("Successfully created: circle");
                break;

            case "rectangle":
                shapeManager.addShape(new Rectangle(
                        Math.abs(Double.parseDouble(parts[2])),
                        Math.abs(Double.parseDouble(parts[3])),
                        Double.parseDouble(parts[4]),
                        Double.parseDouble(parts[5]),
                        color
                ));
                System.out.println("Successfully created: rectangle");
                break;

            default:
                System.out.println("Unknown shape type.");
        }
    }
}
