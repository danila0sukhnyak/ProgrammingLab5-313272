package org.example.command;

import org.example.bootstrap.ServiceLocator;
import org.example.exception.InterruptScriptException;
import org.example.service.IConsoleService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExecuteCommand extends AbstractCommand {

    @Override
    public String command() {
        return "execute_script";
    }

    @Override
    public String description() {
        return "Считать и исполнить скрипт из указанного файла";
    }

    /**
     * Создает {@link InputStream} из файла, устанавливает его в {@link IConsoleService}
     * взамен {@code System.in} затем запускает исполнение комманд в {@link ServiceLocator}
     * Исполнение скрипта прерывется, когда из {@link ServiceLocator} приходит исключение
     * {@link InterruptScriptException}, после исполнения скрипта закрывает поток файла и
     * устанавливает в {@link IConsoleService} консольный поток {@code System.in}
     *
     * @param args аргументы (1 - путь к файлу со скриптом)
     */
    @Override
    public void execute(String[] args) {
        if (args.length < 2 || args[1] == null) {
            consoleService.printLn("Не хватает аргумента");
            return;
        }
        File file = new File(args[1]);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            consoleService.setInputStream(inputStream);
            consoleService.printLn("***Началось выполнение скрипта***");
            serviceLocator.executeCommands();
        } catch (InterruptScriptException e) {
            consoleService.printLn("***Выполнение скрипта завершено***");
        } catch (IOException e) {
            consoleService.printLn("Ошибка при чтении файла скрипта");
        } finally {
            consoleService.setSystemIn();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
