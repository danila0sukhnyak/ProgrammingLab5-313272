package org.example.command.server;

import org.example.exception.MusicBandWrongAttributeException;
import org.example.model.Message;
import org.example.model.MusicBand;
import org.example.util.MusicBandValidator;

import java.time.LocalDateTime;

public class AddServerCommand extends AbstractServerCommand {

    @Override
    public String command() {
        return "add";
    }

    @Override
    public String description() {
        return "Добавить новый элемент в коллекцию";
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
        if (args.getMusicBand() == null){
            return new Message("Не хватает аргумента");
        }
        MusicBand musicBand = args.getMusicBand();
        musicBand.setCreationDate(LocalDateTime.now());
        musicBand.setUserName(args.getUser().getLogin());
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
        musicBandDAO.save(musicBand);
        return new Message("Элемент успешно добавлен!");
    }
}
