package sepm.ss15.grp16.service.user;

import sepm.ss15.grp16.entity.user.BodyfatHistory;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.util.List;

/**
 * This interface defines all operations for a bodyfathistory service
 *
 * @author Michael Sober
 * @version 1.0
 */
public interface BodyfatHistoryService extends Service<BodyfatHistory> {

    /**
     * Searches all bodyfathistory records for one user
     *
     * @param user_id from the user
     * @return all records from the given user
     * @throws ServiceException, if an error while searching occurs
     */
    List<BodyfatHistory> searchByUserID(int user_id) throws ServiceException;

    /**
     * Searches for the actual bodyfat of the user
     *
     * @param user_id from the user
     * @return the actual bodyfat of the user
     * @throws ServiceException, if an error while searching occurs
     */
    BodyfatHistory getActualBodyfat(int user_id) throws ServiceException;
}
