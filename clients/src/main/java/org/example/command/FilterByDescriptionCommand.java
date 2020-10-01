package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.FilterByDescriptionServerCommand;
import org.example.model.Message;

public class FilterByDescriptionCommand extends AbstractCommand {

    @Override
    public String command() {
        return null;
    }

    @Override
    public String description() {
        return "Вывести элементы, значение поля description которых меньше заданного";
    }

    @Override
    public AbstractServerCommand serverCommand() {
        return new FilterByDescriptionServerCommand();
    }

    @Override
    public Message execute(String[] args) {
        if (args.length < 2 || args[1] == null) {
            consoleService.printLn("Не хватает аргумента");
            return null;
        }
        return new Message(serverCommand(), args[1]);
    }
}
