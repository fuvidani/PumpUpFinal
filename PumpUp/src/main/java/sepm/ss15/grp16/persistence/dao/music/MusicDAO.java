package sepm.ss15.grp16.persistence.dao.music;

import sepm.ss15.grp16.entity.music.Playlist;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.Map;

/**
 * Author: Lukas
 * Date: 04.06.2015
 */
public interface MusicDAO extends DAO<Playlist> {
    Map<String, Playlist> getMotivations() throws PersistenceException;
}
