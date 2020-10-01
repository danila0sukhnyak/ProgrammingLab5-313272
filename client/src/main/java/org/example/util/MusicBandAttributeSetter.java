package org.example.util;

import org.example.exception.InterruptInputException;
import org.example.model.MusicBand;
import org.example.service.IConsoleService;

import java.util.function.Consumer;

/**
 * Сервис
 */
public class MusicBandAttributeSetter {
    private final IConsoleService consoleService;

    public MusicBandAttributeSetter(IConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    /**
     * Применяет функцию {@code consumer} к объекту,
     * в случае возникновения ошибки выводит ее текст и спрашивает о повторном вводе,
     * в случае отрицательного ответа выбрасывет {@link InterruptInputException}
     *
     * @param musicBand объект для заполнения
     * @param message   текст приглашения к вводу значения
     * @param consumer  функция, заполняющая нужное поле
     */
    public void setAttribute(MusicBand musicBand,
                             String message,
                             Consumer<MusicBand> consumer) {
        boolean stop = false;
        while (!stop) {
            consoleService.printLn(message);
            try {
                consumer.accept(musicBand);
                stop = true;
            } catch (NumberFormatException e) {
                consoleService.printLn("Требуется ввести число");
                askDoAgain();
            } catch (IllegalArgumentException e) {
                consoleService.printLn("Неверный ввод (введите значение из квадратных скобок)");
                askDoAgain();
            }
        }
    }

    private void askDoAgain() {
        consoleService.printLn("Повторить ввод - любой символ, выйти - exit");
        if ("exit".equals(consoleService.read())) {
            throw new InterruptInputException();
        }
    }
}
