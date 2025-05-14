package bg.tu_varna.sit.b1.f23621713;

import java.util.*;

/**
 * Основен клас на приложението за работа със SVG фигури.
 * Изпълнява команди от потребителя чрез конзолен интерфейс:
 * създаване, изтриване, преместване, зареждане и запис на фигури.
 * Менюто е реализирано чрез HashMap, без използване на switch.
 */

/**
 * Клас SVGApplication – основен клас, който стартира приложението и обработва потребителските команди.
 */

public class SVGApplication {
    private ShapeManager shapeManager = new ShapeManager();
    private SVGFileHandler fileHandler = new SVGFileHandler();
    private String currentFilePath = null;
    private boolean[] running = { true };

    private final Map<String, Command> commandMap = new HashMap<>();

    public SVGApplication() {
        initializeCommands();
    }

    private void initializeCommands() {
        commandMap.put("open", new OpenCommand(fileHandler, shapeManager));
        commandMap.put("close", new CloseCommand(shapeManager));
        commandMap.put("save", new SaveCommand(fileHandler, shapeManager, currentFilePath));
        commandMap.put("saveas", new SaveAsCommand(fileHandler, shapeManager));
        commandMap.put("print", new PrintCommand(shapeManager));
        commandMap.put("create", new CreateCommand(shapeManager));
        commandMap.put("erase", new EraseCommand(shapeManager));
        commandMap.put("translate", new TranslateCommand(shapeManager));
        commandMap.put("within", new WithinCommand(shapeManager));
        commandMap.put("help", new HelpCommand());
        commandMap.put("exit", new ExitCommand(running));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the SVG Application!\nPlease enter help command!");

        while (running[0]) {
            System.out.print("\n > ");
            String commandLine = scanner.nextLine();
            processCommand(commandLine);
        }

        scanner.close();
    }

    private void processCommand(String commandLine) {
        String[] parts = commandLine.trim().split(" ");
        if (parts.length == 0) return;

        String action = parts[0];
        Command command = commandMap.get(action);

        if (command != null) {
            command.execute(parts);
        } else {
            System.out.println("Unknown command.");
        }
    }
}
