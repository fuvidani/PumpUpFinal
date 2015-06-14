package sepm.ss15.grp16.service.user;

import org.junit.Test;
import sepm.ss15.grp16.entity.user.PictureHistory;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.service.AbstractServiceTest;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
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

    @Test
    public void updateWithValidPictureHistoryShouldPersist() throws Exception {
        String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();
        String testImagePath = pathToResource + "/testbild.jpg";
        String testImagePath2 = pathToResource + "/testbild.jpg";

        PictureHistory testPictureHistoryBefore = new PictureHistory(null, createUserForTest().getUser_id(), testImagePath, new Date());
        PictureHistory testPictureHistoryAfter = new PictureHistory(null, createUserForTest().getUser_id(), testImagePath2, new Date());
        updateTest(testPictureHistoryBefore, testPictureHistoryAfter);

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

    @Test
    public void deleteWithValidPictureHistoryShouldPersist() throws Exception {
        String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();
        String testImagePath = pathToResource + "/testbild.jpg";

        User testUser = createUserForTest();

        PictureHistory testPictureHistory = new PictureHistory(null, testUser.getUser_id(), testImagePath, new Date());
        deleteTest(testPictureHistory);

        String savedImagePath = pathToResource + testPictureHistory.getLocation();
        File savedImage = new File(savedImagePath);
        assertTrue(savedImage.exists());
        if (savedImage.exists()) {
            savedImage.delete();
        }
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
        pictureHistoryList.add(pictureHistoryService.create(pictureHistory1));
        pictureHistoryList.add(pictureHistoryService.create(pictureHistory2));
        pictureHistoryList.add(pictureHistoryService.create(pictureHistory3));
        pictureHistoryList.add(pictureHistoryService.create(pictureHistory4));

        PictureHistory pictureHistoryActual = pictureHistoryService.getActualPicture(testUser1.getUser_id());
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
