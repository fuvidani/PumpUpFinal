package sepm.ss15.grp16.persistence.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import sepm.ss15.grp16.entity.DTO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 11.05.2015
 */
public abstract class AbstractDAOTest<T extends DTO> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractDAOTest.class);
    private List<T> debugglist;

    public abstract DAO<T> getDAO();

    /**
     * create the dto
     * checks first, if the find dto dont exists before and exist after the create methode
     * @param dto
     * @throws PersistenceException
     */
    public void createValid(T dto) throws PersistenceException {
        LOGGER.info("createValid: " + dto);
        debugglist = getDAO().findAll();
        Assert.assertFalse(getDAO().findAll().contains(dto));
        dto = getDAO().create(dto);
        debugglist = getDAO().findAll();
        Assert.assertTrue(getDAO().findAll().contains(dto));
    }

    /**
     * creates the dto_old, and set the id to the dto_new
     * then calls the DAO update method and checks, if the dto_old no longer exists, but the dto_new
     * @param dto_old
     * @param dto_new
     * @throws PersistenceException
     */
    public void updateValid(T dto_old, T dto_new) throws PersistenceException {
        LOGGER.info("updateValid: " + dto_old + "to " + dto_new);
        debugglist = getDAO().findAll();
        Assert.assertFalse(getDAO().findAll().contains(dto_old));

        dto_old = getDAO().create(dto_old);
        dto_new.setId(dto_old.getId());

        debugglist = getDAO().findAll();
        Assert.assertTrue(getDAO().findAll().contains(dto_old));
        Assert.assertFalse(getDAO().findAll().contains(dto_new));

        dto_new = getDAO().update(dto_new);
        debugglist = getDAO().findAll();
        Assert.assertFalse(getDAO().findAll().contains(dto_old));
        Assert.assertTrue(getDAO().findAll().contains(dto_new));
    }

    /**
     * create the dto and the deletes it
     * checks, if the dto first exists not, the exists and the exists no longer
     * @param dto
     * @throws PersistenceException
     */
    public void deleteValid(T dto) throws PersistenceException {
        LOGGER.info("deleteValid: " + dto);
        debugglist = getDAO().findAll();
        Assert.assertFalse(getDAO().findAll().contains(dto));
        dto = getDAO().create(dto);
        debugglist = getDAO().findAll();
        Assert.assertTrue(getDAO().findAll().contains(dto));

        getDAO().delete(dto);
        debugglist = getDAO().findAll();
        Assert.assertFalse(getDAO().findAll().contains(dto));
    }

    /**
     * creates the dto and the search for it
     * @param dto
     * @throws PersistenceException
     */
    public void searchByIDValid(T dto) throws PersistenceException {
        LOGGER.info("deleteValid: " + dto);
        debugglist = getDAO().findAll();
        Assert.assertFalse(getDAO().findAll().contains(dto));
        dto = getDAO().create(dto);
        debugglist = getDAO().findAll();
        Assert.assertTrue(getDAO().findAll().contains(dto));

        T dto_found = getDAO().searchByID(dto.getId());
        debugglist = getDAO().findAll();
        LOGGER.info(dto);
        LOGGER.info(dto_found);
        Assert.assertEquals(dto_found, dto);
    }
}
