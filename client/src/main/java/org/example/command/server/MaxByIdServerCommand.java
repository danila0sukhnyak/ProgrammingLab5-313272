package org.example.command.server;

import org.example.model.Message;
import org.example.model.MusicBand;

public class MaxByIdServerCommand extends AbstractServerCommand {

    @Override
    public String command() {
        return "max_by_id";
    }

    @Override
    public String description() {
        return "Вывести любой объект из коллекции, значение поля id которого является максимальным";
    }

    @Override
    public String execute(Message args) {
        MusicBand maxId = musicBandDAO.getByMaxId();
        if (maxId == null) {
            return "Коллекция пустая";
        } else {
            return maxId.toString();
        }
    }
}
