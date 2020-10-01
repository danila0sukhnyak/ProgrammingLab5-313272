package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.InfoServerCommand;
import org.example.model.DataStorage;
import org.example.model.Message;

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
    public Message execute(String[] args) {
        return new Message(serverCommand());
    }
}
