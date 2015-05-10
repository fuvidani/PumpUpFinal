package PersitanceTest.TrainingTest;

import org.junit.Assert;
import org.junit.Test;
import sepm.ss15.grp16.entity.Training.TrainingsplanType;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsplanTypeDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 10.05.2015
 */
public abstract class AbstractTrainingsTypeDAOTest {

	public abstract TrainingsplanTypeDAO getTrainingsplanTypeDAO();

	@Test
	public void createValid() throws PersistenceException {
		TrainingsplanType TrainingsplanType = dummyTrainingsplanType();

		Assert.assertFalse(getTrainingsplanTypeDAO().findAll().contains(TrainingsplanType));
		getTrainingsplanTypeDAO().create(TrainingsplanType);
		Assert.assertTrue(getTrainingsplanTypeDAO().findAll().contains(TrainingsplanType));
	}

	@Test
	public void updateValid() throws PersistenceException {
		TrainingsplanType TrainingsplanType_old = dummyTrainingsplanType();

		Assert.assertFalse(getTrainingsplanTypeDAO().findAll().contains(TrainingsplanType_old));

		TrainingsplanType_old = getTrainingsplanTypeDAO().create(TrainingsplanType_old);
		TrainingsplanType TrainingsplanType_new = new TrainingsplanType(TrainingsplanType_old);
		TrainingsplanType_new.setName("changed name");

		Assert.assertTrue(getTrainingsplanTypeDAO().findAll().contains(TrainingsplanType_old));
		Assert.assertFalse(getTrainingsplanTypeDAO().findAll().contains(TrainingsplanType_new));

		TrainingsplanType_new = getTrainingsplanTypeDAO().update(TrainingsplanType_new);
		Assert.assertFalse(getTrainingsplanTypeDAO().findAll().contains(TrainingsplanType_old));
		Assert.assertTrue(getTrainingsplanTypeDAO().findAll().contains(TrainingsplanType_new));
	}

	@Test
	public void deleteValid() throws PersistenceException {
		TrainingsplanType TrainingsplanType = dummyTrainingsplanType();

		Assert.assertFalse(getTrainingsplanTypeDAO().findAll().contains(TrainingsplanType));
		getTrainingsplanTypeDAO().create(TrainingsplanType);
		Assert.assertTrue(getTrainingsplanTypeDAO().findAll().contains(TrainingsplanType));

		getTrainingsplanTypeDAO().delete(TrainingsplanType);
		Assert.assertFalse(getTrainingsplanTypeDAO().findAll().contains(TrainingsplanType));
	}

	@Test
	public void searchByIDValid() throws PersistenceException {
		TrainingsplanType TrainingsplanType = dummyTrainingsplanType();

		Assert.assertFalse(getTrainingsplanTypeDAO().findAll().contains(TrainingsplanType));
		getTrainingsplanTypeDAO().create(TrainingsplanType);
		Assert.assertTrue(getTrainingsplanTypeDAO().findAll().contains(TrainingsplanType));

		TrainingsplanType searched = getTrainingsplanTypeDAO().searchByID(TrainingsplanType.getId());
		Assert.assertEquals(TrainingsplanType, searched);
	}

	@Test
	public void findValid() throws PersistenceException {
		TrainingsplanType TrainingsplanType1 = dummyTrainingsplanType();
		TrainingsplanType1.setName("asdf");
		TrainingsplanType TrainingsplanType2 = dummyTrainingsplanType();
		TrainingsplanType2.setName("xyz");

		Assert.assertFalse(getTrainingsplanTypeDAO().findAll().contains(TrainingsplanType1));
		Assert.assertFalse(getTrainingsplanTypeDAO().findAll().contains(TrainingsplanType2));
		getTrainingsplanTypeDAO().create(TrainingsplanType1);
		getTrainingsplanTypeDAO().create(TrainingsplanType2);
		Assert.assertTrue(getTrainingsplanTypeDAO().findAll().contains(TrainingsplanType1));
		Assert.assertTrue(getTrainingsplanTypeDAO().findAll().contains(TrainingsplanType2));

		List<TrainingsplanType> list = getTrainingsplanTypeDAO().find(new TrainingsplanType(null, null, "asdf", null, null, null));
		Assert.assertTrue(list.contains(TrainingsplanType1));
		Assert.assertFalse(list.contains(TrainingsplanType2));
	}

	private TrainingsplanType dummyTrainingsplanType() {
		TrainingsplanType TrainingsplanType = new TrainingsplanType();

		TrainingsplanType.setName("testtraining");
		TrainingsplanType.setDescr("testdescription");
		TrainingsplanType.setIsDeleted(false);

		return TrainingsplanType;
	}


}
