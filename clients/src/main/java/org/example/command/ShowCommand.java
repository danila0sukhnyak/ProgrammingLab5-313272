package org.example.command;

import org.example.model.MusicBand;

import java.util.List;

public class ShowCommand extends AbstractCommand {

    @Override
    public String command() {
        return "show";
    }

    @Override
    public String description() {
        return "Вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public void execute(String[] args) {
        List<MusicBand> all = musicBandDAO.getAll();
        if (all.isEmpty()) {
            consoleService.printLn("Коллекция пустая");
        } else {
            all.forEach(s -> {
                if (s != null)
                    consoleService.printLn(s.toString());
            });
        }
    }
}
