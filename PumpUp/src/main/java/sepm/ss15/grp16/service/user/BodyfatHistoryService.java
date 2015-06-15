package sepm.ss15.grp16.service.user;

import sepm.ss15.grp16.entity.user.BodyfatHistory;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.List;

/**
 * This interface defines all operations for a bodyfathistory service
 *
 * @author Michael Sober
 * @version 1.0
 */
public interface BodyfatHistoryService extends Service<BodyfatHistory> {

    /**
     * Validates the given bodyfatHistory and sends it to the the persistence for creation.
     * The ID is set to the new generated one.
     *
     * @param bodyfatHistory which shall be sent to the persistence.
     *                       must not be null, id must be null
     * @return the given bodyfatHistory for further usage
     * @throws ValidationException if the given bodyfatHistory can't be validated
     * @throws ServiceException    if there are complications in the service or persistence-layer
     */
    @Override
    BodyfatHistory create(BodyfatHistory bodyfatHistory) throws ServiceException;

    /**
     * Asks the persistence for all bodyfatHistories, deleted bodyfatHistories are ignored.
     *
     * @return List of all bodyfatHistories returned form the persistence-layer
     * @throws ServiceException if there are complications in the service or persistence-layer
     */
    @Override
    List<BodyfatHistory> findAll() throws ServiceException;

    /**
     * Validates a bodyfatHistory and sends it to the persistence-layer for updating.
     *
     * @param bodyfatHistory which shall be updated
     *                       must not be null, id must not be null and must not be changed
     * @return given bodyfatHistory with updated values
     * @throws ValidationException if the given bodyfatHistory can't be validated
     * @throws ServiceException    if there are complications in the service or persistence-layer
     */
    @Override
    BodyfatHistory update(BodyfatHistory bodyfatHistory) throws ServiceException;

    /**
     * Sends a given bodyfatHistory to the persistence-layer for deleting.
     *
     * @param bodyfatHistory which shall be deleted,
     *                       must not be null, id must not be null and must not be changed
     * @throws ValidationException if the bodyfatHistory user can't be validated
     * @throws ServiceException    if there are complications in the service or persistence-layer
     */
    @Override
    void delete(BodyfatHistory bodyfatHistory) throws ServiceException;

    /**
     * Checks if the bodyfatHistory conforms all restrictions in its properties.
     *
     * @param bodyfatHistory to check
     * @throws ValidationException if the bodyfatHistory is not valid.
     */
    @Override
    void validate(BodyfatHistory bodyfatHistory) throws ValidationException;

    /**
     * Sends a given userID to the persistence-layer to search for the bodyfatHistories of this user
     *
     * @param user_id of the user, of which we search the bodyfatHistories
     * @return a list of all bodyfatHistories, which are associated with the user wth the given id
     * @throws ServiceException if there are complications in the service or persistence-layer
     */
    List<BodyfatHistory> searchByUserID(int user_id) throws ServiceException;

    /**
     * Sends a given userID to the persistence-layer to search for the latest bodyfatHistory
     *
     * @param user_id of the user, of which we search the latest bodyfatHistory
     * @return the last bodyfatHistory, associated with the user with the given id, or null if there is no entry
     * @throws ServiceException if there are complications in the service or persistence-layer
     */
    BodyfatHistory getActualBodyfat(int user_id) throws ServiceException;

}
