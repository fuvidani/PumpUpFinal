package sepm.ss15.grp16.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import sepm.ss15.grp16.entity.DTO;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 13.05.2015
 */
public abstract class AbstractServiceTest<T extends DTO> {

	private static final Logger LOGGER = LogManager.getLogger(AbstractServiceTest.class);
	private List<T> debugglist;

	public abstract Service<T> getService();

	public void createTest(T dto) throws ServiceException {
		LOGGER.info("createValid: " + dto);
		debugglist = getService().findAll();
		Assert.assertFalse(getService().findAll().contains(dto));
		dto = getService().create(dto);
		debugglist = getService().findAll();
		Assert.assertTrue(getService().findAll().contains(dto));
	}

	public void updateTest(T dto_old, T dto_new) throws ServiceException {
		LOGGER.info("updateValid: " + dto_old + "to " + dto_new);
		debugglist = getService().findAll();
		Assert.assertFalse(getService().findAll().contains(dto_old));

		dto_old = getService().create(dto_old);
		dto_new.setId(dto_old.getId());

		debugglist = getService().findAll();
		Assert.assertTrue(getService().findAll().contains(dto_old));
		Assert.assertFalse(getService().findAll().contains(dto_new));

		dto_new = getService().update(dto_new);
		debugglist = getService().findAll();
		Assert.assertFalse(getService().findAll().contains(dto_old));
		Assert.assertTrue(getService().findAll().contains(dto_new));
	}

	public void deleteTest(T dto) throws ServiceException {
		LOGGER.info("deleteValid: " + dto);
		debugglist = getService().findAll();
		Assert.assertFalse(getService().findAll().contains(dto));
		dto = getService().create(dto);
		debugglist = getService().findAll();
		Assert.assertTrue(getService().findAll().contains(dto));

		getService().delete(dto);
		debugglist = getService().findAll();
		Assert.assertFalse(getService().findAll().contains(dto));
	}

	public void validateNoException(T dto) throws ServiceException{
		getService().validate(dto);
	}

	public void validateWithException(T dto) throws ServiceException{
		dto.setIsDeleted(null);
		getService().validate(dto);
	}
}
