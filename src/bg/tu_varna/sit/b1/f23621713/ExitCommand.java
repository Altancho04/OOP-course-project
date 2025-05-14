package bg.tu_varna.sit.b1.f23621713;

/**
 * Клас ExitCommand – команда за изход от програмата.
 */

public class ExitCommand implements Command{
    private boolean[] running;
    public ExitCommand(boolean[] runningFlag) {
        this.running = runningFlag;
    }

    /** Изпълнява командата, подадена от потребителя. @param parts аргументи от командния ред */
    @Override
    public void execute(String[] parts) {
        System.out.println("Exiting the program...");
        running[0] = false;
    }

}
