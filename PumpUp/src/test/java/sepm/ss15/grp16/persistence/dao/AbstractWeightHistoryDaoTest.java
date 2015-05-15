package sepm.ss15.grp16.persistence.dao;

import org.junit.Test;
import sepm.ss15.grp16.entity.WeightHistory;
import sepm.ss15.grp16.persistence.dao.WeightHistoryDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
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

    @Test
    public void getActualWeightWithValidId() throws Exception {
        WeightHistory testWeightHistory = new WeightHistory(null, 1, 93, new Date());
        WeightHistory testWeightHistory1 = new WeightHistory(null, 1, 96, new Date());
        WeightHistory testWeightHistory2 = new WeightHistory(null, 1, 88, new Date());
        WeightHistory testWeightHistory3 = new WeightHistory(null, 1, 89, new Date());
        weightHistoryDAO.create(testWeightHistory);
        weightHistoryDAO.create(testWeightHistory1);
        weightHistoryDAO.create(testWeightHistory2);
        weightHistoryDAO.create(testWeightHistory3);

        WeightHistory actualWeightHistory = weightHistoryDAO.getActualWeight(1);
        assertEquals(testWeightHistory3, actualWeightHistory);
    }


}
