package sepm.ss15.grp16.persistence.dao.user;

import org.junit.Test;
import sepm.ss15.grp16.entity.user.PictureHistory;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.dao.AbstractDAOTest;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class provides methods for testing PictureHistoryDAOs
 *
 * @author Michael Sober
 * @version 1.0
 */
public abstract class AbstractPictureHistoryDaoTest extends AbstractDAOTest<PictureHistory> {

    protected PictureHistoryDAO pictureHistoryDAO;
    protected UserDAO userDAO;

    @Override
    public DAO<PictureHistory> getDAO() {
        return pictureHistoryDAO;
    }

    @Test(expected = PersistenceException.class)
    public void createWithNullShouldThrowException() throws Exception {
        pictureHistoryDAO.create(null);
    }

    @Test
    public void createWithValidPictureHistoryShouldPersist() throws Exception {
        String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();
        String testImagePath = pathToResource + "/testbild.jpg";

        PictureHistory testPictureHistory = new PictureHistory(null, createUserForTest().getUser_id(), testImagePath, new Date());
        createValid(testPictureHistory);

        String savedImagePath = pathToResource + testPictureHistory.getLocation();
        File savedImage = new File(savedImagePath);
        assertTrue(savedImage.exists());
        if (savedImage.exists()) {
            savedImage.delete();
        }
    }

    @Test(expected = PersistenceException.class)
    public void createWithFileNotFoundShouldFail() throws Exception {
        PictureHistory testPictureHistory = new PictureHistory(null, createUserForTest().getUser_id(), "failPath", new Date());
        createValid(testPictureHistory);
    }

    @Test
    public void searchWithValidUserID() throws Exception {
        String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();
        String testImagePath = pathToResource + "/testbild.jpg";

        User testUser = createUserForTest();

        PictureHistory testPictureHistory1 = new PictureHistory(null, testUser.getUser_id(), testImagePath, new Date());
        PictureHistory testPictureHistory2 = new PictureHistory(null, testUser.getUser_id(), testImagePath, new Date());
        PictureHistory testPictureHistory3 = new PictureHistory(null, testUser.getUser_id(), testImagePath, new Date());

        pictureHistoryDAO.create(testPictureHistory1);
        pictureHistoryDAO.create(testPictureHistory2);
        pictureHistoryDAO.create(testPictureHistory3);

        List<PictureHistory> pictureHistoryList = pictureHistoryDAO.searchByUserID(testUser.getUser_id());

        assertTrue(pictureHistoryList.contains(testPictureHistory1));
        assertTrue(pictureHistoryList.contains(testPictureHistory2));
        assertTrue(pictureHistoryList.contains(testPictureHistory3));

        for (int i = 0; i < pictureHistoryList.size(); i++) {
            String savedImagePath = pathToResource + pictureHistoryList.get(i).getLocation();
            File savedImage = new File(savedImagePath);
            assertTrue(savedImage.exists());
            if (savedImage.exists()) {
                savedImage.delete();
            }
        }
    }

    @Test
    public void searchByIDShouldFind() throws Exception {
        String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();
        String testImagePath = pathToResource + "/testbild.jpg";

        User testUser = createUserForTest();

        PictureHistory testPictureHistory = new PictureHistory(null, testUser.getUser_id(), testImagePath, new Date());
        searchByIDValid(testPictureHistory);

        String savedImagePath = pathToResource + testPictureHistory.getLocation();
        File savedImage = new File(savedImagePath);
        assertTrue(savedImage.exists());
        if (savedImage.exists()) {
            savedImage.delete();
        }
    }

    @Test
    public void deleteWithValidPictureHistoryShouldPersist() throws Exception {
        String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();
        String testImagePath = pathToResource + "/testbild.jpg";

        User testUser = createUserForTest();

        PictureHistory testPictureHistory = new PictureHistory(null, testUser.getUser_id(), testImagePath, new Date());
        deleteValid(testPictureHistory);

        String savedImagePath = pathToResource + testPictureHistory.getLocation();
        File savedImage = new File(savedImagePath);
        assertTrue(savedImage.exists());
        if (savedImage.exists()) {
            savedImage.delete();
        }
    }

    @Test
    public void updateWithValidPictureHistoryShouldPersist() throws Exception {
        String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();
        String testImagePath = pathToResource + "/testbild.jpg";
        String testImagePath2 = pathToResource + "/testbild.jpg";

        PictureHistory testPictureHistoryBefore = new PictureHistory(null, createUserForTest().getUser_id(), testImagePath, new Date());
        PictureHistory testPictureHistoryAfter = new PictureHistory(null, createUserForTest().getUser_id(), testImagePath2, new Date());
        updateValid(testPictureHistoryBefore, testPictureHistoryAfter);

        String savedImagePath = pathToResource + testPictureHistoryBefore.getLocation();
        String savedImagePath2 = pathToResource + testPictureHistoryAfter.getLocation();
        File savedImage = new File(savedImagePath);
        File savedImage2 = new File(savedImagePath2);
        assertTrue(savedImage.exists());
        assert (savedImage2.exists());

        if (savedImage.exists()) {
            savedImage.delete();
        }
        if (savedImage2.exists()) {
            savedImage2.delete();
        }

    }

    @Test(expected = PersistenceException.class)
    public void updateWithFileNotFoundShouldFail() throws Exception {
        PictureHistory testPictureHistory = new PictureHistory(null, createUserForTest().getUser_id(), "failPath", new Date());
        updateValid(testPictureHistory, testPictureHistory);
    }

    @Test(expected = PersistenceException.class)
    public void updateWithNullShouldFail() throws Exception {
        updateValid(null, null);
    }

    @Test
    public void getActualPictureWithValidId() throws Exception {
        String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();
        String testImagePath = pathToResource + "/testbild.jpg";

        User testUser1 = createUserForTest();
        User testUser2 = createUserForTest();
        PictureHistory pictureHistory1 = new PictureHistory(null, testUser1.getUser_id(), testImagePath, new Date());
        PictureHistory pictureHistory2 = new PictureHistory(null, testUser1.getUser_id(), testImagePath, new Date());
        PictureHistory pictureHistory3 = new PictureHistory(null, testUser1.getUser_id(), testImagePath, new Date());
        PictureHistory pictureHistory4 = new PictureHistory(null, testUser2.getUser_id(), testImagePath, new Date());

        List<PictureHistory> pictureHistoryList = new ArrayList<>();
        pictureHistoryList.add(pictureHistoryDAO.create(pictureHistory1));
        pictureHistoryList.add(pictureHistoryDAO.create(pictureHistory2));
        pictureHistoryList.add(pictureHistoryDAO.create(pictureHistory3));
        pictureHistoryList.add(pictureHistoryDAO.create(pictureHistory4));

        PictureHistory pictureHistoryActual = pictureHistoryDAO.getActualPicture(testUser1.getUser_id());
        assertEquals(pictureHistoryActual, pictureHistory3);

        for (int i = 0; i < pictureHistoryList.size(); i++) {
            String savedImagePath = pathToResource + pictureHistoryList.get(i).getLocation();
            File savedImage = new File(savedImagePath);
            assertTrue(savedImage.exists());
            if (savedImage.exists()) {
                savedImage.delete();
            }
        }

    }

    @Test(expected = PersistenceException.class)
    public void deleteWithNullShouldFail() throws Exception {
        pictureHistoryDAO.delete(null);
    }

    private User createUserForTest() throws Exception {
        User testUser = new User(null, "maxmustermann", true, 20, 194, "max.mustermann@gmail.com", "/path/playlist/", false);
        return userDAO.create(testUser);
    }

}
