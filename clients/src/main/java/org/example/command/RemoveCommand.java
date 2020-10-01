package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.RemoveServerCommand;
import org.example.model.Message;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class RemoveCommand extends AbstractCommand {

    @Override
    public String command() {
        return "remove_by_id";
    }

    @Override
    public String description() {
        return "Удалить элемент из коллекции по его id";
    }

    @Override
    public AbstractServerCommand serverCommand() {
        return new RemoveServerCommand();
    }

    @Override
    public Queue<Message> execute(String[] args) {
        if (args.length < 2 || args[1] == null) {
            consoleService.printLn("Не хватает аргумента");
            return new LinkedList<>();
        }
        try {
            Long.valueOf(args[1]);
        } catch (NumberFormatException e) {
            consoleService.printLn("Неверный формат аргумента");
            return new LinkedList<>();
        }
        return new LinkedList<>(Collections.singletonList(new Message(serverCommand(), args[1])));
    }
}
