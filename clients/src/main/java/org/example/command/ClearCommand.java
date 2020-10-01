package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.ClearServerCommand;
import org.example.model.Message;

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
    public Message execute(String[] args) {
        return new Message(serverCommand());
    }
}
