package sepm.ss15.grp16.persistence.dao.training.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.persistence.dao.training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lukas
 * Date: 13.05.2015
 */
public abstract class AbstractSessionHelperDAOTest {
	private static final Logger LOGGER = LogManager.getLogger(AbstractSessionHelperDAOTest.class);

	private List<TrainingsSession> debugglist;

	public abstract TrainingsplanDAO getTrainingsplanDAO();

	public abstract TrainingsSessionHelperDAO getDAO();

	@Test
	public void createValid() throws PersistenceException {
		TrainingsSession dto = dummyTrainingsSession();
		LOGGER.info("createValid: " + dto);
		debugglist = getDAO().findAll();
		Assert.assertFalse(getDAO().findAll().contains(dto));
		dto = getDAO().create(dto, dummyTrainingsPlan().getId());
		debugglist = getDAO().findAll();
		Assert.assertTrue(getDAO().findAll().contains(dto));
	}

	@Test
	public void updateValid() throws PersistenceException {
		TrainingsSession dto_old = dummyTrainingsSession();
		TrainingsSession dto_new = new TrainingsSession(dto_old);
		dto_new.setName("asd");

		LOGGER.info("updateValid: " + dto_old + "to " + dto_new);
		debugglist = getDAO().findAll();
		Assert.assertFalse(getDAO().findAll().contains(dto_old));

		dto_old = getDAO().create(dto_old, dummyTrainingsPlan().getId());
		dto_new.setId(dto_old.getId());

		debugglist = getDAO().findAll();
		Assert.assertTrue(getDAO().findAll().contains(dto_old));
		Assert.assertFalse(getDAO().findAll().contains(dto_new));

		dto_new = getDAO().update(dto_new, dummyTrainingsPlan().getId());
		debugglist = getDAO().findAll();
		Assert.assertFalse(getDAO().findAll().contains(dto_old));
		Assert.assertTrue(getDAO().findAll().contains(dto_new));
	}

	@Test
	public void deleteValid() throws PersistenceException {
		TrainingsSession dto = dummyTrainingsSession();

		LOGGER.info("deleteValid: " + dto);
		debugglist = getDAO().findAll();
		Assert.assertFalse(getDAO().findAll().contains(dto));
		dto = getDAO().create(dto, dummyTrainingsPlan().getId());
		debugglist = getDAO().findAll();
		Assert.assertTrue(getDAO().findAll().contains(dto));

		getDAO().delete(dto);
		debugglist = getDAO().findAll();
		Assert.assertFalse(getDAO().findAll().contains(dto));
	}

	@Test
	public void searchByIDValid() throws PersistenceException {
		TrainingsSession dto = dummyTrainingsSession();

		LOGGER.info("deleteValid: " + dto);
		debugglist = getDAO().findAll();
		Assert.assertFalse(getDAO().findAll().contains(dto));
		dto = getDAO().create(dto, dummyTrainingsPlan().getId());
		debugglist = getDAO().findAll();
		Assert.assertTrue(getDAO().findAll().contains(dto));

		TrainingsSession dto_found = getDAO().searchByID(dto.getId());
		debugglist = getDAO().findAll();
		Assert.assertEquals(dto_found, dto);
	}

	@Test
	public void searchByPlanIDValid() throws PersistenceException{
		TrainingsSession dto = dummyTrainingsSession();
		Trainingsplan plan = dummyTrainingsPlan();

		LOGGER.info("deleteValid: " + dto);
		debugglist = getDAO().findAll();
		Assert.assertFalse(getDAO().findAll().contains(dto));
		dto = getDAO().create(dto, plan.getId());
		debugglist = getDAO().findAll();
		Assert.assertTrue(getDAO().findAll().contains(dto));

		List<TrainingsSession> list = getDAO().searchByPlanID(plan.getId());
		debugglist = getDAO().findAll();
		Assert.assertTrue(list.contains(dto));
	}

	@Test
	public void getPlanBySessionValid() throws PersistenceException{
		TrainingsSession dto = dummyTrainingsSession();
		Trainingsplan plan = dummyTrainingsPlan();
		List<TrainingsSession> sessions = new ArrayList<>();
		sessions.add(dto);
		plan.setTrainingsSessions(sessions);

		LOGGER.info("deleteValid: " + dto);
		debugglist = getDAO().findAll();
		Assert.assertFalse(getDAO().findAll().contains(dto));
		dto = getDAO().create(dto, plan.getId());
		debugglist = getDAO().findAll();
		Assert.assertTrue(getDAO().findAll().contains(dto));

		Trainingsplan plan1 = getDAO().getPlanBySession(dto);
		debugglist = getDAO().findAll();
		Assert.assertEquals(plan1, plan);
	}

	private TrainingsSession dummyTrainingsSession() throws PersistenceException {
		TrainingsSession trainingsSession = new TrainingsSession();

		trainingsSession.setName("testsession");
		trainingsSession.setIsDeleted(false);

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
