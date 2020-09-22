package org.example.command;

import org.example.bootstrap.ServiceLocator;
import org.example.dao.IMusicBandDAO;
import org.example.service.IConsoleService;
import org.example.service.IFileService;

import java.util.Map;

/**
 * Абстракный класс консольной комманды.
 */
public abstract class AbstractCommand {
    protected IConsoleService consoleService;

    protected IMusicBandDAO musicBandDAO;

    protected IFileService fileService;

    protected ServiceLocator serviceLocator;

    protected Map<String, AbstractCommand> commands;

    public void init(ServiceLocator serviceLocator) {
        this.musicBandDAO = serviceLocator.getMusicDAO();
        this.consoleService = serviceLocator.getConsoleService();
        this.fileService = serviceLocator.getFileService();
        this.serviceLocator = serviceLocator;
        this.commands = serviceLocator.getCommands();
    }

    /**
     * @return имя комманды (как она вводится в консоль)
     */
    public abstract String command();

    /**
     * @return описание команды
     */
    public abstract String description();

    /**
     * Выполнение комманды
     *
     * @param args аргументы
     * @throws Exception ошибки при выполнении комманды
     */
    public abstract void execute(String[] args) throws Exception;
}
