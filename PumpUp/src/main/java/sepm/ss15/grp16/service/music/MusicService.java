package sepm.ss15.grp16.service.music;

import sepm.ss15.grp16.entity.music.Playlist;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.util.Map;

/**
 * Author: Lukas
 * Date: 04.06.2015
 */
public interface MusicService extends Service<Playlist> {

    Map<String, Playlist> getMotivations() throws ServiceException;
}
