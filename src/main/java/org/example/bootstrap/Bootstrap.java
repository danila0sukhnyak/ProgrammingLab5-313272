package org.example.bootstrap;

import org.example.command.*;
import org.example.dao.IMusicBandDAO;
import org.example.dao.MusicBandDAO;
import org.example.exception.InterruptScriptException;
import org.example.model.DataStorage;
import org.example.service.ConsoleService;
import org.example.service.FileService;
import org.example.service.IConsoleService;
import org.example.service.IFileService;

import java.util.*;

/**
 * Класс-загрузчик, используется для старта приложения и доступа к необходимым сервисам.
 */
public class Bootstrap implements ServiceLocator {
    private IMusicBandDAO musicDAO = new MusicBandDAO();
    private IConsoleService consoleService = new ConsoleService();
    private IFileService fileService = new FileService("", consoleService);
    private Map<String, AbstractCommand> commands = new HashMap<>();
    public static ArrayList<String> execute_script_check = new ArrayList<>();


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
        initCommands();
        consoleService.printLn("***WELCOME TO MUSIC BAND COLLECTION***");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            executeCommands(line);
        }
        System.out.println("Завершение работы");
        System.exit(0);
    }

    /**
     * Начинает исполнение введенных с консоли комманд в цикле с условием выхода введенная строка = "exit"
     *
     * @throws InterruptScriptException перебрасывается с уровня выше
     */
    public void executeCommands(String line) {
        String command = "";
        try {
            String[] params = line.split(" ");
            command = params[0];
            if (!commands.containsKey(command)) {
                consoleService.printLn("Такой комманды не существует, наберите help для справки");
            } else {
                commands.get(command).execute(params);
            }
        } catch (Exception ignored){}

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
        registryCommand(new SaveCommand());
        registryCommand(new ShowCommand());
        registryCommand(new UpdateCommand());
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

    public Map<String, AbstractCommand> getCommands() {
        return commands;
    }

    public void setCommands(Map<String, AbstractCommand> commands) {
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
