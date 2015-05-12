package sepm.ss15.grp16.persistence.dao;

import org.junit.Test;
import sepm.ss15.grp16.entity.WeightHistory;
import sepm.ss15.grp16.persistence.dao.WeightHistoryDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class provides methods for testing WeightHistoryDAOs
 *
 * @author Michael Sober
 * @version 1.0
 */
public abstract class AbstractWeightHistoryDaoTest {

    protected WeightHistoryDAO weightHistoryDAO;

    @Test(expected = PersistenceException.class)
    public void createWithNullShouldThrowException() throws Exception{
        weightHistoryDAO.create(null);
    }

    @Test
    public void createWithValidBodyfatHistoryShouldPersist() throws Exception {
        WeightHistory testWeightHistory = new WeightHistory(null, 1, 93, new Date());
        List<WeightHistory> allUsers = weightHistoryDAO.findAll();
        assertFalse(allUsers.contains(testWeightHistory));
        weightHistoryDAO.create(testWeightHistory);
        allUsers = weightHistoryDAO.findAll();
        assertTrue(allUsers.contains(testWeightHistory));
    }

}
