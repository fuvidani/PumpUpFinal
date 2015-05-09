package sepm.ss15.grp16.persistence.dao;

import sepm.ss15.grp16.entity.PictureHistory;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * This class represents the DAO for a picturehistory
 *
 * @author Michael Sober
 * @version 1.0
 */
public interface PictureHistoryDAO extends DAO<PictureHistory> {

    /**
     * Searches all picturehistory records for one user
     * @param user_id from the user
     * @return all records from the given user
     * @throws PersistenceException, if an error while searching occurs
     */
    List<PictureHistory> searchByUserID(int user_id) throws PersistenceException;

}
