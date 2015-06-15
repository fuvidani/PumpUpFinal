package sepm.ss15.grp16.service.user;

import org.junit.Test;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.service.AbstractServiceTest;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.user.impl.UserServiceImpl;

/**
 * This class provides methods for testing UserServices
 *
 * @author Michael Sober
 * @version 1.0
 */
public abstract class AbstractUserServiceTest extends AbstractServiceTest<User> {

    protected UserService userService;

    @Override
    public Service<User> getService() {
        return userService;
    }

    @Test(expected = ServiceException.class)
    public void newUserServiceWithNull() throws Exception {
        new UserServiceImpl(null);
    }

    @Test
    public void createWithValidUser() throws Exception {
        User testUser = new User(null, "msober", true, 20, 194, "michael.sober@ymail.com", "/path/playlist/", false);
        createTest(testUser);
    }

    @Test(expected = ServiceException.class)
    public void createWithNoneValidUser() throws Exception {
        User testUser = null;
        createTest(testUser);
    }

    @Test
    public void updateWithValidUser() throws Exception {
        User userBeforeUpdate = new User(null, "msober", true, 20, 194, "michael.p.sober@gmail.com", "/path/playlist/", false);
        User userAfterUpdate = new User(null, "ksober", false, 18, 174, "michael.sober@ymail.com", "/newpath/playlist", false);
        updateTest(userBeforeUpdate, userAfterUpdate);
    }

    @Test(expected = ServiceException.class)
    public void updateWithNoneValidUser() throws Exception {
        User userBeforeUpdate = null;
        User userAfterUpdate = new User(null, "ksober", false, 18, 174, "michael.sober@ymail.com", "/newpath/playlist", false);
        updateTest(userBeforeUpdate, userAfterUpdate);
    }

    @Test
    public void deleteWithValidUser() throws Exception {
        User testUser = new User(null, "msober", true, 20, 194, "michael.sober@ymail.com", "/path/playlist/", false);
        deleteTest(testUser);
    }

    @Test(expected = ServiceException.class)
    public void deleteWithNoneValidUser() throws Exception {
        User testUser = null;
        deleteTest(testUser);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidUsername() throws Exception {
        String toLongUsername = "asdfghjklqwertzuioyxcvbnmasdfghjkwertzuycvbnmasdfghj";
        User testUser = new User(null, toLongUsername, true, 20, 194, "michael.sober@ymail.com", "/path/playlist/", false);
        userService.validate(testUser);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidAge() throws Exception {
        User testUser = new User(null, "msober", true, -20, 194, "michael.sober@ymail.com", "/path/playlist/", false);
        userService.validate(testUser);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidHeight() throws Exception {
        User testUser = new User(null, "msober", true, 20, -194, "michael.sober@ymail.com", "/path/playlist/", false);
        userService.validate(testUser);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidEmail() throws Exception {
        String toLongEmail = "";
        for (int i = 0; i <= 300; i++) {
            toLongEmail = toLongEmail + "a";
        }
        User testUser = new User(null, "msober", true, 20, -194, toLongEmail, "/path/playlist/", false);
        userService.validate(testUser);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidUser() throws Exception {
        User testUser = null;
        userService.validate(testUser);
    }

}
