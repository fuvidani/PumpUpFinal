package sepm.ss15.grp16.service.user.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.service.user.AbstractBodyfatHistoryServiceTest;
import sepm.ss15.grp16.service.user.BodyfatHistoryService;
import sepm.ss15.grp16.service.user.UserService;

/**
 * Created by michaelsober on 05.05.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class AbstractBodyfatHistoryServiceImplTest extends AbstractBodyfatHistoryServiceTest {

    @Autowired
    private DBHandler dbConnector;

    @Autowired
    public void setBodyfatHistoryService(BodyfatHistoryService bodyfatHistoryService) {
        this.bodyfatHistoryService = bodyfatHistoryService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Before
    public void setUp() throws Exception {
        dbConnector.getConnection().setAutoCommit(false);
    }

    @After
    public void tearDown() throws Exception {
        dbConnector.getConnection().rollback();
        dbConnector.getConnection().setAutoCommit(true);
    }
}
