package sepm.ss15.grp16.service.user;

import org.junit.Test;
import sepm.ss15.grp16.entity.user.BodyfatHistory;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.entity.user.WeightHistory;
import sepm.ss15.grp16.service.AbstractServiceTest;
import sepm.ss15.grp16.service.Service;

import java.util.Date;

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

    @Test
    public void createWithValidWeightHistory() throws Exception{
        WeightHistory testWeightHistory = new WeightHistory(null, createUserForTest().getUser_id(), 85, new Date());
        createTest(testWeightHistory);
    }

    private User createUserForTest() throws Exception {
        User testUser = new User(null, "maxmustermann", true, 20, 194, "max.mustermann@gmail.com", "/path/playlist/", false);
        return userService.create(testUser);
    }
}
