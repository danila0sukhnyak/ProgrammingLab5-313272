package org.example.bootstrap;

import org.example.command.*;
import org.example.command.server.AbstractServerCommand;
import org.example.controller.ClientController;
import org.example.dao.IMusicBandDAO;
import org.example.exception.InterruptApplicationException;
import org.example.exception.MusicBandWrongAttributeException;
import org.example.model.Message;
import org.example.service.ConsoleService;
import org.example.service.IConsoleService;
import org.example.service.IFileService;

import java.util.*;

/**
 * Класс-загрузчик, используется для старта приложения и доступа к необходимым сервисам.
 */
public class Bootstrap implements ServiceLocator {
    private IConsoleService consoleService = new ConsoleService();
    private Map<String, AbstractCommand> commands = new HashMap<>();
    public static ArrayList<String> execute_script_check = new ArrayList<>();
    public static boolean isEx = false;

    /**
     * Запускает приложение
     *
     * @param args аргументы приложения
     */
    public void start(String[] args) {
        initCommands();
        consoleService.printLn("***WELCOME TO MUSIC BAND COLLECTION***");

        ClientController clientController = new ClientController("127.0.0.1", "27015", this);
        clientController.run();
        System.out.println("Завершение работы");
        System.exit(0);
    }


    public Queue<Message> executeCommands(String line) {
        isEx = false;
        String[] params = line.split(" ");
        String command = params[0];
        if (!execute_script_check.contains(line)) {
            if (!commands.containsKey(command)) {
                consoleService.printLn("Такой комманды не существует, наберите help для справки");
            } else {
                try {
                    return commands.get(command).execute(params);
                }
                catch (MusicBandWrongAttributeException ignored){ }
                catch (InterruptApplicationException e) {
                    throw e;
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
        return new LinkedList<>();
    }

    @Override
    public IMusicBandDAO getMusicDAO() {
        return null;
    }

    @Override
    public IFileService getFileService() {
        return null;
    }

    public void executeCommands(ArrayList<String> lines, Queue<Message> messages) {
        isEx = true;
        ConsoleService.tmp = lines;
        for (String line : lines) {
            String[] params = line.split(" ");
            String command = params[0];
            if (!execute_script_check.contains(line)) {
                if (!commands.containsKey(command)) {
                    consoleService.printLn("Такой комманды не существует, наберите help для справки");
                } else {
                    try {
                        messages.addAll(commands.get(command).execute(params));
                    } catch (Exception e) {
                       //e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Инициализирует комманды
     */
    private void initCommands() {
        registryCommand(new HelpCommand());
        registryCommand(new ExitCommand());
        registryCommand(new AddCommand());
        registryCommand(new AddIfMinCommand());
        registryCommand(new ClearCommand());
        registryCommand(new ExecuteCommand());
        registryCommand(new FilterByDescriptionCommand());
        registryCommand(new InfoCommand());
        registryCommand(new MaxByIdCommand());
        registryCommand(new RemoveByDescriptionCommand());
        registryCommand(new RemoveCommand());
        registryCommand(new RemoveLastCommand());
        registryCommand(new ReorderCommand());
        registryCommand(new ShowCommand());
        registryCommand(new UpdateCommand());
        registryCommand(new AuthCommand());
        registryCommand(new RegisterCommand());
    }

    /**
     * Добавляет объект комманды в мапу
     */
    private void registryCommand(final AbstractCommand command) {
        if (command.command() == null || command.command().isEmpty()) {
            return;
        }
        command.init(this);
        commands.put(command.command(), command);
    }

    public IConsoleService getConsoleService() {
        return consoleService;
    }

    public void setConsoleService(IConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    public Map<String, AbstractServerCommand> getCommands() {
        return Collections.emptyMap();
    }

    public void setCommands(Map<String, AbstractCommand> commands) {
        this.commands = commands;
    }
}
