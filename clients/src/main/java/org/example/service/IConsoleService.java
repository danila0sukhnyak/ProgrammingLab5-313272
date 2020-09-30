package org.example.service;

import java.io.InputStream;

/**
 * Интерфейс сервиса работы с пользовательским вводом-выводом.
 */
public interface IConsoleService {

    void print(String message);

    void printLn(String message);

    String read();

    void setInputStream(InputStream inputStreamReader);

    void setSystemIn();
}
