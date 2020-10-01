package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.model.Message;

public class ExitCommand extends AbstractCommand {

    @Override
    public String command() {
        return "exit";
    }

    @Override
    public String description() {
        return "Завершить программу (без сохранения в файл)";
    }

    @Override
    public AbstractServerCommand serverCommand() {
        return null;
    }

    @Override
    public Message execute(String[] args) {
        consoleService.printLn("Программа закрывается. До свидания!");
        System.exit(0);
        return null;
    }
}
