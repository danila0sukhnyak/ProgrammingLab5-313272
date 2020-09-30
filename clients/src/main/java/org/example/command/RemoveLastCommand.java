package org.example.command;

import org.example.model.MusicBand;

public class RemoveLastCommand extends AbstractCommand {

    @Override
    public String command() {
        return "remove_last";
    }

    @Override
    public String description() {
        return "Удалить последний элемент из коллекции";
    }

    @Override
    public void execute(String[] args) {
        MusicBand musicBand = musicBandDAO.removeLast();
        if (musicBand == null) {
            consoleService.printLn("Коллекция пустая");
        } else {
            consoleService.printLn("Удален элемент с id = " + musicBand.getId());
        }
    }
}
