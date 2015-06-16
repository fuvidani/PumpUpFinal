package sepm.ss15.grp16.persistence.dao.calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import sepm.ss15.grp16.entity.calendar.Appointment;
import sepm.ss15.grp16.entity.exercise.AbsractCategory;
import sepm.ss15.grp16.entity.exercise.Exercise;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.dao.AbstractDAOTest;
import sepm.ss15.grp16.persistence.dao.exercise.ExerciseDAO;
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

    protected abstract ExerciseDAO getExerciseDAO();


    @Test
    public void createValidAppointment() throws PersistenceException {
        LOGGER.info("createValidAppointment");

        //dummyTrainingsPlanWithSession();
        //dummyTrainingsPlanWithSet();
        //createValid(dummyAppointment());
    }

    @Test
    public void updateValidAppointment() throws PersistenceException {
        LOGGER.info("createValidAppointment");
        Appointment old = dummyAppointment();
        Appointment newApp = dummyAppointment();
        newApp.setId(old.getId());
        newApp.setIsTrained(true);
        //updateValid(old, newApp);
    }

    @Test(expected = PersistenceException.class)
    public void createWithNullShouldThrowException() throws PersistenceException {
        LOGGER.info("createWithNullShouldThrowException");
        getDAO().create(null);
    }

    @Test(expected = PersistenceException.class)
    public void createWithInvalidSessionIDShouldThrowException() throws PersistenceException {
        LOGGER.info("createWithInvalidSessionIDShouldThrowException");
        Appointment appointment = dummyAppointment();
        appointment.setSession_id(123);

        getDAO().create(appointment);
    }


    @Test(expected = PersistenceException.class)
    public void createWithInvalidUserIDShouldThrowException() throws PersistenceException {
        LOGGER.info("createWithInvalidUserIDShouldThrowException");
        Appointment appointment = dummyAppointment();
        appointment.setUser_id(123);

        getDAO().create(appointment);
    }

    @Test(expected = PersistenceException.class)
    public void updateWithNullShouldThrowException() throws PersistenceException {
        LOGGER.info("updateWithNullShouldThrowException");
        getDAO().create(null);
    }

    @Test(expected = PersistenceException.class)
    public void deleteWithNullShouldThrowException() throws PersistenceException {
        LOGGER.info("deleteWithNullShouldThrowException");
        getDAO().create(null);
    }


    private User dummyUser() throws PersistenceException {
        User testUser = new User(null, "lukas", true, 22, 178, false);
        return getUserDAO().create(testUser);
    }

    private Appointment dummyAppointment() throws PersistenceException {
        Appointment appointment = new Appointment(null, new Date(), dummyTrainingsPlanWithSession().getTrainingsSessions().get(0).getId_session(), dummyUser().getUser_id(), false, false);
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

    private List<ExerciseSet> dummySet() throws PersistenceException {
        List<ExerciseSet> sets = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ExerciseSet set = new ExerciseSet();

            set.setRepeat(15);
            set.setType(ExerciseSet.SetType.repeat);
            set.setExercise(dummyExercise());
            set.setIsDeleted(false);
            set.setUser(dummyUser());

            set.setOrder_nr(i + 1);
            sets.add(set);
        }
        return sets;
    }

    private Exercise dummyExercise() throws PersistenceException {
        List<String> gifList = new ArrayList<>();
        //URL url = this.getClass().getResource("/img/pushup.jpg");
        //gifList.add(url.toString().substring(6));
        List<AbsractCategory> categoryList = new ArrayList<>();
        //categoryList.add(new MusclegroupCategory(5, "Bizeps NEU"));
        //categoryList.add(new TrainingsCategory(2, "kraft"));

        Exercise liegestuetz = new Exercise(null, "beinheben", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, dummyUser(), categoryList);

        return getExerciseDAO().create(liegestuetz);
    }

    private Trainingsplan dummyTrainingsPlanWithSet() throws PersistenceException {
        Trainingsplan trainingsplan = dummySimpleTrainingsPlan();

        List<TrainingsSession> sessions = dummySession();
        for (TrainingsSession session : sessions) {
            session.setExerciseSets(dummySet());
        }

        trainingsplan.setTrainingsSessions(sessions);
        return getTrainingsplanDAO().create(trainingsplan);
    }
}
