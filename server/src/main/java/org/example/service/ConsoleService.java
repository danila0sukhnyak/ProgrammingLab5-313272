package org.example.service;

import org.example.bootstrap.Bootstrap;
import org.example.exception.InterruptScriptException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Реализация сервиса работы с пользовательским вводом-выводом.
 */
public class ConsoleService implements IConsoleService {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private boolean isSystemIn = true;
    public static ArrayList<String> tmp = new ArrayList<>();
    public void setInputStream(InputStream inputStream) {
        this.isSystemIn = false;
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public void setSystemIn() {
        this.isSystemIn = true;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void print(String message) {
        System.out.print(message);
    }

    @Override
    public void printLn(String message) {
        System.out.println(message);
    }

    @Override
    public String read() {
        if(Bootstrap.isEx) {
            String result = ConsoleService.tmp.get(1);
            System.out.println(result);
            ConsoleService.tmp.remove(1);
            return result;
        }
        else{
            String s = null;
            try {
                if (!isSystemIn && !reader.ready()) {
                    throw new InterruptScriptException();
                }
                s = reader.readLine();
                if (!isSystemIn) {
                    printLn(s);
                }
            } catch (IOException e) {
                printLn("Ошибка потока данных");
            }
            return s;
        }
    }
}
