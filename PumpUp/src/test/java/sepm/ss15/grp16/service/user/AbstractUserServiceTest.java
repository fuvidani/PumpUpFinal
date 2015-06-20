package sepm.ss15.grp16.service.user;

import org.junit.Test;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.dao.user.UserDAO;
import sepm.ss15.grp16.service.AbstractServiceTestMockito;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.user.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * This class provides methods for testing UserServices
 *
 * @author Michael Sober
 * @version 1.0
 */
public abstract class AbstractUserServiceTest extends AbstractServiceTestMockito<User> {

    protected UserService userService;
    protected UserDAO mockedUserDAO;

    @Override
    public Service<User> getService() {
        return userService;
    }

    @Override
    public DAO<User> getMockedDAO() {
        return mockedUserDAO;
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
    public void createWithPersistenceException() throws Exception {
        User testUser = new User(null, "msober", true, 20, 194, "michael.sober@ymail.com", "/path/playlist/", false);
        createTestFail(testUser);
    }

    @Test
    public void updateWithValidUser() throws Exception {
        User userWithUpdatedData = new User(1, "ksober", false, 18, 174, "michael.sober@ymail.com", "/newpath/playlist", false);
        updateTest(userWithUpdatedData);
    }

    @Test(expected = ServiceException.class)
    public void updateWithPersistenceException() throws Exception {
        User userWithUpdatedData = new User(1, "ksober", false, 18, 174, "michael.sober@ymail.com", "/newpath/playlist", false);
        updateTestFail(userWithUpdatedData);
    }

    @Test
    public void deleteWithValidUser() throws Exception {
        User notDeletedUser = new User(1, "ksober", false, 18, 174, "michael.sober@ymail.com", "/newpath/playlist", false);
        deleteTest(notDeletedUser);
    }

    @Test(expected = ServiceException.class)
    public void deleteWithPersistenceException() throws Exception {
        User notDeletedUser = new User(1, "ksober", false, 18, 174, "michael.sober@ymail.com", "/newpath/playlist", false);
        deleteTestFail(notDeletedUser);
    }

    @Test
    public void findAllShouldReturnAllUsers() throws Exception {
        User testUser1 = new User(1, "msober", true, 20, 194, "michael.sober@ymail.com", "/path/playlist/", false);
        User testUser2 = new User(2, "msober", true, 20, 194, "michael.sober@ymail.com", "/path/playlist/", false);
        User testUser3 = new User(3, "msober", true, 20, 194, "michael.sober@ymail.com", "/path/playlist/", false);
        List<User> userList = new ArrayList<>();
        userList.add(testUser1);
        userList.add(testUser2);
        userList.add(testUser3);
        findAllTest(userList);
    }

    @Test(expected = ServiceException.class)
    public void findAllWithPersistenceException() throws Exception {
        findAllTestFail();
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
        for (int i = 0; i <= 320; i++) {
            toLongEmail = toLongEmail + "a";
        }
        User testUser = new User(null, "msober", true, 20, 194, toLongEmail, "/path/playlist/", false);
        userService.validate(testUser);
    }

    @Test(expected = ValidationException.class)
    public void validateWithNoneValidUser() throws Exception {
        User testUser = null;
        userService.validate(testUser);
    }

    @Test
    public void setGetLoggedInUserShouldWork() {
        User testUser = new User(null, "msober", true, 20, 194, "michael.sober@ymail.com", "/path/playlist/", false);
        userService.setLoggedInUser(testUser);
        assertEquals(userService.getLoggedInUser(), testUser);
    }

}
