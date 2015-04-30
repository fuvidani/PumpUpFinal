package java.PersitanceTest;

import main.java.sepm.ss15.grp16.persistence.DBHandler;
import main.java.sepm.ss15.grp16.persistence.ExerciseDAO;
import main.java.sepm.ss15.grp16.persistence.exception.DBException;
import main.java.sepm.ss15.grp16.persistence.exception.PersistenceException;
import main.java.sepm.ss15.grp16.persistence.h2.H2DBConnectorImpl;
import main.java.sepm.ss15.grp16.persistence.h2.H2ExerciseDAOImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.sql.SQLException;

/**
 * Created by lukas on 30.04.2015.
 */
public class H2ExerciseDAOImplTest extends  AbstractExerciseDaoTest {
    private static ExerciseDAO exerciseDAO;
    private DBHandler dbHandler;

    @BeforeClass
    public static void setUpClass() {

        try {
            exerciseDAO = H2ExerciseDAOImpl.getInstance();
            setExerciseDAO(exerciseDAO);
        }catch (PersistenceException e){
            e.printStackTrace();
        }

    }

    @Before
    public void setUp() {
        try {
            dbHandler = H2DBConnectorImpl.getInstance();
            dbHandler.getConnection().setAutoCommit(false);
        }catch ( DBException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @After
    public void tearDown() {
        try {
            dbHandler.getConnection().rollback();
        } catch (DBException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDownClass() {
    }

}
