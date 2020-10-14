package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.exception.InterruptApplicationException;
import org.example.model.Message;

import java.util.LinkedList;
import java.util.Queue;

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
    public Queue<Message> execute(String[] args) {
        System.exit(0);
        return null;
    }
}