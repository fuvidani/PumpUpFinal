package sepm.ss15.grp16.persistence.dao.user;

import sepm.ss15.grp16.entity.user.PictureHistory;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * This class represents the DAO for a pictureHistory
 *
 * @author Michael Sober
 * @version 1.0
 */
public interface PictureHistoryDAO extends DAO<PictureHistory> {

    /**
     * Creates a new pictureHistory
     *
     * @param pictureHistory which shall be inserted into the underlying persistence layer.
     *                       must not be null, id must be null
     * @return the given pictureHistory assigned with an id
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    PictureHistory create(PictureHistory pictureHistory) throws PersistenceException;

    /**
     * Find all persistent pictureHistories, which are not deleted
     *
     * @return a list of all persistent pictureHistories, which are not deleted
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    List<PictureHistory> findAll() throws PersistenceException;

    /**
     * Searches for a persistent pictureHistory with the given id, which is not deleted
     *
     * @param id of the pictureHistory, we search for
     * @return the persistent pictureHistory with the given id or null if there's no pictureHistory with this id
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    PictureHistory searchByID(int id) throws PersistenceException;

    /**
     * Updates a persistent pictureHistory. It's not possible to change the id.
     *
     * @param pictureHistory which should be updated, must not be null, id must not be null and must not be changed
     * @return the updated pictureHistory
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    PictureHistory update(PictureHistory pictureHistory) throws PersistenceException;

    /**
     * Deletes a persistent pictureHistory
     *
     * @param pictureHistory which should be deleted, must not be null, id must not be null and must not be changed
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    void delete(PictureHistory pictureHistory) throws PersistenceException;

    /**
     * Searches for all pictureHistories associated with the user with the given id and are not deleted
     *
     * @param user_id of the user, of which we search the pictureHistories
     * @return a list of all pictureHistories, which are associated with the user wth the given id and are not deleted
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    List<PictureHistory> searchByUserID(int user_id) throws PersistenceException;

    /**
     * Gets the last pictureHistory, associated with the user with the given id and is not deleted
     *
     * @param user_id of the user, of which we search the last pictureHistory
     * @return the last pictureHistory, associated with the user with the given id and is not deleted,
     * or null if there is no entry
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    PictureHistory getActualPicture(int user_id) throws PersistenceException;

}
