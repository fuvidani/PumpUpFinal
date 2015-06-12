package sepm.ss15.grp16.service.user;

import sepm.ss15.grp16.entity.user.WeightHistory;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.List;

/**
 * This interface defines all operations for a weightHistory service
 *
 * @author Michael Sober
 * @version 1.0
 */
public interface WeightHistoryService extends Service<WeightHistory> {

    /**
     * Validates the given weightHistory and sends it to the the persistence for creation.
     * The ID is set to the new generated one.
     *
     * @param weightHistory which shall be sent to the persistence.
     *                      must not be null, id must be null
     * @return the given weightHistory for further usage
     * @throws ValidationException if the given weightHistory can't be validated
     * @throws ServiceException    if there are complications in the service or persistence-layer
     */
    @Override
    WeightHistory create(WeightHistory weightHistory) throws ServiceException;

    /**
     * Asks the persistence for all weightHistories, deleted weightHistories are ignored.
     *
     * @return List of all weightHistories returned form the persistence-layer
     * @throws ServiceException if there are complications in the service or persistence-layer
     */
    @Override
    List<WeightHistory> findAll() throws ServiceException;

    /**
     * Validates a weightHistory and sends it to the persistence-layer for updating.
     *
     * @param weightHistory which shall be updated
     *                      must not be null, id must not be null and must not be changed
     * @return given weightHistory with updated values
     * @throws ValidationException if the given weightHistory can't be validated
     * @throws ServiceException    if there are complications in the service or persistence-layer
     */
    @Override
    WeightHistory update(WeightHistory weightHistory) throws ServiceException;

    /**
     * Sends a given weightHistory to the persistence-layer for deleting.
     *
     * @param weightHistory which shall be deleted,
     *                      must not be null, id must not be null and must not be changed
     * @throws ValidationException if the weightHistory user can't be validated
     * @throws ServiceException    if there are complications in the service or persistence-layer
     */
    @Override
    void delete(WeightHistory weightHistory) throws ServiceException;

    /**
     * Checks if the weightHistory conforms all restrictions in its properties.
     *
     * @param weightHistory to check
     * @throws ValidationException if the weightHistory is not valid.
     */
    @Override
    void validate(WeightHistory weightHistory) throws ValidationException;

    /**
     * Sends a given userID to the persistence-layer to search for the weightHistories of this user
     * @param user_id of the user, of which we search the weightHistories
     * @return a list of all weightHistories, which are associated with the user wth the given id
     * @throws ServiceException if there are complications in the service or persistence-layer
     */
    List<WeightHistory> searchByUserID(int user_id) throws ServiceException;

    /**
     * Sends a given userID to the persistence-layer to search for the latest weightHistory
     * @param user_id of the user, of which we search the latest weightHistory
     * @return the last weightHistory, associated with the user with the given id, or null if there is no entry
     * @throws ServiceException if there are complications in the service or persistence-layer
     */
    WeightHistory getActualWeight(int user_id) throws ServiceException;

}
