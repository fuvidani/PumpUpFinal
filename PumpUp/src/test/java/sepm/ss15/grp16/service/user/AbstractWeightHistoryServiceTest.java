package sepm.ss15.grp16.service.user;

import org.junit.Test;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.entity.user.WeightHistory;
import sepm.ss15.grp16.service.AbstractServiceTest;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.user.impl.WeightHistoryServiceImpl;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by michaelsober on 12.06.15.
 */
public abstract class AbstractWeightHistoryServiceTest extends AbstractServiceTest<WeightHistory> {

    protected WeightHistoryService weightHistoryService;
    protected UserService userService;

    @Override
    public Service<WeightHistory> getService() {
        return weightHistoryService;
    }

    @Test(expected = ServiceException.class)
    public void newUserServiceWithNull() throws Exception{
        new WeightHistoryServiceImpl(null);
    }

    @Test
    public void createWithValidWeightHistory() throws Exception{
        WeightHistory testWeightHistory = new WeightHistory(null, createUserForTest().getUser_id(), 85, new Date());
        createTest(testWeightHistory);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidWeightHistory() throws Exception{
        WeightHistory weightHistory = null;
        weightHistoryService.validate(weightHistory);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidWeight() throws Exception{
        WeightHistory testWeightHistory = new WeightHistory(null, createUserForTest().getUser_id(), -85, new Date());
        weightHistoryService.validate(testWeightHistory);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidUserID() throws Exception{
        WeightHistory testWeightHistory = new WeightHistory(null, null, -85, new Date());
        weightHistoryService.validate(testWeightHistory);
    }

    @Test
    public void searchWithValidUserID() throws Exception {

        User testUser = createUserForTest();

        WeightHistory testWeightHistory1 = new WeightHistory(null, testUser.getUser_id(), 25, new Date());
        WeightHistory testWeightHistory2 = new WeightHistory(null, testUser.getUser_id(), 21, new Date());
        WeightHistory testWeightHistory3 = new WeightHistory(null, testUser.getUser_id(), 30, new Date());

        weightHistoryService.create(testWeightHistory1);
        weightHistoryService.create(testWeightHistory2);
        weightHistoryService.create(testWeightHistory3);

        List<WeightHistory> weightHistoryList = weightHistoryService.searchByUserID(testUser.getUser_id());

        assertTrue(weightHistoryList.contains(testWeightHistory1));
        assertTrue(weightHistoryList.contains(testWeightHistory2));
        assertTrue(weightHistoryList.contains(testWeightHistory3));

    }

    @Test
    public void getActualWeightWithValidId() throws Exception {
        User testUser = createUserForTest();
        WeightHistory testWeightHistory = new WeightHistory(null, testUser.getUser_id(), 93, new Date());
        WeightHistory testWeightHistory1 = new WeightHistory(null, testUser.getUser_id(), 96, new Date());
        WeightHistory testWeightHistory2 = new WeightHistory(null, testUser.getUser_id(), 88, new Date());
        WeightHistory testWeightHistory3 = new WeightHistory(null, testUser.getUser_id(), 89, new Date());
        weightHistoryService.create(testWeightHistory);
        weightHistoryService.create(testWeightHistory1);
        weightHistoryService.create(testWeightHistory2);
        weightHistoryService.create(testWeightHistory3);

        WeightHistory actualWeightHistory = weightHistoryService.getActualWeight(testUser.getUser_id());
        assertEquals(testWeightHistory3, actualWeightHistory);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void deleteNotSupported() throws Exception{
        weightHistoryService.delete(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void updateNotSupported() throws Exception{
        weightHistoryService.update(null);
    }

    private User createUserForTest() throws Exception {
        User testUser = new User(null, "maxmustermann", true, 20, 194, "max.mustermann@gmail.com", "/path/playlist/", false);
        return userService.create(testUser);
    }
}
