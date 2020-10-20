package org.example.command.server;

import org.example.exception.InterruptInputException;
import org.example.exception.MusicBandNotFoundException;
import org.example.exception.MusicBandWrongAttributeException;
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
        try {
            MusicBand musicBand = musicBandDAO.getById(id);
            MusicBandValidator validator = new MusicBandValidator(musicBandDAO);
            validator.validateUpdate(musicBand, args.getMusicBand());
            musicBandDAO.update(args.getMusicBand(), id);
        } catch (MusicBandWrongAttributeException e){
            if (e.getMessage().equals("Wrong user")) return new Message("У вас нет доступа");
            else return new Message(e.getMessage());
        }
        catch (InterruptInputException e) {
            return new Message("Обновление элемента прервано");
        } catch (MusicBandNotFoundException e) {
            return new Message("Такого элемента нет");
        }
        return new Message("Элемент успешно обновлен!");
    }
}
