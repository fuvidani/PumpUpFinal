package PersitanceTest;

import org.junit.Assert;
import sepm.ss15.grp16.entity.DTO;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

/**
 * Author: Lukas
 * Date: 11.05.2015
 */
public abstract class AbstractDAOTest<T extends DTO> {

	public abstract DAO<T> getDAO();

	public void createValid(T dto) throws PersistenceException {
		Assert.assertFalse(getDAO().findAll().contains(dto));
		getDAO().create(dto);
		Assert.assertTrue(getDAO().findAll().contains(dto));
	}

	public void updateValid(T dto_old, T dto_new) throws PersistenceException {
		Assert.assertFalse(getDAO().findAll().contains(dto_old));

		dto_old = getDAO().create(dto_old);
		dto_new.setId(dto_old.getId());

		Assert.assertTrue(getDAO().findAll().contains(dto_old));
		Assert.assertFalse(getDAO().findAll().contains(dto_new));

		dto_new = getDAO().update(dto_new);
		Assert.assertFalse(getDAO().findAll().contains(dto_old));
		Assert.assertTrue(getDAO().findAll().contains(dto_new));
	}

	public void deleteValid(T dto) throws PersistenceException {
		Assert.assertFalse(getDAO().findAll().contains(dto));
		getDAO().create(dto);
		Assert.assertTrue(getDAO().findAll().contains(dto));

		getDAO().delete(dto);
		Assert.assertFalse(getDAO().findAll().contains(dto));
	}

	public void searchByIDValid(T dto) throws PersistenceException {
		Assert.assertFalse(getDAO().findAll().contains(dto));
		getDAO().create(dto);
		Assert.assertTrue(getDAO().findAll().contains(dto));

		T dto_found = getDAO().searchByID(dto.getId());
		Assert.assertEquals(dto_found, dto);
	}
}
