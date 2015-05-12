package PersitanceTest.TrainingTest;

import org.junit.Assert;
import org.junit.Test;
import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsSessionDAO;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.dao.UserDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Author: Lukas
 * Date: 09.05.2015
 */
public abstract class AbstractTrainingssessionDAOTest {

	public abstract TrainingsplanDAO getTrainingsplanDAO();
	public abstract TrainingsSessionDAO getTrainingsSessionDAO();
	public abstract UserDAO getUserDAO();

	@Test
	public void searchByIDValid() throws PersistenceException {
		Trainingsplan plan = dummyTrainingsplan();
		List<TrainingsSession> list = new ArrayList<>();
		TrainingsSession session = dummySession();
		list.add(session);
		plan.setTrainingsSessions(list);

		Assert.assertFalse(getTrainingsplanDAO().findAll().contains(plan));
		plan = getTrainingsplanDAO().create(plan);
		Assert.assertTrue(getTrainingsplanDAO().findAll().contains(plan));

		session = plan.getTrainingsSessions().get(0);

		TrainingsSession dto_found = getTrainingsSessionDAO().searchByID(session.getId());
		Assert.assertEquals(dto_found, session);
	}

	@Test
	public void searchByUserValid() throws PersistenceException {
		User testUser = new User(null, "msober", true, 20, 194, false);
		testUser = getUserDAO().create(testUser);

		Trainingsplan plan = dummyTrainingsplan();
		List<TrainingsSession> list = new ArrayList<>();
		TrainingsSession session = dummySession();
		session.setUser(testUser);
		list.add(session);
		plan.setTrainingsSessions(list);

		Assert.assertFalse(getTrainingsplanDAO().findAll().contains(plan));
		getTrainingsplanDAO().create(plan);
		Assert.assertTrue(getTrainingsplanDAO().findAll().contains(plan));

		session = plan.getTrainingsSessions().get(0);

		List<TrainingsSession> dto_found = getTrainingsSessionDAO().searchByUser(testUser);
		Assert.assertTrue(dto_found.contains(session));
	}

	private Trainingsplan dummyTrainingsplan() throws PersistenceException {
		Trainingsplan plan = new Trainingsplan();

		plan.setName("testtraining");
		plan.setDescr("testdescription");
		plan.setIsDeleted(false);

		return plan;
	}

	private TrainingsSession dummySession() throws PersistenceException {
		TrainingsSession trainingsSession = new TrainingsSession();

		trainingsSession.setName("testsession");
		trainingsSession.setIsDeleted(false);

		return trainingsSession;
	}
}
