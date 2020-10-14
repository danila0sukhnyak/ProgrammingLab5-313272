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
    public String execute(Message args) {
        musicBandDAO.clear(args.getUser().getLogin());
        return "Коллекция очищена";
    }
}
