package PersitanceTest;

import org.junit.Test;
import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.persistence.dao.UserDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class provides methods for testing UserDAOs
 *
 * @author Michael Sober
 * @version 1.0
 */
public abstract class AbstractUserDaoTest {

    protected UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Test(expected = PersistenceException.class)
    public void createWithNullShouldThrowException() throws Exception{
        userDAO.create(null);
    }

    @Test
    public void createWithValidUserShouldPersist() throws Exception {
        User testUser = new User(null, "msober", true, 20, 194, false, null, null, null);
        List<User> allUsers = userDAO.findAll();
        assertFalse(allUsers.contains(testUser));
        userDAO.create(testUser);
        allUsers = userDAO.findAll();
        assertTrue(allUsers.contains(testUser));
    }
}
