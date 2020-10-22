package org.example.command.server;

import org.example.model.Message;
import org.example.model.MusicBand;

import java.util.ArrayList;
import java.util.List;

public class ShowServerCommand extends AbstractServerCommand {

    @Override
    public String command() {
        return "show";
    }

    @Override
    public String description() {
        return "Вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public Message execute(Message args) {
        List<MusicBand> all = musicBandDAO.getAll();
        if (all.isEmpty()) {
            return new Message(new ArrayList<>());
        } else {
            return new Message(all);
        }
    }
}
