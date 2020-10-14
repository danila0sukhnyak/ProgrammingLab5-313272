package org.example.controller;

import org.example.bootstrap.ServiceLocator;
import org.example.command.server.AbstractServerCommand;
import org.example.enums.AuthState;
import org.example.model.Message;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerController {
    private static final int BUFF_SIZE = 1000000;
    static String host = "127.0.0.1";
    static int port = 27015;
    private final ServiceLocator serviceLocator;
    private static final List<Message> incomingMessages = Collections.synchronizedList(new LinkedList<>());
    private static final List<Message> outcomingMessages = Collections.synchronizedList(new LinkedList<>());

    static Iterator<SelectionKey> iterator;
    static Selector selector;
    static ServerSocketChannel server;

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
            selector = Selector.open();
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.socket().bind(new InetSocketAddress(host, port));
            server.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Server started");

            StringBuilder console_line = new StringBuilder();
            while (true) {
                console_line = CheckCommands(console_line);
                selector.select();
                iterator = selector.selectedKeys().iterator();
                ExecutorService executorService = Executors.newFixedThreadPool(9);
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (selector.isOpen()) {
                        Thread connector = new Thread(new Connector(key));
                        connector.setDaemon(true);
                        connector.start();
                        executorService.submit(new Reader(key));
                        Thread executor = new Thread(new CommandExecute(key, serviceLocator));
                        executor.setDaemon(true);
                        executor.start();
                        Thread writer = new Thread(new Writer(key));
                        writer.setDaemon(true);
                        writer.start();
                        executor.join();
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
        if (s.equals("exit")) {
            System.out.println("Выход из программы");
            System.exit(0);
        }
    }

    static class Writer implements Runnable {
        SelectionKey key;

        Writer(SelectionKey key) {
            this.key = key;
        }

        @Override
        public void run() {
            if (key.isValid() && key.isWritable()) {
                SocketChannel channel = (SocketChannel) key.channel();
                synchronized (outcomingMessages) {
                    for (Message mes : outcomingMessages) {
                        if (mes.getSocketChannel().equals(channel)) {
                            try {
                                sendSocketObject(channel, mes);
                                outcomingMessages.remove(mes);
                                channel.register(selector, SelectionKey.OP_READ);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    static class CommandExecute implements Runnable {
        SelectionKey key;
        ServiceLocator serviceLocator;

        public CommandExecute(SelectionKey key, ServiceLocator serviceLocator) {
            this.key = key;
            this.serviceLocator = serviceLocator;
        }

        @Override
        public void run() {
            String result = "Неизвестная команда";
            synchronized (incomingMessages) {
                for (Message mes : incomingMessages) {
                    AbstractServerCommand command = mes.getCommand();
                    if (command != null) {
                        String auth = serviceLocator.auth(mes.getUser());
                        if (!AuthState.AUTH_SUCCESS.name().equals(auth) && !command.command().equals("register")) {
                            result = "Ошибка авторизации";
                        } else {
                            command.init(serviceLocator);
                            try {
                                result = command.execute(mes);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    Message message = new Message(result);
                    message.setSocketChannel(mes.getSocketChannel());
                    synchronized (incomingMessages) {
                        incomingMessages.remove(mes);
                    }
                    synchronized (outcomingMessages) {
                        outcomingMessages.add(message);
                    }
                }
            }
        }
    }

    static class Reader implements Runnable {
        SelectionKey key;

        Reader(SelectionKey key) {
            this.key = key;
        }

        @Override
        public void run() {
            if (key.isValid() && key.isReadable()) {
                SocketChannel channel = (SocketChannel) key.channel();
                try {
                    synchronized (incomingMessages) {
                        Message message = getSocketObject(channel);
                        message.setSocketChannel(channel);
                        incomingMessages.add(message);
                        channel.register(selector, SelectionKey.OP_WRITE);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Клиент отключился");
                    try {
                        channel.close();
                        server.register(selector, SelectionKey.OP_ACCEPT);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }

    static class Connector implements Runnable {
        SelectionKey key;

        Connector(SelectionKey key) {
            this.key = key;
        }

        @Override
        public void run() {
            if (key.isValid() && key.isAcceptable()) {
                try {
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                    System.out.println("Новое подключение");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
