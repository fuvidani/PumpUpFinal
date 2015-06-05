package sepm.ss15.grp16.service.music;

import sepm.ss15.grp16.entity.music.Playlist;
import sepm.ss15.grp16.entity.music.Song;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;

/**
 * Author: Lukas
 * Date: 04.06.2015
 */
public interface MusicService extends Service<Playlist> {

    Song getCurrentSong() throws ServiceException;

    Playlist getCurrentPlaylist() throws ServiceException;

    void startMusic() throws ServiceException;

    void stopMusic() throws ServiceException;

    void nextSong() throws ServiceException;

    void resetSong() throws ServiceException;

    void increaseVol() throws ServiceException;

    void increaseVol(Double percent) throws ServiceException;

    void decreaseVol() throws ServiceException;

    void decreaseVol(Double percent) throws ServiceException;
}
