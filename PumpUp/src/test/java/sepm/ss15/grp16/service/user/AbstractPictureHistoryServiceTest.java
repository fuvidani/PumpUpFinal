package sepm.ss15.grp16.service.user;

import org.junit.Test;
import sepm.ss15.grp16.entity.user.PictureHistory;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.service.AbstractServiceTest;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * Created by michaelsober on 12.06.15.
 */
public abstract class AbstractPictureHistoryServiceTest extends AbstractServiceTest<PictureHistory> {

    protected PictureHistoryService pictureHistoryService;
    protected UserService userService;

    @Override
    public Service<PictureHistory> getService() {
        return pictureHistoryService;
    }

    @Test
    public void createWithValidPictureHistory() throws Exception{
        String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();
        String testImagePath = pathToResource + "/testbild.jpg";

        PictureHistory testPictureHistory = new PictureHistory(null, createUserForTest().getUser_id(), testImagePath, new Date());
        createTest(testPictureHistory);

        String savedImagePath = pathToResource + testPictureHistory.getLocation();
        File savedImage = new File(savedImagePath);
        assertTrue(savedImage.exists());
        if (savedImage.exists()) {
            savedImage.delete();
        }
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidPictureHistory() throws Exception{
        PictureHistory pictureHistory  = null;
        pictureHistoryService.validate(pictureHistory);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidUserID() throws Exception{
        String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();
        String testImagePath = pathToResource + "/testbild.jpg";

        PictureHistory testPictureHistory = new PictureHistory(null, null, testImagePath, new Date());
        pictureHistoryService.validate(testPictureHistory);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidImagePath() throws Exception{
        PictureHistory testPictureHistory = new PictureHistory(null, createUserForTest().getUser_id(), "", new Date());
        pictureHistoryService.validate(testPictureHistory);
    }

    private User createUserForTest() throws Exception {
        User testUser = new User(null, "maxmustermann", true, 20, 194, "max.mustermann@gmail.com", "/path/playlist/", false);
        return userService.create(testUser);
    }
}
