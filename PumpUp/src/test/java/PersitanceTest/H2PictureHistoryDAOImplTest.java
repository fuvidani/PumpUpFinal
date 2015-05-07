package PersitanceTest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import sepm.ss15.grp16.persistence.dao.impl.H2PictureHistoryDAOImpl;
import sepm.ss15.grp16.persistence.database.impl.H2DBConnectorImpl;

import java.sql.Connection;

/**
 * Created by michaelsober on 07.05.15.
 */
public class H2PictureHistoryDAOImplTest extends AbstractPictureHistoryDaoTest{

    private static Connection con;

    @BeforeClass
    public static void setUpClass() throws Exception{
        con = new H2DBConnectorImpl("jdbc:h2:tcp://localhost/~/pumpup", "sa", "").getConnection();
    }

    @AfterClass
    public static void tearDownClass() throws Exception{
        new H2DBConnectorImpl("jdbc:h2:tcp://localhost/~/pumpup", "sa", "").closeConnection();
    }

    @Before
    public void setUp() throws Exception {
        this.setPictureHistoryDAO(new H2PictureHistoryDAOImpl());
        con.setAutoCommit(false);
    }

    @After
    public void tearDown() throws Exception {
        con.rollback();
        con.setAutoCommit(true);
    }
}
