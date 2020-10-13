package org.example.model;

import org.example.command.server.AbstractServerCommand;

import java.io.Serializable;
import java.nio.channels.SocketChannel;

public class Message implements Serializable {
    private static final long serialVersionUID = 33L;
    transient SocketChannel socketChannel;
    private String string;
    private AbstractServerCommand command;
    private String args;
    private MusicBand musicBand;
    private User user;

    public Message(String string) {
        this.string = string;
    }

    public Message(SocketChannel socketChannel, String string) {
        this.socketChannel = socketChannel;
        this.string = string;
    }

    public Message(AbstractServerCommand serverCommand, User user) {
        this.command = serverCommand;
        this.user = user;
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }

    public Message(AbstractServerCommand command, String args) {
        this.command = command;
        this.args = args;
    }

    public Message(AbstractServerCommand command, MusicBand musicBand, String args) {
        this.command = command;
        this.musicBand = musicBand;
        this.args = args;
    }

    public Message(SocketChannel socketChannel, AbstractServerCommand command) {
        this.socketChannel = socketChannel;
        this.command = command;
    }

    public Message(AbstractServerCommand command, MusicBand musicBand) {
        this.command = command;
        this.musicBand = musicBand;
    }

    public Message(AbstractServerCommand command) {
        this.command = command;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public AbstractServerCommand getCommand() {
        return command;
    }

    public void setCommand(AbstractServerCommand command) {
        this.command = command;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Message{" +
                "socketChannel=" + socketChannel +
                ", string='" + string + '\'' +
                ", command=" + command +
                ", args='" + args + '\'' +
                ", musicBand=" + musicBand +
                '}';
    }
}
