package sepm.ss15.grp16.service.exercise.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import sepm.ss15.grp16.entity.exercise.Exercise;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.exception.DBException;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exercise.AbstractExerciseServiceTest;
import sepm.ss15.grp16.service.exercise.ExerciseService;
import sepm.ss15.grp16.service.user.UserService;

import java.sql.SQLException;

/**
 * Created by lukas on 17.05.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class ExerciseServiceTestImpl extends AbstractExerciseServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    ExerciseService exerciseService;
    @Autowired
    private DBHandler dbConnector;

    @Before
    public void setUp() throws DBException, SQLException {
        dbConnector.activateTestMode();
    }

    @After
    public void tearDown() throws DBException, SQLException {
        dbConnector.deactivateTestMode();
    }

    @Override
    public UserService getUserService() {
        return userService;
    }

    @Override
    public ExerciseService getExerciseService() {
        return exerciseService;
    }


    @Override
    public Service<Exercise> getService() {
        return exerciseService;
    }
}
