package PersitanceTest.TrainingTest.Impl;

import PersitanceTest.TrainingTest.AbstractExerciseSetDAOTest;
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
import sepm.ss15.grp16.persistence.dao.Training.ExerciseSetDAO;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsSessionDAO;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsplanDAO;
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
public class H2ExerciseSetDAOTestImpl extends AbstractExerciseSetDAOTest{

    @Autowired
    private DBHandler dbConnector;

    @Autowired
    private ExerciseDAO exerciseDAO;

    @Autowired
    private ExerciseSetDAO exerciseSetDAO;

    @Autowired
    private TrainingsplanDAO trainingsplanDAO;

    @Autowired
    private TrainingsSessionDAO trainingsSessionDAO;

    @Override
    public ExerciseDAO getExerciseDAO() {
        return exerciseDAO;
    }

    @Override
    public ExerciseSetDAO getExerciseSetDAO() {
        return exerciseSetDAO;
    }

    @Override
    public TrainingsplanDAO getTrainingsplanDAO() {
        return trainingsplanDAO;
    }

    @Override
    public TrainingsSessionDAO getTrainingsSessionDAO() {
        return trainingsSessionDAO;
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
