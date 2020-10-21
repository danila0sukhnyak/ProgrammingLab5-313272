package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.enums.MusicGenre;
import org.example.exception.MusicBandWrongAttributeException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MusicBand implements Comparable<MusicBand>, Serializable {
    public MusicBand() {
        this.creationDate = LocalDateTime.now();
    }

    private Long id;

    private String name;

    private Coordinates coordinates;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    private Integer numberOfParticipants;

    private String description;

    private MusicGenre genre;

    private Person frontMan;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new MusicBandWrongAttributeException("Поле не может быть null");
        }
        if (name.trim().equals("")) {
            throw new MusicBandWrongAttributeException("Строка не может быть пустой");
        }
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new MusicBandWrongAttributeException("Поле не может быть null");
        }
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }


    public Integer getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(Integer numberOfParticipants) {
        if (numberOfParticipants == null) {
            throw new MusicBandWrongAttributeException("Поле не может быть null");
        }
        if (numberOfParticipants <= 0) {
            throw new MusicBandWrongAttributeException("Значение поля должно быть больше 0");
        }
        this.numberOfParticipants = numberOfParticipants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if ("".equals(description)) {
            this.description = null;
        }
        this.description = description;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        if (genre == null || "".equals(genre)) {
            this.genre = null;
        }
        this.genre = MusicGenre.valueOf(genre);
    }

    public Person getFrontMan() {
        return frontMan;
    }

    public void setFrontMan(Person frontMan) {
        if (frontMan == null) {
            throw new MusicBandWrongAttributeException("Поле не может быть null");
        }
        this.frontMan = frontMan;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        if (creationDate != null) {
            this.creationDate = creationDate;
        }
    }

    @Override
    public int compareTo(MusicBand o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", Название: " + name +
                ", Дата добавления: " + creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) +
                ", Количество участников: " + numberOfParticipants +
                ", Описание: " + description +
                ", Жанр: " + genre +
                ", Солист: " + frontMan +
                ", Координаты: " + coordinates;
    }
}
