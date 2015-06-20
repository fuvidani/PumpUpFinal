package sepm.ss15.grp16.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.DTO;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * This class provides methods for testing Services
 *
 * @author Michael Sober
 * @version 1.0
 */
public abstract class AbstractServiceTestMockito<T extends DTO> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractServiceTestMockito.class);

    public abstract Service<T> getService();

    public abstract DAO<T> getMockedDAO();

    protected void createTest(T dto) throws Exception {
        LOGGER.info("createValid: " + dto);
        when(getMockedDAO().create(dto)).thenReturn(dto);
        assertEquals(getService().create(dto), dto);
    }

    protected void createTestFail(T dto) throws Exception {
        LOGGER.info("createWithExceptionFromDao: " + dto);
        when(getMockedDAO().create(dto)).thenThrow(PersistenceException.class);
        getService().create(dto);
    }

    protected void updateTest(T dto) throws Exception {
        LOGGER.info("updateValid: " + dto);
        when(getMockedDAO().update(dto)).thenReturn(dto);
        assertEquals(getService().update(dto), dto);
    }

    protected void updateTestFail(T dto) throws Exception {
        LOGGER.info("updateWithExceptionFromDao: " + dto);
        when(getMockedDAO().update(dto)).thenThrow(PersistenceException.class);
        getService().update(dto);
    }

    protected void deleteTest(T dto) throws Exception {
        LOGGER.info("deleteValid: " + dto);
        getService().delete(dto);
        verify(getMockedDAO()).delete(dto);
    }

    protected void deleteTestFail(T dto) throws Exception {
        LOGGER.info("deleteWithExceptionFromDao: " + dto);
        doThrow(PersistenceException.class).when(getMockedDAO()).delete(dto);
        getService().delete(dto);
    }

    protected void findAllTest(List<T> dtoList) throws Exception {
        LOGGER.info("findAll: " + dtoList);
        when(getMockedDAO().findAll()).thenReturn(dtoList);
        assertEquals(getService().findAll(), dtoList);
    }

    protected void findAllTestFail() throws Exception {
        LOGGER.info("findAllWithException");
        when(getMockedDAO().findAll()).thenThrow(PersistenceException.class);
        getService().findAll();
    }

}
