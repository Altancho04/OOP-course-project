package bg.tu_varna.sit.b1.f23621713;

/**
 * Клас CloseCommand – команда за затваряне на отворения файл и изчистване на паметта.
 */

public class CloseCommand implements Command{
    private ShapeManager shapeManager;
    private String currentFilePath;

    public CloseCommand(ShapeManager shapeManager) {
        this.shapeManager = shapeManager;
    }

    /** Изпълнява командата, подадена от потребителя. @param parts аргументи от командния ред */
    @Override
    public void execute(String[] parts) {
        shapeManager.getShapes().clear();
        System.out.println("Successfully closed file.");
    }
}
