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
import sepm.ss15.grp16.service.AbstractServiceTestMockito;
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
public abstract class AbstractTrainingsServiceTest extends AbstractServiceTestMockito<Trainingsplan> {

    @Test
    public void createSimpleValid() throws Exception {
        createTest(dummySimpleTrainingsPlan());
    }

    @Test(expected = ServiceException.class)
    public void createSimpleNonValid() throws Exception {
        createTestFail(null);
    }

    @Test
    public void createPlanWithSessionValid() throws Exception {
        createTest(dummyTrainingsPlanWithSession());
    }

    @Test
    public void createPlanWithSetValid() throws Exception {
        createTest(dummyTrainingsPlanWithSet());
    }

    @Test(expected = ValidationException.class)
    public void createSimpleNotValid() throws Exception {
        Trainingsplan trainingsplan = dummySimpleTrainingsPlan();

        trainingsplan.setName(null);
        createTest(trainingsplan);

    }

    @Test(expected = ValidationException.class)
    public void createPlanWithSessionNotValid() throws Exception {
        Trainingsplan trainingsplan = dummyTrainingsPlanWithSession();

        trainingsplan.getTrainingsSessions().get(0).setName(null);
        createTestFail(trainingsplan);
    }

    @Test(expected = ValidationException.class)
    public void createPlanWithSetNotValid() throws Exception {
        Trainingsplan trainingsplan = dummyTrainingsPlanWithSet();

        trainingsplan.getTrainingsSessions().get(0).getExerciseSets().get(0).setOrder_nr(null);
        createTestFail(trainingsplan);
    }

    @Test
    public void updateValidSimplePlan() throws Exception {
        updateTest(dummySimpleTrainingsPlan());
    }

    @Test
    public void updateValidPlanWithSession() throws Exception {
        updateTest(dummyTrainingsPlanWithSession());
    }

    @Test
    public void updateValidPlanWithSet() throws Exception {
        updateTest(dummyTrainingsPlanWithSet());
    }

    private User dummyUser() throws Exception {
        return new User(1, "msober", true, 20, 194, false);
    }

    private Exercise dummyExercise() throws Exception {
        List<String> gifList = new ArrayList<>();
        List<AbsractCategory> categoryList = new ArrayList<>();
        categoryList.add(new MusclegroupCategory(5, "Trizeps"));
        categoryList.add(new TrainingsCategory(2, "Balance"));
        return new Exercise(1, "liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, null, categoryList);
    }

    private List<ExerciseSet> dummySet() throws Exception {
        List<ExerciseSet> sets = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ExerciseSet set = new ExerciseSet();

            set.setId(i);
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
            trainingsSession.setId(1);
            trainingsSession.setName("testsession");
            trainingsSession.setIsDeleted(false);
            sessions.add(trainingsSession);
        }

        return sessions;
    }

    private Trainingsplan dummySimpleTrainingsPlan() {
        Trainingsplan trainingsplan = new Trainingsplan();

        trainingsplan.setId(1);
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

    private Trainingsplan dummyTrainingsPlanWithSet() throws Exception {
        Trainingsplan trainingsplan = dummySimpleTrainingsPlan();

        List<TrainingsSession> sessions = dummySession();
        for (TrainingsSession session : sessions) {
            session.setExerciseSets(dummySet());
        }

        trainingsplan.setTrainingsSessions(sessions);
        return trainingsplan;
    }
}
