package org.example.command;

import org.example.model.DataStorage;

public class SaveCommand extends AbstractCommand {

    @Override
    public String command() {
        return "save";
    }

    @Override
    public String description() {
        return "Сохранить коллекцию в файл";
    }

    @Override
    public void execute(String[] args) {
        DataStorage data = musicBandDAO.getData();
        if (fileService.write(data)) {
            consoleService.printLn("Успешно сохранено");
        } else {
            consoleService.printLn("Ошибка при сохранении в файл");
        }
    }
}
