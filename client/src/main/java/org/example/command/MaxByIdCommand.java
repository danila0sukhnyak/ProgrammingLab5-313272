package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.MaxByIdServerCommand;
import org.example.model.Message;
import org.example.model.MusicBand;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

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
    public Queue<Message> execute(String[] args) {
        return new LinkedList<>(Collections.singletonList(new Message(serverCommand())));
    }
}
