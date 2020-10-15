package org.example.controller;

import org.example.bootstrap.ServiceLocator;
import org.example.enums.AuthState;
import org.example.model.Message;
import org.example.model.User;
import org.example.util.UserHolder;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;

public class ClientController {
    private static final int BUFF_SIZE = 1000000;
    String hostname;
    int port;
    ServiceLocator serviceLocator;
    private Queue<Message> messages = new LinkedList<>();
    private Scanner scanner = new Scanner(System.in);
    private User user = null;

    public ClientController(String hostname, String port, ServiceLocator serviceLocator) {
        this.hostname = hostname;
        this.port = Integer.parseInt(port);
        this.serviceLocator = serviceLocator;
    }

    public void run() {
        if (hostname == null || port == 0) {
            System.out.println("Класс не инициализирован");
            throw new RuntimeException("Не инициализирован hostname и port");
        } else {
            try {
                Selector selector = Selector.open();
                SocketChannel connectionClient = SocketChannel.open();
                connectionClient.connect(new InetSocketAddress(hostname, port));
                connectionClient.configureBlocking(false);
                connectionClient.register(selector, SelectionKey.OP_WRITE);
                System.out.println("Введите auth для авторизации, register для регисрации. help - помощь");
                while (true) {
                    selector.select();
                    for (SelectionKey key : selector.selectedKeys()) {
                        //iterator.remove(); не работает на helios
                        if (key.isValid()) {
                            SocketChannel client = (SocketChannel) key.channel();
                            if (client != null) {
                                try {
                                    if (WriteThread(selector, key, client)) continue;
                                    if (ReadThread(selector, key, client)) continue;
                                } catch (StreamCorruptedException | SocketException e) {
                                    selector = tryReconnect(selector);
                                } catch (IOException | NoSuchElementException e) {
                                    System.out.println("Завершение работы.");
                                    client.close();
                                    e.printStackTrace();
                                    System.exit(0);
                                }
                            }
                        }
                    }
                }
            } catch (ConnectException e) {
                System.out.println("Невозможно подключиться к данному хосту или порту");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Selector tryReconnect(Selector selector) throws InterruptedException {
        SocketChannel connectionClient;
        boolean isComplete = false;
        for (int i = 1; i <= 100; i++) {
            try {
                System.out.println("Попытка подключения к серверу: " + i);
                selector = Selector.open();
                connectionClient = SocketChannel.open();
                connectionClient.connect(new InetSocketAddress(hostname, port));
                connectionClient.configureBlocking(false);
                connectionClient.register(selector, SelectionKey.OP_WRITE);
                System.out.println("Введите help");
                isComplete = true;
                break;
            } catch (Exception not_ignored) {
                Thread.sleep(1000);
            }
        }
        if (!isComplete) {
            System.out.println("Завершение работы, сервер наелся и спит.");
            System.exit(0);
        }
        return selector;
    }

    private boolean WriteThread(Selector selector, SelectionKey key, SocketChannel client) throws IOException {
        if ((key.interestOps() & SelectionKey.OP_WRITE) != 0) {
            if (messages.isEmpty()) {
                fillMessagesQueue();
            }
            Message message = messages.poll();
            if (message != null) {
                user = message.getUser();
                if (!"register".equals(message.getCommand().command())) {
                    message.setUser(UserHolder.getUser());
                }
                sendSocketObject(client, message);
                key.interestOps(SelectionKey.OP_READ);
                client.register(selector, SelectionKey.OP_READ);
            }
            return true;
        }
        return false;
    }

    private boolean ReadThread(Selector selector, SelectionKey key, SocketChannel client) throws IOException, ClassNotFoundException {
        if ((key.interestOps() & SelectionKey.OP_READ) != 0) {
            Message message = getSocketObject(client);
            if (message.getString().equals(AuthState.AUTH_SUCCESS.name())) {
               UserHolder.setUser(user);
                System.out.println("Успешная авторизация");
            } else {
                System.out.println(message.getString());
            }
            key.interestOps(SelectionKey.OP_WRITE);
            client.register(selector, SelectionKey.OP_WRITE);
            return true;
        }
        return false;
    }

    private void fillMessagesQueue() {
        System.out.print(">");
        if (scanner.hasNextLine()) {
            messages = serviceLocator.executeCommands(scanner.nextLine());
        } else {
            System.out.println("Завершение работы");
            System.exit(0);
        }
    }

    public static Message getSocketObject(SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        ByteBuffer data = ByteBuffer.allocate(BUFF_SIZE);
        socketChannel.read(data);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (Message) objectInputStream.readObject();
    }

    private static void sendSocketObject(SocketChannel client, Message message) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        client.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
    }
}
