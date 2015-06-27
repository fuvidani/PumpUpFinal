package sepm.ss15.grp16.service.user;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.mockito.Mockito.when;
import sepm.ss15.grp16.entity.user.BodyfatHistory;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.dao.user.BodyfatHistoryDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.AbstractServiceTestMockito;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.user.impl.BodyfatHistoryServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class provides methods for testing BodyFatHistoryServices
 *
 * @author Michael Sober
 * @version 1.0
 */
public abstract class AbstractBodyfatHistoryServiceTest extends AbstractServiceTestMockito<BodyfatHistory> {

    protected BodyfatHistoryService bodyfatHistoryService;
    protected BodyfatHistoryDAO     mockedBodyfatHistoryDAO;

    @Override
    public Service<BodyfatHistory> getService() {
        return bodyfatHistoryService;
    }

    @Override
    public DAO<BodyfatHistory> getMockedDAO() {
        return mockedBodyfatHistoryDAO;
    }

    @Test(expected = ServiceException.class)
    public void newBodyfatHistoryServiceWithNull() throws Exception {
        new BodyfatHistoryServiceImpl(null);
    }

    @Test
    public void createWithValidBodyfatHistory() throws Exception {
        BodyfatHistory testBodyfatHistory = new BodyfatHistory(null, 1, 23, new Date());
        createTest(testBodyfatHistory);
    }

    @Test(expected = ServiceException.class)
    public void createWithPersistenceException() throws Exception {
        BodyfatHistory testBodyfatHistory = new BodyfatHistory(null, 1, 23, new Date());
        createTestFail(testBodyfatHistory);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void updateNotSupported() throws Exception {
        bodyfatHistoryService.update(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void deleteNotSupported() throws Exception {
        bodyfatHistoryService.delete(null);
    }

    @Test
    public void searchWithValidUserID() throws Exception {
        BodyfatHistory testBodyfatHistory1 = new BodyfatHistory(null, 1, 25, new Date());
        BodyfatHistory testBodyfatHistory2 = new BodyfatHistory(null, 1, 21, new Date());
        BodyfatHistory testBodyfatHistory3 = new BodyfatHistory(null, 1, 30, new Date());
        List<BodyfatHistory> bodyfatHistoryList = new ArrayList<>();
        bodyfatHistoryList.add(testBodyfatHistory1);
        bodyfatHistoryList.add(testBodyfatHistory2);
        bodyfatHistoryList.add(testBodyfatHistory3);

        when(mockedBodyfatHistoryDAO.searchByUserID(1)).thenReturn(bodyfatHistoryList);
        assertEquals(bodyfatHistoryService.searchByUserID(1), bodyfatHistoryList);
    }

    @Test(expected = ServiceException.class)
    public void searchByUserIDWithException() throws Exception {
        when(mockedBodyfatHistoryDAO.searchByUserID(1)).thenThrow(PersistenceException.class);
        bodyfatHistoryService.searchByUserID(1);
    }

    @Test
    public void getActualWeightWithValidId() throws Exception {
        BodyfatHistory testBodyfatHistory = new BodyfatHistory(null, 1, 23, new Date());

        when(mockedBodyfatHistoryDAO.getActualBodyfat(1)).thenReturn(testBodyfatHistory);
        assertEquals(bodyfatHistoryService.getActualBodyfat(1), testBodyfatHistory);
    }

    @Test(expected = ServiceException.class)
    public void getActualWeightWithPersistenceException() throws Exception {
        when(mockedBodyfatHistoryDAO.getActualBodyfat(1)).thenThrow(PersistenceException.class);
        bodyfatHistoryService.getActualBodyfat(1);
    }

    @Test
    public void findAllShouldReturnAllBodyfatHistories() throws Exception {
        BodyfatHistory testBodyfatHistory1 = new BodyfatHistory(null, 1, 25, new Date());
        BodyfatHistory testBodyfatHistory2 = new BodyfatHistory(null, 1, 21, new Date());
        BodyfatHistory testBodyfatHistory3 = new BodyfatHistory(null, 1, 30, new Date());
        List<BodyfatHistory> bodyfatHistoryList = new ArrayList<>();
        bodyfatHistoryList.add(testBodyfatHistory1);
        bodyfatHistoryList.add(testBodyfatHistory2);
        bodyfatHistoryList.add(testBodyfatHistory3);
        findAllTest(bodyfatHistoryList);
    }

    @Test(expected = ServiceException.class)
    public void findAllWithPersistenceException() throws Exception {
        findAllTestFail();
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidBodyfatHistory() throws Exception {
        BodyfatHistory bodyfatHistory = null;
        bodyfatHistoryService.validate(bodyfatHistory);
    }

    @Test(expected = ValidationException.class)
    public void validateWithToHightBodyfat() throws Exception {
        BodyfatHistory testBodyfatHistory = new BodyfatHistory(null, 1, 101, new Date());
        bodyfatHistoryService.validate(testBodyfatHistory);
    }

    @Test(expected = ValidationException.class)
    public void validateWithToLowBodyfat() throws Exception {
        BodyfatHistory testBodyfatHistory = new BodyfatHistory(null, 1, -101, new Date());
        bodyfatHistoryService.validate(testBodyfatHistory);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidUserID() throws Exception {
        BodyfatHistory testBodyfatHistory = new BodyfatHistory(null, null, 23, new Date());
        bodyfatHistoryService.validate(testBodyfatHistory);
    }

}
