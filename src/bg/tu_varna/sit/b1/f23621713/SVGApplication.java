package bg.tu_varna.sit.b1.f23621713;

import java.util.*;
import java.util.function.Consumer;
import java.io.IOException;

/**
 * Основен клас на приложението за работа със SVG фигури.
 * Изпълнява команди от потребителя чрез конзолен интерфейс:
 * създаване, изтриване, преместване, зареждане и запис на фигури.
 * Менюто е реализирано чрез HashMap, без използване на switch.
 */

public class SVGApplication {
    private ShapeManager shapeManager = new ShapeManager();
    private SVGFileHandler fileHandler = new SVGFileHandler();
    private boolean running = true;
    private String currentFilePath = null;

    private final Map<String, Consumer<String[]>> commandMap = new HashMap<>();

    /**
     * Стартира приложението и приема команди от потребителя.
     */
    public void start() {
        initializeCommands();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the SVG Application!\n-Please use the help command!");

        while (running) {
            System.out.print("\n> ");
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
        commandMap.put("help", this::displayHelp);
        commandMap.put("exit", parts -> {
            System.out.println("Exiting the application.");
            running = false;
        });
    }

    /**
     * Обработва подадена команда и извиква съответния метод.
     *
     * @param command пълната текстова команда от потребителя
     */
    private void processCommand(String command) {
        String[] parts = command.split(" ");
        String action = parts[0];

        if (currentFilePath == null &&
                !action.equals("open") &&
                !action.equals("exit") &&
                !action.equals("help")) {
            System.out.println("No file is currently open.");
            return;
        }

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

        System.out.println("Shapes: ");
        for (int i = 0; i < shapeManager.getShapes().size(); i++) {
            Shape shape = shapeManager.getShapes().get(i);
            if (shape instanceof Rectangle rect) {
                System.out.printf("%d. rectangle %.0f %.0f %.0f %.0f %s\n", i,
                        rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), rect.getFill());
            } else if (shape instanceof Circle circle) {
                System.out.printf("%d. circle %.0f %.0f %.0f %s\n", i,
                        circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circle.getFill());
            } else if (shape instanceof Line line) {
                System.out.printf("%d. line %d %d %d %d\n", i,
                        line.getX1(), line.getY1(), line.getX2(), line.getY2());
            }
        }
    }

    /**
     * Създава фигура според типа и параметрите.
     *
     * @param parts масив от аргументи (напр. "create rectangle 10 10 20 20 red")
     */
    private void createShape(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: create <shape_type> <params>");
            return;
        }

        String type = parts[1];

        switch (type) {
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
                        Double.parseDouble(parts[2]),
                        Double.parseDouble(parts[3]),
                        Double.parseDouble(parts[4]),
                        parts[5]
                ));
                System.out.println("Successfully created: circle");
                break;

            case "rectangle":
                shapeManager.addShape(new Rectangle(
                        Double.parseDouble(parts[2]),
                        Double.parseDouble(parts[3]),
                        Double.parseDouble(parts[4]),
                        Double.parseDouble(parts[5]),
                        parts[6]
                ));
                System.out.println("Successfully created: rectangle");
                break;

            default:
                System.out.println("Unknown shape type.");
        }
    }

    /**
     * Изтрива фигура по индекс.
     *
     * @param parts масив с индекс за изтриване
     */
    private void eraseShape(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: erase <n>");
            return;
        }
        int index = Integer.parseInt(parts[1]);
        if (index >= 0 && index < shapeManager.getShapes().size()) {
            shapeManager.removeShape(shapeManager.getShapes().get(index));
            System.out.printf("Erased a shape (%d) ", index);
        } else {
            System.out.println("There is no figure number " + index + "!");
        }
    }

    /**
     * Премества фигури по индекс или всички наведнъж.
     *
     * @param parts индекс или празно за всички
     */
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

    /**
     * Проверява кои фигури се намират в зададена област (кръг или правоъгълник).
     *
     * @param parts параметри на командата
     */
    private void within(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: within <circle|rectangle> <params>");
            return;
        }

        switch (parts[1]) {
            case "circle":
                if (parts.length < 5) {
                    System.out.println("Usage: within circle <cx> <cy> <radius>");
                    return;
                }
                double cx = Double.parseDouble(parts[2]);
                double cy = Double.parseDouble(parts[3]);
                double radius = Double.parseDouble(parts[4]);
                boolean foundCircle = false;
                System.out.println("Shapes within circle:");
                for (Shape shape : shapeManager.getShapes()) {
                    if (shape.isWithinCircle(cx, cy, radius)) {
                        System.out.println(shape.toSVG());
                        foundCircle = true;
                    }
                }
                if (!foundCircle)
                    System.out.println("No figures are located within the specified circle.");
                break;

            case "rectangle":
                if (parts.length < 6) {
                    System.out.println("Usage: within rectangle <x> <y> <width> <height>");
                    return;
                }
                double x = Double.parseDouble(parts[2]);
                double y = Double.parseDouble(parts[3]);
                double width = Double.parseDouble(parts[4]);
                double height = Double.parseDouble(parts[5]);
                boolean foundRect = false;
                System.out.println("Shapes within rectangle:");
                for (Shape shape : shapeManager.getShapes()) {
                    if (shape.isWithinRectangle(x, y, width, height)) {
                        System.out.println(shape.toSVG());
                        foundRect = true;
                    }
                }
                if (!foundRect)
                    System.out.println("No figures are located within the specified rectangle.");
                break;

            default:
                System.out.println("Unknown option.");
        }
    }

    /**
     * Отваря SVG файл и зарежда фигурите.
     *
     * @param parts параметри на командата (име на файл)
     */
    private void openFile(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: open <filename>");
            return;
        }

        String filename = parts[1];
        fileHandler.openFile(filename, shapeManager);
        currentFilePath = filename;
    }

    private void closeFile() {
        shapeManager = new ShapeManager();
        currentFilePath = null;
        System.out.println("Successfully closed the file.");
    }

    private void saveFile() {
        if (currentFilePath == null) {
            System.out.println("No file is currently open. Use 'saveas' to save to a new file.");
        } else {
            fileHandler.saveFile(currentFilePath, shapeManager);
        }
    }

    /**
     * Записва текущите фигури в нов файл.
     *
     * @param parts параметри на командата (ново име на файл)
     */
    private void saveAsFile(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: saveas <filename>");
            return;
        }

        String filename = parts[1];
        fileHandler.saveFile(filename, shapeManager);
        currentFilePath = filename;
    }

    /**
     * Отпечатва помощна информация за всички поддържани команди.
     *
     * @param parts не се използва (присъства за съвместимост)
     */

    private void displayHelp(String[] parts) {
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
