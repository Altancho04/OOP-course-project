package bg.tu_varna.sit.b1.f23621713;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Клас за работа с SVG файлове – зареждане и запис на фигури.
 * Поддържа основните SVG елементи: <line>, <circle> и <rect>.
 * Използва файловата система за четене/запис с вградени проверки и съобщения.
 */

public class SVGFileHandler {
    /**
     * Отваря SVG файл и зарежда фигурите в мениджъра на фигури.
     *
     * @param filename      път до SVG файла
     * @param shapeManager  ShapeManager, който управлява всички фигури
     */
    public void openFile(String filename, ShapeManager shapeManager) {
        Path path = Paths.get(filename);

        if (!Files.exists(path)) {
            try {
                String emptySvg = "<svg xmlns=\"http://www.w3.org/2000/svg\"></svg>";
                Files.write(path, emptySvg.getBytes());
                System.out.println("New empty SVG file created: " + filename);
            } catch (IOException e) {
                System.out.println("Error creating new file: " + e.getMessage());
                return;
            }
        }

        try {
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                line = line.trim();
                if (line.contains("<line")) {
                    int x1 = Integer.parseInt(line.split("x1=\"")[1].split("\"")[0]);
                    int y1 = Integer.parseInt(line.split("y1=\"")[1].split("\"")[0]);
                    int x2 = Integer.parseInt(line.split("x2=\"")[1].split("\"")[0]);
                    int y2 = Integer.parseInt(line.split("y2=\"")[1].split("\"")[0]);
                    shapeManager.addShape(new Line(x1, y1, x2, y2));
                } else if (line.contains("<circle")) {
                    double cx = Double.parseDouble(line.split("cx=\"")[1].split("\"")[0]);
                    double cy = Double.parseDouble(line.split("cy=\"")[1].split("\"")[0]);
                    double r = Double.parseDouble(line.split("r=\"")[1].split("\"")[0]);
                    String fill = line.contains("fill=") ? line.split("fill=\"")[1].split("\"")[0] : "black";
                    shapeManager.addShape(new Circle(cx, cy, r, fill));
                } else if (line.contains("<rect")) {
                    double x = Double.parseDouble(line.split("x=\"")[1].split("\"")[0]);
                    double y = Double.parseDouble(line.split("y=\"")[1].split("\"")[0]);
                    double width = Double.parseDouble(line.split("width=\"")[1].split("\"")[0]);
                    double height = Double.parseDouble(line.split("height=\"")[1].split("\"")[0]);
                    String fill = line.contains("fill=") ? line.split("fill=\"")[1].split("\"")[0] : "black";
                    shapeManager.addShape(new Rectangle(x, y, width, height, fill));
                }
            }

            System.out.println("Successfully opened " + filename);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Записва текущите фигури от ShapeManager в SVG файл.
     *
     * @param filename      път до SVG файла, в който ще се запише
     * @param shapeManager  ShapeManager, съдържащ фигурите
     */
    public void saveFile(String filename, ShapeManager shapeManager) {
        try {
            Path path = Paths.get(filename);
            String svgContent = shapeManager.toSVG();
            Files.write(path, svgContent.getBytes());
            System.out.println("Successfully saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
