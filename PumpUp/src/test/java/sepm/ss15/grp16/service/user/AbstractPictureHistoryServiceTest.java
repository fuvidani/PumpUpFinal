package sepm.ss15.grp16.service.user;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.mockito.Mockito.when;
import sepm.ss15.grp16.entity.user.PictureHistory;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.dao.user.PictureHistoryDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.AbstractServiceTestMockito;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.user.impl.PictureHistoryServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class provides methods for testing PictureHistoryServices
 *
 * @author Michael Sober
 * @version 1.0
 */
public abstract class AbstractPictureHistoryServiceTest extends AbstractServiceTestMockito<PictureHistory> {

    protected PictureHistoryService pictureHistoryService;
    protected PictureHistoryDAO     mockedPictureHistoryDAO;

    @Override
    public Service<PictureHistory> getService() {
        return pictureHistoryService;
    }

    @Override
    public DAO<PictureHistory> getMockedDAO() {
        return mockedPictureHistoryDAO;
    }

    @Test(expected = ServiceException.class)
    public void newPictureHistoryServiceWithNull() throws Exception {
        new PictureHistoryServiceImpl(null);
    }

    @Test
    public void createWithValidPictureHistory() throws Exception {
        PictureHistory testPictureHistory = new PictureHistory(null, 1, "/testbild.jpg", new Date());
        createTest(testPictureHistory);
    }

    @Test(expected = ServiceException.class)
    public void createWithPersistenceException() throws Exception {
        PictureHistory testPictureHistory = new PictureHistory(null, 1, "/testbild.jpg", new Date());
        createTestFail(testPictureHistory);
    }

    @Test
    public void updateWithValidPictureHistory() throws Exception {
        PictureHistory testPictureHistory = new PictureHistory(null, 1, "/testbild.jpg", new Date());
        updateTest(testPictureHistory);
    }

    @Test(expected = ServiceException.class)
    public void updateWithPersistenceException() throws Exception {
        PictureHistory testPictureHistory = new PictureHistory(null, 1, "/testbild.jpg", new Date());
        updateTestFail(testPictureHistory);
    }

    @Test
    public void deleteWithValidPictureHistory() throws Exception {
        PictureHistory testPictureHistory = new PictureHistory(null, 1, "/testbild.jpg", new Date());
        deleteTest(testPictureHistory);
    }

    @Test(expected = ServiceException.class)
    public void deleteWithPersistenceException() throws Exception {
        PictureHistory testPictureHistory = new PictureHistory(null, 1, "/testbild.jpg", new Date());
        deleteTestFail(testPictureHistory);
    }

    @Test
    public void getActualPictureWithValidId() throws Exception {
        PictureHistory testPictureHistory = new PictureHistory(null, 1, "/testbild.jpg", new Date());

        when(mockedPictureHistoryDAO.getActualPicture(1)).thenReturn(testPictureHistory);
        assertEquals(pictureHistoryService.getActualPicture(1), testPictureHistory);
    }

    @Test(expected = ServiceException.class)
    public void getActualPictureWithPersistenceException() throws Exception {
        when(mockedPictureHistoryDAO.getActualPicture(1)).thenThrow(PersistenceException.class);
        pictureHistoryService.getActualPicture(1);
    }

    @Test
    public void findAllShouldReturnAllPictureHistories() throws Exception {
        PictureHistory testPictureHistory1 = new PictureHistory(1, 1, "/testbild.jpg", new Date());
        PictureHistory testPictureHistory2 = new PictureHistory(2, 1, "/testbild.jpg", new Date());
        PictureHistory testPictureHistory3 = new PictureHistory(3, 1, "/testbild.jpg", new Date());
        List<PictureHistory> pictureHistoryList = new ArrayList<>();
        pictureHistoryList.add(testPictureHistory1);
        pictureHistoryList.add(testPictureHistory2);
        pictureHistoryList.add(testPictureHistory3);
        findAllTest(pictureHistoryList);
    }

    @Test(expected = ServiceException.class)
    public void findAllWithPersistenceException() throws Exception {
        findAllTestFail();
    }

    @Test
    public void searchWithValidUserID() throws Exception {
        PictureHistory testPictureHistory1 = new PictureHistory(1, 1, "/testbild.jpg", new Date());
        PictureHistory testPictureHistory2 = new PictureHistory(2, 1, "/testbild.jpg", new Date());
        PictureHistory testPictureHistory3 = new PictureHistory(3, 1, "/testbild.jpg", new Date());
        List<PictureHistory> pictureHistoryList = new ArrayList<>();
        pictureHistoryList.add(testPictureHistory1);
        pictureHistoryList.add(testPictureHistory2);
        pictureHistoryList.add(testPictureHistory3);

        when(mockedPictureHistoryDAO.searchByUserID(1)).thenReturn(pictureHistoryList);
        assertEquals(pictureHistoryService.searchByUserID(1), pictureHistoryList);
    }

    @Test(expected = ServiceException.class)
    public void searchByUserIDWithException() throws Exception {
        when(mockedPictureHistoryDAO.searchByUserID(1)).thenThrow(PersistenceException.class);
        pictureHistoryService.searchByUserID(1);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidPictureHistory() throws Exception {
        PictureHistory pictureHistory = null;
        pictureHistoryService.validate(pictureHistory);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidUserID() throws Exception {
        String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();
        String testImagePath = pathToResource + "/testbild.jpg";

        PictureHistory testPictureHistory = new PictureHistory(null, null, testImagePath, new Date());
        pictureHistoryService.validate(testPictureHistory);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidImagePath() throws Exception {
        PictureHistory testPictureHistory = new PictureHistory(null, 1, "", new Date());
        pictureHistoryService.validate(testPictureHistory);
    }

}
