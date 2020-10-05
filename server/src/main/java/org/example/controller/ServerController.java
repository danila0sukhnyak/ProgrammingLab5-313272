package org.example.controller;

import org.example.bootstrap.ServiceLocator;
import org.example.command.server.AbstractServerCommand;
import org.example.dao.IMusicBandDAO;
import org.example.model.DataStorage;
import org.example.model.Message;
import org.example.service.IFileService;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ServerController {
    private static final int BUFF_SIZE = 1000000;
    static String host = "127.0.0.1";
    static int port = 27015;
    private final ServiceLocator serviceLocator;
    private static final List<Message> incomingMessages = new LinkedList<Message>();

    public ServerController(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    public ServerController(ServiceLocator serviceLocator, String host, String port) {
        this.serviceLocator = serviceLocator;
        ServerController.host = host;
        ServerController.port = Integer.parseInt(port);
    }

    public void run() {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.socket().bind(new InetSocketAddress(host, port));
            server.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Server started");

            StringBuilder console_line = new StringBuilder();
            while (true) {
                console_line = CheckCommands(console_line);
                selector.selectNow();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    /*
                    Замедление сервера, хз зачем это
                    На всякий случай
                     */
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    SelectionKey key = (SelectionKey) iterator.next();
                    iterator.remove();
                    if (selector.isOpen()) {
                        if (CheckKey(key)) continue;
                        if (ConnectThread(selector, server, key)) continue;
                        if (ReadThread(selector, key)) continue;
                        if (WriteThread(selector, key)) continue;
                    }
                }
            }
        } catch (SocketException e) {
            System.out.println("Порт занят");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Приложение завершено");
            e.printStackTrace();
        }
    }

    private StringBuilder CheckCommands(StringBuilder console_line) throws IOException {
        if (System.in.available() > 0) {
            int ch = 0;
            while (true) {
                ch = System.in.read();
                if (ch == 10) {
                    break;
                } else {
                    console_line.append((char) ch);
                }
            }
            server_commands(console_line.toString());
            console_line = new StringBuilder();
        }
        return console_line;
    }

    private boolean WriteThread(Selector selector, SelectionKey key) throws Exception {
        if (key.isWritable() && key.isValid()) {
            SocketChannel channel = (SocketChannel) key.channel();
            String result = "Неизвестная команда";
            for (Message mes : incomingMessages) {
                if (mes.getSocketChannel().equals(channel)) {
                    AbstractServerCommand command = mes.getCommand();
                    if (command != null) {
                        command.init(serviceLocator);
                        result = command.execute(mes);
                    }
                    incomingMessages.remove(mes);
                }
            }
            sendSocketObject(channel, new Message(result));
            channel.register(selector, SelectionKey.OP_READ);
            return true;
        }
        return false;
    }

    private boolean ReadThread(Selector selector, SelectionKey key) throws ClassNotFoundException, IOException {
        if (key.isReadable() && key.isValid()) {
            SocketChannel channel = (SocketChannel) key.channel();
            try {
                Message message = getSocketObject(channel);
                message.setSocketChannel(channel);
                incomingMessages.add(message);
                System.out.println(message + " : " + message.getCommand() + " : " + message.getString() + " : " + message.getArgs());
                channel.register(selector, SelectionKey.OP_WRITE);
            } catch (IOException e) {
                System.out.println("Клиент отключился");
                channel.close();
            }
            return true;

        }
        return false;
    }

    private boolean ConnectThread(Selector selector, ServerSocketChannel server, SelectionKey key) throws IOException {
        if (key.isAcceptable() && key.isValid()) {
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
            System.out.println("Новое подключение");
            return true;
        }
        return false;
    }

    private boolean CheckKey(SelectionKey key) {
        return !key.isValid();
    }

    public static Message getSocketObject(SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        ByteBuffer data = ByteBuffer.allocate(BUFF_SIZE);
        socketChannel.read(data);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Message message = (Message) objectInputStream.readObject();
        message.setSocketChannel(socketChannel);
        return message;
    }

    private static void sendSocketObject(SocketChannel client, Message message) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        client.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
    }

    private void server_commands(String s) {
        if (s.equals("save")) {
            save();
        } else if (s.equals("exit")) {
            System.out.println("Выход из программы");
            save();
            System.exit(0);
        }
    }

    private void save() {
        IMusicBandDAO musicDAO = serviceLocator.getMusicDAO();
        IFileService fileService = serviceLocator.getFileService();
        DataStorage data = musicDAO.getData();
        if (fileService.write(data)) {
            System.out.println("Успешно сохранено");
        } else {
            System.out.println("Ошибка при сохранении в файл");
        }
    }
}
