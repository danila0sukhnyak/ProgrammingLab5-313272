package org.example.command;

public class ExitCommand extends AbstractCommand {

    @Override
    public String command() {
        return "exit";
    }

    @Override
    public String description() {
        return "Завершить программу (без сохранения в файл)";
    }

    @Override
    public void execute(String[] args) {
        consoleService.printLn("Программа закрывается. До свидания!");
        System.exit(0);
    }
}
