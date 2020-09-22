package org.example.model;

import org.example.enums.Color;
import org.example.exception.MusicBandWrongAttributeException;

import java.io.Serializable;

public class Person implements Serializable {

    private String name;

    private double height;

    private String passportID;

    private Color eyeColor;

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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if (height < 0) {
            throw new MusicBandWrongAttributeException("Значение поля должно быть больше 0");
        }
        this.height = height;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        if (passportID.trim().equals("")) {
            throw new MusicBandWrongAttributeException("Строка не может быть пустой");
        }
        if (passportID.length() > 31) {
            throw new MusicBandWrongAttributeException("Длина строки не должна быть больше 31");
        }
        this.passportID = passportID;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        if (eyeColor == null) {
            throw new MusicBandWrongAttributeException("Поле не может быть null");
        }
        this.eyeColor = Color.valueOf(eyeColor);
    }

    @Override
    public String toString() {
        return "Имя: " + name +
                ", Рост: " + height +
                ", Номер паспорта: " + passportID +
                ", Цвет глаз: " + eyeColor;
    }
}
