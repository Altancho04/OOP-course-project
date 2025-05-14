package bg.tu_varna.sit.b1.f23621713;

/**
 * Клас SaveAsCommand – команда за запис на фигурите в нов SVG файл.
 */

public class SaveAsCommand implements Command{
    private SVGFileHandler fileHandler;
    private ShapeManager shapeManager;
    private String currentFilePath;

    public SaveAsCommand(SVGFileHandler fileHandler, ShapeManager shapeManager) {
        this.fileHandler = fileHandler;
        this.shapeManager = shapeManager;
    }

    /** Изпълнява командата, подадена от потребителя. @param parts аргументи от командния ред */
    @Override
    public void execute(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: saveas <filename>");
            return;
        }

        String filename = parts[1];

        fileHandler.saveFile(filename, shapeManager);
        System.out.println("Successfully saved " + filename);
    }
}
