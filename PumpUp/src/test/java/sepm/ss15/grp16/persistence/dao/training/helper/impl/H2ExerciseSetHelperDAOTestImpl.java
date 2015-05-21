package sepm.ss15.grp16.persistence.dao.training.helper.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import sepm.ss15.grp16.persistence.dao.ExerciseDAO;
import sepm.ss15.grp16.persistence.dao.training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.dao.training.helper.AbstractExerciseSetDAOTest;
import sepm.ss15.grp16.persistence.dao.training.helper.ExerciseSetHelperDAO;
import sepm.ss15.grp16.persistence.dao.training.helper.TrainingsSessionHelperDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.exception.DBException;

import java.sql.SQLException;

/**
 * Created by lukas on 11.05.15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class H2ExerciseSetHelperDAOTestImpl extends AbstractExerciseSetDAOTest {

    @Autowired
    private DBHandler dbConnector;

    @Autowired
    private ExerciseDAO exerciseDAO;

    @Autowired
    private TrainingsplanDAO trainingsplanDAO;

    @Autowired
    private TrainingsSessionHelperDAO trainingsSessionHelperDAO;

    @Autowired
    private ExerciseSetHelperDAO exerciseSetHelperDAO;

    public ExerciseDAO getExerciseDAO() {
        return exerciseDAO;
    }

    public TrainingsplanDAO getTrainingsplanDAO() {
        return trainingsplanDAO;
    }

    @Override
    public TrainingsSessionHelperDAO getTrainingsSessionHelperDAO() {
        return trainingsSessionHelperDAO;
    }

    @Override
    public ExerciseSetHelperDAO getDAO() {
        return exerciseSetHelperDAO;
    }

    @Before
    public void setUp() throws DBException, SQLException {
        dbConnector.activateTestMode();
    }

    @After
    public void tearDown() throws DBException, SQLException {
        dbConnector.deactivateTestMode();
    }
}
