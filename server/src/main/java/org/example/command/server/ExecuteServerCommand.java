package org.example.command.server;

import org.example.bootstrap.Bootstrap;
import org.example.bootstrap.ServiceLocator;
import org.example.exception.InterruptScriptException;
import org.example.model.Message;
import org.example.service.IConsoleService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ExecuteServerCommand extends AbstractServerCommand {

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
    public String execute(Message args) {
        if (args.getArgs() == null) {
            return "Не хватает аргумента";
        }

        try {
            File file = new File(args.getArgs());
            InputStreamReader fr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(fr);
            String line;
            consoleService.printLn("***Началось выполнение скрипта: " + args.getArgs() + "***");
            Bootstrap.execute_script_check.add("execute_script " + args.getArgs());
            ArrayList<String> lines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            serviceLocator.executeCommands(lines);
            Bootstrap.execute_script_check.remove("execute_script " + args.getArgs());
            return ("***Выполнение скрипта завершено: " + args.getArgs() + "***");
        } catch (IOException e) {
            return "Ошибка при чтении файла скрипта";
        } finally {
            consoleService.setSystemIn();
        }
    }
}
