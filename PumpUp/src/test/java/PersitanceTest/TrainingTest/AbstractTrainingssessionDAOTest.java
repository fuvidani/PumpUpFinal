package PersitanceTest.TrainingTest;

import PersitanceTest.AbstractDAOTest;
import org.junit.Assert;
import org.junit.Test;
import sepm.ss15.grp16.entity.Training.ExerciseSet;
import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsSessionDAO;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 09.05.2015
 */
public abstract class AbstractTrainingssessionDAOTest extends AbstractDAOTest<TrainingsSession> {

	public abstract TrainingsplanDAO getTrainingsplanDAO();

	@Test
	public void createValid() throws PersistenceException {
		createValid(dummyTrainingsSession());
	}

	@Test
	public void updateValid() throws PersistenceException {
		TrainingsSession trainingsSession_old = dummyTrainingsSession();
		TrainingsSession TrainingsSession_new = new TrainingsSession(trainingsSession_old);
		TrainingsSession_new.setName("changed name");

		updateValid(trainingsSession_old, TrainingsSession_new);
	}

	@Test
	public void deleteValid() throws PersistenceException {
		deleteValid(dummyTrainingsSession());
	}

	@Test
	public void searchByIDValid() throws PersistenceException {
		searchByIDValid(dummyTrainingsSession());
	}

	@Test
	public void findValid() throws PersistenceException {
		TrainingsSession TrainingsSession1 = dummyTrainingsSession();
		TrainingsSession1.setName("asdf");
		TrainingsSession TrainingsSession2 = dummyTrainingsSession();
		TrainingsSession2.setName("xyz");

		Assert.assertFalse(getDAO().findAll().contains(TrainingsSession1));
		Assert.assertFalse(getDAO().findAll().contains(TrainingsSession2));
		getDAO().create(TrainingsSession1);
		getDAO().create(TrainingsSession2);
		Assert.assertTrue(getDAO().findAll().contains(TrainingsSession1));
		Assert.assertTrue(getDAO().findAll().contains(TrainingsSession2));

		List<TrainingsSession> list = ((TrainingsSessionDAO) getDAO()).find(new TrainingsSession(null, null, null, "asdf", null, null));
		Assert.assertTrue(list.contains(TrainingsSession1));
		Assert.assertFalse(list.contains(TrainingsSession2));
	}

	private Trainingsplan createRefTraining() throws PersistenceException {
		Trainingsplan plan = new Trainingsplan();

		plan.setName("testtraining");
		plan.setDescr("testdescription");
		plan.setIsDeleted(false);

		return getTrainingsplanDAO().create(plan);
	}

	private TrainingsSession dummyTrainingsSession() throws PersistenceException {
		Trainingsplan plan = createRefTraining();

		TrainingsSession TrainingsSession = new TrainingsSession();

		TrainingsSession.setTrainingsplan(plan);
		TrainingsSession.setName("testsession");
		TrainingsSession.setIsDeleted(false);

		return TrainingsSession;
	}
}
