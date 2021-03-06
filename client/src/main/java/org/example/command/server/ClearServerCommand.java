package org.example.command.server;

import org.example.model.Message;

public class ClearServerCommand extends AbstractServerCommand {

    @Override
    public String command() {
        return "clear";
    }

    @Override
    public String description() {
        return "Очистить коллекцию";
    }

    @Override
    public Message execute(Message args) {
        return new Message("Коллекция очищена");
    }
}
