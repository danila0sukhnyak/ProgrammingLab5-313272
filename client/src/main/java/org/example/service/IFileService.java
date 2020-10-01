package org.example.service;

import org.example.model.DataStorage;

/**
 * Интерфейс сервиса работы с файлом.
 */
public interface IFileService {

    boolean write(DataStorage musicBand);

    DataStorage readFromXml();
}
