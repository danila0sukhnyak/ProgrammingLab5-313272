package org.example.command;

import org.example.command.server.AbstractServerCommand;
import org.example.command.server.ShowServerCommand;
import org.example.model.Message;
import org.example.model.MusicBand;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShowCommand extends AbstractCommand {

    @Override
    public String command() {
        return "show";
    }

    @Override
    public String description() {
        return "Вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public AbstractServerCommand serverCommand() {
        return new ShowServerCommand();
    }

    @Override
    public Queue<Message> execute(String[] args) {
        return new LinkedList<>(Arrays.asList(new Message(serverCommand())));
    }
}
