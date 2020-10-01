package org.example.command.server;

import org.example.enums.SortStatus;
import org.example.model.Message;

public class ReorderServerCommand extends AbstractServerCommand {

    @Override
    public String command() {
        return "reorder";
    }

    @Override
    public String description() {
        return "Отсортировать коллекцию в порядке, обратном нынешнему";
    }

    @Override
    public String execute(Message args) {
        SortStatus reorder = musicBandDAO.reorder();
        switch (reorder) {
            case ASC:
                return "Коллекция отсортирована по возрастанию";
            case DESC:
                return "Коллекция отсортирована по убыванию";
            default:
                return "";
        }
    }
}
