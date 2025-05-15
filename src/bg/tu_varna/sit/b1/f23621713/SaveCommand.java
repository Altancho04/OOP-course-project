package bg.tu_varna.sit.b1.f23621713;

import java.io.IOException;

/**
 * Клас SaveCommand – команда за запис в текущия SVG файл.
 */

public class SaveCommand implements Command{
    private SVGFileHandler fileHandler;
    private ShapeManager shapeManager;
    private String currentFilePath;

    public SaveCommand(SVGFileHandler fileHandler, ShapeManager shapeManager, String currentFilePath) {
        this.fileHandler = fileHandler;
        this.shapeManager = shapeManager;
        this.currentFilePath = currentFilePath;
    }

    /** Изпълнява командата, подадена от потребителя. @param parts аргументи от командния ред */
    @Override
    public void execute(String[] parts) {
        if (currentFilePath == null) {
            System.out.println("No file is currently open. Use 'saveas' to save to a new file.");
            return;
        }
        try {
            fileHandler.saveFile(currentFilePath, shapeManager);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Successfully saved " + currentFilePath);
    }
}
