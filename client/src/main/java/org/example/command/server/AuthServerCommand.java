package org.example.command.server;

import org.example.model.Message;
import org.example.util.MusicBandValidator;

public class AuthServerCommand extends AbstractServerCommand {

    @Override
    public String command() {
        return "auth";
    }

    @Override
    public String description() {
        return "Авторизация на сервере";
    }

    /**
     * Использует {@link MusicBandValidator} для наполнения объекта {@link MusicBand}
     * и добавляет его в коллекцию
     *
     * @param args аргументы
     * @return
     */
    @Override
    public Message execute(Message args) {
        return null;
    }
}
