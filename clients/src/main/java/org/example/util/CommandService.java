package org.example.util;

import org.example.bootstrap.ServiceLocator;
import org.example.command.server.*;
import org.example.model.Message;

import java.util.HashMap;
import java.util.Map;

public class CommandService {
    ServiceLocator serviceLocator;
    private Map<String, AbstractServerCommand> commands = new HashMap<>();

    public CommandService(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
        initCommands();
    }

    /**
     * Инициализирует комманды
     */
    private void initCommands() {
        registryCommand(new ShowServerCommand());
        registryCommand(new AddServerCommand());
        registryCommand(new AddIfMinServerCommand());
        registryCommand(new ClearServerCommand());
        registryCommand(new ExecuteServerCommand());
        registryCommand(new FilterByDescriptionServerCommand());
        registryCommand(new InfoServerCommand());
        registryCommand(new MaxByIdServerCommand());
        registryCommand(new RemoveByDescriptionServerCommand());
        registryCommand(new RemoveServerCommand());
        registryCommand(new RemoveLastServerCommand());
        registryCommand(new ReorderServerCommand());
        registryCommand(new HelpServerCommand());
        registryCommand(new UpdateServerCommand());
    }

    /**
     * Добавляет объект комманды в мапу
     */
    private void registryCommand(final AbstractServerCommand command) {
        if (command.command() == null || command.command().isEmpty()) {
            return;
        }
        command.init(serviceLocator);
        commands.put(command.command(), command);
    }

    public Message getMessage(String[] commandName) {
        try {
            if (commandName.length > 0 && !commandName[0].equals("")) {
                AbstractServerCommand command = commands.get(commandName[0]);
                return new Message(command);
            } else {
                System.out.println("Вы не ввели команду.");
            }
        } catch (NullPointerException ex) {
            System.out.println("Не существует команды " + commandName[0] + ". Для справки используйте – help");
        } catch (IllegalStateException ex) {
            if (ex.getMessage().equals("Connection reset by peer")) {
                System.out.println("Там это... Сервер помер, но ты приходи в следующий раз и обязательно сможешь пошалить с коллекцией");
                System.exit(0);
            }
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
