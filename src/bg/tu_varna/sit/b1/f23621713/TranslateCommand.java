package bg.tu_varna.sit.b1.f23621713;

/**
 * Клас TranslateCommand – команда за преместване на една или всички фигури по координати.
 */

public class TranslateCommand implements Command{
    private ShapeManager shapeManager;
    public TranslateCommand(ShapeManager shapeManager) {
        this.shapeManager = shapeManager;
    }

    /** Изпълнява командата, подадена от потребителя. @param parts аргументи от командния ред */
    @Override
    public void execute(String[] parts) {
        int deltaX = 100;
        int deltaY = 10;

        if (parts.length == 2) {
            try {
                int index = Integer.parseInt(parts[1]) - 1; // Защото print започва от 1
                if (index >= 0 && index < shapeManager.getShapes().size()) {
                    shapeManager.moveShape(shapeManager.getShapes().get(index), deltaX, deltaY);
                    System.out.println("Translated figure (" + (index + 1) + ")");
                } else {
                    System.out.println("There is no figure number " + parts[1] + "!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid figure number: " + parts[1]);
            }
        } else {
            for (Shape shape : shapeManager.getShapes()) {
                shape.move(deltaX, deltaY);
            }
            System.out.println("Translated all figures");
        }
    }
}
