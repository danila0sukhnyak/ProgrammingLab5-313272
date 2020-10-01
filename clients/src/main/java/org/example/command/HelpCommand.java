package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.HelpServerCommand;
import org.example.model.Message;

public class HelpCommand extends AbstractCommand {

    @Override
    public String command() {
        return "help";
    }

    @Override
    public String description() {
        return "Вывести справку по доступным коммандам";
    }

    @Override
    public AbstractServerCommand serverCommand() {
        return new HelpServerCommand();
    }

    @Override
    public Message execute(String[] args) {
        return new Message(serverCommand());
    }
}
