package sepm.ss15.grp16.service.music.impl;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.music.Playlist;
import sepm.ss15.grp16.entity.music.Song;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.dao.music.MusicDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.music.MusicService;
import sepm.ss15.grp16.service.user.UserService;

import java.util.List;

/**
 * Author: Lukas
 * Date: 04.06.2015
 */
public class MusicServiceImpl implements MusicService {
    private static final Logger LOGGER = LogManager.getLogger(MusicServiceImpl.class);
    private final UserService userService;
    private final MusicDAO musicDAO;

    private

    Media media;
    MediaPlayer mediaPlayer;

    private Playlist playlist;

    public MusicServiceImpl(MusicDAO musicDAO, UserService userService) {
        this.musicDAO = musicDAO;
        this.userService = userService;
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
    public Song getCurrentSong() throws ServiceException {
        return null;
    }

    @Override
    public Playlist getCurrentPlaylist() throws ServiceException {
        return null;
    }

    @Override
    public void startMusic() throws ServiceException {
        User user = userService.getLoggedInUser();
        this.playlist = this.create(new Playlist(user, user.getPlaylist(), null));
        if (playlist != null && playlist.getPlayers() != null && playlist.getPlayers().size() > 0) {
            if (mediaPlayer != null) mediaPlayer.stop();
            mediaPlayer = playlist.getPlayers().get(0);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();
        }
    }

    @Override
    public void stopMusic() throws ServiceException {
        if (mediaPlayer != null) mediaPlayer.stop();
    }

    @Override
    public void nextSong() throws ServiceException {

    }

    @Override
    public void resetSong() throws ServiceException {

    }

    @Override
    public void increaseVol() throws ServiceException {

    }

    @Override
    public void increaseVol(Double percent) throws ServiceException {

    }

    @Override
    public void decreaseVol() throws ServiceException {

    }

    @Override
    public void decreaseVol(Double percent) throws ServiceException {

    }
}
