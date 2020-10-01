package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.RemoveByDescriptionServerCommand;
import org.example.model.Message;
import org.example.model.MusicBand;

import java.util.List;

public class RemoveByDescriptionCommand extends AbstractCommand {

    @Override
    public String command() {
        return "remove_any_by_description";
    }

    @Override
    public String description() {
        return "Удалить из коллекции один элемент, значение поля description которого эквивалентно заданному";
    }

    @Override
    public AbstractServerCommand serverCommand() {
        return new RemoveByDescriptionServerCommand();
    }

    @Override
    public Message execute(String[] args) {
        if (args.length < 2 || args[1] == null) {
            consoleService.printLn("Не хватает аргумента");
            return null;
        }
        String descr = "";
        for (int i = 1; i < args.length; i++) {
            descr = descr.concat(args[i]).concat(" ");
        }
        return new Message(serverCommand(), descr.trim());
    }
}
