package PersitanceTest.TrainingTest;

import org.junit.Assert;
import org.junit.Test;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.entity.Training.ExerciseSet;
import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.persistence.dao.ExerciseDAO;
import sepm.ss15.grp16.persistence.dao.Training.ExerciseSetDAO;
import sepm.ss15.grp16.persistence.dao.Training.ExerciseSetDAO;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsSessionDAO;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lukas
 * Date: 09.05.2015
 */
public abstract class AbstractExerciseSetDAOTest {

	public abstract ExerciseDAO getExerciseDAO();
	public abstract ExerciseSetDAO getExerciseSetDAO();
    public abstract TrainingsplanDAO getTrainingsplanDAO();
    public abstract TrainingsSessionDAO getTrainingsSessionDAO();

	@Test
	public void createValid() throws PersistenceException {
		ExerciseSet exerciseSet = dummyExerciseSet();

		Assert.assertFalse(getExerciseSetDAO().findAll().contains(exerciseSet));
		getExerciseSetDAO().create(exerciseSet);
		Assert.assertTrue(getExerciseSetDAO().findAll().contains(exerciseSet));
	}

	@Test
	public void updateValid() throws PersistenceException {
		ExerciseSet exerciseSet_old = dummyExerciseSet();

		Assert.assertFalse(getExerciseSetDAO().findAll().contains(exerciseSet_old));

		exerciseSet_old = getExerciseSetDAO().create(exerciseSet_old);
		ExerciseSet exerciseSet_new = new ExerciseSet(exerciseSet_old);
		exerciseSet_new.setRepeat(5);

		Assert.assertTrue(getExerciseSetDAO().findAll().contains(exerciseSet_old));
		Assert.assertFalse(getExerciseSetDAO().findAll().contains(exerciseSet_new));

		exerciseSet_new = getExerciseSetDAO().update(exerciseSet_new);
		Assert.assertFalse(getExerciseSetDAO().findAll().contains(exerciseSet_old));
		Assert.assertTrue(getExerciseSetDAO().findAll().contains(exerciseSet_new));
	}

	@Test
	public void deleteValid() throws PersistenceException {
		ExerciseSet exerciseSet = dummyExerciseSet();

		Assert.assertFalse(getExerciseSetDAO().findAll().contains(exerciseSet));
		getExerciseSetDAO().create(exerciseSet);
		Assert.assertTrue(getExerciseSetDAO().findAll().contains(exerciseSet));

		getExerciseSetDAO().delete(exerciseSet);
		Assert.assertFalse(getExerciseSetDAO().findAll().contains(exerciseSet));
	}

	@Test
	public void searchByIDValid() throws PersistenceException{
		ExerciseSet ExerciseSet = dummyExerciseSet();

		Assert.assertFalse(getExerciseSetDAO().findAll().contains(ExerciseSet));
		getExerciseSetDAO().create(ExerciseSet);
		Assert.assertTrue(getExerciseSetDAO().findAll().contains(ExerciseSet));

		ExerciseSet searched = getExerciseSetDAO().searchByID(ExerciseSet.getId());
		Assert.assertEquals(ExerciseSet, searched);
	}

	private ExerciseSet dummyExerciseSet() throws PersistenceException {
		ExerciseSet exerciseSet = new ExerciseSet();

        exerciseSet.setRepeat(20);
        exerciseSet.setOrder_nr(1);

		exerciseSet.setIsDeleted(false);

        exerciseSet.setExercise(createExercise());
        exerciseSet.setSession(dummyTrainingsSession());

		return exerciseSet;
	}

	private Exercise createExercise() throws PersistenceException {

		Exercise liegestuetz = new Exercise("liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", false);
		return getExerciseDAO().create(liegestuetz);

	}

    private TrainingsSession dummyTrainingsSession() throws PersistenceException {
        Trainingsplan plan = createRefTraining();

        TrainingsSession trainingsSession = new TrainingsSession();

        trainingsSession.setTrainingsplan(plan);
        trainingsSession.setName("testsession");
        trainingsSession.setIsDeleted(false);

        getTrainingsSessionDAO().create(trainingsSession);

        return trainingsSession;
    }

    private Trainingsplan createRefTraining() throws PersistenceException {
        Trainingsplan plan = new Trainingsplan();

        plan.setName("testtraining");
        plan.setDescr("testdescription");
        plan.setIsDeleted(false);

        return getTrainingsplanDAO().create(plan);
    }
}
