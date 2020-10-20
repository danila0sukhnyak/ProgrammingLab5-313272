package org.example.command.server;

import org.example.exception.MusicBandWrongAttributeException;
import org.example.model.Message;
import org.example.model.MusicBand;
import org.example.util.MusicBandValidator;

import java.time.LocalDateTime;

public class AddIfMinServerCommand extends AbstractServerCommand {

    @Override
    public String command() {
        return "add_if_min";
    }

    @Override
    public String description() {
        return "Добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    /**
     * Использует {@link MusicBandValidator} для наполнения объекта {@link MusicBand}
     * и добавляет его в коллекцию, если он самый меньший в коллекций или она пуста
     *
     * @param args аргументы
     * @return
     */
    @Override
    public Message execute(Message args) {
        MusicBand musicBand = args.getMusicBand();
        musicBand.setUserName(args.getUser().getLogin());
        musicBand.setCreationDate(LocalDateTime.now());
        MusicBandValidator validator = new MusicBandValidator(musicBandDAO);
        try {
            validator.validate(musicBand);
        }
        catch (MusicBandWrongAttributeException e){
            return new Message("Не прошла валидация на сервере. PasportID должен быть уникальным");
        }
        catch (Exception e) {
            return new Message("Не прошла валидация на сервере");
        }
        MusicBand minimal = musicBandDAO.getMinimal();
        if (minimal == null || musicBand.compareTo(minimal) < 0) {
            musicBandDAO.save(musicBand);
            return new Message("Элемент успешно добавлен!");
        } else {
            return new Message("Введенный элемент больше минимального");
        }
    }
}
