package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.InfoServerCommand;
import org.example.model.DataStorage;
import org.example.model.Message;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class InfoCommand extends AbstractCommand {

    @Override
    public String command() {
        return "info";
    }

    @Override
    public String description() {
        return "Вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д)";
    }

    @Override
    public AbstractServerCommand serverCommand() {
        return new InfoServerCommand();
    }

    @Override
    public Queue<Message> execute(String[] args) {
        return new LinkedList<>(Collections.singletonList(new Message(serverCommand())));
    }
}
