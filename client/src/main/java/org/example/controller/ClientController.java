package org.example.controller;

import org.example.bootstrap.ServiceLocator;
import org.example.model.Message;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;

import static java.lang.Thread.sleep;

public class ClientController {
    private static final int BUFF_SIZE = 1000000;
    String hostname;
    int port;
    ServiceLocator serviceLocator;
    private Queue<Message> messages = new LinkedList<>();
    private Scanner scanner = new Scanner(System.in);

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
                System.out.println("Введите help");
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
                                } catch (StreamCorruptedException e) {
                                    for (int i = 0; i < 100; i++) {
                                        try {
                                            System.out.println("Попытка подключения к серверу: " + i);
                                            selector = Selector.open();
                                            connectionClient = SocketChannel.open();
                                            connectionClient.connect(new InetSocketAddress(hostname, port));
                                            connectionClient.configureBlocking(false);
                                            connectionClient.register(selector, SelectionKey.OP_WRITE);
                                            System.out.println("Введите help");
                                            break;
                                        } catch (Exception not_ignored) {
                                            Thread.sleep(1000);
                                        }
                                    }
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

    private boolean WriteThread(Selector selector, SelectionKey key, SocketChannel client) throws IOException {
        if ((key.interestOps() & SelectionKey.OP_WRITE) != 0) {
            if (messages.isEmpty()) { fillMessagesQueue(); }
            Message message = messages.poll();
            if (message != null) {
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
            System.out.println(message.getString());
            key.interestOps(SelectionKey.OP_WRITE);
            client.register(selector, SelectionKey.OP_WRITE);
            return true;
        }
        return false;
    }

    private void fillMessagesQueue() {
        System.out.print(">");
        messages = serviceLocator.executeCommands(scanner.nextLine());
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
