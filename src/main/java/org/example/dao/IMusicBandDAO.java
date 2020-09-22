package org.example.dao;

import org.example.enums.SortStatus;
import org.example.model.DataStorage;
import org.example.model.MusicBand;

import java.util.List;

/**
 * Интерфейс сервиса доступа к данным (Data Access Object)
 */
public interface IMusicBandDAO {

    void save(MusicBand musicBand);

    List<MusicBand> getAll();

    void update(MusicBand musicBand, Long id);

    void clear();

    boolean isPassportIDExists(String passportID);

    int getNumberById(Long id);

    MusicBand getByNumber(int number);

    MusicBand getById(Long id);

    void init(DataStorage dataStorage);

    DataStorage getData();

    void checkPassportIDUnique(String passportID);

    MusicBand getMinimal();

    MusicBand getByMaxId();

    MusicBand removeLast();

    MusicBand removeById(Long id);

    List<MusicBand> removeByDescription(String description);

    SortStatus reorder();

    List<MusicBand> filterByDescription(String description);
}
