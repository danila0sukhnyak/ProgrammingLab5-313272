package org.example.command;

import org.example.bootstrap.ServiceLocator;
import org.example.command.server.AbstractServerCommand;
import org.example.dao.IMusicBandDAO;
import org.example.model.Message;
import org.example.service.IConsoleService;
import org.example.service.IFileService;

import java.io.Serializable;
import java.util.Map;

/**
 * Абстракный класс консольной комманды.
 */
public abstract class AbstractCommand implements Serializable {
    private static final long serialVersionUID = 33L;

    protected transient IConsoleService consoleService;

    protected transient IMusicBandDAO musicBandDAO;

    protected transient IFileService fileService;

    protected transient ServiceLocator serviceLocator;

    protected transient Map<String, AbstractCommand> commands;

    public void init(ServiceLocator serviceLocator) {
        this.musicBandDAO = serviceLocator.getMusicDAO();
        this.consoleService = serviceLocator.getConsoleService();
        this.fileService = serviceLocator.getFileService();
        this.serviceLocator = serviceLocator;
//        this.commands = serviceLocator.getCommands();
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
     * @return какую серверную команду вызывать
     */
    public abstract AbstractServerCommand serverCommand();

    /**
     * Выполнение комманды
     *
     * @param args аргументы
     * @throws Exception ошибки при выполнении комманды
     */
    public abstract Message execute(String[] args) throws Exception;
}
