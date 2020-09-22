package org.example.command;

import org.example.bootstrap.Bootstrap;
import org.example.bootstrap.ServiceLocator;
import org.example.exception.InterruptScriptException;
import org.example.service.IConsoleService;

import java.io.*;

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

        try {
            File file = new File(args[1]);
            InputStreamReader fr = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader reader = new BufferedReader(fr);
            String line;
            consoleService.printLn("***Началось выполнение скрипта: " + args[1] + "***");
            Bootstrap.execute_script_check.add("execute_script " + args[1]);

            while ((line = reader.readLine()) != null) {
                if(!Bootstrap.execute_script_check.contains(line)) {
                    if(line.contains("execute_script")){
                        Bootstrap.execute_script_check.add(line);
                        serviceLocator.executeCommands(line);
                        Bootstrap.execute_script_check.remove(line);
                    }
                    else{
                        serviceLocator.executeCommands(line);
                    }
                }
            }
            Bootstrap.execute_script_check.remove("execute_script " + args[1]);
            consoleService.printLn("***Выполнение скрипта завершено: " + args[1] + "***");
        } catch (IOException e) {
            consoleService.printLn("Ошибка при чтении файла скрипта");
        } finally {
            consoleService.setSystemIn();
        }
    }
}
