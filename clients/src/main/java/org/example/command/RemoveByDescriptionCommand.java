package org.example.command;

import org.example.model.MusicBand;

import java.util.List;

public class RemoveByDescriptionCommand extends AbstractCommand {

    @Override
    public String command() {
        return "remove_any_by_description";
    }

    @Override
    public String description() {
        return "Удалить из коллекции один элемент, значение поля description которого эквивалентно заданному";
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2 || args[1] == null) {
            consoleService.printLn("Не хватает аргумента");
            return;
        }
        String descr = "";
        for (int i = 1; i < args.length; i++) {
            descr = descr.concat(args[i]).concat(" ");
        }

        List<MusicBand> musicBands = musicBandDAO.removeByDescription(descr.trim());
        if (musicBands.isEmpty()) {
            consoleService.printLn("Совпадений не найдено");
        } else {
            consoleService.printLn("Удалены объекты:");
            musicBands.forEach(s -> {
                if (s != null)
                    consoleService.printLn(s.toString());
            });
        }
    }
}
