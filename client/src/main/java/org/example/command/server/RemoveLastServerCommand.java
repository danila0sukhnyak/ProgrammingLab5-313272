package org.example.command.server;

import org.example.model.Message;
import org.example.model.MusicBand;

public class RemoveLastServerCommand extends AbstractServerCommand {

    @Override
    public String command() {
        return "remove_last";
    }

    @Override
    public String description() {
        return "Удалить последний элемент из коллекции";
    }

    @Override
    public Message execute(Message args) {
        MusicBand musicBand = musicBandDAO.removeLast();
        if (musicBand == null) {
            return new Message("Коллекция пустая");
        } else {
            return new Message("Удален элемент с id = " + musicBand.getId());
        }
    }
}
