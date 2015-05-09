package sepm.ss15.grp16.persistence.dao;

import sepm.ss15.grp16.entity.BodyfatHistory;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * This class represents the DAO for a bodyfathistory
 *
 * @author Michael Sober
 * @version 1.0
 */
public interface BodyfatHistoryDAO extends DAO<BodyfatHistory>{

    /**
     * Searches all bodyfathistory records for one user
     * @param user_id from the user
     * @return all records from the given user
     * @throws PersistenceException, if an error while searching occurs
     */
    List<BodyfatHistory> searchByUserID(int user_id) throws PersistenceException;

}
