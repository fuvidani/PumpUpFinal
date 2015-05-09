package PersitanceTest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sepm.ss15.grp16.persistence.dao.BodyfatHistoryDAO;
import sepm.ss15.grp16.persistence.dao.impl.H2BodyfatHistoryDAOImpl;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.database.impl.H2DBConnectorImpl;

import java.sql.Connection;

/**
 * Created by michaelsober on 06.05.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class H2BodyfatHistoryDAOImplTest extends AbstractBodyfatHistoryDaoTest {

    @Autowired
    private DBHandler dbConnector;

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
