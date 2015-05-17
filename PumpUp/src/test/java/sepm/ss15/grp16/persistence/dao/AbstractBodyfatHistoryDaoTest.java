package sepm.ss15.grp16.persistence.dao;

import org.junit.Test;
import sepm.ss15.grp16.entity.BodyfatHistory;
import sepm.ss15.grp16.persistence.dao.BodyfatHistoryDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class provides methods for testing BodyfatHistoryDAOs
 *
 * @author Michael Sober
 * @version 1.0
 */
public abstract class AbstractBodyfatHistoryDaoTest {

    protected BodyfatHistoryDAO bodyfatHistoryDAO;

    @Test(expected = PersistenceException.class)
    public void createWithNullShouldThrowException() throws Exception{
        bodyfatHistoryDAO.create(null);
    }

    @Test
    public void createWithValidBodyfatHistoryShouldPersist() throws Exception {
        BodyfatHistory testBodyfatHistory = new BodyfatHistory(null, 1, 23, new Date());
        List<BodyfatHistory> allUsers = bodyfatHistoryDAO.findAll();
        assertFalse(allUsers.contains(testBodyfatHistory));
        bodyfatHistoryDAO.create(testBodyfatHistory);
        allUsers = bodyfatHistoryDAO.findAll();
        assertTrue(allUsers.contains(testBodyfatHistory));
    }

    @Test
    public void getActualWeightWithValidId() throws Exception {
        BodyfatHistory testBodyfatHistory = new BodyfatHistory(null, 1, 23, new Date());
        BodyfatHistory testBodyfatHistory1 = new BodyfatHistory(null, 1, 25, new Date());
        BodyfatHistory testBodyfatHistory2 = new BodyfatHistory(null, 1, 21, new Date());
        BodyfatHistory testBodyfatHistory3 = new BodyfatHistory(null, 1, 18, new Date());
        bodyfatHistoryDAO.create(testBodyfatHistory);
        bodyfatHistoryDAO.create(testBodyfatHistory1);
        bodyfatHistoryDAO.create(testBodyfatHistory2);
        bodyfatHistoryDAO.create(testBodyfatHistory3);

        BodyfatHistory bodyfatHistory = bodyfatHistoryDAO.getActualBodyfat(1);
        assertEquals(testBodyfatHistory3, bodyfatHistory);
    }
}
