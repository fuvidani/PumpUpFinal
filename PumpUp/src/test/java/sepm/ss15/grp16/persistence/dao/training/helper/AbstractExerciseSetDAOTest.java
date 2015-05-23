package sepm.ss15.grp16.persistence.dao.training.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import sepm.ss15.grp16.entity.exercise.AbsractCategory;
import sepm.ss15.grp16.entity.exercise.Exercise;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.persistence.dao.exercise.ExerciseDAO;
import sepm.ss15.grp16.persistence.dao.training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lukas
 * Date: 09.05.2015
 */
public abstract class AbstractExerciseSetDAOTest {

    private static final Logger LOGGER = LogManager.getLogger(AbstractExerciseSetDAOTest.class);

    private List<ExerciseSet> debugglist;

    public abstract ExerciseDAO getExerciseDAO();

    public abstract TrainingsplanDAO getTrainingsplanDAO();

    public abstract TrainingsSessionHelperDAO getTrainingsSessionHelperDAO();

    public abstract ExerciseSetHelperDAO getDAO();

    @Test
    public void createValid() throws PersistenceException {
        ExerciseSet dto = dummyExerciseSet();
        LOGGER.info("createValid: " + dto);
        debugglist = getDAO().findAll();
        Assert.assertFalse(getDAO().findAll().contains(dto));
        dto = getDAO().create(dto, dummyTrainingsSession().getId());
        debugglist = getDAO().findAll();
        Assert.assertTrue(getDAO().findAll().contains(dto));
    }

    @Test
    public void updateValid() throws PersistenceException {
        ExerciseSet dto_old = dummyExerciseSet();
        ExerciseSet dto_new = new ExerciseSet(dto_old);
        dto_new.setRepeat(5);

        LOGGER.info("updateValid: " + dto_old + "to " + dto_new);
        debugglist = getDAO().findAll();
        Assert.assertFalse(getDAO().findAll().contains(dto_old));

        dto_old = getDAO().create(dto_old, dummyTrainingsSession().getId());
        dto_new.setId(dto_old.getId());

        debugglist = getDAO().findAll();
        Assert.assertTrue(getDAO().findAll().contains(dto_old));
        Assert.assertFalse(getDAO().findAll().contains(dto_new));

        dto_new = getDAO().update(dto_new, dummyTrainingsSession().getId());
        debugglist = getDAO().findAll();
        Assert.assertFalse(getDAO().findAll().contains(dto_old));
        Assert.assertTrue(getDAO().findAll().contains(dto_new));
    }

    @Test
    public void deleteValid() throws PersistenceException {
        ExerciseSet dto = dummyExerciseSet();

        LOGGER.info("deleteValid: " + dto);
        debugglist = getDAO().findAll();
        Assert.assertFalse(getDAO().findAll().contains(dto));
        dto = getDAO().create(dto, dummyTrainingsSession().getId());
        debugglist = getDAO().findAll();
        Assert.assertTrue(getDAO().findAll().contains(dto));

        getDAO().delete(dto);
        debugglist = getDAO().findAll();
        Assert.assertFalse(getDAO().findAll().contains(dto));
    }

    @Test
    public void searchByIDValid() throws PersistenceException {
        ExerciseSet dto = dummyExerciseSet();

        LOGGER.info("deleteValid: " + dto);
        debugglist = getDAO().findAll();
        Assert.assertFalse(getDAO().findAll().contains(dto));
        dto = getDAO().create(dto, dummyTrainingsSession().getId());
        debugglist = getDAO().findAll();
        Assert.assertTrue(getDAO().findAll().contains(dto));

        ExerciseSet dto_found = getDAO().searchByID(dto.getId());
        debugglist = getDAO().findAll();
        Assert.assertEquals(dto_found, dto);
    }

    @Test
    public void searchBySessionIDValid() throws PersistenceException {
        ExerciseSet dto = dummyExerciseSet();
        TrainingsSession session = dummyTrainingsSession();

        LOGGER.info("deleteValid: " + dto);
        debugglist = getDAO().findAll();
        Assert.assertFalse(getDAO().findAll().contains(dto));
        dto = getDAO().create(dto, session.getId());
        debugglist = getDAO().findAll();
        Assert.assertTrue(getDAO().findAll().contains(dto));

        List<ExerciseSet> list = getDAO().searchBySessionID(session.getId());
        debugglist = getDAO().findAll();
        Assert.assertTrue(list.contains(dto));
    }

    @Test
    public void getSessionBySetValid() throws PersistenceException {
        ExerciseSet dto = dummyExerciseSet();
        TrainingsSession session = dummyTrainingsSession();
        List<ExerciseSet> sets = new ArrayList<>();
        sets.add(dto);
        session.setExerciseSets(sets);

        LOGGER.info("deleteValid: " + dto);
        debugglist = getDAO().findAll();
        Assert.assertFalse(getDAO().findAll().contains(dto));
        dto = getDAO().create(dto, session.getId());
        debugglist = getDAO().findAll();
        Assert.assertTrue(getDAO().findAll().contains(dto));

        TrainingsSession session1 = getDAO().getSessionBySet(dto);
        debugglist = getDAO().findAll();
        Assert.assertEquals(session1, session);
    }

    private Exercise createExercise() throws PersistenceException {
        List<String> gifList = new ArrayList<>();
        //URL url = this.getClass().getResource("/img/pushup.jpg");
        //gifList.add(url.toString().substring(6));
        List<AbsractCategory> categoryList = new ArrayList<>();
        //categoryList.add(new MusclegroupCategory(5, "Bizeps NEU"));
        //categoryList.add(new TrainingsCategory(2, "kraft"));

        Exercise liegestuetz = new Exercise(null, "beinheben", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, null, categoryList);

        return getExerciseDAO().create(liegestuetz);
    }

    private ExerciseSet dummyExerciseSet() throws PersistenceException {
        ExerciseSet exerciseSet = new ExerciseSet();

        exerciseSet.setRepeat(20);
        exerciseSet.setType(ExerciseSet.SetType.repeat);
        exerciseSet.setOrder_nr(1);
        exerciseSet.setIsDeleted(false);

        exerciseSet.setExercise(createExercise());

        return exerciseSet;
    }

    private TrainingsSession dummyTrainingsSession() throws PersistenceException {
        TrainingsSession trainingsSession = new TrainingsSession();
        Trainingsplan plan = dummyTrainingsPlan();

        trainingsSession.setName("testsession");
        trainingsSession.setIsDeleted(false);

        trainingsSession = getTrainingsSessionHelperDAO().create(trainingsSession, plan.getId());

        return trainingsSession;
    }

    private Trainingsplan dummyTrainingsPlan() throws PersistenceException {
        Trainingsplan plan = new Trainingsplan();

        plan.setName("testtraining");
        plan.setDescr("testdescription");
        plan.setDuration(10);
        plan.setIsDeleted(false);

        plan = getTrainingsplanDAO().create(plan);

        return plan;
    }
}
