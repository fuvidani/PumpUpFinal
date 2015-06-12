package sepm.ss15.grp16.persistence.dao.user;

import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * This class represents the DAO for a user
 *
 * @author Michael Sober
 * @version 1.0
 */
public interface UserDAO extends DAO<User> {

    /**
     * Creates a new user. Attention: The user does not include the weight and the bodyfat,
     * they are found in an other table.
     * @param user which shall be inserted into the underlying persistence layer.
     *            must not be null, id must be null
     * @return the given user assigned with an id
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    User create(User user) throws PersistenceException;

    /**
     * Find all persistent users, which are not deleted
     * @return a list of all persistent users, which are not deleted
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    List<User> findAll() throws PersistenceException;

    /**
     * Searches for a persistent user with the given id, which is not deleted
     * @param id of the user, we search for
     * @return the persistent user with the given id or null if there's no user with this id
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    User searchByID(int id) throws PersistenceException;

    /**
     * Updates a persistent user. It's not possible to change the id.
     * @param user which should be updated, must not be null, id must not be null and must not be changed
     * @return the updated user
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    User update(User user) throws PersistenceException;

    /**
     * Deletes a persistent user.
     * @param user which should be deleted, must not be null, id must not be null and must not be changed
     * @throws PersistenceException, if an error in the persistence-layer occurs
     */
    @Override
    void delete(User user) throws PersistenceException;
}
