package org.example.command.server;

import org.example.model.Message;

public class RemoveServerCommand extends AbstractServerCommand {

    @Override
    public String command() {
        return "remove_by_id";
    }

    @Override
    public String description() {
        return "Удалить элемент из коллекции по его id";
    }

    @Override
    public Message execute(Message args) {
        return null;
    }
}
