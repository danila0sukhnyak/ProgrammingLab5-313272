package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataStorage implements Serializable {

    public DataStorage(List<MusicBand> bands, LocalDateTime initDate, String type) {
        this.bands = bands;
        this.initDate = initDate;
        this.type = type;
    }

    public DataStorage() {
    }

    private List<MusicBand> bands = new ArrayList<>();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime initDate;

    private String type;

    public List<MusicBand> getBands() {
        return bands;
    }

    public void setBands(List<MusicBand> bands) {
        this.bands = bands;
    }

    public LocalDateTime getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDateTime initDate) {
        this.initDate = initDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Размер коллекции: " + bands.size() +
                ", Дата инициализации: " + initDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) +
                ", Тип коллекции: " + type;
    }
}
