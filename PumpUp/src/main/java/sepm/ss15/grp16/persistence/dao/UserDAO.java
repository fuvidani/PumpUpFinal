package sepm.ss15.grp16.persistence.dao;

import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * This class represents the DAO for a user
 *
 * @author Michael Sober
 * @version 1.0
 */
public interface UserDAO {

    /**
     * Creates a new user
     * @param user, which should persist
     * @return the created user
     * @throws PersistenceException, if an error while saving the user occur
     */
    public User create(User user) throws PersistenceException;

    /**
     * Finds all Users, which are in the system
     * @return a list of all users found in the system
     * @throws PersistenceException, if an error while finding the users occur
     */
    public List<User> findAll() throws PersistenceException;

    /**
     * Updates an existing user
     * @param user, which should be updated including the new values
     * @return the updated user
     * @throws PersistenceException, if an error while updating the user occur
     */
    public User update(User user) throws PersistenceException;

    /**
     * Deletes an exisiting user
     * @param user, which should be deleted from the system
     * @return the deleted user
     * @throws PersistenceException, if an error while deleting the user occur
     */
    public User delete(User user) throws PersistenceException;

}
