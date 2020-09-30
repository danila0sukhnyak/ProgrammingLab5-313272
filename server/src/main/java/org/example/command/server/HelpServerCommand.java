package org.example.command.server;

import org.example.model.Message;

public class HelpServerCommand extends AbstractServerCommand {

    @Override
    public String command() {
        return "help";
    }

    @Override
    public String description() {
        return "Вывести справку по доступным коммандам";
    }

    @Override
    public String execute(Message args) {
        String output = "";
        for (AbstractServerCommand value : commands.values()) {
            output = output.concat(value.command() + " : " + value.description()).concat(System.lineSeparator());
        }
        return output;
    }
}
