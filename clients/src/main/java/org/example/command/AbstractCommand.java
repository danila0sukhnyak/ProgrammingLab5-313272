package org.example.command;

import org.example.bootstrap.ServiceLocator;
import org.example.command.server.AbstractServerCommand;
import org.example.model.Message;
import org.example.service.IConsoleService;

import java.io.Serializable;
import java.util.Queue;

/**
 * Абстракный класс консольной комманды.
 */
public abstract class AbstractCommand implements Serializable {
    private static final long serialVersionUID = 33L;

    protected transient IConsoleService consoleService;

    protected transient ServiceLocator serviceLocator;

    public void init(ServiceLocator serviceLocator) {
        this.consoleService = serviceLocator.getConsoleService();
        this.serviceLocator = serviceLocator;
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
    public abstract Queue<Message> execute(String[] args) throws Exception;
}
