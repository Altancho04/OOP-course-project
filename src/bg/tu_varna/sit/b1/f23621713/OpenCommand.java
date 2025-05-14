package bg.tu_varna.sit.b1.f23621713;

/**
 * Клас OpenCommand – команда за зареждане на SVG файл и добавяне на фигурите в ShapeManager.
 */

public class OpenCommand implements Command{
    private SVGFileHandler fileHandler;
    private ShapeManager shapeManager;

    public OpenCommand(SVGFileHandler fileHandler, ShapeManager shapeManager) {
        this.fileHandler = fileHandler;
        this.shapeManager = shapeManager;
    }

    /** Изпълнява командата, подадена от потребителя. @param parts аргументи от командния ред */
    @Override
    public void execute(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: open <filename>");
            return;
        }

        String filename = parts[1];
        fileHandler.openFile(filename, shapeManager);
        System.out.println("Successfully opened " + filename);
    }
}
