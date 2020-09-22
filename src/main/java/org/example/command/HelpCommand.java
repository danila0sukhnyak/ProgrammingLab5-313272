package org.example.command;

public class HelpCommand extends AbstractCommand {

    @Override
    public String command() {
        return "help";
    }

    @Override
    public String description() {
        return "Вывести справку по доступным коммандам";
    }

    @Override
    public void execute(String[] args) {
        commands.forEach((key, value) -> consoleService.printLn(key + " : " + value.description()));
    }
}
