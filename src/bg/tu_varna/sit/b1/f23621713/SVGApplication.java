package bg.tu_varna.sit.b1.f23621713;

import java.io.IOException;
import java.util.Scanner;

public class SVGApplication {

    private ShapeManager shapeManager = new ShapeManager();
    private SVGFileHandler fileHandler = new SVGFileHandler();
    private boolean running = true;
    private String currentFilePath = null;

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the SVG Application!");

        while (running) {
            System.out.print("> ");
            String command = scanner.nextLine();
            processCommand(command);
        }
        scanner.close();
    }

    private void processCommand(String command) {
        String[] parts = command.split(" ");
        String action = parts[0];

        switch (action) {
            case "print":
                printShapes();
                break;
            case "create":
                createShape(parts);
                break;
            case "erase":
                eraseShape(parts);
                break;
            case "translate":
                translateShape(parts);
                break;
            case "within":
                within(parts);
                break;
            case "open":
                openFile(parts);
                break;
            case "close":
                closeFile();
                break;
            case "save":
                saveFile();
                break;
            case "saveas":
                saveAsFile(parts);
                break;
            case "help":
                displayHelp();
                break;
            case "exit":
                running = false;
                break;
            default:
                System.out.println("Unknown command.");
        }
    }


    private void printShapes() {
        System.out.println("Shapes:");
        for (int i = 0; i < shapeManager.getShapes().size(); i++) {
            Shape shape = shapeManager.getShapes().get(i);

            if (shape instanceof Rectangle) {
                Rectangle rect = (Rectangle) shape;
                System.out.printf("%d. rectangle %f %f %f %f %s\n", i, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), rect.getFill());
            } else if (shape instanceof Circle) {
                Circle circle = (Circle) shape;
                System.out.printf("%d. circle %f %f %f %s\n", i, circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circle.getFill());
            } else if (shape instanceof Line) {
                Line line = (Line) shape;
                System.out.printf("%d. line %d %d %d %d\n", i, line.getX1(), line.getY1(), line.getX2(), line.getY2());
            }
        }
    }

    private void createShape(String[] parts) {
        if (parts.length < 6) {
            System.out.println("Usage: create <shape_type> <params> <color>");
            return;
        }

        String shapeType = parts[1];
        String color = parts[parts.length - 1];

        switch (shapeType) {
            case "line":
                shapeManager.addShape(new Line(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5])));
                System.out.println("Successfully created line.");
                break;
            case "circle":
                shapeManager.addShape(new Circle(Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), color));
                System.out.println("Successfully created circle.");
                break;
            case "rectangle":
                shapeManager.addShape(new Rectangle(Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), Double.parseDouble(parts[5]), color));
                System.out.println("Successfully created rectangle.");
                break;
            default:
                System.out.println("Unknown shape type.");
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
        int deltaX = 0;
        int deltaY = 0;
        Integer index = null;

        for (String part : parts) {
            if (part.startsWith("vertical=")) {
                deltaY = Integer.parseInt(part.substring("vertical=".length()));
            } else if (part.startsWith("horizontal=")) {
                deltaX = Integer.parseInt(part.substring("horizontal=".length()));
            } else {
                try {
                    index = Integer.parseInt(part); // Ако е число, това е индекс на фигура
                } catch (NumberFormatException ignored) {
                }
            }
        }

        if (index != null) {
            if (index >= 0 && index < shapeManager.getShapes().size()) {
                shapeManager.moveShape(shapeManager.getShapes().get(index), deltaX, deltaY);
                System.out.println("Translated figure " + index);
            } else {
                System.out.println("Invalid index.");
            }
        } else {
            for (Shape shape : shapeManager.getShapes()) {
                shape.move(deltaX, deltaY);
            }
            System.out.println("Translated all figures");
        }
    }

    private void within(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: within <option> <params>");
            return;
        }

        String option = parts[1];

        switch (option) {
            case "circle":
                if (parts.length < 5) {
                    System.out.println("Usage: within circle <cx> <cy> <radius>");
                    return;
                }

                double cx = Double.parseDouble(parts[2]);
                double cy = Double.parseDouble(parts[3]);
                double radius = Double.parseDouble(parts[4]);

                System.out.println("Shapes within circle:");

                boolean foundCircle = false;

                for (Shape shape : shapeManager.getShapes()) {
                    if (shape.isWithinCircle(cx, cy, radius)) {
                        System.out.println(shape.toSVG());
                        foundCircle = true;
                    }
                }

                if (!foundCircle) {
                    System.out.printf("No figures are located within circle %f %f %f\n", cx, cy, radius);
                }

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

                System.out.println("Shapes within rectangle:");

                boolean foundRectangle = false;

                for (Shape shape : shapeManager.getShapes()) {
                    if (shape.isWithinRectangle(x, y, width, height)) {
                        System.out.println(shape.toSVG());
                        foundRectangle = true;
                    }
                }

                if (!foundRectangle) {
                    System.out.printf("No figures are located within rectangle %f %f %f %f\n", x, y, width, height);
                }

                break;

            default:
                System.out.println("Unknown option.");
                break;
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
            System.out.println("Successfully opened " + filename);

        } catch (IOException e) {
            System.out.println("Error opening file: " + e.getMessage());
        }
    }


    private void closeFile() {
        shapeManager = new ShapeManager();
        currentFilePath = null;
        System.out.println("File closed.");
    }


    private void saveFile() {
        if (currentFilePath == null) {
            System.out.println("No file is currently open. Use 'saveas' to save to a new file.");
        } else {
            try {
                fileHandler.saveFile(currentFilePath, shapeManager);
                System.out.println("Successfully saved the changes to " + currentFilePath);

            } catch (IOException e) {
                System.out.println("Error saving file: " + e.getMessage());
            }
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
            System.out.println("Successfully saved as " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private void displayHelp() {
        System.out.println("Available commands:");
        System.out.println("print - Print all shapes");
        System.out.println("create <shape_type> <params> <color> - Create a new shape (line, circle, rectangle)");
        System.out.println("erase <n> - Erase shape at index n");
        System.out.println("translate - Translate all shapes by a fixed amount");
        System.out.println("within <option> <params> - Check shapes within a region (circle or rectangle)");
        System.out.println("open <filename> - Open an SVG file");
        System.out.println("close - Close the current file");
        System.out.println("save - Save the current file");
        System.out.println("saveas <filename> - Save to a new file");
        System.out.println("help - Display this help message");
        System.out.println("exit - Exit the application");
    }
}
