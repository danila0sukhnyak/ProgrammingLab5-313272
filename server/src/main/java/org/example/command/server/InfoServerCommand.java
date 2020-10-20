package org.example.command.server;

import org.example.model.DataStorage;
import org.example.model.Message;

public class InfoServerCommand extends AbstractServerCommand {

    @Override
    public String command() {
        return "info";
    }

    @Override
    public String description() {
        return "Вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д)";
    }

    @Override
    public Message execute(Message args) {
        DataStorage data = musicBandDAO.getData();
        return new Message(data.toString());
    }
}
