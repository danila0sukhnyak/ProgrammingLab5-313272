package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.ClearServerCommand;
import org.example.model.Message;

import java.util.LinkedList;
import java.util.Queue;

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
    public AbstractServerCommand serverCommand() {
        return new ClearServerCommand();
    }

    @Override
    public Queue<Message> execute(String[] args) {
        Queue<Message> messages = new LinkedList<>();
        messages.add(new Message(serverCommand()));
        return messages;
    }
}
