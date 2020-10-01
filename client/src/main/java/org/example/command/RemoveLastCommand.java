package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.RemoveLastServerCommand;
import org.example.model.Message;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class RemoveLastCommand extends AbstractCommand {

    @Override
    public String command() {
        return "remove_last";
    }

    @Override
    public String description() {
        return "Удалить последний элемент из коллекции";
    }

    @Override
    public AbstractServerCommand serverCommand() {
        return new RemoveLastServerCommand();
    }

    @Override
    public Queue<Message> execute(String[] args) {
        return new LinkedList<>(Arrays.asList(new Message(serverCommand())));
    }
}
