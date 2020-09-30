package org.example.command.server;

import org.example.exception.InterruptInputException;
import org.example.exception.MusicBandNotFoundException;
import org.example.model.Message;
import org.example.model.MusicBand;
import org.example.util.MusicBandValidator;

public class UpdateServerCommand extends AbstractServerCommand {

    @Override
    public String command() {
        return "update";
    }

    @Override
    public String description() {
        return "Обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String execute(Message args) {
        if (args.getArgs() == null) {
            return "Не хватает аргумента";
        }
        Long id;
        try {
            id = Long.valueOf(args.getArgs());
        } catch (NumberFormatException e) {
            return "Неверный формат аргумента";
        }
        try {
            MusicBand musicBand = musicBandDAO.getById(id);
            MusicBandValidator validator = new MusicBandValidator(musicBandDAO);
            validator.validateUpdate(musicBand, args.getMusicBand());
            musicBandDAO.update(args.getMusicBand(), id);
        } catch (InterruptInputException e) {
            return "Обновление элемента прервано";
        } catch (MusicBandNotFoundException e) {
            return "Такого элемента нет";
        }
        return "Элемент успешно обновлен!";
    }
}
