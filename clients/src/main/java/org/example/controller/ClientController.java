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
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientController {
    private static final int BUFF_SIZE = 1000000;
    String hostname;
    int port;
    ServiceLocator serviceLocator;

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
                //connectionClient.register(selector, SelectionKey.OP_CONNECT);
                connectionClient.register(selector, SelectionKey.OP_WRITE);
                int reconect_schetchick = 1;

                Scanner scanner = new Scanner(System.in);
                //session.closeSession();
                System.out.println("Введите help");
                while (true) {
                    selector.select();

                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                    while (iterator.hasNext()) {
                        SelectionKey key = (SelectionKey) iterator.next();
                        //iterator.remove();
                        if (key.isValid()) {
                            SocketChannel client = (SocketChannel) key.channel();
                            if (client != null) {
                                try {

                                    if ((key.interestOps() & SelectionKey.OP_WRITE) != 0) {
                                        try {
                                            System.out.print(">");
                                            Message message = serviceLocator.getCommandService().getMessage(scanner.nextLine().trim().split(" "));
                                            if (message == null) {
                                                continue;
                                            } else {
                                                sendSocketObject(client, message);
                                                key.interestOps(SelectionKey.OP_READ);
                                                client.register(selector, SelectionKey.OP_READ);
                                                break;
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            System.out.println("Повторное подлючение");
                                            try {
                                                selector = Selector.open();
                                                connectionClient = SocketChannel.open();
                                                connectionClient.connect(new InetSocketAddress(hostname, port));
                                                connectionClient.configureBlocking(false);
                                                //connectionClient.register(selector, SelectionKey.OP_CONNECT);
                                            } catch (Exception ignored) {
                                            }
                                            key.interestOps(SelectionKey.OP_WRITE);
                                            client.register(selector, SelectionKey.OP_WRITE);
                                            continue;
                                        }
                                    }
                                    if ((key.interestOps() & SelectionKey.OP_READ) != 0) {
                                        Message message = getSocketObject(client);
                                        System.out.println(message.getString());
                                        key.interestOps(SelectionKey.OP_WRITE);
                                        client.register(selector, SelectionKey.OP_WRITE);
                                        Thread.sleep(500);
                                        continue;
                                    }
                                } catch (IOException | NoSuchElementException | InterruptedException e) {
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
