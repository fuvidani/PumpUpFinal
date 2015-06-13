package sepm.ss15.grp16.persistence.dao.user;

import org.junit.Test;
import sepm.ss15.grp16.entity.user.BodyfatHistory;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.dao.AbstractDAOTest;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class provides methods for testing BodyfatHistoryDAOs
 *
 * @author Michael Sober
 * @version 1.0
 */
public abstract class AbstractBodyfatHistoryDaoTest extends AbstractDAOTest<BodyfatHistory> {

    protected BodyfatHistoryDAO bodyfatHistoryDAO;
    protected UserDAO userDAO;

    @Override
    public DAO<BodyfatHistory> getDAO() {
        return bodyfatHistoryDAO;
    }

    @Test(expected = PersistenceException.class)
    public void createWithNullShouldThrowException() throws Exception {
        bodyfatHistoryDAO.create(null);
    }

    @Test
    public void createWithValidBodyfatHistoryShouldPersist() throws Exception {
        BodyfatHistory testBodyfatHistory = new BodyfatHistory(null, createUserForTest().getUser_id(), 23, new Date());
        createValid(testBodyfatHistory);
    }

    @Test
    public void getActualWeightWithValidId() throws Exception {
        User testUser = createUserForTest();
        BodyfatHistory testBodyfatHistory = new BodyfatHistory(null, testUser.getUser_id(), 23, new Date());
        BodyfatHistory testBodyfatHistory1 = new BodyfatHistory(null, testUser.getUser_id(), 25, new Date());
        BodyfatHistory testBodyfatHistory2 = new BodyfatHistory(null, testUser.getUser_id(), 21, new Date());
        BodyfatHistory testBodyfatHistory3 = new BodyfatHistory(null, testUser.getUser_id(), 18, new Date());
        bodyfatHistoryDAO.create(testBodyfatHistory);
        bodyfatHistoryDAO.create(testBodyfatHistory1);
        bodyfatHistoryDAO.create(testBodyfatHistory2);
        bodyfatHistoryDAO.create(testBodyfatHistory3);

        BodyfatHistory bodyfatHistory = bodyfatHistoryDAO.getActualBodyfat(testUser.getUser_id());
        assertEquals(testBodyfatHistory3, bodyfatHistory);
    }

    @Test
    public void searchWithValidUserID() throws Exception {

        User testUser = createUserForTest();

        BodyfatHistory testBodyfatHistory1 = new BodyfatHistory(null, testUser.getUser_id(), 25, new Date());
        BodyfatHistory testBodyfatHistory2 = new BodyfatHistory(null, testUser.getUser_id(), 21, new Date());
        BodyfatHistory testBodyfatHistory3 = new BodyfatHistory(null, testUser.getUser_id(), 30, new Date());

        bodyfatHistoryDAO.create(testBodyfatHistory1);
        bodyfatHistoryDAO.create(testBodyfatHistory2);
        bodyfatHistoryDAO.create(testBodyfatHistory3);

        List<BodyfatHistory> bodyfatHistoryList = bodyfatHistoryDAO.searchByUserID(testUser.getUser_id());

        assertTrue(bodyfatHistoryList.contains(testBodyfatHistory1));
        assertTrue(bodyfatHistoryList.contains(testBodyfatHistory2));
        assertTrue(bodyfatHistoryList.contains(testBodyfatHistory3));

    }

    @Test
    public void searchByIDShouldFind() throws Exception {

        User testUser = createUserForTest();

        BodyfatHistory testBodyfatHistory = new BodyfatHistory(null, testUser.getUser_id(), 25, new Date());
        searchByIDValid(testBodyfatHistory);

    }

    @Test(expected = UnsupportedOperationException.class)
    public void updateNotSupported() throws Exception {
        bodyfatHistoryDAO.update(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void deleteNotSupported() throws Exception {
        bodyfatHistoryDAO.delete(null);
    }

    private User createUserForTest() throws Exception {
        User testUser = new User(null, "maxmustermann", true, 20, 194, "max.mustermann@gmail.com", "/path/playlist/", false);
        return userDAO.create(testUser);
    }
}
