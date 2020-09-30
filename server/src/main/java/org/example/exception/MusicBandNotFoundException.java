package org.example.exception;

public class MusicBandNotFoundException extends RuntimeException{
    public MusicBandNotFoundException(String s) {
        super(s);
    }
}
