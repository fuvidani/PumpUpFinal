package sepm.ss15.grp16.service.user;

import org.junit.Test;
import sepm.ss15.grp16.entity.user.BodyfatHistory;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.service.AbstractServiceTest;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by michaelsober on 12.06.15.
 */
public abstract class AbstractBodyfatHistoryServiceTest extends AbstractServiceTest<BodyfatHistory> {

    protected BodyfatHistoryService bodyfatHistoryService;
    protected UserService userService;

    @Override
    public Service<BodyfatHistory> getService() {
        return bodyfatHistoryService;
    }

    @Test
    public void createWithValidBodyfatHistory() throws Exception {
        BodyfatHistory testBodyfatHistory = new BodyfatHistory(null, createUserForTest().getUser_id(), 23, new Date());
        createTest(testBodyfatHistory);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidBodyfatHistory() throws Exception {
        BodyfatHistory bodyfatHistory = null;
        bodyfatHistoryService.validate(bodyfatHistory);
    }

    @Test(expected = ValidationException.class)
    public void validateWithToHightBodyfat() throws Exception {
        BodyfatHistory testBodyfatHistory = new BodyfatHistory(null, createUserForTest().getUser_id(), 101, new Date());
        bodyfatHistoryService.validate(testBodyfatHistory);
    }

    @Test(expected = ValidationException.class)
    public void validateWithToLowBodyfat() throws Exception {
        BodyfatHistory testBodyfatHistory = new BodyfatHistory(null, createUserForTest().getUser_id(), -101, new Date());
        bodyfatHistoryService.validate(testBodyfatHistory);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidUserID() throws Exception {
        BodyfatHistory testBodyfatHistory = new BodyfatHistory(null, null, 23, new Date());
        bodyfatHistoryService.validate(testBodyfatHistory);
    }

    @Test
    public void getActualWeightWithValidId() throws Exception {
        User testUser = createUserForTest();
        BodyfatHistory testBodyfatHistory = new BodyfatHistory(null, testUser.getUser_id(), 23, new Date());
        BodyfatHistory testBodyfatHistory1 = new BodyfatHistory(null, testUser.getUser_id(), 25, new Date());
        BodyfatHistory testBodyfatHistory2 = new BodyfatHistory(null, testUser.getUser_id(), 21, new Date());
        BodyfatHistory testBodyfatHistory3 = new BodyfatHistory(null, testUser.getUser_id(), 18, new Date());
        bodyfatHistoryService.create(testBodyfatHistory);
        bodyfatHistoryService.create(testBodyfatHistory1);
        bodyfatHistoryService.create(testBodyfatHistory2);
        bodyfatHistoryService.create(testBodyfatHistory3);

        BodyfatHistory bodyfatHistory = bodyfatHistoryService.getActualBodyfat(testUser.getUser_id());
        assertEquals(testBodyfatHistory3, bodyfatHistory);
    }

    @Test
    public void searchWithValidUserID() throws Exception {

        User testUser = createUserForTest();

        BodyfatHistory testBodyfatHistory1 = new BodyfatHistory(null, testUser.getUser_id(), 25, new Date());
        BodyfatHistory testBodyfatHistory2 = new BodyfatHistory(null, testUser.getUser_id(), 21, new Date());
        BodyfatHistory testBodyfatHistory3 = new BodyfatHistory(null, testUser.getUser_id(), 30, new Date());

        bodyfatHistoryService.create(testBodyfatHistory1);
        bodyfatHistoryService.create(testBodyfatHistory2);
        bodyfatHistoryService.create(testBodyfatHistory3);

        List<BodyfatHistory> bodyfatHistoryList = bodyfatHistoryService.searchByUserID(testUser.getUser_id());

        assertTrue(bodyfatHistoryList.contains(testBodyfatHistory1));
        assertTrue(bodyfatHistoryList.contains(testBodyfatHistory2));
        assertTrue(bodyfatHistoryList.contains(testBodyfatHistory3));

    }

    @Test(expected = UnsupportedOperationException.class)
    public void deleteNotSupported() throws Exception {
        bodyfatHistoryService.delete(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void updateNotSupported() throws Exception {
        bodyfatHistoryService.update(null);
    }

    private User createUserForTest() throws Exception {
        User testUser = new User(null, "maxmustermann", true, 20, 194, "max.mustermann@gmail.com", "/path/playlist/", false);
        return userService.create(testUser);
    }
}
