package org.example;

import org.example.bootstrap.Bootstrap;

public class ServerStart {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.start(args);
    }
}
