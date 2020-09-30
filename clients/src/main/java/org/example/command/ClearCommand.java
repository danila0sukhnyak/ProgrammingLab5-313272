package org.example.command;

public class ClearCommand extends AbstractCommand {

    @Override
    public String command() {
        return "clear";
    }

    @Override
    public String description() {
        return "Очистить коллекцию";
    }

    @Override
    public void execute(String[] args) {
        musicBandDAO.clear();
        consoleService.printLn("Коллекция очищена");
    }
}
