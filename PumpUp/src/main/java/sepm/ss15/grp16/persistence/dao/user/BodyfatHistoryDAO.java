package sepm.ss15.grp16.persistence.dao.user;

import sepm.ss15.grp16.entity.user.BodyfatHistory;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * This class represents the DAO for a bodyfathistory
 *
 * @author Michael Sober
 * @version 1.0
 */
public interface BodyfatHistoryDAO extends DAO<BodyfatHistory> {

    /**
     * Creates a new bodyfatHistory
     *
     * @param bodyfatHistory which shall be inserted into the underlying persistence layer.
     *                       must not be null, id must be null
     * @return the given bodyfatHistory assigned with an id
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    BodyfatHistory create(BodyfatHistory bodyfatHistory) throws PersistenceException;

    /**
     * Find all persistent bodyfathistories
     *
     * @return a list of all persistent bodyfathistories
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    List<BodyfatHistory> findAll() throws PersistenceException;

    /**
     * Searches for a persistent bodyfatHistory with the given id
     *
     * @param id of the bodyfatHistory, we search for
     * @return the persistent bodyfatHistory with the given id or null if there's no bodyfatHistory with this id
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    BodyfatHistory searchByID(int id) throws PersistenceException;

    /**
     * Updates a persistent bodyfatHistory. It's not possible to change the id.
     *
     * @param bodyfatHistory which should be updated, must not be null, id must not be null and must not be changed
     * @return the updated bodyfatHistory
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    BodyfatHistory update(BodyfatHistory bodyfatHistory) throws PersistenceException;

    /**
     * Deletes a persistent bodyfatHistory
     *
     * @param bodyfatHistory which should be deleted, must not be null, id must not be null and must not be changed
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    void delete(BodyfatHistory bodyfatHistory) throws PersistenceException;

    /**
     * Searches for all bodyfathistories associated with the user with the given id
     *
     * @param user_id of the user, of which we search the bodyfathistories
     * @return a list of all bodyfathistories, which are associated with the user wth the given id
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    List<BodyfatHistory> searchByUserID(int user_id) throws PersistenceException;

    /**
     * Gets the last bodyfathistory, associated with the user with the given id
     *
     * @param user_id of the user, of which we search the last bodyfathistory
     * @return the last bodyfathistory, associated with the user with the given id, or null if there is no entry
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    BodyfatHistory getActualBodyfat(int user_id) throws PersistenceException;

}
