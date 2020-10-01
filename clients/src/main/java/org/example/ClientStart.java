package org.example;

import org.example.bootstrap.Bootstrap;

public class ClientStart {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.start(args);
    }
}
