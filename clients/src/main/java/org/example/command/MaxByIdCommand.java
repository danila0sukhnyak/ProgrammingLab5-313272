package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.MaxByIdServerCommand;
import org.example.model.Message;
import org.example.model.MusicBand;

public class MaxByIdCommand extends AbstractCommand {

    @Override
    public String command() {
        return "max_by_id";
    }

    @Override
    public String description() {
        return "Вывести любой объект из коллекции, значение поля id которого является максимальным";
    }

    @Override
    public AbstractServerCommand serverCommand() {
        return new MaxByIdServerCommand();
    }

    @Override
    public Message execute(String[] args) {
        return new Message(serverCommand());
    }
}
