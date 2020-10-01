package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.AddServerCommand;
import org.example.enums.Color;
import org.example.enums.MusicGenre;
import org.example.model.Coordinates;
import org.example.model.Message;
import org.example.model.MusicBand;
import org.example.model.Person;
import org.example.util.MusicBandAttributeSetter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class AddCommand extends AbstractCommand {

    @Override
    public String command() {
        return "add";
    }

    @Override
    public String description() {
        return "Добавить новый элемент в коллекцию";
    }

    @Override
    public AbstractServerCommand serverCommand() {
        return new AddServerCommand();
    }

    /**
     * Использует {@link MusicBandAttributeSetter} для наполнения объекта {@link MusicBand}
     * и добавляет его в коллекцию
     *
     * @param args аргументы
     */
    @Override
    public Queue<Message> execute(String[] args) {
        Queue<Message> messages = new LinkedList<>();
        MusicBand musicBand = new MusicBand();
        musicBand.setFrontMan(new Person());
        musicBand.setCoordinates(new Coordinates());
        MusicBandAttributeSetter setter = new MusicBandAttributeSetter(consoleService);
        try {
            setter.setAttribute(musicBand,
                    "Введите название группы",
                    s -> s.setName(consoleService.read()));
            setter.setAttribute(musicBand,
                    "Введите количество участников",
                    s -> s.setNumberOfParticipants(Integer.valueOf(consoleService.read())));
            setter.setAttribute(musicBand,
                    "Введите описание группы",
                    s -> s.setDescription(consoleService.read()));
            setter.setAttribute(musicBand,
                    String.format("Введите жанр группы %s", Arrays.toString(MusicGenre.values())),
                    s -> s.setGenre(consoleService.read()));
            setter.setAttribute(musicBand,
                    "Введите координату x",
                    s -> s.getCoordinates().setX(Long.valueOf(consoleService.read())));
            setter.setAttribute(musicBand,
                    "Введите координату y",
                    s -> s.getCoordinates().setY(Float.valueOf(consoleService.read())));
            setter.setAttribute(musicBand,
                    "Введите имя солиста",
                    s -> s.getFrontMan().setName(consoleService.read()));
            setter.setAttribute(musicBand,
                    "Введите рост солиста",
                    s -> s.getFrontMan().setHeight(Double.parseDouble(consoleService.read())));
            setter.setAttribute(musicBand,
                    String.format("Введите цвет глаз солиста %s", Arrays.toString(Color.values())),
                    s -> s.getFrontMan().setEyeColor(consoleService.read()));
            setter.setAttribute(musicBand,
                    "Введите номер паспорта солиста",
                    s -> {
                        String line = consoleService.read();
                        s.getFrontMan().setPassportID(line);
                    });
        } catch (Exception e) {
            consoleService.printLn("Добавление элемента прервано");
            return messages;
        }

        messages.add(new Message(serverCommand(), musicBand));
        return messages;
    }
}
