package sepm.ss15.grp16.service.training;

import org.junit.Test;
import sepm.ss15.grp16.entity.exercise.AbsractCategory;
import sepm.ss15.grp16.entity.exercise.Exercise;
import sepm.ss15.grp16.entity.exercise.MusclegroupCategory;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.AbstractServiceTest;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.exercise.ExerciseService;
import sepm.ss15.grp16.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lukas
 * Date: 13.05.2015
 */
public abstract class AbstractTrainingsServiceTest extends AbstractServiceTest<Trainingsplan> {

    public abstract UserService getUserService();

    public abstract ExerciseService getExerciseService();

    @Test
    public void createSimpleValid() throws ServiceException {
        createTest(dummySimpleTrainingsPlan());
    }

    @Test
    public void createPlanWithSessionValid() throws ServiceException {
        createTest(dummyTrainingsPlanWithSession());
    }

    @Test
    public void createPlanWithSetValid() throws ServiceException {
        createTest(dummyTrainingsPlanWithSet());
    }

    @Test(expected = ValidationException.class)
    public void createSimpleNotValid() throws ServiceException {
        Trainingsplan trainingsplan = dummySimpleTrainingsPlan();

        trainingsplan.setName(null);
        createTest(trainingsplan);

    }

    @Test(expected = ValidationException.class)
    public void createPlanWithSessionNotValid() throws ServiceException {
        Trainingsplan trainingsplan = dummyTrainingsPlanWithSession();

        trainingsplan.getTrainingsSessions().get(0).setName(null);
        createTest(trainingsplan);
    }

    @Test(expected = ValidationException.class)
    public void createPlanWithSetNotValid() throws ServiceException, PersistenceException {
        Trainingsplan trainingsplan = dummyTrainingsPlanWithSet();

        trainingsplan.getTrainingsSessions().get(0).getExerciseSets().get(0).setOrder_nr(null);
        createTest(trainingsplan);
    }

    @Test
    public void updateValidSimplePlan() throws ServiceException {
        Trainingsplan trainingsplan_old = dummySimpleTrainingsPlan();
        Trainingsplan trainingsplan_new = new Trainingsplan(trainingsplan_old);
        trainingsplan_new.setName("changed name");

        updateTest(trainingsplan_old, trainingsplan_new);
    }

    @Test
    public void updateValidPlanWithSession() throws ServiceException {
        Trainingsplan trainingsplan_old = dummyTrainingsPlanWithSession();
        Trainingsplan trainingsplan_new = new Trainingsplan(trainingsplan_old);
        trainingsplan_new.setName("changed name");

        updateTest(trainingsplan_old, trainingsplan_new);
    }

    @Test
    public void updateValidPlanWithSet() throws ServiceException {
        Trainingsplan trainingsplan_old = dummyTrainingsPlanWithSet();
        Trainingsplan trainingsplan_new = new Trainingsplan(trainingsplan_old);
        trainingsplan_new.setName("changed name");

        updateTest(trainingsplan_old, trainingsplan_new);
    }

    private User dummyUser() throws ServiceException {
        User testUser = new User(null, "msober", true, 20, 194, false);
        return getUserService().create(testUser);
    }

    private Exercise dummyExercise() throws ServiceException {
        List<String> gifList = new ArrayList<>();
        //String url = this.getClass().getResource("/img/testbild.jpg").toURI().getPath();
        //gifList.add(url);
        List<AbsractCategory> categoryList = new ArrayList<>();
        categoryList.add(new MusclegroupCategory(5, "Trizeps"));
        categoryList.add(new TrainingsCategory(2, "Balance"));
        Exercise exercise = new Exercise(null, "liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, null, categoryList);
        return getExerciseService().create(exercise);
    }

    private List<ExerciseSet> dummySet() throws ServiceException {
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
        trainingsplan.setDuration(10);
        trainingsplan.setIsDeleted(false);

        return trainingsplan;
    }

    private Trainingsplan dummyTrainingsPlanWithSession() {
        Trainingsplan trainingsplan = dummySimpleTrainingsPlan();
        trainingsplan.setTrainingsSessions(dummySession());

        return trainingsplan;
    }

    private Trainingsplan dummyTrainingsPlanWithSet() throws ServiceException {
        Trainingsplan trainingsplan = dummySimpleTrainingsPlan();

        List<TrainingsSession> sessions = dummySession();
        for (TrainingsSession session : sessions) {
            session.setExerciseSets(dummySet());
        }

        trainingsplan.setTrainingsSessions(sessions);
        return trainingsplan;
    }
}
