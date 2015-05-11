package PersitanceTest.TrainingTest;

import PersitanceTest.AbstractDAOTest;
import org.junit.Assert;
import org.junit.Test;
import sepm.ss15.grp16.entity.Training.ExerciseSet;
import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 09.05.2015
 */
public abstract class AbstractTrainingsPlanDAOTest extends AbstractDAOTest<Trainingsplan> {

	@Test
	public void createValid() throws PersistenceException {
		createValid(dummyTrainingsPlan());
	}

	@Test
	public void updateValid() throws PersistenceException {
		Trainingsplan trainingsplan_old = dummyTrainingsPlan();
		Trainingsplan trainingsplan_new = new Trainingsplan(trainingsplan_old);
		trainingsplan_new.setName("changed name");

		updateValid(trainingsplan_old, trainingsplan_new);
	}

	@Test
	public void deleteValid() throws PersistenceException {
		deleteValid(dummyTrainingsPlan());
	}

	@Test
	public void searchByIDValid() throws PersistenceException{
		searchByIDValid(dummyTrainingsPlan());
	}

	@Test
	public void findValid() throws PersistenceException {
		Trainingsplan trainingsplan1 = dummyTrainingsPlan();
		trainingsplan1.setName("asdf");
		Trainingsplan trainingsplan2 = dummyTrainingsPlan();
		trainingsplan2.setName("xyz");

		Assert.assertFalse(getDAO().findAll().contains(trainingsplan1));
		Assert.assertFalse(getDAO().findAll().contains(trainingsplan2));
		getDAO().create(trainingsplan1);
		getDAO().create(trainingsplan2);
		Assert.assertTrue(getDAO().findAll().contains(trainingsplan1));
		Assert.assertTrue(getDAO().findAll().contains(trainingsplan2));

		List<Trainingsplan> list = ((TrainingsplanDAO)getDAO()).find(new Trainingsplan(null, null, "asdf", null, null, null, null));
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
