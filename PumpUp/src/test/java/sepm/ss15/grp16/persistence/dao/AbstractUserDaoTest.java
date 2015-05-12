package sepm.ss15.grp16.persistence.dao;

import org.junit.Test;
import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.persistence.dao.UserDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

import static org.junit.Assert.assertEquals;
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

    @Test(expected = PersistenceException.class)
    public void createWithNullShouldThrowException() throws Exception{
        userDAO.create(null);
    }

    @Test
    public void createWithValidUserShouldPersist() throws Exception {
        User testUser = new User(null, "msober", true, 20, 194, false);
        List<User> allUsers = userDAO.findAll();
        assertFalse(allUsers.contains(testUser));
        userDAO.create(testUser);
        allUsers = userDAO.findAll();
        assertTrue(allUsers.contains(testUser));
    }

    @Test(expected = PersistenceException.class)
    public void deleteWithNullShouldThrowException() throws Exception{
        userDAO.delete(null);
    }

    @Test
    public void deleteWithValidUserShouldPersist() throws Exception{
        User testUser = new User(null, "msober", true, 20, 194, false);
        userDAO.create(testUser);
        List<User> allUsers = userDAO.findAll();
        assertTrue(allUsers.contains(testUser));
        userDAO.delete(testUser);
        testUser.setIsDeleted(true);
        allUsers = userDAO.findAll();
        assertTrue(allUsers.contains(testUser));
    }

    @Test(expected = PersistenceException.class)
    public void updateWithNullShouldThrowException() throws Exception{
        userDAO.update(null);
    }

    @Test
    public void updateWithValidParametersShouldPersist() throws Exception{
        User userBeforeUpdate = new User(null, "msober", true, 20, 194, false);
        User userAfterUpdate = new User(null, "ksober", false, 18, 174, false);
        userDAO.create(userBeforeUpdate);
        userAfterUpdate.setUser_id(userBeforeUpdate.getUser_id());
        List<User> allJockeys = userDAO.findAll();
        assertTrue(allJockeys.contains(userBeforeUpdate));
        userDAO.update(userAfterUpdate);
        allJockeys = userDAO.findAll();
        assertTrue(allJockeys.contains(userAfterUpdate));
    }

    @Test
    public void searchByIDShouldFindUser() throws Exception{
        User testUser1 = new User(null, "msober", true, 20, 194, false);
        User testUser2 = new User(null, "ksober", false, 18, 174, false);
        User testUser3 = new User(null, "psober", true, 48, 188, false);
        userDAO.create(testUser1);
        userDAO.create(testUser2);
        userDAO.create(testUser3);
        User foundTestUser2 = userDAO.searchByID(testUser2.getUser_id());
        assertEquals(testUser2, foundTestUser2);
    }

}
