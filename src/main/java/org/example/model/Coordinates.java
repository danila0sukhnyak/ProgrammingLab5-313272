package org.example.model;

import org.example.exception.MusicBandWrongAttributeException;

import java.io.Serializable;

public class Coordinates implements Serializable {

    private Long x;

    private Float y;

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        if (x == null) {
            throw new MusicBandWrongAttributeException("Поле не может быть null");
        }
        if (x > 565L) {
            throw new MusicBandWrongAttributeException("Максимальное значение поля: 565");
        }
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        if (y == null) {
            throw new MusicBandWrongAttributeException("Поле не может быть null");
        }
        if (y > 900L) {
            throw new MusicBandWrongAttributeException("Максимальное значение поля: 900");
        }
        this.y = y;
    }

    @Override
    public String toString() {
        return "x: " + x +
                ", y: " + y;
    }
}
