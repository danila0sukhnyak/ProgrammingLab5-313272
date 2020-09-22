package org.example.command;

import org.example.model.MusicBand;

public class RemoveCommand extends AbstractCommand {

    @Override
    public String command() {
        return "remove_by_id";
    }

    @Override
    public String description() {
        return "Удалить элемент из коллекции по его id";
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2 || args[1] == null) {
            consoleService.printLn("Не хватает аргумента");
            return;
        }
        Long id;
        try {
            id = Long.valueOf(args[1]);
        } catch (NumberFormatException e) {
            consoleService.printLn("Неверный формат аргумента");
            return;
        }
        MusicBand musicBand = musicBandDAO.removeById(id);
        if (musicBand == null) {
            consoleService.printLn("Элемент не найден");
        } else {
            consoleService.printLn("Элемент успешно удален");
        }
    }
}
