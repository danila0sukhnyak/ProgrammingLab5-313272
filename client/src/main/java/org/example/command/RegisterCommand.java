package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.AuthServerCommand;
import org.example.command.server.RegisterServerCommand;
import org.example.model.Message;
import org.example.model.MusicBand;
import org.example.model.User;
import org.example.util.MusicBandAttributeSetter;

import java.util.LinkedList;
import java.util.Queue;

public class RegisterCommand extends AbstractCommand {

    @Override
    public String command() {
        return "register";
    }

    @Override
    public String description() {
        return "Регистрация на сервере";
    }

    @Override
    public AbstractServerCommand serverCommand() {
        return new RegisterServerCommand();
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
        messages.add(new Message(serverCommand(), user));
        return messages;
    }
}
