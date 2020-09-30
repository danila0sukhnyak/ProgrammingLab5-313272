package org.example.command;

import org.example.model.DataStorage;

public class InfoCommand extends AbstractCommand {

    @Override
    public String command() {
        return "info";
    }

    @Override
    public String description() {
        return "Вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д)";
    }

    @Override
    public void execute(String[] args) {
        DataStorage data = musicBandDAO.getData();
        consoleService.printLn(data.toString());
    }
}
