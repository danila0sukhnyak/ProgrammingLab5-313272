package org.example.command.server;

import org.example.model.Message;
import org.example.model.MusicBand;

import java.util.List;

public class RemoveByDescriptionServerCommand extends AbstractServerCommand {

    @Override
    public String command() {
        return "remove_any_by_description";
    }

    @Override
    public String description() {
        return "Удалить из коллекции один элемент, значение поля description которого эквивалентно заданному";
    }

    @Override
    public String execute(Message args) {
        if (args.getArgs() == null) {
            return "Не хватает аргумента";
        }
        String descr = args.getArgs();

        List<MusicBand> musicBands = musicBandDAO.removeByDescription(descr.trim(), args.getUser().getLogin());
        if (musicBands.isEmpty()) {
            return "Совпадений не найдено";
        } else {
            String output = "";
            output = output.concat("Удалены объекты:");
            for (MusicBand musicBand : musicBands) {
                if (musicBand != null)
                    output = output.concat(musicBand.toString()).concat(System.lineSeparator());
            }
            return output;
        }
    }
}
