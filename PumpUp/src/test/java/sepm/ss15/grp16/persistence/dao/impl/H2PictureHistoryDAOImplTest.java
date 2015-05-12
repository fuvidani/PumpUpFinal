package sepm.ss15.grp16.persistence.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sepm.ss15.grp16.persistence.dao.AbstractPictureHistoryDaoTest;
import sepm.ss15.grp16.persistence.dao.PictureHistoryDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;

/**
 * Created by michaelsober on 07.05.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class H2PictureHistoryDAOImplTest extends AbstractPictureHistoryDaoTest {

    @Autowired
    private DBHandler dbConnector;

    @Autowired
    public void setPictureHistoryDAO(PictureHistoryDAO pictureHistoryDAO) {
        this.pictureHistoryDAO = pictureHistoryDAO;
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
