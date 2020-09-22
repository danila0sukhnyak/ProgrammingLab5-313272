package org.example.bootstrap;

import org.example.command.AbstractCommand;
import org.example.dao.IMusicBandDAO;
import org.example.service.IConsoleService;
import org.example.service.IFileService;

import java.util.Map;

public interface ServiceLocator {
    IConsoleService getConsoleService();

    IFileService getFileService();

    IMusicBandDAO getMusicDAO();

    Map<String, AbstractCommand> getCommands();

    void executeCommands(String line);
}
