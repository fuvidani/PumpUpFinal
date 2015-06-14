package sepm.ss15.grp16.persistence.dao.calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import sepm.ss15.grp16.entity.calendar.Appointment;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.dao.AbstractDAOTest;
import sepm.ss15.grp16.persistence.dao.training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.dao.user.UserDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by David on 2015.06.14..
 */
public abstract class AbstractCalendarDAOTest extends AbstractDAOTest<Appointment> {
    private static final Logger LOGGER = LogManager.getLogger(AbstractCalendarDAOTest.class);

    protected abstract UserDAO getUserDAO();
    protected abstract TrainingsplanDAO getTrainingsplanDAO();

    @Test
    public void createValidAppointment() throws PersistenceException {
        LOGGER.info("createValidAppointment");
        getDAO().create(dummyAppointment());
    }

    @Test (expected = PersistenceException.class)
    public void createWithNullShouldThrowException() throws PersistenceException {
        LOGGER.info("createWithNullShouldThrowException");
        getDAO().create(null);
    }

    @Test (expected = PersistenceException.class)
    public void updateWithNullShouldThrowException() throws PersistenceException {
        LOGGER.info("updateWithNullShouldThrowException");
        getDAO().create(null);
    }

    @Test (expected = PersistenceException.class)
    public void deleteWithNullShouldThrowException() throws PersistenceException {
        LOGGER.info("deleteWithNullShouldThrowException");
        getDAO().create(null);
    }
    


    private User dummyUser() throws PersistenceException {
        User testUser = new User(null, "lukas", true, 22, 178, false);
        return getUserDAO().create(testUser);
    }

    private Appointment dummyAppointment() throws PersistenceException {
        Appointment appointment = new Appointment(null,new Date(),dummyTrainingsPlanWithSession().getTrainingsSessions().get(0).getId_session(),dummyUser().getUser_id(),false,false);
        return appointment;
    }

    private Trainingsplan dummyTrainingsPlanWithSession() throws PersistenceException {
        Trainingsplan trainingsplan = dummySimpleTrainingsPlan();
        trainingsplan.setTrainingsSessions(dummySession());

        return getTrainingsplanDAO().create(trainingsplan);

    }

    private List<TrainingsSession> dummySession() {
        List<TrainingsSession> sessions = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            TrainingsSession trainingsSession = new TrainingsSession();
            trainingsSession.setName("testsession");
            trainingsSession.setIsDeleted(false);
            sessions.add(trainingsSession);
        }

        return sessions;
    }

    private Trainingsplan dummySimpleTrainingsPlan() {
        Trainingsplan trainingsplan = new Trainingsplan();

        trainingsplan.setName("testtraining");
        trainingsplan.setDescr("testdescription");
        trainingsplan.setIsDeleted(false);
        trainingsplan.setDuration(5);

        return trainingsplan;
    }

}
