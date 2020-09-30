package org.example.command.server;

import org.example.model.Message;
import org.example.model.MusicBand;

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
    public String execute(Message args) {
        List<MusicBand> all = musicBandDAO.getAll();
        if (all.isEmpty()) {
            return "Коллекция пустая";
        } else {
            String output = "";
            for (MusicBand musicBand : all) {
                if (musicBand != null)
                    output = output.concat(musicBand.toString()).concat(System.lineSeparator());
            }
            return output;
        }
    }
}
