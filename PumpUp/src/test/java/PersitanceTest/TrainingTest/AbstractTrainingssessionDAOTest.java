package PersitanceTest.TrainingTest;

import org.junit.Assert;
import org.junit.Test;
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
public abstract class AbstractTrainingssessionDAOTest {

	public abstract TrainingsSessionDAO getTrainingsSessionDAO();

	public abstract TrainingsplanDAO getTrainingsplanDAO();

	@Test
	public void createValid() throws PersistenceException {
		TrainingsSession trainingsSession = dummyTrainingsSession();

		Assert.assertFalse(getTrainingsSessionDAO().findAll().contains(trainingsSession));
		getTrainingsSessionDAO().create(trainingsSession);
		List<TrainingsSession> list = getTrainingsSessionDAO().findAll();
		Assert.assertTrue(getTrainingsSessionDAO().findAll().contains(trainingsSession));
	}

	@Test
	public void updateValid() throws PersistenceException {
		TrainingsSession trainingsSession_old = dummyTrainingsSession();

		Assert.assertFalse(getTrainingsSessionDAO().findAll().contains(trainingsSession_old));

		trainingsSession_old = getTrainingsSessionDAO().create(trainingsSession_old);
		TrainingsSession TrainingsSession_new = new TrainingsSession(trainingsSession_old);
		TrainingsSession_new.setName("changed name");

		Assert.assertTrue(getTrainingsSessionDAO().findAll().contains(trainingsSession_old));
		Assert.assertFalse(getTrainingsSessionDAO().findAll().contains(TrainingsSession_new));

		TrainingsSession_new = getTrainingsSessionDAO().update(TrainingsSession_new);
		Assert.assertFalse(getTrainingsSessionDAO().findAll().contains(trainingsSession_old));
		Assert.assertTrue(getTrainingsSessionDAO().findAll().contains(TrainingsSession_new));
	}

	@Test
	public void deleteValid() throws PersistenceException {
		TrainingsSession TrainingsSession = dummyTrainingsSession();

		Assert.assertFalse(getTrainingsSessionDAO().findAll().contains(TrainingsSession));
		getTrainingsSessionDAO().create(TrainingsSession);
		Assert.assertTrue(getTrainingsSessionDAO().findAll().contains(TrainingsSession));

		getTrainingsSessionDAO().delete(TrainingsSession);
		Assert.assertFalse(getTrainingsSessionDAO().findAll().contains(TrainingsSession));
	}

	@Test
	public void searchByIDValid() throws PersistenceException {
		TrainingsSession TrainingsSession = dummyTrainingsSession();

		Assert.assertFalse(getTrainingsSessionDAO().findAll().contains(TrainingsSession));
		getTrainingsSessionDAO().create(TrainingsSession);
		Assert.assertTrue(getTrainingsSessionDAO().findAll().contains(TrainingsSession));

		TrainingsSession searched = getTrainingsSessionDAO().searchByID(TrainingsSession.getId());
		Assert.assertEquals(TrainingsSession, searched);
	}

	@Test
	public void findValid() throws PersistenceException {
		TrainingsSession TrainingsSession1 = dummyTrainingsSession();
		TrainingsSession1.setName("asdf");
		TrainingsSession TrainingsSession2 = dummyTrainingsSession();
		TrainingsSession2.setName("xyz");

		Assert.assertFalse(getTrainingsSessionDAO().findAll().contains(TrainingsSession1));
		Assert.assertFalse(getTrainingsSessionDAO().findAll().contains(TrainingsSession2));
		getTrainingsSessionDAO().create(TrainingsSession1);
		getTrainingsSessionDAO().create(TrainingsSession2);
		Assert.assertTrue(getTrainingsSessionDAO().findAll().contains(TrainingsSession1));
		Assert.assertTrue(getTrainingsSessionDAO().findAll().contains(TrainingsSession2));

		List<TrainingsSession> list = getTrainingsSessionDAO().find(new TrainingsSession(null, null, null, "asdf", null, null));
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
