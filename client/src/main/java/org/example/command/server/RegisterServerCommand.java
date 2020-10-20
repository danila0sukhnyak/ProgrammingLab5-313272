package org.example.command.server;

import org.example.model.Message;
import org.example.util.MusicBandValidator;

public class RegisterServerCommand extends AbstractServerCommand {

    @Override
    public String command() {
        return "register";
    }

    @Override
    public String description() {
        return "Регистрация на сервере";
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
        return new Message("Успешная регистрация");
    }
}
