package sepm.ss15.grp16.persistence.dao.user.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import sepm.ss15.grp16.persistence.dao.user.AbstractBodyfatHistoryDaoTest;
import sepm.ss15.grp16.persistence.dao.user.BodyfatHistoryDAO;
import sepm.ss15.grp16.persistence.dao.user.UserDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;

/**
 * Created by michaelsober on 06.05.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config-test.xml")
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class H2BodyfatHistoryDAOImplTest extends AbstractBodyfatHistoryDaoTest {

    @Autowired
    private DBHandler dbConnector;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setBodyfatHistoryDAO(BodyfatHistoryDAO bodyfatHistoryDAO) {
        this.bodyfatHistoryDAO = bodyfatHistoryDAO;
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
