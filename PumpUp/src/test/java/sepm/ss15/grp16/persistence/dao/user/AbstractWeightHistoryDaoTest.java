package sepm.ss15.grp16.persistence.dao.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.entity.user.WeightHistory;
import sepm.ss15.grp16.persistence.dao.AbstractDAOTest;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.Date;
import java.util.List;

/**
 * This class provides methods for testing WeightHistoryDAOs
 *
 * @author Michael Sober
 * @version 1.0
 */
public abstract class AbstractWeightHistoryDaoTest extends AbstractDAOTest<WeightHistory> {

    protected WeightHistoryDAO weightHistoryDAO;
    protected UserDAO          userDAO;

    @Override
    public DAO<WeightHistory> getDAO() {
        return weightHistoryDAO;
    }

    @Test(expected = PersistenceException.class)
    public void createWithNullShouldThrowException() throws Exception {
        weightHistoryDAO.create(null);
    }

    @Test
    public void createWithValidBodyfatHistoryShouldPersist() throws Exception {
        WeightHistory testWeightHistory = new WeightHistory(null, createUserForTest().getUser_id(), 93, new Date());
        createValid(testWeightHistory);
    }

    @Test
    public void getActualWeightWithValidId() throws Exception {
        User testUser = createUserForTest();
        WeightHistory testWeightHistory = new WeightHistory(null, testUser.getUser_id(), 93, new Date());
        WeightHistory testWeightHistory1 = new WeightHistory(null, testUser.getUser_id(), 96, new Date());
        WeightHistory testWeightHistory2 = new WeightHistory(null, testUser.getUser_id(), 88, new Date());
        WeightHistory testWeightHistory3 = new WeightHistory(null, testUser.getUser_id(), 89, new Date());
        weightHistoryDAO.create(testWeightHistory);
        weightHistoryDAO.create(testWeightHistory1);
        weightHistoryDAO.create(testWeightHistory2);
        weightHistoryDAO.create(testWeightHistory3);

        WeightHistory actualWeightHistory = weightHistoryDAO.getActualWeight(testUser.getUser_id());
        assertEquals(testWeightHistory3, actualWeightHistory);
    }

    @Test
    public void searchWithValidUserID() throws Exception {

        User testUser = createUserForTest();

        WeightHistory testWeightHistory1 = new WeightHistory(null, testUser.getUser_id(), 25, new Date());
        WeightHistory testWeightHistory2 = new WeightHistory(null, testUser.getUser_id(), 21, new Date());
        WeightHistory testWeightHistory3 = new WeightHistory(null, testUser.getUser_id(), 30, new Date());

        weightHistoryDAO.create(testWeightHistory1);
        weightHistoryDAO.create(testWeightHistory2);
        weightHistoryDAO.create(testWeightHistory3);

        List<WeightHistory> weightHistoryList = weightHistoryDAO.searchByUserID(testUser.getUser_id());

        assertTrue(weightHistoryList.contains(testWeightHistory1));
        assertTrue(weightHistoryList.contains(testWeightHistory2));
        assertTrue(weightHistoryList.contains(testWeightHistory3));

    }

    @Test
    public void searchByIDShouldFind() throws Exception {

        User testUser = createUserForTest();

        WeightHistory testWeightHistory = new WeightHistory(null, testUser.getUser_id(), 25, new Date());
        searchByIDValid(testWeightHistory);

    }

    @Test(expected = UnsupportedOperationException.class)
    public void updateNotSupported() throws Exception {
        weightHistoryDAO.update(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void deleteNotSupported() throws Exception {
        weightHistoryDAO.delete(null);
    }

    private User createUserForTest() throws Exception {
        User testUser = new User(null, "maxmustermann", true, 20, 194, "max.mustermann@gmail.com", "/path/playlist/", false);
        return userDAO.create(testUser);
    }
}
