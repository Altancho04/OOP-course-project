package bg.tu_varna.sit.b1.f23621713;

/**
 * Клас HelpCommand – команда за показване на поддържаните от приложението команди.
 */

public class HelpCommand implements Command{

    /** Изпълнява командата, подадена от потребителя. @param parts аргументи от командния ред */
    @Override
    public void execute(String[] parts) {
        System.out.println("The following commands are supported:");
        System.out.println("open <file>            opens a file");
        System.out.println("close                  closes the currently opened file");
        System.out.println("save                   saves the currently open file");
        System.out.println("saveas <file>          saves the currently open file in <file>");
        System.out.println("print                  prints all loaded shapes");
        System.out.println("create <shape> <params> creates a shape (rectangle, circle, line)");
        System.out.println("erase <index>          removes a shape at a given index");
        System.out.println("translate [index]      moves one or all shapes");
        System.out.println("within <shape> <params> shows shapes inside area");
        System.out.println("help                   prints this information");
        System.out.println("exit                   exits the program");
    }
}
