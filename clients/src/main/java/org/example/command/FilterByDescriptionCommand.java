package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.FilterByDescriptionServerCommand;
import org.example.model.Message;

import java.util.LinkedList;
import java.util.Queue;

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
    public Queue<Message> execute(String[] args) {
        Queue<Message> messages = new LinkedList<>();
        if (args.length < 2 || args[1] == null) {
            consoleService.printLn("Не хватает аргумента");
            return messages;
        }
        messages.add(new Message(serverCommand(), args[1]));
        return messages;
    }
}
