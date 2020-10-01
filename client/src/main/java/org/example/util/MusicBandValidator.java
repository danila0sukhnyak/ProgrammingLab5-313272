package org.example.util;

import org.example.dao.IMusicBandDAO;
import org.example.model.Coordinates;
import org.example.model.MusicBand;
import org.example.model.Person;

/**
 * Сервис
 */
public class MusicBandValidator {

    private IMusicBandDAO dao;

    public MusicBandValidator(IMusicBandDAO dao) {
        this.dao = dao;
    }

    public void validateUpdate(MusicBand musicBand, MusicBand newMusicBand) {
        doValidate(musicBand);
        String passportID = newMusicBand.getFrontMan().getPassportID();
        if (!musicBand.getFrontMan().getPassportID().equals(passportID)) {
            dao.checkPassportIDUnique(passportID);
        }
    }

    public void validate(MusicBand musicBand) {
        doValidate(musicBand);
        dao.checkPassportIDUnique(musicBand.getFrontMan().getPassportID());
    }

    private void doValidate(MusicBand musicBand) {
        MusicBand testMusicBand = new MusicBand();
        Person testPerson = new Person();
        Coordinates testCoordinates = new Coordinates();
        Person frontMan = musicBand.getFrontMan();
        Coordinates coordinates = musicBand.getCoordinates();
        testMusicBand.setDescription(musicBand.getDescription());
        testMusicBand.setNumberOfParticipants(musicBand.getNumberOfParticipants());
        testMusicBand.setName(musicBand.getName());
        testPerson.setHeight(frontMan.getHeight());
        testPerson.setName(frontMan.getName());
        testPerson.setEyeColor(frontMan.getEyeColor().name());
        testPerson.setPassportID(frontMan.getPassportID());
        testCoordinates.setY(coordinates.getY());
        testCoordinates.setX(coordinates.getX());
    }
}
