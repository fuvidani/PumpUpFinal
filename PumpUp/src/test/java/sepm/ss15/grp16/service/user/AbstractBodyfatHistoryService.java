package sepm.ss15.grp16.service.user;

import org.junit.Test;
import sepm.ss15.grp16.entity.user.BodyfatHistory;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.service.AbstractServiceTest;
import sepm.ss15.grp16.service.Service;

import java.util.Date;

/**
 * Created by michaelsober on 12.06.15.
 */
public abstract class AbstractBodyfatHistoryService extends AbstractServiceTest<BodyfatHistory> {

    protected BodyfatHistoryService bodyfatHistoryService;
    protected UserService userService;

    @Override
    public Service<BodyfatHistory> getService() {
        return bodyfatHistoryService;
    }

    @Test
    public void createWithValidBodyfatHistory() throws Exception{
        BodyfatHistory testBodyfatHistory = new BodyfatHistory(null, createUserForTest().getUser_id(), 23, new Date());
        createTest(testBodyfatHistory);
    }

    private User createUserForTest() throws Exception {
        User testUser = new User(null, "maxmustermann", true, 20, 194, "max.mustermann@gmail.com", "/path/playlist/", false);
        return userService.create(testUser);
    }
}
