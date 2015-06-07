package sepm.ss15.grp16.service.music;

import sepm.ss15.grp16.entity.music.Playlist;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;

/**
 * Author: Lukas
 * Date: 04.06.2015
 */
public interface MusicService extends Service<Playlist> {

    Playlist getMotivations() throws ServiceException;
}
