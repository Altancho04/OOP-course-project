package bg.tu_varna.sit.b1.f23621713;

/**
 * Клас EraseCommand – команда за премахване на фигура по неин номер.
 */

public class EraseCommand implements Command{
    private ShapeManager shapeManager;
    public EraseCommand(ShapeManager shapeManager) {
        this.shapeManager = shapeManager;
    }

    /** Изпълнява командата, подадена от потребителя. @param parts аргументи от командния ред */
    @Override
    public void execute(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: erase <n>");
            return;
        }

        int index = Integer.parseInt(parts[1]) - 1;
        if (index >= 0 && index < shapeManager.getShapes().size()) {
            shapeManager.removeShape(shapeManager.getShapes().get(index));
            System.out.printf("Erased a shape (%d)\n", index + 1);
        } else {
            System.out.println("There is no figure number " + parts[1] + "!");
        }
    }
}
