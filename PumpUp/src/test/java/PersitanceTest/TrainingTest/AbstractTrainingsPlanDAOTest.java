package PersitanceTest.TrainingTest;

import org.junit.Assert;
import org.junit.Test;
import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 09.05.2015
 */
public abstract class AbstractTrainingsPlanDAOTest {

	public abstract TrainingsplanDAO getTrainingsplanDAO();

	@Test
	public void createValid() throws PersistenceException {
		Trainingsplan trainingsplan = dummyTrainingsPlan();

		Assert.assertFalse(getTrainingsplanDAO().findAll().contains(trainingsplan));
		getTrainingsplanDAO().create(trainingsplan);
		Assert.assertTrue(getTrainingsplanDAO().findAll().contains(trainingsplan));
	}

	@Test
	public void updateValid() throws PersistenceException {
		Trainingsplan trainingsplan_old = dummyTrainingsPlan();

		Assert.assertFalse(getTrainingsplanDAO().findAll().contains(trainingsplan_old));

		trainingsplan_old = getTrainingsplanDAO().create(trainingsplan_old);
		Trainingsplan trainingsplan_new = new Trainingsplan(trainingsplan_old);
		trainingsplan_new.setName("changed name");

		Assert.assertTrue(getTrainingsplanDAO().findAll().contains(trainingsplan_old));
		Assert.assertFalse(getTrainingsplanDAO().findAll().contains(trainingsplan_new));

		trainingsplan_new = getTrainingsplanDAO().update(trainingsplan_new);
		Assert.assertFalse(getTrainingsplanDAO().findAll().contains(trainingsplan_old));
		Assert.assertTrue(getTrainingsplanDAO().findAll().contains(trainingsplan_new));
	}

	@Test
	public void deleteValid() throws PersistenceException {
		Trainingsplan trainingsplan = dummyTrainingsPlan();

		Assert.assertFalse(getTrainingsplanDAO().findAll().contains(trainingsplan));
		getTrainingsplanDAO().create(trainingsplan);
		Assert.assertTrue(getTrainingsplanDAO().findAll().contains(trainingsplan));

		getTrainingsplanDAO().delete(trainingsplan);
		Assert.assertFalse(getTrainingsplanDAO().findAll().contains(trainingsplan));
	}

	@Test
	public void searchByIDValid() throws PersistenceException{
		Trainingsplan trainingsplan = dummyTrainingsPlan();

		Assert.assertFalse(getTrainingsplanDAO().findAll().contains(trainingsplan));
		getTrainingsplanDAO().create(trainingsplan);
		Assert.assertTrue(getTrainingsplanDAO().findAll().contains(trainingsplan));

		Trainingsplan searched = getTrainingsplanDAO().searchByID(trainingsplan.getId());
		Assert.assertEquals(trainingsplan, searched);
	}

	@Test
	public void findValid() throws PersistenceException {
		Trainingsplan trainingsplan1 = dummyTrainingsPlan();
		trainingsplan1.setName("asdf");
		Trainingsplan trainingsplan2 = dummyTrainingsPlan();
		trainingsplan2.setName("xyz");

		Assert.assertFalse(getTrainingsplanDAO().findAll().contains(trainingsplan1));
		Assert.assertFalse(getTrainingsplanDAO().findAll().contains(trainingsplan2));
		getTrainingsplanDAO().create(trainingsplan1);
		getTrainingsplanDAO().create(trainingsplan2);
		Assert.assertTrue(getTrainingsplanDAO().findAll().contains(trainingsplan1));
		Assert.assertTrue(getTrainingsplanDAO().findAll().contains(trainingsplan2));

		List<Trainingsplan> list = getTrainingsplanDAO().find(new Trainingsplan(null, null, "asdf", null, null, null, null));
		Assert.assertTrue(list.contains(trainingsplan1));
		Assert.assertFalse(list.contains(trainingsplan2));
	}

	private Trainingsplan dummyTrainingsPlan() {
		Trainingsplan trainingsplan = new Trainingsplan();

		trainingsplan.setName("testtraining");
		trainingsplan.setDescr("testdescription");
		trainingsplan.setIsDeleted(false);

		return trainingsplan;
	}

}
