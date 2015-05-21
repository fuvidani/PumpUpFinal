package sepm.ss15.grp16.persistence.dao.user;

import org.junit.Test;
import sepm.ss15.grp16.entity.user.PictureHistory;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.dao.AbstractDAOTest;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.dao.user.PictureHistoryDAO;
import sepm.ss15.grp16.persistence.dao.user.UserDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.io.File;
import java.util.Date;

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

    private User createUserForTest() throws Exception {
        User testUser = new User(null, "maxmustermann", true, 20, 194, "max.mustermann@gmail.com", "/path/playlist/", false);
        return userDAO.create(testUser);
    }

}
