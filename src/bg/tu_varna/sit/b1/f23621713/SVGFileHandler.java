package bg.tu_varna.sit.b1.f23621713;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Клас, който се грижи за зареждане на фигури от SVG файлове
 * и записване на промените обратно във валидни SVG файлове.
 * Поддържа само основните SVG елементи: правоъгълник, кръг и линия.
 */

public class SVGFileHandler {
    public void openFile(String filename, ShapeManager shapeManager) throws IOException {
        Path path = Paths.get(filename);
        List<String> lines = Files.readAllLines(path);

        for (String line : lines) {
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
                String fill = line.contains("fill=\"") ? line.split("fill=\"")[1].split("\"")[0] : "black";

                shapeManager.addShape(new Circle(cx, cy, r, fill));

            } else if (line.contains("<rect")) {
                double x = Double.parseDouble(line.split("x=\"")[1].split("\"")[0]);
                double y = Double.parseDouble(line.split("y=\"")[1].split("\"")[0]);
                double width = Double.parseDouble(line.split("width=\"")[1].split("\"")[0]);
                double height = Double.parseDouble(line.split("height=\"")[1].split("\"")[0]);
                String fill = line.contains("fill=\"") ? line.split("fill=\"")[1].split("\"")[0] : "black";

                shapeManager.addShape(new Rectangle(x, y, width, height, fill));
            }
        }
    }

    public void saveFile(String filename, ShapeManager shapeManager) throws IOException {
        Path path = Paths.get(filename);
        String svgContent = shapeManager.toSVG();
        Files.write(path, svgContent.getBytes());
    }
}