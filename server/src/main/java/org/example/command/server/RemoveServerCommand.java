package org.example.command.server;

import org.example.model.Message;
import org.example.model.MusicBand;

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
        if (args.getArgs() == null) {
            return new Message("Не хватает аргумента");
        }
        Long id;
        try {
            id = Long.valueOf(args.getArgs());
        } catch (NumberFormatException e) {
            return new Message("Неверный формат аргумента");
        }
        MusicBand musicBand = musicBandDAO.removeById(id, args.getUser().getLogin());
        if (musicBand == null) {
            return new Message("Элемент не найден. Либо у вас нет доступа.");
        } else {
            return new Message("Элемент успешно удален");
        }
    }
}
