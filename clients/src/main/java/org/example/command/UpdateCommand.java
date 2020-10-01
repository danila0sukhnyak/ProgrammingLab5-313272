package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.UpdateServerCommand;
import org.example.enums.Color;
import org.example.enums.MusicGenre;
import org.example.exception.InterruptInputException;
import org.example.exception.MusicBandNotFoundException;
import org.example.model.Message;
import org.example.model.MusicBand;
import org.example.util.MusicBandAttributeSetter;

import java.util.Arrays;

public class UpdateCommand extends AbstractCommand {

    @Override
    public String command() {
        return "update";
    }

    @Override
    public String description() {
        return "Обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public AbstractServerCommand serverCommand() {
        return new UpdateServerCommand();
    }

    @Override
    public Message execute(String[] args) {
        if (args.length < 2 || args[1] == null) {
            consoleService.printLn("Не хватает аргумента");
            return null;
        }
        try {
            Long.valueOf(args[1]);
        } catch (NumberFormatException e) {
            consoleService.printLn("Неверный формат аргумента");
            return null;
        }
        try {
            MusicBand musicBand = new MusicBand();
            MusicBandAttributeSetter setter = new MusicBandAttributeSetter(consoleService);
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
            return new Message(serverCommand(), musicBand, args[1]);
        } catch (InterruptInputException e) {
            consoleService.printLn("Обновление элемента прервано");
            return null;
        } catch (MusicBandNotFoundException e) {
            consoleService.printLn("Такого элемента нет");
            return null;
        }
    }
}
