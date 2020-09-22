package org.example.command;

import org.example.model.MusicBand;

public class MaxByIdCommand extends AbstractCommand {

    @Override
    public String command() {
        return "max_by_id";
    }

    @Override
    public String description() {
        return "Вывести любой объект из коллекции, значение поля id которого является максимальным";
    }

    @Override
    public void execute(String[] args) {
        MusicBand maxId = musicBandDAO.getByMaxId();
        if (maxId == null) {
            consoleService.printLn("Коллекция пустая");
        } else {
            consoleService.printLn(maxId.toString());
        }
    }
}
