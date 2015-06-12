package sepm.ss15.grp16.service.user;

import sepm.ss15.grp16.entity.user.PictureHistory;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.List;

/**
 * This interface defines all operations for a pictureHistory service
 *
 * @author Michael Sober
 * @version 1.0
 */
public interface PictureHistoryService extends Service<PictureHistory> {

    /**
     * Validates the given pictureHistory and sends it to the the persistence for creation.
     * The ID is set to the new generated one.
     *
     * @param pictureHistory which shall be sent to the persistence.
     *                       must not be null, id must be null
     * @return the given pictureHistory for further usage
     * @throws ValidationException if the given pictureHistory can't be validated
     * @throws ServiceException    if there are complications in the service or persistence-layer
     */
    @Override
    PictureHistory create(PictureHistory pictureHistory) throws ServiceException;

    /**
     * Asks the persistence for all pictureHistories, deleted pictureHistories are ignored.
     *
     * @return List of all pictureHistories returned form the persistence-layer
     * @throws ServiceException if there are complications in the service or persistence-layer
     */
    @Override
    List<PictureHistory> findAll() throws ServiceException;

    /**
     * Validates a pictureHistory and sends it to the persistence-layer for updating.
     *
     * @param pictureHistory which shall be updated
     *                       must not be null, id must not be null and must not be changed
     * @return given pictureHistory with updated values
     * @throws ValidationException if the given pictureHistory can't be validated
     * @throws ServiceException    if there are complications in the service or persistence-layer
     */
    @Override
    PictureHistory update(PictureHistory pictureHistory) throws ServiceException;

    /**
     * Sends a given pictureHistory to the persistence-layer for deleting.
     *
     * @param pictureHistory which shall be deleted,
     *                       must not be null, id must not be null and must not be changed
     * @throws ValidationException if the pictureHistory user can't be validated
     * @throws ServiceException    if there are complications in the service or persistence-layer
     */
    @Override
    void delete(PictureHistory pictureHistory) throws ServiceException;

    /**
     * Checks if the pictureHistory conforms all restrictions in its properties.
     *
     * @param pictureHistory to check
     * @throws ValidationException if the pictureHistory is not valid.
     */
    @Override
    void validate(PictureHistory pictureHistory) throws ValidationException;

    /**
     * Sends a given userID to the persistence-layer to search for the pictureHistories of this user
     * @param user_id of the user, of which we search the pictureHistories
     * @return a list of all pictureHistories, which are associated with the user wth the given id
     * @throws ServiceException if there are complications in the service or persistence-layer
     */
    List<PictureHistory> searchByUserID(int user_id) throws ServiceException;

    /**
     * Sends a given userID to the persistence-layer to search for the latest pictureHistory
     * @param user_id of the user, of which we search the latest pictureHistory
     * @return the last pictureHistory, associated with the user with the given id, or null if there is no entry
     * @throws ServiceException if there are complications in the service or persistence-layer
     */
    PictureHistory getActualPicture(int user_id) throws ServiceException;

}
