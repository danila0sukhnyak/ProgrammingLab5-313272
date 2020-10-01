package org.example.bootstrap;

import org.example.command.server.*;
import org.example.command.server.AddServerCommand;
import org.example.controller.ServerController;
import org.example.dao.IMusicBandDAO;
import org.example.dao.MusicBandDAO;
import org.example.model.DataStorage;
import org.example.model.Message;
import org.example.service.ConsoleService;
import org.example.service.FileService;
import org.example.service.IConsoleService;
import org.example.service.IFileService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс-загрузчик, используется для старта приложения и доступа к необходимым сервисам.
 */
public class Bootstrap implements ServiceLocator {
    private IMusicBandDAO musicDAO = new MusicBandDAO();
    private IConsoleService consoleService = new ConsoleService();
    private IFileService fileService = new FileService("", consoleService);
    private Map<String, AbstractServerCommand> commands = new HashMap<>();
    public static ArrayList<String> execute_script_check = new ArrayList<>();
    public static boolean isEx = false;

    /**
     * Запускает приложение
     *
     * @param args аргументы приложения
     */
    public void start(String[] args) {
        if (args != null && args.length > 0) {
            initCollection(args[0]);

        } else {
            consoleService.printLn("Отсутвует аргумент с адресом файла, коллекция не загружена");
        }
        consoleService.printLn("***WELCOME TO MUSIC BAND COLLECTION***");
        initCommands();
        ServerController controller = new ServerController(this);
        controller.run();
        System.out.println("Завершение работы");
        System.exit(0);
    }


    public void executeCommands(ArrayList<String> lines) {
        isEx = true;
        ConsoleService.tmp = lines;
        for (String line : lines) {
            String[] params = line.split(" ");
            String command = params[0];
            if(!execute_script_check.contains(line)) {
                if (!commands.containsKey(command)) {
                    consoleService.printLn("Такой комманды не существует, наберите help для справки");
                } else {
                    try {
                        commands.get(command).execute(new Message(command));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Инициализирует коллекцию данными из файла
     *
     * @param arg путь к файлу
     */
    private void initCollection(String arg) {
        fileService = new FileService(arg, consoleService);
        DataStorage dataStorage = fileService.readFromXml();
        if (dataStorage != null) {
            musicDAO.init(dataStorage);
        }
    }

    /**
     * Инициализирует комманды
     */
    private void initCommands() {
        registryCommand(new AddIfMinServerCommand());
        registryCommand(new AddServerCommand());
        registryCommand(new ClearServerCommand());
        registryCommand(new ExecuteServerCommand());
        registryCommand(new FilterByDescriptionServerCommand());
        registryCommand(new HelpServerCommand());
        registryCommand(new InfoServerCommand());
        registryCommand(new MaxByIdServerCommand());
        registryCommand(new RemoveByDescriptionServerCommand());
        registryCommand(new RemoveLastServerCommand());
        registryCommand(new RemoveServerCommand());
        registryCommand(new ReorderServerCommand());
        registryCommand(new ShowServerCommand());
        registryCommand(new UpdateServerCommand());
    }

    /**
     * Добавляет объект комманды в мапу
     */
    private void registryCommand(final AbstractServerCommand command) {
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
        return commands;
    }

    public void setCommands(Map<String, AbstractServerCommand> commands) {
        this.commands = commands;
    }

    public IFileService getFileService() {
        return fileService;
    }

    public void setFileService(IFileService fileService) {
        this.fileService = fileService;
    }

    public IMusicBandDAO getMusicDAO() {
        return musicDAO;
    }

    public void setMusicDAO(IMusicBandDAO musicDAO) {
        this.musicDAO = musicDAO;
    }


}
