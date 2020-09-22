package org.example;

import org.example.bootstrap.Bootstrap;

/**
 * Главный класс приложения
 */
public class App {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.start(args);
    }
}
