package PersitanceTest.TrainingTest;

import PersitanceTest.AbstractDAOTest;
import org.junit.Test;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.entity.Training.ExerciseSet;
import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.persistence.dao.ExerciseDAO;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsSessionDAO;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

/**
 * Author: Lukas
 * Date: 09.05.2015
 */
public abstract class AbstractExerciseSetDAOTest extends AbstractDAOTest<ExerciseSet> {

	public abstract ExerciseDAO getExerciseDAO();

	public abstract TrainingsplanDAO getTrainingsplanDAO();

	public abstract TrainingsSessionDAO getTrainingsSessionDAO();

	@Test
	public void createValid() throws PersistenceException {
		createValid(dummyExerciseSet());
	}

	@Test
	public void updateValid() throws PersistenceException {
		ExerciseSet exerciseSet_old = dummyExerciseSet();
		ExerciseSet exerciseSet_new = new ExerciseSet(exerciseSet_old);
		exerciseSet_new.setRepeat(5);

		updateValid(exerciseSet_old, exerciseSet_new);
	}

	@Test
	public void deleteValid() throws PersistenceException {
		deleteValid(dummyExerciseSet());
	}

	@Test
	public void searchByIDValid() throws PersistenceException {
		searchByIDValid(dummyExerciseSet());
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
