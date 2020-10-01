package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.RemoveServerCommand;
import org.example.model.Message;
import org.example.model.MusicBand;

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
    public Message execute(String[] args) {
        if (args.length < 2 || args[1] == null) {
            consoleService.printLn("Не хватает аргумента");
            return null;
        }
        try {
            Long.valueOf(args[1]);
        } catch (NumberFormatException e) {
            consoleService.printLn("Неверный формат аргумента");
            return null;
        }
        return new Message(serverCommand(), args[1]);
    }
}
