package sepm.ss15.grp16.service;

import sepm.ss15.grp16.entity.user.WeightHistory;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.util.List;

/**
 * This interface defines all operations for a weighthistory service
 *
 * @author Michael Sober
 * @version 1.0
 */
public interface WeightHistoryService extends Service<WeightHistory> {

    /**
     * Searches all weighthistory records for one user
     *
     * @param user_id from the user
     * @return all records from the given user
     * @throws ServiceException, if an error while searching occurs
     */
    List<WeightHistory> searchByUserID(int user_id) throws ServiceException;

    /**
     * Searches for the actual weight of the user
     *
     * @param user_id from the user
     * @return the actual weight of the user
     * @throws ServiceException, if an error while searching occurs
     */
    WeightHistory getActualWeight(int user_id) throws ServiceException;
}
