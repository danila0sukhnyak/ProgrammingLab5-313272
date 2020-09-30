package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.model.DataStorage;

import java.io.*;

/**
 * Реализация сервиса работы с файлом. Хранит путь к файлу и сервис работы с консолью
 */
public class FileService implements IFileService {
    private String path;
    private IConsoleService consoleService;

    public FileService(String path, IConsoleService consoleService) {
        this.path = path;
        this.consoleService = consoleService;
    }

    /**
     * Создает {@link FileOutputStream} из файла, оборачивает его в {@link PrintWriter}
     * использует его в {@link XmlMapper} для записи объекта в xml файл.
     * Отлавливает возникающие ошибки и выводит их в {@link IConsoleService}
     *
     * @param musicBand объект для записи
     * @return флаг успешности операции
     */
    @Override
    public boolean write(DataStorage musicBand) {
        final ObjectMapper mapper = new XmlMapper();
        mapper.registerModule(new JavaTimeModule());
        final File file = new File(path);
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            PrintWriter printWriter = new PrintWriter(outputStream);
            mapper.writerWithDefaultPrettyPrinter().writeValue(printWriter, musicBand);
            return true;
        } catch (FileNotFoundException e) {
            consoleService.printLn("Файл не найден");
        } catch (IOException e) {
            consoleService.printLn("Ошибка при записи в файл");
        }
        return false;
    }

    /**
     * Создает {@link FileInputStream} из файла, оборачивает его в {@link InputStreamReader}
     * использует его в {@link XmlMapper} для чтения объекта из xml файла.
     * Отлавливает возникающие ошибки и выводит их в {@link IConsoleService}
     *
     * @return объект, считанный из файла (null в случае возникновения ошибок)
     */
    @Override
    public DataStorage readFromXml() {
        final ObjectMapper mapper = new XmlMapper();
        mapper.registerModule(new JavaTimeModule());
        File file = new File(path);
        InputStreamReader inputStreamReader;
        try (FileInputStream inputStream = new FileInputStream(file)) {
            inputStreamReader = new InputStreamReader(inputStream);
            return mapper.readValue(inputStreamReader, DataStorage.class);
        } catch (FileNotFoundException e) {
            consoleService.printLn("Файл не найден");
        } catch (IOException e) {
            consoleService.printLn("Ошибка при чтении файла");
            e.printStackTrace();
        }
        return null;
    }
}
