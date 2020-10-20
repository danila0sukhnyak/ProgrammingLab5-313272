package org.example.command.server;

import org.example.model.Message;

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
    public Message execute(Message args) {
        return null;
    }
}
