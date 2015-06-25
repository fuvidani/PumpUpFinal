package sepm.ss15.grp16.service.music.impl;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.music.Playlist;
import sepm.ss15.grp16.persistence.dao.music.MusicDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.music.MusicService;

import java.util.List;
import java.util.Map;

/**
 * Author: Lukas
 * Date: 04.06.2015
 */
public class MusicServiceImpl implements MusicService {
    private static final Logger LOGGER = LogManager.getLogger(MusicServiceImpl.class);
    private final MusicDAO musicDAO;
    MediaPlayer mediaPlayer;
    private

    Media media;
    private Playlist playlist;

    public MusicServiceImpl(MusicDAO musicDAO) {
        this.musicDAO = musicDAO;
    }

    @Override
    public Playlist create(Playlist dto) throws ServiceException {
        try {
            LOGGER.info("Service try to create Playlist " + dto);
            Playlist playlist = musicDAO.create(dto);
            LOGGER.info("Service creation successful");
            return playlist;
        } catch (PersistenceException e) {
            LOGGER.error("" + e);
            throw new ServiceException("Fehler beim Einlesen der Musik aufgetreten");
        }
    }

    @Override
    public List<Playlist> findAll() throws ServiceException {
        try {
            LOGGER.info("Service try to find all");
            List<Playlist> list = musicDAO.findAll();
            LOGGER.info("Service find all successful");
            return list;
        } catch (PersistenceException e) {
            LOGGER.error("" + e);
            throw new ServiceException("Fehler beim Einlesen der Musik aufgetreten");
        }
    }

    @Override
    public Playlist update(Playlist dto) throws ServiceException {
        try {
            LOGGER.info("Service try to update Playlist " + dto);
            Playlist playlist = musicDAO.update(dto);
            LOGGER.info("Service update successful");
            return playlist;
        } catch (PersistenceException e) {
            LOGGER.error("" + e);
            throw new ServiceException("Fehler beim Einlesen der Musik aufgetreten");
        }
    }

    @Override
    public void delete(Playlist dto) throws ServiceException {
        try {
            LOGGER.info("Service try to delete Playlist " + dto);
            musicDAO.delete(dto);
            LOGGER.info("Service delete successful");
        } catch (PersistenceException e) {
            LOGGER.error("" + e);
            throw new ServiceException("Fehler beim Einlesen der Musik aufgetreten");
        }
    }

    @Override
    public void validate(Playlist dto) throws ValidationException {
    }

    @Override
    public Map<String, Playlist> getMotivations() throws ServiceException {
        try {
            LOGGER.info("Service try to load Motivation-Playlist");
            Map<String, Playlist> playlists = musicDAO.getMotivations();
            LOGGER.info("Service loading Motivation-Playlist successful");
            return playlists;
        } catch (PersistenceException e) {
            LOGGER.error("" + e);
            throw new ServiceException("Fehler beim Laden der Motivation-Playlist");
        }
    }
}
