package org.example.command;

import org.example.enums.SortStatus;

public class ReorderCommand extends AbstractCommand {

    @Override
    public String command() {
        return "reorder";
    }

    @Override
    public String description() {
        return "Отсортировать коллекцию в порядке, обратном нынешнему";
    }

    @Override
    public void execute(String[] args) {
        SortStatus reorder = musicBandDAO.reorder();
        switch (reorder) {
            case ASC:
                consoleService.printLn("Коллекция отсортирована по возрастанию");
                break;
            case DESC:
                consoleService.printLn("Коллекция отсортирована по убыванию");
                break;
            default:
                break;
        }
    }
}
