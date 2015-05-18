package sepm.ss15.grp16.service;

import sepm.ss15.grp16.entity.PictureHistory;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.util.List;

/**
 * This interface defines all operations for a picturehistory service
 *
 * @author Michael Sober
 * @version 1.0
 */
public interface PictureHistoryService extends Service<PictureHistory> {

    /**
     * Searches all picturehistory records for one user
     * @param user_id from the user
     * @return all records from the given user
     * @throws ServiceException, if an error while searching occurs
     */
    List<PictureHistory> searchByUserID(int user_id) throws ServiceException;

    /**
     * Searches for the actual picture of the user
     * @param user_id from the user
     * @return the actual picture of the user
     * @throws ServiceException, if an error while searching occurs
     */
    PictureHistory getActualPicture(int user_id) throws ServiceException;

}
