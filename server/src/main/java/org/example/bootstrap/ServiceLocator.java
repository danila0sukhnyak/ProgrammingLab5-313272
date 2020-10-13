package org.example.bootstrap;

import org.example.command.server.AbstractServerCommand;
import org.example.dao.DBController;
import org.example.dao.IMusicBandDAO;
import org.example.service.IConsoleService;
import org.example.service.IFileService;

import java.util.ArrayList;
import java.util.Map;

public interface ServiceLocator {
    IConsoleService getConsoleService();

    IFileService getFileService();

    IMusicBandDAO getMusicDAO();

    DBController getDbController();

    Map<String, AbstractServerCommand> getCommands();

    void executeCommands(ArrayList<String> line);
}
