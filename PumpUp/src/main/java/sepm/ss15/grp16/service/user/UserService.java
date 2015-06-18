package sepm.ss15.grp16.service.user;

import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.List;

/**
 * This interface defines all operations for a user service
 *
 * @author Michael Sober
 * @version 1.0
 */
public interface UserService extends Service<User> {

    /**
     * Validates the given user and sends it to the the persistence for creation.
     * The ID is set to the new generated one.
     *
     * @param user which shall be sent to the persistence.
     *             must not be null, id must be null
     * @return the given user for further usage
     * @throws ValidationException if the given user can't be validated
     * @throws ServiceException    if there are complications in the service or persistence-layer
     */
    @Override
    User create(User user) throws ServiceException;

    /**
     * Asks the persistence for all users, deleted users are ignored.
     *
     * @return List of all users returned form the persistence-layer
     * @throws ServiceException if      there are complications in the service or persistence-layer
     */
    @Override
    List<User> findAll() throws ServiceException;

    /**
     * Validates a user and sends it to the persistence-layer for updating.
     *
     * @param user which shall be updated
     *             must not be null, id must not be null and must not be changed
     * @return given user with updated values
     * @throws ValidationException if the given user can't be validated
     * @throws ServiceException    if there are complications in the service or persistence-layer
     */
    @Override
    User update(User user) throws ServiceException;

    /**
     * Sends a given user to the persistence-layer for deleting.
     *
     * @param user which shall be deleted,
     *             must not be null, id must not be null and must not be changed
     * @throws ValidationException if the given user can't be validated
     * @throws ServiceException    if there are complications in the service or persistence-layer
     */
    @Override
    void delete(User user) throws ServiceException;

    /**
     * Checks if the user conforms all restrictions in its properties.
     *
     * @param user to check
     * @throws ValidationException if the user is not valid.
     */
    @Override
    void validate(User user) throws ValidationException;

    /**
     * Gets the currently User, which is logged in
     *
     * @return the user, which is currently logged in
     */
    User getLoggedInUser();

    /**
     * Sets the user on login
     *
     * @param loggedInUser the user which wants to log in
     */
    void setLoggedInUser(User loggedInUser);

}
