package bg.tu_varna.sit.b1.f23621713;

import java.util.*;
import java.util.function.Consumer;
import java.io.IOException;

/**
 * Главен клас, който предоставя команден интерфейс за работа със SVG фигури.
 */
public class SVGApplication {
    private ShapeManager shapeManager = new ShapeManager();
    private SVGFileHandler fileHandler = new SVGFileHandler();
    private boolean running = true;
    private String currentFilePath = null;

    private final Map<String, Consumer<String[]>> commandMap = new HashMap<>();

    public void start() {
        initializeCommands();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the SVG Application!");

        while (running) {
            System.out.print("> ");
            String command = scanner.nextLine();
            processCommand(command);
        }

        scanner.close();
    }

    private void initializeCommands() {
        commandMap.put("print", parts -> printShapes());
        commandMap.put("create", this::createShape);
        commandMap.put("erase", this::eraseShape);
        commandMap.put("translate", this::translateShape);
        commandMap.put("within", this::within);
        commandMap.put("open", this::openFile);
        commandMap.put("close", parts -> closeFile());
        commandMap.put("save", parts -> saveFile());
        commandMap.put("saveas", this::saveAsFile);
        commandMap.put("help", parts -> displayHelp());
        commandMap.put("exit", parts -> {
            System.out.println("Exiting the application.");
            running = false;
        });
    }

    private void processCommand(String command) {
        String[] parts = command.split(" ");
        String action = parts[0];
        Consumer<String[]> handler = commandMap.get(action);

        if (handler != null) {
            handler.accept(parts);
        } else {
            System.out.println("Unknown command.");
        }
    }

    private void printShapes() {
        if (shapeManager.getShapes().isEmpty()) {
            System.out.println("No shapes loaded.");
            return;
        }

        System.out.println("Shapes:");
        for (int i = 0; i < shapeManager.getShapes().size(); i++) {
            Shape shape = shapeManager.getShapes().get(i);
            if (shape instanceof Rectangle rect) {
                System.out.printf("%d. rectangle %.1f %.1f %.1f %.1f %s\n", i, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), rect.getFill());
            } else if (shape instanceof Circle circle) {
                System.out.printf("%d. circle %.1f %.1f %.1f %s\n", i, circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circle.getFill());
            } else if (shape instanceof Line line) {
                System.out.printf("%d. line %d %d %d %d\n", i, line.getX1(), line.getY1(), line.getX2(), line.getY2());
            }
        }
    }

    private void createShape(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: create <shape_type> <params>");
            return;
        }

        String shapeType = parts[1];

        try {
            switch (shapeType) {
                case "line":
                    shapeManager.addShape(new Line(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5])));
                    System.out.println("Successfully created: line");
                    break;
                case "circle":
                    shapeManager.addShape(new Circle(Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), parts[5]));
                    System.out.println("Successfully created: circle");
                    break;
                case "rectangle":
                    shapeManager.addShape(new Rectangle(Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), Double.parseDouble(parts[5]), parts[6]));
                    System.out.println("Successfully created: rectangle");
                    break;
                default:
                    System.out.println("Unknown shape type.");
            }
        } catch (Exception e) {
            System.out.println("Invalid parameters for shape creation.");
        }
    }

    private void eraseShape(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: erase <n>");
            return;
        }

        int index = Integer.parseInt(parts[1]);
        if (index >= 0 && index < shapeManager.getShapes().size()) {
            shapeManager.removeShape(shapeManager.getShapes().get(index));
            System.out.printf("Erased a shape (%d)\n", index);
        } else {
            System.out.println("There is no figure number " + index + "!");
        }
    }

    private void translateShape(String[] parts) {
        int deltaX = 10;
        int deltaY = 10;

        if (parts.length == 2) {
            int index = Integer.parseInt(parts[1]);
            if (index >= 0 && index < shapeManager.getShapes().size()) {
                shapeManager.moveShape(shapeManager.getShapes().get(index), deltaX, deltaY);
                System.out.println("Successfully translated figure " + index);
            } else {
                System.out.println("Invalid index.");
            }
        } else {
            for (Shape shape : shapeManager.getShapes()) {
                shape.move(deltaX, deltaY);
            }
            System.out.println("Successfully translated all figures.");
        }
    }

    private void within(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: within <circle|rectangle> <params>");
            return;
        }

        String option = parts[1];
        boolean found = false;

        switch (option) {
            case "circle" -> {
                if (parts.length < 5) {
                    System.out.println("Usage: within circle <cx> <cy> <r>");
                    return;
                }
                double cx = Double.parseDouble(parts[2]);
                double cy = Double.parseDouble(parts[3]);
                double r = Double.parseDouble(parts[4]);

                System.out.println("Shapes within circle:");
                for (Shape shape : shapeManager.getShapes()) {
                    if (shape.isWithinCircle(cx, cy, r)) {
                        System.out.println(shape.toSVG());
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No figures are located within the specified circle.");
                }
            }

            case "rectangle" -> {
                if (parts.length < 6) {
                    System.out.println("Usage: within rectangle <x> <y> <w> <h>");
                    return;
                }
                double x = Double.parseDouble(parts[2]);
                double y = Double.parseDouble(parts[3]);
                double w = Double.parseDouble(parts[4]);
                double h = Double.parseDouble(parts[5]);

                System.out.println("Shapes within rectangle:");
                for (Shape shape : shapeManager.getShapes()) {
                    if (shape.isWithinRectangle(x, y, w, h)) {
                        System.out.println(shape.toSVG());
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No figures are located within the specified rectangle.");
                }
            }

            default -> System.out.println("Unknown option.");
        }
    }

    private void openFile(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: open <filename>");
            return;
        }

        String filename = parts[1];
        try {
            fileHandler.openFile(filename, shapeManager);
            currentFilePath = filename;
        } catch (IOException e) {
            System.out.println("Error opening file: " + e.getMessage());
        }
    }

    private void closeFile() {
        shapeManager = new ShapeManager();
        currentFilePath = null;
        System.out.println("Successfully closed the file.");
    }

    private void saveFile() {
        if (currentFilePath == null) {
            System.out.println("No file is currently open. Use 'saveas' to save to a new file.");
            return;
        }

        try {
            fileHandler.saveFile(currentFilePath, shapeManager);
            System.out.println("Successfully saved: " + currentFilePath);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private void saveAsFile(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: saveas <filename>");
            return;
        }

        String filename = parts[1];

        try {
            fileHandler.saveFile(filename, shapeManager);
            currentFilePath = filename;
            System.out.println("Successfully saved: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private void displayHelp() {
        System.out.println("Available commands:");
        System.out.println("print - Print all shapes");
        System.out.println("create <shape_type> <params> - Create a new shape (line, circle, rectangle)");
        System.out.println("erase <n> - Erase shape at index n");
        System.out.println("translate - Translate all shapes or one by index");
        System.out.println("within <circle|rectangle> <params> - Check shapes within a region");
        System.out.println("open <filename> - Open an SVG file");
        System.out.println("close - Close the current file");
        System.out.println("save - Save the current file");
        System.out.println("saveas <filename> - Save to a new file");
        System.out.println("help - Display this help message");
        System.out.println("exit - Exit the application");
    }
}
