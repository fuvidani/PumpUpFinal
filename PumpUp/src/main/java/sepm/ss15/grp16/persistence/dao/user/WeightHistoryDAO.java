package sepm.ss15.grp16.persistence.dao.user;

import sepm.ss15.grp16.entity.user.WeightHistory;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * This class represents the DAO for a weightHistory
 *
 * @author Michael Sober
 * @version 1.0
 */
public interface WeightHistoryDAO extends DAO<WeightHistory> {

    /**
     * Creates a new weightHistory
     *
     * @param weightHistory which shall be inserted into the underlying persistence layer.
     *                      must not be null, id must be null
     * @return the given weightHistory assigned with an id
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    WeightHistory create(WeightHistory weightHistory) throws PersistenceException;

    /**
     * Find all persistent weightHistories
     *
     * @return a list of all persistent weightHistories
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    List<WeightHistory> findAll() throws PersistenceException;

    /**
     * Searches for a persistent weightHistory with the given id
     *
     * @param id of the weightHistory, we search for
     * @return the persistent weightHistory with the given id or null if there's no weightHistory with this id
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    WeightHistory searchByID(int id) throws PersistenceException;

    /**
     * Updates a persistent weightHistory. It's not possible to change the id.
     *
     * @param weightHistory which should be updated, must not be null, id must not be null and must not be changed
     * @return the updated weightHistory
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    WeightHistory update(WeightHistory weightHistory) throws PersistenceException;

    /**
     * Deletes a persistent weightHistory
     *
     * @param weightHistory which should be deleted, must not be null, id must not be null and must not be changed
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    void delete(WeightHistory weightHistory) throws PersistenceException;

    /**
     * Searches for all weightHistories associated with the user with the given id
     *
     * @param user_id of the user, of which we search the weightHistories
     * @return a list of all weightHistories, which are associated with the user wth the given id
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    List<WeightHistory> searchByUserID(int user_id) throws PersistenceException;

    /**
     * Gets the last weightHistory, associated with the user with the given id
     *
     * @param user_id of the user, of which we search the last weightHistory
     * @return the last weightHistory, associated with the user with the given id, or null if there is no entry
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    WeightHistory getActualWeight(int user_id) throws PersistenceException;

}
