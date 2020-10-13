package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.AddServerCommand;
import org.example.command.server.AuthServerCommand;
import org.example.enums.Color;
import org.example.enums.MusicGenre;
import org.example.model.*;
import org.example.util.MusicBandAttributeSetter;
import org.example.util.UserHolder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class AuthCommand extends AbstractCommand {

    @Override
    public String command() {
        return "auth";
    }

    @Override
    public String description() {
        return "Авторизация на сервере";
    }

    @Override
    public AbstractServerCommand serverCommand() {
        return new AuthServerCommand();
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
        User user = new User();
        System.out.println("Введите логин");
        user.setLogin(consoleService.read());
        System.out.println("Введите пароль");
        user.setPassword(consoleService.read());
        UserHolder.setUser(user);
        messages.add(new Message(serverCommand(), user));
        return messages;
    }
}
