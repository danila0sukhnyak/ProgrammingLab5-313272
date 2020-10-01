package org.example.bootstrap;

import org.example.command.server.AbstractServerCommand;
import org.example.model.Message;
import org.example.service.IConsoleService;

import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;

public interface ServiceLocator {
    IConsoleService getConsoleService();

    Map<String, AbstractServerCommand> getCommands();

    void executeCommands(ArrayList<String> line , Queue<Message> messages);

    Queue<Message> executeCommands(String line);

}
