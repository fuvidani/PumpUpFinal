package sepm.ss15.grp16.service.user;

import org.junit.Test;
import sepm.ss15.grp16.entity.user.WeightHistory;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.dao.user.WeightHistoryDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.AbstractServiceTestMockito;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.user.impl.WeightHistoryServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * This class provides methods for testing WeightHistoryServices
 *
 * @author Michael Sober
 * @version 1.0
 */
public abstract class AbstractWeightHistoryServiceTest extends AbstractServiceTestMockito<WeightHistory> {

    protected WeightHistoryService weightHistoryService;
    protected WeightHistoryDAO mockedWeightHistoryDAO;

    @Override
    public Service<WeightHistory> getService() {
        return weightHistoryService;
    }

    @Override
    public DAO<WeightHistory> getMockedDAO() {
        return mockedWeightHistoryDAO;
    }

    @Test(expected = ServiceException.class)
    public void newWeightHistoryServiceWithNull() throws Exception {
        new WeightHistoryServiceImpl(null);
    }

    @Test
    public void createWithValidWeightHistory() throws Exception {
        WeightHistory testWeightHistory = new WeightHistory(null, 1, 85, new Date());
        createTest(testWeightHistory);
    }

    @Test(expected = ServiceException.class)
    public void createWithPersistenceException() throws Exception {
        WeightHistory testWeightHistory = new WeightHistory(null, 1, 85, new Date());
        createTestFail(testWeightHistory);
    }

    @Test
    public void findAllShouldReturnAllWeightHistories() throws Exception {
        WeightHistory testWeightHistory1 = new WeightHistory(1, 1, 85, new Date());
        WeightHistory testWeightHistory2 = new WeightHistory(2, 2, 85, new Date());
        WeightHistory testWeightHistory3 = new WeightHistory(3, 3, 85, new Date());
        List<WeightHistory> weightHistoryList = new ArrayList<>();
        weightHistoryList.add(testWeightHistory1);
        weightHistoryList.add(testWeightHistory2);
        weightHistoryList.add(testWeightHistory3);
        findAllTest(weightHistoryList);
    }

    @Test(expected = ServiceException.class)
    public void findAllWithPersistenceException() throws Exception {
        findAllTestFail();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void updateNotSupported() throws Exception {
        weightHistoryService.update(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void deleteNotSupported() throws Exception {
        weightHistoryService.delete(null);
    }

    @Test
    public void searchWithValidUserID() throws Exception {
        WeightHistory testWeightHistory1 = new WeightHistory(1, 1, 85, new Date());
        WeightHistory testWeightHistory2 = new WeightHistory(2, 1, 85, new Date());
        WeightHistory testWeightHistory3 = new WeightHistory(3, 1, 85, new Date());
        List<WeightHistory> weightHistoryList = new ArrayList<>();
        weightHistoryList.add(testWeightHistory1);
        weightHistoryList.add(testWeightHistory2);
        weightHistoryList.add(testWeightHistory3);

        when(mockedWeightHistoryDAO.searchByUserID(1)).thenReturn(weightHistoryList);
        assertEquals(weightHistoryService.searchByUserID(1), weightHistoryList);
    }

    @Test(expected = ServiceException.class)
    public void searchByUserIDWithException() throws Exception {
        when(mockedWeightHistoryDAO.searchByUserID(1)).thenThrow(PersistenceException.class);
        weightHistoryService.searchByUserID(1);
    }

    @Test
    public void getActualWeightWithValidId() throws Exception {
        WeightHistory testWeightHistory = new WeightHistory(1, 1, 85, new Date());

        when(mockedWeightHistoryDAO.getActualWeight(1)).thenReturn(testWeightHistory);
        assertEquals(weightHistoryService.getActualWeight(1), testWeightHistory);
    }

    @Test(expected = ServiceException.class)
    public void getActualWeightWithPersistenceException() throws Exception {
        when(mockedWeightHistoryDAO.getActualWeight(1)).thenThrow(PersistenceException.class);
        weightHistoryService.getActualWeight(1);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidWeightHistory() throws Exception {
        WeightHistory weightHistory = null;
        weightHistoryService.validate(weightHistory);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidWeight() throws Exception {
        WeightHistory testWeightHistory = new WeightHistory(null, 1, -85, new Date());
        weightHistoryService.validate(testWeightHistory);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidUserID() throws Exception {
        WeightHistory testWeightHistory = new WeightHistory(null, null, -85, new Date());
        weightHistoryService.validate(testWeightHistory);
    }

}
