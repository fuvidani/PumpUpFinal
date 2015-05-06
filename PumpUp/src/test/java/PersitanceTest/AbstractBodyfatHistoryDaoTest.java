package PersitanceTest;

import org.junit.Test;
import sepm.ss15.grp16.entity.BodyfatHistory;
import sepm.ss15.grp16.persistence.dao.BodyfatHistoryDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by michaelsober on 06.05.15.
 */
public abstract class AbstractBodyfatHistoryDaoTest {

    protected BodyfatHistoryDAO bodyfatHistoryDAO;

    public void setBodyfatHistoryDAO(BodyfatHistoryDAO bodyfatHistoryDAO) {
        this.bodyfatHistoryDAO = bodyfatHistoryDAO;
    }

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
}
