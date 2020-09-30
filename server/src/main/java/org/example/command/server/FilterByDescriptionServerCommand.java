package org.example.command.server;

import org.example.model.Message;
import org.example.model.MusicBand;

public class FilterByDescriptionServerCommand extends AbstractServerCommand {

    @Override
    public String command() {
        return null;
    }

    @Override
    public String description() {
        return "Вывести элементы, значение поля description которых меньше заданного";
    }

    @Override
    public String execute(Message args) {
        if (args.getArgs() == null) {
            return "Не хватает аргумента";
        }
        String output = "";
        for (MusicBand musicBand : musicBandDAO.filterByDescription(args.getArgs())) {
            if (musicBand != null)
                output = output.concat(musicBand.toString()).concat(System.lineSeparator());
        }
        return output;
    }
}
