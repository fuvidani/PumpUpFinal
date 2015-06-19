package sepm.ss15.grp16.persistence.dao.calendar.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import sepm.ss15.grp16.entity.calendar.Appointment;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.dao.calendar.AbstractCalendarDAOTest;
import sepm.ss15.grp16.persistence.dao.calendar.CalendarDAO;
import sepm.ss15.grp16.persistence.dao.exercise.ExerciseDAO;
import sepm.ss15.grp16.persistence.dao.training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.dao.user.UserDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.exception.DBException;

import java.sql.SQLException;

/**
 * Created by David on 2015.06.14..
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config-test.xml")
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class H2CalendarDAOTestImpl extends AbstractCalendarDAOTest {

    @Autowired
    private CalendarDAO calendarDAO;

    @Autowired
    private DBHandler dbConnector;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ExerciseDAO exerciseDAO;

    @Autowired
    private TrainingsplanDAO trainingsplanDAO;

    @Before
    public void setUp() throws DBException, SQLException {
    }

    @After
    public void tearDown() throws DBException, SQLException {
    }

    @Override
    public DAO<Appointment> getDAO() {
        return calendarDAO;
    }

    @Override
    protected UserDAO getUserDAO() {
        return userDAO;
    }

    @Override
    protected ExerciseDAO getExerciseDAO() {
        return exerciseDAO;
    }

    @Override
    protected TrainingsplanDAO getTrainingsplanDAO() {
        return trainingsplanDAO;
    }
}

