package sepm.ss15.grp16.persistence.dao.exercise.impl;

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
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.dao.exercise.AbstractExerciseDaoTest;
import sepm.ss15.grp16.persistence.dao.exercise.ExerciseDAO;
import sepm.ss15.grp16.persistence.dao.user.UserDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.exception.DBException;

import java.sql.SQLException;

/**
 * Created by lukas on 30.04.2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config-test.xml")
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class H2ExerciseDAOImplTest extends AbstractExerciseDaoTest {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ExerciseDAO exerciseDAO;

    @Override
    public DAO<Exercise> getDAO() {
        return exerciseDAO;
    }

    @Override
    public UserDAO getUserDAO() {
        return userDAO;
    }
}
